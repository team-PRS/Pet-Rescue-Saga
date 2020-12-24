package prs.graphics;
import prs.Controller;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelGame extends JPanel{
  private PanelPlateau pPlateau;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelGame(PanelPlateau pp){
    setOpaque(false);
    setLayout(null);
    add(pp);
  }
  public PanelGame(){
    this(new PanelPlateau());
  }
  // GET SET -------------------------------------------------------------------
  public void setPPlateau(PanelPlateau pp){pPlateau=pp;}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(Controller.getData().getPPlateauImg() != null){
      g.drawImage(Controller.getData().getPPlateauImg(),0,0,this);
    }
    super.paintComponent(g);
  }
}
