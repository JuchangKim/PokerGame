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

public class Player {
    
    private String name;
    private int chips;
    private Card[] holeCards = new Card[2];
    private boolean isInGame;
    private Hand hand;
    
    //ask 

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.isInGame = true;
        this.holeCards = new Card[2];
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
    
    public void addToChips(int additionAmount) {
        this.chips += additionAmount;
    }
    
    public void reduceFromChips(int reduceAmount) {
        this.chips -= reduceAmount;
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
    } else {
        throw new IllegalStateException("Both hole cards are already set");
    }
    if (this.hand != null) {
        this.hand.addCard(card);
    }
}


   
    public boolean getIsInGame() {
        return isInGame;
    }
    
    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }
    
    @Override
    public String toString() {
        
        return ("Player " +this.name+ " has a chips of "+this.chips+ ".\nHole cards " +Arrays.toString(this.holeCards)+ ".\nIs in the Game: "+this.isInGame);
        
    }
    
    
    public static void main(String[] args) {
        
        Card[] holeCards = {new Card(1, "Hearts"), new Card(2, "Spades")};
        Player p1 = new Player("Simon", 100);
        p1.setHoleCards(holeCards);
        System.out.println(p1.getName());
        System.out.println(p1.getChips());
        p1.addToChips(2);
        System.out.println(p1.getChips());
        p1.reduceFromChips(1);
        System.out.println(p1.getChips());
        System.out.println(Arrays.toString(p1.getHoleCards()));
        System.out.println(p1.toString());
        Card[] holeCards2 = {new Card(12, "Clubs"), new Card(7, "Diamonds")};
        p1.setHoleCards(holeCards2);
        System.out.println(Arrays.toString(p1.getHoleCards()));
        
    }

    public Hand getHand() {
        return hand;
    }
    
    
}
