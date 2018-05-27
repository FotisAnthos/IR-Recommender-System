package main;


public class Core {

	private Generator generator;
	private Calculator calc; 
	private int[][] data;
	private int iterations;
	private int users;
	private int items;
	private int percentage;
	private int kneighs;

	public Core(int iterations, int users, int items, int percentage, int kneights) {
		this.generator = new Generator(users, items, percentage);
		this.calc = new Calculator();
		this.data = generator.newMatrix();
		this.iterations = iterations;
		this.users = users;
		this.items = items;
		this.percentage = percentage;
		this.kneighs = kneights;		
	}
	
	

	







}
