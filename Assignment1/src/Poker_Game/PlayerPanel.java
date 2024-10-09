/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerPanel {
    private JPanel panel;
    private JLabel playerNameLabel;
    private JLabel playerChipLabel;
    private JPanel playerCardPanel;
    private JPanel chipsPanel;
    private Player player;


    public PlayerPanel(Player player) {
        this.player = player;
        initializePanel();
    }

    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.GREEN);

        playerNameLabel = new JLabel(player.getName());
        playerChipLabel = new JLabel("Chips: " + player.getChips());
        playerCardPanel = new JPanel();
        playerCardPanel.setBackground(Color.GREEN);
        chipsPanel = new JPanel();
        chipsPanel.setLayout(null); // Layout for manual positioning of chips
        chipsPanel.setBackground(Color.GREEN);

        panel.add(playerNameLabel);
        panel.add(playerChipLabel);
        panel.add(playerCardPanel);

        updateCards(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JPanel getChipsPanel() {
        return chipsPanel;
    }

    public void updatePlayerInfo(Player player, boolean isUserPlayer) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerChipLabel.setText("Chips: " + player.getChips());
        updateCards(isUserPlayer);
    }

    private void updateCards(boolean isUserPlayer) {
        playerCardPanel.removeAll();
        List<Card> cards = player.getHand().getCards();

        for (Card card : cards) {
            JLabel cardLabel;
            if (isUserPlayer) {
                // Show the front side of the card for the user player
                cardLabel = new JLabel(card.getFrontImageIcon());
            } else {
                // Show the back side of the card for other players
                cardLabel = new JLabel(card.getBackImageIcon());
            }
            playerCardPanel.add(cardLabel);
        }

        playerCardPanel.revalidate();
        playerCardPanel.repaint();
    }
    
    

    public void updateChipsPanel(int chipCount) {
        chipsPanel.removeAll();
        String chipImageName = "/Poker_Game/CardImages/9_chips.png"; // Example path for the chip image
        java.net.URL imgUrl = getClass().getResource(chipImageName);

        if (imgUrl != null) {
            ImageIcon chipIcon = new ImageIcon(imgUrl);
            Image chipImage = chipIcon.getImage().getScaledInstance(30, 50, Image.SCALE_SMOOTH); // Resize the chip image

            int overlapOffset = 15; // The overlap offset between chips
            int chipsPerRow = 15; // Number of chips per row before stacking a new row
            int startX = 0; // Starting X position for the first chip
            int startY = 0; // Starting Y position for the first chip

            // Add the specified number of chip images to the panel with overlap
            int chipImagesCount = chipCount / 90; // Assuming each chip image represents 90 chips
            for (int i = 0; i < chipImagesCount; i++) {
                JLabel chipLabel = new JLabel(new ImageIcon(chipImage));
                chipLabel.setBounds(startX, startY, 30, 50); // Position the chip
                chipsPanel.add(chipLabel); // Add each chip image to the panel
                startX += overlapOffset; // Adjust X position for overlap
                // Check if we need to start a new row after reaching the chipsPerRow limit
                if ((i + 1) % chipsPerRow == 0) {
                    startX = 0; // Reset X position to start a new row
                    startY += 25; // Move Y position down for the new row
                }
            }

            chipsPanel.revalidate();
            chipsPanel.repaint();
        } else {
            System.err.println("Chip image not found: " + chipImageName);
        }
    }
}
