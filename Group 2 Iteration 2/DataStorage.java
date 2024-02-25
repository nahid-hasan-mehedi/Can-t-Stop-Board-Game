import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;


/**
 * Write a description of class DataStorage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DataStorage implements Serializable
{
    //Player Details
    int numPlayers;
    ArrayList<String> players;
    HashMap<String, String> playerColors = new HashMap<>();
    HashMap<String, Boolean> isComputer = new HashMap<>();
    HashMap<String, String> computerDifficulty = new HashMap<>();
}