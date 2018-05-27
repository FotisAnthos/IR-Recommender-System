package main;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;


public class Calculator {

	public Calculator() {
		super();
	}

	public double JaccardSimilarity(int[] table1, int[] table2) {
		if(table1.length != table2.length) {
			System.err.println("Calculator.java->JaccardSimilarity->(table1.length != table2.length)");
			System.exit(5);
		}
		Set<Integer> u1 = new HashSet<Integer>();
		Set<Integer> u2 = new HashSet<Integer>();
		Set<Integer> union = new HashSet<Integer>();
		Set<Integer> intersection = new HashSet<Integer>();

		for(int i =0;i<table1.length;i++) {//user1Ratings and user2Ratings lengths must be equal(problem's definition)
			if(table1[i] > 0 && table1[i] < 6) {
				u1.add(i);
			}
			if(table2[i] > 0 && table2[i] < 6) {
				u2.add(i);
			}
		}

		union.addAll(u1);
		union.addAll(u2);
		intersection.addAll(u1);
		intersection.retainAll(u2);

		double result = (double)intersection.size() / (double)union.size();
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		return result;
	}

	public double CosineSimilarity(int[] table1, int[] table2) {
		if(table1.length != table2.length) {
			System.err.println("Calculator.java->CosineSimilarity->(table1.length != table2.length)");
			System.exit(5);
		}
		double sumAB = 0;
		double sumA2 = 0;
		double sumB2 = 0;

		for(int i=0; i < table1.length; i++) {
			sumAB += table1[i] * table2[i];
			sumA2 += Math.pow(table1[i], 2);
			sumB2 += Math.pow(table2[i], 2);
		}

		double result = (double)sumAB / (double)((double)Math.sqrt(sumA2) * (double)Math.sqrt(sumB2));
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		return result;
	}


	public double PeasonCorrelationCoeff(int[] table1, int[] table2) {
		if(table1.length != table2.length) {
			System.err.println("Calculator.java->PeasonCorrelationCoeff->(table1.length != table2.length)");
			System.exit(5);
		}

		double mean1, mean2, sum1, sum2, sum3;
		int sum=0;
		int count=0;
		for(int i=0; i < table1.length; i++) {
			if(table1[i]!=0 && table2[i]!=0) {
				sum+= table1[i];
				count++;
			}
		}
		mean1 = (double)sum / (double)count;
		
		sum=count=0;
		for(int i=0; i < table2.length; i++) {
			if(table1[i]!=0 && table2[i]!=0) {
				sum+= table2[i];
				count++;
			}
		}
		mean2 = (double)sum / (double)count;
		
		sum1=sum2=sum3=0;
		for(int i=0; i < table1.length; i++) {
			if(table1[i]!=0 && table2[i]!=0) {
				sum1 += ((double) table1[i] - mean1) * ((double) table2[i] - mean2);
				sum2 += Math.pow(((double) table1[i] - mean1), 2);
				sum3 += Math.pow(((double) table2[i] - mean2), 2);
			}
		}

		double result = (double)sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		return result;
	}
	
	public double meanAbsoluteError(double[] realValues, double[] predicted) {
		if(realValues.length != predicted.length) {
			System.err.println("Calculator.java->meanAbsoluteError->(realValues.length != predicted.length)");
			System.exit(5);
		}
		int count = 0;
		double sum=0;
		
		for(int i=0; i<realValues.length; i++) { 
			if(realValues[i] !=0 && predicted[i] !=0) {
				sum += Math.abs(realValues[i] - predicted[i]);
				count++;
			}
		}
		
		double result = (double)sum / (double)count;
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		return result;
	}
	

}
