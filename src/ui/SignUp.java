package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.UserSQL;
import model.User;

public class SignUp extends JFrame implements ActionListener{
	UserSQL userData;
	User user;
	JButton resetButton;

	JLabel userLabel;
	JTextField userField;
	JLabel passLabel;
	JTextField passField;
	JLabel nameLabel;
	JTextField nameField;
	JButton submitButton;
	JLabel conPassLabel;
	JTextField conPassField;

	public SignUp() {
		super("Sign Up");
		userData = new UserSQL();
		// 1. Create the window container
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLayout(new FlowLayout());

		// 2. Create the three simple components
		nameLabel = new JLabel("Name: ");
		nameField = new JTextField(10);
		
		userLabel = new JLabel("Username:");
		userField = new JTextField(10);
		
		passLabel = new JLabel("Password:");
		passField = new JTextField(10);
		
		conPassLabel = new JLabel("Confirm Password:");
		conPassField = new JTextField(10);
		
		submitButton = new JButton("Sign Up");
		
		submitButton.addActionListener(this);

		// 3. Add them to the window
		this.add(nameLabel);
		this.add(nameField);
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(conPassLabel);
		this.add(conPassField);
		this.add(submitButton);
		// 4. Make it visible
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			if(passField.getText().equals(conPassField.getText())) {
				User user = new User(nameField.getText());
				UserSQL repo = new UserSQL();
				repo.createUserWithCredentials(user, userField.getText(), passField.getText());
				MileTracker tracker = new MileTracker(user);
			}
		}

	}
}
