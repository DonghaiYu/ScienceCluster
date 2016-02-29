
public class Point {
	private String id;
	private int vectorSize;
	private double[] vector;
	private int clusterLabel;
	
	public Point(Point p){
		this(p.getId(), p.getVectorSize(), p.getVector());
		this.clusterLabel = p.getClusterLabel();
	}
	
	public Point(String id,int size,double[] vector) {
		this.id = id;
		this.vectorSize = size;
		this.vector = vector;
		this.clusterLabel = -1;
	}
	
	public Point(String id,int size,String vecString,String splitString) {
		String[] temp = vecString.split(splitString);
		if (temp.length != size) {
			System.out.println("the vector's size is not " + size);
		}
		if(id != null) {
			this.id = id;
			this.vectorSize = size;
			this.vector = Util.VecString2double(temp);
			this.clusterLabel = -1;
		}else {
			System.out.println("id is " + id +",not allowed!");
		}
	}
	
	public Point(String text,int size,String splitString) {
		if(text != null) {
			if (splitString == null) {
				System.out.println("can't split String with " + splitString);
			}else {
				String[] temp = text.split(splitString);
				if (temp.length != (size + 1)) {
					System.out.println("the vector's size is not " + size);
				}
				if (temp.length <= 1) {
					System.out.println("the input is not allowed:" + text);
				}else {
					this.id = temp[0];
					this.vectorSize = size;
					int x = text.indexOf(splitString);
					String[] vStrings = text.substring(x+1).split(splitString);
					this.vector = Util.VecString2double(vStrings);
					this.clusterLabel = -1;
				}
				
			}
			
		}else {
			System.out.println("id is " + id +",not allowed!");
		}
		
		
	}
	
	//欧氏距离
	public double distance(Point p){
		if (p.getVectorSize() != this.vectorSize) {
			System.out.println("can't calculate difference size vectors' distance!");
			return 0;
		} else {
			double sum = 0;
			for (int i = 0; i < vector.length; i++) {
				sum +=  (this.vector[i] - p.getVector()[i]) * (this.vector[i] - p.getVector()[i]);
			}
			return Math.sqrt(sum);
		}
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getVectorSize() {
		return vectorSize;
	}
	public void setVectorSize(int vectorSize) {
		this.vectorSize = vectorSize;
	}
	public double[] getVector() {
		return vector;
	}
	public void setVector(double[] vector) {
		this.vector = vector;
	}

	public int getClusterLabel() {
		return clusterLabel;
	}

	public void setClusterLabel(int clusterLabel) {
		this.clusterLabel = clusterLabel;
	}
	
	
}
