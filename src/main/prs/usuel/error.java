package prs.usuel;

public class error {
  public static String lieu0 = "an unknow place";
  public static boolean muet=false;

  public static void setMuet(boolean b){muet=b;}
  private static void println(String s){
    if(!muet){
      System.out.println(s);
    }
  }

  public static void stopGame(){
    println("trouver si dessous la raison et la liste des fonctions qui était en cours lors de l'arrêt forcé :");
    try {
      //@SuppressWarnings("unchecked")
      int x = 7/0;
    } catch (Exception e){
      if(!muet){
        e.printStackTrace();
      }
    }
    System.exit(-1);
  }

  public static void error(String message, String lieu, String correction, boolean fatale){
    String m = "An error";
    if (fatale){
      m = "A fatal error";
    }
    println(m + " occure in " + lieu + " : ");
    println(message);
    if (!correction.equals("")){
      println("Correction : " + correction);
    }
    if (fatale){
      stopGame();
    }
  }
  public static void error(String message, String lieu, String correction){
    error(message, lieu, correction, false);
  }
  public static void error(String message, String lieu, boolean fatale){
    error(message, lieu, "", fatale);
  }
  public static void error(String message, String lieu){
    error(message, lieu, false); // les errors sont non fatale par défaut.
  }
  public static void error(String message){
    error(message, lieu0);
  }
  public static void error(String message, boolean fatale){
    error(message, lieu0, fatale);
  }
  public static void alert(String message, String lieu, String correction){
    println("Something wrong happend in "+ lieu +", maybe there is no reason to worried.");
    if (!message.equals("")) println(message);
    if (!correction.equals("")){
      println("Correction : " + correction);
    }
  }
  public static void alert(String message, String lieu){
    alert(message,lieu,"");
  }
  public static void alert(String message){
    alert(message, lieu0);
  }
  public static void alert(){
    alert("");
  }

}
