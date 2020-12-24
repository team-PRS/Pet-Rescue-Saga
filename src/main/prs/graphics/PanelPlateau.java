package prs.graphics;
import prs.Controller;
import javax.swing.JPanel;
import java.awt.Graphics;
import prs.Data;
import prs.Plateau;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelPlateau extends JPanel{
  private Plateau plateau;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelPlateau(){
    //setExtendedState(JPanel.MAXIMIZED_BOTH);
  }
  // GET SET -------------------------------------------------------------------
  public void setPlateau(Plateau p){plateau=p;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(Controller.getData().getPPlateauImg() != null){
      g.drawImage(Controller.getData().getPPlateauImg(),0,0,this);
    }
    if(plateau!=null){
      paintCase(g);
      paintGrid(g);
    }
  }
  public void paintCase(Graphics g){
    for (int i = 0; i < plateau.getHeight(); i++){
      for (int j = 0; j < plateau.getWidth(); j++){
        //if(plateau.getObject(i,j) instanceof Blocs){

        //}
      }
    }
  }
  public void paintGrid(Graphics g){
    int xCase = plateau.getWidth();
    int yCase = plateau.getHeight();
    for (int i=0;i<xCase+1 ;i++ ) {
      int xT = Controller.getData().getTailleDUneCase()*i;
      g.drawLine(xT,0,xT,Controller.getData().getTailleDUneCase()*yCase);
    }
    for (int i=0;i<yCase+1 ;i++ ) {
      int xT = Controller.getData().getTailleDUneCase()*i;
      g.drawLine(0,xT,Controller.getData().getTailleDUneCase()*xCase,xT);
    }
    System.out.println((xCase+yCase)+" lines have been paint");//@a
  }
  // SUB-CLASS -----------------------------------------------------------------

}
