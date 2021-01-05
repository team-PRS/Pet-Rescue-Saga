package prs;

import java.util.LinkedList;
import java.util.Random;

public class Plateau
{
    private int height;
    private int width;
    private ObjectSurCase[][] plateau;
    private boolean isOnFloor = false;
    private boolean isRescued = false;
    private boolean isDecoStop = false;
    private int nbrOfMaxColor = 5; //it can be 2,3,4 or 5.

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

    /**
     * Return game object by coordinates
     */
    public ObjectSurCase getObject(int x, int y)
    {
        if (isOnPlateau(x, y))
        {
            return (this.plateau[x][y]);
        }
        return null;
    }

    /**
     * Set game object on position (x,y)
     */
    public void setObject(ObjectSurCase a, int x, int y)
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

    /**
     * Return color of colored Blocs (non decoration)
     */
    public String getColorOfBloc(int x, int y)
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

    /**
     * Return true/false if animal was rescued
     */
    public boolean getIsRescued() {return isRescued;}

    /*================================ Cleaning ==============================*/

    /**
     * Delete ObjectSurCase by coordinates (x,y)
     */
    public void cleanCase(int x, int y)
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

    /**
     * Check if Joueur click on Plateau
     */
    public boolean isOnPlateau(int x, int y)
    {
        boolean onPlateau = false;
        if ((x >= 0) && (x < height) && (y >= 0) && (y < width))
            onPlateau = true;
        return onPlateau;
    }

    /**
     * Check if cell is empty
     */
    public boolean isEmpty(int x, int y)
    {
        return (getObject(x, y)==null);
        //return (getObject(x, y).equals(null) ? true : false);
    }

    /*================================= Filling by elements ==============================*/

    /**
     * Fill the game board by elements {decorations, blocs, animals, ballons}
     * firstly deco, after blocs, animals etc
     */
    public void remplirPlateau(int nmbImmoBlocs, int nmbBlocs, int nmbAnimals,int nmbBombs)
    {
        int a=nmbImmoBlocs, b=nmbBlocs, c=nmbAnimals, d=nmbBombs;
        int ia = 0, ib=0, ic=0, id=0;
        // Set decoration (RANDOM mode)
        if (nmbImmoBlocs != 0)
        {
            for (int i = nmbImmoBlocs; i > 0; i--)
            {
                while (nmbImmoBlocs - ia != 0)
                {
                    int x = new Random().nextInt(height - 1);
                    int y = new Random().nextInt(width - 1);
                    if (y != 0)
                    {
                        if (plateau[x][y] == null)
                        {
                            Bloc immo = new Bloc("NONE");
                            setObject(immo, x, y);
                            ia++;
                        }
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
                                else if (nmbBombs > 0)                     //it helps to fill without gaps
                                {
                                    plateau[i][j] = new Bomb();
                                    nmbBombs--;
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
                                // add bomb
                                if (nmbBombs > 0)
                                {
                                    plateau[i][j] = new Bomb();
                                    nmbBombs--;
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
                            if (nmbBombs > 0)                                    // add ballons (10% probability)
                            {
                                plateau[i][j] = new Bomb();
                                nmbBombs--;
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
      //  System.out.println("need:added \n" + "deco: " + a + ":" + ia + "  blocs: " + b + ":" + ib +   //test does all element added
      //          "  animaux: " + c + ":" + ic + "  bombs: " + d + ":" + id);
      //  System.out.println("");
    }

    /*================================= Find group of blocs ==============================*/

    /**
    * Find all Blocs of the same color, i.e. check if there is a group for the Bloc. Make loop with next function
     * @return LinkedList<Point> of blocs
     * @param x,y coordinates of bloc which group need to find
    */
    public LinkedList<Case> getGroup(int x, int y)
    {
        int[][] groupe = new int[height][width];
        LinkedList<Case> dataToExit = new LinkedList<Case>();
        getGroup(x, y, groupe);                             // use the RECURSIVE function
        for (int i = 0; i < height; i++)                    // print group founded to console AND create the LIST of cells the blocs of the group
        {
            for (int j = 0; j < width; j++)
            {
                if (groupe[i][j] == 2)
                {
                    //System.out.print(" 2 ");                 // code debug
                    dataToExit.addLast(new Case(i, j));
                }
                else if (groupe[i][j] == 1)
                {
                    //System.out.print(" 1 ");                // code debug
                }
                else if (groupe[i][j] == 0)
                {
                    //System.out.print(" 0 ");                 // code debug
                }
            }
            //System.out.println("");                          // code debug
        }
        return dataToExit;
    }

    /**
     * Find all Blocs of the same color. Make a loop with previous function.
     * @param x,y - coordinates of click, groupe - list of already founded blocs (or not)      *
     */
    public void getGroup(int x, int y, int[][] groupe)
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

    /**
     * Print game board. This function should not be visible outside. Just for debug!
     */
    private void printMap()
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

    /*========================================== Object's Mouvements =================================================*/

    /**
     * Makes effect of gravity
     * @param x,y - coordinates of element to apply gravity
     */
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

    /**
     * Makes shift to left for column of elements, if there are empty place enough to contain this column
     */
    public void shiftLeft()
    {
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
                    if (obj.isClicable())                                //move only clicable objects
                    {
                        int lenColumnToMove = findLengthOfColumn(coordX, coordY);
                        if (lenColumnToMove > 0)
                        {
                            int lenFreeSpace = lengthFreeSpace(i, j);
                            int[] stop = nextLeftNotEmptyColumn(coordX, coordY);
                            int coordYStop = stop[1] ;

                            if ((lenFreeSpace >= lenColumnToMove) && (coordYStop <= coordY))               //TODO DOESN'T WORK
                            {
                                if (isDecoStop)
                                {
                                    int lenFreeSpaceUnderDeco = lengthFreeSpace(stop[0], stop[1]);
                                    if (lenFreeSpaceUnderDeco >= lenColumnToMove )
                                    {
                                        moveColumn(j, coordY);
                                    }
                                    else
                                    {
                                        moveColumn(coordYStop + 1, coordY);
                                    }
                                }
                                else
                                {
                                  moveColumn(j, coordY);
                                }
                                printMap();

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

    public void shiftAnimal()
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (plateau[i][j] != null)
                {
                    ObjectSurCase obj = getObject(i, j);
                    if (!obj.isClicable())
                    {
                        if (((i - 1) >=0 ) && ((i - 1) < height) && (plateau[i - 1][j] != null))
                        {
                            ObjectSurCase obj1 = getObject(i - 1, j);
                            if (obj1 instanceof Animal)
                            {
                                //make shiftLeft and shiftDown
                                int[] newCoords = shiftOneStepLeft(i, j);
                                //can't move if not empty cell and in cell near (just left cell)
                                if ((plateau[newCoords[0]] [newCoords[1]] == null) && (newCoords[0] != i + 1 ) && (newCoords[1] != j + 1))
                                {
                                    this.setObject(obj1, newCoords[0], newCoords[1]);
                                    this.cleanCase(i - 1, j);
                                    for (int k = i - 1; k > 0; k-- )
                                    {

                                        shiftDown(k, j);
                                    }
                                }
                               // shiftDown(newCoords[0], newCoords[1]);
                            }
                        }
                    }
                }
            }
        }
    }


    //--------------- service functions for shiftLeft ----------------------------------------------------------------//

    /**
     * Finds the length of free space INCLUDING (x, y)
     */
    public int lengthFreeSpace(int x, int y)
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

    /**
     * Finds nearest right NOT empty column from the floor = find column to move     *
     * @return null if find deco or if doesn't find not empty column
     * @return coordinates of column to move
     */
    public int[] nextRightNotEmptyColumn_Floor(int x, int y)
    {
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

    /**
     * Finds nearest left NOT empty column (= STOP for ShiftLeft)
     * @return coordinates of column to stop for ShiftLeft or null
     */
    public int[] nextLeftNotEmptyColumn(int x, int y)
    {
        int i = x;
        int j = y - 1;
        boolean isFound = false;
        this.isDecoStop = false;
        int counter = 0;
        while ((!isFound) && (j >= 0))
        {
            for (j = y - 1; j >= 0; j--)
            {
                for (i = x; i >= 0; i--)
                if ((plateau[i][j] != null)  && (j >= 0) )
                {
                    ObjectSurCase obj = getObject(i, j);
                    if (!obj.isClicable())
                    {
                        isDecoStop = true;
                    }
                    counter++;
                    return new int[]{height - 1, j};
                }
            }
        }
        if (counter == 0)
        {
            return new int[]{height - 1, j + 1};
        }
        return new int[]{height - 1, j};
    }

    /**
     * Moves columns from (floor,y2) to (floor,y1)
     */
    public void moveColumn(int y1, int y2)
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

    /**
     * Finds the length of column INCLUDING (x, y)
     */
    public int findLengthOfColumn(int x, int y)
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

    //--------------- service functions for shiftAnimal---------------------------------------------------------------//

    private int[] shiftOneStepLeft(int x, int y)
    {
        int i = x;
        int j = y - 1;
        for ( i = x; i < height; i++)
        {
            if (plateau[i][j] != null)
            {
                return new int[]{i - 1, j};        //coordinates of empty cell where need to move animal
            }
            else
            {
                //keep moving down and looking for first not empty cell
            }
        }
        return new int[]{i - 1, j};
    }


    /*================================= Object's behaviour ==============================*/

    /**
     * Provides disappearance of 8 blocs around the bomb
     * @param x,y - bomb's coordinates
     * @return number of blocs disappeared
     */
    public int bombExplosion(int x, int y)
    {
        int points = 0;
        if (plateau[x][y] != null)
        {
            ObjectSurCase obj = getObject(x, y);

            if (obj instanceof Bomb)
            {
                for (int i = x - 1; i <= x + 1; i++)
                {
                    for (int j = y - 1; j <= y + 1; j++)
                    {
                        if ((i >= 0) && (i <= height - 1) && (j >= 0) && (j <= width - 1))
                        {
                            if (plateau[i][j] != null)
                            {
                                ObjectSurCase obj1 = getObject(i, j);
                                if ((obj1 instanceof Bloc) && (obj1.isClicable()))
                                {
                                    cleanCase(i, j);
                                    shiftDown(i, j);
                                    points++;
                                }
                                else if (obj1 instanceof Bomb)
                                {
                                    cleanCase(i, j);
                                    shiftDown(i, j);
                                } else
                                {
                                    //skip
                                }
                            } else
                            {
                                //skip
                            }
                        }
                        else
                        {
                            //skip
                        }
                    }
                }
            } else
            {
                System.out.println("It is not a bomb");
            }
        }
        return points;
    }

    /**
     * Provides disappearance of all blocs of the color on game board
     * @param ballonColor
     * @return number of blocs disappeared
     */
    public int ballonExplosion(String ballonColor)
    {
        int points = 0;
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
                            points ++;
                            //return on the same place to check if shifted element will not a bloc of the same color
                            j++;
                        }
                    }
                    else if (obj instanceof Ballon && ((Ballon)obj).getColor().equals(ballonColor))                        //if ballon
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
        return points;
    }

    /**
     * Provides the list of coordinates of all animals on the floor
     * @return LinkedList list of coordinates
     */
    public LinkedList<Case> getAnimalsOnFloor()
    {
        this.isOnFloor = false;
        LinkedList<Case> animalsToRescue = new LinkedList<Case>();
        for (int j = 0; j < width; j++)
        {
            int i = height - 1;
            if (plateau[i][j] instanceof Animal)
            {
                isOnFloor = true;
                Case animalCoord = new Case(i, j);
                animalsToRescue.add(animalCoord);
            }
        }
        return animalsToRescue;
    }

    /**
     * Makes animals disappear
     * @param animalsToRescue - list of animal's coordinates
     */
    public void rescueAnimals(LinkedList<Case> animalsToRescue)
    {
        this.isRescued = false;
        if (animalsToRescue != null)
        {
            for (Case animalCoord : animalsToRescue)
            {
                int x = animalCoord.getCoordX();
                int y = animalCoord.getCoordY();
                cleanCase(x, y);
                shiftDown(x, y);
                isRescued = true;
                isOnFloor = false;
                //System.out.println("            1 animal rescued");                    //debug code
            }
        }
    }

    /*==================================== GameSet Screening =========================================================*/

    /**
     * Checks the status of the game
     * @return {continue, win, lost}
     */
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

    /*============================= Add Elements During the Set ==========================*/

   // public void addBlocsInGame(int nmbBlocs)
   // {
   // }
   //
   //
   //
   // /*================================= MAIN ==============================*/
   //
  //  public static void main(String[] args)
  //
  //  {
  //
  //      Plateau test1 = new Plateau(5, 5);
  //
  //   //   test1.remplirPlateau(3, 14, 1, 0);
  //
  //      Bloc b1 = new Bloc("BLUE");                                         //test shiftLeft();
  //      Bloc b2 = new Bloc("NONE");
  //      Bloc b3 = new Bloc("BLUE");
  //      Bloc b4 = new Bloc("BLUE");
  //      Animal a = new Animal("CAT");
  //
  //      test1.setObject(b1, 4, 1);
  //      test1.setObject(b2, 3, 2);
  //      test1.setObject(a, 2, 2);
  //      test1.setObject(b3, 4, 4);
  //      test1.setObject(b4, 3, 4);
  //
  //      test1.printMap();
  //
  //      test1.shiftLeft();
  //
  //      test1.printMap();
  //
  //      test1.shiftAnimal();
  //
  //      System.out.println("");
  //      test1.printMap();
  //
  //                                                                              //test shiftAnimal();
  //
  //
  //
  //
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
  //  }
}
