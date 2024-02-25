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
    //Inventory; Anything to Save, Add Here
    public Player[] player = new Player[4];
    int numPlayers;
}