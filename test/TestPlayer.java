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
 * This Test class has unit tests to test the methods of the player class.
 */
public class TestPlayer {

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
  
  //CHECK ALL METHODS AND FUNCTIONALITY OF PLAYER
  @Test
  public void testPlayerCreation() {
    //Using toString() to check if player is created correctly and values are set correctly.
    world.createPlayer(1, "carlos", 2, 5);
    assertEquals("\nPlayer Num: 1\nPlayer Name: carlos\nPlayer in Room: Num: 5 Name: Guest Room\n"
        + "Max Number of Items allowed to pick: 2\nItems Picked:\n", 
        world.getSpecificPlayerDetails(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerCreationIfIllegalArgument() {
    world.createPlayer(1, "carlos", -2, 5);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerCreationIfIllegalArgument2() {
    world.createPlayer(1, "", 2, -2);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerCreationIfIllegalArgument3() {
    world.createPlayer(1, "carlos", 2, 30);
  }
  
  @Test
  public void testValidMovePlayer() {
    world.createPlayer(1, "carlos", 2, 5);
    assertEquals("Move Completed", world.movePlayer(1, 20));
  }
  
  @Test
  public void testInvalidMovePlayer() {
    world.createPlayer(1, "carlos", 2, 5);
    assertEquals("Room not a neighbor. Lose your turn", world.movePlayer(1, 8));
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testMovePlayerException() {
    world.createPlayer(1, "carlos", 2, 5);
    world.movePlayer(1, 30);
  }
  
  @Test
  public void testPickItem() {
    world.createPlayer(1, "carlos", 1, 6);
    assertEquals("Item Picked", world.pickItem(1, 14));
  }
  
  @Test
  public void testPickItemOverflow() {
    world.createPlayer(1, "carlos", 2, 6);
    world.pickItem(1, 14);
    world.pickItem(1, 21);
    world.movePlayer(1, 1);
    assertEquals("Max item count reached for player", world.pickItem(1, 13));
  }
  
  @Test
  public void testInvalidPickItem() {
    world.createPlayer(1, "carlos", 1, 6);
    assertEquals("Item Not Present", world.pickItem(1, 5));
  }
  
  @Test (expected = IllegalArgumentException.class)
  public void testPickItemException() {
    world.createPlayer(1, "carlos", 1, 5);
    world.pickItem(1, 30);
  }
  
  @Test 
  public void testPlayerDetailsAfterMoveAndItemPick() {
    world.createPlayer(1, "carlos", 2, 6);
    assertEquals("\nPlayer Num: 1\nPlayer Name: carlos\n"
        + "Player in Room: Num: 6 Name: Bathroom\n"
        + "Max Number of Items allowed to pick: 2\nItems Picked:\n",
        world.getSpecificPlayerDetails(1));
    world.pickItem(1, 21);
    world.pickItem(1, 21);
    assertEquals("\nPlayer Num: 1\nPlayer Name: carlos\n"
        + "Player in Room: Num: 6 Name: Bathroom\n"
        + "Max Number of Items allowed to pick: 2\nItems Picked:\n"
        + "21. Hair Comb, Item Damage: 2\n", world.getSpecificPlayerDetails(1));
    world.movePlayer(1, 1);
    assertEquals("\nPlayer Num: 1\nPlayer Name: carlos\n"
        + "Player in Room: Num: 1 Name: Trophy Room\n"
        + "Max Number of Items allowed to pick: 2\nItems Picked:\n"
        + "21. Hair Comb, Item Damage: 2\n", world.getSpecificPlayerDetails(1));
  }
  
  @Test
  public void testLookAround() {
    world.createPlayer(1, "carlos", 2, 6);
    assertEquals("Current Room Num = 6, Current Room Name = Bathroom \n"
        + "Neighboring Rooms with Items: \nNeighbor Num = 1 Neighbor Name = Trophy Room\n"
        + "Items in Room:\n13. Pinking Shears, Item Damage: 2, Item Room: 1\n"
        + "\nNeighbor Num = 4 Neighbor Name = Dining Hall\nItems in Room:\n"
        + "\nNeighbor Num = 5 Neighbor Name = Guest Room\nItems in Room:\n"
        + "\nNeighbor Num = 7 Neighbor Name = Drawing Room\nItems in Room:\n\n",
        world.lookAround(1));
  }
  
  @Test
  public void testOtherPlayerWhenActiveTurnPlayerMoves() {
    world.createPlayer(1, "carlos", 1, 16);
    world.createPlayer(2, "Doris", 2, 13);
    String player21 = world.getSpecificPlayerDetails(2) + world.getSpecificRoomDetails(13); 
    world.createPlayer(3, "computer", 1, 5);
    String player31 = world.getSpecificPlayerDetails(3) + world.getSpecificRoomDetails(5); 
    world.movePlayer(1, 2);
    assertEquals(player21, world.getSpecificPlayerDetails(2) + world.getSpecificRoomDetails(13));
    assertEquals(player31, world.getSpecificPlayerDetails(3) + world.getSpecificRoomDetails(5));
  }
  
  @Test
  public void testOtherPlayerWhenActiveTurnPlayerPicksItem() {
    world.createPlayer(1, "carlos", 1, 16);
    world.createPlayer(2, "Doris", 2, 13);
    String player21 = world.getSpecificPlayerDetails(2) + world.getSpecificRoomDetails(13); 
    world.createPlayer(3, "computer", 1, 5);
    String player31 = world.getSpecificPlayerDetails(3) + world.getSpecificRoomDetails(5); 
    world.pickItem(1, 13);
    assertEquals(player21, world.getSpecificPlayerDetails(2) + world.getSpecificRoomDetails(13));
    assertEquals(player31, world.getSpecificPlayerDetails(3) + world.getSpecificRoomDetails(5));
  }
  
  @Test
  public void testOtherPlayerWhenActiveTurnPlayerLooksAround() {
    world.createPlayer(1, "carlos", 1, 16);
    world.createPlayer(2, "Doris", 2, 13);
    String player21 = world.getSpecificPlayerDetails(2) + world.getSpecificRoomDetails(13); 
    world.createPlayer(3, "computer", 1, 5);
    String player31 = world.getSpecificPlayerDetails(3) + world.getSpecificRoomDetails(5); 
    world.lookAround(1);
    assertEquals(player21, world.getSpecificPlayerDetails(2) + world.getSpecificRoomDetails(13));
    assertEquals(player31, world.getSpecificPlayerDetails(3) + world.getSpecificRoomDetails(5));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testCreatingPlayerWithSameIndex() {
    world.createPlayer(1, "carlos", 1, 16);
    world.createPlayer(1, "Doris", 2, 13);
  }
}
