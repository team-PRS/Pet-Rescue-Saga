package prs;

abstract public class ObjectSurCase
{
    private boolean clicable;

    public abstract boolean isClicable();
}
//import prs.list.CCase;
//
//public class ObjectSurCase{
//  private final int id;//in lowercase because it's a non-static var.
//  private static int IDCPT=0;//in uppercase because it's a static var.
//  private CCase cc;
//  public ObjectSurCase(CCase cc){
//    this.cc=cc;
//    id=IDCPT++;//atribute a unique id for every ObjectSurCase.
//  }
//  public ObjectSurCase(int x, int y){
//    //when we will have a link to the Plateau we will be able to link this & a CCase.
//    //It will alowed us to interact with next case with getHaut() getDroite() etc.
//    //this(Main.getPlateau().getCCase(x,y));
//  }
//
//  public CCase getCc(){return cc;}
//  public void setCc(CCase x){cc=x;}
//
//  public boolean equals(ObjectSurCase o){return id==o.getId();}//Or they have the same id or they are not equals.

