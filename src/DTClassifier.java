import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class DTClassifier extends Classifier {
	ArrayList<String> outputclass = new ArrayList<String>();
	LinkedHashMap<String, ArrayList<featureValue>> features = new LinkedHashMap<String, ArrayList<featureValue>>();
	Object[] featureToIndex;

	public class featureValue {
		private String name;
		public featureValue(String name) {
			this.name = name; //get the name of the feature name ie: private, self-employed, federal gov't, etc...
			TreeMap<String, Integer> classVal = new TreeMap <String, Integer>(); //create a mapping for each feature value to possible classifications
			for (String s : outputclass) {
				classVal.put(s, 0); //initialize all values to zero to begin
			}
		}
		public String toString() {
			return this.name;
		}

	}

	public DTClassifier(String namesFilepath) {
		super(namesFilepath);


		try {
			BufferedReader reader = new BufferedReader(new FileReader(namesFilepath));
			String line;

			if((line = reader.readLine()) != null) { //get first line for output classes
				for (String s : line.split("[\\s]+"))
					outputclass.add(s); //add all possible output classifications
			}

			while ((line = reader.readLine()) != null) { //get rest of information
				if (line.trim().isEmpty()) //ignore blank lines
					line = reader.readLine();

				String arr[] = line.split("[\\s]+");
				ArrayList<featureValue> feat_names = new ArrayList<featureValue>();
				for (int i = 1; i < arr.length; i++) { 
					featureValue newVal = new featureValue(arr[i]);
					feat_names.add(newVal); //get possible values of features
				}
				features.put(arr[0], feat_names); //add feature name and possible feature values
			}		
			
			featureToIndex = features.keySet().toArray(); //changes key set to array to map indices to features
			
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
				if (line.trim().isEmpty()) //ignore blank lines
					line = reader.readLine();
				String arr[] = line.split("[\\s]+");
				ArrayList<String> singleperson = new ArrayList<String>();
				for (int i = 0; i < arr.length-1; i++) {
					if(features.get(featureToIndex[i]).get(0).toString().equals("numeric") && features.get(featureToIndex[i]).size() == 1)
						System.out.println(featureToIndex[i]);
					singleperson.add(arr[i]);
				}
				trainingList.add(singleperson); //contains all information for single person from census data
			}


			reader.close();
		}
		catch (Exception e){
			System.err.format("Exception occurred trying to read '%s'.", trainingDataFilpath);
			e.printStackTrace();
		}

//		for(String s: this.features.keySet()) {
//			if(features.get(s).get(0).equals("numeric") && features.get(s).size() == 1) { //do something different if feature is numeric value
//				//System.out.println(s);
//			}
//			else { //keep track of feature and output class
//
//			}
//		}
	}

	@Override
	public void makePredictions(String testDataFilepath) {
		// TODO Auto-generated method stub

	}

	public ArrayList<String> getOutputClass() {
		return this.outputclass;
	}

	public HashMap<String, ArrayList<featureValue>> getFeatures() {
		return this.features;
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
		dt.train("census.train");
	}
}
