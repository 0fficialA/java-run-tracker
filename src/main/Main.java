package main;

import controller.UserController;
import ui.Login;

//Application Entry
public class Main{
	public static void main(String[] args) {
		new Login(new UserController());
	}
}
