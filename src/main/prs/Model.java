package prs;
import prs.graphics.*;
import prs.usuel.image;
import java.awt.Image;

public class Model {

  // CONSTRUCTORS --------------------------------------------------------------
  private Frame frame;
  private PanelMap pMap;
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
  public boolean iniImage(){
    Image img = image.getImage("background.jpg");
    if(img==null){return false;}
    img = img.getScaledInstance(frame.getWidth(),frame.getHeight() ,Image.SCALE_SMOOTH);
    Controller.getData().setPMapImg(img);
    return true;
  }
  public void repaint(){
    frame.repaint();
  }
  // SUB-CLASS -----------------------------------------------------------------

}
