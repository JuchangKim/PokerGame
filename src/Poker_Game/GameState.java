package Poker_Game;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private List<Player> players;
    private List<Card> communityCards;
    private int pot;
    private int currentBet;
    private BettingSystem betting;
    private Player winner;

    public GameState(List<Player> players, List<Card> communityCards, int pot, int currentBet) {
        this.players = players;
        this.communityCards = communityCards;
        this.betting = new BettingSystem();
        this.currentBet = currentBet;
       
        
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }
    
    public void resetGameState() {
    players.clear();
        getCommunityCards().clear();
    currentBet = 0;
 
}

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * @param communityCards the communityCards to set
     */
    public void setCommunityCards(List<Card> communityCards) {
        this.communityCards = communityCards;
    }

    /**
     * @return the betting
     */
    public BettingSystem getBetting() {
        return betting;
    }

    /**
     * @param betting the betting to set
     */
    public void setBetting(BettingSystem betting) {
        this.betting = betting;
    }

    public Player getWinner() {
        return this.winner;
    }
    public void setWinner(Player p) {
        this.winner = p;
    }
}