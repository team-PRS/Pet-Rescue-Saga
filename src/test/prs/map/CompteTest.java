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
    assertEquals(1,c.getUnlockLevel());// 1/4 level are unlocked
    assertTrue(!c.isLevelUnlock(2));
    assertTrue(c.unlockLevel(2));
    assertTrue(c.isLevelUnlock(2));
    assertTrue(c.unlockLevel(4));
    assertTrue(c.isLevelUnlock(4));
    assertTrue(!c.isLevelUnlock(3));
    assertEquals(3,c.getUnlockLevel());// 3/4 level are unlocked
    //if i is wrong :
    assertTrue(!c.isLevelUnlock(100));
    assertTrue(!c.isLevelUnlock(0));
    assertTrue(!c.isLevelUnlock(-1));
    assertTrue(!c.isLevelUnlock(-3));
  }
}
