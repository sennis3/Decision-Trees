//Keeps track of each attribute's data

public class AttributeValueData {
	
	String value;
	int count;
	int positives;
	int negatives;
	
	public AttributeValueData(String value, int count, int positives, int negatives) {
		this.value = value;
		this.count = count;
		this.positives = positives;
		this.negatives = negatives;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getPositives() {
		return positives;
	}
	
	public int getNegatives() {
		return negatives;
	}
}
