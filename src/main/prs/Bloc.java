package prs;


import java.util.Random;

public class Bloc extends ObjectSurCase
{
    private String color;
    private String[] colors = new String[]{"YELLOW", "BLUE", "RED", "GREEN", "PURPLE", "NONE"};

    public Bloc(String color)                        //usual bloc's constructor
    {
        this.color = color;
    }

    public Bloc()                                   //random color bloc's constructor (to fill an the beginning of set or for additional blocs)
    {
        int index = new Random().nextInt(5);
        this.color = colors[index];
    }

    public String getColor(){return color;}

    public void setColor(String c) {this.color = c;}


    public boolean sameColor(Bloc a, Bloc b)                    // check if two blocs has the same color
    {
        return ((a.isClicable()) && (b.isClicable()) && (a.getColor() == b.getColor()) ? true : false);
    }

    @Override
    public boolean isClicable ()
    {
        return (color != "NONE" ? true : false);        // blocs without color = decoration = non clicable
    }

    public static void main(String[] args)
    {
        Bloc test = new Bloc();
        System.out.println(test.getColor());
        Bloc test1 = new Bloc();
        System.out.println(test1.getColor());
    }
}
