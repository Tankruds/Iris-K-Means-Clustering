import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Numbers {
	
	final public static int k = 10;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		File information = new File("numbers.txt");
		BufferedReader informationReader = new BufferedReader(new FileReader(information));
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		String info;
		while ((info = informationReader.readLine()) != null) {
			String[] values = info.split(",");
			double[] variables = new double[values.length];
			for (int i = 0; i < values.length; i++) 
				variables[i] = Double.parseDouble(values[i]);
			points.add(new Point(variables));
		}
		
		int numberOfValues = points.get(0).getSize();
		int numberOfPoints = points.size();
		
		System.out.println("Values: " + numberOfValues);
		System.out.println("Points: " + numberOfPoints);
		
		double[] minValues = new double[numberOfValues];
		double[] maxValues = new double[numberOfValues];
		
		for (int i = 0; i < numberOfValues; i++) {
			double val = points.get(0).getValue(i);
			minValues[i] = val;
			maxValues[i] = val;
		}
		
		for (int i = 1; i < numberOfPoints; i++) {
			for (int j = 0; j < numberOfValues; j++) {
				double val = points.get(i).getValue(j);
				if (val < minValues[j])
					minValues[j] = val;
				if (val > maxValues[j])
					maxValues[j] = val;
			}
		}
		
		Centroid[] centroids = new Centroid[k];
		for (int i = 0; i < centroids.length; i++)
			centroids[i] = new Centroid(i, minValues, maxValues);
		
		
		String[] oldCentroidValues = new String[centroids.length];
		for (int i = 0; i < centroids.length; i++)
			oldCentroidValues[i] = centroids[i].toString();
		
		boolean same = false;
		int x = 0;
		
		do {
			for (int j = 0; j < numberOfPoints; j++) {
				int closestCentroid = 0;
				double distance = centroids[0].distanceFromPoint(points.get(j));
				for (int k = 1; k < centroids.length; k++) {
					double newDistance = centroids[k].distanceFromPoint(points.get(j));
					if (newDistance < distance) {
						distance = newDistance;
						closestCentroid = k;
					}
				}
				if (points.get(j).getCurrentCentroid() != -1) {
					centroids[points.get(j).getCurrentCentroid()].removePoint(points.get(j));
				}
				points.get(j).updateCurrentCentroid(closestCentroid);
				centroids[closestCentroid].addPoint(points.get(j));
			}
			for (int j = 0; j < centroids.length; j++)
				centroids[j].updateValues();
			
			same = true;
			for (int i = 0; i < centroids.length; i++) {
				if (!centroids[i].toString().equals(oldCentroidValues[i])) {
						same = false;
						System.out.println(x++);
				}
			}
			
			for (int i = 0; i < centroids.length; i++)
				oldCentroidValues[i] = centroids[i].toString();
			
		} while (!same);
		Thread.sleep(1000);
		
		File output = new File("output.txt");
		BufferedWriter write = new BufferedWriter(new FileWriter(output));
		for (int i = 0; i < numberOfPoints; i++)
			write.write((points.get(i).getCurrentCentroid() + 1) + System.lineSeparator());
		
		write.close();
		informationReader.close();
	}
}