import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import model.ItemList;
import model.Room;
import org.junit.Before;
import org.junit.Test;



/**
 * Unit test for the Room class.
 * This test verifies the behavior of the Room class and its methods.
 */
public class RoomTest {

  private Room room;
  private List<ItemList> itemList;

  /**
   * Sets up a Room instance before each test.
   */
  @Before
  public void setUp() {
    itemList = new ArrayList<>();
    itemList.add(new ItemList(1, 1, "Sword", 10));
    itemList.add(new ItemList(2, 1, "Shield", 5));
    int[] coordinates = {0, 0, 0, 0};
    room = new Room(1, "Entrance", itemList, coordinates);
  }

  /**
   * Test the constructor and room number validation.
   */
  @Test
  public void testConstructorValidRoomNum() {
    Room testRoom = new Room(2, "Hallway", new ArrayList<>(), new int[]{1, 1, 1, 1});
    assertEquals("Hallway", testRoom.getRoomName());
  }

  /**
   * Test the constructor for invalid room number.
   */
  @Test
  public void testConstructorInvalidRoomNum() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Room(0, "Invalid Room", new ArrayList<>(), new int[]{0, 0, 0, 0});
    });
  }

  /**
   * Test the setNeighbor method with valid neighbors.
   */
  @Test
  public void testSetNeighborValid() {
    List<Room> neighbors = new ArrayList<>();
    neighbors.add(new Room(2, "Hallway", new ArrayList<>(), new int[]{1, 1, 1, 1}));
    room.setNeighbor(neighbors);
    assertEquals(1, room.getNeighborRoomNum().size());
    assertEquals(2, (int) room.getNeighborRoomNum().get(0));
  }

  /**
   * Test the setNeighbor method for null input.
   */
  @Test
  public void testSetNeighborNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      room.setNeighbor(null);
    });
  }

  /**
   * Test getting the room name.
   */
  @Test
  public void testGetRoomName() {
    assertEquals("Entrance", room.getRoomName());
  }

  /**
   * Test getting the list of items in the room.
   */
  @Test
  public void testGetItems() {
    assertEquals(2, room.getItems().size());
    assertEquals("Sword", room.getItems().get(0).getItemName());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    String expected = "Room Num = 1, Room Name = Entrance, Neighbors = null, "
            + "Items = [Item Num = 1, Item Room = 1, Item Name = Sword, Item Damage = 10, "
            + "Item Num = 2, Item Room = 1, Item Name = Shield, Item Damage = 5], "
            + "coord = 0, 0, 0, 0";
    assertEquals(expected, room.toString());
  }

  /**
   * Test the equals method for equality.
   */
  @Test
  public void testEquals() {
    Room anotherRoom = new Room(1, "Entrance", itemList, new int[]{0, 0, 0, 0});
    assertEquals(room, anotherRoom);
  }

  /**
   * Test the hashCode method.
   */
  @Test
  public void testHashCode() {
    Room anotherRoom = new Room(1, "Entrance", itemList, new int[]{0, 0, 0, 0});
    assertEquals(room.hashCode(), anotherRoom.hashCode());
  }
  
  /**
   * Test getting neighbors when no neighbors are set.
   */
  @Test
  public void testGetNeighborsWhenNoNeighbors() {
    // Ensure no neighbors are set
    room.setNeighbor(new ArrayList<>());

    // Check that the neighbor objects list is empty
    List<Room> neighborObjects = room.getNeighborObject();
    assertEquals(0, neighborObjects.size()); // Expecting size 0

    // Check that the neighbor room numbers list is empty
    List<Integer> neighborRoomNums = room.getNeighborRoomNum();
    assertEquals(0, neighborRoomNums.size()); // Expecting size 0
  }
  
  
  
}
