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

public class SignUp extends JFrame implements ActionListener{
	User user;
	UserController controller;
	
	JButton resetButton;
	JLabel userLabel;
	JTextField userField;
	JLabel passLabel;
	JPasswordField passField;
	JLabel nameLabel;
	JTextField nameField;
	JButton submitButton;
	JLabel conPassLabel;
	JPasswordField conPassField;
	JButton backToLoginButton;

	public SignUp(UserController controller) {
		super("Sign Up");
		
		this.controller = controller;
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
		passField = new JPasswordField(10);
		
		conPassLabel = new JLabel("Confirm Password:");
		conPassField = new JPasswordField(10);
		
		submitButton = new JButton("Sign Up");
		backToLoginButton = new JButton("Back to Login");
		
		submitButton.addActionListener(this);
		backToLoginButton.addActionListener(this);
		

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
		this.add(backToLoginButton);
		// 4. Make it visible
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
            String name = nameField.getText();
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String confirmPassword = new String(conPassField.getPassword());

            // 1. Validate matching passwords
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                passField.setText("");
                conPassField.setText("");
                return;
            }

            // 2. Delegate creation to Controller
            boolean success = controller.registerUser(name, username, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Account created successfully! Please log in.");
                this.dispose();
                new Login(controller);
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Ensure all fields are filled.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backToLoginButton) {
            this.dispose();
            new Login(controller);
        }

	}
}
