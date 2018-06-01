package main;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		String input_path;

		
			if(args[0] == null) syntax_message();	
			input_path = args[0];
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
				String folder = "Iteration" + iteration;
				String description = "InitialMatrix" + iteration;
				io.writeInitialMatrix(data, folder, description);
				
				Core theCore = new Core(usersNumber, itemsNumber, kneights, data, io);
				theCore.userCollaborativeFiltering("jaccard", iteration);
				theCore.userCollaborativeFiltering("cosine", iteration);
				theCore.userCollaborativeFiltering("pearson", iteration);
			}

		
		

		return;
	}

	private static void syntax_message() {
		System.out.println("Use syntax:\n\n");
		System.out.println("\t<Program_Name> <input_file> \n\n");
		System.out.println("where parameters are:\n ");
		System.out.println("\t<input_file> = String indicating the input file.\n");

		System.exit(2);
	}

}
