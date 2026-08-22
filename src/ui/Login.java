package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.UserSQL;
import model.User;

public class Login extends JFrame implements ActionListener{
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
		userData = new UserSQL();
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
		
		submitButton.addActionListener(this);

		// 3. Add them to the window
		this.add(userLabel);
		this.add(userField);
		this.add(passLabel);
		this.add(passField);
		this.add(submitButton);
		// 4. Make it visible
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			System.out.println("You put: " + passField.getText() + " but it is: " + userData.retrievePasswordByUsername(userField.getText()));
			
			String inputUser = userField.getText();
	        String inputPass = passField.getText();

	        // Single DB call
	        String dbPassword = userData.retrievePasswordByUsername(inputUser);

	        if (dbPassword == null) {
	            System.out.println("Does not exist");
	        } else if (dbPassword.equals(inputPass)) {
	            System.out.println("Success");
	        } else {
	            System.out.println("Fail");
	        }

	        userField.setText("");
	        passField.setText("");
		}

	}
}
