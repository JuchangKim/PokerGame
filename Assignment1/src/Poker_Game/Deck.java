 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Deck {
    
    //Deck of cards is private to ensure changes are mafe in a controlled manner
    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {

        this.reset();
        this.shuffleDeck();

    }
    
    // Method to reset the deck by clearing it and adding all 52 cards
    public void reset() {
        
        //clear the current deck
        this.deck.clear();
        
        //adding a hearts suit with values from 2 to 14. 14 = Ace
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Hearts"));
        }

        //adding a clubs suit with values from 2 to 14. 14 = Ace
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Clubs"));
        }

        //adding a spades suit with values from 2 to 14. 14 = Ace
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Spades"));
        }

        //adding a diamonds suit with values from 2 to 14. 14 = Ace
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Diamonds"));
        }

    }
    
    // Method to shuffle the deck by randomizing the order of the cards
    public void shuffleDeck() {
        
        //create a temporary deck to hold the shuffled cards
        ArrayList<Card> tempDeck = new ArrayList<Card>();

        while (this.deck.size() > 0) {
            // Generate a random index within the bounds of the current deck size
            int randomIndex = ((int) (Math.random() * 100)) % this.deck.size();
            // Remove the card at the random index from the original deck and add it to the temp deck
            tempDeck.add(this.deck.remove(randomIndex));

        }
        // Assign the shuffled cards back to the original deck
        this.deck = tempDeck;
        this.deck = tempDeck;
        
    }

    public Card getCard() {
        // Remove and return the last card in the deck (top card)
        return this.deck.remove(this.deck.size() - 1);

    }
    
    // Method to get the number of remaining cards in the deck
    public int getRemainingCount() {
        return this.deck.size();
    }

    
}
