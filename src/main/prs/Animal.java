package prs;

public class Animal extends ObjectSurCase
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

}
