package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.UserSQL;
import model.User;

public class MileTracker extends JFrame implements ActionListener {
    private UserSQL userData;
    private User user;

    private JLabel label;
    private JTextField inputField;
    private JButton button;
    private JButton resetButton;

    public MileTracker(User user) {
        super("Mile Tracker - Dashboard");
        System.out.println(user);

        this.user = user;
        this.userData = new UserSQL();

        // 1. Configure modern window frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(380, 280);
        this.setLocationRelativeTo(null); // Center window on screen
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Clean spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 2. Header / Display Label
        label = new JLabel("Current Miles: " + user.getMiles(), JLabel.CENTER);
        label.setFont(label.getFont().deriveFont(18.0f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(label, gbc);

        // Reset gridwidth for input fields
        gbc.gridwidth = 1;

        // 3. Input Label & Text Field
        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Add Miles:"), gbc);

        inputField = new JTextField(10);
        inputField.setPreferredSize(new Dimension(160, 30));
        gbc.gridx = 1; gbc.gridy = 1;
        this.add(inputField, gbc);

        // 4. Action Buttons
        button = new JButton("Add Miles");
        button.setPreferredSize(new Dimension(130, 35));
        button.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 2;
        this.add(button, gbc);

        resetButton = new JButton("Reset Total");
        resetButton.setPreferredSize(new Dimension(130, 35));
        resetButton.addActionListener(this);
        gbc.gridx = 1; gbc.gridy = 2;
        this.add(resetButton, gbc);

        // 5. Make Visible
        this.setVisible(true);
    }

    public static void main(String[] args) {
        UserSQL repo = new UserSQL();

        HashMap<String, Object> userInfo = repo.retrieveUserById(1);
        User user = new User((int) userInfo.get("id"), (String) userInfo.get("name"), (double) userInfo.get("miles"));

        MileTracker tracker = new MileTracker(user);

        System.out.println("User Data: " + repo.retrieveUserById(user.getId()).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            user.resetMiles();
            userData.saveUser(user);
            label.setText("Current Miles: " + user.getMiles());
            JOptionPane.showMessageDialog(this, "Miles total has been reset to 0.0");
            System.out.println("Successfully Reset Miles!");
        } else if (e.getSource() == button) {
            String text = inputField.getText().trim();

            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Input box cannot be left blank.", "Input Error", JOptionPane.WARNING_MESSAGE);
                System.err.println("Error: Input box cannot be left blank.");
                return;
            }

            try {
                double inputMiles = Double.parseDouble(text);
                if (inputMiles >= 0) {
                    user.addMiles(inputMiles);
                    userData.saveUser(user);

                    label.setText("Current Miles: " + user.getMiles());
                    inputField.setText("");
                    System.out.println("Successfully added and saved miles!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot enter negative miles.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Cannot have negative miles");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid decimal number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: Please enter a valid decimal number.");
                inputField.setText("");
            }
        }
    }
}