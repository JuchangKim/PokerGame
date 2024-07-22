/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
import java.util.ArrayList;
import java.util.List;

public class PokerGame {
    private List<Player> players;
    private Deck deck;
    private BettingSystem bettingSystem;
    private List<Card> communityCards;

    public PokerGame() {
        players = new ArrayList<>();
        deck = new Deck();
        bettingSystem = new BettingSystem();
        communityCards = new ArrayList<>();
    }

    public void addPlayer(String name, int chips) {
        players.add(new Player(name, chips));
    }

    public void startGame() {
        // Implement the game logic here
        deck.reset();
        communityCards.clear();
        for(Player player : players)
        {
            player.getHand().clear();
            
        }
        
        // Release Initial cards
        for(int i = 0; i < 2; i++)
        {
            for(Player player : players)
            {
                player.addCardToHand(deck.getCard());
            }
        }
        
        // Game flow
        
        
    }
/*
    public void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.addCardToHand(deck.dealCard());
            }
        }
    }
*/
    public void showHands() {
        for (Player player : players) {
            System.out.println(player);
        }
    }
}
