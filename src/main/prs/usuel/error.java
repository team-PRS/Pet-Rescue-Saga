package prs.usuel;

public class error {

  // FUNCTIONS -----------------------------------------------------------------
  public static void error(String s, String file){
    System.out.println("An error occured in "+file+" :");
    System.out.println(s);
  }
  public static void error(String s){
    error(s,"an unknow file");
  }
}
