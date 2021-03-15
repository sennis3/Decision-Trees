import java.util.ArrayList;
import java.util.Collections;

public class Node {
	ArrayList<Example> examples;
	ArrayList<Attribute> attributes;
	
	boolean isLeaf;
	String targetClassification;
	
	Node parentNode;
	String chosenAttrName;
	String branchLabel;
	
	ArrayList<Node> childNodes;
	
	
	//Constructor for a subtree node
	public Node(ArrayList<Example> examples, ArrayList<Attribute> attributes, String branchLabel, String chosenAttribute) {
		isLeaf = false;
		childNodes = new ArrayList<Node>();
		this.examples = new ArrayList<Example>();
		this.attributes = new ArrayList<Attribute>();
		
		for (int i = 0; i < examples.size(); i++) {
			this.examples.add(examples.get(i));
		}
		
		for (int i = 0; i < attributes.size(); i++) {
			this.attributes.add(attributes.get(i));
		}
		
		//this.parentNode = parentNode;
		this.branchLabel = branchLabel;
		this.chosenAttrName = chosenAttribute;
	}
	
	//Constructor for a leaf node
	public Node(String targetVal, String branchLabel) {
		isLeaf = true;
		
		this.branchLabel = branchLabel;
		this.targetClassification = targetVal;
		//this.parentNode = parentNode;
	}
	
	public boolean getIsLeaf() {
		return isLeaf;
	}
	
	public String getTargetClassification() {
		return targetClassification;
	}
	
	public String getChosenAttribute() {
		return chosenAttrName;
	}
	
	public ArrayList<Node> getChildNodes() {
		return childNodes;
	}
	
	public String getBranchLabel() {
		return branchLabel;
	}
}
