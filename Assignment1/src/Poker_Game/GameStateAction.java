/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public interface GameStateAction {
    
    //Executes specific actions related to the current game state
    void play(PokerGame game) throws InterruptedException;
    
}
