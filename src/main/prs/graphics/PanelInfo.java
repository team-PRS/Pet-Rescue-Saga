package prs.graphics;
import prs.GuiPrs;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Font;

import prs.map.Compte;
/**
{@summary Represent the player info in gold and tools.}
*/
public class PanelInfo extends JPanel{
    private Compte compte;
    private JLabel score;
    private JLabel gold;
    //private JLabel bomb;
    private JLabel ballon;
    private JButton addBallon;
    private JButton placeBallon;
    //private JButton retry;
    private GuiPrs jeu;
    // CONSTRUCTORS --------------------------------------------------------------
    public PanelInfo(Compte c, GuiPrs j){
        jeu=j;
        compte=c;
        score = new JLabel();
        gold = new JLabel();
        //bomb = new JLabel();
        ballon = new JLabel();
        addBallon = new JButton();
        placeBallon = new JButton();
        add(score);
        add(gold);
        add(ballon);
        add(addBallon);
        add(placeBallon);
        //font
        Font font = new Font("Arial",Font.BOLD,30);
        score.setFont(font);
        gold.setFont(font);
        ballon.setFont(font);
        addBallon.setFont(font);
        placeBallon.setFont(font);
    }
    // GET SET -------------------------------------------------------------------
    public JButton getAddBallon(){return addBallon;}
    public JButton getPlaceBallon(){return placeBallon;}
    // FUNCTIONS -----------------------------------------------------------------
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      //setSize(200,60);
      score.setText("score: "+compte.getPoints());
      gold.setText("gold: "+compte.getGold());
      ballon.setText("ballon: "+compte.getBallon());
      addBallon.setText("Buy 1 ballon");
      placeBallon.setText("Place 1 ballon");
      int k=0;
      score.setLocation(0,40*k);k++;
      gold.setLocation(0,40*k);k++;
      ballon.setLocation(0,40*k);k++;
      addBallon.setLocation(0,40*k);k++;
      placeBallon.setLocation(0,40*k);k++;
    }
}
