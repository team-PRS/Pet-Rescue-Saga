package prs.graphics;
import prs.Controller;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import prs.Data;
import prs.Plateau;
import prs.*;
import java.awt.Color;
import java.awt.BasicStroke;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelPlateau extends JPanel{
  private Plateau plateau;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelPlateau(){
    setOpaque(false);
    setLayout(null);
    //setExtendedState(JPanel.MAXIMIZED_BOTH);
  }
  // GET SET -------------------------------------------------------------------
  public Plateau getPlateau(){return plateau;}
  public void setPlateau(Plateau p){plateau=p;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(plateau!=null){
      paintCase(g);
      paintGrid(g);
    }
  }
  public void paintCase(Graphics g){
   // for (int i = 0; i < plateau.getWidth(); i++){
   //   for (int j = 0; j < plateau.getHeight(); j++){
   //     if(plateau.getObject(j,i) instanceof Bloc){
   //       Bloc b = (Bloc) plateau.getObject(j,i);
   //       if(b.getColorName()!="NONE"){
   //         drawColorRect(i,j,b.getColor(),g);
   //       }else{
   //         //g.drawImage(...);
   //       }
   //     }else if(plateau.getObject(j,i) instanceof Animal){
   //       //g.drawImage(...);
   //     }else if(plateau.getObject(j,i) instanceof Outil){
   //       //g.drawImage(...);
   //     }
   //   }
   // }
  }
  public void paintGrid(Graphics g){
    g.setColor(Color.BLACK);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(1));
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
  }
  public void drawColorRect(int i, int j, Color c, Graphics g){
    int tc = Controller.getData().getTailleDUneCase();
    g.setColor(c);
    g.fillRect(tc*i,tc*j,tc,tc);
    //graphics bonus
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(tc/8));
    g2d.setColor(new Color(100,100,100,100));
    g2d.drawRect(tc*i+tc/16,tc*j+tc/16,tc-tc/8,tc-tc/8);
    g2d.setStroke(new BasicStroke(2));
    g2d.setColor(new Color(255,255,255,200));
    g2d.drawLine(tc*i,tc*j,tc*i+tc/8,tc*j+tc/8);
    g2d.setColor(new Color(255,255,255,80));
    g2d.drawLine(tc*i,tc*(j+1),tc*i+tc/8,tc*(j+1)-tc/8);
    g2d.setColor(new Color(255,255,255,180));
    g2d.drawLine(tc*(i+1),tc*j,tc*(i+1)-tc/8,tc*j+tc/8);
    g2d.setColor(new Color(255,255,255,50));
    g2d.drawLine(tc*(i+1),tc*(j+1),tc*(i+1)-tc/8,tc*(j+1)-tc/8);
  }
  // SUB-CLASS -----------------------------------------------------------------

}
