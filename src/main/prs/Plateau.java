package prs;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Plateau
{
    private int height;
    private int width;
    private ObjectSurCase[][] plateau;
    private boolean isOnFloor = false;
    private boolean isRescued = false;
    private int nbrOfMaxColor = 2; //it can be 2,3,4 or 5.

    /*================================= Constructor ==============================*/
    public Plateau(int h, int w)
    {
        this.height = h;
        this.width = w;

        this.plateau = new ObjectSurCase[height][width];
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                plateau[i][j] = null;
            }
        }
    }

    /*============================== Getters & Setters ============================*/

    public int getHeight(){return height;}

    public int getWidth(){return width;}

    public ObjectSurCase getObject(int x, int y)                       //return ObjectSurCase by coordinates
    {
        if (isOnPlateau(x, y))
        {
            return (this.plateau[x][y]);
        }
        return null;
    }

    public void setObject(ObjectSurCase a, int x, int y)               //set the ObjectSurCase on position (x,y)
    {
        if (isOnPlateau(x, y))
        {
            this.plateau[x][y] = a;
        }
        else
        {
            System.out.println("Can't set object in this cell: out of bounds");
        }
    }

    public String getColorOfBloc(int x, int y)                         //return color of colored Blocs (non decoration)
    {
        String colorBloc = "";
        if (plateau[x][y] != null)
        {
            ObjectSurCase obj = getObject(x, y);
            if ((obj instanceof Bloc) && (obj.isClicable()))
            {
                colorBloc = ((Bloc) obj).getColor();
            }
        }
        return colorBloc;
    }

    public boolean getIsRescued() {return isRescued;}

    /*================================ Cleaning ==============================*/
    public void cleanCase(int x, int y)                                //delete ObjectSurCase ons position (x,y)
    {
        ObjectSurCase obj = getObject(x, y);
        if (obj.isClicable())
        {
            this.plateau[x][y] = null;
            //System.out.println("            1 case cleaned");               //debug code
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
        return (getObject(x, y).equals(null) ? true : false);
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
                    int x = new Random().nextInt(height - 1);
                    int y = new Random().nextInt(width - 1);
                    if (plateau[x][y] == null)
                    {
                        Bloc immo = new Bloc("NONE");
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
                                    plateau[i][j] = new Bloc(nbrOfMaxColor);
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
                                        plateau[i][j] = new Bloc(nbrOfMaxColor);
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
                                        plateau[i][j] = new Bloc(nbrOfMaxColor);
                                        nmbBlocs--;
                                        ib ++;
                                    }
                                }
                            }
                        }
                    }

                    else          //can't add animals in the bottom of plateau
                    {
                        int rd = new Random().nextInt(100);
                        if (rd <= 80)                                              // add blocs (80% probability)
                        {
                            if (nmbBlocs > 0)
                            {
                                plateau[i][j] = new Bloc(nbrOfMaxColor);
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
                                plateau[i][j] = new Bloc(nbrOfMaxColor);
                                nmbBlocs--;
                                ib ++;
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("need:added \n" + "deco: " + a + ":" + ia + "  blocs: " + b + ":" + ib +   //test does all element added
        //        "  animaux: " + c + ":" + ic + "  ballons: " + d + ":" + id);
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
            //TODO this is service function, REMOVE after implementation GUI and CI
            for (int j = 0; j < width; j++)
            {
                if (groupe[i][j] == 2)
                {
                    //System.out.print(" 2 ");
                    dataToExit.addLast(new Point(i, j));
                }
                else if (groupe[i][j] == 1)
                {
                    //System.out.print(" 1 ");
                }
                else if (groupe[i][j] == 0)
                {
                    //System.out.print(" 0 ");
                }
            }
            //System.out.println("");
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

    //TODO: this function should not be visible outside. Just for debug!
    public void printMap()		                                    // print Plateau
    {
        System.out.println("h:" + height + " l:" + width);
        System.out.println("");
        //print y-scale
        System.out.print("  | ");
        for (int k = 0; k < width; k++)
        {
            System.out.print(" " + (k + 1) + " ");
        }
        System.out.println("");
        System.out.print("-----");
        for (int l = 0; l < width; l++)
        {
            System.out.print("---");
        }
        System.out.println("");
        //print table
        for (int i = 0; i < height; i++)
        {
            char ch = (char) ('a' + i);
            System.out.print(ch + " | ");

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
                    //System.out.println("            1 shift down made");     //debug code
                }
                else
                {
                    //if deco
                    break;
                }

                //printMap();                                                   //debug code
                if ((i >=2) && (plateau[i - 2][y] == null)) break;                 //protection against NullPointerException
            }
        }
    }

    public int lengthFreeSpace(int x, int y)                        //find the length of free space INCLUDING (x, y)
    {
        int counter = 0;
        int j = y;
        for (int i = x; i >= 0; i--)
        {
            if (plateau[i][j] == null)
            {
                counter++;
            }
            else
            {
                break;
            }
        }
        return counter;
    }

    public int[] nextRightNotEmptyColumn_Floor(int x, int y)          //find nearest right NOT empty column from the floor
    {                                                                  // if find deco return null
        int i = height - 1;
        int j = y + 1;
        boolean isFound = false;
        int counter = 0;
        while ((!isFound) && (j < width))
        {
            if (plateau[i][j] != null)
            {
                ObjectSurCase obj = getObject(i, j);
                //if deco = do not remove at all
                if (!obj.isClicable())
                {
                    return null;
                }
                else
                {
                    //if not deco = need to remove, so return coordinates
                    counter++;
                    isFound = true;
                }
            }
            else
            {
                j++;
            }
        }
        if (counter == 0)
        {
            return null;
        }
        return new int[]{i, j};
    }

    public void moveColumn(int y1, int y2)                        //move columns from (floor,y2) to (floor,y1)
    {
        if ((y1 < y2) && (plateau[height - 1][y2] != null))
        {
            //can move
            int len = findLengthOfColumn(height - 1, y2);
            int x = height - 1;
            for (int i = 0; i < len; i++)
            {
                ObjectSurCase obj = getObject(x, y2);
                if (obj.isClicable())
                {
                    setObject(obj, x, y1);
                    cleanCase(x, y2);
                    x--;
                }
                else
                {
                    //skip
                }
            }
        }
        else //can't move column
        {
            System.out.println("Can't move column");
        }
    }

    public int findLengthOfColumn(int x, int y)                    //find the length of column INCLUDING (x, y)
    {                                                                 //TODO make -1 if on (x,y) will be deco
        int len = 0;
        for (int i = x; i >= 0; i--)
        {
            if (plateau[i][y] != null)
            {
                ObjectSurCase obj = getObject(i, y);
                if (obj.isClicable())
                {
                    len++;
                } else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        return len;
    }

    public void shiftLeft()
    {
        //TODO shiftLeft need to be call 2 time if 2 collumns are empty.
        //we can try to do shiftLeft while there have been a move done.
        int i = height - 1;
        for (int j = 0; j < width - 1; j++)
        {
            // find null on the floor
            if (plateau[i][j] == null)
            {
                //find next right non null
                int[] next = nextRightNotEmptyColumn_Floor(i, j);
                if (next != null)
                {
                    int coordX = next[0];
                    int coordY = next[1];
                    ObjectSurCase obj = getObject(coordX, coordY);
                    if (obj.isClicable())
                    {
                        int lenColumnToMove = findLengthOfColumn(coordX, coordY);
                        if (lenColumnToMove > 0)
                        {
                            int lenFreeSpace = lengthFreeSpace(i, j);
                            if (lenFreeSpace >= lenColumnToMove)
                            {
                                moveColumn(j, coordY);
                            } else  //if it is deco
                            {
                                //skip do nothing
                            }
                        } else
                        {
                            break;
                        }
                    }
                }
            }
            else
            {
                //find next NOT null on the flor
                continue;           //skip to j++
            }
        }
    }

    /*================================= Object's behaviour ==============================*/

    public void bombExplosion(int x, int y)
    {
        ObjectSurCase obj = getObject(x, y);
        if (obj instanceof Bomb)
        {
            for (int i = x - 1; i <= x + 1; i++)
            {
                for (int j = y - 1; j <= y + 1; j++)
                {
                    if (plateau[i][j] != null)
                    {
                        cleanCase(i, j);
                        shiftDown(i, j);
                    } else
                    {
                        //skip
                    }
                }
            }
        }
        else { System.out.println("It is not a bomb"); }
    }

    public void ballonExplosion(String ballonColor)
    {
        for (int i = height - 1; i >= 0; i--)
        {
            for (int j = width - 1; j >= 0; j--)
            {
                if (plateau[i][j] != null)
                {
                    ObjectSurCase obj = getObject(i, j);
                    if ((obj instanceof Bloc) && obj.isClicable())            //if color bloc
                    {
                        if (((Bloc) obj).getColor().equals(ballonColor))
                        {
                            cleanCase(i, j);
                            shiftDown(i, j);
                            //return on the same place to check if shifted element will not a bloc of the same color
                            j++;
                        }
                    }
                    else if (obj instanceof Ballon)                        //if ballon
                    {
                        cleanCase(i, j);
                        shiftDown(i, j);
                        //return on the same place to check if shifted element will not a bloc of the same color
                        j++;
                    }
                    else                                     //if animal, deco, bomb
                    {
                        //do nothing just keep moving
                        continue;
                    }
                }
            }
        }
    }

    public LinkedList<Point> getAnimalsOnFloor()
    {
        this.isOnFloor = false;
        LinkedList<Point> animalsToRescue = new LinkedList<Point>();
        for (int j = 0; j < width; j++)
        {
            int i = height - 1;
            if (plateau[i][j] instanceof Animal)
            {
                isOnFloor = true;
                Point animalCoord = new Point(i, j);
                animalsToRescue.add(animalCoord);
            }
        }
        return animalsToRescue;
    }

    public void rescueAnimals(LinkedList<Point> animalsToRescue)
    {
        this.isRescued = false;
        if (animalsToRescue != null)
        {
            for (Point animalCoord : animalsToRescue)
            {
                int x = animalCoord.getCoordX();
                int y = animalCoord.getCoordY();
                cleanCase(x, y);
                shiftDown(x, y);
                isRescued = true;
                //System.out.println("            1 animal rescued");                    //debug code
            }
        }
    }

    /*================================= GameSet Screening ==============================*/

    public String gameState()
    {
        //(animals != 0) && (ballons == 0) && (can't find any group anymore)
        int an = 0;
        int bal = 0;
        LinkedList[] lists = null;
        boolean isGroupsPresent = false;
        String state = "";
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
                if (plateau[i][j] instanceof Bloc)
                {
                    LinkedList l = getGroup(i, j);
                    if (l.size() != 1)
                        {
                            isGroupsPresent = true;
                        }
                }
            }
        }

        if ((an != 0) && (bal == 0) && (isGroupsPresent == false)) { state = "lost"; }
        else if (an == 0) { state = "win"; }
        else { state = "continue"; }

        return state;
    }

    /*================================= Points ==================================*/

    public int calculatePoints(LinkedList<Point> blocs)             //TODO      1 bloc deleted = 10 points
    {
        int len = blocs.size();
        return len*10;
    }

    /*============================= Add Elements During the Set ==========================*/

    public void addBlocsInGame(int nmbBlocs)            //TODO need the logic how and were to add the blocs
    {
    }



    /*================================= MAIN ==============================*/

  //  public static void main(String[] args)
  //
  //  {
  //      Plateau test1 = new Plateau(5, 5);
  //
  //      test1.remplirPlateau(3, 34, 4, 1);

  //      Bloc b1 = new Bloc("BLUE");                                         //test shiftLeft();
  //      Bloc b2 = new Bloc("NONE");
  //      Bloc b3 = new Bloc("BLUE");
  //      Bloc b4 = new Bloc("BLUE");
  //
  //      test1.setObject(b1, 4, 1);
  //      test1.setObject(b2, 3, 1);
  //      test1.setObject(b3, 4, 3);
  //      test1.setObject(b4, 3, 3);
  //
  //      test1.printMap();
  //
  //      test1.shiftLeft();
  //
  //      test1.printMap();
  //      System.out.println("");
  //
  //      for (int i = test1.height - 1; i >= 0; i--)                             //test ballonExplosion()
  //      {
  //          for (int j = 6; j >= 0; j--)
  //          {
  //              if (test1.getObject(i, j) != null)
  //              {
  //                  ObjectSurCase obj = test1.getObject(i, j);
  //
  //                  if (obj instanceof Outil)
  //                  {
  //                      test1.ballonExplosion("BLUE");
  //                  }
  //              } else
  //              {
  //                  //System.out.println("has not element");          //for test
  //              }
  //          }
  //      }
  //
  //      //  for (int i = 6; i >= 0; i--)                                //test bombExplosion()
  //      //  {
  //      //      for (int j = 6; j >= 0; j--)
  //      //      {
  //      //          if (test1.getObject(i, j) != null)
  //      //          {
  //      //              ObjectSurCase obj = test1.getObject(i, j);
  //      //
  //      //              if (obj instanceof Outil)
  //      //              {
  //      //                  test1.bombExplosion(i, j);
  //      //              }
  //      //          }
  //      //          else
  //      //          {
  //      //              System.out.println("has not element");
  //      //          }
  //      //      }
  //      //  }
  //      test1.printMap();
  //
  //      //   for (int i = 0; i < 6; i++)
  //      //   {
  //      //       System.out.println(isOnFloor);
  //      //       test1.getAnimalsOnFloor();
  //      //       if (isOnFloor == true)                                             //test animal rescue()
  //      //       {
  //      //           System.out.println("");
  //      //           System.out.println("Need to rescue ");
  //      //           test1.rescueAnimals(test1.getAnimalsOnFloor());
  //      //           // System.out.println("");
  //      //           test1.printMap();
  //      //       }
  //      //       System.out.println("Start cycle " + i);
  //      //       test1.cleanCase(6, 6);
  //      //       test1.printMap();
  //      //       test1.shiftDown(6, 6);
  //      //       System.out.println("");
  //      //       test1.getAnimalsOnFloor();
  //      //       if (isOnFloor == true)
  //      //       {
  //      //           System.out.println("");
  //      //           System.out.println("Need to rescue ");
  //      //           test1.rescueAnimals(test1.getAnimalsOnFloor());
  //      //           // System.out.println("");
  //      //           test1.printMap();
  //      //       }
  //      //
  //      //   }
  //
  //      //  test1.getGroup(5, 6);                                       //test getGroup()
  //      //  System.out.println("");
  //      //  test1.getGroup(3, 4);
  //
   // }
}
