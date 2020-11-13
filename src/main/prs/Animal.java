package prs;

import prs.Amovible;

public class Animal extends ObjectSurCase implements Amovible, Disparaissant
{
    private int ID;

    public enum typeAnimal
    {
        CAT (1),
        DOG (2),
        BIRD (3),
        FROG (3);

        private final int ID;

        typeAnimal(int iVal)
        {
            this.ID = iVal;
        }

    }

    public Animal(int id)
    {
        ID = id;
    }

    public int getID()
    {
        return ID;
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
