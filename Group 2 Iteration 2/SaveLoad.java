import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;

public class SaveLoad
{
    CantStopBoard board;
    public Boolean isUnique = true;
    
    public SaveLoad()
    {
        
    }
    
    public void save(CantStopGame game,String fileName){
        try{
            
            // Check if file exists
            File file = new File(fileName);
            if (file.exists()) {
                isUnique = false;
                }
            
            ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(new File(fileName)));
                
            DataStorage ds = new DataStorage();
            
            ds.numPlayers = game.numPlayers;
            ds.players = game.players;
            for(int i = 1; i <= 4; i++){
                if(game.playerColors.containsKey("Player " + String.valueOf(i))){
                    ds.playerColors.put("Player " + String.valueOf(i), game.playerColors.get("Player " + String.valueOf(i)));
                }
                if(game.isComputer.containsKey("Player " + String.valueOf(i))){
                    ds.isComputer.put("Player " + String.valueOf(i), game.isComputer.get("Player " + String.valueOf(i)));
                }
                if(game.computerDifficulty.containsKey("Player " + String.valueOf(i))){
                    ds.computerDifficulty.put("Player " + String.valueOf(i), game.computerDifficulty.get("Player " + String.valueOf(i)));
                }
            }
            
            oos.writeObject(ds);
        }
        catch(Exception e){
            System.out.println("Save Exception");
        }
    }
}