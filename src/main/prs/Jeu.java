package prs;

import prs.map.Compte;
import prs.map.Joueur;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
* Controller class
*/

public class Jeu
{
    private Joueur joueur;
    private Plateau plateau;
    private int initialBlocs, initialAnimals, initialImmoBlocs, initialBombs, initialBallons;
    private int additionalBlocs, additionalAnimals, additionalBombs, additionalBallons;
    private Configuration configLevel;
    private int level;
    private ArrayList<Joueur> gamers;
    //private Compte compte;
    private static boolean IsGui;


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
        //this.compte = joueur.getCompte();
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

    /*============================== Get Set ==========================================================*/

    public int getCurentLevel(){return level;}

    public Plateau getPlateau(){return plateau;}

    public Compte getCompte(){return getCurrentJoueur().getCompte();}


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
    }

    public void selectJoueur(String pseudo)
    {
        for (Joueur j : gamers) {
            if(j.getPseudo().equals(pseudo)){
                selectJoueur(j);
                return;
            }
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
        this.gamers.add(this.joueur);
    }


    /*============================== Game board functions ============================================================*/

    /**
     * Recreate game board using current user level parameter.
     */
    public void createPlateau(){
        createPlateau(joueur.getCompte().getLastUnlockLevel());
    }

    /**
     * Recreate game board using the selected level.
     */
    public void createPlateau(int level)
    {
        this.level=level;
        // this helps create plateau in all cases cause getLevel check getUnlockLevel

        this.plateau = new Plateau(Integer.parseInt(this.configLevel.getLevelValue(level, "height")), Integer.parseInt(this.configLevel.getLevelValue(level, "width")));

        //initialisation of start values of number of blocs, animals etc.
        initialBlocs = Integer.parseInt(configLevel.getLevelValue(level, "initialBlocs"));
        initialAnimals = Integer.parseInt(configLevel.getLevelValue(level, "initialAnimals"));
        initialImmoBlocs = Integer.parseInt(configLevel.getLevelValue(level, "initialImmoBlocs"));
        initialBombs = Integer.parseInt(configLevel.getLevelValue(level, "initialBombs"));
        initialBallons = Integer.parseInt(configLevel.getLevelValue(level, "initialBallons"));

        //initialisation of additional values which would be added during the game according the level
        additionalBlocs = Integer.parseInt(configLevel.getLevelValue(level, "additionalBlocs"));
        additionalAnimals = Integer.parseInt(configLevel.getLevelValue(level, "additionalAnimals"));
        additionalBombs = Integer.parseInt(configLevel.getLevelValue(level, "additionalBombs"));
        additionalBallons = Integer.parseInt(configLevel.getLevelValue(level, "additionalBallons"));

        this.plateau.remplirPlateau(initialImmoBlocs, initialBlocs, initialAnimals, initialBombs);
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
        String status = "";
        if (plateau.gameState().equals("lost") &&
                joueur.getCompte().getBallon() < 1 &&
                joueur.getCompte().getGold() < joueur.getCompte().BALLON_PRIX)
        {
            status = "lost";
        }
        else if (plateau.gameState().equals("win"))
        {
            status = "win";
        }
        else
        {
            status = "continue";
        }
        return status;
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
        if (this.plateau==null){return false;}
        // player has clicked on plateau
        if (plateau.isOnPlateau(x, y))
        {
            // not empty cell
            if (!plateau.isEmpty(x, y))
            {
                ObjectSurCase obj = plateau.getObject(x, y);
                // if bloc
                boolean b=false;                                    //for GUI
                if (obj instanceof Bloc)
                {
                    b=pressBlocs(x,y);
                }
                // if animal
                else if (obj instanceof Animal){}
                // if outil
                else if (obj instanceof Outil)
                {
                    // if bomb
                    if (obj instanceof Bomb)
                    {
                        int points1 = plateau.bombExplosion(x, y);
                        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points1 * 5);
                        b=true;
                    }

                    //if ballon
                    else if (obj instanceof Ballon)
                    {
                        //ballon destroyed all blocs of his color
                        String ballonColor = ((Ballon)plateau.getObject(x, y)).getColor();
                        plateau.ballonExplosion(ballonColor);
                        b=true;

                    }
                }
                check();
                return b;
            }
            else{} //empty cell
        }
        else{} // player has clicked out of plateau
        return false;
    }
    /**
    *Delete a groupe of blocs is the press blocs have close blos of the same color.
    */
    public boolean pressBlocs(int x, int y){
        // if only one bloc
        if (plateau.getGroup(x, y).size() == 0){
            if (!this.IsGui){
                System.out.println("\n Can't delete single bloc \n");
            }
        }
        else{ // if group of blocs
            LinkedList<Case> blocGroupe = plateau.getGroup(x, y);
            int points = blocGroupe.size();
            // delete group and shift down all upstairs elements
            deleteGroupeBlocs(blocGroupe);
            this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points*5);
            return true;
            // TODO add another blocs and elements (depends of level)
            //  if (configLevel.getValue(level, "addBlocs") == "true")
        }
        return false;
    }
    /**
    *Delete all Blocs of a groupe.
    */
    public void deleteGroupeBlocs(LinkedList<Case> blocGroupe){
        for (Case p : blocGroupe)
        {
            int xCoord = p.getCoordX();
            int yCoord = p.getCoordY();
            plateau.cleanCase(xCoord, yCoord);
            plateau.shiftDown(xCoord, yCoord);
        }
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

        //plateau.rescueAnimals_1();
        check();
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
        check();
    }

    /**
     * Makes shifts and rescue of animals ans blocs
     */
    public void check()
    {
        rescue();
        plateau.shiftLeft();
        plateau.shiftAnimal();
        rescue();
        plateau.shiftLeft();
        plateau.shiftAnimal();
        rescue();
        plateau.shiftLeft();
    }

    public void endLevel(){
        if(getCurentLevel()==getCompte().getLastUnlockLevel()){
            getCompte().unlockNextLevel();
        }
        getCompte().saveScore(getCurentLevel(),true);
    }

    public void endLevelLost(){
        getCompte().saveScore(getCurentLevel(),false);
    }

    /*============================== Private & internal functions ====================================================*/
    /**
     * check animal on the floor, rescue them and print message for each of them
     */
    private void rescue()
    {
        LinkedList<Case> animals = plateau.getAnimalsOnFloor();
        plateau.rescueAnimals(animals);
        if (plateau.getIsRescued())
        {
            if (!this.IsGui)
            {
                for (int i = 0; i < animals.size(); i++)
                {
                    System.out.println("One pet rescued");
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
