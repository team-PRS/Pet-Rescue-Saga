package prs.graphics;
import prs.GuiPrs;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Font;

import prs.Jeu;
import prs.map.Compte;
/**
{@summary Represent the player info in gold and tools.}
*/
public class PanelInfo extends JPanel{

    private Jeu motor;
    private JLabel score;
    private JLabel gold;
    //private JLabel bomb;
    private JLabel ballon;
    private JButton addBallon;
    private JButton placeBallon;
    private JButton backToMap;
    private JButton iaFinish;
    //private JButton retry;
    private GuiPrs jeu;
    // CONSTRUCTORS --------------------------------------------------------------
    public PanelInfo(Jeu m, GuiPrs j){
        jeu=j;
        motor=m;
        //create & add all component.
        score = new JLabel();
        gold = new JLabel();
        //bomb = new JLabel();
        ballon = new JLabel();
        addBallon = new JButton();
        placeBallon = new JButton();
        backToMap = new JButton();
        iaFinish = new JButton();
        add(score);
        add(gold);
        add(ballon);
        add(addBallon);
        add(placeBallon);
        add(backToMap);
        add(iaFinish);
        setFont();
    }
    // GET SET -------------------------------------------------------------------
    public JButton getAddBallon(){return addBallon;}
    public JButton getPlaceBallon(){return placeBallon;}
    public JButton getBackToMap(){return backToMap;}
    public JButton getIaFinish(){return iaFinish;}
    // FUNCTIONS -----------------------------------------------------------------
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      setText2();
      setLocation2();
    }
    private void setText2(){
        score.setText("Points : "+motor.getCurrentJoueur().getCompte().getPoints());
        gold.setText("Gold : "+motor.getCurrentJoueur().getCompte().getGold());
        ballon.setText("Ballon : "+motor.getCurrentJoueur().getCompte().getBallon());
        addBallon.setText("Buy 1 ballon");
        placeBallon.setText("Place 1 ballon");
        backToMap.setText("Abandon");
        iaFinish.setText("Bot's game");
    }
    private void setLocation2(){
        int k=0;
        score.setLocation(0,40*k);k++;
        gold.setLocation(0,40*k);k++;
        ballon.setLocation(0,40*k);k++;
        addBallon.setLocation(0,40*k);k++;
        placeBallon.setLocation(0,40*k);k++;
        backToMap.setLocation(0,40*k);k++;
        iaFinish.setLocation(0,40*k);k++;
    }
    private void setFont(){
        //font
        Font font = new Font("Arial",Font.BOLD,30);
        score.setFont(font);
        gold.setFont(font);
        ballon.setFont(font);
        addBallon.setFont(font);
        placeBallon.setFont(font);
        backToMap.setFont(font);
        iaFinish.setFont(font);
    }
}
