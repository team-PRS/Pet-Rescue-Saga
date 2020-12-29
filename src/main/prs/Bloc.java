package prs;

import java.awt.Color;
import java.util.Random;

public class Bloc extends ObjectSurCase
{
    private String color;
    private String[] colors = new String[]{"YELLOW", "BLUE", "RED", "GREEN", "PURPLE", "NONE"};
    private Color color2;
    private Color[] colors2 = new Color[]{Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, new Color(0,0,0,0)};
    public Bloc(String color)                        //usual bloc's constructor
    {
        this.color = color;
    }

    //random color bloc's constructor (to fill at the beginning of game-set or for additional blocs during the game-set)
    //doesn't concern blocs without color (NONE)
    public Bloc(int nbrOfMaxColor)
    {
        if(nbrOfMaxColor<2){nbrOfMaxColor=2;}
        if(nbrOfMaxColor>colors.length - 1){nbrOfMaxColor=colors.length - 1;}
        int index = new Random().nextInt(nbrOfMaxColor);
        this.color = colors[index];
        this.color2 = colors2[index];
    }

    public String getColor(){return color;}
    public void setColor(String c) {this.color = c;}
    public Color getColor2(){return color2;}
    public void setColor2(Color c) {this.color2 = c;}


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
