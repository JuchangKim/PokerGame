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
import javax.swing.border.Border;
import java.awt.*;

public class AnnouncementPanel {
    private JPanel panel;  
    private JLabel[] announcementLabels;

    // Set up the announcement panel and contents which are empty first
    public AnnouncementPanel(Border border) {
        panel = new JPanel(); // Create an inner JPanel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(border);
        panel.setBackground(Color.GREEN);
        panel.setBounds(10, 450, 450, 200);

        announcementLabels = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            announcementLabels[i] = new JLabel(" ");
            panel.add(announcementLabels[i]);
        }
    }

    // Method to update the announcements
    public void updateAnnouncements(String[] announcements) {
        for (int i = 0; i < announcementLabels.length && i < announcements.length; i++) {
            announcementLabels[i].setText(announcements[i]);
        }

        panel.revalidate();
        panel.repaint();
    }

    // Getter to return the encapsulated JPanel
    public JPanel getPanel() {
        return panel;
    }
}