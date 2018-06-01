package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import main.Core;
import main.Generator;
import main.IOHandle;

class GeneralTesting {
	IOHandle io;
	int iterations;
	int usersNumber;
	int itemsNumber;
	int percentage;
	int kneights;
	Generator generator;

	@Test
	void testInitializer() {
		io  = new IOHandle(System.getProperty("user.dir") + "\\input1.txt");

		ArrayList<Integer> conf = io.retrieve();
		this.iterations = conf.get(0);
		this.usersNumber = conf.get(1);
		this.itemsNumber = conf.get(2);
		this.percentage = conf.get(3);
		this.kneights = conf.get(4);

		assertEquals(3, this.iterations);
		assertEquals(50, this.usersNumber);
		assertEquals(50, this.itemsNumber);
		assertEquals(75, this.percentage);
		assertEquals(3, this.kneights);

		generator = new Generator(usersNumber, itemsNumber, percentage);

		for(int iteration=0; iteration<iterations; iteration++) {
			int data[][] = generator.newMatrix();
			String folder = "Iteration" + iteration;
			String description = "InitialMatrix" + iteration;
			io.writeInitialMatrix(data, folder, description);
			
			Core theCore = new Core(usersNumber, itemsNumber, kneights, data, io);
			theCore.userCollaborativeFiltering("jaccard", iteration);
			theCore.userCollaborativeFiltering("cosine", iteration);
			theCore.userCollaborativeFiltering("pearson", iteration);
			
			theCore.itemCollaborativeFiltering("jaccard", iteration);
			theCore.itemCollaborativeFiltering("cosine", iteration);
			theCore.itemCollaborativeFiltering("pearson", iteration);
		}
	}



}
