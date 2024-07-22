/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

/**
 *
 * @author user
 */
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private List<Player> players;
    private int currentPlayerIndex;

    public GameState(List<Player> players, int currentPlayerIndex) {
        this.players = players;
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}
