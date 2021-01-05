package prs.graphics;

import prs.GuiPrs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Graphics;

/**
{@summary Main Frame.}
*/
public class Frame extends JFrame{
  private GuiPrs jeu;
  // CONSTRUCTORS --------------------------------------------------------------
  public Frame(GuiPrs j){
    jeu=j;
    setTitle("Pet Rescue Saga");
    setResizable(true);
    //setLocationRelativeTo(null); // fenetre centrée
    setExtendedState(JFrame.MAXIMIZED_BOTH);//Frame take as many space as it can.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // bouton fermer par défaut.
    //fullScreen
    /*
    setUndecorated(true);
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    gd.setFullScreenWindow(this);
    */
    setVisible(true);
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void addBackgroud(){
      setContentPane(new PanelNull());
      jeu.paintAll();
  }
  // SUB-CLASS -----------------------------------------------------------------
  class PanelNull extends JPanel{
      public PanelNull(){
        setOpaque(false);
        setLayout(null);
      }
      public void paintComponent(Graphics g){
          super.paintComponent(g);
          if(jeu.getData().getPMapImg() != null){
              g.drawImage(jeu.getData().getPMapImg(),0,0,this);
          }
      }
  }
}
