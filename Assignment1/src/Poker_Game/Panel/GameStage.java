package Poker_Game.Panel;

import Poker_Game.GameListener;
import Poker_Game.Panel.ExitButtonPanel;
import Poker_Game.Panel.PlayerPanel;
import Poker_Game.Panel.CommunityCardsPanel;
import Poker_Game.Panel.ControlButtonPanel;
import Poker_Game.Panel.AnnouncementPanel;
import Poker_Game.Panel.PokerController;
import Poker_Game.Panel.CardDeckPanel;
import Poker_Game.Panel.BettingPotPanel;
import Poker_Game.Player;
import Poker_Game.PokerGame;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/* this GameStage is abstract class and allow to extends to standardgamestage class also
   this class contains abstract methods that will be implemented in the standardgamestage class
*/
public abstract class GameStage extends JFrame implements GameListener {

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

   // Abstract methods to be implemented by the subclass
    protected abstract void initializeComponents();
    protected abstract void startGameInBackground();
    protected abstract void updateUI();

    @Override
    public void onGameStateUpdated() {
        SwingUtilities.invokeLater(this::updateUI);
    }

    // When the user turn control buttons are represented.
    @Override
    public void onPlayerTurn(Player player) {
        if (player.equals(getGame().getGameState().getPlayers().get(0))) {
            // It is the user's turn; show the buttons and wait for user input
            setUserInputLatch(new CountDownLatch(1));
            getControlButtonPanel().showButtons(true);
            try {
                getUserInputLatch().await(); // Wait for user action to be handled
            } catch (InterruptedException ex) {
                Logger.getLogger(GameStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
    From here to down, there are get and set methods.
    */
    public PokerGame getGame() {
        return game;
    }

    public void setGame(PokerGame game) {
        this.game = game;
    }

    public BettingPotPanel getBettingPotPanel() {
        return bettingPotPanel;
    }

    public PokerController getController() {
        return controller;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ControlButtonPanel getControlButtonPanel() {
        return controlButtonPanel;
    }

    public AnnouncementPanel getAnnouncementPanel() {
        return announcementPanel;
    }

    public ExitButtonPanel getExitButtonPanel() {
        return exitButtonPanel;
    }

    public CardDeckPanel getCardDeckPanel() {
        return cardDeckPanel;
    }

    public CommunityCardsPanel getCommunityCardsPanel() {
        return communityCardsPanel;
    }

    public PlayerPanel[] getPlayerPanels() {
        return playerPanels;
    }

    public void setController(PokerController controller) {
        this.controller = controller;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void setBettingPotPanel(BettingPotPanel bettingPotPanel) {
        this.bettingPotPanel = bettingPotPanel;
    }

    public void setControlButtonPanel(ControlButtonPanel controlButtonPanel) {
        this.controlButtonPanel = controlButtonPanel;
    }

    public void setAnnouncementPanel(AnnouncementPanel announcementPanel) {
        this.announcementPanel = announcementPanel;
    }

    public void setExitButtonPanel(ExitButtonPanel exitButtonPanel) {
        this.exitButtonPanel = exitButtonPanel;
    }

    public void setCardDeckPanel(CardDeckPanel cardDeckPanel) {
        this.cardDeckPanel = cardDeckPanel;
    }
    
    public void setCommunityCardsPanel(CommunityCardsPanel communityCardsPanel) {
        this.communityCardsPanel = communityCardsPanel;
    }

    public void setPlayerPanels(PlayerPanel[] playerPanels) {
        this.playerPanels = playerPanels;
    }

    public CountDownLatch getUserInputLatch() {
        return userInputLatch;
    }

    public void setUserInputLatch(CountDownLatch userInputLatch) {
        this.userInputLatch = userInputLatch;
    }

    public Player getUserPlayer() {
        return userPlayer;
    }

    public void setUserPlayer(Player userPlayer) {
        this.userPlayer = userPlayer;
    }
}
