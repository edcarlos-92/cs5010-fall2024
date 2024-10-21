import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import model.World;
import model.WorldSetup;
import org.junit.Before;
import org.junit.Test;


/**
 * This Test class has unit tests to test the methods of the Target class.
 */
public class TestTarget {

  private World world;
  
  private Readable convertFileToString(String filePath) {   
    Path  path = Paths.get(filePath);    
    String fileContent = "";
    try {
      fileContent = Files.readString(path, StandardCharsets.US_ASCII);
    } catch (IOException e) {
      System.out.println("File Path is not correct. "
          + "Please enter correct path and try again.");
    }

    Readable fileInput = new StringReader(fileContent);
    return fileInput;
  } 
  
  /**
   * Sets the initial requirements before running the tests.
   */
  @Before
  public void setUp() {  
    String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
        + "m1-the-world/res/dr_lucky_in_marios_castle.txt";
    Readable fileContent = convertFileToString(filePath); 
    world = new WorldSetup(fileContent, 8);
  }
  
  @Test
  public void testWorldbyGettingTargetObject() {
    assertEquals("Target =  Doctor Lucky, Health = 50, Current Location = 1", 
        world.getTarget());
  }
  
  @Test
  public void testTargetMoveAndTargetLocation() {
    //Target is moved after every turn. So create a player and make them look around.
    world.createPlayer(1, "carlos", 2, 5);
    world.lookAround(1);
    assertEquals("Target =  Doctor Lucky, Health = 50, Current Location = 2", world.getTarget());
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    assertEquals("Target =  Doctor Lucky, Health = 50, Current Location = 10", world.getTarget());
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    world.lookAround(1);
    assertEquals("Target =  Doctor Lucky, Health = 50, Current Location = 21", world.getTarget());
    world.lookAround(1);
    assertEquals("Target =  Doctor Lucky, Health = 50, Current Location = 1", world.getTarget());
  }
  
}
