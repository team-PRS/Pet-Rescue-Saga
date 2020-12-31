package prs;

import prs.map.Compte;
import prs.map.Joueur;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;


/**
* Main class / controller class
*/
public class Jeu
{
    private Joueur joueur;
    private Plateau plateau;
    private int initialBlocs, initialAnimals, initialImmoBlocs, initialBombs, initialBallons;
    private int additionalBlocs, additionalAnimals, additionalBombs, additionalBallons;
    private Configuration configLevel;
    private ArrayList<Joueur> gamers;
    private Compte compte;
    private boolean IsGui;


    /*============================== Constructor & close function ===================================================*/

    /**
     * create game engine
     *
     * @param IsGui - true if engine is used from GUI application, false - from console
     */
    public Jeu(boolean IsGui)
    {
        this.configLevel = new Configuration("config.txt");
        this.joueur = new Joueur();
        this.compte = joueur.getCompte();
        this.IsGui = IsGui;

        this.plateau = null;
        this.gamers = null;

        loadUserAccountFromFile();
    }

    /**
     * Close internal objects, dump data to config & accounts files.
     * Must be called right before application close
     */
    public void Close()
    {
        saveUserAccountsToFile();
    }

    /*============================== User account functions ==========================================================*/

    /**
     * Get list of players if any
     *
     * @return list of players or empty list
     */
    public ArrayList<Joueur> getListOfJoueurs()
    {
        return this.gamers;
    }

    /**
     * Get current gamer account and parameters
     *
     * @return gamer account
     */
    public Joueur getCurrentJoueur()
    {
        return this.joueur;
    }

    /**
     * select current gamer's account
     *
     * @param account - gamer's account which has been retrieved by getListOfJoueurs function call
     */
    public void selectJoueur(Joueur account)
    {
        this.joueur = account;

        if (null != account)
        {
            //set link to compte
            this.compte = this.joueur.getCompte();
        } else
        {
            this.compte = null;
        }
    }

    /**
     * Create new gamer account
     * add it into the list
     * make it current
     */
    public void createNewJoueur(String name)  // set pseudo which gamer give and add joueur to array gamers
    {
        Joueur newRecord = new Joueur();
        this.joueur = newRecord;
        this.joueur.setPseudo(name);
        //set link to compte
        this.compte = this.joueur.getCompte();
        this.gamers.add(this.joueur);
    }


    /*============================== Game board functions ============================================================*/

    /**
     * Recreate game board using current user level parameter.
     */
    public void createPlateau()
    {
        // this helps create plateau in all cases cause getLevel check getUnlockLevel
        int level = this.joueur.getCompte().getUnlockLevel();

        this.plateau = new Plateau(Integer.parseInt(this.configLevel.getLevelValue(level, "height")), Integer.parseInt(this.configLevel.getLevelValue(level, "width")));

        //initialisation of start values of number of blocs, animals etc.
        initialBlocs = Integer.parseInt(configLevel.getLevelValue(level, "initialBlocs"));
        initialAnimals = Integer.parseInt(configLevel.getLevelValue(level, "initialAnimals"));
        initialImmoBlocs = Integer.parseInt(configLevel.getLevelValue(level, "initialImmoBlocs"));

        // if level with outils
        if (configLevel.getLevelValue(level, "outils") == "true")
        {
            initialBombs = Integer.parseInt(configLevel.getLevelValue(level, "initialBombs"));
            initialBallons = Integer.parseInt(configLevel.getLevelValue(level, "initialBallons"));
        } else
        {
            initialBombs = 0;
            initialBallons = 0;
        }

        //initialisation of additional values which would be added during the game according the level
        if (configLevel.getLevelValue(level, "addBlocs") == "true")
        {
            additionalBlocs = Integer.parseInt(configLevel.getLevelValue(level, "additionalBlocs"));
        } else
        {
            additionalBlocs = 0;
        }

        if (configLevel.getLevelValue(level, "addAnimals") == "true")
        {
            additionalAnimals = Integer.parseInt(configLevel.getLevelValue(level, "additionalAnimals"));
        } else
        {
            additionalAnimals = 0;
        }

        if (configLevel.getLevelValue(level, "addBombs") == "true")
        {
            additionalBombs = Integer.parseInt(configLevel.getLevelValue(level, "additionalBombs"));
        } else
        {
            additionalBombs = 0;
        }

        if (configLevel.getLevelValue(level, "addBallons") == "true")
        {
            additionalBallons = Integer.parseInt(configLevel.getLevelValue(level, "additionalBallons"));
        } else
        {
            additionalBallons = 0;
        }

        this.plateau.remplirPlateau(initialImmoBlocs, initialBlocs, initialAnimals, initialBallons);
    }

    /**
     * return object on game board
     *
     * @return game object {Bloc, Bomb, Ballon, Animal}
     */
    public ObjectSurCase getCell(int x, int y)
    {
        return plateau.getObject(x, y);
    }


    /**
     * return current level status
     *
     * @return level status {lost, win, continue}
     */
    public String getCurrentLevelStatus()
    {
        return plateau.gameState();
    }


    /**
     * return game board width 
     * @return width
     */
    public int getPlateauWidth()
    {
        if (null == this.plateau)
        {
            return 0;
        }
        return plateau.getWidth();
    }

    /**
     * return game board height
     * @return width
     */
    public int getPlateauHeight()
    {
        if (null == this.plateau)
        {
            return 0;
        }
        return plateau.getHeight();
    }

    /**
     * return count of animals on the board. Application may monitor this value to detect when animals get rescued
     * @return animals count
     */
    public int getAnimalsCount()
    {
        if (null == this.plateau)
        {
            return 0;
        }
        
        return plateau.getAnimalsOnFloor().size();
    }


    /**
      {@summary function which provide reactions for clic:}<br>
     - if group of blocs, delete them all<br>
     - if one bloc, do nothing<br>
     - if bomb, make an explosion and deletion of 8 cells around<br>
     - if ballon, delete all bloc of its color<br>
     - if outil, launch it<br>
     - if animal, do nothing for the moment<br>
     @return true if click was successful, false - otherwise
     */
    public boolean pressCell(int x, int y)
    {
        if (null == this.plateau)
        {
            return false;
        }

        // player has clicked on plateau
        if (plateau.isOnPlateau(x, y))
        {
            // not empty cell
            if (!plateau.isEmpty(x, y))
            {
                ObjectSurCase obj = plateau.getObject(x, y);
                // if bloc
                if (obj instanceof Bloc)
                {
                    // if only one bloc
                    if (plateau.getGroup(x, y).size() == 0)
                    {
                        if (!this.IsGui)
                        {
                            System.out.println("\n Can't delete single bloc \n");
                        }
                    }
                    // if group of blocs
                    else
                    {
                        LinkedList<Point> blocGroupe = plateau.getGroup(x, y);
                        int points = blocGroupe.size();
                        // delete group and shift down all upstairs elements
                        for (Point p : blocGroupe)
                        {
                            int xCoord = p.getCoordX();
                            int yCoord = p.getCoordY();
                            plateau.cleanCase(xCoord, yCoord);
                            plateau.shiftDown(xCoord, yCoord);
                        }
                        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points*5);

                        // TODO add another blocs and elements (depends of level)
                        //  if (configLevel.getValue(level, "addBlocs") == "true")
                        // {
                        //     plateau.addBlocsInGame(configLevel.getValue(level, "additionalBlocs"));
                        // }
                    }
                }
                // if animal
                else if (obj instanceof Animal)
                {
                }
                // if outil
                else if (obj instanceof Outil)
                {
                    // if bomb
                    if (obj instanceof Bomb)
                    {
                        plateau.bombExplosion(x, y);
                    }

                    //if ballon
                    else if (obj instanceof Ballon)
                    {
                        //ballon destroyed all blocs of his color
                        String ballonColor = ((Ballon)plateau.getObject(x, y)).getColor();
                        plateau.ballonExplosion(ballonColor);

                    }
                }
                rescue();
                plateau.shiftLeft();
                rescue();
                plateau.shiftLeft();
            }
            else //empty cell
            {
                return false;
            }
        }
        else // player has clicked out of plateau
        {
            return false;
        }
        
        return true;
    }

    /**
     * ballon explosion by coordinates
     * @param x coordinate X
     * @param x coordinate Y
     */
    public void ballonExplosion(int x, int y)
    {
        int points = plateau.ballonExplosion(plateau.getColorOfBloc(x, y));
        joueur.activateBallon();
        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points * 5);

        rescue();
        plateau.shiftLeft();
        rescue();
        plateau.shiftLeft();
    }

    /**
     * bomb explosion by coordinates
     * @param x coordinate X
     * @param x coordinate Y
     */
    public void bombExplosion(int x, int y)
    {
        int points1 = plateau.bombExplosion(x, y);
        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points1 * 5);

        rescue();
        plateau.shiftLeft();
        rescue();
        plateau.shiftLeft();
    }


    /*============================== Private & internal functions ====================================================*/
    /**
     * check animal on the floor, rescue them and print message for each of them
     */
    private void rescue()
    {
        LinkedList<Point> animals = plateau.getAnimalsOnFloor();
        plateau.rescueAnimals(animals);
        if (plateau.getIsRescued())
        {
            if (!this.IsGui)
            {
                for (int i = 0; i < animals.size(); i++)
                {
                    System.out.println("petRescued");
                }
            }
        }

        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + animals.size() * 10);
    }

    /**
     * Save list of players to binary file
     */
    private void saveUserAccountsToFile()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("gamers.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.gamers);
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            if (!this.IsGui)
            {
                System.out.println("Can't write to file");
            }
        }
    }


    /**
     * load list of players from binary file
     */
    private void loadUserAccountFromFile()
    {
        try
        {
            FileInputStream fis = new FileInputStream("gamers.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.gamers = (ArrayList<Joueur>) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            this.gamers = new ArrayList<Joueur>();
        }
    }
}
