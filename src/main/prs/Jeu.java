package prs;

import prs.map.Compte;
import prs.map.Joueur;
import prs.graphics.*;
import prs.usuel.image;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.Image;

public class Jeu
{
    private Joueur joueur;
    private Plateau plateau;
    private int initialBlocs, initialAnimals, initialImmoBlocs, initialBombs, initialBallons;
    private int additionalBlocs, additionalAnimals, additionalBombs, additionalBallons;
    private Configuration configLevel;
    private int level;
    private Compte compte;
    private Scanner scanAnswer;
    private Frame frame;
    private PanelMap pMap;
    private PanelPlateau pPlateau;
    private PanelGame pGame;
    private static Data data = new Data();


    /*================================= Constructor ==============================*/
    public Jeu()
    {
        this.configLevel = new Configuration("config.txt");
        this.scanAnswer = new Scanner(System.in);
        this.joueur = new Joueur();
        this.compte = joueur.getCompte();
        this.level = compte.getUnlockLevel();
        //initialisation of start values of number of blocs, animals etc.
        initialBlocs = Integer.parseInt(configLevel.getLevelValue(level, "initialBlocs"));
        initialAnimals = Integer.parseInt(configLevel.getLevelValue(level, "initialAnimals"));
        initialImmoBlocs = Integer.parseInt(configLevel.getLevelValue(level, "initialImmoBlocs"));

        // if level with outils
        if (configLevel.getLevelValue(level, "outils") == "true")
        {
            initialBombs = Integer.parseInt(configLevel.getLevelValue(level, "initialBombs"));
            initialBallons = Integer.parseInt(configLevel.getLevelValue(level, "initialBallons"));
        }
        else
        {
            initialBombs = 0;
            initialBallons = 0;
        }

        //initialisation of additional values which would be added during the game according the level
        if (configLevel.getLevelValue(level, "addBlocs") == "true")
        {
            additionalBlocs = Integer.parseInt(configLevel.getLevelValue(level, "additionalBlocs"));
        }
        else {additionalBlocs = 0;}

        if (configLevel.getLevelValue(level, "addAnimals") == "true")
        {
            additionalAnimals = Integer.parseInt(configLevel.getLevelValue(level, "additionalAnimals"));
        }
        else {additionalAnimals = 0;}

        if (configLevel.getLevelValue(level, "addBombs") == "true")
        {
            additionalBombs = Integer.parseInt(configLevel.getLevelValue(level, "additionalBombs"));
        }
        else {additionalBombs = 0;}

        if (configLevel.getLevelValue(level, "addBallons") == "true")
        {
            additionalBallons = Integer.parseInt(configLevel.getLevelValue(level, "additionalBallons"));
        }
        else {additionalBallons = 0;}


    }
    // GET SET -------------------------------------------------------------------
    public Frame getFrame(){return frame;}
    public int getWidthMax(){return getFrame().getWidth();}
    public int getHeightMax(){return getFrame().getHeight();}
    public PanelPlateau getPPlateau(){return pPlateau;}
    public static Data getData(){return data;}
    public void addPlateau(Plateau p){plateau=p;}
    /*============================= Common functions ==========================*/

    private void createPlateau(Configuration config, Joueur gamer)
    {
        this.joueur = gamer;
        this.level = joueur.getCompte().getUnlockLevel();
        this.plateau = new Plateau(Integer.parseInt(configLevel.getLevelValue(level, "height")),
                Integer.parseInt(configLevel.getLevelValue(level, "width")));
        this.plateau.remplirPlateau(initialImmoBlocs, initialBlocs, initialAnimals,initialBallons);
    }
    /**
    function that caculate coordinates of a clic & launch pressCell
    */
    public void clicOnPlateau(int x, int y)
    {
      if(x<0 || y<0){return;}
      //if(x>Data.getScreenDimX() || y>Data.getScreenDimY()){return;}
      pressCell(x/Data.getTailleDUneCase(),y/Data.getTailleDUneCase());
      //TODO add a sound ?
    }
    /**
      {@summary function which provide reactions for clic:}<br>
     - if group of blocs, delete them all<br>
     - if one bloc, do nothing<br>
     - if bomb, make an explosion and deletion of 8 cells around<br>
     - if ballon, delete all bloc of its color<br>
     - if outil, launch it<br>
     - if animal, do nothing for the moment<br>
     */
    public void pressCell(int x, int y)
    {
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
                    if (plateau.getGroup(x, y).size() == 1)
                    {
                        System.out.println("Can't delete single bloc");
                    }
                    // if group of blocs
                    else
                    {
                        LinkedList<Point> blocGroupe = plateau.getGroup(x, y);
                        // delete group and syhift down all upstairs elements
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
                else if (obj instanceof Animal)
                {
                    //TODO get sound
                }
                // if outil
                else if (obj instanceof Outil)
                {
                    // if bomb
                    if (obj instanceof Bomb)
                    {
                        //bomb destroyed 9 cells -- its cell + 8 around
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
            }
            //empty cell
            else
            {
                System.out.println("Ooups! This case is empty");
            }
        }
        // player has clicked out of plateau
        else
        {
            System.out.println("Out of bounds of plateau");
        }
    }


    /*============================= graphics functions ==========================*/
    public boolean addFrame(){
      try {
        frame = new Frame();
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    public boolean addPanelMap(){
      try {
        pMap = new PanelMap(this);
        frame.setContentPane(pMap);
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    /**
    *Add a Panel to represent a level.
    */
    /*public boolean addPanelPlateau(int id){
      try {
        pGame = new PanelGame();
        frame.setContentPane(pGame);
        pGame.setJeu(this);
        //TODO load the save of the id level & ask pPlateau to print it.
        return true;
      }catch (Exception e) {
        return false;
      }
    }*/
    /**
    *Add a Panel to represent a level.
    */
    public boolean addPanelPlateau(){
      try {
        pPlateau = new PanelPlateau();
        pPlateau.setPlateau(plateau);
        pGame = new PanelGame(pPlateau);
        pGame.setJeu(this);
        frame.setContentPane(pGame);
        setPanelPlateauSize();
        //TODO load the save of the id level & ask pPlateau to print it.
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    public boolean setPanelPlateauSize(){
      try {
        int dimX = 1+getData().getTailleDUneCase()*pPlateau.getPlateau().getWidth();
        int dimY = 1+getData().getTailleDUneCase()*pPlateau.getPlateau().getHeight();
        int xCenter = (getWidthMax()-dimX)/2;
        int yCenter = (getHeightMax()-dimY)/2;
        pPlateau.setBounds(xCenter,yCenter,dimX,dimY);
        System.out.println(xCenter+" "+yCenter);
        pGame.revalidate();
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    /**
    *Load game images.
    */
    public boolean iniImage(){
      boolean ok = true;
      Image img = image.getImage("background.jpg");
      try {
        img = img.getScaledInstance(getWidthMax(),getHeightMax() ,Image.SCALE_SMOOTH);
        getData().setPMapImg(img);
      }catch (Exception e) {
        ok=false;
      }
      Image img2 = image.getImage("background2.jpg");
      try {
        img2 = img2.getScaledInstance(getWidthMax(),getHeightMax() ,Image.SCALE_SMOOTH);
        getData().setPPlateauImg(img2);
      }catch (Exception e) {
        ok=false;
      }
      return true;
    }
    public void repaint(){
      frame.repaint();
    }
    /**
    * Unlock the next level
    */
    public boolean addLevel(){
      if(pMap.getNbrButton() < data.getNbrLevelAviable()){
        pMap.addLevel();
        return true;
      }
      return false;
    }
    public boolean paintAll(){
      try {
        frame.paintAll(frame.getGraphics());
        return true;
      }catch (Exception e) {
        //error.error("fail repaintAll");
        return false;
      }
    }
    //static
    public static void pause(int ms){
      //if(ms<1){error.error("fail to pause "+ms);}
      try {
          Thread.sleep(ms);
      } catch (InterruptedException ie) {
          //error.error("fail to pause "+ms);
      }
    }

    /*============================= Console UI functions ==========================*/

    public void createAccount() throws IOException
    {
        System.out.println("Let's register");
        String name = askPseudo();
        this.joueur.setPseudo(name);

        ArrayList<Joueur> gamers = new ArrayList<>();
        //Read
        try
        {
            // if where are already records in file
            FileInputStream fis = new FileInputStream("gamers.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            gamers = (ArrayList<Joueur>) ois.readObject();
            ois.close();
            fis.close();
            try
            {
                FileOutputStream fos = new FileOutputStream("gamers.bin");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                gamers.add(joueur);
                oos.writeObject(gamers);
                oos.close();
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            // if where are neither records nor array in file (file is empty)
            gamers.add(joueur);
            try
            {
                FileOutputStream fos = new FileOutputStream("gamers.bin");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                gamers.add(joueur);
                oos.writeObject(gamers);
                oos.close();
                fos.close();
            }
            catch (IOException m)
            {
                m.printStackTrace();
            }
        }
    }
    
    public Joueur loadFromAccount() throws IOException
    {
        System.out.println("Please, enter your pseudo : ");
        String answer = scanAnswer.next().toLowerCase();
        try
        {
            FileInputStream fis = new FileInputStream("gamers.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Joueur> gamers = (ArrayList<Joueur>) ois.readObject();
            for (Joueur gamer : gamers)
            {
                try
                {
                    if (gamer.getPseudo().equals(answer))
                    {
                        this.joueur = gamer;
                        break;
                    }
                }
                catch (NullPointerException e)
                {
                    System.out.println("There is not such pseudo, please try again");
                }
            }
            ois.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return joueur;
    }

    public String gamersToPrint()
    {
        String str = "";
        try
        {
            FileInputStream fis = new FileInputStream("gamers.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Joueur> gamers = (ArrayList<Joueur>) ois.readObject();
            ois.close();
            str = Arrays.toString(new ArrayList[]{gamers});
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public void saveToAccount()           //TODO
    {

    }

    public boolean wantPlay()
    {
        boolean answer = false;
        boolean isCorrectAnswer = false;
        while (!isCorrectAnswer)
        {
            System.out.print("Do you want to play (yes/no) ? ");
            String answerPlay = scanAnswer.next().toLowerCase();
            if ((answerPlay.equals("yes")) || (answerPlay.equals("y")))
            {
                isCorrectAnswer = true;
                answer = true;
            } else if ((answerPlay.equals("no")) || (answerPlay.equals("n")) || (answerPlay.equals("non")))
            {
                isCorrectAnswer = true;
                answer = false;
            } else System.out.println("Wrong input, try again");
        }
        return answer;
    }

    public String hasAccount()
    {
        System.out.print("Do you have an account ? y / n ");
        String rep = scanAnswer.next().toLowerCase();
        return rep;
    }

    public String askPseudo()
    {
        System.out.print("Please enter you pseudo : ");
        String nameJoueur = scanAnswer.next();
        return nameJoueur;
    }

    public int[] askCoordinates()
    {
        boolean IsValid = false;
        int xCoord = 0; int yCoord = 0;

        String CoordStr;
        System.out.print("In which cell do you want to play now? (Exemple: b3) : ");

        while(!IsValid)
        {
            CoordStr = scanAnswer.next();
            if (CoordStr.length() >= 2)
            {
                int input = CoordStr.charAt(0) - 'a';                    // -'a' because all loops started by 0
                for (int i = 0; i < plateau.getHeight(); i++)
                {
                    if (input == i)
                    {
                        xCoord = input;
                        IsValid = true;
                        break;
                    }
                }

                if (IsValid)
                {
                    yCoord = Integer.parseInt(CoordStr.substring(1)) - 1;  // -1 because all loops started by 0, but table markings by 1
                    if (yCoord < 0)
                    {
                        IsValid = false;
                    }
                }
            }
            if (!IsValid)
            {
                System.out.print("wrong input, try again (Exemple: b3) : ");
            }
        }
        return new int[]{xCoord, yCoord};
    }

    public char askAction()
    {

        System.out.print("Do you want \n - click on the cell (c)" +
                "\n - buy the ballon /cost 3 ingots/ (b) " +
                "\n - activate your ballon (a) " +
                "\n - activate bomb (e) " +
                "\n - convert score to gold /1 ingot = 100 points/ (g) " +
                "\n ? \n");
        String Action = scanAnswer.next();
        return Action.charAt(0);
    }

    public void finish() { this.scanAnswer.close(); }


    public void receptionConsole() throws IOException
    {
        boolean isCorrectAnswer = false;
        while (!isCorrectAnswer)
        {
            String answer = hasAccount();
            if (answer.equals("y") || (answer).equals("yes"))
            {
                isCorrectAnswer = true;
                loadFromAccount();
            }
            else if (answer.equals("n") || answer.equals("no"))
            {
                isCorrectAnswer = true;
                createAccount();
            }
            else System.out.println("Wrong input, try again");
        }
    }

    public void printPlateau()		                                    // print Plateau
    {
        System.out.println("Points: " + joueur.getCompte().getScore(1) + " / Gold: " + joueur.getCompte().getGold() +
                        " / Ballon: " + joueur.getCompte().getBallon());
        System.out.println("h:" + plateau.getHeight() + " l:" + plateau.getWidth());
        System.out.println("");
        //print y-scale
        System.out.print("  | ");
        for (int k = 0; k < plateau.getHeight(); k++)
        {
            System.out.print(" " + (k + 1) + " ");
        }
        System.out.println("");
        System.out.print("-----");
        for (int l = 0; l < plateau.getWidth(); l++)
        {
            System.out.print("---");
        }
        System.out.println("");
        //print table
        for (int i = 0; i < plateau.getHeight(); i++)
        {
            char ch = (char) ('a' + i);
            System.out.print(ch + " | ");

            for (int j = 0; j < plateau.getWidth(); j++)
            {
                ObjectSurCase obj = plateau.getObject(i, j);
                if (plateau.getObject(i, j) instanceof Bloc)
                {
                    if (((Bloc) obj).getColor() == "NONE")
                        System.out.print(" nn");
                    else if (((Bloc) obj).getColor() == "BLUE")
                        System.out.print(" bb");
                    else if (((Bloc) obj).getColor() == "YELLOW")
                        System.out.print(" by");
                    else if (((Bloc) obj).getColor() == "RED")
                        System.out.print(" br");
                    else if (((Bloc) obj).getColor() == "GREEN")
                        System.out.print(" bg");
                    else
                        System.out.print(" bp");
                }
                else if (obj instanceof Outil)
                    System.out.print(" u ");
                else if (obj instanceof Animal)
                    System.out.print(" a ");
                else
                    System.out.print(" - ");
            }
            System.out.println("");
        }
    }


    public void consoleGame() throws IOException
    {
        
        if (wantPlay())
        {
            receptionConsole();
            createPlateau(this.configLevel, this.joueur); 
            System.out.println("LEVEL 1\nTry to eliminate the groups of blocs of the same color under animals (a)\n" +
                    "so they will go down and will be rescued\n");
            System.out.println("h:" + plateau.getHeight() + " l:" + plateau.getWidth());
            printPlateau();
            //System.out.println(gamersToPrint());

            while (! plateau.gameState().equals("lost"))
            {
                if (plateau.gameState().equals("continue"))
                {
                   // askAction();
                    char action = askAction();
                    if (action == 'c')          //clic
                    {
                        int[] coord = askCoordinates();
                        pressCell(coord[0], coord[1]);
                    }
                    else if (action == 'b')    //buy ballon
                    {
                        joueur.buyBallon();
                    }
                    else if (action == 'a')    //activate ballon
                    {
                        if (this.joueur.getCompte().getBallon() > 0)
                        {
                            int[] coord = askCoordinates();
                            String color = plateau.getColorOfBloc(coord[0], coord[1]);
                            plateau.ballonExplosion(color);
                            joueur.activateBallon();
                        }
                    }
                    else if (action == 'e')    //activate bombe
                    {
                        int[] coord = askCoordinates();
                        plateau.bombExplosion(coord[0], coord[1]);
                    }
                    else if (action == 'g')    //convert score to gold
                    {
                        //TODO   1 ingot = 100 points
                        joueur.convertPointsToGold();
                    }
                    else System.out.println("Wrong input, try again");

                    printPlateau();
                    plateau.shiftLeft();
                    plateau.rescueAnimals(plateau.getAnimalsOnFloor());
                    printPlateau();
                    plateau.gameState();
                }
                else if (plateau.gameState().equals("win"))
                {
                    System.out.println("Congratulations, you win !! ");
                }
                else
                {
                    System.out.println("The level is lost. Try again.. ");
                }
            }
        }
        else
        {
            finish();               //close scanner (en mode textuel)
        }
    }


    /*================================= MAIN ===================================*/

    public static void main (String[] args) throws IOException
    {
        Jeu test = new Jeu();
        test.consoleGame();
    }
}
