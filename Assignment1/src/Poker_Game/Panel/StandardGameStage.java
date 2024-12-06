/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game.Panel;

import Poker_Game.Player;
import Poker_Game.PokerGame;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.Border;

/**
 *
 * @author user
 */

public class StandardGameStage extends GameStage {

    public StandardGameStage(PokerGame game) throws InterruptedException {
        super(game);
    }

    // initialising main panel and component panels too
    @Override
    protected void initializeComponents() {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        setMainPanel(new JPanel(null));
        getMainPanel().setBackground(Color.GREEN);

        setBettingPotPanel(new BettingPotPanel());
        getBettingPotPanel().setBounds(270, 200, 300, 100);
        getMainPanel().add(getBettingPotPanel());

        setCommunityCardsPanel(new CommunityCardsPanel());
        JPanel communityCardsContainer = getCommunityCardsPanel().getPanel();
        communityCardsContainer.setBounds(270, 300, 390, 140);
        getMainPanel().add(communityCardsContainer);

        setAnnouncementPanel(new AnnouncementPanel(border));
        getMainPanel().add(getAnnouncementPanel().getPanel());

        setControlButtonPanel(new ControlButtonPanel(e -> handleUserAction(e.getActionCommand())));
        getMainPanel().add(getControlButtonPanel());

        setExitButtonPanel(new ExitButtonPanel());
        getMainPanel().add(getExitButtonPanel());

        setCardDeckPanel(new CardDeckPanel());
        getMainPanel().add(getCardDeckPanel());

        List<Player> players = getGame().getGameState().getPlayers();
        setPlayerPanels(new PlayerPanel[players.size()]);
        for (int i = 0; i < players.size(); i++) {
            getPlayerPanels()[i] = new PlayerPanel(players.get(i));

            switch (i) {
                case 0:
                    getPlayerPanels()[i].getPanel().setBounds(480, 500, 200, 150);
                    getPlayerPanels()[i].getChipsPanel().setBounds(680, 550, 200, 120);
                    break;
                case 1:
                    getPlayerPanels()[i].getPanel().setBounds(270, 20, 200, 150);
                    getPlayerPanels()[i].getChipsPanel().setBounds(470, 50, 250, 120);
                    break;
                case 2:
                    getPlayerPanels()[i].getPanel().setBounds(680, 170, 200, 150);
                    getPlayerPanels()[i].getChipsPanel().setBounds(680, 320, 250, 120);
                    break;
                case 3:
                    getPlayerPanels()[i].getPanel().setBounds(20, 170, 200, 150);
                    getPlayerPanels()[i].getChipsPanel().setBounds(20, 320, 250, 120);
                    break;
                default:
                    break;
            }

            getMainPanel().add(getPlayerPanels()[i].getPanel());
            getMainPanel().add(getPlayerPanels()[i].getChipsPanel());

            getPlayerPanels()[i].getChipsPanel().setBackground(Color.GREEN);
            getPlayerPanels()[i].getPanel().setBackground(Color.GREEN);
        }

        getContentPane().add(getMainPanel());
        setVisible(true);

        startGameInBackground();
    }

    // when game is running, the gamestage is running together
    @Override
    protected void startGameInBackground() {
        SwingWorker<Void, Void> gameWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                getGame().startGame(getGame().getGameState().getPlayers().get(0).getName());
                return null;
            }

            @Override
            protected void done() {
                updateUI();
                if (getGame().isFinished()) {
                    getBettingPotPanel().clearBettingPot();
                    new PlayAgainMenu(getGame(), StandardGameStage.this).setVisible(true);
                }
            }
        };
        gameWorker.execute();
    }

    // when user turn, control buttons are represented and after clicking, the control buttons are disappeared without exit button
    private void handleUserAction(String action) {
        Player player = getGame().getGameState().getPlayers().get(0); // Assume player 0 is the user
        updateUI();
        try {
            getController().performAction(action, player); // Delegate the action to the controller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getControlButtonPanel().showButtons(false); // Hide buttons after the action is performed
        getUserInputLatch().countDown(); // Signal that the user input is complete
        updateUI();
    }
    
    // update player chips, betting pot information and images, community cards, announcements every time
    @Override
    public void updateUI() {
        List<Player> players = getGame().getGameState().getPlayers();
        boolean showFrontCards = getGame().getGameState().getWinner() != null;

        for (int i = 0; i < 4; i++) {
            Player player = players.get(i);
            boolean isUserPlayer = player.equals(getUserPlayer());
            getPlayerPanels()[i].updatePlayerInfo(player, showFrontCards || isUserPlayer);
            getPlayerPanels()[i].updateChipsPanel(player.getChips());
        }

        getCommunityCardsPanel().updateCommunityCards(getGame().getGameState().getCommunityCards());
        getBettingPotPanel().updatePot(getGame().getBettingSystem().getPot(), getGame().getGameState().getCurrentBet());
        getAnnouncementPanel().updateAnnouncements(getGame().getAnnouncement());
    }
}