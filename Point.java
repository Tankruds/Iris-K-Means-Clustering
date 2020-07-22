public class Point {
	private double variables[];
	private int currentCentroid;
	private int size;
	
	public Point(double variables[]) {
		this.variables = variables;
		this.size = variables.length;
		this.currentCentroid = -1;
	}
	
	public void updateCurrentCentroid(int newCentroid) {
		this.currentCentroid = newCentroid;
	}
	
	public int getCurrentCentroid() {
		return currentCentroid;
	}
	
	public double getValue(int loc) {
		return variables[loc];
	}
	
	public int getSize() {
		return size;
	}
	
	public String toString() {
		String value = "";
		for (int i = 0; i < size - 1; i++)
			value += variables[i] + ", ";
		value += variables[size - 1];
		return value;
	}
	
	public double distanceFromCentroid(Centroid centroid) {
		return centroid.distanceFromPoint(this);
	}
}