package prs.map;

import java.io.Serializable;
import prs.usuel.tableau;

public class Compte implements Serializable{
  /**
  *{@summary Number of gold, the vip monney.}
  */
  public static final int ballonPrix = 10;
  public static final int PointsPerGoldCoin = 50;
  private int gold;
  private int ballon;
  private int unlockLevel;
  /**
  *{@summary Containt the score for every level.}
  *If score = -1 level is lock. If score = 0 level is unlock but not done yet. If score > 0 level have been done.
  */

  private int points;
 // private int [] levelScore;
 // public static final int NUMBER_OF_LEVEL = 4;
  // CONSTRUCTEUR ---------------------------------------------------------------
  /**
  *{@summary Creates a Compte with 100 gold and evry level to lock exept the 1a.}
  */
  public Compte () {
    this.gold = 100;
    this.ballon = 0;
    this.unlockLevel = 1;
    this.points = 0;
   // levelScore = new int [NUMBER_OF_LEVEL];
   // levelScore[0]=0; //1a level is unlocked
   // for (int i=1;i<NUMBER_OF_LEVEL ;i++ ) {
   //   levelScore[i]=-1;
   // }
  }
 // // GET SET --------------------------------------------------------------------
  public int getGold(){ return gold;}
  public void setGold(int g){gold = g;}
  public int getBallon(){ return ballon;}
  public void setBallon(int b){ballon = b;}
 // public void setUnlockLevel(int l)
 // {
 //   this.unlockLevel = l;
 // }
 // public int getUnlockLevel(){
 //     int k=0;
 //     for (int i=0;i<NUMBER_OF_LEVEL ;i++ ) {
 //         if(levelScore[i]>=0){k++;}
 //     }
 //     return k;
 // }

  // sorry Emilien !
  //------------------ Natalia's function to test serialisation --------------

  public int getUnlockLevel() { return this.unlockLevel;}

  public void setUnlockLevel(int unlock)
  {
    this.unlockLevel = unlock;
  }

  public int getPoints(){return points;}

  public void setPoints(int points)
  {
     // System.out.println("setPoints(" + String.valueOf(points)+") called");
     // StackTraceElement[] stackTrace =Thread.currentThread().getStackTrace();
     // for(StackTraceElement st : stackTrace)
     // {
     //     System.out.println("called from: " + st);
     // }

      this.points = points;
  }

  public String toString1()
  {
    String str = "Ballons : " + Integer.toString(ballon) + "  Gold : " + Integer.toString(gold)
            + "  UnlockLevel : " + Integer.toString(unlockLevel)  + "  Points : " + Integer.toString(points);
    return str;
  }

  // END-- Natalia's function to test serialisation --------------

  

 // //public void setUnlockLevel(int l){unlockLevel = l;}
 // public int getScore(int i){return levelScore[i];}
 // /**
 // *{@summary setScore.}
 // *@param i the id of the level and his place in levelScore [].
 // *@param score the score to set.
 // *@return true if it worked.
 // */
 // public boolean setScore(int i, int score){
 //   if(score < 0){
 //     System.out.println("Impossible to set a < 0 score.");
 //     return false;
 //   }
 //   if(!isLevelUnlock(i)){
 //     System.out.println("Impossible to set as score to a lock level.");
 //     return false;
 //   }
 //   levelScore[i]=score;
 //   return true;
 // }
 // // Fonctions propre -----------------------------------------------------------
 // public String toString(){
 //   String r = "gold: "+gold+"\n";
 //   r+=tableau.tableauToString(levelScore);
 //   return r;
 // }
 // /**
 // *{@summary Check if a level is unlock.}
 // *@param i the id of the level and his place in levelScore [].
 // *@return true if the level is unlock.
 // */
 // public boolean isLevelUnlock(int i){
 //     i--; //to start with level 1.
 //   if(i<0 || i>=levelScore.length){System.out.println("i n'est pas correcte");return false;}
 //   return levelScore[i]!=-1;
 // }
 // /**
 // *{@summary Unlock a level.}
 // *@param i the id of the level and his place in levelScore [].
 // *@return true if it worked.
 // */
 // public boolean unlockLevel(int i){
 //     i--; //to start with level 1.
 //   if(i<0 || i>=levelScore.length){System.out.println("i n'est pas correcte");return false;}
 //   if(levelScore[i]==-1){
 //     levelScore[i]=0;
 //     return true;
 //   }return false;
 // }
}
