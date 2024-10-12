import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.ItemList;
import model.Room;
import model.Target;
import model.World;
import org.junit.Before;
import org.junit.Test;



/**
 * Unit test for the World interface.
 * This test verifies the behavior of a mock implementation of the World interface.
 */
public class WorldTest {

  private World world;
  private Map<Integer, Room> rooms;
  private Map<Integer, ItemList> items;
  private Target target;

  /**
   * Mock implementation of the World interface for testing.
   */
  private class MockWorld implements World {
    @Override
    public String getWorldSpecs() {
      return "Fantasy World with 5 Rooms";
    }

    @Override
    public Map<Integer, Room> getRooms() {
      return rooms;
    }

    @Override
    public Target getTarget() {
      return target;
    }

    @Override
    public Map<Integer, ItemList> getItems() {
      return items;
    }
  }

  /**
   * Sets up a mock World instance before each test.
   */
  @Before
  public void setUp() {
    rooms = new HashMap<>();
    items = new HashMap<>();

    // Initialize rooms
    rooms.put(1, new Room(1, "Entrance", new ArrayList<>(), new int[]{0, 0, 0, 0}));
    rooms.put(2, new Room(2, "Hallway", new ArrayList<>(), new int[]{1, 1, 1, 1}));

    // Initialize items
    items.put(1, new ItemList(1, 1, "Sword", 10));
    items.put(2, new ItemList(2, 1, "Shield", 5));

    // Initialize target
    target = new Target("Enemy", 100, 1, 5);

    // Create the mock world instance
    world = new MockWorld();
  }

  /**
   * Test getting the world specifications.
   */
  @Test
  public void testGetWorldSpecs() {
    assertEquals("Fantasy World with 5 Rooms", world.getWorldSpecs());
  }

  /**
   * Test getting the rooms in the world.
   */
  @Test
  public void testGetRooms() {
    assertNotNull(world.getRooms());
    assertEquals(2, world.getRooms().size());
    assertEquals("Entrance", world.getRooms().get(1).getRoomName());
  }

  /**
   * Test getting the target in the world.
   */
  @Test
  public void testGetTarget() {
    assertNotNull(world.getTarget());
    assertEquals("Enemy", world.getTarget().toString().split(",")[0].split("=")[1].trim());
  }

  /**
   * Test getting the items in the world.
   */
  @Test
  public void testGetItems() {
    assertNotNull(world.getItems());
    assertEquals(2, world.getItems().size());
    assertEquals("Sword", world.getItems().get(1).getItemName());
  }
}
