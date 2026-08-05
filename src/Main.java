import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main implements ActionListener{
	JFrame frame;
	UserRepository userData;
	User user;
	JButton resetButton;

	JLabel label;
	JTextField inputField;
	JButton button;

	public Main() {
		userData = new UserRepository();
		user = new User("Anthony", userData.getMiles());
		// 1. Create the window container
		frame = new JFrame("Ugly Run Tracker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLayout(new FlowLayout());

		// 2. Create the three simple components
		label = new JLabel("Current Miles: " + user.getMiles());
		inputField = new JTextField(10);
		resetButton = new JButton("Reset Total");
		button = new JButton("Add Miles");

		resetButton.addActionListener(this);
		button.addActionListener(this);

		// 3. Add them to the window
		frame.add(label);
		frame.add(inputField);
		frame.add(resetButton);
		frame.add(button);
		// 4. Make it visible
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Main tracker = new Main();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resetButton) {
			user.resetMiles();
			userData.updateMiles(user.getMiles());
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
					userData.updateMiles(user.getMiles());

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
				// Prevent your app from crashing if you type letters instead of numbers
				System.out.println("Error: Please enter a valid decimal number.");
				inputField.setText("");
			}
		}

	}
}
