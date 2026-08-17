package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class UserSQL {
	String url = "jdbc:sqlite:C:\\Users\\aopar\\eclipse-workspace\\my_database.db";

	public UserSQL() {
		// Try-with-resources ensures connections and statements close automatically
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				System.out.println("Connected to SQLite database successfully!");

				// Create a statement
				try (Statement stmt = conn.createStatement()) {

					// Create a table if it does not exist
					String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
							"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"name TEXT NOT NULL, miles DOUBLE NULL);";
					stmt.execute(createTableSQL);
					System.out.println("Table 'users' ready.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}

	public void createUser(User user) {
	    // If you want to use the 'num' parameter instead of the user object's value:
	    String insertSQL = "INSERT INTO users (name, miles) VALUES (?, ?);";
	    
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	        
	        if (conn != null) {
	            System.out.println("Connected to SQLite database successfully!");

	            // Set the variables using the PreparedStatement directly
	            pstmt.setString(1, user.getName()); 
	            pstmt.setDouble(2, user.getMiles()); // Using the method parameter 'num'

	            pstmt.executeUpdate();
	            System.out.println("Miles inserted successfully." + user.getMiles());
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Database error: " + e.getMessage());
	    }
	}
	
	public void retrieveUsers() {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				System.out.println("Connected to SQLite database successfully!");

				// Create a statement
				try (Statement stmt = conn.createStatement()) {
					// 3. Query data
					String selectSQL = "SELECT id, name FROM users;";
					try (ResultSet rs = stmt.executeQuery(selectSQL)) {
						System.out.println("\n--- Users List ---");
						while (rs.next()) {
							int id = rs.getInt("id");
							String name = rs.getString("name");
							System.out.println("ID: " + id + ", Name: " + name);
						}
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}
	
	public HashMap<String, Object> retrieveUserById(int userId) {
	    // If you want to use the 'num' parameter instead of the user object's value:
	    String selectSQL = "SELECT id, name, miles FROM users WHERE id = ?;";
	    HashMap<String, Object> userInfo = new HashMap<String, Object>();
	    
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
	        
	        if (conn != null) {
	            System.out.println("Connected to SQLite database successfully!");

	            // Set the variables using the PreparedStatement directly
	            pstmt.setInt(1, userId); 

	            try (ResultSet rs = pstmt.executeQuery()) {
					System.out.println("\n--- User Details ---");
					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						double miles = rs.getDouble("miles");
						
						userInfo.put("id", id);
						userInfo.put("name", name);
						userInfo.put("miles", miles);
						
						System.out.println("ID: " + id + ", Name: " + name + ", Miles: " + miles);
					}
				}
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Database error: " + e.getMessage());
	    }
		return userInfo;
	}
	
	public void updateUser(User user, Double num) {
	    // If you want to use the 'num' parameter instead of the user object's value:
	    String insertSQL = "UPDATE users SET miles= ? WHERE name = ?;";
	    
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	        
	        if (conn != null) {
	            System.out.println("Connected to SQLite database successfully!");

	            // Set the variables using the PreparedStatement directly
	            pstmt.setDouble(1, num); 
	            pstmt.setString(2, user.getName()); // Using the method parameter 'num'

	            pstmt.executeUpdate();
	            System.out.println("Miles updated successfully." + user.getMiles());
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Database error: " + e.getMessage());
	    }
	}
	
	public void deleteUser(int userId) {
	    String insertSQL = "DELETE FROM users WHERE id = ?;";
	    
	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	        
	        if (conn != null) {
	            System.out.println("Connected to SQLite database successfully!");

	            // Set the variables using the PreparedStatement directly
	            pstmt.setInt(1, userId);
	            pstmt.executeUpdate();
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Database error: " + e.getMessage());
	    }
	}
}