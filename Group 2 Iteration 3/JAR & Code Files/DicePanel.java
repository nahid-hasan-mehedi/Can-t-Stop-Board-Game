import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class DicePanel extends JPanel implements ActionListener,MouseListener {

    private JLabel playerLabel,statusLabel,combLabel;
    private JPanel dicePanel;
    private JButton rollButton;
    private JButton takeTurn;
    private JButton endTurn;
    private JPanel[] dices;
    private JLabel[] labels;
    private JLabel selectedDice;
    private Boolean rolled = false, resetted = false;
    private ImageIcon[] diceIcons = new ImageIcon[6];
    private static final ArrayList<Integer> diceNumbers = new ArrayList<Integer>();
    private Integer rand,first,second,third,fourth;
    private int sum,remainingSum,total;
    private ArrayList<Integer> sequence = new ArrayList<>();
        public DicePanel(CantStopGame game) {
            
        setLayout(new BorderLayout());
        
        JPanel playerStatus = new JPanel(new BorderLayout());
        // create the player label with an image icon
        playerLabel = new JLabel(new ImageIcon("p1.png"));
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        playerStatus.add(playerLabel, BorderLayout.NORTH);
        
        JLabel statusLabel = new JLabel();
        for(int i = 0; i < 4;i++){
            if (!(game.player[i]==null)){
                statusLabel.setText(game.player[i].getName() + "'s Turn");
                break;
            }
        }
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        playerStatus.add(statusLabel,BorderLayout.SOUTH);
        
        add(playerStatus, BorderLayout.NORTH);
        
        // create the dice panel with 2x2 grid layout
        dicePanel = new JPanel(new GridLayout(2,0,2,0));
    
        dices = new JPanel[4];
        labels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            dices[i] = new JPanel();
            labels[i] = new JLabel();
            ImageIcon icon = new ImageIcon("dice_"+Integer.toString(i+1)+".png");
            labels[i].setIcon(icon);
            labels[i].addMouseListener(this);
            dices[i].add(labels[i]);
            dicePanel.add(dices[i]);
        }
        
        dicePanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 40, 10));
        add(dicePanel);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());        
        
        //JLabel combLabel = new JLabel();
        combLabel = new JLabel();

        combLabel.setText("<html>Select The Combinations</html>");
        combLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        combLabel.setForeground(Color.BLACK);
        combLabel.setHorizontalAlignment(JLabel.CENTER);
        combLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        bottomPanel.add(combLabel, BorderLayout.NORTH);

        // create the take turn and end turn buttons and add them to the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        takeTurn = new JButton("Take a Turn");
        endTurn = new JButton("End a Turn");
        takeTurn.addActionListener(this);
    
        endTurn.addActionListener(this);

        buttonPanel.add(takeTurn, BorderLayout.WEST);
        JButton space = new JButton();
        space.setVisible(false);
        buttonPanel.add(space, BorderLayout.CENTER);
        buttonPanel.add(endTurn, BorderLayout.EAST);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        add(bottomPanel, BorderLayout.SOUTH);

        // create the roll button and add it to the bottom
        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(this);
        //setBorder(BorderFactory.createEmptyBorder(0,0,100,0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 85));
        bottomPanel.add(rollButton, BorderLayout.CENTER); 

    }

    public void animateRoll(JLabel diceLabel) {
        Random random = new Random();
        int numFrames = 5; // Number of frames in the animation
        int delay = 10; // Delay between each frame (in milliseconds)

        for (int i = 0; i < 6; i++) {
            diceIcons[i] = new ImageIcon(getClass().getResource("/dice_" + (i+1) + ".png"));
            diceIcons[i].setDescription(Integer.toString(i));
        }

        // Animate the roll
        for (int i = 0; i < 4; i++) {
            // Get a random dice image
            rand = random.nextInt(6);
            ImageIcon diceIcon = diceIcons[rand];
            // Update the dice label with the new image
            diceLabel.setIcon(diceIcon);

            // Pause for a short time before updating again
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        diceNumbers.add(rand+1);
        remainingSum += rand+1;
        }

 public void actionPerformed(ActionEvent e) {
    //for roll dice 
    if (e.getSource() == rollButton) {
        resetDicePanel();
        for (int i = 0; i < 4; i++) {
            animateRoll(labels[i]);
        }
        rolled = true;
    }
    //for take a turn and end a turn
    if (e.getSource() == takeTurn || e.getSource() == endTurn) {
        if (!rolled) {
            JOptionPane.showMessageDialog(
                    dicePanel,
                    "Please Roll The Dices First!",
                    "Dices Not Rolled",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
        }
        int sum = 0;
        for (int i = 0; i < sequence.size(); i++) {
            sum += sequence.get(i);
        }
        if (sum == 0) {
            JOptionPane.showMessageDialog(
                dicePanel,
                "Please select at least one die!",
                "No die selected",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        total += sum;
        remainingSum -= sum;
        combLabel.setText("<html>Select The Combinations<br>Total Score: " + total + "</html>");
        if (remainingSum == 0) {
            JOptionPane.showMessageDialog(
                dicePanel,
                "End of turn! Total score: " + total,
                "End of turn",
                JOptionPane.INFORMATION_MESSAGE
            );
            rolled = false;
            resetted = true;
            sequence.clear();
        }
        else {
            sequence.clear();
        }
    }
}

    
    public void mouseClicked(MouseEvent mevt) {
        Object selected = mevt.getSource();
        if (selected instanceof JLabel) {

        if (!rolled && !resetted) {
            JOptionPane.showMessageDialog(
                    dicePanel,
                    "Please Roll The Dices First!",
                    "Dices Not Rolled",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            } else {
            JLabel selectedDice = (JLabel) selected;
            selectedDice.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            selectedDice.setEnabled(false);
            if (selected == labels[0]){
                first = diceNumbers.get(0);
                sequence.add(first);
            }
            if (selected == labels[1]){
                second = diceNumbers.get(1);
                sequence.add(second);
            }
            if (selected == labels[2]){
                third = diceNumbers.get(2);
                sequence.add(third);
            }
            if (selected == labels[3]){
                fourth = diceNumbers.get(3);
                sequence.add(fourth);
            }
        }
        if (sequence.size() == 4){
            int firstSeq = sequence.get(0)+sequence.get(1);
            int secondSeq = sequence.get(2)+sequence.get(3);
            int choice = JOptionPane.showConfirmDialog(
            dicePanel,
            "Your Column Progress would be at "+firstSeq+" & "+secondSeq+".",
            "Confirm Combinations",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
            );
            if (choice == JOptionPane.NO_OPTION) {
            sequence.clear();
              for (int i = 0; i < 4; i++) {
                // Clear the selected dices
                labels[i].setEnabled(true);
                // Remove the borders on the selected dices
                labels[i].setBorder(null);
            }
            resetted = true;
            }
        }
        }
    
    
    }

    private void resetDicePanel() {
        for (int i = 0; i < 4; i++) {
            // Clear the selected dices
            labels[i].setEnabled(true);
            // Remove the borders on the selected dices
            labels[i].setBorder(null);
        }
        // Clear the sums/dices/sequence
        sequence.clear();
        diceNumbers.clear();
        rolled = false;
        resetted = true;
    }

    // not used but must be present to fulfil MouseListener contract
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}
