import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeDifficultyPanel {

    private JFrame changeDifficultyFrame;
    private JPanel changeDifficultyPanel;
    private JButton saveButton;
    private ArrayList<Player> players = new ArrayList<>();
    
    private JComboBox[] difficultyComboBox = new JComboBox[3];

    public ChangeDifficultyPanel(Player[] p) {
        for(int i = 0; i < 3; i++){
            difficultyComboBox[i] = new JComboBox();
        }
        //Create an ArrayList of players who are computer
        for (int i = 0; i<4 ; i++) {
            if(p[i] != null){
            if (!p[i].isHuman){
                this.players.add(p[i]);
            }}
        }
        
        if (players.size() == 0){
            JOptionPane.showMessageDialog(
                    changeDifficultyFrame,
                    "No Computer Players Exist!",
                    "No Computer Players",
                    JOptionPane.INFORMATION_MESSAGE
                    );
            return;
        }
        
        // Create the frame
        changeDifficultyFrame = new JFrame("Change Difficulty");
        changeDifficultyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the panel
        changeDifficultyPanel = new JPanel();
        changeDifficultyPanel.setLayout(new BoxLayout(changeDifficultyPanel, BoxLayout.Y_AXIS));
        changeDifficultyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add title label to panel
        JLabel titleLabel = new JLabel("Change Difficulty", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        changeDifficultyPanel.add(titleLabel);
        
        // Add components for each non-human player
        int count = 0;
        for (int i = 0; i<4 ; i++) {
            if (p[i] != null){
              if (!p[i].isHuman()) {
                JPanel playerPanel = new JPanel();
                playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Add player icon
                ImageIcon playerIcon = new ImageIcon("p1.png");
                JLabel pIcon = new JLabel();
                pIcon.setIcon(playerIcon);
                playerPanel.add(pIcon);

                // Add player name
                JLabel playerNameLabel = new JLabel(p[i].getName());
                //playerNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                playerPanel.add(playerNameLabel);

                // Add player difficulty combo box
                difficultyComboBox[count] = new JComboBox<>(new String[]{"Easy", "Hard"});
                difficultyComboBox[count].setSelectedItem(p[i].getDifficultyLevel());
                playerPanel.add(new JLabel("Difficulty:"));
                playerPanel.add(difficultyComboBox[count]);

                changeDifficultyPanel.add(playerPanel);

                count++;
        
            }
          }
        }

        // Add save button to panel
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update difficulty for each non-human player
                int i = 0;
                for (Player player : players) {
                    if (!player.isHuman()) {
                        player.setDifficultyLevel(difficultyComboBox[i].getSelectedItem().toString());
                        i++;
                    }
                }
                JOptionPane.showMessageDialog(
                    changeDifficultyFrame,
                    "Difficulty Level(s) Changed",
                    "Difficulty Changed",
                    JOptionPane.INFORMATION_MESSAGE
                    );
                // Close the window
                changeDifficultyFrame.dispose();
            }
        });
        changeDifficultyPanel.add(saveButton);

        // Add panel to frame and display
        changeDifficultyFrame.add(changeDifficultyPanel);
        changeDifficultyFrame.pack();
        changeDifficultyFrame.setVisible(true);
    }

    private JPanel getPlayerPanel(Player player) {
        int count = 0;
        for (Component c : changeDifficultyPanel.getComponents()) {
            if (c instanceof JPanel) {
                JLabel playerNameLabel = (JLabel) ((JPanel) c).getComponent(1);
                if (playerNameLabel.getText().equals(player.getName())) {
                    return (JPanel) c;
                }
            }
            count++;
        }
        return null;
    }

    public static void main(String[] args) {
        Player[] players = {
            
                new Player("Player 1", "Purple",true, "Easy"),
                new Player("Player 2", "Pink",false, "Hard"),
                new Player("Player 3", "Yellow",true, "Easy"),
                new Player("Player 4", "Green",false, "Hard")
        };

        ChangeDifficultyPanel changeDifficulty = new ChangeDifficultyPanel(players);
    }
}