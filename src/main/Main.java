package main;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		String input_path;

		boolean debug = true;

		if(!debug) {
			input_path = args[0];

			IOHandle io  = new IOHandle(input_path, "");
			ArrayList<Integer> conf = io.retrieve();
			int T = conf.get(0);
			int N = conf.get(1);
			int M = conf.get(2);
			int X = conf.get(3);
			int K = conf.get(4);
			
			Core theCore = new Core(T, N, M, X, K);
		}
		else {
			int T = 10;
			int N = 50;
			int M = 50;
			int X = 75;
			int K = 0;

			Core theCore = new Core(T, N, M, X, K);
		}


		return;
	}

	private static void syntax_message() {
		System.out.println("Use syntax:\n\n");
		System.out.println("\trecco <input_file> \n\n");
		System.out.println("where:\n ");
		System.out.println("\t<input_file> = String indicating the input file.\n");

		System.exit(2);
	}

}
