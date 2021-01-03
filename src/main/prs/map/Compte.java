package prs.map;

import java.io.Serializable;
import prs.usuel.tableau;

public class Compte implements Serializable {

     private int gold;
     private int ballon;
     private int unlockLevel;
    /**
     *Temporary point for the curent level
     */
     private int points;
     public static final int NUMBER_OF_LEVEL = 4;
     public static final int BALLON_PRIX = 10; //TO_DO replace by BALLON_PRIX
     public static final int POINTS_PER_GOLD_COIN = 50; //TO_DO replace by POINTS_PER_GOLD_COIN
     private static final int WIN_POINT = 100;

     /**
     *{@summary Containt the score for every level.}
     *If score = -1 level is lock. If score = 0 level is unlock but not done yet. If score > 0 level have been done.
     */
     private int [] levelScore;
     

     // CONSTRUCTEUR ---------------------------------------------------------------
     /**
     *{@summary Creates a Compte with 100 gold and evry level to lock exept the 1a.}
     */
     public Compte () {
       this.gold = 100;
       this.ballon = 0;
       this.unlockLevel = 1;
       this.points = 0;
       levelScore = new int [NUMBER_OF_LEVEL];
       levelScore[0]=0; //1a level is unlocked
       for (int i=1;i<NUMBER_OF_LEVEL ;i++ ) {
         levelScore[i]=-1;
       }
     }
    // // GET SET --------------------------------------------------------------------
     public int getGold(){ return gold;}
     public void setGold(int g){gold = g;}
     public int getBallon(){ return ballon;}
     public void setBallon(int b){ballon = b;}
     /**
     *Return the number of unlocked level.
     */
     public int getLastUnlockLevel(){
         int k=0;
         for (int i=0;i<NUMBER_OF_LEVEL ;i++ ) {
             if(levelScore[i]>=0){k++;}
             else{return k;}
         }
         return k;
     }
     /**
     *Unlock the next level.
     */
     public void unlockNextLevel(){
         unlockLevel(getLastUnlockLevel()+1);
     }
     /**
     *Save the point in score.
     */
     public void saveScore(int level,boolean win){
         //if that's a new best score.
         if(win){points+=WIN_POINT;}
         if(getScore(level)<points){
             setScore(level,points);
         }
         convertPointsToGold();
     }
     public void saveScore(int level){saveScore(level,true);}

     /*public int getUnlockLevel() { return this.unlockLevel;}

     public void setUnlockLevel(int unlock)
     {
       this.unlockLevel = unlock;
}   */

     public int getPoints(){return points;}

     public void setPoints(int points){ this.points = points;}

     public String toString1()
     {
       return toString();
     }

    // //public void setUnlockLevel(int l){unlockLevel = l;}
    /**
    *{@summary getScore.}
    *@param i the id of the level in [1,4].
    *@return the score of the level i.
    */
    public int getScore(int i){
        if(i>0 && i<=NUMBER_OF_LEVEL){
            System.out.println("score de level "+(i-1));
            return levelScore[i-1];
        }
        return -2;
    }
    /**
    *{@summary setScore.}
    *@param i the id of the level in [1,4].
    *@param score the score to set.
    *@return true if it worked.
    */
    public boolean setScore(int i, int score){
      if(score < 0){
        System.out.println("Impossible to set a < 0 score.");
        return false;
      }
      if(!isLevelUnlock(i)){
        System.out.println("Impossible to set as score to a lock level.");
        return false;
      }
      levelScore[i-1]=score;
      return true;
    }
    // Fonctions propre -----------------------------------------------------------
    public String toString(){
        String str = "Ballons : " + Integer.toString(ballon) + "  Gold : " + Integer.toString(gold)
                + "  UnlockLevel : " + Integer.toString(unlockLevel)  + "  Points : " + Integer.toString(points);
          str+="\n"+tableau.tableauToString(levelScore);
        return str;
    }
    /**
    *{@summary Check if a level is unlock.}
    *@param i the id of the level and his place in levelScore [].
    *@return true if the level is unlock.
    */
    public boolean isLevelUnlock(int i){
        i--; //to start with level 1.
      if(i<0 || i>=levelScore.length){System.out.println("i n'est pas correcte i:"+i);return false;}
      return levelScore[i]!=-1;
    }
    /**
    *{@summary Unlock a level.}
    *@param i the id of the level and his place in levelScore [].
    *@return true if it worked.
    */
    public boolean unlockLevel(int i){
        i--; //to start with level 1.
      if(i<0 || i>=levelScore.length){System.out.println("i n'est pas correcte i:"+i);return false;}
      if(levelScore[i]==-1){
        levelScore[i]=0;
        return true;
      }return false;
    }

    public void convertPointsToGold()
    {
        if (points >= POINTS_PER_GOLD_COIN)
        {
            setGold(getGold() + points/ POINTS_PER_GOLD_COIN);
            this.setPoints(this.points% POINTS_PER_GOLD_COIN);
        }
        else
        {
            System.out.println("Can't convert, too few points");
        }
    }
}
