package main;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Generator {

	int N, M, X;
	int min = 1;
	int max = 5;

	public Generator(int users, int items, int fullness) {
		this.N = users;
		this.M = items;
		this.X = fullness;
	}

	public int[][] newMatrix(){
		int[][] data = new int[N][M];
		Queue<Integer> auxList = new LinkedList<Integer>();

		Random rand = new Random();
		int numberOfElements = Math.round(N * M * ((float)X/100) );

		for(int i= 0; i<numberOfElements; i++) {
			int temp = rand.nextInt(5) + 1;//random number[1, 5]
			auxList.add(temp);
		}
		for(int i= numberOfElements; i< N*M; i++) {
			auxList.add(0);
		}
		Collections.shuffle((List<?>) auxList);//yes, i know


		for(int user=0; user<N; user++) {
			for(int item=0; item<M; item++) {
				data[user][item] = auxList.remove();
			}
		}
		printMatrix(data);
		return data;
	}


	public void printMatrix(int[][] data) {
		for(int user=0; user<N; user++) {
			for(int item=0; item<M; item++) {
				System.out.print(data[user][item]);
			}
			System.out.println();
		}
	}

}
