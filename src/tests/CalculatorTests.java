package tests;

import main.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;


class CalculatorTests{
	
	@Test
	void testJaccardSimilarity() {
		Calculator calc = new Calculator();
		
		int[] A = {0,1,2,5,6};
		int[] B = {0,2,3,4,5};
		int[] C = {4,5,1};
		int[] D = {5,5,4};
		int[] E = {1,2,3};
		int[] F = {1,0,4};
		int[] G = {4,5,6};
		
		assertEquals(0.75, calc.JaccardSimilarity(A, B));
		assertEquals(1.0, calc.JaccardSimilarity(C, D));
		assertEquals(0.667, calc.JaccardSimilarity(E, F));
		assertEquals(0.667, calc.JaccardSimilarity(E, G));
		assertEquals(0.333, calc.JaccardSimilarity(F, G));
	}
	
	@Test
	void testCosineSimilarity() {
		Calculator calc = new Calculator();
		
		int[] A = {1,5,3,0,2,5,1,2,0,3,0,4,0,0,0,0,1,5,0,2};
		int[] B = {5,4,0,3,4,0,4,1,0,5,0,4,0,5,2,3,5,0,4,5};
		int[] C = {0,0,1,0,3,0,5,0,3,1,2,0,2,3,0,2,1,4,0,1};
		
		//the test values are as calculated by a certain excel document *wink wink*
		assertEquals(0.506, calc.CosineSimilarity(A, B));
		assertEquals(1.0, calc.CosineSimilarity(B, B));
		assertEquals(0.491, calc.CosineSimilarity(C, B));
	}
	
	@Test
	void testPeasonCorrelationCoeff() {
		Calculator calc = new Calculator();
		
		
		
		int[] A = {5,3,4,4,0};//from ir08.pdf #56 -Alice
		int[] B = {3,1,2,3,3};//from ir08.pdf #56 -User1

		int[] u1 = {1,5,0,3,0,0,3,5};
		int[] u2 = {5,4,0,0,3,2,1,0};
		int[] nu1 ={3,0,5,4,2,3,0,5};
		int[] nu2 ={0,5,2,2,4,0,1,3};
		
		
		//the test values are as calculated by a certain excel document *wink wink*
		assertEquals(1.0, calc.PeasonCorrelationCoeff(u1, nu1));
		assertEquals(0.189, calc.PeasonCorrelationCoeff(u2, nu1));
		assertEquals(0.845, calc.PeasonCorrelationCoeff(u1, nu2));
		assertEquals(0.996, calc.PeasonCorrelationCoeff(u2, nu2));
		
		//from ir08.pdf #56
		double result = calc.PeasonCorrelationCoeff(A, B);
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
		assertEquals(0.85, result);

	}
	
	
	@Test
	void testmeanAbsoluteError() {
		Calculator calc = new Calculator();
		
		double[] nu1 ={3,0,5,4,2,3,0,5};
		double[] nu2 ={0,5,2,2,4,0,1,3};
		
		double[] pred1 = {1.260,0,3.453,3.453,1.562,2.000,0,4.062};
		double[] pred2 = {0,4.284,1.900,1.900,2.844,0,1.939,2.314};

		//the test values are as calculated by a certain excel document
		assertEquals(1.035, calc.meanAbsoluteError(nu1, pred1));
		assertEquals(0.616, calc.meanAbsoluteError(nu2, pred2));
	}
	
	

}
