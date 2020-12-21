package prs.usuel;

public class tableau {

  // FUNCTIONS -----------------------------------------------------------------
  public static String tableauToString(String t [], String sep){
    String sr = "";
    for (String s :t ) {
      if(sr.length()!=0){sr=sr+sep+s;}
      else{sr+=s;}
    }return sr;
  }public static String tableauToString(String t []){ return tableauToString(t," ");}
  public static String tableauToString(int t [],String sep){
    String sr = "";
    for (int s :t ) {
      if(sr.length()!=0){sr=sr+sep+s;}
      else{sr+=s;}
    }return sr;
  }public static String tableauToString(int t []){ return tableauToString(t," ");}
  public static String tableauToString(byte t [],String sep){
    String sr = "";
    for (byte s :t ) {
      if(sr.length()!=0){sr=sr+sep+s;}
      else{sr+=s;}
    }return sr;
  }public static String tableauToString(byte t []){ return tableauToString(t," ");}
  public static String tableauToString(boolean t [],String sep){
    String sr = "";
    for (boolean s :t ) {
      if(sr.length()!=0){sr=sr+sep+s;}
      else{sr+=s;}
    }return sr;
  }public static String tableauToString(boolean t []){ return tableauToString(t," ");}

  /**
  *{@summary check if t contain x.<br>}
  */
  public static boolean contient(char t [], char x){
    int lent = t.length;
    for (int i=0;i<lent ;i++ ) {
      if(t[i]==x){return true;}
    }
    return false;
  }
}
