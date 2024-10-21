import static org.junit.Assert.assertEquals;

import java.io.File;
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
 * This is the test class created for testing all the methods of the
  classes in this project.
 */
public class TestWorld {
  
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
  
  //Check All exceptions
  @Test(expected = IllegalArgumentException.class)
  public void testWorldCoordinates() {
    String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
        + "m1-the-world/res/dr_lucky_in_marios_castle.txt";
    Readable fileContent = convertFileToString(filePath); 
    new WorldSetup(fileContent, 8);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testRoomCoord() {
    String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
        + "m1-the-world/res/dr_lucky_in_marios_castle.txt";
    Readable fileContent = convertFileToString(filePath); 
    new WorldSetup(fileContent, 8);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalRoomForItem() {
    String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
        + "m1-the-world/res/dr_lucky_in_marios_castle.txt";
    Readable fileContent = convertFileToString(filePath); 
    new WorldSetup(fileContent, 8);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testDamage() {
    String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
        + "m1-the-world/res/dr_lucky_in_marios_castle.txt";
    Readable fileContent = convertFileToString(filePath); 
    new WorldSetup(fileContent, 8);
  }
  
  @Test
  public void testPlayerCreated() {
    assertEquals("Player Created", world.createPlayer(1, "carlos", 2, 10));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPlayerCreated() {
    assertEquals("Room does not exist", world.createPlayer(1, "carlos", 2, 30));
  }
  
  @Test
  public void testMovePlayer() {
    world.createPlayer(1, "carlos", 2, 10);
    assertEquals("Move Completed", world.movePlayer(1, 8));
  }
  
  @Test
  public void testWrongMoveByPlayer() {
    world.createPlayer(1, "carlos", 2, 10);
    assertEquals("Room not a neighbor. Lose your turn", world.movePlayer(1, 5));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMovePlayer() {
    world.createPlayer(10, "carlos", 2, 10);
    world.movePlayer(1, 5);
  }
  
  @Test
  public void testPickItem() {
    world.createPlayer(1, "carlos", 2, 10);
    assertEquals("Item Not Present", world.pickItem(1, 5));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPickItem() {
    world.createPlayer(1, "carlos", 2, 10);
    world.pickItem(1, 30);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLookAround() {
    world.createPlayer(1, "carlos", 2, 10);
    world.lookAround(20);
  }
  
  @Test
  public void testNoPlayerOnePlayerInSpace() {
    world.createPlayer(1, "carlos", 2, 4);
    assertEquals("Room Num = 4, Room Name = Dining Hall, "
        + "Neighbors = {3=Foyer, 6=Bathroom, 7=Drawing Room, 13=Kitchen},"
        + " Items = [], player = {1=carlos}, "
        + "coord = 9, 20, 14, 24", world.getCompleteSpaceDetails().get(3));
    assertEquals("Room Num = 3, Room Name = Foyer, Neighbors = "
        + "{4=Dining Hall, 7=Drawing Room, 13=Kitchen, 14=Piazza}, Items = "
        + "[3. Shoe Horn, Item Damage: 2, Item Room: 3, 7. Chain Saw, Item Damage: 4, "
        + "Item Room: 3, 12. Big Red Hammer, Item Damage: 4, Item Room: 3], "
        + "player = {}, coord = 9, 24, 14, 27", world.getCompleteSpaceDetails().get(2));
  }
   
  @Test
  public void testTwoPlayersAddedToSameSpace() {
    world.createPlayer(1, "carlos", 2, 7);
    world.createPlayer(2, "Doris", 1, 13);
    world.movePlayer(1, 4);
    world.movePlayer(2, 4);
    assertEquals("Room Num = 4, Room Name = Dining Hall, "
        + "Neighbors = {3=Foyer, 6=Bathroom, 7=Drawing Room, 13=Kitchen},"
        + " Items = [], player = {1=carlos, 2=Doris}, "
        + "coord = 9, 20, 14, 24", world.getCompleteSpaceDetails().get(3));
  }
  
  @Test
  public void testGetTurn() {
    world.createPlayer(0, "computer", 2, 7);
    world.createPlayer(1, "Doris", 1, 13);
    world.createPlayer(2, "carlos", 3, 12);
    assertEquals("[1, Doris]", world.getTurn().toString());
    world.lookAround(1);
    assertEquals("[2, carlos]", world.getTurn().toString());
    world.movePlayer(2, 20);
    assertEquals("[0, computer]", world.getTurn().toString());
    world.movePlayer(0, 12);
    assertEquals(false, world.gameOver());
    assertEquals("[1, Doris]", world.getTurn().toString());
    world.pickItem(1, 5); //wrong item, so no turn change
    assertEquals("[1, Doris]", world.getTurn().toString());
    world.pickItem(1, 1);
    assertEquals("[2, carlos]", world.getTurn().toString());
    world.lookAround(2);
    assertEquals("[0, computer]", world.getTurn().toString());
    world.movePlayer(0, 1);
  }
  
  @Test
  public void testGameOver() {
    //Max turns is defined as 8 for this test.
    world.createPlayer(1, "carlos", 2, 7);
    world.movePlayer(1, 4);
    world.createPlayer(2, "Doris", 1, 13);
    world.movePlayer(2, 4);
    world.createPlayer(3, "computer", 3, 12);
    world.movePlayer(3, 20);
    assertEquals(false, world.gameOver());
    world.lookAround(1);
    world.lookAround(2);
    world.lookAround(3);
    world.createPlayer(4, "player4", 3, 19);
    world.movePlayer(4, 1);
    world.lookAround(4);
    assertEquals(true, world.gameOver());
  }
  
  @Test 
  public void testWorldGraphCreated() {
    try {
      world.drawWorld();
    } catch (IOException e) {
      System.out.println("could not draw world");
    }
    File tempFile = new File("res/world.png");
    boolean exists = tempFile.exists();
    assertEquals(true, exists);
  }
}