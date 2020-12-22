package prs;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Jeu
{
    private Plateau plateau;
    private int initialBlocs, initialAnimals, initialImmoBlocs, initialBombs, initialBallons;
    private int additionalBlocs, additionalAnimals, additionalBombs, additionalBallons;
    private Configuration configLevel;
    private Configuration configJoueur;
    private int level;
    //private Joueur jouer;
    //private Compte compte;

    public Jeu()
    {
       // this.configLevel = new Configuration("config.txt");
        this.level = Integer.parseInt(configJoueur.getPlayerValue("Level"));

        //initialisation of start values of number of blocs, animals etc.
        initialBlocs = Integer.parseInt(configLevel.getValue(level, "initialBlocs"));
        initialAnimals = Integer.parseInt(configLevel.getValue(level, "initialAnimals"));
        initialImmoBlocs = Integer.parseInt(configLevel.getValue(level, "initialImmoBlocs"));
        
        // if level with outils
        if (configLevel.getValue(level, "outils") == "true")
        {
            initialBombs = Integer.parseInt(configLevel.getValue(level, "initialBombs"));
            initialBallons = Integer.parseInt(configLevel.getValue(level, "initialBallons"));
        }
        else
            {
                initialBombs = 0;
                initialBallons = 0;
            }

        //initialisation of additional values which would be added during the game according the level
        if (configLevel.getValue(level, "addBlocs") == "true")
        {
            additionalBlocs = Integer.parseInt(configLevel.getValue(level, "additionalBlocs"));
        }
        else {additionalBlocs = 0;}

        if (configLevel.getValue(level, "addAnimals") == "true")
        {
            additionalAnimals = Integer.parseInt(configLevel.getValue(level, "additionalAnimals"));
        }
        else {additionalAnimals = 0;}

        if (configLevel.getValue(level, "addBombs") == "true")
        {
            additionalBombs = Integer.parseInt(configLevel.getValue(level, "additionalBombs"));
        }
        else {additionalBombs = 0;}

        if (configLevel.getValue(level, "addBallons") == "true")
        {
            additionalBallons = Integer.parseInt(configLevel.getValue(level, "additionalBallons"));
        }
        else {additionalBallons = 0;}


    }


    private Plateau createPlateau(Configuration config)
    {
        this.configLevel = config;
        return plateau = new Plateau(Integer.parseInt(configLevel.getValue(level, "height")),
                Integer.parseInt(configLevel.getValue(level, "width")));
    }

    private void remplirPlateau(Configuration config)
    {
        this.configLevel = config;
        this.plateau.remplirPlateau(initialImmoBlocs, initialBlocs, initialAnimals,initialBallons);
    }



    /* function which provide reactions for clic:
     - if group of blocs, delete them all
     - if one bloc, do nothing
     - if bomb, make an explosion and deletion of 8 cells around
     - if ballon, delete all bloc of its color
     - if outil, launch it
     - if animal, do nothing for the moment
     */
    
    private void pressCell(int x, int y)       
    {
        // player has clicked on plateau
        if (plateau.isOnPlateau(x, y))
        {
            // not empty cell
            if (!plateau.isEmpty(x, y))
            {
                // if bloc
                if (plateau.getObject(x, y) instanceof Bloc)
                {
                    // if only one bloc
                    if (plateau.getGroup(x, y).size() == 1)
                    {
                        //skip
                    }
                    // if group of blocs
                    else
                    {
                        LinkedList<Point> blocGroupe = plateau.getGroup(x, y);
                        // delete group and shift down all upstairs elements
                        for (Point p : blocGroupe)
                        {
                            int xCoord = p.getCoordX();
                            int yCoord = p.getCoordY();
                            plateau.cleanCase(xCoord, yCoord);
                            plateau.shiftDown(xCoord, yCoord);
                        }

                        // TODO add another blocs and elements (depends of level)
                       //  if (configLevel.getValue(level, "addBlocs") == "true")
                       // {
                       //     plateau.addBlocsInGame(configLevel.getValue(level, "additionalBlocs"));
                       // }
                    }
                }
                // if animal
                else if (plateau.getObject(x, y) instanceof Animal)
                {
                    //TODO get sound
                }
                // if outil
                else if (plateau.getObject(x, y) instanceof Outil)
                {
                    // if bomb
                    if (plateau.getObject(x, y) instanceof Bomb)
                    {
                        //bomb destroyed 9 cells -- its cell + 8 around
                        plateau.bombExplosion(x, y);
                    }

                    //ballon
                    else if (plateau.getObject(x, y) instanceof Ballon)
                    {
                        //ballon destroyed all blocs of his color
                        String ballonColor = ((Ballon)plateau.getObject(x, y)).getColor();
                        plateau.ballonExplosion(ballonColor);

                    }
                }
            }
            //empty cell
            else
            {
                //skip
            }
        }
        // player has clicked out of plateau
        else
        {
            System.out.println("Clicked out of bounds of plateau");
        }
    }






    public static void main (String[] args)
    {
        Configuration conf = new Configuration("config.txt");
        System.out.println(conf.getValue(1, "additionalBlocs"));
        Configuration confPlayer = new Configuration("configPlayer.txt");
        System.out.println(confPlayer.getPlayerValue("Level"));

        /*  GameSet
          1) ask if want <play> (button) or <exit> (button)
        if push play:
          2) make un account -> create config/save obj joueur OR insert name -> load level, points etc from configJoueur/dataJoueur
          3) push <Play>
          4) Game set:
               a) create plateau, fill it by elements
               b) pressCell <-> reactions (cycle)
                   till:
                          - level win
                          - level lost
               c) save level, points etc to configJoueur
               d) <start new level> OR <exit>
         */

    }
}
