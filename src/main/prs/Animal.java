package prs;

import java.util.Random;

public class Animal extends ObjectSurCase
{
    private String type;
    private String[] types = new String[]{"CAT", "DOG", "BIRD", "FROG"};        

    public Animal(String ty)
    {
        this.type = ty;
    }

    /**
     *  Random animal's constructor (used in functions of filling game board)
     */
    public Animal()
    {
        int index = new Random().nextInt(types.length);
        this.type = types[index];

    }

    public String getType()
    {
        return type;
    }

    @Override
    public boolean isClicable()
    {
        return true;
    }    

}
