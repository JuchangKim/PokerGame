/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Poker_Game;

import java.awt.image.BufferedImage;
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
public class PlayerIT {
    
    private Player player;
    private Card card1;
    private Card card2;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Setting up PlayerIT test class...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Tearing down PlayerIT test class...");
    }

    @Before
    public void setUp() {
        System.out.println("\nSetting up a new test...");
        player = new Player("TestPlayer", 1000);
        card1 = new Card(10, "Hearts", new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        card2 = new Card(11, "Spades", new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down the test...");
    }

    @Test
    public void testPlayerInitialization() {
        System.out.println("\n\nRunning testPlayerInitialization...");
        try {
            assertEquals("TestPlayer", player.getName());
            assertEquals(1000, player.getChips());
            assertTrue(player.getIsInGame());
            assertFalse(player.isFolded());
            assertEquals(0, player.getNumOfWin());
            System.out.println("testPlayerInitialization PASSED");
        } catch (AssertionError e) {
            System.out.println("testPlayerInitialization FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAddCardToHand() {
        System.out.println("\n\nRunning testAddCardToHand...");
        try {
            player.addCardToHand(card1);
            player.addCardToHand(card2);
            assertEquals(2, player.getHoleCards().length);
            assertEquals(card1, player.getHoleCards()[0]);
            assertEquals(card2, player.getHoleCards()[1]);
            assertEquals(2, player.getHand().getCards().size());
            System.out.println("testAddCardToHand PASSED");
        } catch (AssertionError e) {
            System.out.println("testAddCardToHand FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testClearHand() {
        System.out.println("\n\nRunning testClearHand...");
        try {
            player.addCardToHand(card1);
            player.addCardToHand(card2);
            player.clearHand();
            assertNull(player.getHoleCards()[0]);
            assertNull(player.getHoleCards()[1]);
            assertTrue(player.getHand().getCards().isEmpty());
            System.out.println("testClearHand PASSED");
        } catch (AssertionError e) {
            System.out.println("testClearHand FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testBet() {
        System.out.println("\n\nRunning testBet...");
        try {
            player.bet(500);
            assertEquals(500, player.getChips());
            assertEquals(500, player.getCurrentBet());
            try {
                player.bet(600);
                fail("Expected IllegalArgumentException was not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
            System.out.println("testBet PASSED");
        } catch (AssertionError e) {
            System.out.println("testBet FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testFold() {
        System.out.println("\n\nRunning testFold...");
        try {
            player.fold();
            assertTrue(player.isFolded());
            assertFalse(player.getIsInGame());
            System.out.println("testFold PASSED");
        } catch (AssertionError e) {
            System.out.println("testFold FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAddNumOfWin() {
        System.out.println("\n\nRunning testAddNumOfWin...");
        try {
            player.addNumOfWin();
            assertEquals(1, player.getNumOfWin());
            System.out.println("testAddNumOfWin PASSED");
        } catch (AssertionError e) {
            System.out.println("testAddNumOfWin FAILED: " + e.getMessage());
            throw e;
        }
    }
    
}
