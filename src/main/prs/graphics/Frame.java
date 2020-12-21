package prs.graphics;
import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

/**
{@summary Main Frame.}
*/
public class Frame extends JFrame{

  // CONSTRUCTORS --------------------------------------------------------------
  public Frame(){
    setTitle("Pet Rescue Saga");
    //setLocationRelativeTo(null); // fenetre centrée
    setExtendedState(JFrame.MAXIMIZED_BOTH);//Frame take as many space as it can.
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // bouton fermer par défaut.
    //TODO notre bouton fermer si dessous qui demande une validation de l'utilisateur.
    /*
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    Runnable r = new Runnable() {
      @Override // indique au compilateur qu'on écrit sur la méthode run.
      public void run() {
        setBoutonFermer(); //Méthode qui demande validation si le joueur clic sur le bouton fermé la fenetre.
      }
    };*/
    //fullScreen
    /*
    setUndecorated(true);
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    gd.setFullScreenWindow(this);
    */
    setVisible(true);
  }
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  public void setBoutonFermer(){
    this.addWindowListener(new WindowAdapter() {
        @Override // indique au compilateur qu'on écrit sur la méthode windowClosing déjà défini et il est sencé vérifier qu'on a pas fait de bêtise d'écriture.
        public void windowClosing(WindowEvent e) {
          quit();
        }
    });
  }
  public void quit(){
    try {
      boolean choix=true;
      //ici on pourrait demander au joueur si il veux vraiment quitter le jeu
      //si validation.
      if(choix){
        setVisible(false);
        dispose();
        // On provoque une fermeture normale du jeu.
        //Main.quitter(); // on pourrait réaliser une sauvegarde automatique.
        System.exit(0);
      }//sinon rien.
    }catch (Exception e) {
      System.out.println("Someting wrong happend in Frame.quit()");
      System.exit(1);
    }
  }
  // SUB-CLASS -----------------------------------------------------------------

}
