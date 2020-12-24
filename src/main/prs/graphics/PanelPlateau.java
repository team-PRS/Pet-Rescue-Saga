package prs.graphics;
import prs.Controller;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelPlateau extends JPanel{

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelPlateau(){
    //setExtendedState(JPanel.MAXIMIZED_BOTH);
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(Controller.getData().getPPlateauImg() != null){
      g.drawImage(Controller.getData().getPPlateauImg(),0,0,this);
    }
  }
  // SUB-CLASS -----------------------------------------------------------------

}
