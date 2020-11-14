package prs;

public class Bloc extends ObjectSurCase implements Amovible, Disparaissant
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

    public String getColor()
    {
        return color;
    }

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

    @Override
    public void ceDeplacer()
    {
        // TODO clarify method logic with Emilien
        //Where we want to go.
        CCase emptyCc = ...;
        disparaitre();//remove from Plateau
        emptyCc.getContenu().setO(this);//add at the new place.
    }

    @Override
    public void disparaitre()
    {
        // TODO clarify method logic with Emilien
        cc.getContenu().removeO(); //remove this from the Case (& from the Plateau).
    }

}
