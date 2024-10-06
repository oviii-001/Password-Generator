package com.ovi.passwordgenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PasswordGenerator extends JFrame {

    private JTextField lengthField;
    private JCheckBox upperCaseCheckBox, lowerCaseCheckBox, numbersCheckBox, specialCharsCheckBox;
    private JTextArea resultArea;

    public PasswordGenerator() {
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        //creating components
        JLabel lengthLabel = new JLabel("Password Length:");
        lengthField = new JTextField(5);

        upperCaseCheckBox = new JCheckBox("Include Uppercase Letters (A-Z)");
        lowerCaseCheckBox = new JCheckBox("Include Lowercase Letters (a-z)");
        numbersCheckBox = new JCheckBox("Include Numbers (0-9)");
        specialCharsCheckBox = new JCheckBox("Include Special Characters (!@#$)");

        JButton generateButton = new JButton("Generate Password");
        generateButton.addActionListener(new GenerateButtonListener());

        resultArea = new JTextArea(3, 20);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);

        //result font and color
        Font resultFont = new Font("SansSerif", Font.BOLD, 16);
        resultArea.setFont(resultFont);
        resultArea.setForeground(new Color(255, 0, 0));  //red

        // adding components to layout
        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.add(lengthLabel);
        panel.add(lengthField);
        panel.add(upperCaseCheckBox);
        panel.add(lowerCaseCheckBox);
        panel.add(numbersCheckBox);
        panel.add(specialCharsCheckBox);
        panel.add(generateButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int length;
            try {
                length = Integer.parseInt(lengthField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PasswordGenerator.this, "Please enter a valid number.");
                return;
            }

            boolean includeUpperCase = upperCaseCheckBox.isSelected();
            boolean includeLowerCase = lowerCaseCheckBox.isSelected();
            boolean includeNumbers = numbersCheckBox.isSelected();
            boolean includeSpecialChars = specialCharsCheckBox.isSelected();

            if (!includeUpperCase && !includeLowerCase && !includeNumbers && !includeSpecialChars) {
                JOptionPane.showMessageDialog(PasswordGenerator.this, "Please select at least one option.");
                return;
            }

            String generatedPassword = generatePassword(length, includeUpperCase, includeLowerCase, includeNumbers, includeSpecialChars);
            resultArea.setText(generatedPassword);
        }
    }

    private String generatePassword(int length, boolean upper, boolean lower, boolean numbers, boolean special) {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/";

        StringBuilder allChars = new StringBuilder();
        if (upper) allChars.append(upperChars);
        if (lower) allChars.append(lowerChars);
        if (numbers) allChars.append(numberChars);
        if (special) allChars.append(specialChars);

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordGenerator());
    }
}
