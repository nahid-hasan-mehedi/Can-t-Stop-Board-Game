import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import javax.swing.*;

public class SaveLoad
{
    CantStopBoard board;
    public Boolean isUnique = true;
    
    public SaveLoad()
    {
        
    }
    
    public void save(CantStopGame game) {
    //Set the look of File Chooser
    
        
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Game");
    
    //SwingUtilities.updateComponentTreeUI(fileChooser);

    
    // Show the file chooser dialog
    int userSelection = fileChooser.showSaveDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        // Get the selected file
        File selectedFile = fileChooser.getSelectedFile();
        
        // Append ".dat" to the file name
        String filePath = selectedFile.getAbsolutePath();
        if (!filePath.endsWith(".dat")) {
            selectedFile = new File(filePath + ".dat");
        }
        
        // Check if the file already exists
        if (selectedFile.exists()) {
            int confirmOverride = JOptionPane.showConfirmDialog(
                    null,
                    "This file already exists. Do you want to overwrite it?",
                    "File Exists",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirmOverride != JOptionPane.YES_OPTION) {
                // User cancelled the save operation
                return;
            }
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(selectedFile));

            // Construct a DataStorage object to save the game data
            DataStorage ds = new DataStorage();
            ds.player = game.player;
            ds.numPlayers = game.numPlayers;

            // Write to the file
            oos.writeObject(ds);

            JOptionPane.showMessageDialog(
                    null,
                    "Game saved successfully.",
                    "Save Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Failed to save game. Please try again.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
        public CantStopGame load(String fileName){
        CantStopGame game = null;

        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            DataStorage ds = (DataStorage) ois.readObject();

            game = new CantStopGame();
            game.numPlayers = ds.numPlayers;
            
            for (int i = 0; i < 4; i++){
                game.player[i] = ds.player[i];
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(
                    game,
                    "Some Error Occured While Loading The Game!",
                    "Loading Error",
                    JOptionPane.ERROR_MESSAGE
                );
            return null;
        }

        return game;
    }
}