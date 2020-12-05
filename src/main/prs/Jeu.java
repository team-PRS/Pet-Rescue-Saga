package prs;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Jeu
{
    private Plateau plateau;
    private Point cell;
    int randomNmb;     // nmb de blocs to add according the level
    //private Joueur jouer;
    //private Compte compte;

    public Jeu()
    {
        //this.plateau = new Plateau();

    }

    /* function which provide the reactions for clic:
     - if group of blocs, delete them all
     - if one bloc, do nothing
     - if bomb, make an explosion and deletion of 8 cells around
     - if ballon, delete all bloc of its color
     - if outil, launch it
     - if animal, do nothing for the moment
     */

    private void mouseClicked(MouseEvent e)    //TODO verify how to make it as lambda
    {
        int x = cell.getCoordX();
        int y = cell.getCoordY();

        // player has clicked on plateau
        if (plateau.isOnPlateau(cell))
        {
            // not empty cell
            if (!plateau.isEmpty(x, y))
            {
                // if bloc
                if (plateau.getObject(x, y) instanceof Bloc)
                {
                    // only one bloc
                    if (plateau.getGroup(x, y).size() == 1)
                    {
                        //skip
                    }
                    // group of blocs
                    else
                    {
                        LinkedList<Point> gr = plateau.getGroup(x, y);
                        // delete group
                        gr.clear();
                        // TODO move upstairs
                        // TODO add another blocs  (depends of level)
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
                        String ballonColor = ((Ballon)plateau.getObject(x, y)).getName();
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
            System.out.println("Clicked out of bonds of plateau");
        }
    }
}
