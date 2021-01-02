package prs;

import prs.map.Compte;
import prs.map.Joueur;
import prs.map.Map;
import prs.Jeu;
import prs.graphics.*;
import prs.usuel.image;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.awt.Image;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GuiPrs
{
    private static Data data;
    private Jeu motor;
    private Map map;

    public GuiPrs(){
        motor = new Jeu(false);
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
                //...
            }
        });
        getData().getPInfo().getBackToMap().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getJeu().endLevelLost();
                JOptionPane.showMessageDialog(getData().getFrame(),"The level is lost. Try again.. ");
                playOrExit(true);
            }
        });
    }
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
      try {
        img = img.getScaledInstance(data.getTailleDUneCase(),data.getTailleDUneCase() ,Image.SCALE_SMOOTH);
        if(img==null){throw new NullPointerException();}
        getData().setBallon(img);
      }catch (Exception e) {ok=false;}
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
        if(data==null){iniIsGui();}
        System.out.println(addFrame());
        //TODO faire changer ou creer un compte graphiquement.
        loadPlayerInfo();
        map = new Map(getJeu().getJoueur());
        System.out.println(iniImage());
        System.out.println(addPanelMap());
        System.out.println("addLevel "+addLevel());
        repaint();
        System.out.println("end of main of GUIGame");
    }
    //TODO add all of this for GUI
    /*else if (action == 'b')    //buy ballon
    {
        getJeu().getJoueur().buyBallon();
    }
    else if (action == 'a')    //activate ballon
    {
        if (this.getJeu().getJoueur().getCompte().getBallon() > 0)
        {
            int[] coord = askCoordinates();
            String color = getJeu().getPlateau().getColorOfBloc(coord[IsGui=true;
        data = new Data();
        getJeu().getJoueur() = new getJeu().getJoueur()();0], coord[1]);
            getJeu().getPlateau().ballonExplosion(color);
            getJeu().getJoueur().activateBallon();
        }
    }
    else if (action == 'e')    //activate bombe
    {
        int[] coord = askCoordinates();
        getJeu().getPlateau().bombExplosion(coord[0], coord[1]);
    }*/

    public void GUIClicAction(){
        /*rescue(); //in pressCell.
        getJeu().getPlateau().shiftLeft();
        rescue();*/
        repaint();
        getJeu().getPlateau().gameState();
        endAction();
    }
    public void endAction(){
        if (getJeu().getPlateau().gameState().equals("win"))
        {
            getJeu().endLevel();
            JOptionPane.showMessageDialog(getData().getFrame(),"Congratulations, you win !");
            int answer = JOptionPane.showConfirmDialog​(getData().getFrame(),"do you want to replay ?");
            playOrExit(answer==0);
        }
        else if (getJeu().getPlateau().gameState().equals("lost"))
        {
            getJeu().endLevelLost();
            JOptionPane.showMessageDialog(getData().getFrame(),"The level is lost. Try again.. ");
            int answer = JOptionPane.showConfirmDialog​(getData().getFrame(),"do you want to try again ?");
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
    /*public void finish() {
        getData().getFrame().dispose();
    }*/
    public void loadPlayerInfo(){
        //... TODO
        //getJeu().createPlateau();
    }
    /**
     function that calculate coordinates of a click & launch pressCell
     */
    public void clicOnPlateau(int x, int y)
    {
        if(x<0 || y<0){return;}
        //if(x>Data.getScreenDimX() || y>Data.getScreenDimY()){return;}
        getJeu().pressCell(x/Data.getTailleDUneCase(),y/Data.getTailleDUneCase());
        //TODO add a sound ?
    }

}
