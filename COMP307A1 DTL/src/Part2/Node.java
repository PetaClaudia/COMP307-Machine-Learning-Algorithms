package Part2;

public class Node{

	private String constructor;
	private String name;
	private Node left, right;
	private double probability;
	private int attribute;
	
	 public Node(int att, Node l, Node r) {
		 this.constructor = "node";
		 this.setLeft(l);
		 this.setRight(r);
		 this.setAttribute(att);
	 }
	 
	 public Node(String name, double prob) {
		 this.constructor = "leaf";
		 this.setName(name);
		 this.setProbability(prob);
	 }
	 
	 public String getConstructor() {
		 return constructor;
	 }

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

