package prs.usuel;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class image {

  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary Try to read an Image file<br/>}
   *Image are File who end with ".png" or ".jpg".<br>
   *@param f File that sould contain the Image.
   *@return Image on the file or null if something went wrong.
   */
  public static BufferedImage readImage(File f){
    if(!isImage(f)){error.error("can not read file "+f);return null;}
    try {
      return ImageIO.read(f);
    }catch (IOException e) {
      //on n'affiche plus systématiquement l'erreur car on a parfois besoin d'essayer d'ouvrir une image et de ressevoir null pour réésayer dans un autre répertoire.
      //erreur.erreur("L'Image sencé être dans le fichier suivant n'as pas été correctement trouvé et chargée. "+f.toString());
      return null;
    }
  }public static BufferedImage readImage(String s){return readImage(new File(s));}

  public static boolean isImage(File f){//2 possibilité, le fichier ce termine par .pnj ou par .jpg.
    if(f==null){return false;}
    if(str.contient(f.getName(),".png",2)){return true;}
    if(str.contient(f.getName(),".jpg",2)){return true;}
    return false;
  }
  /**
   *{@summary get an Image in a directory.<br/>}
   *Image are File who end with ".png" or ".jpg".<br>
   *.png or .jpg do not need to be precised on the name.<br>
   *@param nom Name of the file without REP part.
   *@param repTemp directory were to search.
   */
  public static BufferedImage getImage(String nom, String repTemp){
    BufferedImage imgr = null;
    repTemp = str.sToDirectoryName(repTemp);
    // si le .png ou .jpg etc n'as pas été précisé, on teste les 2 terminaison (.png d'habord).
    if(str.contient(nom,".png",2) || str.contient(nom,".jpg",2)){//si on a déja un .png ou un .jpd a la fin du nom.
      imgr = readImage(new File(repTemp+nom));
    }else{//sinon il nous faut ajouter l'un ou l'autre.
      String nomTemp = nom + ".png";
      File f = new File(repTemp+nomTemp);
      if(f.exists()){
        imgr = readImage(f);
      }else{
        nomTemp = nom + ".jpg";
        f = new File(repTemp+nomTemp);
        if(f.exists()){
          imgr = readImage(f);
        }else{
          return null;
        }
      }
    }
    return imgr;
  }

}
