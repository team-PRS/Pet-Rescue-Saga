package prs.map;

import java.io.Serializable;
import prs.usuel.tableau;

public class Compte implements Serializable{
  /**
  *{@summary Number of gold, the vip monney.}
  */
  private int gold;
  /**
  *{@summary Containt the score for every level.}
  *If score = -1 level is lock. If score = 0 level is unlock but not done yet. If score > 0 level have been done.
  */
  private int [] levelScore;
  public static final int NUMBER_OF_LEVEL = 4;
  // CONSTRUCTEUR ---------------------------------------------------------------
  /**
  *{@summary Creates a Compte with 100 gold and evry level to lock exept the 1a.}
  */
  public Compte () {
    gold = 100;
    levelScore = new int [NUMBER_OF_LEVEL];
    levelScore[0]=0;
    for (int i=1;i<NUMBER_OF_LEVEL ;i++ ) {
      levelScore[i]=-1;
    }
  }
  // GET SET --------------------------------------------------------------------
  public int getGold(){ return gold;}
  public void setGold(int g){gold =g;}
  public boolean setScore(int i, int score){
    if(score < 0){
      System.out.println("Impossible to set a < 0 score.");
      return false;
    }
    if(!isLevelUnlock(i)){
      System.out.println("Impossible to set as score to a lock level.");
      return false;
    }
    levelScore[i]=score;
    return true;
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String r = "gold: "+gold+"\n";
    r+=tableau.tableauToString(levelScore);
    return r;
  }
  public boolean isLevelUnlock(int i){
    return levelScore[i]!=-1;
  }
  public boolean unlockLevel(int i){
    if(levelScore[i]==-1){
      levelScore[i]=0;
      return true;
    }return false;
  }
}
