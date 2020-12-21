package prs.map;

import java.io.Serializable;

public class Joueur implements Serializable{
  private final Compte compte;
  //private String pseudo;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Joueur(){
    compte = new Compte();
  }
  // GET SET --------------------------------------------------------------------
  public Compte getCompte(){return compte;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return "Player: \n"+compte.toString();
  }
  public boolean unlockLevel(int i){ return compte.unlockLevel(i);}
  public boolean isLevelUnlock(int i){return compte.isLevelUnlock(i);}
}
