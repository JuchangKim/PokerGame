package Poker_Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameStage extends JFrame implements PokerGame.GameStateListener {
    private PokerGame game;
    private JPanel mainPanel;
    private JLabel[] playerChipLabels, playerCardLabels, playerNameLabels;
    private JLabel communityCardsLabel, bettingPotLabel, announcementLabel;
    private JButton callButton, foldButton, raiseButton, checkButton, exitButton;

    public GameStage(PokerGame game) {
        this.game = game;
        setSize(900, 600);
        setTitle("Poker Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen.s
        this.game.addGameStateListener(this);
        initializeComponents();
    }

    private void initializeComponents() {
        super.setBackground(Color.GREEN);
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.GREEN); // A vibrant green background
        announcementLabel = new JLabel("Starting the game...", SwingConstants.CENTER);
        getContentPane().add(mainPanel); // Add the panel to the JFrame
        
        setupPlayerPanels();
        mainPanel.setBackground(Color.GREEN); // A vibrant green background
        setupCenterPanel();
        mainPanel.setBackground(Color.GREEN); // A vibrant green background
        setupControlButtons();
        mainPanel.setBackground(Color.GREEN); // A vibrant green background
        setupAnnouncementPanel(); // New method to handle announcements
        
        //pack(); // Adjusts the frame size to fit the components
        setVisible(true);
        startGameInBackground();
    }

    @Override
    public void onGameStateUpdated() {
        SwingUtilities.invokeLater(this::updateUI); // Ensure updates are on the EDT
    }
    
    private void setupPlayerPanels() {
     
    playerChipLabels = new JLabel[4]; // Assuming four players
    playerCardLabels = new JLabel[4];
    playerNameLabels = new JLabel[4];

    for (int i = 0; i < 4; i++) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        playerNameLabels[i] = new JLabel(game.getGameState().getPlayers().get(i).getName());
        repaint();
        playerChipLabels[i] = new JLabel("Chips: " + game.getGameState().getPlayers().get(i).getChips());
        repaint();
        playerCardLabels[i] = new JLabel("Cards: " + game.getGameState().getPlayers().get(i).getHand());
        repaint();
        
        panel.add(playerNameLabels[i]);
        panel.add(playerChipLabels[i]);
        panel.add(playerCardLabels[i]);

        switch (i) {
            case 0:
                panel.setBounds(375, 490, 150, 100);
                break;
            case 1:
                panel.setBounds(375, 10, 150, 100);
                break;
            case 2:
                panel.setBounds(730, 200, 150, 100);
                break;
            case 3:
                panel.setBounds(10, 200, 150, 100);
                break;
            default:
                break;
        }
        mainPanel.add(panel);
        panel.setBackground(Color.GREEN); // A vibrant green background
    }

    }

    private void setupAnnouncementPanel() {
    
    announcementLabel = new JLabel("Game Announcements Here", SwingConstants.CENTER);
    announcementLabel.setBounds(200, 360, 500, 50);
    mainPanel.add(announcementLabel);
    announcementLabel.setBackground(Color.GREEN); // A vibrant green background
}
    private void startGameInBackground() {
        Thread gameThread = new Thread(() -> {
            try {
                game.startGame(game.getGameState().getPlayers().get(0).getName());
                SwingUtilities.invokeLater(this::updateUI);
                repaint();
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> announcementLabel.setText("Failed to start game: " + e.getMessage()));
            }
        });
        gameThread.start();
        repaint();
    }

    private void setupCenterPanel() {
        JPanel centerUpPanel = new JPanel(new BorderLayout());
        JPanel centerDownPanel = new JPanel(new BorderLayout());
        communityCardsLabel = new JLabel("Community Cards : " + game.getGameState().getCommunityCards());
        bettingPotLabel = new JLabel("Betting Pot: " + game.getBettingSystem().getPot());
        announcementLabel = new JLabel(game.getGameState().getAnnouncement(), SwingConstants.CENTER);

        centerDownPanel.add(communityCardsLabel);
        centerUpPanel.add(bettingPotLabel);

        centerUpPanel.setBounds(350, 150, 200, 100);
        centerDownPanel.setBounds(350, 250, 200, 100);
        centerUpPanel.setBackground(Color.GREEN); // A vibrant green background
        centerDownPanel.setBackground(Color.GREEN); // A vibrant green background
        mainPanel.add(centerUpPanel);
        mainPanel.add(centerDownPanel);
        
    }

    private void setupControlButtons() {
        JPanel buttonPanel = new JPanel();
        callButton = new JButton("Call");
        foldButton = new JButton("Fold");
        raiseButton = new JButton("Raise");
        checkButton = new JButton("Check");
        exitButton = new JButton("Exit Game");

        // Add action listeners to handle game logic on button press
        callButton.addActionListener(e -> performAction("Call"));
        foldButton.addActionListener(e -> performAction("Fold"));
        raiseButton.addActionListener(e -> performAction("Raise"));
        checkButton.addActionListener(e -> performAction("Check"));
        exitButton.addActionListener(e -> System.exit(0)); // Close the application
        
        buttonPanel.add(callButton);
        buttonPanel.add(foldButton);
        buttonPanel.add(raiseButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(exitButton);

        buttonPanel.setBounds(250, 450, 400, 50);
        mainPanel.add(buttonPanel);
        buttonPanel.setBackground(Color.GREEN); // A vibrant green background
    }
    
    private void performAction(String action) {
        // Placeholder for integrating with game logic
        announcementLabel.setText(action + " button pressed.");
        // Update game state based on action
    }
    
    public void updateUI() {
        java.util.List<Player> players = game.getGameState().getPlayers();
        for (int i = 0; i < 4; i++) {
            playerNameLabels[i].setText(players.get(i).getName());
            playerChipLabels[i].setText("Chips: " + players.get(i).getChips());
            playerCardLabels[i].setText("Cards: " + players.get(i).getHand());
        }
        communityCardsLabel.setText("Community Cards : " + game.getGameState().getCommunityCards().toString());
        bettingPotLabel.setText("Betting Pot: " + game.getBettingSystem().getPot() + "       "
                                + "Current Bet: " + game.getGameState().getCurrentBet());
        announcementLabel.setText(game.getGameState().getAnnouncement());
        repaint();
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PokerGame game = new PokerGame(); // Create a new instance of the game.
            GameStage gameStage = new GameStage(game);
            gameStage.setVisible(true);
        });
    }
}
