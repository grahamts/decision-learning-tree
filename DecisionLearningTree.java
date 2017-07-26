import java.util.ArrayList;
import java.util.Collections;

public class DecisionLearningTree {

	public static void main(String[] args) {
		boolean [][] data1 = {
				{  true,  true,  true,  true,  true},
				{  true,  true, false,  true,  true},
				{  true,  true,  true, false,  true},
				{  true, false,  true,  true,  true},
				{ false,  true, false, false, false},
				{ false,  true,  true, false, false},
				{ false,  true, false,  true, false},
				{ false,  true, false, false, false},
		};
		boolean [][] data2 = {
				{ false, false,  true,  true,  true,  true},
				{ false, false,  true,  true, false,  true},
				{  true, false,  true, false,  true,  true},
				{  true,  true,  true, false, false,  true},
				{ false, false,  true,  true, false, false},
				{  true, false,  true,  true,  true, false},
				{  true, false, false, false,  true, false},
				{ false, false, false, false,  true, false},
		};

		System.out.println(GetAttribute(data1));
		System.out.println(GetAttribute(data2));
	}

	public static int GetAttribute(boolean[][] data) {
		int numAttributes = data[0].length - 1;
		int numSamples = data.length;
		boolean[] attribute = new boolean[numSamples];
		boolean[] defClass = new boolean[numSamples];
		ArrayList<Double> learningTree = new ArrayList<Double>();
		double I = findI(data);

		for(int i = 0; i < numAttributes; i++) {
			for(int j = 0; j < numSamples; j++) {
				attribute[j] = data[j][i];
				defClass[j] = data[j][numAttributes];
			}
			learningTree.add(findEntropy(attribute, defClass));
		}

		for(int i = 0; i < learningTree.size(); i++) {
			double attri =  Math.abs(I) - Math.abs(learningTree.get(i));
			learningTree.set(i, attri);
		}

		return learningTree.indexOf(Collections.max(learningTree));
	}

	public static double findEntropy(boolean[] attribute, boolean[] defClass) {
		double yesPos = 0;
		double noPos = 0;
		double yesNeg = 0;
		double noNeg = 0;

		for(int i = 0; i < attribute.length; i++) {
			if(attribute[i] == true && defClass[i] == true) {
				yesPos++;
			} else if (attribute[i] == true && defClass[i] == false) {
				yesNeg++;
			} else if (attribute[i] == false && defClass[i] == true) {
				noPos++;
			} else if (attribute[i] == false && defClass[i] == false) {
				noNeg++;
			}
		}

		return (((yesPos+yesNeg)/(attribute.length))*findI(yesPos, yesNeg, (yesPos+yesNeg))
				+(((noPos+noNeg)/(attribute.length))*findI(noPos, noNeg, (noPos+noNeg))));
	}

	public static double findI(double pos, double neg, double total) {
		return -(((pos/total)*logBaseTwo((pos/total)))+((neg/total)*logBaseTwo((neg/total))));	
	}

	public static double findI(boolean[][] data) {
		int numAttributes = data[0].length;
		double pos = 0;
		double neg = 0;

		for(int i = 0; i < data.length; i++) {
			if(data[i][numAttributes-1] == true) {
				pos++;
			} else {
				neg++;
			}
		}
		return (((pos/numAttributes)*logBaseTwo((pos/numAttributes)))+((neg/numAttributes)*logBaseTwo((neg/numAttributes))));
	}

	public static double logBaseTwo(double num) {
		double x = Math.log(num)/Math.log(2);

		if(Double.isInfinite(x)){
			return Double.MIN_VALUE;
		} else {
			return x;
		}
	}
}
