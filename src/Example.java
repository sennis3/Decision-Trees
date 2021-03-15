import java.util.ArrayList;

public class Example {
	ArrayList<String> attributeNames;
	ArrayList<String> attributeValues;
	
	public Example(ArrayList<String> attributeNames, ArrayList<String> attributeValues) {
		//Creates list of attributes
		this.attributeNames = new ArrayList<String>();
		for (int i = 0; i < attributeNames.size(); i++) {
			this.attributeNames.add(attributeNames.get(i));
		}
		
		//Creates list of attribute values
		this.attributeValues = new ArrayList<String>();
		for (int i = 0; i < attributeValues.size(); i++) {
			this.attributeValues.add(attributeValues.get(i));
		}
	}
	
	//Finds the example's attribute value for a given attribute name
	public String getValByAttrName(String attrName) {
		int index = -1;
		for (int i = 0; i < attributeNames.size(); i++) {
			if (attrName.equals(attributeNames.get(i))) {
				index = i;
			}
		}
		return getValByIndex(index);
	}
	
	/*
	public void removeAttribute(String attributeName) {
		for (int i = 0; i < attributeNames.size(); i++) {
			if (attributeName.equals(attributeNames.get(i))) {
				attributeValues.remove(i);
				attributeNames.remove(i);
			}
		}
	}*/
	
	//Finds attribute value given its index in the arraylist
	public String getValByIndex(int index) {
		return attributeValues.get(index);
	}
	
	//Gets the goal value at the end of the list
	public String getTargetValue() {
		return attributeValues.get(attributeValues.size()-1);
	}
	
	public ArrayList<String> getAttributeValues() {
		return attributeValues;
	}
}
