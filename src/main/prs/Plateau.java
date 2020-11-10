package prs;
import prs.list.GCase;
import java.io.Serializable;

public class Plateau implements Serializable{
  private GCase gc;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Plateau(GCase gcIn){
    gc=gcIn;
  }
  public Plateau(int x, int y){
    this(new GCase(x,y));
  }
  // GET SET --------------------------------------------------------------------
  public GCase getGc(){ return gc;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return gc.toString();
  }
}
