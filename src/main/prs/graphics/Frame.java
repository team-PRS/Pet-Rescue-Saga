package prs.graphics;
import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

/**
{@summary Main Frame.}
*/
public class Frame extends JFrame{

  // CONSTRUCTORS --------------------------------------------------------------
  public Frame(){
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

}
