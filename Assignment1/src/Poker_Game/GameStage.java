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
    private JPanel mainPanel;
    private BettingPotPanel bettingPotPanel;
    private ControlButtonPanel controlButtonPanel;
    private AnnouncementPanel announcementPanel;
    private ExitButtonPanel exitButtonPanel;
    private CardDeckPanel cardDeckPanel;
    private CommunityCardsPanel communityCardsPanel;
    private PlayerPanel[] playerPanels;
    private CountDownLatch userInputLatch;  // Latch to pause the game flow
    private Player userPlayer;  // Keep track of the user player

    public GameStage(PokerGame game) throws InterruptedException {
        this.game = game;
        this.controller = new PokerController(game);
        this.game.addGameListener(this);
        this.userPlayer = game.getGameState().getPlayers().get(0); // Assume the first player is the user
        setSize(950, 750);
        setSize(950, 750);
        setTitle("Poker Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
    }

    private void initializeComponents() {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.GREEN);
        announcementPanel = new AnnouncementPanel(border);

        // Create and add components
        bettingPotPanel = new BettingPotPanel();
        getBettingPotPanel().setBounds(270, 200, 300, 100);
        mainPanel.add(getBettingPotPanel());
        
        communityCardsPanel = new CommunityCardsPanel();
        JPanel communityCardsContainer = communityCardsPanel.getPanel();
        communityCardsContainer.setBounds(270, 300, 390, 140);
        mainPanel.add(communityCardsContainer);

        announcementPanel = new AnnouncementPanel(border);
        mainPanel.add(announcementPanel.getPanel());

        controlButtonPanel = new ControlButtonPanel(e -> handleUserAction(e.getActionCommand()));
        mainPanel.add(controlButtonPanel);

        exitButtonPanel = new ExitButtonPanel();
        mainPanel.add(exitButtonPanel);

        cardDeckPanel = new CardDeckPanel();
        mainPanel.add(cardDeckPanel);

        // Set up player panels
        List<Player> players = getGame().getGameState().getPlayers();
        playerPanels = new PlayerPanel[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerPanels[i] = new PlayerPanel(players.get(i));
            // Set the bounds for the player panel and chips panel according to the specified locations
        switch (i) {
            case 0:
                playerPanels[i].getPanel().setBounds(480, 500, 200, 150);
                playerPanels[i].getChipsPanel().setBounds(680, 550, 200, 120);
                break;
            case 1:
                playerPanels[i].getPanel().setBounds(270, 20, 200, 150);
                playerPanels[i].getChipsPanel().setBounds(470, 50, 250, 120);
                break;
            case 2:
                playerPanels[i].getPanel().setBounds(680, 170, 200, 150);
                playerPanels[i].getChipsPanel().setBounds(680, 320, 250, 120);
                break;
            case 3:
                playerPanels[i].getPanel().setBounds(20, 170, 200, 150);
                playerPanels[i].getChipsPanel().setBounds(20, 320, 250, 120);
                break;
            default:
                break;
        }
        // Add the player panel and chips panel to the main panel
        mainPanel.add(playerPanels[i].getPanel());
        mainPanel.add(playerPanels[i].getChipsPanel());

        mainPanel.setLayout(null);
        // Set panel backgrounds
        playerPanels[i].getChipsPanel().setBackground(Color.GREEN);
        playerPanels[i].getPanel().setBackground(Color.GREEN);
        }
        mainPanel.setLayout(null);
        getContentPane().add(mainPanel);
        setVisible(true);
        
        startGameInBackground();
    }

    @Override
    public void onGameStateUpdated() {
        SwingUtilities.invokeLater(this::updateUI);
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
                    updateUI(); // Update the UI after the game has started

                    // When the game finishes, close the GameStage and open PlayAgainMenu
                    if (getGame().isFinished()) {
                        new PlayAgainMenu(getGame(), GameStage.this).setVisible(true); // Show PlayAgainMenu
                    }

                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> announcementPanel.updateAnnouncements(new String[]{"Failed to start game: " + e.getMessage()}));
                }
            }
        };
        gameWorker.execute();
    }
    
    private void updateUI() {
        // Determine if we should show the front side of all cards (e.g., after determining the winner)
    boolean showFrontCards = getGame().getGameState().getWinner() != null;

    for (int i = 0; i < 4; i++) {
            Player player = game.getGameState().getPlayers().get(i);
            boolean isUserPlayer = player.equals(userPlayer);
            playerPanels[i].updatePlayerInfo(player, showFrontCards || isUserPlayer);
            int chipCount = player.getChips();
            playerPanels[i].updateChipsPanel(chipCount);
        }
        if(showFrontCards) {
            // Call clearBettingPot() to clear the chips from the betting pot panel
            getBettingPotPanel().clearBettingPot();
        }
        communityCardsPanel.updateCommunityCards(game.getGameState().getCommunityCards());
        announcementPanel.updateAnnouncements(game.getAnnouncement());
        getBettingPotPanel().updatePot(game.getBettingSystem().getPot(), game.getGameState().getCurrentBet());

    }

    private void handleUserAction(String action) {
        Player player = getGame().getGameState().getPlayers().get(0); // Assume player 0 is the user
        updateUI();
        try {
            controller.performAction(action, player); // Delegate the action to the controller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controlButtonPanel.showButtons(false); // Hide buttons after the action is performed
        userInputLatch.countDown(); // Signal that the user input is complete
        updateUI();
    }

    @Override
    public void onPlayerTurn(Player player) {
        if (player.equals(getGame().getGameState().getPlayers().get(0))) {
            // It is the user's turn; show the buttons and wait for user input
            userInputLatch = new CountDownLatch(1);
            controlButtonPanel.showButtons(true);
            try {
                userInputLatch.await(); // Wait for user action to be handled
            } catch (InterruptedException ex) {
                Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    /**
     * @return the bettingPotPanel
     */
    public BettingPotPanel getBettingPotPanel() {
        return bettingPotPanel;
    }
}
