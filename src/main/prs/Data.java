package prs;
import java.awt.Image;
import java.awt.Font;
import prs.graphics.*;

/**
*{@summary Contain all data (images) that will be used by the graphic interface.}
*@author Hydrolien
*/
public class Data {
  private Image pMapImg;
  private Image pPlateauImg;
  private Image animal;
  private Image inmovable;
  private Image bomb;
  private Image ballon;
  private int screenDimX;
  private int screenDimY;
  private int nbrLevelAviable=4;
  private Font font=new Font("FreeSans",Font.BOLD,30);
  private int tailleDUneCase = 100;

  private Frame frame;
  private PanelMap pMap;
  private PanelPlateau pPlateau;
  private PanelGame pGame;
  private PanelInfo pInfo;
  private boolean clic;
  // CONSTRUCTORS --------------------------------------------------------------

  // GET SET -------------------------------------------------------------------
  public Image getPMapImg(){return pMapImg;}
  public void setPMapImg(Image i){pMapImg = i;}
  public Image getPPlateauImg(){return pPlateauImg;}
  public void setPPlateauImg(Image i){pPlateauImg = i;}
  public Image getAnimal(){return animal;}
  public void setAnimal(Image i){animal = i;}
  public Image getInmovable(){return inmovable;}
  public void setInmovable(Image i){inmovable = i;}
  public Image getBomb(){return bomb;}
  public void setBomb(Image i){bomb = i;}
  public Image getBallon(){return ballon;}
  public void setBallon(Image i){ballon = i;}
  /*public int getScreenDimX(){return screenDimX;}
  public void setScreenDimX(int x){screenDimX=x;}
  public int getScreenDimY(){return frame.getHeight();}
  public void setScreenDimY(int x){screenDimY=x;}*/
  public int getNbrLevelAviable(){return nbrLevelAviable;}
  public Font getFont(){return font;}
  public int getTailleDUneCase(){return tailleDUneCase;}
  public void setTailleDUneCase(int x){tailleDUneCase=x;}

  public Frame getFrame(){return frame;}
  public void setFrame(Frame f){frame=f;}
  public PanelMap getPMap(){return pMap;}
  public void setPMap(PanelMap p){pMap=p;}
  public PanelPlateau getPPlateau(){return pPlateau;}
  public void setPPlateau(PanelPlateau p){pPlateau=p;}
  public PanelGame getPGame(){return pGame;}
  public void setPGame(PanelGame p){pGame=p;}
  public PanelInfo getPInfo(){return pInfo;}
  public void setPInfo(PanelInfo p){pInfo=p;}


  public boolean getClic(){return clic;}
  public void setClic(boolean b){clic=b;}
  public int getWidthMax(){return getFrame().getWidth();}
  public int getHeightMax(){return getFrame().getHeight();}
  //public void addPlateau(Plateau p){plateau=p;}
  //public Plateau getPlateau(){return pPlateau.getPlateau();}
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------

}
