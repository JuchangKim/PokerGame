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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokerRules {
    
    // This method evaluates the rank of a given hand and assigns a score
    public static int evaluateHand(Hand hand) {
       
        List<Card> cards = hand.getCards();
        // Giving higher rank more points to determine winner
        if(isRoyalFlush(cards))
        {
            hand.setHandRank("Royal Flush");
            return 10;
        }
        else if(isStraightFlush(cards))
        {
            hand.setHandRank("Straight Flush");
            return 9;
        }
        else if(isFourOfAKind(cards))
        {
            hand.setHandRank("Four Of A Kind");
            return 8;
        }
        else if(isFullHouse(cards))
        {
            hand.setHandRank("Full House");
            return 7;
        }
        else if(isFlush(cards))
        {
            hand.setHandRank("Flush");
            return 6;
        }
        else if(isStraight(cards))
        {
            hand.setHandRank("Straight");
            return 5;
        }
        else if(isThreeOfAKind(cards))
        {
            hand.setHandRank("Three Of A Kind");
            return 4;
        }
        else if(isTwoPair(cards))
        {
            hand.setHandRank("Two Pair");
            return 3;
        }
        else if(isOnePair(cards))
        {
            hand.setHandRank("One Pair");
            return 2;
        }
        else
        {
            hand.setHandRank("High Card");
            return 1; // High card
        }
    }

    // This method determines the winner among multiple players based on their hands and the community cards
    public static Player determineWinner(List<Player> players, List<Card> communityCards) {
        Player winner = null;
        int bestPoints = 0;
        Hand bestHand = null;
        
        //Evaluate each player's hand
        for(Player player : players)
        {
            if(player.getIsInGame())
            {
                List<Card> allCards = new ArrayList<>(communityCards);
                allCards.addAll(Arrays.asList(player.getHoleCards()));
                Hand playerHand = new Hand(allCards);
                player.setHand(playerHand);
                
                // Compare the player's hand with the current best hand
                int playerRank = evaluateHand(playerHand);
                if(playerRank > bestPoints || (playerRank == bestPoints && compareHands(playerHand, bestHand) > 0))
                {
                    bestPoints = playerRank;
                    bestHand = playerHand;
                    winner = player;
                }
            }
        }
        return winner;
    }
    
    // Check if the hand is a Royal Flush (the highest possible hand)
    private static boolean isRoyalFlush(List<Card> cards)
    {
        return isStraightFlush(cards) && cards.get(4).getValue() == 14;
    }
    
    // Check if the hand is a Straight Flush (consecutive cards of the same suit)
    private static boolean isStraightFlush(List<Card> cards)
    {
        return isFlush(cards) && isStraight(cards);
    }
    
    // Check if the hand is Four of a Kind (four cards of the same value)
    private static boolean isFourOfAKind(List<Card> cards)
    {
        return hasNOfAKind(cards, 4);
    }
    
    // Check if the hand is a Full House (three of a kind and a pair)
    private static boolean isFullHouse(List<Card> cards)
    {
        return hasNOfAKind(cards, 3) && hasNOfAKind(cards, 2);
    }
    
    // Check if the hand is a Flush (all cards of the same suit)
    private static boolean isFlush(List<Card> cards)
    {
        String suit = cards.get(0).getSuit();
        for(Card card : cards)
        {
            if(!card.getSuit().equals(suit))
            {
                return false;
            }
        }
        return true;
    }
    
    // Check if the hand is a Straight (five consecutive cards of any suit)
    private static boolean isStraight(List<Card> cards)
    {
        for(int i = 0; i < cards.size() - 1; i++)
        {
            if(cards.get(i).getValue() + 1 != cards.get(i + 1).getValue())
            {
                return false;
            }
        }
        return true;
    }
    
    // Check if the hand is Three of a Kind (three cards of the same value)
    private static boolean isThreeOfAKind(List<Card> cards)
    {
        return hasNOfAKind(cards, 3);
    }
    
    // Check if the hand is Two Pair (two different pairs)
    private static boolean isTwoPair(List<Card> cards)
    {
        int pairs = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        for(Card card : cards)
        {
            counts.put(card.getValue(), counts.getOrDefault(card.getValue(), 0) + 1);
        }
        for(int count : counts.values())
        {
            if(count == 2)
            {
                pairs++;
            }
        }
        return pairs == 2;
    }
    
    // Check if the hand is One Pair (two cards of the same value)
    private static boolean isOnePair(List<Card> cards)
    {
        return hasNOfAKind(cards, 2);
    }
    
    // Helper method to check if the hand has a specific number of cards of the same value (e.g., pair, three of a kind, etc.)
    private static boolean hasNOfAKind(List<Card> cards, int n)
    {
        Map<Integer, Integer> counts = new HashMap<>();
        for(Card card : cards)
        {
            counts.put(card.getValue(), counts.getOrDefault(card.getValue(), 0) + 1);
        }
        for(int count : counts.values())
        {
            if(count == n)
            {
                return true;
            }
        }
        return false;
    }
    
    // Helper method to compare two hands based on their values (used when two hands have the same rank)
    private static int compareHands(Hand hand1, Hand hand2)
    {
        List<Card> cards1 = hand1.getCards();
        List<Card> cards2 = hand2.getCards();
        
        for(int i = cards1.size() - 1; i >= 0; i--)
        {
            int compare = Integer.compare(cards1.get(i).getValue(), cards2.get(i).getValue());
            if(compare != 0)
            {
                return compare;
            }
        }
        return 0; //Hands are identical in value
    }
}
