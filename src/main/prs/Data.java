package prs;
import java.awt.Image;
import java.awt.Font;

/**
*{@summary Contain all data (images) that will be used by the graphic interface.}
*@author Hydrolien
*/
public class Data {
  private Image pMapImg;
  private Image pPlateauImg;
  private int screenDimX;
  private int screenDimY;
  private static int nbrLevelAviable=4;
  private static Font font=new Font("FreeSans",Font.BOLD,60);
  // CONSTRUCTORS --------------------------------------------------------------

  // GET SET -------------------------------------------------------------------
  public Image getPMapImg(){return pMapImg;}
  public void setPMapImg(Image i){pMapImg = i;}
  public Image getPPlateauImg(){return pPlateauImg;}
  public void setPPlateauImg(Image i){pPlateauImg = i;}
  public int getScreenDimX(){return screenDimX;}
  public void setScreenDimX(int x){screenDimX=x;}
  public int getScreenDimY(){return screenDimY;}
  public void setScreenDimY(int x){screenDimY=x;}
  public static int getNbrLevelAviable(){return nbrLevelAviable;}
  public static Font getFont(){return font;}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------

}
