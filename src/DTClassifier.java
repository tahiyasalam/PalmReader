import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class DTClassifier extends Classifier {

	public DTClassifier(String namesFilepath) {
		super(namesFilepath);
		ArrayList<String> outputclass = new ArrayList<String>();
		TreeMap<String, ArrayList<String>> features = new TreeMap<String, ArrayList<String>>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(namesFilepath));
			String line;

			if((line = reader.readLine()) != null) { //get first line for output classes
				for (String s : line.split("[\\s]+"))
					outputclass.add(s);
			}

			line = reader.readLine(); //ignore blank line

			while ((line = reader.readLine()) != null) { //get rest of information
				String arr[] = line.split("[\\s]+");
				ArrayList<String> feat_names = new ArrayList<String>();
				for (int i = 1; i < arr.length; i++) {
					feat_names.add(arr[i]);
				}
				features.put(arr[0], feat_names);
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
		ArrayList<ArrayList<String>> trainingList = new ArrayList<ArrayList<String>>();	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(trainingDataFilpath));
			String line;
			while ((line = reader.readLine()) != null) {
				String arr[] = line.split("[\\s]+");
				ArrayList<String> singleperson = new ArrayList<String>();
				for (int i = 1; i < arr.length; i++) {
					singleperson.add(arr[i]);
				}
				trainingList.add(singleperson);
			}
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
	
	public double gain(ArrayList<ArrayList<String>>listOfLists, String feature) {
		double entropy_s = entropy(listOfLists);
		
		
		return 0;
	}
	
	public double entropy(ArrayList<ArrayList<String>>listOfLists) {
		double p_plus = 0;
		double p_neg = 0;
		for (int i = 0; i < listOfLists.size(); i++) {
			if (listOfLists.get(i).get(listOfLists.get(i).size()-1).equals("true")) {
				p_plus += 1;
			}
			else
				p_neg += 1;
		}

		double p_total = p_plus + p_neg;
		p_plus = (p_plus/p_total);
		p_neg = (p_neg/p_total);
		
		return -1*(p_plus)*Math.log(p_plus)/Math.log(2)-(p_neg)*Math.log(p_neg)/Math.log(2);
	}
	

	public static void main(String[]args) {
		DTClassifier dt = new DTClassifier("census.names");
	}
}
