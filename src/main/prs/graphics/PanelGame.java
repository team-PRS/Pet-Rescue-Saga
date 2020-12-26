package prs.graphics;
import prs.Jeu;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelGame extends JPanel{
  private PanelPlateau pPlateau;
  private Jeu jeu;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelGame(PanelPlateau pp){
    setOpaque(false);
    setLayout(null);
    add(pp);
    pPlateau=pp;
  }
  public PanelGame(){
    this(new PanelPlateau());
  }
  // GET SET -------------------------------------------------------------------
  public void setPPlateau(PanelPlateau pp){pPlateau=pp;}
  public void setJeu(Jeu j){jeu=j; pPlateau.setJeu(j);}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(jeu.getData().getPPlateauImg() != null){
      g.drawImage(jeu.getData().getPPlateauImg(),0,0,this);
    }
    super.paintComponent(g);
  }
}
