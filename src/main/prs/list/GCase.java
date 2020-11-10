package prs.list;
import java.io.Serializable;
import prs.usuel.error;
import prs.Case;
import prs.Point;

public class GCase implements Serializable{
  public CCase debut;
  public CCase actuelle;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public GCase(int horizontale, int verticale){
    if(horizontale < 0 || verticale < 0){ error.error("Impossible de creer une carte si petite","GCase.GCase","la carte la plus petite possible a ete creee."); horizontale = 1; verticale = 1;}

    debut = new CCase(new Case(0,0));
    ajouterDroite(horizontale-1, debut);
    int d = 1; CCase debut2;
    while (d < verticale){
      debut2 = new CCase(new Case(0,d)); d++;
      ajouterDroite(horizontale-1, debut2);
      fusionnnerLigne(debut2);
    }
  }
  public GCase(int taille){
    this(taille, taille);
  }
  public GCase(){}
  // GET SET --------------------------------------------------------------------
  public CCase getDebut(){ return debut;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if (debut==null){error.error("La carte est vide","Gcase.afficheToi");return "";}
    return debut.toString();
  }
  public CCase getCCase(int x, int y){
    if(debut==null){
      return null;
    }else{
      if (x==0 && y==0){
        return debut;
      }
    }
    return debut.getCCase(x,y);
  }
  public CCase getCCase(Point p){
    return getCCase(p.getX(),p.getY());
  }

  public void afficheCarte(){
    if (debut==null){
      error.error("La carte est vide","Gcase.afficheCarte");
    }else{
      debut.afficheCarteTout(1);
    }
  }
  public void ajouterDroite(int x, CCase debutDeLaLigne){
    int k = 1; CCase temp; actuelle = debutDeLaLigne;
    while (x>0){
      temp = new CCase(new Case(k,actuelle.getContenu().getP().getY())); k++;
      actuelle.setDroite(temp);
      temp.setGauche(actuelle);
      actuelle = temp;
      x--;
    }
  }
  public void fusionnnerLigne(CCase debutDeLaLigne2){
    CCase debutDeLaLigne = debut;
    while (debutDeLaLigne.getBas() != null){
      debutDeLaLigne = debutDeLaLigne.getBas();
    }
    CCase tempHaut = debutDeLaLigne;
    CCase tempBas = debutDeLaLigne2;
    while(tempHaut != null && tempBas != null){
      tempHaut.setBas(tempBas);
      tempBas.setHaut(tempHaut);
      tempHaut = tempHaut.getDroite();
      tempBas = tempBas.getDroite();
    }
  }
  public void ajouter(Case c){
    if (debut==null){debut = new CCase(c);return;}
    debut.ajouter(c);
  }
}
