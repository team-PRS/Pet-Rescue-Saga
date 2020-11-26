package prs;

import java.io.Serializable;
import prs.map.*;

public class Main {

  public static void main(String[] args) {
    //To do test easily.
    Map map = new Map();
    System.out.println(map);
    System.out.println("==============================");
    map.getJoueur().unlockLevel(1);
    System.out.println(map);
    System.out.println("==============================");
    map.getJoueur().getCompte().setScore(0,100);
    System.out.println(map);
    System.out.println("==============================");
    map.getJoueur().getCompte().setScore(2,100);
    System.out.println(map);
  }
}
