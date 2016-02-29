
public class Util {
	public static double[]  VecString2double(String[] vec) {
		if (vec == null) {
			System.out.println("can't trans null to double");
			return null;
		}
		double[] vecF = new double[vec.length];
		for (int i = 0; i < vec.length; i++) {
			vecF[i] = Double.parseDouble(vec[i]);
		}
		return vecF;
	}
}
