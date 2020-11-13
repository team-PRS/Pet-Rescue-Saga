package prs;

public class Bomb extends Outil
{
    private String name;

    public Bomb(String name)
    {
        this.name = name;
    }

    // isClicable does't need to be implemented because succeed from Outil


    public void exterminate()
    {
        //TODO I need access to getX et getY but in which class?
    }
}
