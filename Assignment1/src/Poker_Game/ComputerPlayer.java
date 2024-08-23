/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Poker_Game;

import java.util.Random;

/**
 *
 * @author billi
 */
public class ComputerPlayer extends Player {
    
    private static final Random random = new Random();
    
    public ComputerPlayer(String name, int chips) {
        super(name, chips);
    }
    
    //method for making random decisions for betting
    
    public void makeDecision() {
        int decision = random.nextInt(4); // Randomly choose an action 
        switch (decision) {
            case 0:
                // Bet a random amount between 1 and half of the player's chips
                int betAmount = random.nextInt(getChips() / 2) + 1; 
                bet(betAmount);
                System.out.println(getName() + " bets "+betAmount+ " chips");
                break;
            case 1:
                // Raise by a random amount between 1 and half of the player's chips
                int raiseAmount = random.nextInt(getChips() / 2) + 1;
                raise(raiseAmount);
                System.out.println(getName() + " raises by "+raiseAmount+ " chips");
                break;
            case 2:
                // Fold the hand and set the player as out of the game
                fold();
                setIsInGame(false);
                System.out.println(getName() + " folds.");
                break;
            case 3:
                // Check, meaning no betting action is taken
                check();
                System.out.println(getName() + " checks.");
                break;
            default:
                // Default action is to check, although this case is technically unreachable
                check();
                System.out.println(getName() + " checks.");
                break;
            
        }
    }
            
}
