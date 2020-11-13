package prs;

public class Ballon extends Outil
{
    private String name;

    public Ballon(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    // isClicable does't need to be implemented because succeed from Outil
}