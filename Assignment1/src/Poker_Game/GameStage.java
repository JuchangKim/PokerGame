/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Poker_Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author billi
 */
public class GameStage extends javax.swing.JFrame {

    private AddPlayerNames addPlayers = new AddPlayerNames();
    
    ArrayList<String> playerIds;
    String[] pids;
    String[] cards = { "ace_of_clubs.png", "2_of_clubs.png","3_of_clubs.png","10_of_clubs.png","4_of_clubs.png","5_of_clubs.png","6_of_clubs.png",
        "7_of_clubs.png","8_of_clubs.png","9_of_clubs.png", "10_of_clubs.png","jack_of_clubs.png", "queen_of_clubs.png", "king_of_clubs.png",
        "ace_of_diamonds.png", "2_of_diamonds.png","3_of_diamonds.png","4_of_diamonds.png","5_of_diamonds.png","6_of_diamonds.png",
        "7_of_diamonds.png", "8_of_diamonds.png", "9_of_diamonds.png","10_of_diamonds.png","jack_of_diamonds.png","queen_of_diamonds.png",
        "king_of_diamonds.png", "ace_of_hearts.png", "2_of_hearts.png","3_of_hearts.png","4_of_hearts.png","5_of_hearts.png","6_of_hearts.png",
        "7_of_hearts.png","8_of_hearts.png","9_of_hearts.png","10_of_hearts.png","jack_of_hearts.png","queen_of_hearts.png","king_of_hearts.png",
        "ace_of_spades.png", "2_of_spades.png", "3_of_spades.png", "4_of_spades.png", "5_of_spades.png", "6_of_spades.png", "7_of_spades.png", 
        "8_of_spades.png", "9_of_spades.png", "queen_of_spades.png", "king_of_spades.png"
        
    };
    private JLabel[] chipLabels;
    private String currentChipImage = "9_chip (1).png";
            
    
    public GameStage(ArrayList<String> playerIds) {
        initComponents();
        this.playerIds = playerIds;
        // Set the first player's name to DisplayNameTextArea2
        if (!playerIds.isEmpty()) {
            DisplayNameLabel1.setText(playerIds.get(0));  // Set player's name
        }
        
        displayRandomCards();  // Call the method to show 2 random cards
        initializeChipLabels();
        updateAllChipImages();
    }
    
    private void initializeChipLabels() {
        chipLabels = new JLabel[]{
            ChipsDispImageLabel1,
            DisplayChipsLabel1,
            ChipsImageLabel1,
            PlayerChipsLabel2
        };
    }
    
    private void updateAllChipImages() {
        for (JLabel label : chipLabels) {
            updateChipImage(label, currentChipImage);
        }
    }
    
    private void updateChipImage(JLabel label, String imageName) {
        try {
            //BufferedImage img = loadImage("/CardsImages/" + imageName);
            BufferedImage img = loadImage(imageName);
            if (img != null) {
                label.setIcon(new ImageIcon(img));
            } else {
                throw new IOException("Image could not be read: " + imageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading chip image: " + e.getMessage(), "Image Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
       
    
    private void displayRandomCards() {
        Random rand = new Random();
        int firstCardIndex = rand.nextInt(cards.length);
        int secondCardIndex;
        
        do {
            secondCardIndex = rand.nextInt(cards.length);
        } while (secondCardIndex == firstCardIndex);
        
        try {
            BufferedImage img1 = loadImage(cards[firstCardIndex]);
            BufferedImage img2 = loadImage(cards[secondCardIndex]);
            
            if (img1 == null || img2 == null) {
                throw new IOException("One or both images could not be read");
            }
            
            int width = img1.getWidth() + img2.getWidth();
            int height = Math.max(img1.getHeight(), img2.getHeight());
            
            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();
            g.drawImage(img1, 0, 0, null);
            g.drawImage(img2, img1.getWidth(), 0, null);
            g.dispose();
            
            ImageIcon combinedIcon = new ImageIcon(combined);
            MyCardsLabel1.setIcon(combinedIcon);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading card images: " + e.getMessage(), "Image Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
              
     private BufferedImage loadImage(String filename) throws IOException {
       String resourcePath = "/CardImages/" + filename;
        
        java.net.URL imageURL = getClass().getResource(resourcePath);
        if (imageURL == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return ImageIO.read(imageURL);
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PlayerCardsLabel1 = new javax.swing.JLabel();
        PlayerCards2Label1 = new javax.swing.JLabel();
        MyCardsLabel1 = new javax.swing.JLabel();
        compPlayerCards3Label1 = new javax.swing.JLabel();
        compPlayer1Label1 = new javax.swing.JLabel();
        compPlayer2Label1 = new javax.swing.JLabel();
        compPlayer3Label1 = new javax.swing.JLabel();
        DisplayNameLabel1 = new javax.swing.JLabel();
        CallButton1 = new javax.swing.JButton();
        FoldButton1 = new javax.swing.JButton();
        RaiseButton1 = new javax.swing.JButton();
        CheckButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        ChipsDispImageLabel1 = new javax.swing.JLabel();
        ChipsImageLabel1 = new javax.swing.JLabel();
        DisplayChipsLabel1 = new javax.swing.JLabel();
        PlayerChipsLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));

        PlayerCardsLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poker_Game/sbs_-_2d_poker_pack/Top-Down/Cards/Back - Top Down 88x124.png"))); // NOI18N
        PlayerCardsLabel1.setText("jLabel1");

        PlayerCards2Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poker_Game/sbs_-_2d_poker_pack/Top-Down/Cards/Back - Top Down 88x124.png"))); // NOI18N
        PlayerCards2Label1.setText("jLabel1");

        MyCardsLabel1.setText("jLabel1");

        compPlayerCards3Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Poker_Game/sbs_-_2d_poker_pack/Top-Down/Cards/Back - Top Down 88x124.png"))); // NOI18N
        compPlayerCards3Label1.setText("jLabel1");

        compPlayer1Label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        compPlayer1Label1.setText("Computer Player 1");

        compPlayer2Label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        compPlayer2Label1.setText("Computer Player 2");

        compPlayer3Label1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        compPlayer3Label1.setText("Computer Player 3");

        DisplayNameLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        DisplayNameLabel1.setText("player name");

        CallButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        CallButton1.setText("Call");
        CallButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CallButton1ActionPerformed(evt);
            }
        });

        FoldButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        FoldButton1.setText("Fold");
        FoldButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FoldButton1ActionPerformed(evt);
            }
        });

        RaiseButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        RaiseButton1.setText("Raise");
        RaiseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RaiseButton1ActionPerformed(evt);
            }
        });

        CheckButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        CheckButton1.setText("Check");
        CheckButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        ChipsDispImageLabel1.setText("Display Chips");

        ChipsImageLabel1.setText("DisplayChips");

        DisplayChipsLabel1.setText("DisplayChips");

        PlayerChipsLabel2.setText("PlayersChips");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(compPlayer3Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compPlayerCards3Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(DisplayChipsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PlayerCards2Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(compPlayer2Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(CallButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(FoldButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(60, 60, 60)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(489, 489, 489)
                                    .addComponent(ChipsImageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(20, 20, 20)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(MyCardsLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PlayerCardsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(compPlayer1Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(137, 137, 137)
                            .addComponent(ChipsDispImageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DisplayNameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(RaiseButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(CheckButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PlayerChipsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(compPlayer1Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PlayerCardsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(ChipsImageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(compPlayer2Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(compPlayer3Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(compPlayerCards3Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PlayerCards2Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DisplayChipsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(85, 85, 85)))
                        .addComponent(DisplayNameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ChipsDispImageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MyCardsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(PlayerChipsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CheckButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(RaiseButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FoldButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CallButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CallButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CallButton1ActionPerformed
        currentChipImage = "5_chip (1).png";
        updateAllChipImages();
    }//GEN-LAST:event_CallButton1ActionPerformed

    private void FoldButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FoldButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FoldButton1ActionPerformed

    private void CheckButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckButton1ActionPerformed

    private void RaiseButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RaiseButton1ActionPerformed
        currentChipImage = "2_chip.png";
        updateAllChipImages();
    }//GEN-LAST:event_RaiseButton1ActionPerformed

    //I'm not sure about this code
    public void computerPlayerAction(String action) {
        switch (action.toLowerCase()) {
            case "call":
                currentChipImage = "5_chip (1).png";
                break;
            case "raise":
                currentChipImage = "2_chip.png";
                break;
            case "fold":
            case "check":
                // Do nothing, keep current image
                return;
        }
        updateAllChipImages();
    }
    /*
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 // Create a sample player list for testing
                ArrayList<String> playerIds = new ArrayList<>();
                
                
                new GameStage(playerIds).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CallButton1;
    private javax.swing.JButton CheckButton1;
    private javax.swing.JLabel ChipsDispImageLabel1;
    private javax.swing.JLabel ChipsImageLabel1;
    private javax.swing.JLabel DisplayChipsLabel1;
    private javax.swing.JLabel DisplayNameLabel1;
    private javax.swing.JButton FoldButton1;
    private javax.swing.JLabel MyCardsLabel1;
    private javax.swing.JLabel PlayerCards2Label1;
    private javax.swing.JLabel PlayerCardsLabel1;
    private javax.swing.JLabel PlayerChipsLabel2;
    private javax.swing.JButton RaiseButton1;
    private javax.swing.JLabel compPlayer1Label1;
    private javax.swing.JLabel compPlayer2Label1;
    private javax.swing.JLabel compPlayer3Label1;
    private javax.swing.JLabel compPlayerCards3Label1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
