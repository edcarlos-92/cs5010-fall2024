import static org.junit.Assert.assertEquals;

import model.WorldSetup;
import org.junit.Test;
//import static org.junit.Assert.*;


/**
 * This class contains unit tests for the {@link WorldSetup} class.
 * It validates the functionality of the WorldSetup constructor and its behavior
 * when provided with different input scenarios.
 */
public class WorldSetupTest {

  @Test
  public void testWorldSetupValidInput() {
    String fileContent = "5 5\n100 TargetName\n3\n0 0 1 1 Room1\n2 2 4 4 Room2\n1 1 3 3"
        + " Room3\n2\n0 10 Item1\n1 20 Item2\n";
    WorldSetup worldSetup = new WorldSetup(fileContent);
  
    // Validate world dimensions
    assertEquals(5, worldSetup.getTotalRow());
    assertEquals(5, worldSetup.getTotalCol());
    
    // Validate target properties
    assertEquals("TargetName", worldSetup.getTarget());
    assertEquals(100, worldSetup.getHealth());
      
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWorldSetupInvalidHealth() {
    String fileContent = "5 5\n-100 TargetName\n3\n0 0 1 1 Room1\n2 2 4 4 Room2\n1 1 3 3"
        + " Room3\n2\n0 10 Item1\n1 20 Item2\n";
    new WorldSetup(fileContent); // This should throw an IllegalArgumentException
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWorldSetupInvalidRoomCount() {
    String fileContent = "5 5\n100 TargetName\n0\n"; // 0 rooms
    new WorldSetup(fileContent); // This should throw an IllegalArgumentException
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWorldSetupInvalidItemCount() {
    String fileContent = "5 5\n100 TargetName\n3\n0 0 1 1 Room1\n2 2 4 4 Room2\n1 1 3 3"
        + " Room3\n0\n"; // 0 items
    new WorldSetup(fileContent); // This should throw an IllegalArgumentException
  }

    
}
