import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.awt.image.BufferedImage;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

public class CantStopGame extends JFrame implements ActionListener {
    //Frames
    private JFrame mainFrame, playerSelectionFrame, gameBoardFrame;
    //Buttons
    private JButton newGameButton, loadGameButton, instructionsButton, 
              quitButton, quitFromGameButton, startGameButton, saveButton,
              changeDifficultyButton;
    //Combo Boxes        
    private ArrayList<JComboBox<String>> playerColorComboBox = new ArrayList<>();
    private JComboBox[] playerDifficultyBox = new JComboBox[4];
    //Check Boxes
    private JCheckBox[] playerCheckBox = new JCheckBox[4];
    private JCheckBox[] humanBox = new JCheckBox[4];
    //Panels Array
    private JPanel[] playerPanel = new JPanel[4];
    private JPanel[] playerColorPanel = new JPanel[4];
    //Players
    public Player[] player = new Player[4];
    
    private ArrayList<Integer> colsClaimed = new ArrayList<>();
    //Pannels
    public JPanel gameBoardPanel;
    public JPanel dicePanel;
    //Save Load
    private SaveLoad saveLoad;
    //Board
    public CantStopBoard cantStopBoard;
    //ints
    int numPlayers = 0, numHumans = 0;
    //Booleans
    private Boolean isSave = false, playerExist = false, 
                      humanExist = false , correctCombo = false,
                      hasUniqueColor = true;
    
                      //mode
  public Boolean currentMode = false; //false = normalMode, true= specialMode
  //modeSelector
  public JCheckBox modeSelector;
  
  //color choices
  
  //Player colors available depending on the mode
  Color[] normalColors = {Color.blue, Color.pink, Color.green, Color.yellow};
  Color[] specialColors = {new Color(100,143,255),new Color(120,94,240),new Color(220,38,127),new Color(254,97,0)};
    public CantStopGame() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Handle exception
        }
         
        // Create the main window with four buttons
        mainFrame = new JFrame("Can't Stop Game");
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        instructionsButton = new JButton("Read Instructions");
        quitButton = new JButton("Quit Game");
        newGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);
        instructionsButton.addActionListener(this);
        quitButton.addActionListener(this);
    
        // Set up the main panel
        JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BorderLayout());
        gameBoardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Add the buttons to the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(newGameButton);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(instructionsButton);
        buttonPanel.add(quitButton);
        
    
        // Add the button panel and title label to the main panel
        JLabel titleLabel = new JLabel(
          "Welcome to Can't Stop Game!",
          JLabel.CENTER
        );
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        gameBoardPanel.add(titleLabel, BorderLayout.NORTH);
        gameBoardPanel.add(buttonPanel, BorderLayout.CENTER);
    
        // Add the main panel to the main window
        mainFrame.add(gameBoardPanel);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
        for(int i = 0; i < 4; i++){
            playerColorComboBox.add(i,new JComboBox<String>(new String[] {}));
            playerDifficultyBox[i] = new JComboBox();
        }
        
        // Set up the background image
    final ImageIcon backgroundImage = new ImageIcon("game.jpg");
    JPanel backgroundPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage.getImage(), 0, 0, null);
        }
    };
    backgroundPanel.setLayout(new BorderLayout());
    backgroundPanel.add(gameBoardPanel, BorderLayout.CENTER);
    mainFrame.setContentPane(backgroundPanel);
}
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            // two types of color choices depending on the mode selected
        String[] normalColorChoices = {"Blue", "Pink","Green","Yellow"};
        String[] specialColorChoices = {"Color 1", "Color 2","Color 3","Color 4"};
        
        // Create the player selection window
        playerSelectionFrame = new JFrame("Select Players, Colors & Mode");
        playerSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playerSelectionFrame.setPreferredSize(new Dimension(900,800));
    
        // Create the player selection window
        playerSelectionFrame = new JFrame("Select Players, Colors & Mode");
        playerSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playerSelectionFrame.setPreferredSize(new Dimension(900,800));

        // Set up the player selection panel
        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new BoxLayout(playerSelectionPanel, BoxLayout.Y_AXIS));
        playerSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add the title panel to the player selection panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel PtitleLabel = new JLabel("Select Players, Colors & Mode");
        PtitleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titlePanel.add(PtitleLabel);
        playerSelectionPanel.add(titlePanel);

        //Add Mode selector
        modeSelector = new JCheckBox("Color Deficiency Mode");
        modeSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerSelectionPanel.add(modeSelector);
        
        
        
        // show color choices depending on the mode
        modeSelector.addItemListener( x-> {
          if (x.getStateChange() == ItemEvent.SELECTED) {
            for (int i = 0; i < 4; i++){
              playerColorComboBox.get(i).removeAllItems();
              for(int j = 0; j < 4; j++){
              playerColorComboBox.get(i).addItem(specialColorChoices[j]);
              }
            }
            currentMode = true;
          }
          else if (x.getStateChange() == ItemEvent.DESELECTED) {
            for (int i = 0; i < 4; i++){
              playerColorComboBox.get(i).removeAllItems();
              for(int j = 0; j < 4; j++){
              playerColorComboBox.get(i).addItem(normalColorChoices[j]);
               
              
              }
            }
            currentMode = false;
          }  
        });
        
        //New Loop Implementation
        for (int i = 0; i < 4; i++) {
            playerColorComboBox.add(i,new JComboBox<String>(normalColorChoices));
            playerDifficultyBox[i] =
            new JComboBox<String>(new String[] { "Easy", "Hard" });
            //Player Details
            playerPanel[i] = new JPanel();
            playerPanel[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            ImageIcon playerIcon = new ImageIcon("p1.png");
            JLabel pIcon = new JLabel();
            pIcon.setIcon(playerIcon);
            playerPanel[i].add(pIcon);
            playerCheckBox[i] = new JCheckBox("Player " + (i+1));
            playerCheckBox[i].setBounds(120,50,100,30);
            playerPanel[i].add(playerCheckBox[i]);
            playerPanel[i].add(new JLabel("Select color:"));
            playerPanel[i].add(playerColorComboBox.get(i));
            playerColorPanel[i] = new JPanel();
            playerColorPanel[i].setBackground(Color.BLUE);
            playerPanel[i].add(playerColorPanel[i]);
           
            humanBox[i] = new JCheckBox("Human Player");
            playerPanel[i].add(humanBox[i]);
            playerPanel[i].add(new JLabel("Select Difficulty:"));
            playerPanel[i].add(playerDifficultyBox[i]);
        }
            
            playerColorComboBox.get(0).addActionListener(x->{ 
              if(playerColorComboBox.get(0).getSelectedItem()!= null){
              String color1 = playerColorComboBox.get(0).getSelectedItem().toString();
              playerColorPanel[0].setBackground(getColorFromString(color1));
              }
            });
            playerColorComboBox.get(1).addActionListener(x->{ 
              if(playerColorComboBox.get(1).getSelectedItem()!= null){
              String color2 = playerColorComboBox.get(1).getSelectedItem().toString();
              playerColorPanel[1].setBackground(getColorFromString(color2));
              }
            });
            playerColorComboBox.get(2).addActionListener(x->{ 
              if(playerColorComboBox.get(2).getSelectedItem()!= null){
              String color3 = playerColorComboBox.get(2).getSelectedItem().toString();
              playerColorPanel[2].setBackground(getColorFromString(color3));
              }
            });
            playerColorComboBox.get(3).addActionListener(x->{ 
              if(playerColorComboBox.get(3).getSelectedItem()!= null){
              String color4 = playerColorComboBox.get(3).getSelectedItem().toString();
              playerColorPanel[3].setBackground(getColorFromString(color4));
              }
            });
            
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(this);
        startGameButton.setSize(10,10);
        
        for (int i = 0; i < 4; i++) {
            
            if (playerCheckBox[i] != null){
                playerExist = true;
            }
        
            if (humanBox[i] != null){
                humanExist = true;
            }  
            
            playerSelectionPanel.add(playerPanel[i]);
        }      
    
        
        playerSelectionPanel.add(startGameButton);
        playerSelectionFrame.add(playerSelectionPanel);
        playerSelectionFrame.pack();
        playerSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playerSelectionFrame.setVisible(true);
        
    } else if ( (e.getSource() == startGameButton) && humanExist && playerExist) {
            
            if (playerCheckBox[0].isSelected() || playerCheckBox[1].isSelected() || playerCheckBox[2].isSelected() || playerCheckBox[3].isSelected()){
                playerExist = true;
                numPlayers = 1;
                } else {
                    JOptionPane.showMessageDialog(playerSelectionFrame, "Please select at least one player.", "No players selected", JOptionPane.WARNING_MESSAGE);
                    return;
                        }
    
            if (humanBox[0].isSelected() || humanBox[1].isSelected() || humanBox[2].isSelected() || humanBox[3].isSelected()){
                humanExist = true;
                numHumans = 1;
                } else {
                    JOptionPane.showMessageDialog(playerSelectionFrame, "Please select at least one human player.", "No human players selected", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            if (playerExist && humanExist){
                if ( (playerCheckBox[0].isSelected() && humanBox[0].isSelected()) | (playerCheckBox[1].isSelected() && humanBox[1].isSelected()) | (playerCheckBox[2].isSelected() && humanBox[2].isSelected()) | (playerCheckBox[3].isSelected() && humanBox[3].isSelected()) && numPlayers==1  ){
                    
                }
                else{
                    JOptionPane.showMessageDialog(playerSelectionFrame, "Please select at the correct Human Box with Player Box", "Player does not correspond to Human", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            Set<String> selectedColors = new HashSet<>();
            for (int i = 0; i < 4; i++) {
                if (playerCheckBox[i].isSelected()) {
                    String color = playerColorComboBox.get(i).getSelectedItem().toString();
                    if (selectedColors.contains(color)) {
                        JOptionPane.showMessageDialog(playerSelectionFrame, "Please select a unique color for each player.", "Duplicate color", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    selectedColors.add(color);
                }
            }
             
            
            //Pre Conditions To Start Game Board
            recordGameSettings();        
            playerSelectionFrame.dispose();
            createGamePanel(this.player);
            
        } else if(e.getSource() == saveButton){
            saveLoad = new SaveLoad();
            saveLoad.save(this);
            isSave = true;
            
        } else if (e.getSource() == loadGameButton) {
                // Show a file chooser dialog to allow the user to select a game file to load
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Load Game");
                int userSelection = fileChooser.showOpenDialog(gameBoardFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToLoad = fileChooser.getSelectedFile();
                    saveLoad = new SaveLoad();
                    CantStopGame loadedGame = saveLoad.load(fileToLoad.getAbsolutePath());
                    if (loadedGame == null) {
                        // Failed to load the game, show an error message
                        JOptionPane.showMessageDialog(
                        gameBoardFrame,
                        "Failed to load game! Please Try Again!",
                        "Load Game",
                        JOptionPane.ERROR_MESSAGE
                        );
                    return;
                }
                // Game was loaded successfully, update the UI to reflect the new game state
                this.createGamePanel(loadedGame.player);
                mainFrame.dispose();
                checkPlayerTiles(this);
                //For Player to Save the Game Again After Loading
                isSave = false;
            }
        } else if (e.getSource() == instructionsButton) {
            // Show the instructions
            new InstructionsWindow();
        } else if (e.getSource() == quitButton) {
            // Show a message dialog asking the user if they really want to quit the game
            int choice = JOptionPane.showConfirmDialog(
                mainFrame,
                "Are you sure you want to quit the game?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
                );
        
            // If the user chooses Yes, exit the program
            if (choice == JOptionPane.YES_OPTION) {
              System.exit(0);
            }
        } else if (e.getSource() == quitFromGameButton){
            if (isSave == false){
                int choice = JOptionPane.showConfirmDialog(
                gameBoardFrame,
                "Are you sure you want to quit the Game Without Saving?",
                "Quit Game without Saving",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
                );
                
                if (choice == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
            else {
                int choice = JOptionPane.showConfirmDialog(
                gameBoardFrame,
                "Are you sure you want to quit the Game?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
                );
                
                if (choice == 0){
                    System.exit(0);
                }
            }
            
        } else if (e.getSource() == changeDifficultyButton){
            new ChangeDifficultyPanel(player);
            
        }
    }
      
      
      //Record the details in order to save/load
    private void recordGameSettings(){
          
          for (int i = 0; i < 4; i++) {
              
              String prevCol = null;
          
              if(playerCheckBox[i].isSelected()){
        
                  if(humanBox[i].isSelected()){
                      
                      this.player[i] = new Player("Player "+ (i+1), 
                              playerColorComboBox.get(i).getSelectedItem().toString(),
                              true,
                              playerDifficultyBox[i].getSelectedItem().toString());
                  }
                  else{
                      
                      this.player[i] = new ComputerPlayer("Player "+ (i+1), 
                              playerColorComboBox.get(i).getSelectedItem().toString(),
                              false,
                              playerDifficultyBox[i].getSelectedItem().toString());
                      
                  }
              }
          
          }
    }
    
    /*
     * 
     */
    
    private void updatePlayerDetails(Player[] newPlayers){
        this.player = newPlayers;
    }
    
    public void createGamePanel(Player[] newPlayers){
        //Update Players Details
        updatePlayerDetails(newPlayers);
        
        //Initialize the Game Board
        gameBoardFrame = new JFrame("Game Board");
        gameBoardFrame.setPreferredSize(new Dimension(1050,900));
        //gameBoardFrame.setResizable(false);
        gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BorderLayout());
        Color boardBackground = new Color(225,225,225);

        // create a new instance of CantStopBoard and get its gameBoardPanel
        cantStopBoard = new CantStopBoard(currentMode);
        JPanel boardPanel = cantStopBoard.getMainPanel();

        // add the boardPanel to the gameBoardPanel
        gameBoardPanel.add(boardPanel, BorderLayout.WEST);

        // create a new JPanel for the save game button and add it to the top right corner
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        changeDifficultyButton = new JButton("Change Difficulty");
        changeDifficultyButton.addActionListener(this);
        buttonPanel.add(changeDifficultyButton);
        
        saveButton = new JButton("Save Game");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
        
        buttonPanel.add(instructionsButton);
        
        quitFromGameButton = new JButton("Quit Game");
        quitFromGameButton.addActionListener(this);
        buttonPanel.add(quitFromGameButton);
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // New Panel to display selected players
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(4, 1));
        playersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 430));
        
        for (int i = 0; i < 4; i++) {
            JPanel playerPanel = new JPanel();
            JLabel playerLabel = new JLabel();
            
            if (newPlayers[i] != null){
                if (newPlayers[i].isHuman()){
                    playerLabel.setText(newPlayers[i].getName() + " (Human)");
                } else {
                    playerLabel.setText(newPlayers[i].getName() + " (Computer)");
                }
                playerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                //playerLabel.setForeground(Color.BLACK);
                
                // create a small box with player's color
                JPanel colorPanel = new JPanel();
                colorPanel.setPreferredSize(new Dimension(12, 12));
                colorPanel.setBackground(getColorFromString(newPlayers[i].getColor()));
                colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
                // add color box to player label
                playerPanel.add(playerLabel, BorderLayout.WEST);
                playerPanel.add(colorPanel, BorderLayout.EAST);
                
                playersPanel.add(playerPanel);
                colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            }

        }
        
        // create a new JPanel to hold the players panel and button panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(playersPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Create a JPanel for the dices
        
        DicePanel dicePanel = new DicePanel(this,currentMode);
        
        gameBoardPanel.add(dicePanel, BorderLayout.EAST);
        gameBoardPanel.add(topPanel, BorderLayout.NORTH);
        

        // add the gameBoardPanel to the JFrame
        gameBoardFrame.add(gameBoardPanel);
        gameBoardFrame.pack();
        gameBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoardFrame.setVisible(true);
    }
    
    public Player getPlayerFromColor(Color color){
        for (int i = 0; i < 4; i++){
            if (player[i] != null){
                if (color.equals(player[i].nsColor)){
                    return player[i];
                }
            }
        }   
        return null;
    }
    
    public Color getColorFromString(String colorName) {
    switch(colorName) {
        case "Blue":
            return normalColors[0];
        case "Pink":
            return normalColors[1];
        case "Green":
            return normalColors[2];
        case "Yellow":
            return normalColors[3];
        case "Color 1":
            return specialColors[0];
        case "Color 2":
            return specialColors[1];
        case "Color 3":
            return specialColors[2];
        case "Color 4":
            return specialColors[3];
        default:
            return Color.black;
      }
    }
    
    public void checkPlayerTiles(CantStopGame game){
        for(int i = 0 ; i < 4; i++){
            if(game.player[i] != null){
                for (Integer key : game.player[i].colRow.keySet()){
                    int col = key;
                    int row = game.player[i].colRow.get(key);
                    // Check if the color of the panel is not white then add the player's color on the columns and rows he occupy
                    if (!cantStopBoard.panels[row][col].getBackground().equals(Color.WHITE)){
                        cantStopBoard.panels[row][col].setBackground(game.player[i].nsColor);
                    }
                }
            }
        }
    }
    
    public void victory(Player player){
        new VictoryWindow(player, this);
    }

    public boolean isColumnClaimed(int col){
        if(colsClaimed.contains(col)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void addColsClaimed(int col){
        colsClaimed.add(col);
    }
    
    public void removeClaimedColumns(Player playerClaiming, int col){
        for(int i = 0; i < 4; i++){
            if(player[i] != null && !player[i].equals(playerClaiming)){
                player[i].removeCol(col);
            }
        }
    }


    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    new CantStopGame();
                }
            });
    }
}