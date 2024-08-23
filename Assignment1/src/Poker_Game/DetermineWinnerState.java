/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author user
 */
// The DetermineWinnerState class handles the logic for determining the winner of a poker game round.
// It evaluates each player's hand, determines the best hand, and awards the pot to the winner.

public class DetermineWinnerState implements GameStateAction {
    @Override
    public void play(PokerGame game) throws InterruptedException {
        
        System.out.println("Determining winner...\n");
        
         // Use PokerRules to determine the winner based on the players' hands and community cards.
        Player winner = PokerRules.determineWinner(game.getGameState().getPlayers(), game.getGameState().getCommunityCards());
        
        // Evaluate each player's hand and print the result
        for(Player p : game.getGameState().getPlayers())
            {
                if(p.getIsInGame())  // Only consider players who are still in the game
                {
                    // Combine the player's hole cards with the community cards.
                    List<Card> allCards = new ArrayList<>(game.getGameState().getCommunityCards());
                    allCards.addAll(Arrays.asList(p.getHoleCards()));
                    
                    // Create a new Hand object for the player using all their available cards
                    Hand playerHand = new Hand(allCards);
                    p.setHand(playerHand);
                    
                    // Evaluate the player's hand to determine its rank.
                    PokerRules.evaluateHand(playerHand);
                    
                    // Print out the player's hand and its rank.
                    System.out.println(p.getName() + " has " + p.getHand() + " : " + playerHand.getHandRank() + "\n");
                    Thread.sleep(1000); //Deal of 1 ssecond between display of players hands
                }
            }
        
        // Announce the winner of the round.
        System.out.println("The winner is " + winner.getName() + "!\n");
        
        // Set the winner in the game's state.
        game.getGameState().setWinner(winner);
        
        // Increment the winner's number of wins.
        winner.addNumOfWin();
        
        // If there is a winner, award them the pot and reset the pot for the next round
        if (winner != null) {
            winner.addToChips(game.getBettingSystem().getPot()); // Winner takes all
            game.getBettingSystem().resetPot(); // Clear the pot for the next game
            
            // Print out each player's chip count after the round
            for(Player p : game.getGameState().getPlayers())
            {
                System.out.println(p.getName() + " has " + p.getChips() + " chips \n");
            }
        } else {
            
            // If there is no winner, announce that no one won this round
            System.out.println("No winner this round.");
        }
    }
}
