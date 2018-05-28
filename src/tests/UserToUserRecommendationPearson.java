package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.Core;

class UserToUserRecommendationPearson {
	int[] U1 = 	{1,5,0,3,0,0,3,5};
	int[] U2 = 	{5,4,0,0,3,2,1,0};
	int[] U3 = 	{3,0,1,2,2,0,0,5};
	int[] U4 = 	{0,3,0,0,4,1,0,3};
	int[] U5 = 	{2,4,3,0,0,2,2,0};
	int[] U6 = 	{5,0,0,3,1,0,3,1};
	int[] U7 = 	{1,4,5,5,2,0,0,4};
	int[] U8 = 	{2,1,0,0,4,5,1,0};
	int[] U9 = 	{0,0,3,2,2,0,0,5};
	int[] U10 = {3,5,1,0,0,0,4,4};
	int[] U11 = {0,0,2,1,0,2,0,3};
	int[] U12 = {4,4,0,2,0,1,1,4};
	int[] U13 = {0,0,2,0,4,0,4,5};
	int[] U14 = {0,5,3,3,2,0,1,1};
	int[] U15 = {0,2,0,0,3,3,0,2};
	int[] U16 = {0,3,2,1,1,0,4,4};
	int[] U17 = {1,5,1,2,0,4,0,4};
	int[] U18 = {5,0,4,0,3,3,4,5};
	int[] U19 = {0,4,0,2,0,5,1,5};
	int[] U20 = {2,5,1,1,5,3,0,4};
	
	int[] TestU1 = {3,0,5,4,2,3,0,5};
	int[] TestU2 = {0,5,2,2,4,0,1,3};

	@Test
	void test1() {
		int data[][] = {U1,U2,U3,U4,U5,U6,U7,U8,U9,U10,U11,U12,U13,U14,U15,U16,U17,U18,U19,U20,TestU1};
		Core core = new Core( 21, 8, 3, data);
		
		double[] result = core.userToUserRecommendation(20, "pearson");
		double[] solution = {1.26, 4.041,3.453,3.453,1.562,2.000,2.500,4.062};
		
		for(int item=0; item<result.length; item++) {
			assertEquals(solution[item],result[item]);
		}		
	}
	
	@Test
	void test2() {
		int data[][] = {U1,U2,U3,U4,U5,U6,U7,U8,U9,U10,U11,U12,U13,U14,U15,U16,U17,U18,U19,U20,TestU2};
		Core core = new Core( 21, 8, 3, data);
		
		double[] result = core.userToUserRecommendation(20, "pearson");
		double[] solution = {2.912,4.138,1.900,0.917,3.787,2.222,1.459,3.668};
		
		for(int item=0; item<result.length; item++) {
			assertEquals(solution[item],result[item]);
		}		
	}

}
