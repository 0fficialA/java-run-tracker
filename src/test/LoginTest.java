package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import controller.UserController;
import ui.Login;
import ui.SignUp;

class LoginTest {

	@Disabled
	@Test
	void testLogin() {
		Login login = new Login(new UserController());
		try {
			Thread.sleep(110000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	void testSignUp() {
		SignUp signUp = new SignUp(new UserController());
		try {
			Thread.sleep(110000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
