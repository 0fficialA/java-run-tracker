package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import dao.*;
import model.*;

class UserSQLTest {
	@Disabled
	@Test
	void test() {
		UserSQL repo = new UserSQL();
	    User me = new User("Anthonkiy", 10.0);
	    
	    // 1. Run the method you want to test
	    repo.createUser(me); // or your insert method
	    
	    // 2. Add an Assertion to verify it worked
	    // For example, checking that the user object isn't null or has a valid name
	    assertNotNull(me.getName(), "User name should not be null");
	    assertEquals("Anthonkiy", me.getName(), "Names should match");
	}

	@Disabled
	@Test
	void testUpdate() {
		UserSQL repo = new UserSQL();
	    User me = new User("Aj", 15.0);
	    repo.createUser(me);
	    repo.retrieveUserById(2);
	}
	
	@Disabled
	@Test
	void testLoadUser() {
		UserSQL repo = new UserSQL();
	    
	    HashMap<String, Object> userInfo = repo.retrieveUserById(1);
	    User user = new User( (int) userInfo.get("id"), (String) userInfo.get("name"), (double) userInfo.get("miles"));
	    
	    System.out.println(user);
	}
	
	@Test
	void testCreateUser() {
		UserSQL db = new UserSQL();
		User newUser = new User("Anthony", 5);

		// Automatically creates the user profile, gets generated ID, and creates credentials
		db.createUserWithCredentials(newUser, "aopara", "my123");
	}
}
