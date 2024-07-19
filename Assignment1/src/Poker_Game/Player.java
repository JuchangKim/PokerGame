/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import java.util.Arrays;

/**
 *
 * @author user
 */
public class Player {
    
    private String name;
    private int balance;
    private Card[] holeCards = new Card[2];
    private boolean isInGame;

    public Player(String name, int balance, Card[] holeCards) {
        this.name = name;
        this.balance = balance;
        this.holeCards = holeCards;
        this.isInGame = true;
    }

    
    public String getName() {
        return name;
    }

    
    public int getBalance() {
        return balance;
    }
    
    public void addToBalance(int additionAmount) {
        this.balance += additionAmount;
    }
    
    public void reduceFromBalance(int reduceAmount) {
        this.balance -= reduceAmount;
    }

    
    public Card[] getHoleCards() {
        return holeCards;
    }
    
    public void setHoleCards(Card[] holeCards) {
        this.holeCards = holeCards;
    }

   
    public boolean getIsInGame() {
        return isInGame;
    }
    
    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }
    
    public String toString() {
        
        return ("Player " +this.name+ " has a balance of "+this.balance+ ".\nHole cards " +Arrays.toString(this.holeCards)+ ".\nIs in the Game: "+this.isInGame);
        
    }
    
    
    public static void main(String[] args) {
        
        Card[] holeCards = {new Card(1, "Hearts"), new Card(2, "Spades")};
        Player p1 = new Player("Simon", 100, holeCards);
        System.out.println(p1.getName());
        System.out.println(p1.getBalance());
        p1.addToBalance(2);
        System.out.println(p1.getBalance());
        p1.reduceFromBalance(1);
        System.out.println(p1.getBalance());
        System.out.println(Arrays.toString(p1.getHoleCards()));
        System.out.println(p1.toString());
        Card[] holeCards2 = {new Card(12, "Clubs"), new Card(7, "Diamonds")};
        p1.setHoleCards(holeCards2);
        System.out.println(Arrays.toString(p1.getHoleCards()));
        
    }
    
    
}
