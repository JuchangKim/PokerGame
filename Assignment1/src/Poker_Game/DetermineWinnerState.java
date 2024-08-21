/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public class DetermineWinnerState implements GameStateAction {
    @Override
    public void play(PokerGame game) {
        System.out.println("Determining winner...");
        Player winner = PokerRules.determineWinner(game.getGameState().getPlayers(), game.getGameState().getCommunityCards());
        System.out.println("The winner is " + winner.getName() + "!");
        if (winner != null) {
            winner.addToChips(game.getGameState().getPot()); // Winner takes all
            game.getBettingSystem().clearPot(); // Clear the pot for the next game
        } else {
            System.out.println("No winner this round.");
        }
    }
}