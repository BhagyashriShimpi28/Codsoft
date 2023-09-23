package com.number_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private JPanel gamePanel;
    private int secretNumber;
    private int totalGuesses;
    private boolean isGameWon;
    private JLabel messageLabel;
    private JTextField guessInput;
    private JButton submitButton;
    private JButton resetButton;
    private JLabel totalGuessesLabel;
    private Color originalColor;
    private Font originalFont;
    
    public NumberGuessingGame() {
        // Initialize the game
        initializeGame();

        //Set up the frame
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
       
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        setContentPane(mainSplitPane);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridBagLayout());
        mainSplitPane.setTopComponent(upperPanel);
 
        
        // Game Panel
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());
        createGamePanelComponents(gamePanel);
        mainSplitPane.setBottomComponent(gamePanel);
       
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("numbergame.png"));
         
        
        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 0;
        imageConstraints.weightx = 1.0;
        imageConstraints.weighty = 1.0;
        imageConstraints.fill = GridBagConstraints.CENTER;
        upperPanel.add(backgroundLabel, imageConstraints);
             
        mainSplitPane.setDividerLocation(40);
        mainSplitPane.setDividerSize(0);
        
        setLocationRelativeTo(null);
        setVisible(true);
       
     }

    private void createGamePanelComponents(JPanel gamePanel) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel gameLabel = new JLabel("Number Guessing Game");
        gameLabel.setFont(new Font("Matura MT Script Capitals", Font.ITALIC, 40));
        gameLabel.setForeground(new Color(255,127,80)); 
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        gamePanel.add(gameLabel, constraints);
        
        
        messageLabel = new JLabel("Guess the number between 1 and 100:");
        originalColor = messageLabel.getForeground();
        originalFont = messageLabel.getFont();
        messageLabel.setForeground(new Color(100, 149, 237));
        Font font = new Font("Arial", Font.BOLD, 20);    
        messageLabel.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        gamePanel.add(messageLabel, constraints);

        guessInput = new JTextField(20);
        guessInput.setMargin(new Insets(5, 5, 5, 5));
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 3;

        guessInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });

        gamePanel.add(guessInput, constraints);

        submitButton = new JButton("Submit Guess");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        gamePanel.add(submitButton, constraints);

        resetButton = new JButton("Reset Game");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        gamePanel.add(resetButton, constraints);

        totalGuessesLabel = new JLabel("");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        mainPanel.add(totalGuessesLabel, constraints);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        pack();
    }

    private void initializeGame() {
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;
        totalGuesses = 0;
        isGameWon = false;
    }

    private void makeGuess() {
        if (isGameWon) {
            return;
        }

        try {
            int guess = Integer.parseInt(guessInput.getText());
            if (guess < 1 || guess > 100) {
                JOptionPane.showMessageDialog(this,"Please enter a number between 1 and 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                guessInput.setText("");
                return;
            }        
            totalGuesses++;

            if (guess == secretNumber) {
            	if(totalGuesses <= 10) {
                String congratulationsMessage = "Congratulations! You guessed the correct number : " + guess +
                        " in " + totalGuesses + " guesses.";
                Font greenFont = new Font("Arial", Font.BOLD, 20); 
                messageLabel.setFont(greenFont); 
                messageLabel.setForeground(new Color(0,100,0));
                displayMessage(congratulationsMessage);
                isGameWon = true;
                submitButton.setEnabled(false); 
            	}else {
            		String tryAgainMessage = "You guessed the correct number , but it took more than 10 guesses. Try again!";
                    Font redFont = new Font("Arial", Font.BOLD, 20); 
                    messageLabel.setFont(redFont);
                    messageLabel.setForeground(new Color(154,205,50));
                    displayMessage(tryAgainMessage);
            	}
            } 
            else if (guess < secretNumber) {
                String tryHigherMessage = "Try a higher number.";
                Font redFont = new Font("Arial", Font.BOLD, 20); 
                messageLabel.setFont(redFont); 
                messageLabel.setForeground(new Color(220,20,60)); 
                displayMessage(tryHigherMessage);
            } 
            else if (guess > secretNumber) {
                String tryLowerMessage = "Try a lower number.";
                Font redFont = new Font("Arial", Font.BOLD, 14); 
                messageLabel.setFont(redFont);
                messageLabel.setForeground(new Color(220,20,60)); 
                displayMessage(tryLowerMessage);
            }

            
            guessInput.setText("");
        } catch (NumberFormatException ex) {
            displayMessage("Please enter a valid number between 1 and 100.");
        }
    }

    private void resetGame() {
        initializeGame();
        guessInput.setText("");
        displayMessage("Guess a number between 1 and 100:");
        submitButton.setEnabled(true);
    }

    private void displayMessage(String message) {
        messageLabel.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}
