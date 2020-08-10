package Part2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DTLearning {
	int numCategories;
	int numAtts;
	static List<String> categoryNames;
	static List<String> attNames;
	List<Instance> allInstances;
	Node rootNode;

	public DTLearning(String file1, String file2) {
		readDataFile(file1);
		List<Integer> attributes = new ArrayList<Integer>();
		for(int i = 0; i<numAtts; i++) {
			attributes.add(i);
		}
		rootNode = buildTree(allInstances, attributes);
		printNodes("     ", rootNode);
		readDataFile(file2);
		test(allInstances);
	}

	public static void main(String[] args){
		if (args.length == 2) {
			String trainingF = args[0];
			String testF = args[1];
			new DTLearning(trainingF, testF);
		}
	}


	public Node buildTree(List<Instance> instances, List<Integer> attributes){
		if(instances.isEmpty()) {
			return baseline(instances);
		}
		if(nodeImpurity(instances) == 0) { //if pure
			String name = categoryNames.get(instances.get(0).getCategory());
			//System.out.println("3: "+name);
			return new Node(name, 1);
		}
		if(attributes.isEmpty()) {
			int[] maj = majorityClass(instances);
			String name = categoryNames.get(maj[1]);
			//System.out.println("4: "+name);
			return new Node(name, (double)maj[0]/instances.size());
		}
		else {	//find best attribute\
			ArrayList<Instance> bestTrueInsts = new ArrayList<Instance>();
			ArrayList<Instance> bestFalseInsts = new ArrayList<Instance>();
			Integer bestAttribute = 0;
			double bestPurity = Double.MAX_VALUE;
			
			//System.out.println("attributes size: "+attributes.size());
			for(Integer att : attributes) {
				ArrayList<Instance> trueInst = new ArrayList<Instance>();
				ArrayList<Instance> falseInst = new ArrayList<Instance>();
				//System.out.println("attribute "+att);
				for(Instance inst : instances){

					if(inst.getAtt(att) == true) {
						if(!trueInst.contains(inst)) {
							trueInst.add(inst);
						}
					}
					else if(inst.getAtt(att) == false) {
						if(!falseInst.contains(inst)) {
							falseInst.add(inst);
						}
					}
				}
				double tFraction = (double)trueInst.size()/(double)instances.size();
				double fFraction = (double)falseInst.size()/(double)instances.size();
				//System.out.println("tFrac: "+tFraction+ "  fFrac: "+fFraction);
				//System.out.println("t size: "+trueInst.size()+ "f size: "+falseInst.size()+"  i size: "+instances.size());
				double tImp = nodeImpurity(trueInst)*tFraction;
				double fImp = nodeImpurity(falseInst)*fFraction;
				double avgImp = tImp + fImp;
				//System.out.println("avgImp: "+avgImp+ "  bestPurity: "+bestPurity);
				if(avgImp < bestPurity) {
					bestPurity = avgImp;
					bestAttribute = att;
					bestTrueInsts = trueInst;
					bestFalseInsts = falseInst;
				}
			}
			//System.out.println("best att before remove: "+ bestAtt);
			//System.out.println("attributes.size(); "+ attributes.size());
			attributes.remove(bestAttribute);
			Node left = buildTree(bestTrueInsts, attributes);
			Node right = buildTree(bestFalseInsts, attributes);

			return new Node(bestAttribute, left, right);

		}
	}

	public void test(List<Instance> instances){
		boolean correct;
		int count = 0;
		for(int i = 0; i<instances.size(); i++){
			correct = classify(instances.get(i), rootNode);
			if(correct) {
				count++;
			}
		}
		System.out.println("total correct: "+count+"/"+instances.size());
		System.out.printf("percentage correct: %4.2f %%", (double)count/instances.size() * 100);
	}
	
	public boolean classify(Instance inst, Node node){
		if(node.getConstructor().equals("node")) {
			if(inst.getAtt(node.getAttribute())){
				return classify(inst, node.getLeft());
			}
			else{
				return classify(inst, node.getRight());
			}
		}
		else {
			if(DTLearning.categoryNames.indexOf(node.getName()) == inst.getCategory()){
				return true;
			}
			else {
				return false;
			}
		}
	}

	public void printNodes(String indent, Node node) {
		if(node.getConstructor().equals("node")){
			if (node.getLeft() != null) {
				System.out.format("%s%s = True :\n", indent, attNames.get(node.getAttribute()));
				printNodes(indent + "     ", node.getLeft());
			}

			if (node.getRight() != null) {
				System.out.format("%s%s = False :\n", indent, attNames.get(node.getAttribute()));
				printNodes(indent + "     ", node.getRight());
			}
		}
		else {
			System.out.format("%sClass: %s, Prob: %4.2f%% \n", indent, node.getName(), node.getProbability());
		}
	}

	private Node baseline(List<Instance> instances) {
		int a = 0, b = 0;
		for(int i = 0; i<instances.size(); i++) {
			if(instances.get(i).getCategory() == 0) {
				a++;
			}
			else {
				b++;
			}
		}
		if(a > b) {
			Node node = new Node(categoryNames.get(0), 0);
			if(instances.size() !=0) {
				node.setProbability(a / instances.size());
				//System.out.println("1: "+node.getName());
			}
			return node; 
		} 
		else {
			Node node = new Node(categoryNames.get(1), 0);
			if(instances.size() !=0) {
				node.setProbability(b / instances.size());
				//System.out.println("2: "+node.getName());
			}
			return node;
		}
	}

	public int[] majorityClass(List<Instance> instances) {
		int[] maj = new int[2];
		int a = 0, b = 0;
		for(int i = 0; i<instances.size(); i++) {
			if(instances.get(i).getCategory() == 0) {
				a++;
			}
			else {
				b++;
			}
		}
		if(a>b) {
			maj[0] = a;	//total
			maj[1] = 0;	//class
		}
		else if(b>a){
			maj[0] = b;
			maj[1] = 1;
		}
		else {
			int chosen;
			int random = (int)(Math.random()*2);
			if(random == 0) {
				chosen = a;
			}
			else {
				chosen = b;
			}
			maj[0] = chosen;
			maj[1] = random;
		}
		return maj;
	}

	public double nodeImpurity(List<Instance> instances){
		double imp;
		int a = 0, b = 0;
		for(int i = 0; i<instances.size(); i++) {
			if(instances.get(i).getCategory() == 0) {
				a++;
			}
			else {
				b++;
			}
		}
		imp = ((double)a / instances.size()) * ((double)b / instances.size());
		//	  System.out.println("imp: "+ imp);
		return imp;
	}

	private void readDataFile(String fname){
		/* format of names file:
		 * names of categories, separated by spaces
		 * names of attributes
		 * category followed by true's and false's for each instance
		 */
		System.out.println("Reading data from file "+fname);
		try {
			Scanner din = new Scanner(new File(fname));

			categoryNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) categoryNames.add(s.next());
			numCategories=categoryNames.size();
			System.out.println(numCategories +" categories");

			attNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) attNames.add(s.next());
			numAtts = attNames.size();
			System.out.println(numAtts +" attributes");

			allInstances = readInstances(din);
			din.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}

	private List<Instance> readInstances(Scanner din) {
		/* instance = classname and space separated attribute values */
		List<Instance> instances = new ArrayList<Instance>();
		String ln;
		while (din.hasNext()) {
			Scanner line = new Scanner(din.nextLine());
			instances.add(new Instance(categoryNames.indexOf(line.next()), line));

		}

		System.out.println("Read " + instances.size() + " instances");
		return instances;
	}
}
