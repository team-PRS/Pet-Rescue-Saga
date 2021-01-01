package prs;

import java.util.Random;

public class Ballon extends Outil
{
    private String color;
    private String[] colors = new String[]{"YELLOW", "BLUE", "RED", "GREEN", "PURPLE"};


    /**
     *  Random color ballons's constructor (used in functions of filling game board)
     */
    public Ballon()                   
    {
        int index = new Random().nextInt(colors.length);
        this.color = colors[index];
    }

    public String getColor(){return color;}

    // isClicable succeed from Outil, always true

}