package prs;

import prs.map.Compte;
import prs.map.Joueur;
import prs.map.Map;
import prs.Jeu;
import prs.graphics.*;
import prs.usuel.image;

import java.io.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.awt.Image;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;


public class GuiPrs
{
    private static Data data;
    private Jeu motor;
    //private Map map;
    private boolean ballonToPlace;

    public GuiPrs(){
        motor = new Jeu(false);
        ballonToPlace=false;
    }

    // GET SET -------------------------------------------------------------------
    public Data getData(){return data;}
    public static void setData(Data d){data=d;}
    public Jeu getJeu(){return motor;}

    /*============================= graphics functions ==========================*/
    /**
    *Add main window.
    */
    public boolean addFrame(){
      try {
        getData().setFrame(new Frame());
        return true;
      }catch (Exception e) {
        return false;
      }
    }
    /**
    *Add a Panel to alowed player to choose a level.
    */
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
        getData().setTailleDUneCase(getData().getHeightMax()/12);
        getData().setPPlateau(new PanelPlateau());
        getData().getPPlateau().setPlateau(getJeu().getPlateau());
        getData().setPInfo(new PanelInfo(getJeu().getCompte(),this));
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



    /**
    *Add action for the button.
    */
    public void addActionToButton(){
        //button action
        getData().getPInfo().getAddBallon().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getJeu().getJoueur().buyBallon();
                repaint();
            }
        });
        getData().getPInfo().getPlaceBallon().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ballonToPlace=true;
            }
        });
        getData().getPInfo().getBackToMap().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getJeu().endLevelLost();
                JOptionPane.showMessageDialog(getData().getFrame(),"The level is lost. Try again.. ");
                playOrExit(true);
            }
        });
        getData().getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBoutonFermer();
        getData().getPInfo().getIaFinish().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                letsIaPlay();
            }
        });
    }
    public void setBoutonFermer(){
      getData().getFrame().addWindowListener(new WindowAdapter() {
          @Override // indique au compilateur qu'on écrit sur la méthode windowClosing déjà défini et il est sencé vérifier qu'on a pas fait de bêtise d'écriture.
          public void windowClosing(WindowEvent e) {
              System.out.println("close widows & do a save.");
              getJeu().endLevelLost();
              playOrExit(false);
            //quit();
          }
      });
    }

    /**
    *set the best size for PanelPlateau & center it.
    */
    public boolean setPanelPlateauSize(){
      try {
        int dimX = 1+getData().getTailleDUneCase()*getJeu().getPlateau().getWidth();
        int dimY = 1+getData().getTailleDUneCase()*getJeu().getPlateau().getHeight();
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
      }catch (Exception e) {ok=false;}
      img = image.getImage("background2.jpg");
      try {
        img = img.getScaledInstance(getData().getWidthMax(),getData().getHeightMax() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setPPlateauImg(img);
      }catch (Exception e) {ok=false;}
      img = image.getImage("animal.jpg");
      try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setAnimal(img);
      }catch (Exception e) {ok=false;}
      img = image.getImage("inmovable.jpg");
      try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setInmovable(img);
      }catch (Exception e) {ok=false;}
      img = image.getImage("bomb.jpg");
      try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setBomb(img);
      }catch (Exception e) {ok=false;}
      img = image.getImage("ballons.jpg");
      /*try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setBallon(img);
      }catch (Exception e) {ok=false;}*/
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

    //-------------------------------------------------------------------------
    public void iniIsGui(){
        data = new Data();
    }
    public void GUIGame()
    {
        boolean needToLoadAccount = false;
        if(data==null){iniIsGui();needToLoadAccount=true;}
        System.out.println(addFrame());
        //faire changer ou creer un compte graphiquement.
        if(needToLoadAccount){
            addAccountWindow();
        }

        //loadPlayerInfo();
        //Map map = new Map(getJeu().getJoueur());
        System.out.println(iniImage());
        System.out.println(addPanelMap());
        System.out.println("addLevel "+addLevel());
        repaint();
        System.out.println("end of main of GUIGame");
    }

    public void GUIClicAction(){
        /*rescue(); //in pressCell.
        getJeu().getPlateau().shiftLeft();
        rescue();*/
        repaint();
        getJeu().getPlateau().gameState();
        endAction();
    }
    /**
    *End game if level is win or lost.
    */
    public void endAction(){
        if (getJeu().getPlateau().gameState().equals("win")){
            endAction(true);
        }else if (getJeu().getPlateau().gameState().equals("lost")){
            endAction(false);
        }
    }
    /**
    *End game by a win or a lost.
    */
    public void endAction(boolean win){
        if (win){
            getJeu().endLevel();
            JOptionPane.showMessageDialog(getData().getFrame(),"Congratulations, you win !");
            int answer = JOptionPane.showConfirmDialog(getData().getFrame(),"do you want to go go back to map ?");
            playOrExit(answer==0);
        }
        else{
            getJeu().endLevelLost();
            JOptionPane.showMessageDialog(getData().getFrame(),"The level is lost. Try again.. ");
            int answer = JOptionPane.showConfirmDialog(getData().getFrame(),"do you want to try again ?");
            playOrExit(answer==0);
        }
    }

    public void playOrExit(boolean play){
        getJeu().Close();
        if(play){
                getData().getFrame().dispose();
                GUIGame();
        }else{
            System.out.println("See you !! ");
            getData().getFrame().dispose();
        }
    }
    public void launchLevel(int i){
        if(getJeu().getJoueur().getCompte().isLevelUnlock(i)){
            getJeu().createPlateau(i);
            addPanelPlateau();
            showMessageGUI("level"+i);
        }
    }
    public void showMessageGUI(String key){
        JOptionPane.showMessageDialog(getData().getFrame(),getJeu().showMessage(key));
    }
    /**
     function that calculate coordinates of a click & launch pressCell
     */
    public void clicOnPlateau(int x, int y)
    {
        if(x<0 || y<0){return;}
        //if(x>getData().getScreenDimX() || y>getData().getScreenDimY()){return;}
        if(ballonToPlace){
            getJeu().placeBallon(x/getData().getTailleDUneCase(),y/getData().getTailleDUneCase());
            ballonToPlace=false;
        }else{
            getJeu().pressCell(x/getData().getTailleDUneCase(),y/getData().getTailleDUneCase());
        }
        //TODO add a sound ?
    }
    //--------------------
    public void windowPlay()
    {
        //window with button "play"
        //click listener for button -> create accountWindow
    }
    /**
    * Show some windows to make player chose an existing account or create a new 1.
    */
    public void addAccountWindow()
    {
        //window with menu deroulant:
        int answer = JOptionPane.showConfirmDialog(getData().getFrame(),"do you have an account ?");
        if(answer==0){ //if yes
            Object pseudo=null;
            boolean joueurSet=false;
            while(!joueurSet){
                //make player choose on the list.
                Object[] elementsO = getJeu().getListOfJoueurs().toArray();
                String[] elements = new String[elementsO.length];
                int i=0;
                for (Object o : elementsO ) {
                    if(o instanceof Joueur){
                        elements[i]=((Joueur)o).getPseudo();
                        i++;
                    }else{
                        System.out.println("error of convertion in GuiPrs.");
                    }
                }
                //(Component parentComponent, Object message, String title, int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue)
                pseudo = JOptionPane.showInputDialog(getData().getFrame(),"choose a player","Player selection",JOptionPane.QUESTION_MESSAGE,null,elements,null);
                if(pseudo instanceof String){
                    //TODO
                    getJeu().selectJoueur((String)pseudo);
                    joueurSet = true;
                }else{
                    System.out.println("fail to set selectJoueur.");
                }
            }
        }else{
            //ask a pseudo while it isn't a new pseudo.
            String pseudo = "";
            do {
                pseudo = JOptionPane.showInputDialog(getData().getFrame(), "Enter a new pseudo", "Anonymus");
            } while (getJeu().isJoueurExisting(pseudo) || pseudo.equals(""));
            //j = new Joueur();
            //j.setPseudo(pseudo);
            getJeu().createNewJoueur(pseudo);
        }
    }

    public void letsIaPlay(){
        int clic=0;
        while(clic < 5000){
            if(getJeu().getPlateau().gameState().equals("win")){
                endAction(true); //special Gui part
            }
            int x = new Random().nextInt(getJeu().getPlateau().getHeight() - 1);
            int y = new Random().nextInt(getJeu().getPlateau().getWidth() - 1);
            if(getJeu().pressCell(x,y)){
                clic=0;
                paintAll(); //special Gui part
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.out.println("can't pause in Gui.letsIaPlay");
                }
            }
            clic++;
        }
        endAction(false); //special Gui part
    }


    //-------------------
    public static void main(String[] args) {
        GuiPrs gui = new GuiPrs();
        gui.GUIGame();
    }
}
