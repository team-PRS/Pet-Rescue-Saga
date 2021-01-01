package prs.graphics;
import prs.Jeu;
import javax.swing.JPanel;
import java.awt.Graphics;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelGame extends JPanel{
  private PanelPlateau pPlateau;
  private PanelInfo pInfo;
  private Jeu jeu;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelGame(PanelPlateau pp, PanelInfo pi){
    setOpaque(false);
    setLayout(null);
    add(pp);
    add(pi);
    pPlateau=pp;
    pInfo=pi;
  }
 /* public PanelGame(){
        this(new PanelPlateau(),new Panel);
    }*/
  // GET SET -------------------------------------------------------------------
  public void setPPlateau(PanelPlateau pp){pPlateau=pp;}
  public void setJeu(Jeu j){jeu=j; pPlateau.setJeu(j);}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(jeu.getData().getPPlateauImg() != null){
      g.drawImage(jeu.getData().getPPlateauImg(),0,0,this);
    }
    super.paintComponent(g);
  }
}
