package prs.usuel;

public class str {

  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary is subS on the String}
   *@param s String were to search.
   *@param subS String to search on s.
   *@param x 0=s should starts with subS, 1=s should contain subS, 2=s should end with subsS, 3=s should be equals to subS.
   *@return true if it contain subS
   *@version 1.2
   */
  public static boolean contient(String s,String subS, byte x){
    //les cas d'erreur.
    if(s==null){return false;}
    if(subS==null){return false;}
    if(subS.equals("")){return true;}
    if(x<0 || x>4){return false;}
    if(s.length()<subS.length()){return false;}

    //if(x==3){return s.equals(subS);}
    if(x==1){return s.contains(subS);}//quelque part
    int lensubS = subS.length();
    if(x==0){
      s = s.substring(0,lensubS);//au début
    }else if(x==2){
      int lens = s.length();
      s = s.substring(lens-lensubS,lens);//a la fin
    }
    return s.equals(subS);
  }public static boolean contient(String s, String s2, int x){return contient(s,s2,iToBy(x));}
  public static boolean contient(String s,String s2){return contient(s,s2,1);}

  /**
  *{@summary From int to byte}
  *return the max or the min if conversion fail.
  *@version 1.1
  */
  public static byte iToBy(int x){
    if(x>127){ x=127;}//erreurConversion("int To byte",x+"");}
    if(x<-128){ x=-128;}//erreurConversion("int To byte",x+"");}
    return (byte) x;
  }
  /**
  *{@summary Transform a String to a directory name aviable on every os.<br>}
  *if last / is missing it will be add.
  *@param s the String to transform to a directory name.
  *@version 1.3
  */
  public static String sToDirectoryName(String s){
    if(s==null){return null;}
    char w [] = {'<', '>', ':', '\"', '\\', '|', '?', '*'};
    s = filtreCharInterdit(s,w);
    s = ajouterALaFinSiNecessaire(s,"/");
    return s;
  }
  public static String ajouterALaFinSiNecessaire(String s, String fin){
    if(!contient(s,fin,2)){s+=fin;}
    return s;
  }
  /**
  *{@summary Delete forbidden char in the array t.<br>}
  *@param s the String were to delete forbidden char.
  *@param t the array were forbidden char are.
  *@version 1.3
  */
  public static String filtreCharInterdit(String s, char t[]){
    if(s==null){return null;}
    String r = "";
    int len = s.length();
    for (int i=0;i<len ;i++ ) {
      char c = s.charAt(i);
      if(!tableau.contient(t,c)){ r=r+c;}
    }
    return r;
  }
  /**
  *{@summary Delete forbidden char depending of the os.<br>}
  *if os is not define windows char will be deleted.
  *@param s the String were to delete forbidden char.
  *@version 1.3
  */
  public static String filtreCharInterdit(String s){
    if(s==null){return null;}
    char w [] = {'<', '>', ':', '\"', '/', '\\', '|', '?', '*'};
    char ml [] = {':','/','\\'};
    return filtreCharInterdit(s,w);
  }public static String sToFileName(String s){ return filtreCharInterdit(s);}
}
