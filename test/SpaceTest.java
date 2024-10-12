import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.ItemList;
import model.Room;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the Space interface.
 * This test verifies the behavior of the Room class as it implements the Space interface.
 */
public class SpaceTest {

  private Room room;
  private List<ItemList> itemList;

  /**
   * Sets up the test environment before each test case is executed.
   * This method initializes a list of items and creates a Room instance
   * with predefined attributes to be used in the tests.
   * 
   * The Room is initialized with:
   * - Room number: 1
   * - Room name: "Cave"
   * - Item list containing two items: "Potion" and "Lantern"
   * - Coordinates set to {0, 0, 0, 0}
   */
  @Before
  public void setUp() {
    itemList = new ArrayList<>();
    itemList.add(new ItemList(1, 1, "Potion", 0));
    itemList.add(new ItemList(2, 1, "Lantern", 0));
    int[] coordinates = {0, 0, 0, 0};
    room = new Room(1, "Cave", itemList, coordinates);
  }

  @Test
  public void testGetRoomName() {
    assertEquals("Cave", room.getRoomName());
  }

  @Test
  public void testGetItems() {
    List<ItemList> items = room.getItems();
    assertEquals(2, items.size());
    assertEquals("Potion", items.get(0).getItemName());
    assertEquals("Lantern", items.get(1).getItemName());
  }

  @Test
  public void testGetNeighborObject() {
    List<Room> neighbors = new ArrayList<>();
    neighbors.add(new Room(2, "Tunnel", new ArrayList<>(), new int[]{1, 0, 0, 0}));
    room.setNeighbor(neighbors);

    List<Room> neighborObjects = room.getNeighborObject();
    assertEquals(1, neighborObjects.size());
    assertEquals("Tunnel", neighborObjects.get(0).getRoomName());
  }

  @Test
  public void testGetNeighborRoomNum() {
    List<Room> neighbors = new ArrayList<>();
    neighbors.add(new Room(2, "Tunnel", new ArrayList<>(), new int[]{1, 0, 0, 0}));
    room.setNeighbor(neighbors);

    List<Integer> neighborRoomNums = room.getNeighborRoomNum();
    assertEquals(1, neighborRoomNums.size());
    assertEquals(2, (int) neighborRoomNums.get(0));
  }

  @Test
  public void testToString() {
    String expected = "Room Num = 1, Room Name = Cave, Neighbors = [], "
            + "Items = [Item Num = 1, Item Room = 1, Item Name = Potion, Item Damage = 0, "
            + "Item Num = 2, Item Room = 1, Item Name = Lantern, Item Damage = 0], "
            + "coord = 0, 0, 0, 0";
    assertEquals(expected, room.toString());
  }
}
