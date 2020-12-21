package prs.map;

import java.io.Serializable;

public class Map implements Serializable{
  //private Niveau tNiveau [];
  private final Joueur joueur;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Map(Joueur j){
    //TODO charger le fichier contenant les niveaux.
    joueur =j;
  }
  public Map(){
    this(new Joueur());
  }
  // GET SET --------------------------------------------------------------------
  public Joueur getJoueur(){return joueur;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String r = joueur.toString();
    //r+=tNiveau.toString();
    return r;
  }
}
