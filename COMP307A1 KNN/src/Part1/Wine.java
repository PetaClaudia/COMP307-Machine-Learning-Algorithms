package Part1;

public class Wine {
//	Alcohol, Malicacid, Ash, Alcalinityofash, Magnesium, Totalphenols, Flavanoids, 
//	Non-flavanoidphenols, Proanthocyanins, Colorintensity, Hue, OD280%2FOD315ofdilutedwines,
//	andProline
	
	@Override
	public String toString() {
		return "Wine [alcohol=" + alcohol + ", malicAcid=" + malicAcid + ", ash=" + ash + ", ashAlcalinity="
				+ ashAlcalinity + ", magnesium=" + magnesium + ", totalPhenols=" + totalPhenols + ", flavanoids="
				+ flavanoids + ", nonFlavanoid=" + nonFlavanoid + ", proanthocyanins=" + proanthocyanins
				+ ", colIntensity=" + colIntensity + ", hue=" + hue + ", diluted=" + diluted + ", proline=" + proline
				+ ", type=" + type + "]";
	}

	public double alcohol;
	public double malicAcid;
	public double ash;
	public double ashAlcalinity;
	public double magnesium;
	public double totalPhenols;
	public double flavanoids;
	public double nonFlavanoid;
	public double proanthocyanins;
	public double colIntensity;
	public double hue;
	public double diluted;
	public double proline;
	public int type;
	
	public Wine(double al, double mAcid, double ash, double ashAlc, double mag, 
			double phen, double flav, double nonFlav, double proan, double intensity, double hue,
			double d, double proline, int type) {
		
		this.alcohol = al;
		this.malicAcid = mAcid;
		this.ash = ash;
		this.ashAlcalinity = ashAlc;
		this.magnesium = mag;
		this.totalPhenols = phen;
		this.flavanoids = flav;
		this.nonFlavanoid = nonFlav;
		this.proanthocyanins = proan;
		this.colIntensity = intensity;
		this.hue = hue;
		this.diluted = d;
		this.proline = proline;
		this.type = type;
	}
}
