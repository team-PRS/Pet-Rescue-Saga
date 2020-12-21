package prs;
import prs.graphics.*;
import prs.usuel.image;
import java.awt.Image;

/**
{@summary Contain a lot of usefull fonction to modify the graphics interface.}
*/
public class Model {

  // CONSTRUCTORS --------------------------------------------------------------
  private Frame frame;
  private PanelMap pMap;
  private PanelPlateau pPlateau;
  // GET SET -------------------------------------------------------------------

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
  public boolean addPanelPlateau(){
    try {
      pPlateau = new PanelPlateau();
      frame.setContentPane(pPlateau);
      return true;
    }catch (Exception e) {
      return false;
    }
  }
  public boolean iniImage(){
    boolean ok = true;
    Image img = image.getImage("background.jpg");
    try {
      img = img.getScaledInstance(frame.getWidth(),frame.getHeight() ,Image.SCALE_SMOOTH);
      Controller.getData().setPMapImg(img);
    }catch (Exception e) {
      ok=false;
    }
    Image img2 = image.getImage("background2.jpg");
    try {
      img2 = img2.getScaledInstance(frame.getWidth(),frame.getHeight() ,Image.SCALE_SMOOTH);
      Controller.getData().setPPlateauImg(img2);
    }catch (Exception e) {
      ok=false;
    }
    return true;
  }
  public void repaint(){
    frame.repaint();
  }
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
