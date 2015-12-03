import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class LogReg extends Classifier {
	//All features and their possible values, numeric will only store numeric in the array
	LinkedHashMap<String, ArrayList<String>> featureValues= new LinkedHashMap<String, ArrayList<String>>();
	ArrayList<String> featureToIndex = new ArrayList<String>();
	static ArrayList<ArrayList<Double>> testData = new ArrayList<ArrayList<Double>>();
	ArrayList<String> outputClass = new ArrayList<String>();
	static ArrayList<Integer> y = new ArrayList<Integer>();

	public LogReg(String namesFilepath) {
		super(namesFilepath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(namesFilepath));
			String line;

			if((line = reader.readLine()) != null) { //get first line for output classes
				String[] entry = line.split("[\\s]+");
				for (String s: entry){
					outputClass.add(s); //store all possible output classes
				}
			}

			while ((line = reader.readLine()) != null) { //get rest of information
				while (line.trim().isEmpty()){ //ignore blank lines
					line = reader.readLine();}

				String[] entry = line.split("[\\s]+");
				String feature = entry[0];
				featureToIndex.add(feature);
				ArrayList<String>values = new ArrayList<String>();
				for (int i = 1; i < entry.length; i++) 
					values.add(entry[i]);
				featureValues.put(feature, values);
			}
			reader.close();
		}
		catch (Exception e){
			System.err.format("Exception occurred trying to read '%s'.", namesFilepath);
			e.printStackTrace();
		}
	}

	@Override
	public void train(String trainingDataFilpath) {
		//hashmap to store the count of output values to their number of occurences
		//TreeMap<String, Integer> outputCount = new TreeMap<String, Integer>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(trainingDataFilpath));
			String line;
			while ((line = reader.readLine()) != null) {
				while (line.trim().isEmpty()) //ignore blank lines
					line = reader.readLine();
				String arr[] = line.split("[\\s]+");
				ArrayList<Double> entry = new ArrayList<Double>();
				entry.add((double)1);
			    for(int i = 0; i < arr.length-1; i++){
				    entry.add((double) featureValues.get(featureToIndex.get(i)).indexOf(arr[i].toString())+ 1);
			    }
				testData.add(entry);
								
				//create separate vector for output classifications
				if (arr[arr.length-1].equals(outputClass.get(0)))
					y.add(0);
				else if (arr[arr.length-1].equals(outputClass.get(1)))
					y.add(1);
				else
					y.add(-1);
			}

			System.out.println(testData.toString());
//			System.out.println(y.toString());
			reader.close();
		}
		catch (Exception e){
			System.err.format("Exception occurred trying to read '%s'.", trainingDataFilpath);
			e.printStackTrace();
		}		
	}

	@Override
	public void makePredictions(String testDataFilepath) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Double> d_theta(ArrayList<Double> theta, ArrayList<ArrayList<Double>> x, ArrayList<Integer> y) {
		ArrayList<Double> new_thetas = new ArrayList<Double>();
		for(int j = 0; j < featureToIndex.size(); j++){
			double sum2 = 0;
			for(int i = 0; i < x.size(); i++) {
				double sum = 0.0;
				for(int k = 0; k < theta.size(); k++) {
					sum += theta.get(k)*x.get(i).get(k);
				}
				sum2 += (sum-y.get(i))*x.get(i).get(j);
			}
	
			new_thetas.add(sum2/(double)(x.size()));
		}
		return new_thetas;
	}
	
	public double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
	}
	
//	public ArrayList<Double> minCost(ArrayList<Double> hypothesis, ArrayList<Integer> y ) {
//		for(int j = 0; j < featureToIndex.size(); j++){
//			double val = 0;
//			for (int i = 0; i < y.size(); i++) {
//				val += (hypothesis.get(i) - y.get(i))*;
//			}
//		}
//		return val/;
//	}
	

	public static void main(String[] args) {
		LogReg lr = new LogReg("census.names");
		lr.train("census2.train");
		
		ArrayList<Double> theta = new ArrayList<Double>();
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);
		theta.add(1.0);

		
		System.out.println(lr.d_theta(theta, testData, y).toString());
	}
}
