import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.IOException; // Import IOException to handle errors
import java.util.Scanner;             // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class

public class UserRepository {
	private File userData;
	private Scanner reader;
	//initiates Repository
	public UserRepository() {
		try {
			userData = new File("user.txt");
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
		try (FileWriter writer = new FileWriter(userData, true)) {
			reader = new Scanner(userData);
			writer.write(String.valueOf(miles) + "\n");
			writer.close();
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
			miles = Double.parseDouble(reader.next().trim());
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
}
