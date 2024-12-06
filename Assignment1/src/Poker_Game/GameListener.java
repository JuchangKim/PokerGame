/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
public interface GameListener {
    void onGameStateUpdated();
    void onPlayerTurn(Player player);  // Added method for player turn notification
}
