import java.util.ArrayList;
import java.util.HashSet;

public class Attribute {
	String attributeName;
	ArrayList<ArrayList<String>> pairs;
	HashSet<String> valueOptions;
	int positives;
	int negatives;
	int totalCount;
	ArrayList<AttributeValueData> valueDataList;
	boolean alreadyChosen;
	
	//Attribute constructor
	public Attribute(String name, ArrayList<Example> examples, int index) {
		this.attributeName = name;
		positives = 0;
		negatives = 0;
		alreadyChosen = false;
		valueOptions = new HashSet<String>();
		pairs = new ArrayList<ArrayList<String>>();
		valueDataList = new ArrayList<AttributeValueData>();
		
		//Finds all the of the different value options (Vk)
		for (int i = 0; i < examples.size(); i++) {
			Example currExample = examples.get(i);
			String attributeValue = currExample.getValByIndex(index);
			String targetValue = currExample.getTargetValue();
			
			valueOptions.add(attributeValue);
			
			ArrayList<String> newPair = new ArrayList<String>();
			newPair.add(attributeValue);
			newPair.add(targetValue);
			
			pairs.add(newPair);
		}
		
		//Counts the different values to make put in a AttributeValueData object
		for (String valueOption : valueOptions) {
			int valueCount = 0;
			int positiveCount = 0;
			int negativeCount = 0;
			
			for (int i = 0; i < pairs.size(); i++) {
				ArrayList<String> currPair = pairs.get(i);
				
				if (currPair.get(0).equals(valueOption)) {
					valueCount++;
					String goalValue = currPair.get(1);
					if (goalValue.toUpperCase().equals("YES")) {
						positiveCount++;
						positives++;
					}
					else {
						negativeCount++;
						negatives++;
					}
				}
			}
			totalCount = positives + negatives;
			
			AttributeValueData newData = new AttributeValueData(valueOption, valueCount, positiveCount, negativeCount);
			valueDataList.add(newData);
		}
		
	}
	
	//Calculates the entropy of a boolean random variable with probability q
	public double calcEntropy(double q) {  //q = probability
		if(q == 0 || q == 1) {
			return 0;
		}
		
		double entropy = -(q * log2(q) + (1-q) * log2(1-q));  //the entropy formula
		return entropy;
	}
	
	//Calculates log based 2 of x
	public double log2(double x) {
		return Math.log(x) / Math.log(2);
	}
	
	//Calculates the probability function ( p/(p+n) )
	public double calcProbability(int positives, int negatives) {
		double probability = (double) positives / (positives + negatives);
		return probability;
	}
	
	//Calculates the attributes entropy remainder
	public double entropyRemainder() {
		double remainder = 0;
		
		for (int i = 0; i < valueDataList.size(); i++) {
			AttributeValueData currAttributeData = valueDataList.get(i);
			int dataPositives = currAttributeData.getPositives();
			int dataNegatives = currAttributeData.getNegatives();
			int dataTotalCount = dataPositives + dataNegatives;
			
			remainder = remainder + (double) ((double) dataTotalCount / totalCount) * calcEntropy((double) dataPositives / dataTotalCount);
		}
		
		return remainder;
	}
	
	//Calculates the attributes information gain
	public double informationGain() {
		double gain = calcEntropy((double) positives / totalCount) - entropyRemainder(); //subtracts remainder
		return gain;
	}
	
	
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public ArrayList<AttributeValueData> getValueDataList(){
		return valueDataList;
	}

}
