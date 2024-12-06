/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Poker_Game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
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
public class CardIT {
    
    private Card cardRed;
    private Card cardBlack;
    private Image dummyImage;
    private Image dummyImageBack;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Setting up the test class resources...\n");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Cleaning up test class resources...\n");
    }

    @Before
    public void setUp() {
        System.out.println("Setting up before each test...\n");
        dummyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); 
        dummyImageBack = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        cardRed = new Card(14, "Hearts", dummyImage, dummyImageBack);  
        cardBlack = new Card(11, "Spades", dummyImage, dummyImageBack);  
    }

    @After
    public void tearDown() {
        System.out.println("Cleaning up after each test...\n\n");
    }

    @Test
    public void testGetValue() {
        try {
            System.out.println("Testing card value retrieval...");
            int redCardValue = cardRed.getValue();
            System.out.println("Red card value: " + redCardValue);
            assertEquals("Card value should be 14 for Ace of Hearts", 14, redCardValue);

            int blackCardValue = cardBlack.getValue();
            System.out.println("Black card value: " + blackCardValue);
            assertEquals("Card value should be 11 for Jack of Spades", 11, blackCardValue);

            System.out.println("testGetValue PASSED\n\n");
        } catch (AssertionError e) {
            System.out.println("testGetValue FAILED\n\n: " + e.getMessage());
            throw e; // rethrow to let JUnit handle failure
        }
    }

    @Test
    public void testGetSuit() {
        try {
            System.out.println("Testing card suit retrieval...");
            String redCardSuit = cardRed.getSuit();
            System.out.println("Red card suit: " + redCardSuit);
            assertEquals("Card suit should be Hearts", "Hearts", redCardSuit);

            String blackCardSuit = cardBlack.getSuit();
            System.out.println("Black card suit: " + blackCardSuit);
            assertEquals("Card suit should be Spades", "Spades", blackCardSuit);

            System.out.println("testGetSuit PASSED\n\n");
        } catch (AssertionError e) {
            System.out.println("testGetSuit FAILED\n\n: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetColour() {
        try {
            System.out.println("Testing card color assignment based on suit...");
            String redCardColour = cardRed.getColour();
            System.out.println("Red card color: " + redCardColour);
            assertEquals("Card color should be Red for Hearts", "Red", redCardColour);

            String blackCardColour = cardBlack.getColour();
            System.out.println("Black card color: " + blackCardColour);
            assertEquals("Card color should be Black for Spades", "Black", blackCardColour);

            System.out.println("testGetColour PASSED\n\n");
        } catch (AssertionError e) {
            System.out.println("testGetColour FAILED\n\n: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testConvertValueToName() {
        try {
            System.out.println("Testing card value conversion to name...");
            String redCardName = cardRed.convertValueToName();
            System.out.println("Red card name: " + redCardName);
            assertEquals("Ace should be converted to 'Ace'", "Ace", redCardName);

            String blackCardName = cardBlack.convertValueToName();
            System.out.println("Black card name: " + blackCardName);
            assertEquals("Jack should be converted to 'Jack'", "Jack", blackCardName);

            System.out.println("testConvertValueToName PASSED\n\n");
        } catch (AssertionError e) {
            System.out.println("testConvertValueToName FAILED\n\n: " + e.getMessage());
            throw e;
        }
    }

//    @Test
//    public void testToString() {
//        try {
//            System.out.println("Testing toString method of card...");
//            String redCardString = cardRed.toString();
//            System.out.println("Red card string: " + redCardString);
//            assertEquals("toString should return 'Ace of Hearts'", "Ace of Hearts", redCardString);
//
//            String blackCardString = cardBlack.toString();
//            System.out.println("Black card string: " + blackCardString);
//            assertEquals("toString should return 'Jack of Spades'", "Jack of Spades", blackCardString);
//
//            System.out.println("testToString PASSED\n\n");
//        } catch (AssertionError e) {
//            System.out.println("testToString FAILED\n\n: " + e.getMessage());
//            throw e;
//        }
//    }
    
    @Test
public void testToString() {
    try {
        System.out.println("Testing toString method of card...");
        String redCardString = cardRed.toString();
        System.out.println("Red card string: " + redCardString);
        assertEquals("toString should return 'Ace of Hearts'", "Ace of Hearts", redCardString);

        String blackCardString = cardBlack.toString();
        System.out.println("Black card string: " + blackCardString);
        assertEquals("toString should return 'Jack of Spades'", "Jack of Spades", blackCardString);

        System.out.println("testToString PASSED\n\n");
    } catch (AssertionError e) {
        System.out.println("testToString FAILED\n\n: " + e.getMessage());
        throw e;
    }
}


    @Test
    public void testGetFrontImageIcon() {
        try {
            System.out.println("Testing retrieval of the front image icon...");
            ImageIcon frontImageIcon = cardRed.getFrontImageIcon();
            System.out.println("Checking if the front image icon is not null...");
            assertNotNull("Card front image icon should not be null", frontImageIcon);

            System.out.println("Validating that the front image matches the provided dummy image...");
            assertEquals("Front image icon should be the one provided during creation", dummyImage, frontImageIcon.getImage());

            System.out.println("testGetFrontImageIcon PASSED\n\n");
        } catch (AssertionError e) {
            System.out.println("testGetFrontImageIcon FAILED\n\n: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testGetBackImageIcon() {
        try {
            System.out.println("Testing retrieval of the back image icon...");
            ImageIcon backImageIcon = cardRed.getBackImageIcon();
            System.out.println("Checking if the back image icon is not null...");
            assertNotNull("Card back image icon should not be null", backImageIcon);

            System.out.println("Validating that the back image matches the provided dummy image...");
            assertEquals("Back image icon should be the one provided during creation", dummyImageBack, backImageIcon.getImage());

            System.out.println("testGetBackImageIcon PASSED\n\n");
        } catch (AssertionError e) {
            System.out.println("testGetBackImageIcon FAILED\n\n: " + e.getMessage());
            throw e;
        }
    }
}
    


