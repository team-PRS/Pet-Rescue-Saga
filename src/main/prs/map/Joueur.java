package prs.map;

import java.io.*;


public class Joueur implements Serializable{
  private final Compte compte;
  private String pseudo;



  // CONSTRUCTEUR ---------------------------------------------------------------
  public Joueur(){
    compte = new Compte();
    this.pseudo = "Anonime";
  }
  // GET SET --------------------------------------------------------------------
  public Compte getCompte(){return compte;}
  public String getPseudo() { return pseudo;}
  public void setPseudo(String p) {this.pseudo = p; }

  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    return "Player: \n"+compte.toString();
  }
  public boolean unlockLevel(int i){ return compte.unlockLevel(i);}
  public boolean isLevelUnlock(int i){return compte.isLevelUnlock(i);}

  public void buyBallon()
  {
       compte.setGold(compte.getGold() - 10);
       compte.setBallon(compte.getBallon() + 1);
  }


}
