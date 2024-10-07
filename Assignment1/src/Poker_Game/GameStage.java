package Poker_Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;

public class GameStage extends JFrame implements GameListener {
    private PokerGame game;
    private PokerController controller;
    private JPanel mainPanel, buttonPanel, communityCardsPanel, exitButtonPanel, cardDeckPanel, bettingPotPanel;
    private JPanel[] playerCardPanels, playerChipsPanels;
    private JLabel[] playerChipLabels, playerCardLabels, playerNameLabels, announcementLabels;
    private JLabel bettingPotLabel, communityCardsLabel;
    private JButton callButton, foldButton, raiseButton, checkButton, exitButton;
    private Border border;
    private CountDownLatch userInputLatch;  // Latch to pause the game flow
    private int chipSize;       // Size of each chip image
    private int currentChipCount; // Track the number of chips already added
    
    public GameStage(PokerGame game) throws InterruptedException {
        this.game = game;
        this.game.addGameListener(this);
        setSize(950, 750);
        setTitle("Poker Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen.s
        this.controller = new PokerController(game);
        chipSize = 30;
        currentChipCount = 0;
        
        border = BorderFactory.createLineBorder(Color.BLACK, 2); // Create a border object
        
        initializeComponents();
    }
    
    private void initializeComponents() throws InterruptedException {
        super.setBackground(Color.GREEN);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.GREEN); // A vibrant green background
        
        setupAnnouncementPanel(); // New method to handle announcements
        setupPlayerPanels();
        setupCenterPanel();
        setupControlButtons();
        setupExitButton();
        setupCardDeckPanel();
        
        getContentPane().add(mainPanel); // Add the panel to the JFrame
        
        //pack(); // Adjusts the frame size to fit the components
        setVisible(true);
        startGameInBackground();
        
    }
    
    @Override
    public void onGameStateUpdated() {
        SwingUtilities.invokeLater(this::updateUI); // Update UI on the EDT
    }
    
    private void setupPlayerPanels() {
        
        playerChipLabels = new JLabel[4]; // Assuming four players
        playerCardPanels = new JPanel[4];
        playerNameLabels = new JLabel[4];
        playerChipsPanels = new JPanel[4];
        
        for (int i = 0; i < 4; i++) {
            JPanel panel = new JPanel();
            JPanel chipsPanel = new JPanel();
            
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(border); // Apply the border to the panel
            chipsPanel.setLayout(null); // Set layout to null for manual positioning
            chipsPanel.setBorder(border);
            
            
            playerNameLabels[i] = new JLabel(getGame().getGameState().getPlayers().get(i).getName());
            repaint();
            playerChipLabels[i] = new JLabel("Chips: " + getGame().getGameState().getPlayers().get(i).getChips());
            repaint();
            // Initialize card labels panel as a JPanel to hold two card images
            JPanel cardPanel = new JPanel(new FlowLayout()); // To hold the two card labels
            
            JLabel cardLabel1 = new JLabel(); // Initialize empty label for the first card
            JLabel cardLabel2 = new JLabel(); // Initialize empty label for the second card
            
            // Check if the first card image is available and assign it
            List<Card> cards = new ArrayList<>(getGame().getGameState().getPlayers().get(i).getHand().getCards());
            if (!cards.isEmpty() &&
                    cards.get(0).getBackImageIcon() != null) {
                ImageIcon image1 = cards.get(0).getBackImageIcon();
                cardLabel1.setIcon(image1); // Set the card image
            }
            
            // Check if the second card image is available and assign it
            if (cards.size() > 1 &&
                    cards.get(1).getBackImageIcon() != null) {
                ImageIcon image2 = cards.get(1).getBackImageIcon();
                cardLabel2.setIcon(image2); // Set the card image
            }
            
            // Add both card labels to the cardPanel
            cardPanel.add(cardLabel1);
            cardPanel.add(cardLabel2);
            cardPanel.setBackground(Color.GREEN);
            
            // Store this panel in the playerCardLabels array
            playerCardPanels[i] = new JPanel(); // Initialize the playerCardLabel (or use JPanel if you want)
            playerCardPanels[i].add(cardPanel); // Add the cardPanel to the playerCardLabel
            playerCardPanels[i].setBackground(Color.GREEN);
            
            panel.add(playerNameLabels[i]);
            panel.add(playerChipLabels[i]);
            panel.add(playerCardPanels[i]); // Add the card panel directly instead of playerCardLabels[i]
            
            // Calculate how many chip images to display for each player
            int chips = getGame().getGameState().getPlayers().get(i).getChips();
            int chipImagesCount = chips / 90; // Assuming each chip image represents 90 chips
            setChipsToPanel(chipsPanel, chipImagesCount);

            playerChipsPanels[i] = chipsPanel;
            
            switch (i) {
                case 0:
                    panel.setBounds(480, 500, 200, 150);
                    chipsPanel.setBounds(680, 500, 200, 120);
                    break;
                case 1:
                    panel.setBounds(270, 20, 200, 150);
                    chipsPanel.setBounds(470, 50, 250, 120);
                    break;
                case 2:
                    panel.setBounds(680, 170, 200, 150);
                    chipsPanel.setBounds(680, 320, 250, 120);
                    break;
                case 3:
                    panel.setBounds(20, 170, 200, 150);
                    chipsPanel.setBounds(20, 320, 250, 120);
                    break;
                default:
                    break;
            }
            
            mainPanel.add(panel);
            mainPanel.add(chipsPanel);
            chipsPanel.setBackground(Color.GREEN);
            panel.setBackground(Color.GREEN); // A vibrant green background
        }
        
    }
    
    private void setupAnnouncementPanel() {
        announcementLabels = new JLabel[6];
        
        for (int i = 0; i < 6; i++) {
            // Create and initialize a label for each player’s announcement
            announcementLabels[i] = new JLabel(" ");
        }
        
        JPanel announcementPanel = new JPanel();
        announcementPanel.setLayout(new BoxLayout(announcementPanel, BoxLayout.Y_AXIS));
        announcementPanel.setBorder(border); // Apply the defined border to the panel
        announcementPanel.setBounds(10, 450, 450, 200); // Correctly set the bounds
        announcementPanel.setBackground(Color.GREEN); // Set background color
        
        for (int i = 0; i < 6; i++) {
            announcementPanel.add(announcementLabels[i]);
        }
        
        mainPanel.add(announcementPanel);
        
        repaint();
        
        
    }
    
     private void setupExitButton() {
        exitButtonPanel = new JPanel();
        exitButton = new JButton("Exit Game");
        exitButtonPanel.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0)); // Close the application
        exitButtonPanel.setBounds(750, 50, 100, 70);
        exitButtonPanel.setBackground(Color.GREEN); // A vibrant green background
        exitButtonPanel.setVisible(true);  // Initially hidden
        
        mainPanel.add(exitButtonPanel);
        mainPanel.setLayout(null); // Set layout to null for manual positioning
    }

    private void setupCardDeckPanel() {
    cardDeckPanel = new JPanel();

    // Load the card deck image
    String image1Name = "/Poker_Game/CardImages/cards_back_deck.png"; 
    java.net.URL imgUrl = getClass().getResource(image1Name);

    if (imgUrl != null) {
        ImageIcon deckImage = new ImageIcon(imgUrl);

        // Create a JLabel to hold the image
        JLabel deckLabel = new JLabel(deckImage);

        // Optionally resize the image if needed
        Image scaledImage = deckImage.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH); // Resize if necessary
        deckLabel.setIcon(new ImageIcon(scaledImage));  // Set the resized image

        // Add the JLabel with the image to the cardDeckPanel
        deckLabel.setBounds(0, 0, 70, 100);  // Position inside the panel
        
        cardDeckPanel.add(deckLabel);
        mainPanel.add(cardDeckPanel);
    } else {
        System.err.println("Failed to load deck image.");
    }

    // Set the position and size of the cardDeckPanel on the main panel
    cardDeckPanel.setBounds(570, 200, 70, 100);  // Position the card deck panel on mainPanel
    cardDeckPanel.setLayout(null);  // Set layout to null for manual positioning
    cardDeckPanel.setBackground(Color.GREEN);
    // Add the cardDeckPanel to the main panel
    mainPanel.setLayout(null);
    mainPanel.add(cardDeckPanel);

    repaint();
}
 
    private void handleUserAction(String action) {
        Player player = game.getGameState().getPlayers().get(0);  // Assume player 0 is the user
        updateUI();
        try {
            controller.performAction(action, player);  // Delegate the action to the controller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showButtons(false);  // Hide buttons after the action is performed
        userInputLatch.countDown();  // Signal that the user input is complete
        updateUI();
    }
    
    
    private void startGameInBackground() {
        SwingWorker<Void, String> gameWorker;
        gameWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                getGame().startGame(getGame().getGameState().getPlayers().get(0).getName());
                
                return null;
            }
            
            @Override
            protected void done() {
                try {
                    
                    updateUI(); // Update the UI after game has started
                    
                    // When the game finishes, close the GameStage and open PlayAgainMenu
                    if(getGame().isFinished()) {
                        new PlayAgainMenu(getGame(), GameStage.this).setVisible(true);  // Show PlayAgainMenu
                    }
                    
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> announcementLabels[1].setText("Failed to start game: " + e.getMessage()));
                }
            }
        };
        gameWorker.execute();
    }
    
    
    
    private void setupCenterPanel() {
        JPanel centerUpPanel = new JPanel(new BorderLayout());
        JPanel centerDownPanel = new JPanel(new BorderLayout());
        
        bettingPotLabel = new JLabel("Betting Pot: " + getGame().getBettingSystem().getPot() 
                + "    Current Bet: " + getGame().getGameState().getCurrentBet(), SwingConstants.CENTER);
        communityCardsPanel = new JPanel();
        communityCardsPanel.setBackground(Color.GREEN);
        communityCardsLabel = new JLabel("Community Cards", SwingConstants.CENTER);
        
        bettingPotPanel = new JPanel();
        bettingPotPanel.setLayout(null); // Manual positioning for the chips
        bettingPotPanel.setBackground(Color.GREEN); // Set background color

        centerDownPanel.add(communityCardsLabel, BorderLayout.NORTH);
        centerDownPanel.add(communityCardsPanel);
        centerUpPanel.add(bettingPotLabel, BorderLayout.NORTH);
        centerUpPanel.add(bettingPotPanel, BorderLayout.CENTER); // Add the betting pot panel
        
        centerUpPanel.setBounds(270, 200, 300, 100);
        centerDownPanel.setBounds(270, 300, 390, 140);
        centerUpPanel.setBackground(Color.GREEN); // A vibrant green background
        centerDownPanel.setBackground(Color.GREEN); // A vibrant green background
        centerUpPanel.setBorder(border); // Apply the border to the panel
        centerDownPanel.setBorder(border); // Apply the border to the panel
        mainPanel.add(centerUpPanel);
        mainPanel.add(centerDownPanel);
        
        updateBettingPotPanel(); // Initialize with the current pot value
    }
    
    // Method to add chips to the betting pot panel based on the current pot value
private void updateBettingPotPanel() {
    int pot = getGame().getBettingSystem().getPot();
    int newChipCount = pot / 10; // Each chip represents 10 chips

    if (newChipCount > currentChipCount) {
        // Only add the new chips that haven't been added yet
        addChipsToBettingPot(newChipCount - currentChipCount);
        currentChipCount = newChipCount; // Update the current chip count to reflect the new total
    }

    bettingPotPanel.revalidate();
    bettingPotPanel.repaint();
    
    // Update the pot value label
    bettingPotLabel.setText("Betting Pot: " + getGame().getBettingSystem().getPot() 
                + "    Current Bet: " + getGame().getGameState().getCurrentBet());
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

            // Generate random x and y positions within the bounds of the bettingPotPanel
            int xPos = (int) (Math.random() * (bettingPotPanel.getWidth() - chipSize));
            int yPos = (int) (Math.random() * (bettingPotPanel.getHeight() - chipSize));

            chipLabel.setBounds(xPos, yPos, chipSize, chipSize); // Position the chip
            bettingPotPanel.add(chipLabel); // Add the chip image to the panel
        }
    } else {
        System.err.println("Chip image not found: " + chipImageName);
    }
}


// This method should be called whenever the pot value is updated, e.g., after a bet or raise
public void onBettingSystemUpdated() {
    updateBettingPotPanel(); // Update the chips in the betting pot panel
}
    
    private void setupControlButtons() {
        buttonPanel = new JPanel();
        // initialization of buttons as before
        callButton = new JButton("Call");
        foldButton = new JButton("Fold");
        raiseButton = new JButton("Raise");
        checkButton = new JButton("Check");
        
        
        
        
        buttonPanel.add(callButton);
        buttonPanel.add(foldButton);
        buttonPanel.add(raiseButton);
        buttonPanel.add(checkButton);
        
        
        // Add action listeners to handle game logic on button press
        callButton.addActionListener(e -> handleUserAction("Call"));
        foldButton.addActionListener(e -> handleUserAction("Fold"));
        raiseButton.addActionListener(e -> handleUserAction("Raise"));
        checkButton.addActionListener(e -> handleUserAction("Check"));
        
        
        buttonPanel.setBounds(450, 450, 400, 70);
        
        buttonPanel.setBackground(Color.GREEN); // A vibrant green background
        buttonPanel.setVisible(false);  // Initially hidden
        mainPanel.add(buttonPanel);
    }
    
    @Override
    public void onPlayerTurn(Player player) {
        // Only show buttons if it's the first player's (user's) turn
        if (player.equals(game.getGameState().getPlayers().get(0))) {
            userInputLatch = new CountDownLatch(1);  // Create a new latch for this turn
            showButtons(true);  // Show the buttons for the human player
            try {
                userInputLatch.await();  // Wait for the user input
            } catch (InterruptedException ex) {
                Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
   // Method to add overlapping chip images to the chipsPanel
    private void setChipsToPanel(JPanel chipsPanel, int chipImagesCount) {
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
    
    public void showButtons(boolean visible) {
        buttonPanel.setVisible(visible);
        
        revalidate(); // Revalidates the layout to ensure proper rendering
        repaint();    // Repaints the component to reflect visibility changes
        
    }
    
    public void updateUI() {
        if (SwingUtilities.isEventDispatchThread()) {
            if (getGame() != null) {
                List<Player> players = getGame().getGameState().getPlayers();
                
                for (int i = 0; i < Math.min(players.size(), playerNameLabels.length); i++) {
                    if (playerNameLabels[i] != null && playerChipLabels[i] != null && playerCardPanels[i] != null) {
                        playerNameLabels[i].setText(players.get(i).getName());
                        playerChipLabels[i].setText("Chips: " + players.get(i).getChips());
                        updatePlayerCardImages(playerCardPanels[i], players.get(i));  // Pass the player here
                    }
                }
                
                updateCommunityCardImages(getGame().getGameState().getCommunityCards());
                
                if (bettingPotLabel != null) {
                    bettingPotLabel.setText("Betting Pot: " + getGame().getBettingSystem().getPot()
                            + "       Current Bet: " + getGame().getGameState().getCurrentBet());
                    onBettingSystemUpdated();
                }
                
                if (announcementLabels[0] != null) {
                    announcementLabels[0].setText(getGame().getRound() != null ? getGame().getRound() : " ");
                }
                
                // Update announcements for each player
                String[] announcements = getGame().getAnnouncement();
                for (int i = 1; i < 6; i++) {
                    announcementLabels[i].setText(announcements[i]); // Update the announcement labels
                }
                
            }
        } else {
            SwingUtilities.invokeLater(this::updateUI);
        }
    }
    
    
    
    /**
     * @return the game
     */
    public PokerGame getGame() {
        return game;
    }
    
    /**
     * @param game the game to set
     */
    public void setGame(PokerGame game) {
        this.game = game;
    }
    
    // Updating community card images
    private void updateCommunityCardImages(List<Card> communityCards) {
        communityCardsPanel.removeAll();
        // Create a copy of the list to avoid ConcurrentModificationException
        List<Card> communityCardsCopy = new ArrayList<>(communityCards);
        
        for (Card card : communityCardsCopy) {
            if (card != null && card.getFrontImageIcon() != null) {
                communityCardsPanel.add(new JLabel(card.getFrontImageIcon()));
            }
        }
        communityCardsPanel.setBackground(Color.GREEN);
        communityCardsPanel.revalidate();
        communityCardsPanel.repaint();
    }
    
    // Updating player card images (user -> front, computer -> back)
    private void updatePlayerCardImages(JPanel cardPanel, Player player) {
        cardPanel.removeAll();
        
        // If the player is the user (first player), show front side of cards
        
        // Check if the game is in the DetermineWinnerState
        boolean isDetermineWinnerState = getGame().getRound().equalsIgnoreCase("Determining The Winner");
        
        if (getGame().getGameState().getPlayers().get(0).equals(player) || isDetermineWinnerState) {
            // User's cards: show front side
            for (Card card : player.getHand().getCards()) {
                if (card != null && card.getFrontImageIcon() != null) {
                    cardPanel.add(new JLabel(card.getFrontImageIcon()));
                }
            }
        } else {
            // Computer player's cards: show back side
            for (Card card : player.getHand().getCards()) {
                if (card != null && card.getBackImageIcon() != null) {
                    cardPanel.add(new JLabel(card.getBackImageIcon()));
                }
            }
        }
        
        cardPanel.setBackground(Color.GREEN);
        cardPanel.revalidate();
        cardPanel.repaint();
        
        
    }   
    }

   
