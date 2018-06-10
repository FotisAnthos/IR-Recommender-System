package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

		ArrayList<Double> userJaccard = new ArrayList<Double>();
		ArrayList<Double> userCosine = new ArrayList<Double>();
		ArrayList<Double> userPearson = new ArrayList<Double>();
		ArrayList<Double> itemJaccard = new ArrayList<Double>();
		ArrayList<Double> itemCosine = new ArrayList<Double>();
		ArrayList<Double> itemPearson = new ArrayList<Double>();

		for(int iteration=0; iteration<iterations; iteration++) {
			int data[][] = generator.newMatrix();
			String folder = "Iteration" + iteration;
			String description = "InitialMatrix" + iteration;
			io.writeInitialMatrix(data, folder, description);

			Core theCore = new Core(usersNumber, itemsNumber, kneights, data, io);
			userJaccard.add(theCore.userCollaborativeFiltering("jaccard", iteration));
			userCosine.add(theCore.userCollaborativeFiltering("cosine", iteration));
			userPearson.add(theCore.userCollaborativeFiltering("pearson", iteration));

			itemJaccard.add(theCore.itemCollaborativeFiltering("jaccard", iteration));
			itemCosine.add(theCore.itemCollaborativeFiltering("cosine", iteration));
			itemPearson.add(theCore.itemCollaborativeFiltering("pearson", iteration));
		}

		List<String> results = new ArrayList<String>();
		List<String> resultsxml = new ArrayList<String>();
		resultsxml.add("<results>");
		
		resultsxml.add("<userFilt>");
		resultsxml.add("<jaccard>");
		for(double MAE : userJaccard) {
			resultsxml.add("<r>" + MAE + "</r>");
		}
		resultsxml.add("</jaccard>");
		resultsxml.add("<cosine>");
		for(double MAE : userCosine) {
			resultsxml.add("<r>" + MAE + "</r>");
		}
		resultsxml.add("</cosine>");
		resultsxml.add("<pearson>");
		for(double MAE : userPearson) {
			resultsxml.add("<r>" + MAE + "</r>");
		}
		resultsxml.add("</pearson>");
		resultsxml.add("</userFilt>");
		
		resultsxml.add("<itemFilt>");
		resultsxml.add("<jaccard>");
		for(double MAE : itemJaccard) {
			resultsxml.add("<r>" + MAE + "</r>");
		}
		resultsxml.add("</jaccard>");
		resultsxml.add("<cosine>");
		for(double MAE : itemCosine) {
			resultsxml.add("<r>" + MAE + "</r>");
		}
		resultsxml.add("</cosine>");
		resultsxml.add("<pearson>");
		for(double MAE : itemPearson) {
			resultsxml.add("<r>" + MAE + "</r>");
		}
		resultsxml.add("</pearson>");
		resultsxml.add("</itemFilt>");
		
		
		resultsxml.add("</results>");


		results.add("User Collaborative Filtering Mean Absolute Error:");
		results.add("User - Jaccard: " + userJaccard.toString());
		results.add("User - Cosine: " + userCosine.toString());
		results.add("User - Pearson: " + userPearson.toString());

		results.add("*** *** **** ***");

		results.add("Item Collaborative Filtering Mean Absolute Error:");
		results.add("Item - Jaccard: " + itemJaccard.toString());
		results.add("Item - Cosine: " + itemCosine.toString());
		results.add("Item - Pearson: " + itemPearson.toString());

		io.writeLines(results, "", "Results.txt");
		io.writeLines(resultsxml, "", "Results.xml");
	}


}
