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

    public void GUIClose()
    {
        this.Motor.Close();     //save account before closing
    }

    public void windowPlay()
    {
        //window with button "play"
        //click listener for button -> create accountWindow
    }

    public void accountWindow()
    {
        //window with menu deroulant:
        //1) menu "new account" -> create registrationWindow()
        //2) menu avec accounts existants -> getListOfJoueurs()
        //                                   selectJoueur()
        //                                  launchGame()  (va etre creer dans cette classe)
    }

    public void registrationWindow()
    {
        //window with:
        // 1) pannel with text "Invent your pseudo:"
        // 2) feunetre ou enter the pseudo
        // 3) button "enter" -> takes String from 2) + createNewJoueu()

    }

    public void launchGame()
    {
        
    }


    public void main(String[] args)
    {
        
    }

}
