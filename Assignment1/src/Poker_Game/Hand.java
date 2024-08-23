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
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class Hand {
    // Private field for the list of cards in the hand
    private List<Card> cards;
    // Private field for the rank of the hand (e.g., "Pair", "Flush", etc.)
    private String handRank;
    
    // Default constructor that initializes an empty hand with no rank
    public Hand() {
        this.cards = new ArrayList<>();
        this.handRank = "";
    }
    
    // Constructor that initializes the hand with a given list of cards
    public Hand(List<Card> cards) {
        // Create a new list of cards and sort them by their value
        this.cards = new ArrayList<>(cards);
        this.cards.sort(Comparator.comparingInt(Card::getValue));
        this.handRank = "";
    }

    // Method to get the list of cards in the hand
    public List<Card> getCards() {
        return cards;
    }

    // Method to add a card to the hand and keep the cards sorted by value
    public void addCard(Card card) {
        cards.add(card);
        cards.sort(Comparator.comparingInt(Card::getValue));
    }
    
    // Override the toString method to return a string representation of the hand
    @Override
    public String toString() {
        return cards.toString();
    }

    // Method to clear all cards from the hand
    public void clear() {
        this.cards.clear();
    }
    
    // Method to get the rank of the hand (e.g., "Pair", "Straight")
    public String getHandRank() {
        return handRank;
    }

    // Method to set the rank of the hand
    public void setHandRank(String s) {
        this.handRank = s;
    }
}

