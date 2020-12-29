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
  private Image animal;
  private static int screenDimX;
  private static int screenDimY;
  private static int nbrLevelAviable=4;
  private static Font font=new Font("FreeSans",Font.BOLD,60);
  private static int tailleDUneCase = 100;
  // CONSTRUCTORS --------------------------------------------------------------

  // GET SET -------------------------------------------------------------------
  public Image getPMapImg(){return pMapImg;}
  public void setPMapImg(Image i){pMapImg = i;}
  public Image getPPlateauImg(){return pPlateauImg;}
  public void setPPlateauImg(Image i){pPlateauImg = i;}
  public Image getAnimal(){return animal;}
  public void setAnimal(Image i){animal = i;}
  public static int getScreenDimX(){return screenDimX;}
  public static void setScreenDimX(int x){screenDimX=x;}
  public static int getScreenDimY(){return screenDimY;}
  public static void setScreenDimY(int x){screenDimY=x;}
  public static int getNbrLevelAviable(){return nbrLevelAviable;}
  public static Font getFont(){return font;}
  public static int getTailleDUneCase(){return tailleDUneCase;}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------

}
