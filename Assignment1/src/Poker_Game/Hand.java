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
    private List<Card> cards;
    
    public Hand() {
        this.cards = new ArrayList<>();
       
    }
    public Hand(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
        this.cards.sort(Comparator.comparingInt(Card::getValue));
    }

   
    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card)
    {
        cards.add(card);
        cards.sort(Comparator.comparingInt(Card::getValue));
    }
    @Override
    public String toString() {
        return cards.toString();
    }

    public void clear() {
    
        this.cards.clear();
    }
}