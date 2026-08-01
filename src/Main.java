import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main {
	static JFrame frame;
	static UserRepository userData;
	static User user;
	public static void main(String[] args) {
        userData = new UserRepository();
        user = new User("Anthony", userData.getMiles());

        // 1. Create the window container
        frame = new JFrame("Ugly Run Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // 2. Create the three simple components
        JLabel label = new JLabel("Current Miles: " + user.getMiles());
        JTextField inputField = new JTextField(10);
        JButton button = new JButton("Add Miles");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Pull the text straight from the inputField variable directly
                    double inputMiles = Double.parseDouble(inputField.getText().trim());
                    
                    // Update your data models
                    user.addMiles(inputMiles);
                    userData.updateMiles(user.getMiles());
                    
                    // Update the visual text on the screen instantly
                    label.setText("Current Miles: " + user.getMiles());
                    
                    // Clear the text box for the next entry
                    inputField.setText("");
                    System.out.println("Successfully added and saved miles!");
                    
                } catch (NumberFormatException ex) {
                    // Prevent your app from crashing if you type letters instead of numbers
                    System.out.println("Error: Please enter a valid decimal number.");
                }
            }
        });
        
        // 3. Add them to the window
        frame.add(label);
        frame.add(inputField);
        frame.add(button);
        // 4. Make it visible
        frame.setVisible(true);
        
        
        
    }
	
}
