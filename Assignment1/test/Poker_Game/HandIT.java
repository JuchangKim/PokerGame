/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Poker_Game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author billi
 */
public class HandIT {

    private Hand hand;

    @Before
    public void setUp() {
        hand = new Hand();
    }

    @After
    public void tearDown() {
        hand = null;
    }

    @Test
    public void testEmptyHandConstructor() {
        System.out.println("\n\nRunning test: testEmptyHandConstructor");
        try {
            assertTrue(hand.getCards().isEmpty());
            assertEquals("", hand.getHandRank());
            System.out.println("Test passed: testEmptyHandConstructor");
        } catch (AssertionError e) {
            System.out.println("Test failed: testEmptyHandConstructor");
            throw e;  // Re-throw to let the testing framework handle the failure
        }
    }

    @Test
    public void testHandConstructorWithCards() {
        System.out.println("\n\nRunning test: testHandConstructorWithCards");
        try {
            // Create dummy images for testing purposes
            Image frontImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image frontImage2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

            List<Card> cards = new ArrayList<>();
            cards.add(new Card(14, "Hearts", frontImage1, backImage1)); // Ace with value 14
            cards.add(new Card(13, "Spades", frontImage2, backImage2)); // King with value 13

            Hand hand = new Hand(cards);

            assertEquals(2, hand.getCards().size());
            assertEquals(13, hand.getCards().get(0).getValue()); // Check for King
            assertEquals(14, hand.getCards().get(1).getValue()); // Check for Ace

            System.out.println("Test passed: testHandConstructorWithCards");
        } catch (AssertionError e) {
            System.out.println("Test failed: testHandConstructorWithCards");
            throw e;
        }
    }

    @Test
    public void testAddCard() {
        System.out.println("\n\nRunning test: testAddCard");
        try {
            // Create a card image (dummy image for testing)
            Image frontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

            Card card = new Card(12, "Clubs", frontImage, backImage); // Queen
            hand.addCard(card);

            assertEquals(1, hand.getCards().size());
            assertEquals(card, hand.getCards().get(0));

            System.out.println("Test passed: testAddCard");
        } catch (AssertionError e) {
            System.out.println("Test failed: testAddCard");
            throw e;
        }
    }

    @Test
    public void testAddMultipleCards() {
        System.out.println("\n\nRunning test: testAddMultipleCards");
        try {
            // Create card images (dummy images for testing)
            Image frontImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image frontImage2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image frontImage3 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage3 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

            // Create the cards with the correct values
            hand.addCard(new Card(11, "Diamonds", frontImage1, backImage1)); // Jack
            hand.addCard(new Card(12, "Hearts", frontImage2, backImage2)); // Queen
            hand.addCard(new Card(14, "Spades", frontImage3, backImage3)); // Ace

            assertEquals(3, hand.getCards().size());
            assertEquals(11, hand.getCards().get(0).getValue()); // Jack
            assertEquals(12, hand.getCards().get(1).getValue()); // Queen
            assertEquals(14, hand.getCards().get(2).getValue()); // Ace

            System.out.println("Test passed: testAddMultipleCards");
        } catch (AssertionError e) {
            System.out.println("Test failed: testAddMultipleCards");
            throw e;
        }
    }

    @Test
    public void testClear() {
        System.out.println("\n\nRunning test: testClear");
        try {
            // Create card images (dummy images for testing)
            Image frontImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            hand.addCard(new Card(12, "Clubs", frontImage1, backImage1)); // Queen
            hand.addCard(new Card(14, "Hearts", frontImage1, backImage1)); // Ace

            hand.clear();

            assertTrue(hand.getCards().isEmpty());
            System.out.println("Test passed: testClear");
        } catch (AssertionError e) {
            System.out.println("Test failed: testClear");
            throw e;
        }
    }

    @Test
    public void testSetAndGetHandRank() {
        System.out.println("\n\nRunning test: testSetAndGetHandRank");
        try {
            hand.setHandRank("Pair");
            assertEquals("Pair", hand.getHandRank());

            hand.setHandRank("Flush");
            assertEquals("Flush", hand.getHandRank());

            System.out.println("Test passed: testSetAndGetHandRank");
        } catch (AssertionError e) {
            System.out.println("Test failed: testSetAndGetHandRank");
            throw e;
        }
    }

    @Test
    public void testToString() {
        System.out.println("\n\nRunning test: testToString");
        try {
            // Create card images (dummy images for testing)
            Image frontImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Image backImage1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

            hand.addCard(new Card(14, "Hearts", frontImage1, backImage1)); // Ace
            hand.addCard(new Card(13, "Spades", frontImage1, backImage1)); // King

            // The expected output format must match the updated toString method of Card
            String expected = "[King of Spades value is: 13, Ace of Hearts value is: 14]";
            assertEquals(expected, hand.toString());

            System.out.println("Test passed: testToString");
        } catch (AssertionError e) {
            System.out.println("Test failed: testToString");
            throw e;
        }
    }
}

