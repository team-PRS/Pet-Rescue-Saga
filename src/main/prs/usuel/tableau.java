package prs.usuel;
//def par défaut des fichiers depuis 0.79.5

public class tableau <T>{
// piste pour utiliser des méthodes sur des tableau sans qu'un type soit forcément donné :
// List<Objet> mulist = new ArrayList<objet>();
// déclaration des Objet
// mylist.add(objet1) etc.
  private static String f = "tableau";
  public static void errorPosition(int i){
    error.error("La position"+" " + i + " n'existe pas dans le tableau.", "tableau.retir");
  }
  public static void errorPositionCorrigé(int i){
    error.error("La position"+" " + i + " "+"n'existe pas dans le tableau"+".", "tableau.retir","On ajoute x en position finale.");
  }
  public static void errorVide(){
    error.alert("Le tableau est vide !","tableau.");
  }
  public static void errorElementManquant(String x){
    error.alert("\""+x + "\" "+"n'est pas présent dans le tableau"+".", "tableau.retirX");
  }
//String
  //Retire
  public static String [] retirer (String t[], int i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      errorPosition(i);
    }
    String tr [] = new String [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
      //System.out.println("On garde " + tr[j]);
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
      //System.out.println("On garde " + tr[j-1]);
    }
    return tr;
  }//public static String [] retire(String t[], int i){retirer(t,i);}

  public static String [] retirerX (String t[], String x){
    int lent = t.length;
    // On  compte le nombre de x a retirer
    int k=0;
    for (int i=0;i<lent ;i++ ) {
      if (x.equals(t[i])){ k++;}
    }
    if (k==0){ errorElementManquant(x);}
    String tr [] = new String [lent-k];
    // On les retire
    int m=0;
    for (int j=0;j<lent; j++){
      if (!x.equals(t[j])){ // Si c'est qqchose qu'on garde :
        tr[m]=t[j]; m++;
      }//sinon j augemnte mais pas m
    }
    return tr;
  }//public static String [] retireX(String t[],String x){return retirerX(t,x);}

//AJOUTE
  public static String [] ajouterX (String t[], String x, int i){
    // Fonction qui permet d'ajouté x en position i du tableau.
    if (i<0 || i>t.length) {
      errorPositionCorrigé(i);
      i = t.length;
    }
    int lentr = t.length+1;
    String tr [] = new String [lentr];
    for (int j=0;j<i ;j++ ) {
      tr [j] = t[j];
    }
    tr[i] = x;
    for (int j=i+1;j<lentr ;j++ ) {
      tr[j] = t[j-1];
    }
    return tr;
  }
  public static String [] ajouterX (String t[], String x){
    return ajouterX(t,x,t.length); // Par défaut on ajoute a la dernière case d'un tableau
  }//public static String [] ajouteX(String t[],String x){return ajouterX(t,x);}
  public static String [] ajouteT (String t1[], String t2[]){
    int lent1 = t1.length; int lent2 = t2.length;
    String tr [] = new String [lent1 + lent2];
    for (int i=0;i<lent1 ;i++ ) {
      tr[i] = t1[i];
    }
    for (int i=0;i<lent2 ;i++ ) {
      tr[lent1 + i] = t2[i];
    }
    return tr;
  }


  // int
  // retir
  public static int [] retire (int t[], int i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      errorPosition(i);
    }
    int tr [] = new int [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
      //System.out.println("On garde " + tr[j]);
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
      //System.out.println("On garde " + tr[j-1]);
    }
    return tr;
  }

  public static int [] retirerX (int t[], int x){
    int lent = t.length;
    // On  compte le nombre de x a retirer
    int k=0;
    for (int i=0;i<lent ;i++ ) {
      if (x == t[i]){ k++;}
    }
    if (k==0){ errorElementManquant(x+"");}
    int tr [] = new int [lent-k];
    // On les retire
    int m=0;
    for (int j=0;j<lent; j++){
      if (x != t[j]){ // Si c'est qqchose qu'on garde :
        tr[m]=t[j]; m++;
      }//sinon j augemnte mais pas m
    }
    return tr;
  }//public static void retirerX(int t[],int x){return retirerX(t,x);}

//AJOUTE
  public static int [] ajouteX (int t[], int x, int i){
    // Fonction qui permet d'ajouté x en position i du tableau.
    if (i<0 || i>t.length) {
      errorPositionCorrigé(i);
      i = t.length;
    }
    int lentr = t.length+1;
    int tr [] = new int [lentr];
    for (int j=0;j<i ;j++ ) {
      tr [j] = t[j];
    }
    tr[i] = x;
    for (int j=i+1;j<lentr ;j++ ) {
      tr[j] = t[j-1];
    }
    return tr;
  }
  public static int [] ajouteX (int t[], int x){
    return ajouteX(t,x,t.length); // Par défaut on ajoute a la dernière case d'un tableau
  }
  public static int [] ajouteT (int t1[], int t2[]){
    int lent1 = t1.length; int lent2 = t2.length;
    int tr [] = new int [lent1 + lent2];
    for (int i=0;i<lent1 ;i++ ) {
      tr[i] = t1[i];
    }
    for (int i=0;i<lent2 ;i++ ) {
      tr[lent1 + i] = t2[i];
    }
    return tr;
  }



  // byte
  // retir
  public static byte [] retire (byte t[], int i){
    int lentr = t.length-1;
    if (i<0 || i>t.length) {
      errorPositionCorrigé(i);
    }
    byte tr [] = new byte [lentr];
    for (int j=0;j<i; j++){
      tr[j]=t[j];
      //System.out.println("On garde " + tr[j]);
    }
    for (int j=i+1; j<lentr+1; j++){
      tr[j-1]=t[j];
      //System.out.println("On garde " + tr[j-1]);
    }

    return tr;
  }

  public static byte [] retireX (byte t[], byte x){
    int lent = t.length;
    // On  compte le nombre de x a retirer
    byte k=0;
    for (byte i=0;i<lent ;i++ ) {
      if (x == t[i]){ k++;}
    }
    if (k==0){ errorElementManquant(x+"");}
    byte tr [] = new byte [lent-k];
    // On les retire
    byte m=0;
    for (byte j=0;j<lent; j++){
      if (x != t[j]){ // Si c'est qqchose qu'on garde :
        tr[m]=t[j]; m++;
      }//sinon j augemnte mais pas m
    }
    return tr;
  }

  //AJOUTE
  public static byte [] ajouteX (byte t[], byte x, int i){
    // Fonction qui permet d'ajouté x en position i du tableau.
    if (i<0 || i>t.length) {
      errorPositionCorrigé(i);
      i = t.length;
    }
    int lentr = t.length+1;
    byte tr [] = new byte [lentr];
    for (int j=0;j<i ;j++ ) {
      tr [j] = t[j];
    }
    tr[i] = x;
    for (int j=i+1;j<lentr ;j++ ) {
      tr[j] = t[j-1];
    }
    return tr;
  }
  public static byte [] ajouteX (byte t[], byte x){
    return ajouteX(t,x,t.length); // Par défaut on ajoute a la dernière case d'un tableau
  }
  public static byte [] ajouteT (byte t1[], byte t2[]){
    int lent1 = t1.length; int lent2 = t2.length;
    byte tr [] = new byte [lent1 + lent2];
    for (byte i=0;i<lent1 ;i++ ) {
      tr[i] = t1[i];
    }
    for (byte i=0;i<lent2 ;i++ ) {
      tr[lent1 + i] = t2[i];
    }
    return tr;
  }

  public static boolean estDansT(int t[], int x){
    int lent = t.length;
    for(int i=0; i<lent;i++){
      if(t[i]==x){ return true;}
    }
    return false;
  }

  //AFFICHE

  // affiche str
  public static void afficher (String t[], String séparateur){
    int lent =t.length;
    if (lent==0) { errorVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher (String t[]){
    afficher(t," ");
  }
  public static void afficher (String t[][]){
    for ( String t2 [] : t) {
      afficher(t2," ");
    }
  }

  // afficher int
  public static void afficher (int t[], String séparateur){
    int lent =t.length;
    if (lent==0) { errorVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher (int t[]){
    afficher(t," ");
  }
  public static void afficher (int t[][]){
    if (t.length==0) { errorVide();}
    for ( int t2 [] : t) {
      afficher(t2," ");
    }
  }
  //afficher byte
  public static void afficher(byte t[], String séparateur){
    int lent =t.length;
    if (lent==0) { errorVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher (byte t[]){
    afficher(t," ");
  }
  //afficher Boolean
  public static void afficher(boolean t[], String séparateur){
    int lent =t.length;
    if (lent==0) { errorVide();}
    for(int i=0;i<lent;i++){
      System.out.print(t[i] + séparateur);
    }
    System.out.println();
  }
  public static void afficher(boolean t[][], String s){
    for (int i=0;i<t.length ;i++ ) {
      afficher(t[i],s);
    }
  }
  public static void afficher(boolean t[]){afficher(t," ");}
  public static void afficher(boolean t[][]){afficher(t," ");}
  // autres
  public static void boucherLesCasesVide(String t[], String tDéfaut []){
    int lent = t.length;
    for (int i=0;i<lent ;i++ ) {
      if( "".equals(t[i])){
        t[i] = tDéfaut[i];
      }
    }
  }
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
  public static boolean contient(int t [], int x){
    int lent = t.length;
    for (int i=0;i<lent ;i++ ) {
      if(t[i]==x){return true;}
    }
    return false;
  }
  public static boolean contient(char t [], char x){
    int lent = t.length;
    for (int i=0;i<lent ;i++ ) {
      if(t[i]==x){return true;}
    }
    return false;
  }
  public static boolean contient(String t [], String x){
    int lent = t.length;
    for (int i=0;i<lent ;i++ ) {
      if(t[i].equals(x)){return true;}
    }
    return false;
  }
}
