package tests;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
		double sumMAE =0;
		resultsxml.add("<results>");
		
		resultsxml.add("<userFilt>");
		resultsxml.add("<jaccard>");
		sumMAE = 0;
		for(double MAE : userJaccard) {
			resultsxml.add("<r>" + MAE + "</r>");
			sumMAE += MAE;
		}
		double userJaccardMean = sumMAE / (double)iterations;
		BigDecimal bd = new BigDecimal(userJaccardMean);
		userJaccardMean = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		resultsxml.add("<mean>"+ userJaccardMean +"</mean>");
		resultsxml.add("</jaccard>");
		resultsxml.add("<cosine>");
		sumMAE = 0;
		for(double MAE : userCosine) {
			resultsxml.add("<r>" + MAE + "</r>");
			sumMAE += MAE;
		}
		double userCosineMean = sumMAE / (double)iterations;
		bd = new BigDecimal(userCosineMean);
		userCosineMean = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		resultsxml.add("<mean>"+ userCosineMean +"</mean>");
		resultsxml.add("</cosine>");
		resultsxml.add("<pearson>");
		sumMAE = 0;
		for(double MAE : userPearson) {
			resultsxml.add("<r>" + MAE + "</r>");
			sumMAE += MAE;
		}
		double userPearsonMean = sumMAE / (double)iterations;
		bd = new BigDecimal(userPearsonMean);
		userPearsonMean = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		resultsxml.add("<mean>"+ userPearsonMean +"</mean>");
		resultsxml.add("</pearson>");
		resultsxml.add("</userFilt>");
		
		resultsxml.add("<itemFilt>");
		resultsxml.add("<jaccard>");
		sumMAE = 0;
		for(double MAE : itemJaccard) {
			resultsxml.add("<r>" + MAE + "</r>");
			sumMAE += MAE;
		}
		double itemsJaccardMean = sumMAE / (double)iterations;
		bd = new BigDecimal(itemsJaccardMean);
		itemsJaccardMean = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		resultsxml.add("<mean>"+ itemsJaccardMean +"</mean>");
		resultsxml.add("</jaccard>");
		resultsxml.add("<cosine>");
		sumMAE = 0;
		for(double MAE : itemCosine) {
			resultsxml.add("<r>" + MAE + "</r>");
			sumMAE += MAE;
		}
		double itemsCosineMean = sumMAE / (double)iterations;
		bd = new BigDecimal(itemsCosineMean);
		itemsCosineMean = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		resultsxml.add("<mean>"+ itemsCosineMean +"</mean>");
		resultsxml.add("</cosine>");
		resultsxml.add("<pearson>");
		sumMAE = 0;
		for(double MAE : itemPearson) {
			resultsxml.add("<r>" + MAE + "</r>");
			sumMAE += MAE;
		}
		double itemsPearsonMean = sumMAE / (double)iterations;
		bd = new BigDecimal(itemsPearsonMean);
		itemsPearsonMean = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		resultsxml.add("<mean>"+ itemsPearsonMean +"</mean>");
		resultsxml.add("</pearson>");
		resultsxml.add("</itemFilt>");
		
		
		resultsxml.add("</results>");


		results.add("User Collaborative Filtering Mean Absolute Error:");
		results.add("User - Jaccard: " + userJaccard.toString() + " Mean: " +userJaccardMean);
		results.add("User - Cosine: " + userCosine.toString() + " Mean: " +userCosineMean);
		results.add("User - Pearson: " + userPearson.toString() + " Mean: " +userPearsonMean);

		results.add("*** *** **** ***");

		results.add("Item Collaborative Filtering Mean Absolute Error:");
		results.add("Item - Jaccard: " + itemJaccard.toString() + " Mean: " +itemsJaccardMean);
		results.add("Item - Cosine: " + itemCosine.toString() + " Mean: " +itemsCosineMean);
		results.add("Item - Pearson: " + itemPearson.toString() + " Mean: " +itemsPearsonMean);

		io.writeLines(results, "", "Results.txt");
		io.writeLines(resultsxml, "", "Results.xml");
	}


}
