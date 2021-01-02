package prs;

import java.awt.Color;
import java.util.Random;

public class Ballon extends Outil
{
    private String color;
    private String[] colors = new String[]{"YELLOW", "BLUE", "RED", "GREEN", "PURPLE"};
    private Color color2;
    private Color[] colors2 = new Color[]{Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA};


    /**
     *  Random color ballons's constructor (used in functions of filling game board)
     */
    public Ballon()
    {
        int index = new Random().nextInt(colors.length);
        this.color = colors[index];
        this.color2 = colors2[index];
    }

    public String getColor(){return color;}
    public Color getColor2(){return color2;}
    public void setColor(int index){
        this.color = colors[index];
        this.color2 = colors2[index];
    }

    // isClicable succeed from Outil, always true

}
