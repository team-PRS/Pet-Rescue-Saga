package prs;

import prs.map.Compte;
import prs.map.Joueur;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
* Main class / controller class
*/
public class Jeu
{
  //  private Map map;
    private Joueur joueur;
    private Plateau plateau;
    private int initialBlocs, initialAnimals, initialImmoBlocs, initialBombs, initialBallons;
    private int additionalBlocs, additionalAnimals, additionalBombs, additionalBallons;
    private Configuration configLevel;
    private int level;
    private ArrayList<Joueur> gamers;
    private Compte compte;
    private Scanner scanAnswer;
  //  private Frame frame;
  //  private PanelMap pMap;
 //   private PanelPlateau pPlateau;
 //   private PanelGame pGame;
 //   private static Data data;
 //   private boolean GUImode;


    /*================================= Constructor ==============================*/
    public Jeu()
    {
        this.configLevel = new Configuration("config.txt");
        this.scanAnswer = new Scanner(System.in);
        this.joueur = new Joueur();
        this.compte = joueur.getCompte();
        this.level = compte.getUnlockLevel();
        this.gamers = new ArrayList<Joueur>();

        this.plateau = new Plateau(Integer.parseInt(this.configLevel.getLevelValue(this.level, "height")),
                Integer.parseInt(this.configLevel.getLevelValue(level, "width")));

    }

    /*============================= Common functions ==========================*/

    public int getLevel()
    {
        return this.joueur.getCompte().getUnlockLevel();
    }

    public String showMessage(String request)
    {
        String message = "";

        String l1 = "LEVEL 1" + "\n " +
                "\nTry to eliminate the groups of blocs (greek letters) of the same color under animals ( @ )\n" +
                "so they will go down and will be rescued";

        String l2 = "LEVEL 2\nOn this level you can explode bombs ( * )\n" +
                "to destroy the cubes surrounding. Animals will not lost.";

        String l3 = "LEVEL 3\n         \n" +
                "      ";
        
        String l4 = "LEVEL 4\nGo ahead!";

        String pr = "\nOne pet is rescued";

        if (request.equals("level1")) {message = l1;}
        else if (request.equals("level2")) {message = l2;}
        else if (request.equals("level3")) {message = l3;}
        else if (request.equals("level4")) {message = l4;}
        else if (request.equals("petRescued"))
        {
            message = pr;
        }

        return message;
    }

  //  public void launchLevel(int i){
  //      if(joueur.isLevelUnlock(i)){
  //          //TODO get config information
  //          if(i==1){
  //              createPlateau();
  //              if(GUImode){
  //                  addPanelPlateau();
  //              }
  //          }
  //      }else{
  //          System.out.println("Level "+i+" is locked.");
  //      }
  //  }

    private void createPlateau()
    {
        this.level = getLevel();      // this helps create plateau in all cases cause getLevel check getUnlockLevel
                                    // and make + 1.. or give 1 if where is not account yet

        this.plateau = new Plateau(Integer.parseInt(this.configLevel.getLevelValue(this.level, "height")),
                Integer.parseInt(this.configLevel.getLevelValue(level, "width")));

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

        this.plateau.remplirPlateau(initialImmoBlocs, initialBlocs, initialAnimals, initialBallons);
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
                    if (plateau.getGroup(x, y).size() == 0)
                    {
                        System.out.println("\n Can't delete single bloc \n");
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
                    //TODO get sound
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

    /*================= Joueur: Serialisation / DeSerialisation ====================*/

    public ArrayList<Joueur> getListOfJoueurs()                         // get list of players and save it in array gamers
    {
        //ArrayList<Joueur> gamers = null;
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
            return new ArrayList<Joueur>();
        }
        return this.gamers;
    }

    public void createNewAccount(String name)  // set pseudo which gamer give and add joueur to array gamers
    {
        Joueur newRecord = new Joueur();
        this.joueur = newRecord;
        this.joueur.setPseudo(name);
        //set link to compte
        this.compte = this.joueur.getCompte();
        this.gamers.add(this.joueur);
    }

    public void saveToFile()                     //save joueur to file - to use in the and of game, before exit
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
            System.out.println("Can't write to file");
        }
    }


 //   /*============================= graphics functions ==========================*/
 //
 //   // GET SET -------------------------------------------------------------------
 //   public Frame getFrame(){return frame;}
 //   public int getWidthMax(){return getFrame().getWidth();}
 //   public int getHeightMax(){return getFrame().getHeight();}
 //   public PanelPlateau getPPlateau(){return pPlateau;}
 //   public Data getData(){return data;}
 //   public static void setData(Data d){data=d;}
 //   public void addPlateau(Plateau p){plateau=p;}
 //
 //   public boolean addFrame(){
 //     try {
 //       frame = new Frame();
 //       return true;
 //     }catch (Exception e) {
 //       return false;
 //     }
 //   }
 //   public boolean addPanelMap(){
 //     try {
 //       pMap = new PanelMap(this);
 //       pMap.setSize(getWidthMax(),getHeightMax());
 //       frame.setContentPane(pMap);
 //       return true;
 //     }catch (Exception e) {
 //       return false;
 //     }
 //   }
 //   /**
 //   *Add a Panel to represent a level.
 //   */
 //   /*public boolean addPanelPlateau(int id){
 //     try {
 //       pGame = new PanelGame();
 //       frame.setContentPane(pGame);
 //       pGame.setJeu(this);
 //       //TODO load the save of the id level & ask pPlateau to print it.
 //       return true;
 //     }catch (Exception e) {
 //       return false;
 //     }
 //   }*/
 //   /**
 //   *Add a Panel to represent a level.
 //   */
 //   public boolean addPanelPlateau(){
 //     try {
 //       pPlateau = new PanelPlateau();
 //       pPlateau.setPlateau(plateau);
 //       pGame = new PanelGame(pPlateau);
 //       pGame.setJeu(this);
 //       frame.setContentPane(pGame);
 //       setPanelPlateauSize();
 //       //TODO load the save of the id level & ask pPlateau to print it.
 //       return true;
 //     }catch (Exception e) {
 //       return false;
 //     }
 //   }
 //   public boolean setPanelPlateauSize(){
 //     try {
 //       int dimX = 1+getData().getTailleDUneCase()*pPlateau.getPlateau().getWidth();
 //       int dimY = 1+getData().getTailleDUneCase()*pPlateau.getPlateau().getHeight();
 //       int xCenter = (getWidthMax()-dimX)/2;
 //       int yCenter = (getHeightMax()-dimY)/2;
 //       pPlateau.setBounds(xCenter,yCenter,dimX,dimY);
 //       System.out.println(xCenter+" "+yCenter);
 //       pGame.revalidate();
 //       return true;
 //     }catch (Exception e) {
 //       return false;
 //     }
 //   }
 //   /**
 //   *{@summary Load game images.}
 //   *It need to have getFrame()!=null
 //   */
 //   public boolean iniImage(){
 //     boolean ok = true;
 //     Image img = image.getImage("background.jpg");
 //     try {
 //       img = img.getScaledInstance(getWidthMax(),getHeightMax() ,Image.SCALE_SMOOTH);
 //       if(img==null){throw new NullPointerException();}
 //       getData().setPMapImg(img);
 //     }catch (Exception e) {
 //       ok=false;
 //     }
 //     img = image.getImage("background2.jpg");
 //     try {
 //       img = img.getScaledInstance(getWidthMax(),getHeightMax() ,Image.SCALE_SMOOTH);
 //       if(img==null){throw new NullPointerException();}
 //       getData().setPPlateauImg(img);
 //     }catch (Exception e) {
 //       ok=false;
 //     }
 //     img = image.getImage("animal.jpg");
 //     try {
 //       img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
 //       if(img==null){throw new NullPointerException();}
 //       getData().setAnimal(img);
 //     }catch (Exception e) {
 //       ok=false;
 //     }
 //     img = image.getImage("inmovable.jpg");
 //     try {
 //       img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
 //       if(img==null){throw new NullPointerException();}
 //       getData().setInmovable(img);
 //     }catch (Exception e) {
 //       ok=false;
 //     }
 //     return ok;
 //   }
 //   public void repaint(){
 //     frame.repaint();
 //     //frame.printAll(frame.getGraphics());
 //   }
 //   /**
 //   * Unlock the next level
 //   */
 //   public boolean addLevel(){
 //     if(pMap.getNbrButton() < data.getNbrLevelAviable()){
 //       pMap.addLevel();
 //       return true;
 //     }
 //     return false;
 //   }
 //   public boolean paintAll(){
 //     try {
 //       frame.paintAll(frame.getGraphics());
 //       return true;
 //     }catch (Exception e) {
 //       //error.error("fail repaintAll");
 //       return false;
 //     }
 //   }
 //   //static
 //   public static void pause(int ms){
 //     //if(ms<1){error.error("fail to pause "+ms);}
 //     try {
 //         Thread.sleep(ms);
 //     } catch (InterruptedException ie) {
 //         //error.error("fail to pause "+ms);
 //     }
 //   }
 //   /**
 //    function that calculate coordinates of a click & launch pressCell
 //    */
 //   public void clicOnPlateau(int x, int y)
 //   {
 //       if(x<0 || y<0){return;}
 //       //if(x>Data.getScreenDimX() || y>Data.getScreenDimY()){return;}
 //       pressCell(x/Data.getTailleDUneCase(),y/Data.getTailleDUneCase());
 //       //TODO add a sound ?
 //   }
 //
    /*============================= Console UI functions ==========================*/

    public boolean wantPlay()                                           // first reception - want to play or not?
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


    public void accountAdministration()                               // ask create or download account
    {
        final int CreateNewAccountId = 0;
        int Id = CreateNewAccountId;

        if (    (null != this.gamers)
             && (0 < this.gamers.size())
           )
        {
            boolean bCorrupted = false;
            System.out.println("To start game you need to select or create new account");

            System.out.println("0: Create new");

            int index = 1;
            try
            {
                for (Joueur gamer : this.gamers)
                {
                    System.out.println(String.valueOf(index) + ": load " + gamer.getPseudo());
                    index ++;
                }
            }
            catch (NullPointerException e)
            {
                System.out.println("ERROR: Account data is corrupted, new account has to be created");
                bCorrupted = true;
            }

            if (!bCorrupted)
            {
                System.out.print("Please enter action nubmer:");
                while (true)
                {
                    String strId = scanAnswer.next();
                    try
                    {
                        Id = Integer.parseInt(strId);
                    } catch (NumberFormatException e)
                    {
                        Id = -1;
                    }
                    if ((Id >= 0) && (Id <= this.gamers.size()))
                    {
                        break;
                    } else
                    {
                        System.out.print("numer error, value should be in range: [0 .. " + String.valueOf(this.gamers.size()) + "]");
                        System.out.print("Please try again:");
                    }
                }
            }
        }

        if (Id == CreateNewAccountId)
        {
            boolean bCreateNew = true;
            String pseudo = askPseudo();
            if (null != this.gamers)
            {
                for (Joueur j : this.gamers)
                {
                    if (j.getPseudo().equals(pseudo))
                    {
                        this.joueur = j;
                        //set link to compte
                        this.compte = this.joueur.getCompte();

                        bCreateNew = false;
                    }
                }
            }

            if (bCreateNew)
            {
                createNewAccount(pseudo);
            }
        }
        else
        {
            this.joueur = this.gamers.get(Id - 1);
            //set link to compte
            this.compte = this.joueur.getCompte();
        }

    }

    private String askPseudo()
    {
        System.out.print("Please enter your pseudo : ");
        String nameJoueur = scanAnswer.next();
        return nameJoueur;
    }

    public int[] askCoordinates()
    {
        boolean IsValid = false;
        int xCoord = 0; int yCoord = 0;

        String CoordStr;
        System.out.print("\nIn which cell do you want to play now? (Exemple: b3) : ");

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

    public char askAction()                                       // ask that gamer want to do
    {
        System.out.print("\nActions:\n" +
                         "a - activate your ballon\n" +
                         "b - buy the ballon /cost " + String.valueOf(Compte.ballonPrix) + " golds/\n" +
                         "c - click on the cell\n" +
                         "e - activate bomb\n" +
                         "g - convert score to gold /1 ingot = " + String.valueOf(Compte.PointsPerGoldCoin) + " points/\n" +
                         "q - quite game (q)\n" +
                         "Select (a/b/c/e/g/q):\n");
        String Action = scanAnswer.next();
        return Action.charAt(0);
    }

    public String levelInfo(int l)                              // compose level to call message level
    {
        return "level" + Integer.toString(l);
    }

    public void printPlateau()		                            // print Plateau
    {
       // System.out.println("h:" + plateau.getHeight() + " l:" + plateau.getWidth());

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
                        System.out.print(" + ");
                    else if (((Bloc) obj).getColor() == "BLUE")
                        System.out.print(" α ");
                    else if (((Bloc) obj).getColor() == "YELLOW")
                        System.out.print(" β ");
                    else if (((Bloc) obj).getColor() == "RED")
                        System.out.print(" γ ");
                    else if (((Bloc) obj).getColor() == "GREEN")
                        System.out.print(" δ ");
                    else
                        System.out.print(" ε ");
                }
                else if (obj instanceof Bomb)
                    System.out.print(" * ");
                else if (obj instanceof Ballon)
                    System.out.print(" օ ");
                else if (obj instanceof Animal)
                    System.out.print(" @ ");
                else
                    System.out.print(" - ");
            }
            System.out.println("");
        }
    }

    public void rescue()                                   // check animal on the floor, rescue them and print message for each of them
    {
        LinkedList<Point> animals = plateau.getAnimalsOnFloor();
        plateau.rescueAnimals(animals);
        if (plateau.getIsRescued())
        {
            for (int i = 0; i < animals.size(); i++)
                System.out.println(showMessage("petRescued"));
        }

        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + animals.size() * 10);
    }

    public String askPlayOrExit()                                // ask if gamer want to exit
    {
        System.out.print("Do you want exit or play ? (ex/pl)");
        String answer = scanAnswer.next();
        return answer.toLowerCase();
    }

    public boolean isExit()
    {
        boolean IsValid = false;
        boolean IsExit = false;
        String answer = askPlayOrExit();
        while (!IsValid)
        if (answer.equals("ex") || answer.equals("e") || answer.equals("exit"))
        {
            IsValid = true;
            IsExit = true;
            System.out.println("See you !! \n");
        }
        else if (answer.equals("pl") || answer.equals("p") || answer.equals("play"))
        {
            IsValid = true;
            this.level ++;
            saveToFile();
        }
        else
        {
            System.out.println("Wrong input, try again\n");
        }

        return IsExit;
    }


    public void finish() { this.scanAnswer.close(); }      //close console UI




    /*============================= PRINCIPAL FUNCTIONS (Console UI & GUI) ==========================*/

    public void consoleGame()
    {
       // GUImode=false;
        if (wantPlay())
        {
            this.gamers = getListOfJoueurs();
            accountAdministration();
            String gameStatus = "continue";
            boolean endLevel = false;
            boolean forcequite = false;
            while ( (!endLevel) && (!forcequite))
            {
                System.out.println(showMessage(levelInfo(getLevel())));
                System.out.println("");
                createPlateau();
                gameStatus = "continue";
                
                while (!gameStatus.equals("lost") && !gameStatus.equals("win") && (!forcequite))
                {
                    this.joueur.getCompte().afficheInfo();                   
                    printPlateau();

                    char action = askAction();
                    if (action == 'c')          //clic
                    {
                        int[] coord = askCoordinates();
                        pressCell(coord[0], coord[1]);
                    } else if (action == 'b')    //buy ballon
                    {
                        joueur.buyBallon();
                    } else if (action == 'a')    //activate ballon
                    {
                        if (this.joueur.getCompte().getBallon() > 0)
                        {
                            int[] coord = askCoordinates();
                            String color = plateau.getColorOfBloc(coord[0], coord[1]);
                            int points = plateau.ballonExplosion(color);
                            joueur.activateBallon();
                            this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points * 5);
                        }
                    } else if (action == 'e')    //activate bombe
                    {
                        int[] coord = askCoordinates();
                        int points1 = plateau.bombExplosion(coord[0], coord[1]);
                        this.joueur.getCompte().setPoints(this.joueur.getCompte().getPoints() + points1 * 5);
                    } else if (action == 'g')    //convert score to gold
                    {
                        joueur.convertPointsToGold();
                    } else if (action == 'q')    //convert score to gold
                    {
                        forcequite = true;
                    } else System.out.println("Wrong input, try again");

                    rescue();
                    plateau.shiftLeft();
                    rescue();
                    plateau.shiftLeft();
                    //printPlateau();

                    gameStatus = plateau.gameState();
                } //while (!gameStatus.equals("lost") && !gameStatus.equals("win") && (!forcequite))

                if (!forcequite)
                {
                    if (gameStatus.equals("win"))
                    {
                        this.level++;
                        this.joueur.getCompte().setUnlockLevel(this.joueur.getCompte().getUnlockLevel() + 1);

                        System.out.println("\n===========================================");
                        System.out.println("======= Congratulations, you win !! =======");
                        System.out.println("\n===========================================");

                        this.joueur.getCompte().afficheInfo();
                        printPlateau();

                        endLevel = isExit();
                    } else if (gameStatus.equals("lost"))
                    {
                        System.out.println("The level is lost. Try again.. ");

                        System.out.println("\n==============================================");
                        System.out.println("======= The level is lost. Try again.. =======");
                        System.out.println("\n==============================================");

                        this.joueur.getCompte().afficheInfo();
                        printPlateau();

                        endLevel = isExit();
                    }
                }
            } //while (!endLevel)
        }

        saveToFile();
        finish();               //close scanner (en mode textuel)
    }

 //   public void GUIGame()
 //   {
 //       GUImode=true;
 //       data = new Data();
 //       joueur = new Joueur();
 //       map = new Map(joueur);
 //       System.out.println(addFrame());
 //       System.out.println(iniImage());
 //       //TODO faire changer ou creer un compte graphiquement.
 //       System.out.println(addPanelMap());
 //       System.out.println("addLevel "+addLevel());
 //       repaint();
 //       boolean b=true;
 //       while(b){pause(100);}
 //       System.out.println("fin du main de GUIGame");
 //   }

    /*================================= MAIN ===================================*/

    public static void main (String[] args)
    {
        Jeu jeu = new Jeu();

        jeu.consoleGame();


      // if(args.length>0 && args[1].equals("text")){
      //     jeu.consoleGame();
      // }else{
      //     jeu.GUIGame();
      // }
    }
}
