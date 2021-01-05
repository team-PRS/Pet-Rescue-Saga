package prs.graphics;
import prs.GuiPrs;
import javax.swing.JPanel;
import java.awt.Graphics;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import java.awt.Color;

/**
{@summary Represent the map on the Frame.}
*/
public class PanelMap extends JPanel{
  private int nbrButton;
  private GuiPrs jeu;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanelMap(GuiPrs j){
    nbrButton = 0;
    setLayout(null);
    jeu=j;
    setBackground(new Color(255,255,255,200));
    //setExtendedState(JPanel.MAXIMIZED_BOTH);
  }
  // GET SET -------------------------------------------------------------------
  public int getNbrButton(){return nbrButton;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if(jeu.getData().getPMapImg() != null){
      g.drawImage(jeu.getData().getPMapImg(),0,0,this);
    }
  }
  public void addLevel(){
      int nbrDeLevelAPrint=jeu.getMotor().getCompte().getLastUnlockLevel();
      while(nbrDeLevelAPrint>0){
          add(new Button());
          nbrDeLevelAPrint--;
      }
  }
  public int getDimX(){return getWidth();}
  public int getDimY(){return getHeight();}
  // SUB-CLASS -----------------------------------------------------------------
  public class Button extends JButton implements MouseInputListener{
    private final int id;
    // CONSTRUCTORS --------------------------------------------------------------
    /**
    * Create a new JButton with x, y, wheigth & heigth that depend of the sise of the windows.
    */
    public Button(){
      id=1+nbrButton++;
      setBounds(((id-1)*getDimX())/(jeu.getData().getNbrLevelAviable()+1),((id-1)*getDimY())/(jeu.getData().getNbrLevelAviable()+1),getDimX()/(2*jeu.getData().getNbrLevelAviable()),getDimY()/(2*jeu.getData().getNbrLevelAviable()));
      setText(id+"  "+"score:"+jeu.getMotor().getCurrentJoueur().getCompte().getScore(id));
      setFont(jeu.getData().getFont());
      addMouseListener(this);
      setVisible(true);
      setBorderPainted(true);
      setOpaque(true);
      setBackground(jeu.getData().getBackgroundColor());
    }
    // GET SET -------------------------------------------------------------------

    // FUNCTIONS -----------------------------------------------------------------
    @Override
    public void paintComponent(Graphics g){
      super.paintComponent(g);
    }
    //mouse action
    public void mouseClicked(MouseEvent e){
      System.out.println("launch of "+id+" level.");//@a
      jeu.launchLevel(id);
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
  }
}
