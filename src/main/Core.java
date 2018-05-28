package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Core {

	private Calculator calc; 
	private int[][] data;
	private int usersNumber;
	private int itemsNumber;
	private int kneighs;

	public Core(int usersNumber, int itemsNumber, int kneights, int[][] data) {
		this.calc = new Calculator();
		this.data = data;
		this.usersNumber = usersNumber;
		this.itemsNumber = itemsNumber;
		this.kneighs = kneights;		
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

		double[] prediction = new double[itemsNumber];
		for(int item=0; item< itemsNumber; item++) {
			double tempScore = 0;
			int availableScores = 0;

			for(ContainerClass neighbor : kclosest) {
				if(data[neighbor.getID()][item] != 0) {
					tempScore += (double)data[neighbor.getID()][item] * neighbor.getScore();
					availableScores++;
				}
			}
			double result = tempScore / (double)availableScores;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
			prediction[item] = result;	
		}
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


	public double[] itemToItemRecommendation(int itemID, String method) {
		PriorityQueue<ContainerClass> pq = new PriorityQueue<ContainerClass>(new pqComparator());
		double score;
		//calculation of r for user with UserID
		for(int itID=0; itID < usersNumber; itID++) {
			if(itID != itemID) {
				int[] itemData1 = new int[itemsNumber];
				int[] itemData2 = new int[itemsNumber];
				for(int user=0;user<usersNumber;user++) {
					itemData1[user] = data[user][itemID];
					itemData2[user] = data[user][itID];
				}
				score = calc.calculateSimilarity(itemData1, itemData2, method);
				pq.add(new ContainerClass(itID, score));
			}
		}
		if(kneighs > pq.size()) {
			System.err.println("Core.java->itemToItemRecommendation>kneighs>pq.size()");
			System.exit(4);
		}
		ArrayList<ContainerClass> kclosest = new ArrayList<ContainerClass>();
		for(int i=0;i<kneighs;i++) {
			kclosest.add(pq.poll());
		}

		double[] prediction = new double[20];
		for(int item=0; item< itemsNumber;item++) {
			double tempScore = 0;
			int availableScores = 0;

			for(ContainerClass neighbor : kclosest) {
				if(data[neighbor.getID()][item] != 0) {
					tempScore += (double)data[neighbor.getID()][item] * neighbor.getScore();
					availableScores++;
				}
			}
			double result = tempScore / (double)availableScores;
			BigDecimal bd = new BigDecimal(result);
			result = bd.setScale(3, RoundingMode.HALF_UP).doubleValue();
			prediction[item] = result;	
		}
		return prediction;
	}












}
