package prs.graphics;
import prs.GuiPrs;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import prs.Data;
import prs.Plateau;
import prs.*;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
{@summary Represent the plateau on the Frame.}
*/
public class PanelPlateau extends JPanel implements MouseListener{
  private Plateau plateau;                                            //TODO VIOLENCE View Model Controller everywhere
  private Jeu motor;
  private GuiPrs jeu;

  // CONSTRUCTORS --------------------------------------------------------------
  public PanelPlateau(){
    setOpaque(false);
    setLayout(null);
    addMouseListener(this);
    //setBackground(new Color(255,255,255,150)); don't work.
  }
  // GET SET -------------------------------------------------------------------
  public Plateau getPlateau(){return motor.getPlateau();}
  public void setPlateau(Plateau p){plateau=p;}                        //TODO VIOLENCE View Model Controller
  public void setMotor(Jeu ExtMotor){this.motor=ExtMotor;}                        //TODO VIOLENCE View Model Controller
  public void setJeu(GuiPrs j){jeu=j;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    if(plateau!=null){
      g.setColor(new Color(255,255,255,180));
      g.fillRect(0,0,getWidth(),getHeight());
      paintCase(g);
      paintGrid(g);
    }
  }
  public void paintCase(Graphics g){
    for (int i = 0; i < plateau.getWidth(); i++){
      for (int j = 0; j < plateau.getHeight(); j++){
        int xTemp = j*jeu.getData().getTailleDUneCase();
        int yTemp = i*jeu.getData().getTailleDUneCase();

        ObjectSurCase obj = plateau.getObject(j,i);

        if(obj instanceof Bloc){
          Bloc b = (Bloc) plateau.getObject(j,i);
          if(b.getColor()!="NONE"){
            drawColorRect(i,j,b.getColor2(),g);
          }else{
            g.drawImage(jeu.getData().getInmovable(),yTemp,xTemp,this);
          }
        }
        else if(obj instanceof Animal)
        {
          if (((Animal)obj).getType().equals("DOG"))
          {g.drawImage(jeu.getData().getDog(),yTemp,xTemp,this);}

          else if (((Animal)obj).getType().equals("CAT"))
          {g.drawImage(jeu.getData().getCat(),yTemp,xTemp,this);}

          else if (((Animal)obj).getType().equals("FISH"))
          {g.drawImage(jeu.getData().getFish(),yTemp,xTemp,this);}

          else if (((Animal)obj).getType().equals("MOUSE"))
          {g.drawImage(jeu.getData().getMouse(),yTemp,xTemp,this);}


        }
        else if(obj instanceof Bomb){
          g.drawImage(jeu.getData().getBomb(),yTemp,xTemp,this);

        }else if(obj instanceof Ballon){
            Ballon b = (Ballon) obj;
          drawballon(i,j,b.getColor2(),g);
        }
      }
    }
  }
  public void paintGrid(Graphics g){
    g.setColor(Color.BLACK);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(1));
    int xCase = plateau.getWidth();
    int yCase = plateau.getHeight();
    for (int i=0;i<xCase+1 ;i++ ) {
      int xT = jeu.getData().getTailleDUneCase()*i;
      g.drawLine(xT,0,xT,jeu.getData().getTailleDUneCase()*yCase);
    }
    for (int i=0;i<yCase+1 ;i++ ) {
      int xT = jeu.getData().getTailleDUneCase()*i;
      g.drawLine(0,xT,jeu.getData().getTailleDUneCase()*xCase,xT);
    }
  }
  public void drawColorRect(int i, int j, Color c, Graphics g){
    int tc = jeu.getData().getTailleDUneCase();
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
  public void drawballon(int i, int j, Color c, Graphics g){
      int tc = jeu.getData().getTailleDUneCase();
      g.setColor(c);
      g.fillOval(tc*i+tc/10,tc*j,(tc*8)/10,(tc*9)/10);
      g.setColor(Color.BLACK);
      g.drawOval(tc*i+tc/10,tc*j,(tc*8)/10,(tc*9)/10);
  }
  //public class Listener implements MouseListener{
    public void mouseClicked(MouseEvent e){
        System.out.println("clic en "+e.getY()+" "+e.getX());//@a
        jeu.clicOnPlateau(e.getY(),e.getX()); // /!\ our x & y are revers to the java usualx & y.
        //jeu.setClic(true);
        jeu.GUIClicAction();
        jeu.repaint();
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent event) {}
    public void mousePressed(MouseEvent event) { }
    public void mouseReleased(MouseEvent event) { }
  //}
  // SUB-CLASS -----------------------------------------------------------------
}
