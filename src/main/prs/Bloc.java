package prs;
import java.awt.Color;
import java.util.Random;

public class Bloc extends ObjectSurCase
{
    private Color color;
    private Color[] colors = new Color[]{Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, new Color(0,0,0,0)};
    private String colorName;
    private String[] colorsName = new String[]{"YELLOW", "BLUE", "RED", "GREEN", "PURPLE", "NONE"};

    public Bloc(int idColor)                        //usual bloc's constructor
    {
      this.color = colors[idColor];
      this.colorName = colorsName[idColor];
    }
    public Bloc(boolean colored)
    {
      if(colored){
        int index = new Random().nextInt(5);
        this.color = colors[index];
        this.colorName = colorsName[index];
      }else{
        this.color = colors[5];
        this.colorName = colorsName[5];
      }
    }
    public Bloc()                                   //random color bloc's constructor (to fill an the beginning of set or for additional blocs)
    {
        this(true);
    }

    public String getColorName(){return colorName;}
    public Color getColor(){return color;}

    public void setColor(Color c) {this.color = c;}


    public boolean sameColor(Bloc a, Bloc b)                    // check if two blocs has the same color
    {
        return ((a.isClicable()) && (b.isClicable()) && (a.getColor() == b.getColor()) ? true : false);
    }

    @Override
    public boolean isClicable ()
    {
        return (color.toString() != "NONE" ? true : false);        // blocs without color = decoration = non clicable
    }

    public static void main(String[] args)
    {
        Bloc test = new Bloc();
        System.out.println(test.getColor());
        Bloc test1 = new Bloc();
        System.out.println(test1.getColor());
    }
}
