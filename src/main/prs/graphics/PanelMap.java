//package prs.graphics;
//import prs.Jeu;
//import javax.swing.JPanel;
//import java.awt.Graphics;
//import javax.swing.JButton;
//import prs.Data;
//import java.awt.event.MouseEvent;
//import javax.swing.event.MouseInputListener;
//import java.awt.Color;

/**
{@summary Represent the map on the Frame.}
*/
//public class PanelMap extends JPanel{
//  private int nbrButton = 0;
//  private Jeu jeu;
//  // CONSTRUCTORS --------------------------------------------------------------
//  public PanelMap(Jeu j){
//    setLayout(null);
//    jeu=j;
//    setBackground(new Color(255,255,255,200));
//    //setExtendedState(JPanel.MAXIMIZED_BOTH);
//  }
//  // GET SET -------------------------------------------------------------------
//  public int getNbrButton(){return nbrButton;}
//  // FUNCTIONS -----------------------------------------------------------------
//  public void paintComponent(Graphics g){
//    super.paintComponent(g);
//    System.out.println("paint map");//@a
//    if(jeu.getData().getPMapImg() != null){
//        System.out.println("paint map with image");//@a
//      g.drawImage(jeu.getData().getPMapImg(),0,0,this);
//    }
//  }
//  public void addLevel(){
//    add(new Button());
//  }
//  public int getDimX(){return getWidth();}
//  public int getDimY(){return getHeight();}
//  // SUB-CLASS -----------------------------------------------------------------
//  public class Button extends JButton implements MouseInputListener{
//    private final int id;
//    // CONSTRUCTORS --------------------------------------------------------------
//    /**
//    * Create a new JButton with x, y, wheigth & heigth that depend of the sise of the windows.
//    */
//    public Button(){
//      id=1+nbrButton++;
//      setBounds(((id-1)*getDimX())/(Data.getNbrLevelAviable()+1),((id-1)*getDimY())/(Data.getNbrLevelAviable()+1),getDimX()/(2*Data.getNbrLevelAviable()),getDimY()/(2*Data.getNbrLevelAviable()));
//      setText(id+"");
//      setFont(Data.getFont());
//      addMouseListener(this);
//      setVisible(true);
//    }
//    // GET SET -------------------------------------------------------------------
//
//    // FUNCTIONS -----------------------------------------------------------------
//    public void paintComponent(Graphics g){
//      super.paintComponent(g);
//    }
//    //mouse action
//    public void mouseClicked(MouseEvent e){
//      System.out.println("launch of "+id+" level.");//@a
//      jeu.launchLevel(jeu.getLevel());
//    }
//    public void mouseEntered(MouseEvent e){}
//    public void mouseExited(MouseEvent e){}
//    public void mousePressed(MouseEvent e){}
//    public void mouseReleased(MouseEvent e){}
//
//    public void mouseDragged(MouseEvent e){}
//    public void mouseMoved(MouseEvent e){}
//  }
//}
