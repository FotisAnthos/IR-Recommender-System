package main;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class IOHandle {

	private String input_path; 
	private String output_path; 

	public IOHandle(String input_path, String output_path) {
		this.input_path = input_path;
		this.output_path = output_path;
	}

	// This method reads a file containing a free cell puzzle and stores the numbers
	// Input:
	//			nothing
	// Output:
	//			true --> Successful read.
	//			false --> Unsuccessful read
	public ArrayList<Integer> retrieve() {//read problem from input file
		ArrayList<Integer> data = new ArrayList<>();
		try
		{
			FileReader fileReader = new FileReader(input_path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine().trim().replaceAll(" ", "");
			@SuppressWarnings("unused")
			int T = Character.getNumericValue(line.charAt(0));//T is number of iterations
			data.add(T);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			@SuppressWarnings("unused")
			int N = Character.getNumericValue(line.charAt(0));//N is number of users
			data.add(N);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			@SuppressWarnings("unused")
			int M = Character.getNumericValue(line.charAt(0));//M is number of items
			data.add(M);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			@SuppressWarnings("unused")
			int X = Character.getNumericValue(line.charAt(0));//X is percentage of how full the matrix is
			data.add(X);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			@SuppressWarnings("unused")
			int K = Character.getNumericValue(line.charAt(0));//K is number closest neighbors
			data.add(K);


			bufferedReader.close();
			fileReader.close();
		}catch(IOException i){
			System.out.println("Failed to read file");
			i.printStackTrace();
			return null;
		}
		return data;
	}



}
