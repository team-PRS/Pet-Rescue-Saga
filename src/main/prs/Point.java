package prs;
import java.io.Serializable;
import prs.usuel.str;

public class Point implements Serializable{
  protected int x,y; // pas de byte pour pourvoir utliser de très grande carte !
  // CONSTRUCTEUR -----------------------------------------------------------------
  public Point (int x,int y){
    this.x = x;
    this.y = y;
  }
  public Point(String s){//sous la forme -51,34
      String t [] = s.split(".");
      if(t.length<2){
        t = s.split(",");
        if(t.length<2){
          t = s.split(";");
        }
      }
    x=str.sToI(t[0]);
    y=str.sToI(t[1]);
  }

// GET SET -----------------------------------------------------------------------
  public int getX(){return x;}
  public int getY(){return y;}
  public void setX(int x){this.x = x;}
  public void setY(int y){this.y = y;}
  public void ajouteAX(int a){this.x = this.x + a;}
  public void ajouteAY(int a){this.y = this.y + a;}

// Fonctions propre -----------------------------------------------------------
  public String toString(){
    return "("+x+","+y+")";
  }
  public void afficheToi(){
    System.out.println(this);
  }
  public boolean equals(Point p){
    if (x != p.x) { return false;}
    if (y != p.y) { return false;}
    return true;
  }
  public boolean equals(String s){//sous la forme -51,34
    String t [] = s.split(".");
    if(t.length<2){
      t = s.split(",");
      if(t.length<2){
        t = s.split(";");
      }
    }
    if(t.length<2){return false;}
    if(str.sToI(t[0])!=x){return false;}
    if(str.sToI(t[1])!=y){return false;}
    return true;
  }
}
