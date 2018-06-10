package main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class IOHandle {

	private String input_path; 
	private String output_path;
	private int iterations;
	private int usersNumber;
	private int itemsNumber;

	public IOHandle(String input_path) {
		this.input_path = input_path;
		this.output_path = System.getProperty("user.dir");
		
		StringBuilder output = new StringBuilder(output_path);
		output.append("\\results");
		this.output_path = output.toString();
		new File(output.toString()).mkdirs();
	}

	public ArrayList<Integer> retrieve() {//read problem from input file
		ArrayList<Integer> data = new ArrayList<>();

		try
		{
			FileReader fileReader = new FileReader(input_path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = bufferedReader.readLine().trim().replaceAll(" ", "");	
			iterations = Integer.valueOf(line).intValue();//number of iterations
			data.add(iterations);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			usersNumber = Integer.valueOf(line).intValue();//number of users
			data.add(usersNumber);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			itemsNumber = Integer.valueOf(line).intValue();//number of items
			data.add(itemsNumber);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			int percentage = Integer.valueOf(line).intValue();//percentage of how full the matrix is
			data.add(percentage);

			line = bufferedReader.readLine().trim().replaceAll(" ", "");
			int kneights = Integer.valueOf(line).intValue();//number of closest neighbors
			data.add(kneights);

			bufferedReader.close();
			fileReader.close();
		}catch(IOException i){
			System.out.println("Failed to read file");
			i.printStackTrace();
			return null;
		}
		return data;
	}

	public boolean writeMatrix(double[][] data, String folder, String description) {
		List<String> lines = new ArrayList<String>();

		for(int user=0; user<usersNumber;user++) {
			StringBuilder sb = new StringBuilder(50);
			for(int item=0; item<itemsNumber;item++) {
				sb.append(data[user][item] + " ");
			}
			lines.add(sb.toString());
		}

		StringBuilder output = new StringBuilder(output_path);
		output.append("\\"+ folder);
		new File(output.toString()).mkdirs();
		output.append("\\"+ description +".txt");
		Path file = Paths.get(output.toString());
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			return true;
		} catch (IOException e) {
			System.err.println("IOHandle could not write to output file!!!");
			e.printStackTrace();
			return false;
		}
	}

	public boolean writeInitialMatrix(int[][] data, String folder, String description) {
		List<String> lines = new ArrayList<String>();

		for(int user=0; user<usersNumber;user++) {
			StringBuilder sb = new StringBuilder(50);
			for(int item=0; item<itemsNumber;item++) {
				sb.append(data[user][item] + " ");
			}
			lines.add(sb.toString());
		}

		StringBuilder output = new StringBuilder(output_path);
		output.append("\\"+ folder);
		new File(output.toString()).mkdirs();
		output.append("\\"+ description +".txt");
		Path file = Paths.get(output.toString());
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			return true;
		} catch (IOException e) {
			System.err.println("IOHandle could not write to output file!!!");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean writeLines(List<String> lines, String folder, String description) {
		StringBuilder output = new StringBuilder(output_path);
		output.append("\\"+ folder);
		new File(output.toString()).mkdirs();
		output.append("\\"+ description +".txt");
		Path file = Paths.get(output.toString());
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			return true;
		} catch (IOException e) {
			System.err.println("IOHandle could not write to output file!!!");
			e.printStackTrace();
			return false;
		}	
	}

	public String getOutput_path() {
		return output_path;
	}

	public int getIterations() {
		return iterations;
	}
}
