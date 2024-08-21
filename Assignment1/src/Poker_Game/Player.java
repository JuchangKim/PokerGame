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
    
    private String name;
    private int chips;
    private Card[] holeCards = new Card[2];
    private boolean isInGame;
    private Hand hand;
    private int currentBet; // Track the current bet for the player
    private boolean folded;
    
    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.isInGame = true;
        this.holeCards = new Card[2];
        this.hand = new Hand();
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

    public void reduceFromChips(int amount) {
        if (amount > chips) {
            amount = chips;
        }
        chips -= amount;
    }

    public void addToChips(int amount) {
        chips += amount;
    }

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
    
    public void addCardToHand(Card card) {
    if (this.holeCards[0] == null) {
            this.holeCards[0] = card;
        } else if (this.holeCards[1] == null) {
            this.holeCards[1] = card;
        }
        this.hand.addCard(card);
}


   
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
    
    public void bet(int amount) {
        if (amount > chips) {
            throw new IllegalArgumentException("Insufficient chips");
        }
        chips -= amount;
        currentBet += amount;
    }
    
    public void raise(int amount) {
        bet(amount);
    }
    
    
    
    public void check() {
        System.out.println(name+" checks.");
    }
    

    public void clearHand() {
        this.holeCards = new Card[2]; // Clear hole cards
        this.hand.clear();
    }

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
}
