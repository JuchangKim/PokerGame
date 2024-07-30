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
    
    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.isInGame = true;
        this.holeCards = new Card[2];
    }

    public Hand getHand() {
        return hand;
    }
    
    public void setHand(Hand hand) {
        this.hand = hand;
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
    
    public void bet(int amount) {
        if(amount > 0 && amount <= chips) {
            reduceFromChips(amount);
            System.out.println(name + " bets" +amount+ " chips.");
        }
        else {
            System.out.println(name+" cannot bet that amount due to insufficient chips");
        }
    }
    
    public void raise(int amount) {
        if(amount > 0 && amount <= chips) {
            reduceFromChips(amount);
            System.out.println(name + " raises by" +amount+ " chips.");
        }
        else {
            System.out.println(name+" cannot raise that amount due to insufficient chips");
        }
    }
    
    public void fold() {
        setIsInGame(false);
        System.out.println(name+" folds.");
    }
    
    public void check() {
        System.out.println(name+" checks.");
    }
    
    public void makeDecision() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(", it's your turn. You have " + chips + " chips.");
        System.out.println("Choose an action: \n1: Bet\n2: Raise\n3: Fold\n4:Check");
        
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter the amount to bet: ");
                int betAmount = scanner.nextInt();
                bet(betAmount);
                break;
            case 2:
                System.out.println("Enter the amount to raise: ");
                int raiseAmount = scanner.nextInt();
                raise(raiseAmount);
                break;
            case 3:
                fold();
                break;
            case 4:
                check();
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                makeDecision(); // Recurse until a valid choice is made
                break;
        }
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

    
    
}
