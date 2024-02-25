import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

public class InstructionsWindow
{
  private JButton readInstruction;
  private JPanel topPanel;
  
  public InstructionsWindow(){
  JFrame rIns = new JFrame("Game Instructions");
  JPanel insPanel1; 
  JPanel insPanel2;
  JScrollPane spIns;
  
  rIns.setSize(1280,980);
 
  insPanel1 = new JPanel();
  insPanel1.setSize(500,500);
  insPanel1.setLayout(new BorderLayout());
  spIns = new JScrollPane(insPanel1);
  spIns.getVerticalScrollBar().setUnitIncrement(16);
  spIns.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
  spIns.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  rIns.add(spIns);
  
  ImageIcon p1 = new ImageIcon("Cant-Stop-Rules.png");
  
  p1.setImage(p1.getImage().getScaledInstance(1200,1000, Image.SCALE_DEFAULT));
  JLabel l1 = new JLabel(p1);
  insPanel1.add(l1,BorderLayout.PAGE_START);
  
  ImageIcon p2 = new ImageIcon("Cant-Stop-Rules-2.png");
  p2.setImage(p2.getImage().getScaledInstance(1200,1000, Image.SCALE_DEFAULT));
  
  JLabel l2 = new JLabel(p2);
  
  insPanel1.add(l2,BorderLayout.CENTER);
  
  //rIns.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  rIns.setResizable(true);
  rIns.setVisible(true);
  
  JButton svideo = new JButton("Click Here for a short video explaining the game");
  svideo.setBackground(Color.yellow);
  svideo.setOpaque(true);
  svideo.setBorderPainted(false);
  svideo.setFont(new Font("Arial", Font.PLAIN, 20));
  
  insPanel1.add(svideo,BorderLayout.PAGE_END);
  
  svideo.addActionListener( e-> {
    try {
         
        Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=VUGvOQatVDc"));
         
    } catch (IOException | URISyntaxException e1) {
        e1.printStackTrace();
    }
  });
  
  
   
  
  }
   public static void main(String[] var0) {
        new InstructionsWindow();
    }
}