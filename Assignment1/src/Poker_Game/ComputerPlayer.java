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
    public String makeDecision() {
        int decision = random.nextInt(4); // Randomly choose an action 
        switch (decision) {
            case 0:
                return "Bet";
            case 1:
                return "Raise";
            case 2:
                return "Fold";
            case 3:
                return "Check";
            default:
                return "Check";
            
        }
    }
    
    public void bet() {
        int betAmount = random.nextInt(getChips() / 2) + 1;   // Randomly bet between 1 and half of the available chips
        reduceFromChips(betAmount);
        System.out.println(getName() + " bets "+betAmount+ " chips");
    }
    
    public void raise() {
        int raiseAmount = random.nextInt(getChips() / 2) + 1; // Randomly raise between 1 and half of the available chips
        reduceFromChips(raiseAmount);
        System.out.println(getName() + " raises by "+raiseAmount+ " chips");
    }
    
    public void fold() {
        setIsInGame(false);
        System.out.println(getName() + " folds.");
    }
    
    public void check() {
        System.out.println(getName() + " checks.");
    }
    
        
}
