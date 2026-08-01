
public class User {
	private String name;
	private double miles;
	
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
	
	public String getName() {
		return name;
	}
	
	public double getMiles() {
		return miles;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMiles(double miles) {
		this.miles = miles;
	}
}
