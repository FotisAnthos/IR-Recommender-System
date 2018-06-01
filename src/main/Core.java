package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Core {

	private Calculator calc; 
	private int[][] data;
	private int usersNumber;
	private int itemsNumber;
	private int kneighs;
	private IOHandle io;

	public Core(int usersNumber, int itemsNumber, int kneights, int[][] data, IOHandle io) {
		this.calc = new Calculator();
		this.data = data;
		this.usersNumber = usersNumber;
		this.itemsNumber = itemsNumber;
		this.kneighs = kneights;
		this.io = io;
	}

	public void userCollaborativeFiltering(String method, int iteration) {
		double accumulatedMEA = 0;
		double temp;
		List<String> predictionLines = new ArrayList<String>();
		double[] usersMEA = new double[itemsNumber];

		for(int user=0; user < usersNumber; user++) {
			double[] pred = new double[itemsNumber];
			
			pred = userToUserRecommendation(user, method);
			predictionLines.add(Arrays.toString(pred));
			
			temp = calc.meanAbsoluteError(data[user], pred);
			usersMEA[user] = temp;
			accumulatedMEA += temp;
		}
		
		String folder = "Iteration" + iteration + "\\users\\"+ method;
		String description = "usersMEA" +method+iteration;
		io.writeLines(predictionLines, folder, description);
		
		predictionLines = new ArrayList<String>();
		predictionLines.add(Double.toString(accumulatedMEA));
		description = "usersTableMEA" +method + iteration;
		io.writeLines(predictionLines, folder, description);

	}

	public void itemCollaborativeFiltering(String method, int iteration) {
		

	}

	public double[] userToUserRecommendation(int userID, String method) {
		PriorityQueue<ContainerClass> pq = new PriorityQueue<ContainerClass>(new pqComparator());
		double score;
		//calculation of r for user with UserID
		for(int uID=0; uID < usersNumber; uID++) {
			if(uID != userID) {
				score = calc.calculateSimilarity(data[userID], data[uID], method);
				pq.add(new ContainerClass(uID, score));
			}
		}
		if(kneighs > pq.size()) {
			System.err.println("Core.java->userToUserRecommendation>kneighs>pq.size()");
			System.exit(4);
		}
		ArrayList<ContainerClass> kclosest = new ArrayList<ContainerClass>();
		for(int i=0;i<kneighs;i++) {
			kclosest.add(pq.poll());
		}

		double[] prediction = new double[itemsNumber];//used to store the rating prediction about all the items for a specific user with userID
		for(int item=0; item< itemsNumber; item++) {//for every item calculate a possible rating
			double tempScore = 0;
			int availableScores = 0;

			for(ContainerClass neighbor : kclosest) {
				if(neighbor.getID() != userID) {
					if(data[neighbor.getID()][item] != 0) {
						tempScore += (double)data[neighbor.getID()][item] * neighbor.getScore();
						availableScores++;
					}
				}
			}
			
			if(availableScores == 0) {
				prediction[item] = 0;
			}
			else {
				double result = tempScore / (double)availableScores;
				BigDecimal bd = new BigDecimal(result);
				result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
				prediction[item] = result;
			}
		}

		return prediction;
	}

	public double[] itemToItemRecommendation(int userID, String method) {
		double[] predictions = new double[itemsNumber];
		for(int item=0; item < itemsNumber; item++) {
			predictions[item] = itemToItemRecommendationForONEitem(item, userID,method);
		}
		return predictions;
	}

	public double itemToItemRecommendationForONEitem(int itemID, int userID, String method) {
		PriorityQueue<ContainerClass> pq = new PriorityQueue<ContainerClass>(new pqComparator());
		double score;
		//calculation of r for item with UserID
		for(int itID=0; itID < itemsNumber; itID++) {//for every item
			if(itID != itemID) {//except for the one we are interested in
				int[] itemData1 = new int[usersNumber];//start empty arrays that will contain all the
				int[] itemData2 = new int[usersNumber];//-ratings from users
				for(int user=0; user<usersNumber; user++) {
					if(user != userID) {
						itemData1[user] = data[user][itemID];//store the rating of user about the item we are interested in
						itemData2[user] = data[user][itID];//store the rating of the second item to calculate its similarity with the item we are interested in 
					}//itemData[user] -> because every item can have ratings from all the users
				}
				score = calc.calculateSimilarity(itemData1, itemData2, method);
				pq.add(new ContainerClass(itID, score));
			}
		}

		if(kneighs > pq.size()) {
			System.err.println("Core.java->itemToItemRecommendationForONEitem>kneighs>pq.size()");
			System.exit(4);
		}
		ArrayList<ContainerClass> kclosest = new ArrayList<ContainerClass>();
		for(int i=0;i<kneighs;i++) {//make a list just with the neighbors
			kclosest.add(pq.poll());
		}

		double prediction;
		double tempScore = 0;
		int availableScores = 0;

		for(ContainerClass neighbor : kclosest) {
			if(neighbor.getID() != itemID) {
				tempScore += (double)data[userID][neighbor.getID()] * neighbor.getScore();
				availableScores++;
			}
		}
		double result = tempScore / (double)availableScores;
		BigDecimal bd = new BigDecimal(result);
		result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
		prediction = result;	

		return prediction;
	}

	class ContainerClass {
		int ID;
		double score;

		public ContainerClass( int ID, double score) {
			this.ID = ID;
			this.score = score;
		}

		public int getID() {
			return ID;
		}

		public double getScore() {
			return score;
		}

	}

	class pqComparator implements Comparator<ContainerClass>{
		public int compare(ContainerClass c1, ContainerClass c2) {
			if (c1.getScore() < c2.getScore())
				return 1;
			else if (c1.getScore() > c2.getScore())
				return -1;
			return 0;
		}
	}
}
