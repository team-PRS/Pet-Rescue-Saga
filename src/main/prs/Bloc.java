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

    //random color bloc's constructor (to fill at the beginning of game-set or for additional blocs during the game-set)
    //doesn't concern blocs without color (NONE)
    public Bloc()
    {
        int index = new Random().nextInt(colors.length - 1);
        this.color = colors[index];
    }

    public String getColor(){return color;}

    public void setColor(String c) {this.color = c;}


    public boolean sameColor(Bloc a, Bloc b)                    // check if two blocs has the same color
    {
        return ((a.isClicable()) && (b.isClicable()) && (a.getColor().equals(b.getColor())) ? true : false);
    }

    @Override
    public boolean isClicable ()
    {
        return (!color.equals("NONE") ? true : false);        // blocs without color = decoration = non clicable
    }
}
