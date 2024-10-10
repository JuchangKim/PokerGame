/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game.Panel;

/**
 *
 * @author user
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BettingPotPanel extends JPanel {
    private int chipSize;
    private JLabel bettingPotLabel;
    private int currentChipCount;

    public BettingPotPanel() {
        chipSize = 30;
        currentChipCount = 0;  // Initial chip count
        setLayout(null);
        setBackground(Color.GREEN);

        bettingPotLabel = new JLabel("Betting Pot: 0     Current Bet: 0", SwingConstants.CENTER);
        bettingPotLabel.setBounds(0, 0, 300, 20);
        add(bettingPotLabel);
    }

   // Method to update the pot and chips on the betting pot panel
    public void updatePot(int pot, int currentBet) {
        int newChipCount = pot / 10; // Assuming each chip represents 10 units of the pot

        // If the number of chips has increased, add the new chips
        if (newChipCount > currentChipCount) {
            addChipsToBettingPot(newChipCount - currentChipCount);
            currentChipCount = newChipCount; // Update the current chip count to reflect the new total
        }

        // Update the betting pot label with the current pot and current bet values
        bettingPotLabel.setText("Betting Pot: " + pot + "    Current Bet: " + currentBet);
        revalidate();
        repaint();
    }

    // Method to add a specified number of chips to the betting pot panel
    private void addChipsToBettingPot(int numberOfNewChips) {
        String chipImageName = "/Poker_Game/CardImages/1_chip.png"; // Path for the chip image
        java.net.URL imgUrl = getClass().getResource(chipImageName);

        if (imgUrl != null) {
            ImageIcon chipIcon = new ImageIcon(imgUrl);
            Image chipImage = chipIcon.getImage().getScaledInstance(chipSize, chipSize, Image.SCALE_SMOOTH); // Resize the chip image

            // Add the specified number of chip images to the betting pot panel with random placement
            for (int i = 0; i < numberOfNewChips; i++) {
                JLabel chipLabel = new JLabel(new ImageIcon(chipImage));

                // Generate random x and y positions within the bounds of the betting pot panel
                int xPos = (int) (Math.random() * (getWidth() - chipSize));
                // Prevent overlapping with bettingLabel
                int yPos = 20 + (int) (Math.random() * (100 - 20 - chipSize));

                chipLabel.setBounds(xPos, yPos, chipSize, chipSize); // Set the position of the chip
                add(chipLabel); // Add the chip label to the betting pot panel
            }
        } else {
            System.err.println("Chip image not found: " + chipImageName);
        }

        revalidate();
        repaint();
    }
    
    // Method to clear all chips from the betting pot panel
    public void clearBettingPot() {
        // Remove all components except the betting pot label
        Component[] components = getComponents();
        for (Component component : components) {
            if (component != bettingPotLabel) {
                remove(component);
            }
        }
        
        revalidate();
        repaint();
    }
}