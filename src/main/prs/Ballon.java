package prs;

public class Ballon extends Outil
{
    private String name;

    public Ballon(String name)
    {
        this.name = name;
    }   //TODO add name from enum

    public String getName()
    {
        return name;
    }

    // isClicable does't need to be implemented because succeed from Outil
}