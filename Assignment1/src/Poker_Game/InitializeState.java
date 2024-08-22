/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public class InitializeState implements GameStateAction {
    @Override
    public void play(PokerGame game) throws InterruptedException {
        game.getDeck().reset();
        game.getDeck().shuffleDeck();
        game.getGameState().getCommunityCards().clear();
        game.getGameState().setPot(0);
        game.getGameState().setCurrentBet(10);
        

        for (Player player : game.getGameState().getPlayers()) {
            player.clearHand();
            player.addCardToHand(game.getDeck().getCard());
            player.addCardToHand(game.getDeck().getCard());
            Thread.sleep(1000); // Introduce delay
        }

        System.out.println("Starting new round...");
        for (Player player : game.getGameState().getPlayers()) {
            System.out.println(player);
            Thread.sleep(1000); // Introduce delay
        }
    }
}
