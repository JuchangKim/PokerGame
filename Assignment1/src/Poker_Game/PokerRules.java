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


    public static Player determineWinner(List<Player> players, List<Card> communityCards) {
        Player winner = null;
        int bestPoints = 0;
        Hand bestHand = null;
        
        for(Player player : players)
        {
            if(player.getIsInGame())
            {
                List<Card> allCards = new ArrayList<>(communityCards);
                allCards.addAll(Arrays.asList(player.getHoleCards()));
                Hand playerHand = new Hand(allCards);
                player.setHand(playerHand);
                
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
    
    private static boolean isRoyalFlush(List<Card> cards)
    {
        return isStraightFlush(cards) && cards.get(4).getValue() == 14;
    }
    
    private static boolean isStraightFlush(List<Card> cards)
    {
        return isFlush(cards) && isStraight(cards);
    }
    
    private static boolean isFourOfAKind(List<Card> cards)
    {
        return hasNOfAKind(cards, 4);
    }
    
    private static boolean isFullHouse(List<Card> cards)
    {
        return hasNOfAKind(cards, 3) && hasNOfAKind(cards, 2);
    }
    
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
    private static boolean isThreeOfAKind(List<Card> cards)
    {
        return hasNOfAKind(cards, 3);
    }
    
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
    
    private static boolean isOnePair(List<Card> cards)
    {
        return hasNOfAKind(cards, 2);
    }
    
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
        return 0;
    }
}
