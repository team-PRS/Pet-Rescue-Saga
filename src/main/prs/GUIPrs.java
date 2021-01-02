package prs;

import prs.graphics.PanelGame;
import prs.graphics.PanelPlateau;

public class GUIPrs
{
    private Jeu Motor;
    private PanelPlateau panelPlateau;
    private PanelGame panelGame;

    //et les autres variables de classes graphiques

    public GUIPrs()
    {
        Motor = new Jeu(true);
    }

    /**
     *  Save all accounts to file before closing
     */
    public void GUIClose()
    {
        this.Motor.Close();  
    }

    public void windowPlay()
    {
        //window with button "play"
        //click listener for button -> create accountWindow  
    }

    public void accountWindow()
    {
        //window with menu deroulant:
        //1) menu "new account" -> create registrationWindow() va etre creer dans cette classe
        //2) menu avec accounts existants -> getListOfJoueurs() de la classe Jeu
        //                                   selectJoueur() de la classe Jeu
        //                                   createPanelGame() va etre creer dans cette classe
        //                                  launchGame()  va etre creer dans cette classe
    }

    public void registrationWindow()
    {
        //window with:
        // 1) pannel with text "Invent your pseudo:"
        // 2) feunetre ou taper String pseudo
        // 3) button "enter" -> takes String from 2) + createNewJoueur()

    }

    public void createPanelGame()
    {
        //create window PanelGame
        //create panelPlateau - y passer les params de getPlateauWidth(), getPlateauHeight(), getCell()
        //create panelInfo (y passer les params de Motor.getCurrentJoueur().getCompte() ets)
        //bouton "exit" or prevenir faire GUIClose() quand on ferme window PanelGame -
        //                                -  save all gamers to file before closing window
    }

    public void launchGame()
    {
        
    }


    public void main(String[] args)
    {
        
    }

}
