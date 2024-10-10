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

public class ExitButtonPanel extends JPanel {
    private JButton exitButton;

    // Set up exit button which can exit game anytime
    public ExitButtonPanel() {
        setLayout(new FlowLayout());
        setBounds(750, 50, 100, 70);
        setBackground(Color.GREEN);

        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}