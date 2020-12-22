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

    public Animal()                                   //random type Animal's constructor (to fill an the beginning of set or for additional animal)
    {
        int index = new Random().nextInt(4);
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
