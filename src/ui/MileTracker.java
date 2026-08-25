package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dao.*;
import main.*;
import model.*;

public class MileTracker extends JFrame implements ActionListener{
	UserSQL userData;
	User user;
	JButton resetButton;

	JLabel label;
	JTextField inputField;
	JButton button;

	public MileTracker(User user) {
		super("Ugly Run Tracker");
		System.out.println(user);
		
		this.user = user;
		this.userData = new UserSQL();
		// 1. Create the window container
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLayout(new FlowLayout());

		// 2. Create the three simple components
		label = new JLabel("Current Miles: " + user.getMiles());
		inputField = new JTextField(10);
		resetButton = new JButton("Reset Total");
		button = new JButton("Add Miles");

		resetButton.addActionListener(this);
		button.addActionListener(this);

		// 3. Add them to the window
		this.add(label);
		this.add(inputField);
		this.add(resetButton);
		this.add(button);
		// 4. Make it visible
		this.setVisible(true);
	}

	public static void main(String[] args) {
		UserSQL repo = new UserSQL();
	    
	    HashMap<String, Object> userInfo = repo.retrieveUserById(1);
	    User user = new User( (int) userInfo.get("id"), (String) userInfo.get("name"), (double) userInfo.get("miles"));
		
		MileTracker tracker = new MileTracker(user);
		
		System.out.println("User Data: " + repo.retrieveUserById(user.getId()).toString());
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resetButton) {
			user.resetMiles();
			userData.saveUser(user);
			label.setText("Current Miles: " + user.getMiles());
			System.out.println("Successfully Reset Miles!");
		}

		else if (e.getSource() == button) {
			String text = inputField.getText().trim();

			// Catch blank entries before parsing
			if (text.isEmpty()) {
				System.err.println("Error: Input box cannot be left blank.");
				return;
			}
			try {
				// Pull the text straight from the inputField variable directly
				double inputMiles = Double.parseDouble(text);
				if (inputMiles >= 0) {
					// Update your data models
					user.addMiles(inputMiles);
					userData.saveUser(user);

					// Update the visual text on the screen instantly
					label.setText("Current Miles: " + user.getMiles());

					// Clear the text box for the next entry
					inputField.setText("");
					System.out.println("Successfully added and saved miles!");
				}
				else {
					System.err.println("Cannot have negative miles");
				}

			} catch (NumberFormatException ex) {
				// Prevent your application from crashing if you type letters instead of numbers
				System.out.println("Error: Please enter a valid decimal number.");
				inputField.setText("");
			}
		}

	}
}

