import javax.swing.*;

/**
 * Write a description of class Driver here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Driver
{
    /**
    * Constructor for objects of class Driver
    */
    public Driver()
    {
            
    }
    
    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(
        new Runnable() {
            public void run() {
                CantStopGame game = new CantStopGame();
            }
        }
        );
    }
}