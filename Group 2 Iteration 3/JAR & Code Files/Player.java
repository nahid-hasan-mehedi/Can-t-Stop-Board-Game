import java.io.Serializable;

public class Player implements Serializable {
    public String name;
    public String color;
    public boolean isHuman;
    public boolean isTurn;
    public boolean isWinner;
    public String difficultyLevel;

    public Player(String name, String color, boolean isHuman, String difficultyLevel) {
        this.name = name;
        this.color = color;
        this.isHuman = isHuman;
        this.difficultyLevel = difficultyLevel;
        this.isTurn = false;
        this.isWinner = false;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public boolean isWinner() {
        return isWinner;
    }
    
    public String humanOrComputer(){
        if (isHuman){
            return "(Human)";
        }
        else{
            return "(Computer)";
        }
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }
    
    public void setDifficultyLevel(String d){
        this.difficultyLevel = d;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}