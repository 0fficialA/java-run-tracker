package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import model.User;

public class Login extends JFrame implements ActionListener{
	User user;
	JButton resetButton;
	UserController controller;
	JLabel userLabel;
	JTextField userField;
	JLabel passLabel;
	JPasswordField passField;
	JButton submitButton;
	JButton toSignUp;

	public Login(UserController controller) {
		super("Login");
		
		this.controller = controller;
		
		// 1. Create the window container
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLayout(new FlowLayout());

		// 2. Create the three simple components
		userLabel = new JLabel("Username:");
		userField = new JTextField(10);

		passLabel = new JLabel("Password:");
		passField = new JPasswordField(10);

		submitButton = new JButton("Log in");
		toSignUp = new JButton("New? Sign up Now!");

		submitButton.addActionListener(this);
		toSignUp.addActionListener(this);

		// 3. Add them to the window
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(submitButton);
		this.add(toSignUp);
		// 4. Make it visible
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			User user = controller.authenticateUser(userField.getText(), passField.getText());

			if (user != null) {
				JOptionPane.showMessageDialog(this, "Welcome back, " + user.getName() + "!");
				this.dispose(); // Close Login window
				new MileTracker(user); // Launch MileTracker with authenticated user
			} else {
				JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
				passField.setText("");
			}
		}
		else if (e.getSource() == toSignUp) {
			this.dispose();
            new SignUp(controller);
		}
	}

}

