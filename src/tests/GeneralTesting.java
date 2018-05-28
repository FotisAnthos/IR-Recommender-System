package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Generator;
import main.IOHandle;

class GeneralTesting {

	@Test
	void test() {
		String input_path = "C:\\Users\\fotis\\eclipse-workspace\\IR Recommender System\\input1.txt";
		IOHandle io  = new IOHandle(input_path);
		ArrayList<Integer> conf = io.retrieve();
		int iterations = conf.get(0);
		int usersNumber = conf.get(1);
		int itemsNumber = conf.get(2);
		int percentage = conf.get(3);
		int kneights = conf.get(4);
		Generator generator = new Generator(usersNumber, itemsNumber, percentage);
		
		for(int iteration=0; iteration<iterations; iteration++) {
			int data[][] = generator.newMatrix();
			String name = "Matrix" + iteration;
			String folder = "InitialMatrices\\";
			io.writeMatrix(data, folder, name);
			//Core theCore = new Core(usersNumber, itemsNumber, kneights, data);
		}
	}

}
