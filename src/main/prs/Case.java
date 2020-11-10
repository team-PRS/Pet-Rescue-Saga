package prs;
import java.io.Serializable;

public class Case implements Serializable{
  private Point p;
  //private ObjetSurCase o; //TODO le sortir des commentaires et ajouté au CONSTRUCTEUR.

  // CONSTRUCTEUR ---------------------------------------------------------------
  public Case(Point point){
    p=point;
  }
  public Case(int x, int y){this(new Point(x,y));}
  // GET SET --------------------------------------------------------------------
  public Point getP(){return p;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String s = "";
    s=s+ p.toString();s=s+"\n";
    return s;
  }
  public boolean equals(Case c){
    if(!this.getP().equals(c.getP())){ return false;}
    //return this.getO().equals(c.getO);//TODO
    return true;
  }
  public void afficheToi(){
    System.out.println(this);
  }
}
