package prs;

import java.util.LinkedList;

public class Plateau
{
    private int height;
    private int width;
    private ObjectSurCase[][] plateau;

    public Plateau(int h, int w)
    {
        this.height = h;
        this.width = w;

        this.plateau = new ObjectSurCase[height][width];        //constructor of Plateau
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
                plateau[i][j] = null;
        }
    }

    public int getHeight(){return height;}

    public int getWidth(){return width;}

 //   public boolean isOnPlateau(ObjectSurCase a)                       //check if ObjectSurCase is on Plateau
 //   {
 //       boolean onPlateau = false;
 //       for (int i = 0; i < height; i++)
 //       {
 //           for (int j = 0; j < height; j++)
 //           {
 //               if (this.plateau[i][j] == a)
 //               {
 //                   onPlateau = true;
 //               }
 //           }
 //       }
 //       return onPlateau;
 //   }

    public boolean isOnPlateau(Point p)                       //check if Joueur click on Plateau
    {
        boolean onPlateau = false;
        if (((p.getCoordX() >= 0) && (p.getCoordX() <= height)) && ((p.getCoordY() >= 0) && (p.getCoordY() <= width)))
            onPlateau = true;
        return onPlateau;
    }

    public boolean isEmpty(int x, int y)                       //check if Joueur click on Plateau
    {
        boolean isEmpty = false;
        if (getObject(x, y) == null)
            isEmpty = true;
        return isEmpty;
    }

    public ObjectSurCase getObject(int x, int y)                       //return ObjectSurCase by coordinates
    {
        return (this.plateau[x][y]);
    }

    public void setObject(ObjectSurCase a, int x, int y)               //set the ObjectSurCase on position (x,y)
    {
        this.plateau[x][y] = a;
    }

    public void cleanCase(int x, int y)                                //delete ObjectSurCase ons position (x,y)
    {
        this.plateau[x][y] = null;
    }


    /* find all Blocs of the same color, i.e. check if there is a group for the Bloc ----- return LinkedList<Point>
     */

    public LinkedList<Point> getGroup(int x, int y)
    {
        int[][] groupe = new int[height][width];
        LinkedList<Point> dataToExit = new LinkedList<Point>();
        getGroup(x, y, groupe);                             // use the RECURSIVE function
        for (int i = 0; i < height; i++)                    // print group founded to console AND create the LIST of cells the blocs of the group
        {
            for (int j = 0; j < width; j++)
            {
                    if (groupe[i][j] == 2)
                    {
                        System.out.print(" 2 ");
                        dataToExit.addLast(new Point(i, j));
                    }
                    else if (groupe[i][j] == 1)
                            System.out.print(" 1 ");
                    else if (groupe[i][j] == 0)
                        System.out.print(" 0 ");
            }
            System.out.println("");
        }       
        return dataToExit;
    }

    public void getGroup(int x, int y, int[][] groupe)         // find all Blocs of the same color
    {
        ObjectSurCase a = this.getObject(x, y);
        // only for blocs:
        if (a instanceof Bloc)
        {
            for (int i = x - 1; i <= x + 1; i++)
            {
                for (int j = y - 1; j <= y + 1; j++)
                {
                    if ((i == x) && (j == y)) 
                    {
                        // skip - do not examine himself
                    }
                    else
                    {
                        // search only on Plateau..
                        if (((i >= 0) && (i <= height)) && ((j >= 0) && (j <= width)))
                        {
                            ObjectSurCase b = getObject(i, j);
                            if (b instanceof Bloc)
                            {
                                if (((Bloc) a).getColor() == ((Bloc) b).getColor())  // 0 non-examined, 1 - examined, 2 - part of groupe
                                {
                                    // if b is a part of group already
                                    if ((groupe[i][j] == 2))
                                    {
                                        //check if they touch by sides, NOT by corners
                                        if (((Math.abs(i - x) == 1) && (Math.abs(j - y) == 0) ||
                                                ((Math.abs(j - y) == 1) && (Math.abs(i - x) == 0))))
                                        {
                                            groupe[i][j] = 2;     // TODO verify this place while it will be random may be need to change
                                        }
                                    }
                                    else
                                        // if b is not a part of group
                                        {
                                            //check if they touch by sides, NOT by corners
                                            if (((Math.abs(i - x) == 1) && (Math.abs(j - y) == 0) ||
                                                    ((Math.abs(j - y) == 1) && (Math.abs(i - x) == 0)))) 
                                            {
                                                groupe[i][j] = 2;
                                                getGroup(i, j, groupe);             //RECURSION
                                            }
                                        }
                                }
                                else
                                    {
                                        //mark otherwise colored Bloc as examined
                                        groupe[i][j] = 1;
                                    }
                            }
                            else
                                {
                                    //mark non Bloc as examined
                                   groupe[i][j] = 1;
                                }
                        }
                        else
                            {
                                // out of plateau
                            }
                    }
                }
            }
        }
    }

    public void printMap()		                                    // print Plateau
    {
        System.out.println("h:" + height + " l:" + width);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (plateau[i][j] instanceof Bloc)
                {
                    if (((Bloc) plateau[i][j]).getColor() == "BLUE")
                        System.out.print(" bb");
                    else if (((Bloc) plateau[i][j]).getColor() == "YELLOW")
                            System.out.print(" by");
                    else if (((Bloc) plateau[i][j]).getColor() == "RED")
                        System.out.print(" br");
                    else if (((Bloc) plateau[i][j]).getColor() == "GREEN")
                        System.out.print(" bg");
                    else
                        System.out.print(" bp");
                }
                else if (plateau[i][j] instanceof Outil)
                        System.out.print(" u ");
                else if (plateau[i][j] instanceof Animal)
                    System.out.print(" a ");
                else
                    System.out.print(" - ");
            }
            System.out.println("");
        }
    }

    //function for instance of ObjectSurCase: delete from one place, insert to another one
    public void moveObject(int depX, int depY, int arrX, int arrY)
    {
        if ((((depX >= 0) && (depX <= height)) && ((depY >= 0) && (depY <= width))) &&
                (((arrX >= 0) && (arrX <= height)) && ((arrY >= 0) && (arrY <= width))))
        {
            ObjectSurCase obj = this.getObject(depX, depY);
            this.cleanCase(depX, depY);
            if (this.isEmpty(arrX, arrY))                              //TODO add intermediate cases between dep and arr?
            {
                this.setObject(obj, arrX, arrY);
            }
            else
                System.out.println("This place is occupied");
        }
        else
            System.out.println("Out of bounds of tableau");
    }

    public void bombExplosion(int x, int y)
    {
        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
                cleanCase(i, j);
        }
    }

    public void ballonExplosion(String ballonColor)
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (((Bloc) plateau[i][j]).getColor() == ballonColor)
                    cleanCase(i, j);
            }
        }
    }

    public void addBlocs(int nmbBlocs)
    {
        //TODO need the logic how and were to add the blocs
    }

    public void shiftLeft()
    {
        //TODO left shift of element if it is on unmovable bloc
    }


    public static void main(String[] args)
    {
        Plateau test1 = new Plateau(5, 5);

        Bloc b1 = new Bloc("BLUE");
        Bloc b2 = new Bloc("BLUE");
        Bloc b3 = new Bloc("BLUE");
        Bloc b4 = new Bloc("BLUE");
        Bloc b5 = new Bloc("YELLOW");
        Animal a1 = new Animal(1);
        Animal a2 = new Animal(2);

        test1.setObject(b1,2,2);
        test1.setObject(b2,2,3);
        test1.setObject(b3,1,3);
        test1.setObject(b4,4,1);
        test1.setObject(b5,3,2);
        test1.setObject(a1,3,3);
        test1.setObject(a2,1,2);

        test1.printMap();
        System.out.println("");
        test1.getGroup(1, 3);

        test1.moveObject(2,3, 0,2);
        test1.printMap();
        System.out.println("");
        test1.getGroup(1, 3);


        test1.moveObject(3,2, 0,6);
        test1.printMap();
        System.out.println("");

    }

}
