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
public class DetermineWinnerState implements GameStateAction {
    @Override
    public void play(PokerGame game) throws InterruptedException {
        System.out.println("Determining winner...\n");
        Player winner = PokerRules.determineWinner(game.getGameState().getPlayers(), game.getGameState().getCommunityCards());
        
        for(Player p : game.getGameState().getPlayers())
            {
                if(p.getIsInGame())
                {
                    List<Card> allCards = new ArrayList<>(game.getGameState().getCommunityCards());
                    allCards.addAll(Arrays.asList(p.getHoleCards()));
                    Hand playerHand = new Hand(allCards);
                    p.setHand(playerHand);
                    PokerRules.evaluateHand(playerHand);
                    System.out.println(p.getName() + " has " + p.getHand() + " : " + playerHand.getHandRank() + "\n");
                    Thread.sleep(1000);
                }
            }
        System.out.println("The winner is " + winner.getName() + "!\n");
        game.getGameState().setWinner(winner);
        if (winner != null) {
            winner.addToChips(game.getBettingSystem().getPot()); // Winner takes all
            game.getBettingSystem().resetPot(); // Clear the pot for the next game
            for(Player p : game.getGameState().getPlayers())
            {
                System.out.println(p.getName() + " has " + p.getChips() + " chips \n");
            }
        } else {
            System.out.println("No winner this round.");
        }
    }
}