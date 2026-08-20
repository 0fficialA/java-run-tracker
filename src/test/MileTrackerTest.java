package test;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import dao.UserSQL;
import model.User;
import ui.Login;
import ui.MileTracker;

public class MileTrackerTest {
	
	@Test
	void test() {
		UserSQL repo = new UserSQL();
    
    HashMap<String, Object> userInfo = repo.retrieveUserById(1);
    User user = new User( (int) userInfo.get("id"), (String) userInfo.get("name"), (double) userInfo.get("miles"));
	
	MileTracker tracker = new MileTracker(user, repo);
	}
	
	
}
