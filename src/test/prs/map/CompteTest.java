package prs.map;
//def par défaut des fichiers depuis 0.79.5
import org.junit.Test;
import junit.framework.TestCase;

/**
*{@summary use Junit test to test a file.}
*/
public class CompteTest extends TestCase{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testCompte(){
    Compte c = new Compte();
    assertEquals(100,c.getGold());
    assertTrue(c.isLevelUnlock(1));
    assertTrue(!c.isLevelUnlock(2));
    assertTrue(!c.isLevelUnlock(3));
    assertTrue(!c.isLevelUnlock(4));
  }
  @Test
  public void testIsLevelUnlock(){
    Compte c = new Compte();
    assertTrue(c.isLevelUnlock(1));
    assertTrue(!c.isLevelUnlock(2));
    c.unlockLevel(2);
    assertTrue(c.isLevelUnlock(2));
  }
  @Test
  public void testUnlockLevel(){
    Compte c = new Compte();
    assertEquals(1,c.getLastUnlockLevel());// 1/4 level are unlocked
    assertTrue(!c.isLevelUnlock(2));
    assertTrue(c.unlockLevel(2));
    assertTrue(c.isLevelUnlock(2));
    assertTrue(c.unlockLevel(4));
    assertTrue(c.isLevelUnlock(4));
    assertTrue(!c.isLevelUnlock(3));
    assertEquals(2,c.getLastUnlockLevel());// 3/4 level are unlocked but not the 3a, so we want to return 2.
    //if i is wrong :
    assertTrue(!c.isLevelUnlock(100));
    assertTrue(!c.isLevelUnlock(0));
    assertTrue(!c.isLevelUnlock(-1));
    assertTrue(!c.isLevelUnlock(-3));
  }
  @Test
  public void testScore(){
    Compte c = new Compte();
    System.out.println(c);
    assertEquals(0,c.getScore(1));
    assertTrue(c.setScore(1,101));
    assertEquals(101,c.getScore(1));
    //level lock
    assertTrue(!c.setScore(2,101));
    // <0 score
    assertTrue(!c.setScore(1,-101));
    assertTrue(!c.setScore(1,-1));
    assertTrue(c.setScore(1,0));

  }
}
