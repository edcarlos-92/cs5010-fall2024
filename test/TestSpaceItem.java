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
 * This Test class has unit tests to test the methods of the Space class.
 */
public class TestSpaceItem {

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
  public void testSpaceCreationAndDetails() {
    //Using toString() to check if Room is created correctly and values are set correctly.
    assertEquals("Room Num = 1, Room Name = Trophy Room, Neighbors = "
        + "{20=Closet, 5=Guest Room, 6=Bathroom, 8=Lanchester Room, 10=Master Suite},"
        + " Items = [13. Pinking Shears, Item Damage: 2, Item Room: 1], player = {}, "
        + "coord = 8, 15, 14, 18", world.getCompleteSpaceDetails().get(1 - 1));
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
  
  @Test
  public void testPlayerViewOfSpace() {
    assertEquals("Room Num = 2, Room Name = Pantry, \nItems Available:\n"
        + "9. Billiard Cue, Item Damage: 2, Item Room: 2\n", world.getSpecificRoomDetails(2));
  }
  
  @Test
  public void testPlayerAddandRemovedInSpace() {
    world.createPlayer(1, "carlos", 2, 6);
    world.movePlayer(1, 4);
    assertEquals("Room Num = 6, Room Name = Bathroom, Neighbors = {1=Trophy Room, 4=Dining Hall,"
        + " 5=Guest Room, 7=Drawing Room}, Items = [14. Duck Decoy, Item Damage: 3, Item Room: 6, "
        + "21. Hair Comb, Item Damage: 2, Item Room: 6], player = {}, coord = 4, 18, 9, 21", 
        world.getCompleteSpaceDetails().get(6 - 1));
    assertEquals("Room Num = 4, Room Name = Dining Hall, Neighbors = {3=Foyer, 6=Bathroom,"
        + " 7=Drawing Room, 13=Kitchen}, Items = [], player = {1=carlos}, coord = 9, 20, 14, 24", 
        world.getCompleteSpaceDetails().get(4 - 1));
  }
  
  @Test
  public void testPickItemFromSpace() {
    world.createPlayer(1, "carlos", 2, 6);
    assertEquals("Room Num = 6, Room Name = Bathroom, Neighbors = {1=Trophy Room, 4=Dining Hall,"
        + " 5=Guest Room, 7=Drawing Room}, Items = [14. Duck Decoy, Item Damage: 3, "
        + "Item Room: 6, 21. Hair Comb, Item Damage: 2, Item Room: 6], player = {1=carlos}, "
        + "coord = 4, 18, 9, 21", world.getCompleteSpaceDetails().get(6 - 1));
    world.pickItem(1, 14);
    assertEquals("Room Num = 6, Room Name = Bathroom, Neighbors = {1=Trophy Room, 4=Dining Hall,"
        + " 5=Guest Room, 7=Drawing Room}, Items = [21. Hair Comb, Item Damage: 2, Item Room: 6],"
        + " player = {1=carlos}, coord = 4, 18, 9, 21", 
        world.getCompleteSpaceDetails().get(6 - 1));
  }
  
  @Test
  public void testNeighborsNoNeighbors() {
    String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
        + "m1-the-world/res/dr_lucky_in_marios_castle.txt";
    Readable fileContent = convertFileToString(filePath); 
    World world2 = new WorldSetup(fileContent, 8);
    world2.createPlayer(1, "carlos", 2, 17);
    assertEquals("Current Room Num = 17, Current Room Name = Garage \n"
        + "Neighboring Rooms with Items: \n", world2.lookAround(1));
  }
  
  @Test
  public void testNeighborsOneNeighbors() {
    world.createPlayer(1, "carlos", 2, 17);
    assertEquals("Current Room Num = 17, Current Room Name = Garage \n"
        + "Neighboring Rooms with Items: \nNeighbor Num = 14 Neighbor Name = Piazza\n"
        + "Items in Room:\n16. Monkey Hand, Item Damage: 2, Item Room: 14\n\n",
        world.lookAround(1));
  }
  
  @Test
  public void testNeighborsTwoNeighbors() {
    world.createPlayer(1, "carlos", 2, 21);
    assertEquals("Current Room Num = 21, Current Room Name = Green House \n"
        + "Neighboring Rooms with Items: \nNeighbor Num = 11 Neighbor Name = Nursery\n"
        + "Items in Room:\n15. Bad Cream, Item Damage: 1, Item Room: 11\n\n"
        + "Neighbor Num = 18 Neighbor Name = Gallery\nItems in Room:\n\n", world.lookAround(1));
  }
      
  @Test
  public void testNeighborsMultipleNeighbor() {
    world.createPlayer(1, "carlos", 2, 14);
    assertEquals("Current Room Num = 14, Current Room Name = Piazza \n"
        + "Neighboring Rooms with Items: \nNeighbor Num = 3 Neighbor Name = Foyer\n"
        + "Items in Room:\n3. Shoe Horn, Item Damage: 2, Item Room: 3\n"
        + "7. Chain Saw, Item Damage: 4, Item Room: 3\n"
        + "12. Big Red Hammer, Item Damage: 4, Item Room: 3\n\n"
        + "Neighbor Num = 15 Neighbor Name = Hedge Maze\nItems in Room:\n"
        + "10. Rat Poison, Item Damage: 2, Item Room: 15\n\n"
        + "Neighbor Num = 17 Neighbor Name = Garage\nItems in Room:\n"
        + "8. Broom Stick, Item Damage: 2, Item Room: 17\n"
        + "11. Trowel, Item Damage: 2, Item Room: 17\n\n", world.lookAround(1));
  }
        
  @Test
  public void testOneItemsInSpace() {
    assertEquals("Room Num = 2, Room Name = Pantry, Neighbors = {16=Wine Cellar, 8=Lanchester Room,"
        + " 13=Kitchen}, Items = [9. Billiard Cue, Item Damage: 2, Item Room: 2], player = {}, "
        + "coord = 17, 18, 21, 22", world.getCompleteSpaceDetails().get(2 - 1));
  }
  
  @Test
  public void testMultipleItemsInSpace() {
    assertEquals("Room Num = 3, Room Name = Foyer, Neighbors = {4=Dining Hall, 7=Drawing "
        + "Room, 13=Kitchen, 14=Piazza}, Items = [3. Shoe Horn, Item Damage: 2, Item Room: 3,"
        + " 7. Chain Saw, Item Damage: 4, Item Room: 3, 12. Big Red Hammer, Item Damage: 4, "
        + "Item Room: 3], player = {}, coord = 9, 24, 14, 27", 
        world.getCompleteSpaceDetails().get(3 - 1));
  }
  
  @Test
  public void testNoItemsInSpace() {
    assertEquals("Room Num = 5, Room Name = Guest Room, Neighbors = {1=Trophy Room, 20=Closet,"
        + " 6=Bathroom}, Items = [], player = {}, coord = 3, 15, 8, 18", 
        world.getCompleteSpaceDetails().get(4));
  }
  
  @Test
  public void testAllItemDetails() {    
    assertEquals("Items = {1=1. Crepe Pan, Item Damage: 3, Item Room: 13, 2=2. Letter Opener, "
        + "Item Damage: 2, Item Room: 19, 3=3. Shoe Horn, Item Damage: 2, Item Room: 3, 4=4. "
        + "Sharp Knife, Item Damage: 3, Item Room: 9, 5=5. Revolver, Item Damage: 4, Item Room: 12,"
        + " 6=6. Civil War Cannon, Item Damage: 3, Item Room: 16, 7=7. Chain Saw, Item Damage: 4, "
        + "Item Room: 3, 8=8. Broom Stick, Item Damage: 2, Item Room: 17, 9=9. Billiard Cue, "
        + "Item Damage: 2, Item Room: 2, 10=10. Rat Poison, Item Damage: 2, Item Room: 15, "
        + "11=11. Trowel, Item Damage: 2, Item Room: 17, 12=12. Big Red Hammer, Item Damage: 4, "
        + "Item Room: 3, 13=13. Pinking Shears, Item Damage: 2, Item Room: 1, 14=14. Duck Decoy, "
        + "Item Damage: 3, Item Room: 6, 15=15. Bad Cream, Item Damage: 1, Item Room: 11, 16=16. "
        + "Monkey Hand, Item Damage: 2, Item Room: 14, 17=17. Tight Hat, Item Damage: 2, "
        + "Item Room: 8, 18=18. Piece of Rope, Item Damage: 2, Item Room: 20, 19=19. Silken Cord,"
        + " Item Damage: 3, Item Room: 10, 20=20. Loud Noise, Item Damage: 1, Item Room: 8, 21=21."
        + " Hair Comb, Item Damage: 2, Item Room: 6, 22=22. Wooden Stick, Item Damage: 3,"
        + " Item Room: 21},", world.toString().split("\n")[3]);
  }
   
  @Test
  public void testItemRoomAfterPicked() {
    String[] items = world.toString().split("\n")[3].split("\\d+=");
    world.createPlayer(1, "carlos", 2, 6);
    world.pickItem(1, 14);
    assertEquals("14. Duck Decoy, Item Damage: 3, Item Room: 6, ", items[14]);
    items = world.toString().split("\n")[3].split("\\d+=");
    //After Picking an Item, the room number changes to 0.
    assertEquals("14. Duck Decoy, Item Damage: 3, Item Room: 0, ", items[14]);
  }
   
  @Test
  public void testTwoPlayersCannotPickSameItem() {
    world.createPlayer(1, "carlos", 2, 6);
    world.createPlayer(2, "Doris", 2, 6);
    world.pickItem(1, 14);
    assertEquals("Item Not Present", world.pickItem(2, 14));
  }
  
}
