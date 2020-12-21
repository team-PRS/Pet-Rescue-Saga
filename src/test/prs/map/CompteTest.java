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
    assertTrue(c.isLevelUnlock(0));
    assertTrue(!c.isLevelUnlock(1));
    assertTrue(!c.isLevelUnlock(2));
    assertTrue(!c.isLevelUnlock(3));
  }
  @Test
  public void testisLevelUnlock(){
    Compte c = new Compte();
    assertTrue(!c.isLevelUnlock(1));
    c.unlockLevel(1);
    assertTrue(c.isLevelUnlock(1));
    //if i is wrong :
    assertTrue(!c.isLevelUnlock(100));
    assertTrue(!c.isLevelUnlock(-1));
    assertTrue(!c.isLevelUnlock(-3));
  }
  @Test
  public void testUnlockLevel(){
    Compte c = new Compte();
    assertTrue(!c.isLevelUnlock(1));
    assertTrue(c.unlockLevel(1));
    assertTrue(c.isLevelUnlock(1));
    //if i is wrong :
    assertTrue(!c.unlockLevel(100));
    assertTrue(!c.unlockLevel(-1));
    assertTrue(!c.unlockLevel(-3));
  }
}
