package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ui.Login;
import ui.SignUp;

class LoginTest {

	@Disabled
	@Test
	void testLogin() {
		Login login = new Login();
		try {
			Thread.sleep(110000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testSignUp() {
		SignUp signUp = new SignUp();
		try {
			Thread.sleep(110000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
