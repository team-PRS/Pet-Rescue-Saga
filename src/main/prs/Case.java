package prs;

public class Case
{
  private static int xCoord;
  private static int yCoord;

  public Case(int x, int y)
  {
    this.xCoord = x;
    this.yCoord = y;
  }

  public int getX()
  {
    return xCoord;
  }

  public int getY()
  {
    return yCoord;
  }
}
