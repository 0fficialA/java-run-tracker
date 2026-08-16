package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.User;
import main.UserSQL;

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

	@Test
	void testUpdate() {
		UserSQL repo = new UserSQL();
	    User me = new User("Aj", 15.0);
	    repo.createUser(me);
	    repo.retrieveUserById(2);
	}
}
