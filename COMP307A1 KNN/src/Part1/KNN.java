package Part1;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class KNN {

	public static int k;
	public ArrayList<Wine> trainingList;
	public static ArrayList<Wine> testList; 
	public ArrayList<WineDist> distances;
	public ArrayList<WineDist> distancesClone;
	public ArrayList<Wine> class1;
	public ArrayList<Wine> class2;
	public ArrayList<Wine> class3;
	public static int count;
	public double alcRange, mAcidRange, ashRange, ashAlcRange, magRange, phenRange, flavRange, 
	nonFlavRange, proanRange, intensityRange, hueRange, dRange, prolineRange;
	
	public KNN(String file1, String file2) {
		File trainingFile = new File(file1);
		File testFile = new File(file2);
		trainingList = new ArrayList<Wine>();
		testList = new ArrayList<Wine>();
		class1 = new ArrayList<Wine>();
		count = 0;
		parser(trainingFile, testFile);
		scale();
		for(Wine testEntry : testList) {
			predict(testEntry);
		}
	}
	public void parser(File trainingFile, File testFile) {
		try {
			Scanner scan = new Scanner(testFile);
			scan.nextLine();
			while(scan.hasNext()) {
				String line = scan.nextLine();
				String[] tokens = line.split(" ");
				// process the tokens
				double al = Double.parseDouble(tokens[0]);
				double mAcid = Double.parseDouble(tokens[1]);
				double ash = Double.parseDouble(tokens[2]);
				double ashAlc = Double.parseDouble(tokens[3]);
				double mag = Double.parseDouble(tokens[4]);
				double phen = Double.parseDouble(tokens[5]);
				double flav = Double.parseDouble(tokens[6]);
				double nonFlav = Double.parseDouble(tokens[7]);
				double proan = Double.parseDouble(tokens[8]);
				double intensity = Double.parseDouble(tokens[9]);
				double hue = Double.parseDouble(tokens[10]);
				double d = Double.parseDouble(tokens[11]);
				double proline = Double.parseDouble(tokens[12]);
				int type = Integer.parseInt(tokens[13]);
				
				Wine wine = new Wine(al, mAcid, ash, ashAlc, mag, phen, flav, nonFlav, 
						proan, intensity, hue, d, proline, type);
				testList.add(wine);
			}
			System.out.println("test size: "+testList.size());
			System.out.println(testList.get(1).toString());
			scan.close();
			
			scan = new Scanner(trainingFile);
			scan.nextLine();
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] tokens = line.split(" ");
				// process the tokens
				double al = Double.parseDouble(tokens[0]);
				double mAcid = Double.parseDouble(tokens[1]);
				double ash = Double.parseDouble(tokens[2]);
				double ashAlc = Double.parseDouble(tokens[3]);
				double mag = Double.parseDouble(tokens[4]);
				double phen = Double.parseDouble(tokens[5]);
				double flav = Double.parseDouble(tokens[6]);
				double nonFlav = Double.parseDouble(tokens[7]);
				double proan = Double.parseDouble(tokens[8]);
				double intensity = Double.parseDouble(tokens[9]);
				double hue = Double.parseDouble(tokens[10]);
				double d = Double.parseDouble(tokens[11]);
				double proline = Double.parseDouble(tokens[12]);
				int type = Integer.parseInt(tokens[13]);
				
				Wine wine = new Wine(al, mAcid, ash, ashAlc, mag, phen, flav, nonFlav, 
						proan, intensity, hue, d, proline, type);
				trainingList.add(wine);
			}
			System.out.println("training size: "+trainingList.size());
			System.out.println(trainingList.get(1).toString());
			scan.close();
		} catch (Exception e) {
			System.out.println("Parse Error");
			e.printStackTrace();
		}
	}
	public void scale () {
		double alcMax = Double.NEGATIVE_INFINITY;
		double alcMin = Double.POSITIVE_INFINITY;
		double mAcidMax = Double.NEGATIVE_INFINITY;
		double mAcidMin = Double.POSITIVE_INFINITY;
		double ashMax = Double.NEGATIVE_INFINITY;
		double ashMin = Double.POSITIVE_INFINITY;
		double ashAlcMax = Double.NEGATIVE_INFINITY;
		double ashAlcMin = Double.POSITIVE_INFINITY;
		double magMax = Double.NEGATIVE_INFINITY;
		double magMin = Double.POSITIVE_INFINITY;
		double phenMax = Double.NEGATIVE_INFINITY;
		double phenMin = Double.POSITIVE_INFINITY;
		double flavMax = Double.NEGATIVE_INFINITY;
		double flavMin = Double.POSITIVE_INFINITY;
		double nonFlavMax = Double.NEGATIVE_INFINITY;
		double nonFlavMin = Double.POSITIVE_INFINITY;
		double proanMax = Double.NEGATIVE_INFINITY;
		double proanMin = Double.POSITIVE_INFINITY;
		double intensityMax = Double.NEGATIVE_INFINITY;
		double intensityMin = Double.POSITIVE_INFINITY;
		double hueMax = Double.NEGATIVE_INFINITY;
		double hueMin = Double.POSITIVE_INFINITY;
		double dMax = Double.NEGATIVE_INFINITY;
		double dMin = Double.POSITIVE_INFINITY;
		double prolineMax = Double.NEGATIVE_INFINITY;
		double prolineMin = Double.POSITIVE_INFINITY;
		
		for(Wine w : testList) {
			if(w.alcohol<alcMin) {
				alcMin = w.alcohol;
			}
			if(w.alcohol>alcMax) {
				alcMax = w.alcohol;
			}
			if(w.malicAcid<mAcidMin) {
				mAcidMin = w.malicAcid;
			}
			if(w.malicAcid>mAcidMax) {
				mAcidMax = w.malicAcid;
			}
			if(w.ash<ashMin) {
				ashMin = w.ash;
			}
			if(w.ash>ashMax) {
				ashMax = w.ash;
			}
			if(w.ashAlcalinity<ashAlcMin) {
				ashAlcMin = w.ashAlcalinity;
			}
			if(w.ashAlcalinity>ashAlcMax) {
				ashAlcMax = w.ashAlcalinity;
			}
			if(w.magnesium<magMin) {
				magMin = w.magnesium;
			}
			if(w.magnesium>magMax) {
				magMax = w.magnesium;
			}
			if(w.totalPhenols<phenMin) {
				phenMin = w.totalPhenols;
			}
			if(w.totalPhenols>phenMax) {
				phenMax = w.totalPhenols;
			}
			if(w.flavanoids<flavMin) {
				flavMin = w.flavanoids;
			}
			if(w.flavanoids>flavMax) {
				flavMax = w.flavanoids;
			}
			if(w.nonFlavanoid<nonFlavMin) {
				nonFlavMin = w.nonFlavanoid;
			}
			if(w.nonFlavanoid>nonFlavMax) {
				nonFlavMax = w.nonFlavanoid;
			}
			if(w.proanthocyanins<proanMin) {
				proanMin = w.proanthocyanins;
			}
			if(w.proanthocyanins>proanMax) {
				proanMax = w.proanthocyanins;
			}
			if(w.colIntensity<intensityMin) {
				intensityMin = w.colIntensity;
			}
			if(w.colIntensity>intensityMax) {
				intensityMax = w.colIntensity;
			}
			if(w.hue<hueMin) {
				hueMin = w.hue;
			}
			if(w.hue>hueMax) {
				hueMax = w.hue;
			}
			if(w.diluted<dMin) {
				dMin = w.diluted;
			}
			if(w.diluted>dMax) {
				dMax = w.diluted;
			}
			if(w.proline<prolineMin) {
				prolineMin = w.proline;
			}
			if(w.proline>prolineMax) {
				prolineMax = w.proline;
			}
		}
		this.alcRange = alcMax-alcMin;
		this.mAcidRange = mAcidMax - mAcidMin;
		this.ashRange = ashMax - ashMin;
		this.ashAlcRange = ashAlcMax - ashAlcMin;
		this.magRange = magMax - magMin;
		this.phenRange = phenMax - phenMin;
		this.flavRange = flavMax - flavMin;
		this.nonFlavRange = nonFlavMax - nonFlavMin;
		this.proanRange = proanMax - proanMin;
		this.intensityRange = intensityMax - intensityMin;
		this.hueRange = hueMax - hueMin;
		this.dRange = dMax - dMin;
		this.prolineRange = prolineMax - prolineMin;
	}
	
	public double euclideanDist(Wine a, Wine b) {
		double dist = 0;
		
		double alcDist = ((a.alcohol-b.alcohol)*(a.alcohol-b.alcohol))/this.alcRange;
		double mAcidDist = ((a.malicAcid-b.malicAcid)*(a.malicAcid-b.malicAcid))/this.mAcidRange;
		double ashDist = ((a.ash-b.ash)*(a.ash-b.ash))/this.ashRange;
		double ashAlcDist = ((a.ashAlcalinity-b.ashAlcalinity)*(a.ashAlcalinity-b.ashAlcalinity))/this.ashAlcRange;
		double magDist = ((a.magnesium-b.magnesium)*(a.magnesium-b.magnesium))/this.magRange;
		double phenDist = ((a.totalPhenols-b.totalPhenols)*(a.totalPhenols-b.totalPhenols))/this.phenRange;
		double flavDist = ((a.flavanoids-b.flavanoids)*(a.flavanoids-b.flavanoids))/this.flavRange;
		double nonFlavDist = ((a.nonFlavanoid-b.nonFlavanoid)*(a.nonFlavanoid-b.nonFlavanoid))/this.nonFlavRange;
		double proanDist = ((a.proanthocyanins-b.proanthocyanins)*(a.proanthocyanins-b.proanthocyanins))/this.proanRange;
		double intensityDist = ((a.colIntensity-b.colIntensity)*(a.colIntensity-b.colIntensity))/this.intensityRange;
		double hueDist = ((a.hue-b.hue)*(a.hue-b.hue))/this.hueRange;
		double dDist = ((a.diluted-b.diluted)*(a.diluted-b.diluted))/this.dRange;
		double prolineDist = ((a.proline-b.proline)*(a.proline-b.proline))/this.prolineRange;
		
		dist = Math.sqrt(alcDist+mAcidDist+ashDist+ashAlcDist+magDist+phenDist+flavDist
				+nonFlavDist+proanDist+intensityDist+hueDist+dDist+prolineDist);
		
		return dist;
	}
	
	private int determineK() {
	    String sizeS = Integer.toString(testList.size());
	    double sizeD = Double.parseDouble(sizeS);
	    double rawK = Math.sqrt(sizeD)/2;
	    int k = Math.round((float)rawK );
	    if(k%2 != 0) {	//if odd
	        return k ;
	    }
	    else {	//if even
	        return k-1 ;
	    }
	}
	
	private int predict(Wine testEntry) {
		//getting euclidean distance between an entry from each list and adding
		//the distance to a list and a clone list
		distances = new ArrayList<WineDist>();
		distancesClone = new ArrayList<WineDist>();
		class1 = new ArrayList<Wine>();
		class2 = new ArrayList<Wine>();
		class3 = new ArrayList<Wine>();
		for(int i = 0; i<trainingList.size(); i++) {
				double dist = euclideanDist(testEntry, trainingList.get(i));
				distances.add(new WineDist(trainingList.get(i),dist));
				distancesClone.add(new WineDist(trainingList.get(i), dist));
		}
		//order one list by shortest distance
		Collections.sort(distances, new Comparator<WineDist>() {
				@Override
				public int compare(WineDist o1, WineDist o2) {
					// TODO Auto-generated method stub
					return Double.compare(o1.dist, o2.dist);
				}
			});
		
		//add those distances depending on k to a new list
		k = 1;
		int result;
		List<WineDist>shortestDist= new ArrayList<WineDist>();
		for(int i = 0; i<k; i++) {
			shortestDist.add(distances.get(i));
		}
		for (WineDist wine : shortestDist) {
			if(wine.entry.type == 1) {
				class1.add(wine.entry);
			}
			else if(wine.entry.type == 2) {
				class2.add(wine.entry);
			}
			else if(wine.entry.type == 3) {
				class3.add(wine.entry);
			}
		}
		
		if(class1.size() > class2.size()) {
			if(class1.size() > class3.size()) {
				result = 1;
			}
			else {
				result = 3;
			}
		}
		else {
			if(class2.size() > class3.size()) {
			result = 2;
			}
			else {
				result = 3;
			}
		}
		System.out.println("Actual class: " + testEntry.type + "     Predicted class: " + result);
		if(testEntry.type == result) {
			count++;
		}
		return result;
	}
	
	public static void main(String[] args) {
		if (args.length == 2) {
			String trainingf = args[0];
			String testf = args[1];
				new KNN(trainingf, testf);
				System.out.println("K = "+k);
				System.out.println("Total correct = "+count+" / "+testList.size());
				double p = ((double)count/(double)testList.size())*100;
				System.out.println("Percentage correct = "+p);
			
		}
	}
	
}
