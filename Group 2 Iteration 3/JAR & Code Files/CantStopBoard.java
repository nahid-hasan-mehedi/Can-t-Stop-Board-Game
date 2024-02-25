import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class CantStopBoard {

  private JPanel[][] panels;
  private int[][] board = new int[13][11];
  private JLabel numbers;
  private JPanel panel;

  public CantStopBoard() {
    panel = new JPanel();
    panel.setLayout(new GridLayout(13, 11));

    panels = new JPanel[13][11];
    String alphaArray[] = {"C", "A", "N", "'T", "S","T","O","P"};
    
    Color boardBackground = new Color(214,217,223);
    Color labelBorderColor = new Color(255,102,102);

    for (int row = 0; row < 13; row++) {
      for (int col = 0; col < 11; col++) {
        panels[row][col] = new JPanel();
        panels[row][col].setBackground(Color.RED); // set background color to white
        panels[row][col].setBorder(BorderFactory.createLineBorder(labelBorderColor, 2));
        panel.add(panels[row][col]);
      }
    }
    
    //BackGround Tiles
    int[][] specialTiles = {
      { 0, 0 },{ 1, 0 },{ 2, 0 },{ 3, 0 },
      { 4, 0 },{ 8, 0 },{ 9, 0 },{ 10, 0 },
      { 11, 0 },{ 12, 0 },{ 0, 1 },{ 1, 1 },
      { 2, 1 },{ 3, 1 },{ 9, 1 },{ 10, 1 },
      { 11, 1 },{ 12, 1 },{ 0, 2 },{ 1, 2 },
      { 2, 2 },{ 10, 2 },{ 11, 2 },{ 12, 2 },
      { 0, 3 },{ 1, 3 },{ 11, 3 },{ 12, 3 },
      { 0, 4 },{ 12, 4 },{ 0, 6 },{ 12, 6 },
      { 0, 7 },{ 1, 7 },{ 11, 7 },{ 12, 7 },
      { 0, 8 },{ 1, 8 },{ 2, 8 },{ 10, 8 },
      { 11, 8 },{ 12, 8 },{ 0, 9 },{ 1, 9 },
      { 2, 9 },{ 3, 9 },{ 9, 9 },{ 10, 9 },
      { 11, 9 },{ 12, 9 },{ 0, 10 },{ 1, 10 },
      { 2, 10 },{ 3, 10 },{ 4, 10 },{ 8, 10 },
      { 9, 10 },{ 10, 10 },{ 11, 10 },{ 12, 10 },
    };
    
    //Number Tiles
    int[][] numberTiles = {
      { 5, 0 },{ 4, 1 },{ 3, 2 },{ 2, 3 },{ 1, 4 },{ 0, 5 },
      { 1, 6 },{ 2, 7 },{ 3, 8 },{ 4, 9 },{ 5, 10 },
    };
    
    //Alphabet Tiles
    int[][] alphabetTiles = {
        { 8, 0 },{ 9, 1 },{ 10, 2 },{ 11, 3 },
      { 11, 7 },{ 10, 8 },{ 9, 9 },{ 8, 10 },
    };

    for (int i = 0; i < specialTiles.length; i++) {
      int row = specialTiles[i][0];
      int col = specialTiles[i][1];
      panels[row][col].setBackground(boardBackground);
      panels[row][col].setBorder(null);
    }
    
    //For showing numbers on the board
    int numberCount = 2;
    for (int i = 0; i < numberTiles.length; i++) {
      int row = numberTiles[i][0];
      int col = numberTiles[i][1];
      panels[row][col].setBackground(Color.RED);
      panels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      JLabel numbers = new JLabel(Integer.toString(numberCount));
      numbers.setFont(new Font("Serif", Font.BOLD, 24));
      numbers.setForeground(Color.WHITE);
      panels[row][col].add(numbers);
      numberCount++;
    }
    
    //For showing alphabets on the board
    for (int i = 0; i < alphabetTiles.length; i++) {
      int row = alphabetTiles[i][0];
      int col = alphabetTiles[i][1];
      panels[row][col].setBackground(Color.RED);
      panels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      JLabel alphas = new JLabel();
      alphas.setText(alphaArray[i]);
      alphas.setFont(new Font("Serif", Font.BOLD, 26));
      alphas.setForeground(Color.WHITE);
      panels[row][col].add(alphas);
    }

    panel.setPreferredSize(new Dimension(600, 600));
  }

  public JPanel getMainPanel() {
    return panel;
  }

  public static void main(String[] args) {
    CantStopBoard board = new CantStopBoard();
    JFrame frame = new JFrame("Can't Stop");
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(board.getMainPanel());
    frame.pack();
    frame.setVisible(true);
  }
}