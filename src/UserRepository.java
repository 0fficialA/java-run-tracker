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
		try (FileWriter writer = new FileWriter(userData)) {
			reader = new Scanner(userData);
			writer.write(String.valueOf(miles));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getMiles() {
		try {
			reader = new Scanner(userData);
			double miles = Double.parseDouble(reader.nextLine().trim());
			reader.close();
			return miles;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}
}
