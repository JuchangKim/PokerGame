/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;



/**
 *
 * @author user
 */

import java.util.Arrays;
import java.util.Scanner;

public class Player {
    
    // Player attributes are encapsulated to ensure modifications are controlled
    private String name;        // Name of the player
    private int chips;          // Number of chips the player has
    private Card[] holeCards = new Card[2]; // The player's two private cards
    private boolean isInGame;   // Indicates if the player is still in the game
    private Hand hand;          // The player's current hand (combination of cards)
    private int currentBet;     // Tracks the current bet for the player
    private boolean folded;     // Indicates if the player has folded
    private int numOfWin;       // Tracks the number of wins the player has

    
    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.isInGame = true;
        this.holeCards = new Card[2];
        this.hand = new Hand();
        this.numOfWin = 0;
        this.folded = false;
    }

    public Hand getHand() {
        return hand;
    }
    
    public void setHand(Hand hand) {
        this.hand = hand;
    }
    
    public void setCurrentBet(int bet) {
        this.currentBet = bet;
    }

    public int getCurrentBet() {
        return currentBet;
    }
    
    // Reduce the player's chips by a certain amount
    public void reduceFromChips(int amount) {
        if (amount > chips) {
            amount = chips;
        }
        chips -= amount;
    }
    
     // Add a certain amount to the player's chips
    public void addToChips(int amount) {
        chips += amount;
    }
    
    // Player folds, indicating they are no longer in the game
    public void fold() {
        this.setFolded(true);
        this.setIsInGame(false);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getChips() {
        return chips;
    }
    
   
    
    public Card[] getHoleCards() {
        return holeCards;
    }
    
    public void setHoleCards(Card[] holeCards) {
        this.holeCards = holeCards;
    }
    
    // Add a card to the player's hand and hole cards
    public void addCardToHand(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Cannot add null card");
        }

        if (this.holeCards[0] == null) {
            this.holeCards[0] = card;
        } else if (this.holeCards[1] == null) {
            this.holeCards[1] = card;
        } else {
            throw new IllegalStateException("Cannot add more than two hole cards");
        }

        this.hand.addCard(card);
    }
//        public void addCardToHand(Card card) {
//    if (this.holeCards[0] == null) {
//            this.holeCards[0] = card;
//        } else if (this.holeCards[1] == null) {
//            this.holeCards[1] = card;
//        }
//        this.hand.addCard(card);
//}


   
    public boolean getIsInGame() {
        return isInGame;
    }
    
    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }
    
    @Override
    public String toString() {
        
        return ("Player " +this.name+ " has a chips of "+this.chips);
        
    }
    
    // Player bets a certain amount of chips
    public void bet(int amount) {
        if (amount > chips) {
            throw new IllegalArgumentException("Insufficient chips");
        }
        chips -= amount;
        currentBet += amount;
    }
    
    // Player raises the bet by a certain amount
    public void raise(int amount) {
        bet(amount);
    }
    
    
    // Player checks, meaning they do not bet but stay in the game
    public void check() {
        System.out.println(name+" checks.");
    }
    
    // Clear the player's hand and hole cards
    public void clearHand() {
        this.holeCards = new Card[2];
        if (this.hand != null) {
            this.hand.clear();
        } else {
            this.hand = new Hand();
        }
    }
//    public void clearHand() {
//        this.holeCards = new Card[2]; // Clear hole cards
//        this.hand.clear(); //clear the hand
//    }

    // Player calls, matching the current bet
    public void call(int Amount) {
      bet(Amount);
    }

    /**
     * @return the folded
     */
    public boolean isFolded() {
        return folded;
    }

    /**
     * @param folded the folded to set
     */
    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    /**
     * @return the numOfWin
     */
    public int getNumOfWin() {
        return numOfWin;
    }

    /**
     * @param numOfWin the numOfWin to set
     */
    public void setNumOfWin(int numOfWin) {
        this.numOfWin = numOfWin;
    }
    
    // Increment the number of wins by one to be displayed in the file
    public void addNumOfWin() {
        this.numOfWin++;
    }
}
