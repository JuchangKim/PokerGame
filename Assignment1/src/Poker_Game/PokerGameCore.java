/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import Poker_Game.Database.FileManager;
import Poker_Game.Panel.GameStage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author billi
 */
public abstract class PokerGameCore extends FileManager {
    protected GameState gameState;
    protected Deck deck;
    protected BettingSystem bettingSystem;
    protected List<GameListener> listeners;
    protected String[] announcement;
    protected String round;
    protected boolean isFinished;
    private GameStage gameStage;

    public PokerGameCore() {
        List<Player> players = new ArrayList<>();
        List<Card> communityCards = new ArrayList<>();
        gameState = new GameState(players, communityCards, 0, 0);
        deck = new Deck();
        bettingSystem = new BettingSystem();
        listeners = new ArrayList<>();
        announcement = new String[6];
        for (int i = 0; i < announcement.length; i++) {
            announcement[i] = " ";
        }
        round = "";
        isFinished = false;
    }

    public abstract void startGame(String username) throws InterruptedException;
    
    protected abstract void playRound() throws InterruptedException;
    
    protected abstract void playBettingRound(String roundName) throws InterruptedException;
    
    public abstract void playerTurn(Player player) throws InterruptedException;

    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    public void notifyGameUpdated() {
        for (GameListener listener : listeners) {
            listener.onGameStateUpdated();
        }
    }

    public void resetGame() {
        for (Player player : gameState.getPlayers()) {
            player.setFolded(false);
            player.setIsInGame(true);
            player.setCurrentBet(0);
        }
        bettingSystem.resetPot();
        deck.shuffleDeck();
        for (int i = 0; i < announcement.length; i++) {
            announcement[i] = " ";
        }
        notifyGameUpdated();
    }

    public void addPlayer(String name, int chips) {
        gameState.getPlayers().add(new Player(name, chips));
    }

    protected boolean onePlayerIsInGame() {
        int playersInGame = 0;
        Player lastPlayer = null;
        
        for (Player player : gameState.getPlayers()) {
            if (player.getIsInGame()) {
                playersInGame++;
                lastPlayer = player;
            }
        }
        
        if (playersInGame == 1 && lastPlayer != null) {
            handleLastPlayerStanding(lastPlayer);
            return true;
        }
        
        return false;
    }

    protected void handleLastPlayerStanding(Player lastPlayer) {
        System.out.println("All other players have folded. " + lastPlayer.getName() + " is the winner!\n");
        setAnnouncement("All other players have folded. " + lastPlayer.getName() + " is the winner!\n", 0);
        gameState.setWinner(lastPlayer);
        lastPlayer.addNumOfWin();
        lastPlayer.addToChips(bettingSystem.getPot());
        FileManager.saveGame((PokerGame) this, gameState.getPlayers().get(0).getName());
        bettingSystem.resetPot();
        
        notifyGameUpdated();
        
        for (Player p : gameState.getPlayers()) {
            System.out.println(p.getName() + " has " + p.getChips() + " chips \n");
            setAnnouncement(p.getName() + " has " + p.getChips() + " chips \n", gameState.getPlayers().indexOf(p) + 1);
        }
        notifyGameUpdated();
    }

    // Getters and setters
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Deck getDeck() {
        return deck;
    }

    public BettingSystem getBettingSystem() {
        return bettingSystem;
    }

    public void setBettingSystem(BettingSystem bettingSystem) {
        this.bettingSystem = bettingSystem;
    }

    public String[] getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement, int index) {
        if (index >= 0 && index < this.announcement.length) {
            this.announcement[index] = announcement;
        }
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public boolean isFinished() {
        return isFinished;
    }

    protected void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * @return the gameStage
     */
    public GameStage getGameStage() {
        return gameStage;
    }
}
