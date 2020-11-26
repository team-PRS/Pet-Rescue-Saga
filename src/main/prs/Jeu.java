package prs;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Jeu
{
    private Plateau plateau;
    private Point cell;
    //private Joueur jouer;
    //private Compte compte;

    public Jeu()
    {
        //this.plateau = new Plateau();

    }

    private void mouseClicked(MouseEvent e)    //TODO verify how to make it as lambda
    {
        // player has clicked on plateau
        if (plateau.isOnPlateau(cell))
        {
            // not empty cell
            if (!plateau.isEmpty(cell.getCoordX(), cell.getCoordX()))
            {
                // bloc
                if (plateau.getObject(cell.getCoordX(), cell.getCoordX()) instanceof Bloc)
                {
                    // only one bloc
                    if (plateau.getGroup(cell.getCoordX(), cell.getCoordX()).size() == 1)
                    {
                        //skip
                    }
                    // group of blocs
                    else
                    {
                        LinkedList<Point> gr = plateau.getGroup(cell.getCoordX(), cell.getCoordX());
                        // TODO delete group
                        // TODO move upstairs
                        // TODO add another blocs  (depends of level)
                    }
                }
                // animal
                else if (plateau.getObject(cell.getCoordX(), cell.getCoordX()) instanceof Animal)
                {
                    //TODO get sound
                }
                // outil
                else if (plateau.getObject(cell.getCoordX(), cell.getCoordX()) instanceof Outil)
                {
                    //bomb
                    if (plateau.getObject(cell.getCoordX(), cell.getCoordX()) instanceof Bomb)
                    {
                        //TODO bomb destroyed 9 cells
                    }

                    //ballon
                    else if (plateau.getObject(cell.getCoordX(), cell.getCoordX()) instanceof Ballon)
                    {
                        //TODO ballon destroyed all blocs of his color
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
