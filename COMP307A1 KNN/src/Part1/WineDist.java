package Part1;

public class WineDist {
	public Wine entry;
	public double dist;
	
	
	public WineDist(Wine entry, double dist) {
		this.entry = entry;
		this.dist = dist;
	}


	@Override
	public String toString() {
		return "WineDist [entry=" + entry + ", dist=" + dist + "]";
	}

}
