import java.util.Random;
import java.awt.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * Write a description of class ComputerPlayer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComputerPlayer extends Player
{
    Random rand;
    boolean takeAnotherTurn;
    int turnsTaken,delay = 100;
    
    /**
     * Constructor for objects of class ComputerPlayer
     */
    public ComputerPlayer(String name, String color, boolean isH, String difficultyLevel)
    {
        super(name, color, false, difficultyLevel);
    }
    
    public ComputerPlayer(String name, String color, boolean isH, String difficultyLevel, ArrayList<Integer> columnsOccupied, 
                    ArrayList<Integer> currentColumnsOccupied, int runnersOnBoard, HashMap<Integer, Integer> colRow, boolean busted,
                    boolean isTurn, int pointTotal) {
        super(name, color, false, difficultyLevel, columnsOccupied, 
                    currentColumnsOccupied, runnersOnBoard, colRow, busted, isTurn, pointTotal);
    }
    
    
    @Override
    public void takeTurn(DicePanel dicePanel){
        rand = new Random();
        takeAnotherTurn = true;
        
        if(difficultyLevel == "Easy"){
            easyTurn(dicePanel);
        }
        else if(difficultyLevel == "Hard"){
            hardTurn(dicePanel);
        }
        
    }
    
    /*
     * PLAN------------------
     * 1: Hit the Roll Dice button
     * 2: Randomly select the dice
     * 3: Hit the Take A Turn button to solidify the picks
     * 4: Randomly, 50% of the time choose to repeat the previous sequence of 3 steps.
     * 5: (If the ComputerPlayer did not bust) hit the End Turn button.
     * 5.1: (If the Computer Player did bust) hit the ok button on the busted prompt.
     * 
     * What do we need?
     * 1. Access to the Roll Dice Button so we can click it
     * 2. Access to the Panels containing the Dice so we can click them
     * 3. To use the Random object to choose which dice to select in which order (Perhaps use % 4 on rand.nextInt())
     * 4. To have access to the Take A Turn button
     * 5. To Randomly choose whether to click the End Turn button 50% of the time (Perhaps use % 2 on rand.nextInt())
     * 6. To have a while loop which continually performs steps 1-3 in the PLAN
     * 7. To have a function which performs step 4 in the PLAN, the determinant for the while loop continuing
     * 8. To be able to break the while loop if the Computer Player busts.
     * 9. To be able to click the newly created prompt if the Computer Player busts (or maybe just leave it to user input if too difficult)
     */
    public void easyTurn(DicePanel dicePanel){
        //NOT FINISHED
        while(takeAnotherTurn){
            //Do the taking a turn stuff here
            
            dicePanel.cpuClickRollButton();
            //Select the labels in order from 0->3
            //This includes setting the colors of the panels
            //We have to set the sequences properly
            //Set turn to a new TakeATurn object so the board updates with the appropriate things
            dicePanel.setComputerDice(0, 1, 2, 3);
            
            for(int i = 0; i < 4; i++){
                dicePanel.getDiceLabel(i).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                dicePanel.getDiceLabel(i).setEnabled(false);
                waitForDelay();
            }
            
            dicePanel.getTakeTurnButton().doClick();
            waitForDelay();
            
            if(busted){
                takeAnotherTurn = false;
                break;
            }
            
            //At the end decide whether to take another turn
            takeAnotherTurnEasy();
            waitForDelay();
        }
        
        //NOT FINISHED
        if(busted){
            //Perform certain actions based on if the CPU busted.
        }
        //NOT FINISHED
        else{
            //Do the end of turn stuff here like clicking the endTurn button
            
            dicePanel.getEndTurnButton().doClick();
            waitForDelay();
        }
    }
    
    public void hardTurn(DicePanel dicePanel){
        turnsTaken = 0;
        while(takeAnotherTurn){
            dicePanel.cpuClickRollButton();
            dicePanel.setComputerDiceHard(this);
            
            for(int i = 0; i < 4; i++){
                dicePanel.getDiceLabel(i).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                dicePanel.getDiceLabel(i).setEnabled(false);
                waitForDelay();
            }
            
            dicePanel.getTakeTurnButton().doClick();
            waitForDelay();
            
            if(busted){
                takeAnotherTurn = false;
                break;
            }
            
            //At the end decide whether to take another turn
            takeAnotherTurnHard();
            
            waitForDelay();
        }
        
        if(busted){
            //Perform certain actions based on if the CPU busted.
        }
        //NOT FINISHED
        else{
            //Do the end of turn stuff here like clicking the endTurn button
            
            dicePanel.getEndTurnButton().doClick();
            waitForDelay();
        }
    }
    
    //Gives a 50/50 chance on whether or not to take another turn
    private boolean takeAnotherTurnEasy(){
        if(rand.nextInt() % 2 == 0){
            takeAnotherTurn = true;
        }
        else{
            takeAnotherTurn = false;
        }
        return takeAnotherTurn;
    }
    
    private void takeAnotherTurnHard(){
        if(turnsTaken == 0){
            turnsTaken++;
            takeAnotherTurn = true;
        }
        else{
            takeAnotherTurn = false;
        }
    }
    
    private void waitForDelay(){
        try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
