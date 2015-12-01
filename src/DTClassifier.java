import java.io.BufferedReader;
import java.io.FileReader;

public class DTClassifier extends Classifier {

	public DTClassifier(String namesFilepath) {
		super(namesFilepath);
		try {
			 BufferedReader reader = new BufferedReader(new FileReader(namesFilepath));
			 String line;
			 while ((line = reader.readLine()) != null) {
				 
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makePredictions(String testDataFilepath) {
		// TODO Auto-generated method stub
		
	}

}
