/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Poker_Game;

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
public class DeckIT {
    
    private Deck deck;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("\nSetting up the test class...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("\nTearing down the test class...");
    }

    @Before
    public void setUp() {
        System.out.println("\nCreating a new Deck instance for testing...");
        deck = new Deck(); // Initialize a new deck before each test
    }

    @After
    public void tearDown() {
        System.out.println("\nCleaning up after test...");
        deck = null; // Clean up the deck instance after each test
    }

    @Test
    public void testDeckInitialization() {
        System.out.println("\n\nTest: Checking deck initialization...");
        int expectedCount = 52; // There should be 52 cards in a standard deck
        assertEquals("Deck should have 52 cards after initialization.", expectedCount, deck.getRemainingCount());
        System.out.println("Test passed: Deck initialized correctly with " + expectedCount + " cards.");
    }

    @Test
    public void testShuffleDeck() {
        System.out.println("\n\nTest: Checking shuffle functionality...");
        Card firstCardBeforeShuffle = deck.getCard(); // Get the first card
        deck.shuffleDeck(); // Shuffle the deck
        Card firstCardAfterShuffle = deck.getCard(); // Get the new first card
        
        // Check if the first card has changed after shuffling
        assertNotEquals("Shuffle should change the order of cards.", firstCardBeforeShuffle, firstCardAfterShuffle);
        System.out.println("Test passed: Deck shuffled successfully.");
    }

    @Test
    public void testGetCard() {
        System.out.println("\n\nTest: Checking getCard functionality...");
        int initialCount = deck.getRemainingCount(); // Count before getting a card
        Card card = deck.getCard(); // Get a card
        int newCount = deck.getRemainingCount(); // Count after getting a card
        
        // Ensure that getting a card decreases the count by one
        assertEquals("Getting a card should decrease the count by 1.", initialCount - 1, newCount);
        System.out.println("Test passed: Card retrieved successfully. Remaining cards: " + newCount);
    }

    @Test
    public void testRemainingCount() {
        System.out.println("\n\nTest: Checking remaining count of cards...");
        int count = deck.getRemainingCount();
        
        // Check that the remaining count is correct after initialization
        assertEquals("Remaining count should be 52.", 52, count);
        
        // Get a card and check remaining count
        deck.getCard();
        assertEquals("Remaining count should be 51 after getting one card.", 51, deck.getRemainingCount());
        
        System.out.println("Test passed: Remaining count is accurate.");
    }
    
}
