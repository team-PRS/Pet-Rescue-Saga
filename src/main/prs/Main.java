package prs;
import prs.graphics.Frame;
import java.awt.Graphics;
import java.awt.Color;
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
    System.out.println("==============================");
    Jeu m = new Jeu();
    System.out.println(m.addFrame());
    System.out.println(m.addPanelMap());
    System.out.println(m.iniImage());
    m.repaint();
    //m.pause(1000);
    //System.out.println("repaint");
    //System.out.println(m.addPanelMap());
    System.out.println(m.addLevel());
    m.repaint();
    System.out.println(m.addLevel());
    m.repaint();

    System.out.println("==============================");
    Plateau plateau = new Plateau(6, 15);
    plateau.remplirPlateau(2, 60, 4, 1);
    plateau.printMap();
    m.addPlateau(plateau);
    System.out.println(m.addPanelPlateau());
    // printAll > repaint. printAll force le redessinage de la fenetre quand repaint ne fonctionne que lorsqu'il concidère que c'est utile.
    m.getFrame().printAll(m.getFrame().getGraphics());
    //m.getPPlateau().repaint();
    //m.getPPlateau().paintComponent(new Graphics());
    System.out.println("==============================");
  }
}
