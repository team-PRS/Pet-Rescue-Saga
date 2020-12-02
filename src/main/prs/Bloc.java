package prs;

public class Bloc extends ObjectSurCase
{
    private String color;

    public enum Color
    {
        YELLOW,
        BLUE,
        RED,
        GREEN,
        PURPLE
    }

    public Bloc(String color)
    {
        this.color = color;
    }

    public String getColor(){return color;}

    public void setColor(String c)
    {
        this.color = c;
    }


    public boolean sameColor(Bloc a, Bloc b)                    // check if two blocs has the same color
    {
        if (a.getColor() == b.getColor())
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean isClicable ()
    {
        return true;
    }
}
