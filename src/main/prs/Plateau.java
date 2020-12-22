package prs;

import java.util.LinkedList;
import java.util.Random;

public class Plateau
{
    private int height;
    private int width;
    private ObjectSurCase[][] plateau;
    private static boolean inOnFlor = false;

    /*================================= Constructor ==============================*/
    public Plateau(int h, int w)
    {
        this.height = h;
        this.width = w;

        this.plateau = new ObjectSurCase[height][width];
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
                plateau[i][j] = null;
        }
    }

    /*============================== Getters & Setters ============================*/

    public int getHeight(){return height;}

    public int getWidth(){return width;}

    public ObjectSurCase getObject(int x, int y)                       //return ObjectSurCase by coordinates
    {
        return (this.plateau[x][y]);
    }

    public void setObject(ObjectSurCase a, int x, int y)               //set the ObjectSurCase on position (x,y)
    {
        this.plateau[x][y] = a;
    }

    /*================================ Cleaning ==============================*/
    public void cleanCase(int x, int y)                                //delete ObjectSurCase ons position (x,y)
    {
        ObjectSurCase obj = getObject(x, y);
        if (obj.isClicable())
        {
            this.plateau[x][y] = null;
            System.out.println("            1 case cleaned");
        }
        else System.out.println("Can't delete decoration");
    }

    /*================================= Checkups ==============================*/

    public boolean isOnPlateau(int x, int y)                       //check if Joueur click on Plateau
    {
        boolean onPlateau = false;
        if ((x >= 0) && (x < height) && (y >= 0) && (y < width))
            onPlateau = true;
        return onPlateau;
    }

    public boolean isEmpty(int x, int y)                             //check if cell is empty
    {
        return (getObject(x, y) == null ? true : false);
    }


    /*================================= Filling by elements ==============================*/

    public void remplirPlateau(int nmbImmoBlocs, int nmbBlocs, int nmbAnimals,int nmbBallons)
    {
        int a=nmbImmoBlocs, b=nmbBlocs, c=nmbAnimals, d=nmbBallons;
        int ia = 0, ib=0, ic=0, id=0;
        // Set decoration (RANDOM mode)
        if (nmbImmoBlocs != 0)
        {                                                        //TODO may be need to implement on stable positions
            for (int i = nmbImmoBlocs; i > 0; i--)
            {
                while (nmbImmoBlocs - ia != 0)
                {
                    Bloc immo = new Bloc("NONE");
                    int x = new Random().nextInt(height);
                    int y = new Random().nextInt(width);
                    if (plateau[x][y] == null)
                    {
                        setObject(immo, x, y);
                        ia++;
                    }
                }
            }
        }

        /* Set movables objects (RANDOM mode)
         */
        for (int i = height - 1; i >= 0; i--)
        {
            for (int j = width - 1; j >= 0; j--)
            {
                if (plateau[i][j] == null)
                {
                    //top part of plateau - any element can be added
                    if ((i != height - 1) && (i != height - 2) && (i != height - 3))
                    {
                        int rd = new Random().nextInt(100);
                        {
                            if (rd < 80)                                    // add blocs (80% probability)
                            {
                                if (nmbBlocs > 0)
                                {
                                    plateau[i][j] = new Bloc();
                                    nmbBlocs--;
                                    ib ++;
                                }
                                else if (nmbBallons > 0)                     //it helps to fill without gaps
                                {
                                    plateau[i][j] = new Ballon();
                                    nmbBallons--;
                                    id ++;
                                }
                                else if (nmbAnimals > 0)
                                {
                                    plateau[i][j] = new Animal();
                                    nmbAnimals--;
                                    ic ++;
                                }
                            }

                            else if ((rd >= 80) && (rd < 90))                 // add ballons (10% probability)

                            {
                                // add ballon
                                if (nmbBallons > 0)
                                {
                                    plateau[i][j] = new Ballon();
                                    nmbBallons--;
                                    id ++;
                                }
                                else                                          //it helps to fill without gaps
                                {
                                    if (nmbBlocs > 0)
                                    {
                                        plateau[i][j] = new Bloc();
                                        nmbBlocs--;
                                        ib ++;
                                    }
                                }
                            } else                                            // add animals (10% probability)
                            {
                                // add animal
                                if (nmbAnimals > 0)
                                {
                                    plateau[i][j] = new Animal();
                                    nmbAnimals--;
                                    ic ++;
                                }
                                else                                             //it helps to fill without gaps
                                {
                                    if (nmbBlocs > 0)
                                    {
                                        plateau[i][j] = new Bloc();
                                        nmbBlocs--;
                                        ib ++;
                                    }
                                }
                            }
                        }
                    }

                    else          //can't add animals in bottom of plateau
                    {
                        int rd = new Random().nextInt(100);
                        if (rd <= 80)                                              // add blocs (80% probability)
                        {
                            if (nmbBlocs > 0)
                            {
                                plateau[i][j] = new Bloc();
                                nmbBlocs--;
                                ib ++;
                            }
                        } else
                        {
                            if (nmbBallons > 0)                                    // add ballons (10% probability)
                            {
                                plateau[i][j] = new Ballon();
                                nmbBallons--;
                                id ++;
                            }
                            else
                            if (nmbBlocs > 0)                                      //it helps to fill without gaps
                            {
                                plateau[i][j] = new Bloc();
                                nmbBlocs--;
                                ib ++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(" need:added \n" + "deco: " + a + ":" + ia + "  blocs: " + b + ":" + ib +   //test does all element added
                "  animaux: " + c + ":" + ic + "  ballons: " + d + ":" + id);
        System.out.println("");
    }


    /*================================= Find group of blocs ==============================*/

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
        if ((a instanceof Bloc) && (a.isClicable()))
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
                        if (isOnPlateau(i, j))
                        {
                            ObjectSurCase b = getObject(i, j);
                            if ((b instanceof Bloc) && (b.isClicable()))
                            {
                                if (((Bloc) a).getColor() == ((Bloc) b).getColor())  // 0 non-examined, 1 - examined, 2 - part of groupe
                                {
                                    // if b is a part of group already
                                    if ((groupe[i][j] == 2))
                                    {
                                        //check if they touch by sides, NOT by corners
                                        if (((Math.abs(i - x) == 1) && (Math.abs(j - y) == 0) || ((Math.abs(j - y) == 1) && (Math.abs(i - x) == 0))))
                                        {
                                            groupe[i][j] = 2;     // TODO verify this place while it will be random may be need to change
                                        }
                                    } else
                                    // if b is not a part of group
                                    {
                                        //check if they touch by sides, NOT by corners
                                        if (((Math.abs(i - x) == 1) && (Math.abs(j - y) == 0) || ((Math.abs(j - y) == 1) && (Math.abs(i - x) == 0))))
                                        {
                                            groupe[i][j] = 2;
                                            getGroup(i, j, groupe);             //RECURSION
                                        }
                                    }
                                } else
                                {
                                    //mark otherwise colored Bloc as examined
                                    groupe[i][j] = 1;
                                }
                            } else
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

    /*================================= Print to Console ==============================*/

    public void printMap()		                                    // print Plateau
    {
        System.out.println("h:" + height + " l:" + width);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (plateau[i][j] instanceof Bloc)
                {
                    if (((Bloc) plateau[i][j]).getColor() == "NONE")
                        System.out.print(" nn");
                    else if (((Bloc) plateau[i][j]).getColor() == "BLUE")
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

    /*================================= Object's Mouvements ==============================*/

    //function for instance of ObjectSurCase: delete from one place, insert to another one
    public void moveObject(int depX, int depY, int arrX, int arrY)
    {
        if (isOnPlateau(depX, depY) && isOnPlateau(arrX, arrY))
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

    public void shiftDown(int x, int y)
    {
        if ((plateau[x][y] == null) && (plateau[x - 1][y] != null))
        {
            for (int i = x; i > 0; i--)
            {
                ObjectSurCase upper = getObject(i - 1, y);
                if (upper.isClicable())
                {
                    plateau[i][y] = plateau[i - 1][y];
                    cleanCase(i - 1, y);
                    System.out.println("            1 shift down made");
                }
                else
                    {
                        //if deco
                        break;

                    }
                printMap();
                if ((i >=2) && (plateau[i - 2][y] == null)) break;                 //protection against NullPointerException
            }
        }
    }

    public void shiftLeft()
    {
        //TODO left shift of element if it is on top unmovable bloc
    }


    /*================================= Object's behaviour ==============================*/

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

    public LinkedList<Point> animalsOnFloor()
    {
        this.inOnFlor = false;
        LinkedList<Point> animalsToRescue = new LinkedList<Point>();
        for (int j = 0; j < width; j++)
        {
            int i = height - 1;
            if (plateau[i][j] instanceof Animal)
            {
                inOnFlor = true;
                Point animalCoord = new Point(i, j);
                animalsToRescue.add(animalCoord);
            }
        }
        return animalsToRescue;
    }

    public void animalRescue(LinkedList<Point> animalsToRescue)
    {
        for (Point animalCoord : animalsToRescue)
        {
            int x = animalCoord.getCoordX();
            int y = animalCoord.getCoordY();
            cleanCase(x, y);
            shiftDown(x, y);
            System.out.println("            1 animal rescued");
        }
    }

    /*================================= Set Screening ==============================*/

    public boolean levelLost()
    {
        //(animals != 0) && (ballons == 0) && (can't find any groupe anymore)
        int an = 0;
        int bal = 0;
        LinkedList[] lists = null;
        boolean isGroupsPresent = false;
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (plateau[i][j] instanceof Animal)
                {
                    an++;
                }
                if (plateau[i][j] instanceof Ballon)
                {
                    bal++;
                }
                if (plateau[i][j] instanceof Ballon)
                {
                    LinkedList l = getGroup(i, j);
                    if (l.size() != 1)
                        {
                            isGroupsPresent = true;
                        }
                }
            }
        }
        return ((an != 0) && (bal == 0) && (isGroupsPresent == false)) ? true : false;

    }

    public boolean levelWon()
    {
        //(animals == 0)
        int an = 0;
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (plateau[i][j] instanceof Animal)
                {
                    an++;
                }
            }
        }
        return (an == 0) ? true : false;
    }


    /*============================= Add Elements During the Set ==========================*/

    public void addBlocsInGame(int nmbBlocs)            //TODO need the logic how and were to add the blocs
    {

    }


    /*================================= MAIN ==============================*/

    public static void main(String[] args)
    {
        Plateau test1 = new Plateau(7, 7);
        test1.remplirPlateau(2, 34, 4, 2);

        test1.printMap();
        System.out.println("");

        for (int i = 0; i < 6; i++)
        {
            System.out.println(inOnFlor);
            test1.animalsOnFloor();
            if (inOnFlor == true)
            {
                System.out.println("");
                System.out.println("Need to rescue ");
                test1.animalRescue(test1.animalsOnFloor());
                // System.out.println("");
                test1.printMap();
            }
            System.out.println("Start cycle " + i);
            test1.cleanCase(6, 6);
            test1.printMap();
            test1.shiftDown(6, 6);
            System.out.println("");
            test1.animalsOnFloor();
            if (inOnFlor == true)
            {
                System.out.println("");
                System.out.println("Need to rescue ");
                test1.animalRescue(test1.animalsOnFloor());
                // System.out.println("");
                test1.printMap();
            }
           
        }

        //  test1.getGroup(5, 6);
        //
        //
        //  System.out.println("");
        //  test1.getGroup(3, 4);
       // System.out.println(test1.levelLost());
       // System.out.println(test1.levelWon());
    }

}
