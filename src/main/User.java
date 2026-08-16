package main;

public class User {
	private int id;
	private String name;
	private double miles;
	
	//Existing user
	public User(Long id, String name, double miles) {
		this.id = id;
		this.name = name;
		this.miles = miles;
	}
	
	//New user
	public User(String name) {
		this.name = name;
		this.miles = 0;
	}
	
	public User(String name, double miles) {
		this.name = name;
		this.miles = miles;
	}
	
	public void resetMiles() {
		this.miles = 0;
	}
	
	public void addMiles(double miles) {
		this.miles += miles;
	}
	
	public int getId() { return this.id; }
	
	public String getName() { return this.name; }
	
	public double getMiles() { return this.miles; }
	
	public void setId(int id) { this.id = id; }
	
	public void setName(String name) { this.name = name; }
	
	public void setMiles(double miles) { this.miles = miles; }
}
