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

public class CardDeckPanel extends JPanel {
    public CardDeckPanel() {
        setLayout(null);
        setBounds(570, 200, 70, 100);
        setBackground(Color.GREEN);

        String image1Name = "/Poker_Game/CardImages/cards_back_deck.png";
        java.net.URL imgUrl = getClass().getResource(image1Name);

        if (imgUrl != null) {
            ImageIcon deckImage = new ImageIcon(imgUrl);
            JLabel deckLabel = new JLabel(new ImageIcon(deckImage.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            deckLabel.setBounds(0, 0, 70, 100);
            add(deckLabel);
        } else {
            System.err.println("Failed to load deck image.");
        }
    }
}