/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class Card {
    
    // The variables value, suit, and color of the card are private to ensure
    // changes are made in a controlled manner
    
    private int value;
    private String suit;
    private String colour;
    private ImageIcon card_front;
    private ImageIcon card_back;
    

    // Constructor to initialize the card with a value and suit
    public Card(int value, String suit, Image frontImage, Image backImage) {
    if (value < 2 || value > 14) {
        throw new IllegalArgumentException("Invalid card value");
    }
    if (suit == null || suit.isEmpty()) {
        throw new IllegalArgumentException("Suit cannot be null or empty");
    }
    if (frontImage == null || backImage == null) {
        throw new IllegalArgumentException("Card images cannot be null");
    }

    this.value = value;
    this.suit = suit;
    this.card_front = new ImageIcon(frontImage);
    this.card_back = new ImageIcon(backImage);

    if (this.suit.equals("Hearts") || this.suit.equals("Diamonds")) {
        this.colour = "Red";
    } else if (this.suit.equals("Spades") || this.suit.equals("Clubs")) {
        this.colour = "Black";
    } else {
        throw new IllegalArgumentException("Invalid suit");
    }
}

    
//    public Card(int value, String suit, Image frontImage, Image backImage) {
//        if (value < 2 || value > 14) {
//            throw new IllegalArgumentException("Invalid card value");
//        }
//        if (suit == null || suit.isEmpty()) {
//            throw new IllegalArgumentException("Suit cannot be null or empty");
//        }
//        if (frontImage == null || backImage == null) {
//            throw new IllegalArgumentException("Card images cannot be null");
//        }
//
//        this.value = value;
//        this.suit = suit;
//        this.card_front = new ImageIcon(frontImage);
//        this.card_back = new ImageIcon(backImage);
//
//        if (this.suit.equals("Hearts") || this.suit.equals("Diamonds")) {
//            this.colour = "Red";
//        } else if (this.suit.equals("Spades") || this.suit.equals("Clubs")) {
//            this.colour = "Black";
//        } else {
//            throw new IllegalArgumentException("Invalid suit");
//        }
//    }
//    public Card(int value, String suit, Image image, Image image1) {
//        // Assign the value and suit to the card
//        this.value = value;
//        this.suit = suit;
//        this.card_front = new ImageIcon(image);
//        this.card_back = new ImageIcon(image1);
//
//        // Determine the color of the card based on its suit
//        if(this.suit.equals("Hearts") || this.suit.equals("Diamonds")) {
//            this.colour = "Red";
//        }
//        if(this.suit.equals("Spades") || this.suit.equals("Clubs")) {
//            this.colour = "Black";
//        }
//        
//    }

       
    public ImageIcon getFrontImageIcon() {
        return card_front;
    }

    public ImageIcon getBackImageIcon() {
        return card_back;
    }

    
    
    // Getter method for the card's value
    public int getValue() {
        return value;
    }

    // Getter method for the card's suit
    public String getSuit() {
        return suit;
    }

    // Getter method for the card's color
    public String getColour() {
        return colour;
    }
    
    // Method to convert the card's numeric value to its corresponding name
    public String convertValueToName() {
        switch(this.value) {
            
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "Five";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
            case 10: return "Ten";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            case 14: return "Ace";  // Ace can also be high (e.g., in some poker games)
            default: return "Value not recognised";  // Fallback if the value is invalid
        }
    }
    
    // Overriding the toString method to provide a string representation of the card
    
    @Override
    public String toString() {
        // Returns a string with the card's name and suit (e.g., "Ace of Spades")
       return (convertValueToName() + " of " + this.suit);
        //return (convertValueToName() + " of " + this.suit + " value is: " + this.value);
    }
    
}
