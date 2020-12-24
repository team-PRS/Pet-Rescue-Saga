package prs;
import prs.graphics.*;
import prs.usuel.image;
import java.awt.Image;

/**
{@summary Contain a lot of usefull fonction to modify the graphics interface.}
*/
public class Controller {

  // CONSTRUCTORS --------------------------------------------------------------
  private Frame frame;
  private PanelMap pMap;
  private PanelPlateau pPlateau;
  private PanelGame pGame;
  private static Data data = new Data();
  // GET SET -------------------------------------------------------------------
  public Frame getFrame(){return frame;}
  public int getWidthMax(){return getFrame().getWidth();}
  public int getHeightMax(){return getFrame().getHeight();}
  public PanelPlateau getPPlateau(){return pPlateau;}
  public static Data getData(){return data;}
  // FUNCTIONS -----------------------------------------------------------------
  public boolean addFrame(){
    try {
      frame = new Frame();
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  public boolean addPanelMap(){
    try {
      pMap = new PanelMap();
      frame.setContentPane(pMap);
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  /**
  *Add a Panel to represent a level.
  */
  public boolean addPanelPlateau(int id){
    try {
      pGame = new PanelGame();
      frame.setContentPane(pGame);
      //TODO load the save of the id level & ask pPlateau to print it.
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  /**
  *Add a Panel to represent a level.
  */
  public boolean addPanelPlateau(Plateau p){
    try {
      pPlateau = new PanelPlateau();
      pPlateau.setPlateau(p);
      frame.setContentPane(new PanelGame(pPlateau));
      setPanelPlateauSize();
      //TODO load the save of the id level & ask pPlateau to print it.
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  public boolean setPanelPlateauSize(){
    try {
      int dimX = 1+getData().getTailleDUneCase()*pPlateau.getPlateau().getWidth();
      int dimY = 1+getData().getTailleDUneCase()*pPlateau.getPlateau().getHeight();
      int xCenter = (getWidthMax()-dimX)/2;
      int yCenter = (getHeightMax()-dimY)/2;
      pPlateau.setBounds(xCenter,yCenter,dimX,dimY);
      System.out.println(xCenter+" "+yCenter);
      pGame.revalidate();
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  /**
  *Load game images.
  */
  public boolean iniImage(){
    boolean ok = true;
    Image img = image.getImage("background.jpg");
    try {
      img = img.getScaledInstance(getWidthMax(),getHeightMax() ,Image.SCALE_SMOOTH);
      Controller.getData().setPMapImg(img);
    }catch (Exception e) {
      ok=false;
    }
    Image img2 = image.getImage("background2.jpg");
    try {
      img2 = img2.getScaledInstance(getWidthMax(),getHeightMax() ,Image.SCALE_SMOOTH);
      Controller.getData().setPPlateauImg(img2);
    }catch (Exception e) {
      ok=false;
    }
    return true;
  }
  public void repaint(){
    frame.repaint();
  }
  /**
  * Unlock the next level
  */
  public boolean addLevel(){
    if(pMap.getNbrButton() < data.getNbrLevelAviable()){
      pMap.addLevel();
      return true;
    }
    return false;
  }

  //static
  public static void pause(int ms){
    //if(ms<1){error.error("fail to pause "+ms);}
    try {
        Thread.sleep(ms);
    } catch (InterruptedException ie) {
        //error.error("fail to pause "+ms);
    }
  }
  // SUB-CLASS -----------------------------------------------------------------

}
