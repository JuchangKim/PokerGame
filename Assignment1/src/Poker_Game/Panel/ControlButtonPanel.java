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
import java.awt.event.ActionListener;

public class ControlButtonPanel extends JPanel {
    private JButton callButton, foldButton, raiseButton, checkButton;

    // User control button interface which are call, fold, raise, check buttons
    public ControlButtonPanel(ActionListener listener) {
        setLayout(new FlowLayout());
        setBounds(450, 450, 400, 70);
        setBackground(Color.GREEN);

        callButton = new JButton("Call");
        foldButton = new JButton("Fold");
        raiseButton = new JButton("Raise");
        checkButton = new JButton("Check");

        callButton.addActionListener(listener);
        foldButton.addActionListener(listener);
        raiseButton.addActionListener(listener);
        checkButton.addActionListener(listener);

        add(callButton);
        add(foldButton);
        add(raiseButton);
        add(checkButton);

        setVisible(false);
    }

    public void showButtons(boolean visible) {
        setVisible(visible);
    }
}
