package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ui.Login;

class LoginTest {

	@Test
	void test() {
		Login login = new Login();
		try {
			Thread.sleep(110000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
