package prs;

import prs.Amovible;

public class Animal extends ObjectSurCase implements Amovible, Disparaissant
{
    private int type;

    public enum typeAnimal
    {
        CAT (1),
        DOG (2),
        BIRD (3),
        FROG (3);

        private final int type;

        typeAnimal(int iVal)
        {
            this.type = iVal;
        }

    }

    public Animal(int ty)
    {
        super();
        type = ty;
    }

    public int getType()
    {
        return type;
    }

    @Override
    public boolean isClicable()
    {
        return false;
    }

    @Override
    public void ceDeplacer()
    {
        // TODO clarify method logic with Emilien
    }

    @Override
    public void disparaitre()
    {
      //  if (this.getX() == Plateau.getHeight())  //TODO it's just first outline of idea. I need access to getX but which class Case/Plateau?
        {
            // TODO clarify method logic with Emilien
        }
    }
}
