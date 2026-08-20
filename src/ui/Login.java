package ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.UserSQL;
import model.User;

public class Login extends JFrame{
	UserSQL userData;
	User user;
	JButton resetButton;

	JLabel userLabel;
	JTextField userField;
	JLabel passLabel;
	JTextField passField;
	JButton submitButton;

	public Login() {
		super("Login");
		
		// 1. Create the window container
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLayout(new FlowLayout());

		// 2. Create the three simple components
		userLabel = new JLabel("Username:");
		userField = new JTextField(10);
		
		passLabel = new JLabel("Password:");
		passField = new JTextField(10);
		
		submitButton = new JButton("Submit");

		// 3. Add them to the window
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(submitButton);
		// 4. Make it visible
		this.setVisible(true);
	}
}
