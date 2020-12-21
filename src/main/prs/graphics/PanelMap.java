package prs.graphics;
import prs.Controller;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
{@summary Represent the map on the Frame.}
*/
public class PanelMap extends JPanel{

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelMap(){
    //setExtendedState(JPanel.MAXIMIZED_BOTH);
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(Controller.getData().getPMapImg() != null){
      g.drawImage(Controller.getData().getPMapImg(),0,0,this);
    }
  }
  // SUB-CLASS -----------------------------------------------------------------

}
