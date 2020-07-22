import java.util.ArrayList;
import java.util.Random;

public class Centroid {
	private double[] values;
	private int size;
	private int ID;
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public Centroid(int ID, double[] minValues, double[] maxValues) {
		this.ID = ID;
		this.size = minValues.length;
		values = new double[size];
		Random r = new Random();
		for (int i = 0; i < size; i++)
			values[i] = minValues[i] + ((maxValues[i] - minValues[i]) * r.nextDouble());
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public void removePoint(Point point) {
		points.remove(point);
	}
	
	public void updateValues() {
		double[] tempValues = new double[size];
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < size; j++)
				tempValues[j] += points.get(i).getValue(j);
		}
		for (int i = 0; i < size; i++) {
			tempValues[i] /= points.size();
			values[i] = tempValues[i];
		}
	}
	
	public double distanceFromPoint(Point point) {
		double distance = 0;
		for (int i = 0; i < size; i++) {
			distance += Math.pow(values[i] - point.getValue(i), 2);
		}
		return Math.sqrt(distance);
	}
	
	public int getID() {
		return ID;
	}
	
	public int getSize() {
		return size;
	}
	
	public double getValue(int loc) {
		return values[loc];
	}
	
	public String toString() {
		String value = "";
		for (int i = 0; i < size - 1; i++)
			value += values[i] + ", ";
		value += values[size - 1] + "\n";
		for (int i = 0; i < points.size(); i++)
			value += points.get(i).toString() + "\n";
		return value;
	}
}