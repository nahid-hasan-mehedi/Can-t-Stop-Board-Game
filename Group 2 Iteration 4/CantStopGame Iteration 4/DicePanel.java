import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class DicePanel extends JPanel implements ActionListener,MouseListener {

    public JLabel playerLabel,statusLabel,combLabel;
    private JPanel dicePanel, buttonPanel, bottomPanel;
    private JButton rollButton;
    private JButton takeTurn;
    private JButton endTurn;
    private JPanel[] dices;
    private JLabel[] labels;
    private JLabel selectedDice;
    public Boolean rolled = false, resetted = false;
    private ImageIcon[] diceIcons = new ImageIcon[6];
    private static final ArrayList<Integer> diceNumbers = new ArrayList<Integer>();
    private Integer rand,first,second,third,fourth;
    public int sum,remainingSum,total,firstSeq,secondSeq;
    private ArrayList<Integer> sequence = new ArrayList<>();
    public CantStopGame game;
    public Player whosTurn;
    public int whosCount = 0;
    public TakeATurn turn;
    public ArrayList<JLabel> selectedDices;
    public Boolean currentMode;
    
    public DicePanel(CantStopGame c,Boolean currentMode) {
        
        game = c;
        this.currentMode = currentMode;
        setLayout(new BorderLayout());
        
        JPanel playerStatus = new JPanel(new BorderLayout());
        // create the player label with an image icon
        playerLabel = new JLabel(new ImageIcon("p1.png"));
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        playerStatus.add(playerLabel, BorderLayout.NORTH);
        
        statusLabel = new JLabel();
        
        whosTurn = game.player[whosCount];
        for (int i = 0; i < 4; i++){
            if (game.player[i] != null){
            if (whosTurn == null){
                
                whosCount = i;
                whosTurn = game.player[whosCount];
                
            }
        }}
        
        if (whosTurn.isHuman){
            statusLabel.setText("<html><div style='text-align:center'>" + game.player[whosCount].getName()+"'s Turn" + "<br>(Human)</html>");
        }else if (!whosTurn.isHuman){
            statusLabel.setText("<html><div style='text-align:center'>" + game.player[whosCount].getName()+"'s Turn" + "<br>(Computer)</html>");
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
        
        if(!whosTurn.isHuman()){
            whosTurn.takeTurn(this);
        }
        
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
        else if (e.getSource() == takeTurn) {
            if (!rolled) {
                JOptionPane.showMessageDialog(
                        dicePanel,
                        "Please Roll The Dices First!",
                        "Dices Not Rolled",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
            }
        
            if (sequence.size() < 4) {
                JOptionPane.showMessageDialog(
                    dicePanel,
                    "Please select the Combinations!",
                    "No combinations selected",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            else {
                //You have to implement AI here so please check if the player is human send it to take a turn and otherwise AI
                this.turn = new TakeATurn(whosTurn,game,this,currentMode);
                resetDicePanel();
                sequence.clear();
            }
        }
        
        else if (e.getSource() == endTurn) {
            if (whosTurn.currentColumnsOccupied.size() == 0){
                JOptionPane.showMessageDialog(
                            dicePanel,
                            "Please Take The Turn First!",
                            "Turn Not Taken",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
            }
            sequence.clear();
            for (int i = 0; i < 4; i++) {
                // Clear the selected dices
                labels[i].setEnabled(true);
                // Remove the borders on the selected dices
                labels[i].setBorder(null);
            }
            resetted = true;
            turn.endTurn();
        }
    }

    
    public void mouseClicked(MouseEvent mevt) {
        Object selected = mevt.getSource();
        selectedDices = new ArrayList<>();
        if (selected instanceof JLabel) {
            JLabel selectedDice = (JLabel) selected;
            if (selectedDice.isEnabled()) { // check if the dice is already selected
                if (!rolled || !resetted) {
                    JOptionPane.showMessageDialog(
                            dicePanel,
                            "Please Roll The Dices First!",
                            "Dices Not Rolled",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                } else {
                    selectedDice.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    selectedDice.setEnabled(false);
                    selectedDices.add(selectedDice);
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
                    firstSeq = sequence.get(0)+sequence.get(1);
                    secondSeq = sequence.get(2)+sequence.get(3);
                    int choice = JOptionPane.showConfirmDialog(
                    dicePanel,
                    "Your Column Progress would be at "+firstSeq+" & "+secondSeq+".",
                    "Confirm Combinations",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
                    );
                    if (choice == JOptionPane.NO_OPTION) {
                      sequence.clear();
                      for (JLabel dice : selectedDices) { // enable the previously selected dices
                        dice.setEnabled(true);
                        dice.setBorder(null);
                      }
                      selectedDices.clear();
                      resetted = false;
                      JOptionPane.showMessageDialog(
                            dicePanel,
                            "Please Roll The Dices Again!",
                            "Combination Selection Cancelled",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    }
                }
            }
        }
    }

    public void resetDicePanel() {
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
    
    public void reset() {
        // Reset all the dice labels to the initial images
        for (int i = 0; i < 4; i++) {
            ImageIcon icon = new ImageIcon("dice_"+Integer.toString(i+1)+".png");
            labels[i].setIcon(icon);
        }
        
        // Reset the roll button
        rollButton.setEnabled(true);
        
        // Reset the rolled and resetted flags
        rolled = false;
        resetted = true;
        
        // Reset the selected dices list
        selectedDices.clear();
        
        // Reset the player label and status label
        whosCount = 0;
        whosTurn = game.player[whosCount];
        for (int i = 0; i < 4; i++){
            if (game.player[i] != null){
                if (whosTurn == null){
                    whosCount = i;
                    whosTurn = game.player[whosCount];
                }
            }
        }
        if (whosTurn.isHuman){
            statusLabel.setText("<html><div style='text-align:center'>" + game.player[whosCount].getName()+"'s Turn" + "<br>(Human)</html>");
        }else if (!whosTurn.isHuman){
            statusLabel.setText("<html><div style='text-align:center'>" + game.player[whosCount].getName()+"'s Turn" + "<br>(Computer)</html>");
        }
        
        resetDicePanel();
        // Repaint the panel
        repaint();
        selectedDices.clear();
    }

    public void setComputerDice(int zero, int one, int two, int three){
        first = diceNumbers.get(zero);
        sequence.add(first);
        second = diceNumbers.get(one);
        sequence.add(second);
        third = diceNumbers.get(two);
        sequence.add(third);
        fourth = diceNumbers.get(three);
        sequence.add(fourth);
        firstSeq = sequence.get(zero)+sequence.get(one);
        secondSeq = sequence.get(two)+sequence.get(three);
    }
    
    public void setComputerDiceHard(ComputerPlayer cpu){
        if(cpu.getCurrentColumnsOccupied().isEmpty()){
            setComputerDice(0, 1, 2, 3);
        }
        else{
            boolean found = false;
            for(int i = 0; i < cpu.getCurrentColumnsOccupied().size(); i++){
                if(found){
                    break;
                }
                if(diceNumbers.get(0) + diceNumbers.get(1) == cpu.getCurrentColumnsOccupied().get(i)){
                    setComputerDice(0, 1, 2, 3);
                    found = true;
                }
                else if(diceNumbers.get(0) + diceNumbers.get(2) == cpu.getCurrentColumnsOccupied().get(i)){
                    setComputerDice(0, 2, 1, 3);
                    found = true;
                }
                else if(diceNumbers.get(0) + diceNumbers.get(3) == cpu.getCurrentColumnsOccupied().get(i)){
                    setComputerDice(0, 3, 1, 2);
                    found = true;
                }
                else if(diceNumbers.get(1) + diceNumbers.get(2) == cpu.getCurrentColumnsOccupied().get(i)){
                    setComputerDice(1, 2, 0, 3);
                    found = true;
                }
                else if(diceNumbers.get(1) + diceNumbers.get(3) == cpu.getCurrentColumnsOccupied().get(i)){
                    setComputerDice(1, 3, 0, 2);
                    found = true;
                }
                else if(diceNumbers.get(2) + diceNumbers.get(3) == cpu.getCurrentColumnsOccupied().get(i)){
                    setComputerDice(2, 3, 0, 1);
                    found = true;
                }
            }
            if(!found){
                setComputerDice(0, 1, 2, 3);
            }
        }
    }
    
    public JLabel getDiceLabel(int i){
        return labels[i];
    }

    public void cpuClickRollButton(){
        resetDicePanel();
        for (int i = 0; i < 4; i++) {
            animateRoll(labels[i]);
        }
        rolled = true;
    }
    
    public JButton getTakeTurnButton(){
        return takeTurn;
    }
    
    public JButton getEndTurnButton(){
        return endTurn;
    }
    
    public Player getPlayer(){
        return whosTurn;
    }

    // not used but must be present to fulfil MouseListener contract
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}
