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
       getCompte().setGold(getCompte().getGold() - 10);
       getCompte().setBallon(getCompte().getBallon() + 1);
       System.out.println(getCompte().getBallon());
  }

  public void convertPointsToGold()          //TODO  1 ingot = 50 points
  {
    int points = this.getCompte().getScore(1);           //TODO verify what is the score
    if (points >= 50)
    {
        points -= 50;
        this.getCompte().getScore(points);
        this.getCompte().setGold(this.getCompte().getGold() + 1);
    }
    else
        {
          System.out.println("Can't convert, too few points");
        }
  }

  public void activateBallon()                                          // make -1 ballon in Compte
  {
      this.getCompte().setBallon(this.getCompte().getBallon() - 1);
  }
}
