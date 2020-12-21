package prs;

import java.util.Random;

public class Ballon extends Outil
{
    private String color;
    private String[] colors = new String[]{"YELLOW", "BLUE", "RED", "GREEN", "PURPLE"};

    public Ballon(String color)                     //usual ballon's constructor
    {
        this.color = color;
    }

    public Ballon()                                //random color ballon's constructor (to fill an the beginning of set or for additional ballon)
    {
        int index = new Random().nextInt(5);
        this.color = colors[index];
    }

    public String getColor(){return color;}


    // isClicable succeed from Outil, always true

}