package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import model.*;

public class UserSQL {
	String url = "jdbc:sqlite:C:\\Users\\aopar\\eclipse-workspace\\my_database.db";

	public UserSQL() {
		// Try-with-resources ensures connections and statements close automatically
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				System.out.println("Connected to SQLite database successfully!");

				// Create a statement
				try (Statement stmt = conn.createStatement()) {

					// Enable foreign key support in SQLite
					stmt.execute("PRAGMA foreign_keys = ON;");

					// 1. Create users table
					String createUsersSQL = "CREATE TABLE IF NOT EXISTS users (" +
							"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							"name TEXT NOT NULL, " +
							"miles DOUBLE NULL);";
					stmt.execute(createUsersSQL);

					// 2. Create user_credentials table (SQLite compatible)
					String createCredsSQL = "CREATE TABLE IF NOT EXISTS user_credentials (" +
					        "credential_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					        "user_id INTEGER NOT NULL, " +
					        "username TEXT UNIQUE NOT NULL, " +
					        "password_hash TEXT NOT NULL, " +
					        "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);";
					stmt.execute(createCredsSQL);

					System.out.println("Tables 'users' and 'user_credentials' ready.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}

	// NEW METHOD: Creates a user profile and populates login credentials automatically
	public boolean createUserWithCredentials(User user, String username, String password) {
		String insertUserSQL = "INSERT INTO users (name, miles) VALUES (?, ?);";
		String insertCredsSQL = "INSERT INTO user_credentials (user_id, username, password_hash) VALUES (?, ?, ?);";

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				// Turn off auto-commit to start a transaction
				conn.setAutoCommit(false);

				try (PreparedStatement userPstmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
					 PreparedStatement credsPstmt = conn.prepareStatement(insertCredsSQL)) {

					// 1. Insert into 'users' table
					userPstmt.setString(1, user.getName());
					userPstmt.setDouble(2, user.getMiles());
					userPstmt.executeUpdate();

					// Retrieve the auto-generated user id
					ResultSet rs = userPstmt.getGeneratedKeys();
					int newUserId = -1;
					if (rs.next()) {
						newUserId = rs.getInt(1);
					} else {
						conn.rollback();
						return false;
					}

					// 2. Insert into 'user_credentials' using the generated user_id
					credsPstmt.setInt(1, newUserId);
					credsPstmt.setString(2, username);
					credsPstmt.setString(3, password); // Note: Hash this password in production
					credsPstmt.executeUpdate();

					// Commit both inserts together
					conn.commit();
					System.out.println("User and Credentials created successfully! User ID: " + newUserId);
					return true;

				} catch (SQLException e) {
					conn.rollback(); // Rollback if either query fails
					System.out.println("Transaction failed, rolled back: " + e.getMessage());
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
		return false;
	}

	public void createUser(User user) {
		String insertSQL = "INSERT INTO users (name, miles) VALUES (?, ?);";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
			
			if (conn != null) {
				pstmt.setString(1, user.getName()); 
				pstmt.setDouble(2, user.getMiles());

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
				try (Statement stmt = conn.createStatement()) {
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
		String selectSQL = "SELECT id, name, miles FROM users WHERE id = ?;";
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
			
			if (conn != null) {
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
	
	public String retrievePasswordByUsername(String username) {
		String selectSQL = "SELECT username, password_hash FROM user_credentials WHERE username = ?;";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
			
			if (conn != null) {
				pstmt.setString(1, username); 

				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						String password = rs.getString("password_hash");
						
						return password;
					}
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public void saveUser(User user) {
		String insertSQL = "UPDATE users SET miles= ? WHERE name = ?;";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
			
			if (conn != null) {
				pstmt.setDouble(1, user.getMiles()); 
				pstmt.setString(2, user.getName());

				pstmt.executeUpdate();
				System.out.println("Miles updated successfully." + user.getMiles());
			}
			
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}
	
	public void deleteUserByID(int userId) {
		String insertSQL = "DELETE FROM users WHERE id = ?;";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
			
			if (conn != null) {
				pstmt.setInt(1, userId);
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}
	
	public void deleteUser(User user) {
		String insertSQL = "DELETE FROM users WHERE id = ?;";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
			
			if (conn != null) {
				pstmt.setInt(1, user.getId());
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}
}