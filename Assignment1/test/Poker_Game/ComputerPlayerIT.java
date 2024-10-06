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
public class ComputerPlayerIT {
    
    private ComputerPlayer computerPlayer;
    private Card card1;
    private Card card2;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Setting up ComputerPlayerTest class...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Tearing down ComputerPlayerTest class...");
    }

    @Before
    public void setUp() {
        System.out.println("Setting up a new test...");
        computerPlayer = new ComputerPlayer("TestComputer", 1000);
        card1 = new Card(10, "Hearts", new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        card2 = new Card(11, "Spades", new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down the test...");
    }

    @Test
    public void testComputerPlayerInitialization() {
        System.out.println("\n\nRunning testComputerPlayerInitialization...");
        try {
            assertEquals("TestComputer", computerPlayer.getName());
            assertEquals(1000, computerPlayer.getChips());
            assertTrue(computerPlayer.getIsInGame());
            assertFalse(computerPlayer.isFolded());
            assertEquals(0, computerPlayer.getNumOfWin());
            System.out.println("testComputerPlayerInitialization PASSED");
        } catch (AssertionError e) {
            System.out.println("testComputerPlayerInitialization FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testMakeDecision() {
        System.out.println("\n\nRunning testMakeDecision...");
        try {
            int initialChips = computerPlayer.getChips();
            boolean decisionMade = false;
            
            // Run makeDecision multiple times to cover all possible outcomes
            for (int i = 0; i < 100; i++) {
                int chipsBefore = computerPlayer.getChips();
                boolean inGameBefore = computerPlayer.getIsInGame();
                
                computerPlayer.makeDecision();
                
                int chipsAfter = computerPlayer.getChips();
                boolean inGameAfter = computerPlayer.getIsInGame();
                
                if (chipsAfter < chipsBefore || !inGameAfter) {
                    decisionMade = true;
                    break;
                }
            }
            
            assertTrue("ComputerPlayer should have made at least one decision", decisionMade);
            System.out.println("testMakeDecision PASSED");
        } catch (AssertionError e) {
            System.out.println("testMakeDecision FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAddCardToHand() {
        System.out.println("\n\nRunning testAddCardToHand...");
        try {
            computerPlayer.addCardToHand(card1);
            computerPlayer.addCardToHand(card2);
            assertEquals(2, computerPlayer.getHoleCards().length);
            assertEquals(card1, computerPlayer.getHoleCards()[0]);
            assertEquals(card2, computerPlayer.getHoleCards()[1]);
            assertEquals(2, computerPlayer.getHand().getCards().size());
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
            computerPlayer.addCardToHand(card1);
            computerPlayer.addCardToHand(card2);
            computerPlayer.clearHand();
            assertNull(computerPlayer.getHoleCards()[0]);
            assertNull(computerPlayer.getHoleCards()[1]);
            assertTrue(computerPlayer.getHand().getCards().isEmpty());
            System.out.println("testClearHand PASSED");
        } catch (AssertionError e) {
            System.out.println("testClearHand FAILED: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testFold() {
        System.out.println("\n\nRunning testFold...");
        try {
            computerPlayer.fold();
            assertTrue(computerPlayer.isFolded());
            assertFalse(computerPlayer.getIsInGame());
            System.out.println("testFold PASSED");
        } catch (AssertionError e) {
            System.out.println("testFold FAILED: " + e.getMessage());
            throw e;
        }
    }
    
}
