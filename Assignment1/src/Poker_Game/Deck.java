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

    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {

        reset();
        shuffleDeck();

    }

    public void reset() {

        this.deck.clear();
        //adding a hearts suit
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Hearts"));
        }

        //adding a clubs suit
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Clubs"));
        }

        //adding a spades suit
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Spades"));
        }

        //adding a diamonds suit
        for (int i = 2; i < 15; i++) {
            deck.add(new Card(i, "Diamonds"));
        }

    }

    public void shuffleDeck() {

        ArrayList<Card> tempDeck = new ArrayList<Card>();

        while (this.deck.size() > 0) {
            int randomIndex = ((int) (Math.random() * 100)) % this.deck.size();
            tempDeck.add(this.deck.remove(randomIndex));

        }
        this.deck = tempDeck;
    }

    public Card getNextCard() {

        return this.deck.remove(this.deck.size() - 1);

    }

    public int getRemainingCount() {
        return this.deck.size();
    }

    public static void main(String[] args) {

        Deck sd = new Deck();

        System.out.println(sd.deck.toString());
        System.out.println(sd.getNextCard());
        System.out.println(sd.getNextCard());
        System.out.println(sd.getNextCard());
        System.out.println(sd.getNextCard());
        System.out.println(sd.getRemainingCount());

    }

}
