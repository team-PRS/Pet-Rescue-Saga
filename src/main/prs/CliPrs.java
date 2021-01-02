package prs;

import prs.map.Compte;
import prs.map.Joueur;
import java.util.Scanner;
import java.util.ArrayList;


public class CliPrs
{
    private Scanner scanAnswer;
    private Jeu motor;


    /*============================== Constructor & close function ====================================================*/

    public CliPrs()
    {
        this.scanAnswer = new Scanner(System.in);
        motor = new Jeu(false);
    }

    public void CliClose()
    {
        this.scanAnswer.close();
        this.motor.Close();
    }

    /*========================================= User Input ===========================================================*/


    /**
     * Ask that gamer want to do
     */
    public char askAction()
    {
        System.out.print("\nActions:\n" +
                "a - activate your ballon\n" +
                "b - buy the ballon /cost " + String.valueOf(Compte.ballonPrix) + " golds/\n" +
                "c - click on the cell\n" +
                "e - activate bomb\n" +
                //"g - convert score to gold /1 ingot = " + String.valueOf(Compte.PointsPerGoldCoin) + " points/\n" +
                "q - quite game (q)\n" +
                "Select (a/b/c/e/g/q):\n");
        String Action = scanAnswer.next();
        return Action.charAt(0);
    }

    /**
     * Ask if gamer want to exit
     */
    public String askPlayOrExit()
    {
        System.out.print("Do you want exit or play ? (ex/pl)");
        String answer = scanAnswer.next();
        return answer.toLowerCase();
    }


    /*============================================= Output ===========================================================*/

    /**
     *  Bring the messages: level descriptions and if pet is rescued
     *  @param request - is made in levelInfo
     *  @return String - level description or message that pet was rescued
     */
    public String showMessage(String request)
    {
        return motor.showMessage(request);
    }

    /**
     *  Compose String level for function showMessage
     *  @param l - number of current level
     *  @return String - level#
     */
    public String levelInfo(int l)
    {
        return "level" + Integer.toString(l);
    }

    /**
    *  Print current parameters of gamer's account {points, gold, ballon}
    */
    public void afficheAccountInfo(Compte userAccount)
    {
        System.out.println("\nPoints: " + userAccount.getPoints() + " / Gold: " + userAccount.getGold() +
                " / Ballon: " + userAccount.getBallon());
        System.out.println("");
    }


    /*======================================== Personal fonctions ====================================================*/

    /**
     * Administrate answers of gamer for start of game (if true - account administration and launch, il false - save and close program)
     */
    public boolean wantPlay()
    {
        boolean answer = false;
        boolean isCorrectAnswer = false;
        while (!isCorrectAnswer)
        {
            System.out.print("Do you want to play (yes/no) ? ");
            String answerPlay = scanAnswer.next().toLowerCase();
            if ((answerPlay.equals("yes")) || (answerPlay.equals("y")))
            {
                isCorrectAnswer = true;
                answer = true;
            } else if ((answerPlay.equals("no")) || (answerPlay.equals("n")) || (answerPlay.equals("non")))
            {
                isCorrectAnswer = true;
                answer = false;
            } else System.out.println("Wrong input, try again");
        }
        return answer;
    }

    /**
     * Provides load of account from the list or creation of new account
     */
    public void accountAdministration()
    {
        final int CreateNewAccountId = 0;
        int Id = CreateNewAccountId;
        ArrayList<Joueur> gamers = motor.getListOfJoueurs();

        if (    (null != gamers)
             && (0 < gamers.size())
           )
        {
            boolean bCorrupted = false;
            System.out.println("To start game you need to select or create new account");

            System.out.println("0: Create new");

            int index = 1;
            try
            {
                for (Joueur gamer : gamers)
                {
                    System.out.println(String.valueOf(index) + ": load " + gamer.getPseudo());
                    index ++;
                }
            }
            catch (NullPointerException e)
            {
                System.out.println("ERROR: Account data is corrupted, new account has to be created");
                bCorrupted = true;
            }

            if (!bCorrupted)
            {
                System.out.print("Please enter action number:");
                while (true)
                {
                    String strId = scanAnswer.next();
                    try
                    {
                        Id = Integer.parseInt(strId);
                    } catch (NumberFormatException e)
                    {
                        Id = -1;
                    }
                    if ((Id >= 0) && (Id <= gamers.size()))
                    {
                        break;
                    } else
                    {
                        System.out.print("Number error, value should be in range: [0 .. " + String.valueOf(gamers.size()) + "]");
                        System.out.print("Please try again:");
                    }
                }
            }
        }

        if (Id == CreateNewAccountId)
        {
            boolean bCreateNew = true;
            String pseudo = askPseudo();
            if (null != gamers)
            {
                for (Joueur j : gamers)
                {
                    if (j.getPseudo().equals(pseudo))
                    {
                        motor.selectJoueur(j);

                        bCreateNew = false;
                    }
                }
            }

            if (bCreateNew)
            {
                motor.createNewJoueur(pseudo);
            }
        }
        else
        {
            motor.selectJoueur(gamers.get(Id - 1));
        }

    }

    private String askPseudo()
    {
        System.out.print("Please enter your pseudo : ");
        String nameJoueur = scanAnswer.next();
        return nameJoueur;
    }

    public int[] askCoordinates()
    {
        boolean IsValid = false;
        int xCoord = 0; int yCoord = 0;

        String CoordStr;
        System.out.print("\nIn which cell do you want to play now? (Exemple: b3) : ");

        while(!IsValid)
        {
            CoordStr = scanAnswer.next();
            if (CoordStr.length() >= 2)
            {
                int input = CoordStr.charAt(0) - 'a';                    // -'a' because all loops started by 0
                for (int i = 0; i < motor.getPlateauHeight(); i++)
                {
                    if (input == i)
                    {
                        xCoord = input;
                        IsValid = true;
                        break;
                    }
                }

                if (IsValid)
                {
                    yCoord = Integer.parseInt(CoordStr.substring(1)) - 1;  // -1 because all loops started by 0, but table markings by 1
                    if ((yCoord < 0) && (yCoord >= motor.getPlateauWidth()))
                    {
                        IsValid = false;
                    }
                }
            }
            if (!IsValid)
            {
                System.out.print("wrong input, try again (Exemple: b3) : ");
            }
        }
        return new int[]{xCoord, yCoord};
    }

    public void printPlateau()		                            // print Plateau
    {
        //print y-scale
        System.out.print("  | ");
        for (int k = 0; k < motor.getPlateauHeight(); k++)
        {
            System.out.print(" " + (k + 1) + " ");
        }
        System.out.println("");
        System.out.print("-----");
        for (int l = 0; l < motor.getPlateauWidth(); l++)
        {
            System.out.print("---");
        }
        System.out.println("");

        //print table
        for (int i = 0; i < motor.getPlateauHeight(); i++)
        {
            char ch = (char) ('a' + i);
            System.out.print(ch + " | ");

            for (int j = 0; j < motor.getPlateauWidth(); j++)
            {
                ObjectSurCase obj = motor.getCell(i, j);
                if (obj instanceof Bloc)
                {
                    if (((Bloc) obj).getColor() == "NONE")
                        System.out.print(" + ");
                    else if (((Bloc) obj).getColor() == "BLUE")
                        System.out.print(" α ");
                    else if (((Bloc) obj).getColor() == "YELLOW")
                        System.out.print(" β ");
                    else if (((Bloc) obj).getColor() == "RED")
                        System.out.print(" γ ");
                    else if (((Bloc) obj).getColor() == "GREEN")
                        System.out.print(" δ ");
                    else
                        System.out.print(" ε ");
                }
                else if (obj instanceof Bomb)
                    System.out.print(" * ");
                else if (obj instanceof Ballon)
                    System.out.print(" օ ");
                else if (obj instanceof Animal)
                    System.out.print(" @ ");
                else
                    System.out.print(" - ");
            }
            System.out.println("");
        }
    }

    /**
     * Administrate answers of gamer for end of level (if true - save and close program, it false - load next level)
     */
    public boolean isExit()
    {
        boolean IsValid = false;
        boolean IsExit = false;
        String answer = askPlayOrExit();
        while (!IsValid)
            if (answer.equals("ex") || answer.equals("e") || answer.equals("exit"))
            {
                IsValid = true;
                IsExit = true;
                System.out.println("See you !! \n");
            }
            else if (answer.equals("pl") || answer.equals("p") || answer.equals("play"))
            {
                IsValid = true;
            }
            else
            {
                System.out.println("Wrong input, try again\n");
            }

        return IsExit;
    }

    /*======================================= Key function ===========================================================*/

    /**
     * Units together all functions of Console User Interface
     */
    public void consoleGame()
    {
        if (wantPlay())
        {
            accountAdministration();
            String gameStatus = "continue";
            boolean endLevel = false;
            boolean forcequite = false;
            while ( (!endLevel) && (!forcequite))
            {
                System.out.println(showMessage(levelInfo(motor.getCurrentJoueur().getCompte().getLastUnlockLevel())));
                System.out.println("");
                motor.createPlateau();
                gameStatus = "continue";

                while (!gameStatus.equals("lost") && !gameStatus.equals("win") && (!forcequite))
                {
                    afficheAccountInfo(motor.getCurrentJoueur().getCompte());
                    printPlateau();

                    char action = askAction();
                    if (action == 'c')          //clic
                    {
                        int[] coord = askCoordinates();
                        motor.pressCell(coord[0], coord[1]);
                    } else if (action == 'b')    //buy ballon
                    {
                        motor.getCurrentJoueur().buyBallon();
                    } else if (action == 'a')    //activate ballon
                    {
                        if (motor.getCurrentJoueur().getCompte().getBallon() > 0)
                        {
                            int[] coord = askCoordinates();
                            motor.ballonExplosion(coord[0], coord[1]);
                        }
                    } else if (action == 'e')    //activate bombe
                    {
                        int[] coord = askCoordinates();
                        motor.bombExplosion(coord[0], coord[1]);
                    }/* else if (action == 'g')    //convert score to gold
                    {
                        motor.getCompte().saveScore(motor.getCurentLevel());
                    } */else if (action == 'q')    //convert score to gold
                    {
                        forcequite = true;
                    } else System.out.println("Wrong input, try again");

                    gameStatus = motor.getCurrentLevelStatus();
                } //while (!gameStatus.equals("lost") && !gameStatus.equals("win") && (!forcequite))

                if (!forcequite)
                {
                    if (gameStatus.equals("win"))
                    {
                        motor.endLevel();

                        System.out.println("\n===========================================");
                        System.out.println("======= Congratulations, you win !! =======");
                        System.out.println("\n===========================================");

                        afficheAccountInfo(motor.getCurrentJoueur().getCompte());
                        printPlateau();

                        endLevel = isExit();
                    } else if (gameStatus.equals("lost"))
                    {
                        System.out.println("The level is lost. Try again.. ");

                        System.out.println("\n==============================================");
                        System.out.println("======= The level is lost. Try again.. =======");
                        System.out.println("\n==============================================");

                        afficheAccountInfo(motor.getCurrentJoueur().getCompte());
                        printPlateau();

                        endLevel = isExit();
                    }
                }
            } //while (!endLevel)
        }
    }
}
