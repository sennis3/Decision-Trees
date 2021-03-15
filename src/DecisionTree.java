/*
 * Sean Ennis
 * sennis3
 * 653900061
 * CS411 - Assignment 12
 * May 3, 2020
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DecisionTree {

	public static void main(String[] args) throws FileNotFoundException{
		
		ArrayList<String> attributeNames = new ArrayList<String>();
		ArrayList<Example> exampleList = new ArrayList<Example>();
		
		receiveInput(args, attributeNames, exampleList);
		//printDataTable(attributeNames, exampleList);
		
		ArrayList<Attribute> attributes = createAttributeList(attributeNames, exampleList);
		ArrayList<String> chosenAttrs = new ArrayList<String>();
		
		if (exampleList.isEmpty()) {
			System.out.println("Error: No examples input for training");
		}
		else {
			Node treeRootNode = decisionTreeLearning(exampleList, attributes, exampleList, "", chosenAttrs);
			
			System.out.println("Decision Tree:");
			System.out.println();
			printDecisionTree(treeRootNode, 0);
		}
	}
	
	//Parses CSV file and fills the arraylists
	public static void receiveInput(String args[], ArrayList<String> attributeNames, ArrayList<Example> exampleList) throws FileNotFoundException{
		//Read in input from file
		File inputFile = null;
		if (args.length > 0) {
			inputFile = new File(args[0]);
		}
		else {
			System.out.println("Input File Missing");
			System.exit(0);
		}
		
		Scanner scan = new Scanner(inputFile);
		
		boolean attributeListEmpty = true;
		
		//Parses input line by line for each example input
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			line = line.replaceAll("\\s+", "");
			String[] splitLine = line.split(",");
			
			if (attributeListEmpty) { 
				//Fills in the list of attributes
				Collections.addAll(attributeNames, splitLine);
				attributeListEmpty = false;
			}
			else {
				//Gets attribute values for each example
				ArrayList<String> attributeValues = new ArrayList<String>();
				Collections.addAll(attributeValues, splitLine);
				Example newExample = new Example(attributeNames, attributeValues);  //Creates new example object
				exampleList.add(newExample);  //Adds example to the list of examples
			}
		}
		scan.close();
	}
	
	//Creates an arraylist of attributes based on the examples given
	public static ArrayList<Attribute> createAttributeList(ArrayList<String> attributeNames, ArrayList<Example> examples){
		ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
		
		for (int i = 0; i < attributeNames.size(); i++) {
			Attribute newAttributeObj = new Attribute(attributeNames.get(i), examples, i);
			attributeList.add(newAttributeObj);
		}
		
		return attributeList;
	}
	
	//Finds the attribute with the highest information gain
	public static Attribute maxImportance(ArrayList<Attribute> attributeList, ArrayList<String> chosenAttrs) {
		String maxAttribute = "";
		double maxGain = -1;
		Attribute maxGainAttribute = attributeList.get(0);
		
		System.out.println("Information Gain:");
		
		//Finds the max info gain value of the available attributes
		for (int i = 0; i < attributeList.size()-1; i++) {
			Attribute currAttr = attributeList.get(i);
			if (!(chosenAttrs.contains(currAttr.getAttributeName()))) {
				double attributeGain = attributeList.get(i).informationGain();
				System.out.println(attributeList.get(i).getAttributeName() + ": " + attributeGain);
				if (attributeGain > maxGain) {
					maxGain = attributeGain;
					maxAttribute = attributeList.get(i).getAttributeName();
					maxGainAttribute = attributeList.get(i);
				}
			}
		}
		
		System.out.println("Highest Information Gain: " + maxAttribute + " (" + maxGain + ")");
		System.out.println();
		System.out.println();
		chosenAttrs.add(maxAttribute); //Adds attribute to the list of already chosen attributes
		
		//Return the attribute object
		return maxGainAttribute;
	}
	
	//Performs the decision tree learning algorithm and returns the root node of the tree
	public static Node decisionTreeLearning(ArrayList<Example> examples, ArrayList<Attribute> attributes, ArrayList<Example> parentExamples, String branchLabel, ArrayList<String> chosenAttrs) {
		//Returns parent plurality if there are no examples left
		if (examples.isEmpty()) {
			String parentPlurality = pluralityValue(parentExamples);
			Node newLeaf = new Node(parentPlurality, branchLabel);
			return newLeaf;
		}
		//Returns the classification value if all examples have the same value
		else if (allSameClassification(examples)) {
			String classification = examples.get(0).getTargetValue();
			Node newLeaf = new Node(classification, branchLabel);
			return newLeaf;
		}
		//Returns the plurality value if there are no attributes left
		else if (attributes.isEmpty()) {
			String pluralityVal = pluralityValue(examples);
			Node newLeaf = new Node(pluralityVal, branchLabel);
			return newLeaf;
		}
		else {
			ArrayList<Attribute> updatedAttrList = createAttributeList(getAttributeNames(attributes), examples);
			
			printDataTable(getAttributeNames(updatedAttrList), examples); //Prints current table
			
			Attribute chosenAttribute = maxImportance(updatedAttrList, chosenAttrs); //Chooses attribute
			Node newNode = new Node(examples, attributes, branchLabel, chosenAttribute.getAttributeName());
			
			ArrayList<AttributeValueData> valueDataList = chosenAttribute.getValueDataList();
			for (int i = 0; i < valueDataList.size(); i++) {
				String attrValue = valueDataList.get(i).getValue();
				
				ArrayList<Example> exs = new ArrayList<Example>();
				for (int j = 0; j < examples.size(); j++) {
					if (examples.get(j).getValByAttrName(chosenAttribute.getAttributeName()).equals(attrValue)) {
						exs.add(examples.get(j));
					}
				}
				
				//Creates a new child node for the current node
				Node childNode = decisionTreeLearning(exs, updatedAttrList, examples, attrValue, chosenAttrs);
				newNode.getChildNodes().add(childNode);
			}
			return newNode;
		}
	}
	
	//Gets the plurality value of the examples
	public static String pluralityValue(ArrayList<Example> examples){
		int positiveCount = 0;
		int negativeCount = 0;
		
		for(int i = 0; i < examples.size(); i++) {
			String targetVal = examples.get(i).getTargetValue();
			if (targetVal.toUpperCase().equals("YES")) {
				positiveCount++;
			}
			else {
				negativeCount++;
			}
		}
		
		if(positiveCount >= negativeCount) {
			return "Yes";
		}
		else {
			return "No";
		}
	}
	
	//Checks if all the examples have the same goal classification
	public static boolean allSameClassification(ArrayList<Example> examples) {
		boolean yesFound = false;
		boolean noFound = false;
		
		for(int i = 0; i < examples.size(); i++) {
			String targetVal = examples.get(i).getTargetValue();
			if (targetVal.toUpperCase().equals("YES")) {
				yesFound = true;
			}
			else {
				noFound = true;
			}
		}
		
		if (yesFound && noFound) { //if there are both yes and no's they are not all the same classification
			return false;
		}
		else {
			return true;
		}
	}
	
	//Prints the resulting decision tree
	public static void printDecisionTree(Node currNode, int treeLevel) {		
		for (int i = 0; i < treeLevel; i++) { //prints a longer line as the levels increase
			System.out.print("------");
		}
		
		if (treeLevel > 0) { //doesn't print branch value for first node
			System.out.print(" " + currNode.getBranchLabel() + " --> ");
		}
		
		if (currNode.getIsLeaf()) { //if the node is a leaf node it prints the classification
			System.out.println(currNode.targetClassification);
		}
		else { //the node is an attribute
			System.out.println(currNode.getChosenAttribute());
			
			ArrayList<Node> childNodes = currNode.getChildNodes();
			for (int i = 0; i < childNodes.size(); i++) {
				printDecisionTree(childNodes.get(i), treeLevel+1); //recursively calls function for child nodes
			}
		}
	}
	
	//Gets all of the attribute headers for a list of attributes
	public static ArrayList<String> getAttributeNames(ArrayList<Attribute> attributes) {
		ArrayList<String> attributeNames = new ArrayList<String>();
		for (int i = 0; i < attributes.size(); i++) {
			attributeNames.add(attributes.get(i).getAttributeName());
		}
		return attributeNames;
	}
	
	//Prints the training data table (based on the examples left)
	public static void printDataTable(ArrayList<String> attributeNames, ArrayList<Example> examples) {
		//Prints the attribute headers
		for (int i = 0; i < attributeNames.size(); i++) {
			System.out.format("%-10s", attributeNames.get(i));  //formatted to look better
		}
		
		System.out.println();
		
		for (int i = 0; i < attributeNames.size(); i++) {
			System.out.print("----------");
		}
		
		System.out.println();
		
		//Prints the examples with attribute values
		for (int i = 0; i < examples.size(); i++) {
			Example currEx = examples.get(i);
			ArrayList<String> attrValues = currEx.getAttributeValues();
			for (int j = 0; j < attrValues.size(); j++) {
				System.out.format("%-10s", attrValues.get(j)); //formatted to look better
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
	}
	
	//Makes a deep copy of a list of attributes
	public static ArrayList<Attribute> copyAttributeList(ArrayList<Attribute> attributes) {
		ArrayList<Attribute> attributesCopy = new ArrayList<Attribute>();
		for (int i = 0; i < attributes.size(); i++) {
			attributesCopy.add(attributes.get(i));
		}
		return attributesCopy;
	}
	
	//Makes a deep copy of a list of examples
	public static ArrayList<Example> copyExampleList(ArrayList<Example> examples) {
		ArrayList<Example> examplesCopy = new ArrayList<Example>();
		for (int i = 0; i < examples.size(); i++) {
			examplesCopy.add(examples.get(i));
		}
		return examplesCopy;
	}

}
