package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import model.*;

public class UserRepository {
	private File userData;
	private Scanner reader;
	
	public UserRepository() {
		try {
			userData = new File(AppConstraints.UserData);
			if (userData.createNewFile()) {
				System.out.println("New file: " + userData.getName());
			} else {
				System.out.println("File already exists");
			}
		}
		catch (IOException e) {
			System.out.println("File could not be created");
		}
	}

	public void updateMiles(double miles) {
		try (FileWriter mileHistory = new FileWriter(userData, true)) {
			reader = new Scanner(userData);
			mileHistory.write(String.valueOf(miles) + "\n");
			reader.close();
			mileHistory.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getMiles() {
		double miles = 0;
		try {
			reader = new Scanner(userData);

			while (reader.hasNextLine()) {
				String line = reader.nextLine().trim();
				if (!line.isEmpty()) {
					miles = Double.parseDouble(line);
				}
			}
			reader.close();
			return miles;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return miles;
	}

	public void printHistory() {
		try (Scanner historyReader = new Scanner(userData)) {
			System.out.println("--- Your Running History Log ---");
			while (historyReader.hasNextLine()) {
				System.out.println(historyReader.nextLine());
			}
			System.out.println("--------------------------------");
		} catch (FileNotFoundException e) {
			System.err.println("No history file found yet.");
		}
	}
	
	public void printHistory(double minMile) {
		try (Scanner historyReader = new Scanner(userData)) {
			System.out.println("--- Your Running History Log ---");
			while (historyReader.hasNextLine()) {
				double text = Double.parseDouble(historyReader.nextLine());
				
				if (text >= minMile) { System.out.println(text); }
			}
			System.out.println("--------------------------------");
		} catch (FileNotFoundException e) {
			System.err.println("No history file found yet.");
		}
	}
	
	public void printDashboardSumamry() {
	    try (Scanner historyReader = new Scanner(userData)) {
	        System.out.println("--- Dashboard Summary ---");
	        
	        ArrayList<Double> nums = new ArrayList<>();
	        while (historyReader.hasNextLine()) {
	            String line = historyReader.nextLine().trim();
	            if (!line.isEmpty()) {
	                try {
	                    double text = Double.parseDouble(line);
	                    nums.add(text);
	                } catch (NumberFormatException e) {
	                    // Skip malformed lines safely instead of crashing
	                }
	            }
	        }
	        
	        if (nums.isEmpty()) {
	            System.out.println("No data recorded yet.");
	            System.out.println("--------------------------------");
	            return;
	        }

	        double total = 0.0;
	        for (Double num : nums) {
	            total += num;
	        }
	        
	        System.out.println("Total Entries: " + nums.size());
	        System.out.println("Total Miles Logged: " + total);
	        System.out.println("Average Entry: " + (total / nums.size()));
	        System.out.println("--------------------------------");
	        
	    } catch (FileNotFoundException e) {
	        System.err.println("No history file found yet.");
	    }
	}
	
}
