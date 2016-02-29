import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String filePath = "data/vector";
		File fVectors = new File(filePath);
		Scanner sc = new Scanner(fVectors);
		
		ArrayList<Point> vectors = new ArrayList<Point>();
		int vecSize = 0;
		int vocabSize = 0;
		
		int lineN = 0;
		while (sc.hasNext()) {
			String s = (String) sc.nextLine();
			lineN ++;
			if (lineN == 1) {
				vocabSize = Integer.parseInt(s.split(" ")[0]);
				vecSize = Integer.parseInt(s.split(" ")[1]);
			}else {
				Point p = new Point(s, vecSize, " ");
				vectors.add(p);
			}
		}
		sc.close();
		
		//use mean distance to estimate dc
		/*double all = 0;
		for (int i = 0; i < vectors.size(); i++) {
			for (int j = 0; j < vectors.size(); j++) {
				double dist = vectors.get(i).distance(vectors.get(j));
				all += dist;
			}
		}
		System.out.println("mean distances:"+all/(vectors.size()*vectors.size()));*/
		
		//calculate density of each point
		double dc = 20;
		double maxdist = 0;
		int[] localDen = new int[vocabSize];
		double[][] distMatrix = new double[vocabSize][vocabSize];
		for (int i = 0; i < vocabSize; i++) {
			for (int j = 0; j < vocabSize; j++) {
				if (i != j) {
					double dist = vectors.get(i).distance(vectors.get(j));
					distMatrix[i][j] = dist;
					if (dist > maxdist) {
						maxdist = dist;
					}
					if (dist <= dc) {
						localDen[i] = localDen[i] + 1;
					}
				}
				
			}
		}
		
		/*for (int i = 0; i < density.length; i++) {
			System.out.println(density[i]);
		}*/
		//System.out.println("max distance:" +  maxdist);
		
		double[] minDistan = new double[vocabSize];
		for (int i = 0; i < vocabSize; i++) {
			minDistan[i] = maxdist;
			for (int j = 0; j < vocabSize; j++) {
				if (i != j && localDen[j] > localDen[i] && distMatrix[i][j] < minDistan[i]) {
					minDistan[i] = distMatrix[i][j];
				}
				
			}
		}
		
		double[] gama = new double[vocabSize];
		boolean[] center = new boolean[vocabSize];
		List<Point> cenPoints = new ArrayList<Point>();
		int clusterLab = 0;
		for (int i = 0; i < vocabSize; i++) {
			gama[i] = localDen[i] * minDistan[i];
			if (gama[i] > 900) {
				center[i] = true;
				Point c = new Point(vectors.get(i));
				c.setClusterLabel(clusterLab);
				clusterLab +=1;
				cenPoints.add(c);
			}else {
				center[i] = false;
			}
		}
		
		for (int i = 0; i < vocabSize; i++) {
			if (localDen[i] < 10 && minDistan[i] > 17) {
				continue;
			}
			double d = Double.MAX_VALUE;
			for (int j = 0; j < cenPoints.size(); j++) {
				if (i == j) {
					continue;
				}
				double dd = vectors.get(i).distance(cenPoints.get(j));
				if (dd < d) {
					d = dd;
					vectors.get(i).setClusterLabel(cenPoints.get(j).getClusterLabel());
				}
			}
		}
		
		Map<String, String> cluster = new HashMap<String, String>();
		for (int i = 0; i < vocabSize; i++) {
			if (vectors.get(i).getClusterLabel() == -1) {
				continue;
			}
			String ids = cluster.get(vectors.get(i).getClusterLabel()+"");
			if (ids != null) {
				cluster.put(vectors.get(i).getClusterLabel()+"", ids+","+vectors.get(i).getId());
			} else {
				cluster.put(vectors.get(i).getClusterLabel()+"", vectors.get(i).getId());
			}
		}
		
		for(String label : cluster.keySet()) {
			
			String temp = cluster.get(label);
			if (temp.contains(",")) {
				System.out.println(temp);
			}
			
			
		}
		
		/*for (int i = 0; i < vocabSize; i++) {
			System.out.println(vectors.get(i).getId()+","+vectors.get(i).getClusterLabel());
		}
		for (int i = 0; i < cenPoints.size(); i++) {
			System.out.print(cenPoints.get(i).getClusterLabel() + " ");
		}
		System.out.println("");

		for (int i = 0; i < vocabSize; i++) {
			System.out.print(localDen[i] + ",");
		}
		System.out.println("");
		for (int i = 0; i < vocabSize; i++) {
			System.out.print(minDistan[i] + ",");
		}
		System.out.println("");
		
		Arrays.sort(gama);
		for (int i = 0; i < vocabSize; i++) {
			System.out.print(gama[i] + ",");
		}*/
	}

}
