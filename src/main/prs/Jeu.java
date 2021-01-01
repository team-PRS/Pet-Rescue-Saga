package prs;

import prs.map.Compte;
import prs.map.Joueur;
import prs.map.Map;
import prs.graphics.*;
import prs.usuel.image;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.Image;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
* Main class / controller class
*/
public class Jeu
{
    private Map map;
    private Joueur joueur;
    private Plateau plateau;
    private int initialBlocs, initialAnimals, initialImmoBlocs, initialBombs, initialBallons;
    private int additionalBlocs, additionalAnimals, additionalBombs, additionalBallons;
    private Configuration configLevel;
    private int level;
    //private int curentLevel;
    private ArrayList<Joueur> gamers;
    private Compte compte;
    private boolean IsGui;
    private static Data data;

    private Scanner scanAnswer;

    /*================================= Constructor ==============================*/
    public Jeu()
    {
        this.configLevel = new Configuration("config.txt");
        this.scanAnswer = new Scanner(System.in);
        this.joueur = new Joueur();
        this.compte = joueur.getCompte();
        this.level = compte.getLastUnlockLevel();
        this.gamers = new ArrayList<Joueur>();

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

    /*============================= Common functions ==========================*/
    public int getCurentLevel(){return level;}
    public int iniLevel()
    {
        level = 0;
        if (this.joueur.getCompte().getLastUnlockLevel() == 1)
        {
            level = 1;
        }
        else
        {
            level = downloadAccount().getCompte().getLastUnlockLevel();
        }
        return level;
    }

    public String showMessage(String request)
    {
        String message = "";

        String l1 = "LEVEL 1\nTry to eliminate the groups of blocs of the same color under animals ( @ )\n" +
                "so they will go down and will be rescued";

        String l2 = "LEVEL 2\nOn this level you can explode bombs ( * )\n" +
                "to destroy the cubes surrounding. Animals will not lost.";

        String l3 = "LEVEL 3\n         \n" +
                "      ";

        String l4 = "LEVEL 4\nGo ahead!";

        String pr = "\nOne pet is rescued";
        String haveAccount="Do you have an account ? y / n ";
        String pseudo="Please enter your pseudo : ";

        if (request.equals("level1")) {message = l1;}
        else if (request.equals("level2")) {message = l2;}
        else if (request.equals("level3")) {message = l3;}
        else if (request.equals("level4")) {message = l4;}
        else if (request.equals("petRescued")){message = pr;}
        else if (request.equals("haveAccount")){message = haveAccount;}
        else if (request.equals("pseudo")){message = pseudo;}

        return message;
    }
    public void showMessageGUI(String key){
        JOptionPane.showMessageDialog(getData().getFrame(),showMessage(key));
    }

    public void launchLevel(int i){
        System.out.println("launch level "+i);//@a
        if(joueur.isLevelUnlock(i)){
            //TODO get config information
            if(i==1){
                createPlateau();
                if(IsGui){
                    addPanelPlateau();
                    showMessageGUI("level"+i);
                }
            }
        }else{
            System.out.println("Level "+i+" is locked.");
        }
        System.out.println("plateau : "+plateau);//@a
        printPlateau();//@a
    }

    private void createPlateau()
    {
        this.level = iniLevel();      // this helps create plateau in all cases cause iniLevel check getUnlockLevel
                                    // and make + 1.. or give 1 if where is not account yet

        this.plateau = new Plateau(Integer.parseInt(this.configLevel.getLevelValue(this.level, "height")),
                Integer.parseInt(this.configLevel.getLevelValue(level, "width")));
        this.plateau.remplirPlateau(initialImmoBlocs, initialBlocs, initialAnimals,initialBallons);
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
        System.out.println("pressCell: "+x+" "+y);//@a
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

    public Joueur downloadAccount()                                     // download account from array gamers
    {
        try
        {
            for (Joueur gamer : this.gamers)
            {
                if (this.joueur.getPseudo().equals(gamer.getPseudo()))
                {
                    return gamer;
                }
            }
        }
        catch (NullPointerException e)
        {
            System.out.println("Can't find record" + this.joueur.getPseudo());
        }
        return null;
    }

    public void createNewAccount(ArrayList<Joueur> gamers, String name)  // set pseudo which gamer give and add joueur to array gamers
    {
        this.joueur.setPseudo(name);
        this.gamers.add(this.joueur);
        System.out.println(this.joueur.toString());
    }

    public void saveToFile(ArrayList<Joueur> gamers)                     //save joueur to file - to use in the and of game, before exit
    {
        this.gamers.add(this.joueur);
        try
        {
            FileOutputStream fos = new FileOutputStream("gamers.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gamers);
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't write to file");
        }
    }


    /*============================= graphics functions ==========================*/

    // GET SET -------------------------------------------------------------------
    public Data getData(){return data;}
    public static void setData(Data d){data=d;}

    public boolean addFrame(){
      try {
        getData().setFrame(new Frame());
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    public boolean addPanelMap(){
      try {
        getData().setPMap(new PanelMap(this));
        getData().getPMap().setSize(getData().getWidthMax(),getData().getHeightMax());
        getData().getFrame().setContentPane(getData().getPMap());
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    /**
    *Add a Panel to represent a level.
    */
    public boolean addPanelPlateau(){
      try {
        getData().setPPlateau(new PanelPlateau());
        getData().getPPlateau().setPlateau(plateau);
        getData().setPInfo(new PanelInfo(compte,this));
        getData().setPGame(new PanelGame(getData().getPPlateau(),getData().getPInfo()));
        getData().getPGame().setJeu(this);
        getData().getFrame().setContentPane(getData().getPGame());
        setPanelPlateauSize();
        addActionToButton();
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    public void addActionToButton(){
        //button action
        getData().getPInfo().getAddBallon().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                joueur.buyBallon();
                repaint();
            }
        });
        getData().getPInfo().getPlaceBallon().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //...
            }
        });
    }
    public boolean setPanelPlateauSize(){
      try {
        int dimX = 1+getData().getTailleDUneCase()*getData().getPlateau().getWidth();
        int dimY = 1+getData().getTailleDUneCase()*getData().getPlateau().getHeight();
        int xCenter = (getData().getWidthMax()-dimX)/2;
        int yCenter = (getData().getHeightMax()-dimY)/2;
        getData().getPPlateau().setBounds(xCenter,yCenter,dimX,dimY);
        getData().getPInfo().setBounds(getData().getPPlateau().getWidth()+xCenter+10,yCenter,300,300);
        getData().getPGame().revalidate();
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    /**
    *{@summary Load game images.}
    *It need to have getFrame()!=null
    */
    public boolean iniImage(){
      boolean ok = true;
      Image img = image.getImage("background.jpg");
      try {
        img = img.getScaledInstance(getData().getWidthMax(),getData().getHeightMax() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setPMapImg(img);
      }catch (Exception e) {
        ok=false;
      }
      img = image.getImage("background2.jpg");
      try {
        img = img.getScaledInstance(getData().getWidthMax(),getData().getHeightMax() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setPPlateauImg(img);
      }catch (Exception e) {
        ok=false;
      }
      img = image.getImage("animal.jpg");
      try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setAnimal(img);
      }catch (Exception e) {
        ok=false;
      }
      img = image.getImage("inmovable.jpg");
      try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setInmovable(img);
      }catch (Exception e) {
        ok=false;
      }
      return ok;
    }
    public void repaint(){
      getData().getFrame().repaint();
      //paintAll();
    }
    /**
    * Unlock the next level
    */
    public boolean addLevel(){
      if(getData().getPMap().getNbrButton() < data.getNbrLevelAviable()){
        getData().getPMap().addLevel();
        return true;
      }
      return false;
    }
    public boolean paintAll(){
      try {
        getData().getFrame().paintAll(getData().getFrame().getGraphics());
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
    /**
     function that calculate coordinates of a click & launch pressCell
     */
    public void clicOnPlateau(int x, int y)
    {
        if(x<0 || y<0){return;}
        //if(x>Data.getScreenDimX() || y>Data.getScreenDimY()){return;}
        pressCell(x/Data.getTailleDUneCase(),y/Data.getTailleDUneCase());
        //TODO add a sound ?
    }

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

    public String hasAccount()                                          // ask if gamer has account
    {
        System.out.print(showMessage("haveAccount"));
        String rep = scanAnswer.next().toLowerCase();
        return rep;
    }

    public void accountAdministration()                               // ask create or download account
    {
        boolean isCorrectAnswer = false;
        while (!isCorrectAnswer)
        {
            String answer = hasAccount();
            if (answer.equals("y") || (answer).equals("yes"))
            {
                isCorrectAnswer = true;
                accountAdministration(true);
            }
            else if (answer.equals("n") || answer.equals("no"))
            {
                isCorrectAnswer = true;
                accountAdministration(false);
            }
            else System.out.println("Wrong input, try again");
        }
    }
    public void accountAdministration(Boolean b){
        if(b){
            downloadAccount();
        }else{
            String pseudo = "null";
            if(IsGui){
                pseudo = JOptionPane.showInputDialog(showMessage("pseudo"));
            }else{
                pseudo = askPseudo();
            }
            createNewAccount(this.gamers, pseudo);
        }

    }

    public String askPseudo()
    {
        System.out.print(showMessage("pseudo"));
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

    public char askAction()                                       // ask that gamer want to do
    {

        System.out.print("\nDo you want \n - click on the cell (c)" +
                "\n - buy the ballon /cost 3 ingots/ (b) " +
                "\n - activate your ballon (a) " +
                "\n - activate bomb (e) " +
                "\n - convert score to gold /1 ingot = 100 points/ (g) " +
                "\n ? \n");
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
        System.out.println("\nPoints: " + joueur.getCompte().getScore(1) + " / Gold: " + joueur.getCompte().getGold() +
                        " / Ballon: " + joueur.getCompte().getBallon());
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
                        System.out.print(" ≡ ");
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
            if(IsGui){

            }else{
                System.out.println(showMessage("petRescued"));
            }
        }
    }

    public String askPlayOrExit()                                // ask if gamer want to exit
    {
        System.out.print("Do you want exit or play ? (ex/pl)");
        String answer = scanAnswer.next();
        return answer.toLowerCase();
    }


    public void playOrExit()                                 // save account to file and close the program
    {
        boolean IsValid = false;
        String answer = askPlayOrExit();
        while (!IsValid){
            if (answer.equals("ex") || answer.equals("e") || answer.equals("exit") || answer.equals("quit"))
            {
                IsValid = true;
                playOrExit(false);
            }
            else if (answer.equals("pl") || answer.equals("p") || answer.equals("play"))
            {
                IsValid = true;
                playOrExit(true);
            }
            else
            {
                System.out.println("Wrong input, try again");
            }
        }
    }
    public void playOrExit(boolean play){
        this.joueur.getCompte().unlockNextLevel();
        saveToFile(gamers);
        // TODO save points, ballons etc
        if(play){
            if(IsGui){
                finish();
                GUIGame();
            }else{
                launchLevel(iniLevel());
            }
        }else{
            System.out.println("See you !! ");
            finish();
        }
    }

 //  public void exit()                                 // save account to file and close the program
 //  {
 //
 //      saveToFile(gamers);
 //      finish();
 //  }

    public void finish() {
        if(IsGui){
            getData().getFrame().dispose();
        }else{
            this.scanAnswer.close();
        }
    }      //close console UI
    public void loadPlayerInfo(){
        this.gamers = getListOfJoueurs();
        if(IsGui){
            int answer = JOptionPane.showConfirmDialog​(getData().getFrame(),showMessage("haveAccount"));
            accountAdministration(answer==0);
        }else{
            accountAdministration();
        }
        createPlateau();
    }

    /*============================= PRINCIPAL FUNCTIONS (Console UI & GUI) ==========================*/

    public void consoleGame()
    {
        IsGui=false;
        if (wantPlay())
        {
            loadPlayerInfo();
            System.out.println(gamers.toArray().toString());
            System.out.println(showMessage(levelInfo(iniLevel())));
            printPlateau();

            while (! plateau.gameState().equals("lost"))
            {
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
                        joueur.convertPointsToGold();
                    }
                    else System.out.println("Wrong input, try again");
                    rescue();
                    plateau.shiftLeft();
                    rescue();
                    printPlateau();

                    plateau.gameState();

                    if (plateau.gameState().equals("win"))
                    {
                        System.out.println("\nCongratulations, you win !! \n");
                        playOrExit();
                        createPlateau();
                    }
                    else if (plateau.gameState().equals("lost"))
                    {
                        System.out.println("The level is lost. Try again.. ");
                        playOrExit();
                    }
            }
        }
        else
        {
            finish();               //close scanner (en mode textuel)
        }
    }
    public void iniIsGui(){
        IsGui=true;
        data = new Data();
        //joueur = new Joueur();
    }
    public void GUIGame()
    {
        if(data==null){iniIsGui();}
        System.out.println(addFrame());
        //TODO faire changer ou creer un compte graphiquement.
        loadPlayerInfo();
        map = new Map(joueur);
        System.out.println(iniImage());
        System.out.println(addPanelMap());
        System.out.println("addLevel "+addLevel());
        repaint();
        System.out.println("end of main of GUIGame");
    }
    //TODO add all of this for GUI
    /*else if (action == 'b')    //buy ballon
    {
        joueur.buyBallon();
    }
    else if (action == 'a')    //activate ballon
    {
        if (this.joueur.getCompte().getBallon() > 0)
        {
            int[] coord = askCoordinates();
            String color = plateau.getColorOfBloc(coord[IsGui=true;
        data = new Data();
        joueur = new Joueur();0], coord[1]);
            plateau.ballonExplosion(color);
            joueur.activateBallon();
        }
    }
    else if (action == 'e')    //activate bombe
    {
        int[] coord = askCoordinates();
        plateau.bombExplosion(coord[0], coord[1]);
    }*/

    public void GUIClicAction(){
        rescue();
        plateau.shiftLeft();
        rescue();
        repaint();
        plateau.gameState();
        if (plateau.gameState().equals("win"))
        {
            JOptionPane.showMessageDialog(getData().getFrame(),"Congratulations, you win !");
            int answer = JOptionPane.showConfirmDialog​(getData().getFrame(),"do you want to replay ?");
            playOrExit(answer==0);
        }
        else if (plateau.gameState().equals("lost"))
        {
            JOptionPane.showMessageDialog(getData().getFrame(),"The level is lost. Try again.. ");
            int answer = JOptionPane.showConfirmDialog​(getData().getFrame(),"do you want to try again ?");
            playOrExit(answer==0);
        }
    }

    /*================================= MAIN ===================================*/

    public static void main (String[] args)
    {
        Jeu jeu = new Jeu();

        if(args.length>0 && args[0].equals("text")){
            jeu.consoleGame();
        }else{
            jeu.GUIGame();
        }
    }
}
