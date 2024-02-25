import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.ArrayList;

public class CantStopGame extends JFrame implements ActionListener {

  private JFrame mainFrame, playerSelectionFrame, gameBoardFrame;
  private JButton newGameButton, loadGameButton, instructionsButton, quitButton, quitFromGameButton, startGameButton, saveButton;
  private JComboBox<String> player1ColorComboBox, player2ColorComboBox, player3ColorComboBox, player4ColorComboBox;
  private JComboBox<String> player1DifficultyBox, player2DifficultyBox, player3DifficultyBox, player4DifficultyBox;
  private JCheckBox checkBox1, checkBox2, checkBox3, checkBox4;
  private JCheckBox humanBox1, humanBox2, humanBox3, humanBox4;
  private JPanel gameBoardPanel;
  private String player1,player2,player3,player4;
  
  private SaveLoad saveLoad;
  //Details to Save:
  int numPlayers = 0;
  int numHumans = 0;
  ArrayList<String> players = new ArrayList<>();
  HashMap<String, String> playerColors = new HashMap<>();
  HashMap<String, Boolean> isComputer = new HashMap<>();
  HashMap<String, String> computerDifficulty = new HashMap<>();
  private Boolean isSave = false;
  private Boolean playerExist = false;
  private Boolean humanExist = false;

  public CantStopGame() {
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
    gameBoardPanel.add(titleLabel, BorderLayout.NORTH);
    gameBoardPanel.add(buttonPanel, BorderLayout.CENTER);

    // Add the main panel to the main window
    mainFrame.add(gameBoardPanel);
    mainFrame.pack();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == newGameButton) {
      
        // Create the player selection window
        playerSelectionFrame = new JFrame("Select Players");
        playerSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        // Set up the player selection panel
        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new BoxLayout(playerSelectionPanel, BoxLayout.Y_AXIS));
        playerSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Add the title label to the player selection panel
        JLabel titleLabel = new JLabel("Select Players",JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        playerSelectionPanel.add(titleLabel);
    
        player1ColorComboBox =
            new JComboBox<String>(new String[] { "Purple", "Pink","Green","Yellow" });
        player2ColorComboBox =
            new JComboBox<String>(new String[] { "Purple", "Pink","Green","Yellow" });
        player3ColorComboBox =
            new JComboBox<String>(new String[] { "Purple", "Pink","Green","Yellow" });
        player4ColorComboBox =
            new JComboBox<String>(new String[] { "Purple", "Pink","Green","Yellow" });
    
        player1DifficultyBox =
            new JComboBox<String>(new String[] { "Easy", "Hard" });
        player2DifficultyBox =
            new JComboBox<String>(new String[] { "Easy", "Hard" });
        player3DifficultyBox =
            new JComboBox<String>(new String[] { "Easy", "Hard" });
        player4DifficultyBox =
            new JComboBox<String>(new String[] { "Easy", "Hard" });
        
        //Player 1 Details
        JPanel Player1 = new JPanel();
        Player1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ImageIcon p1Icon = new ImageIcon("p1.png");
        JLabel p1 = new JLabel();
        p1.setIcon(p1Icon);
        Player1.add(p1);
        checkBox1 = new JCheckBox("Player 1");
        checkBox1.setBounds(120,50,100,30);
        Player1.add(checkBox1);
        Player1.add(new JLabel("Select color:"));
        Player1.add(player1ColorComboBox);
        humanBox1 = new JCheckBox("Human Player");
        Player1.add(humanBox1);
        Player1.add(new JLabel("Select Difficulty:"));
        Player1.add(player1DifficultyBox);
    
        //Player 2 Details
        JPanel Player2 = new JPanel();
        Player2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ImageIcon p2Icon = new ImageIcon("p1.png");
        JLabel p2 = new JLabel();
        p2.setIcon(p2Icon);
        Player2.add(p2);
        checkBox2 = new JCheckBox("Player 2");
        checkBox2.setBounds(120,50,100,30);
        Player2.add(checkBox2);
        Player2.add(new JLabel("Select color:"));
        Player2.add(player2ColorComboBox);
        humanBox2 = new JCheckBox("Human Player");
        Player2.add(humanBox2);
        Player2.add(new JLabel("Select Difficulty:"));
        Player2.add(player2DifficultyBox);
    
        //Player 3 Details
        JPanel Player3 = new JPanel();
        Player3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ImageIcon p3Icon = new ImageIcon("p1.png");
        JLabel p3 = new JLabel();
        p3.setIcon(p3Icon);
        Player3.add(p3);
        checkBox3 = new JCheckBox("Player 3");
        checkBox3.setBounds(120,50,100,30);
        Player3.add(checkBox3);
        Player3.add(new JLabel("Select color:"));
        Player3.add(player3ColorComboBox);
        humanBox3 = new JCheckBox("Human Player");
        Player3.add(humanBox3);
        Player3.add(new JLabel("Select Difficulty:"));
        Player3.add(player3DifficultyBox);
    
        //Player 4 Details
        JPanel Player4 = new JPanel();
        Player4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ImageIcon p4Icon = new ImageIcon("p1.png");
        JLabel p4 = new JLabel();
        p4.setIcon(p4Icon);
        Player4.add(p4);
        checkBox4 = new JCheckBox("Player 4");
        checkBox4.setBounds(120,50,100,30);
        Player4.add(checkBox4);
        Player4.add(new JLabel("Select color:"));
        Player4.add(player4ColorComboBox);
        humanBox4 = new JCheckBox("Human Player");
        Player4.add(humanBox4);
        Player4.add(new JLabel("Select Difficulty:"));
        Player4.add(player4DifficultyBox);
    
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(this);
        startGameButton.setSize(10,10);
        
        if (checkBox1 != null | checkBox2 != null | checkBox3 != null | checkBox4 != null){
            playerExist = true;
        }
        
        if (humanBox1 != null | humanBox2 != null | humanBox3 != null | humanBox4 != null){
            humanExist = true;
        }        
    
        // Add the player selection panel to the player selection window
        playerSelectionPanel.add(Player1);
        playerSelectionPanel.add(Player2);
        playerSelectionPanel.add(Player3);
        playerSelectionPanel.add(Player4);
        playerSelectionPanel.add(startGameButton);
        playerSelectionFrame.add(playerSelectionPanel);
        playerSelectionFrame.pack();
        playerSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playerSelectionFrame.setVisible(true);
    } else if ( (e.getSource() == startGameButton) && humanExist && playerExist) {
        if (checkBox1.isSelected() || checkBox2.isSelected() || checkBox3.isSelected() || checkBox4.isSelected()){
            playerExist = true;
            numPlayers = 1;
            } else {
                JOptionPane.showMessageDialog(playerSelectionFrame, "Please select at least one player.", "No players selected", JOptionPane.WARNING_MESSAGE);
                return;
                    }

        if (humanBox1.isSelected() || humanBox2.isSelected() || humanBox3.isSelected() || humanBox4.isSelected()){
            humanExist = true;
            numHumans = 1;
            } else {
                JOptionPane.showMessageDialog(playerSelectionFrame, "Please select at least one human player.", "No human players selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
        if (playerExist && humanExist){
            if ( (checkBox1.isSelected() && humanBox1.isSelected()) | (checkBox2.isSelected() && humanBox2.isSelected()) | (checkBox3.isSelected() && humanBox3.isSelected()) | (checkBox4.isSelected() && humanBox4.isSelected()) && numPlayers==1  ){
                
            }
            else{
                JOptionPane.showMessageDialog(playerSelectionFrame, "Please select at the correct Human Box with Player Box", "Player does not correspond to Human", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        playerSelectionFrame.dispose();
        recordGameSettings();
        gameBoardFrame = new JFrame("Game Board");
        gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BorderLayout());

        // create a new instance of CantStopBoard and get its gameBoardPanel
        CantStopBoard cantStopBoard = new CantStopBoard();
        JPanel boardPanel = cantStopBoard.getMainPanel();

        // add the boardPanel to the gameBoardPanel
        gameBoardPanel.add(boardPanel, BorderLayout.CENTER);

        // create a new JPanel for the save game button and add it to the top right corner
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save Game");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
        buttonPanel.add(instructionsButton);
        quitFromGameButton = new JButton("Quit Game");
        quitFromGameButton.addActionListener(this);
        buttonPanel.add(quitFromGameButton);

        // New Panel to display selected players
        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(4, 1));

        for (int i = 1; i <= 4; i++) {
            JLabel playerLabel = new JLabel();
            String[] players = new String[4];
            players[0] = player1;
            players[1] = player2;
            players[2] = player3;
            players[3] = player4;
            if (players[i-1] != null){
                playerLabel.setText("Player " + i + " " + players[i-1]);
                playerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                playerLabel.setForeground(Color.BLACK);
                playersPanel.add(playerLabel);
            }

        }

        // create a new JPanel to hold the players panel and button panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(playersPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        gameBoardPanel.add(topPanel, BorderLayout.NORTH);

        // add the gameBoardPanel to the JFrame
        gameBoardFrame.add(gameBoardPanel);
        gameBoardFrame.pack();
        gameBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoardFrame.setVisible(true);
        
    } else if(e.getSource() == saveButton){
        String input = JOptionPane.showInputDialog(gameBoardFrame,"Enter the name of save game:");
        
        if (input == null){
            JOptionPane.showMessageDialog(
            gameBoardFrame,
            "Game not Saved!",
            "Game Not Saved",
            JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        if (input.contains(".")){
            JOptionPane.showMessageDialog(
            gameBoardFrame,
            "Invalid file name, it should not contain '.'",
            "Invalid Name",
            JOptionPane.INFORMATION_MESSAGE
            );
            JOptionPane.showMessageDialog(
            gameBoardFrame,
            "Game not Saved!",
            "Game not Saved",
            JOptionPane.INFORMATION_MESSAGE
            );
        } else{
        input = input + ".txt";
        int choice = JOptionPane.showConfirmDialog(
            gameBoardFrame,
            "Are you sure you want to save the game?",
            "Confirm Save",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION){
                saveLoad = new SaveLoad();
                saveLoad.save(this,input);
                if (!saveLoad.isUnique) {
                    // File already exists, prompt user
                    JOptionPane.showMessageDialog(
                gameBoardFrame,
                "File Already Exists, Try Again!",
                "File Already Exists",
                JOptionPane.INFORMATION_MESSAGE
                );
                    return;
                } else{
                isSave = true;
                JOptionPane.showMessageDialog(
                gameBoardFrame,
                "Game Saved!",
                "Saved Game",
                JOptionPane.INFORMATION_MESSAGE
                );}
            }
            else{
                JOptionPane.showMessageDialog(
            gameBoardFrame,
            "Game not Saved!",
            "Game not Saved",
            JOptionPane.INFORMATION_MESSAGE
            );
            }
            }
    } else if (e.getSource() == loadGameButton) {
        // Show a message dialog indicating that the load game feature is not yet implemented
        JOptionPane.showMessageDialog(
            mainFrame,
            "The Load Game feature is not yet implemented.",
            "Load Game",
            JOptionPane.INFORMATION_MESSAGE
            );
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
        
    }
  }
  
  
  //We got checkbox and human box
  private void recordGameSettings(){
      numPlayers = 0;
      
      if(checkBox1.isSelected()){
          numPlayers++;
          players.add("Player 1");
          playerColors.put("Player 1", player1ColorComboBox.getSelectedItem().toString());
          if(humanBox1.isSelected()){
              isComputer.put("Player 1", false);
              player1 = "(Human)";
          }
          else{
              isComputer.put("Player 1", true);
              player1 = "(Computer)";
              computerDifficulty.put("Player 1", player1DifficultyBox.getSelectedItem().toString());
          }
      }
      
      if(checkBox2.isSelected()){
          numPlayers++;
          players.add("Player 2");
          playerColors.put("Player 2", player2ColorComboBox.getSelectedItem().toString());
          if(humanBox2.isSelected()){
              isComputer.put("Player 2", false);
              player2 = "(Human)";
          }
          else{
              isComputer.put("Player 2", true);
              player2 = "(Computer)";
              computerDifficulty.put("Player 2", player2DifficultyBox.getSelectedItem().toString());
          }
      }
      
      if(checkBox3.isSelected()){
          numPlayers++;
          players.add("Player 3");
          playerColors.put("Player 3", player3ColorComboBox.getSelectedItem().toString());
          if(humanBox3.isSelected()){
              isComputer.put("Player 3", false);
              player3 = "(Human)";
          }
          else{
              isComputer.put("Player 3", true);
              player3 = "(Computer)";
              computerDifficulty.put("Player 3", player3DifficultyBox.getSelectedItem().toString());
          }
      }
      
      if(checkBox4.isSelected()){
          numPlayers++;
          players.add("Player 4");
          playerColors.put("Player 4", player4ColorComboBox.getSelectedItem().toString());
          if(humanBox4.isSelected()){
              isComputer.put("Player 4", false);
              player4 = "(Human)";
          }
          else{
              isComputer.put("Player 4", true);
              player4 = "(Computer)";
              computerDifficulty.put("Player 4", player4DifficultyBox.getSelectedItem().toString());
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
      }
    );
  }
}
