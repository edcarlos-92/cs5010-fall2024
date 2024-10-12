import static org.junit.Assert.assertEquals;

import model.Item;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the Item interface.
 * This test verifies the behavior of an implementation of the Item interface.
 */
public class ItemTest {
  private Item item;

  /**
   * Mock implementation of the Item interface for testing.
   */
  private class MockItem implements Item {
    private int room;
    private int damage;
    private String name;

    /**
     * Constructor for MockItem.
     *
     * @param itemRoom   the room number where the item is located
     * @param itemDamage the damage value of the item
     * @param itemName   the name of the item
     */
    public MockItem(int itemRoom, int itemDamage, String itemName) {
      this.room = itemRoom;
      this.damage = itemDamage;
      this.name = itemName;
    }

    @Override
    public int getItemRoom() {
      return room;
    }

    @Override
    public int getItemDamage() {
      return damage;
    }

    @Override
    public String getItemName() {
      return name;
    }

    @Override
    public String toString() {
      return String.format("%s (Damage: %d)", name, damage);
    }
  }

  /**
   * Sets up a mock item before each test.
   */
  @Before
  public void setUp() {
    item = new MockItem(1, 10, "Sword");
  }

  /**
   * Test the getItemRoom method.
   */
  @Test
  public void testGetItemRoom() {
    assertEquals(1, item.getItemRoom());
  }

  /**
   * Test the getItemDamage method.
   */
  @Test
  public void testGetItemDamage() {
    assertEquals(10, item.getItemDamage());
  }

  /**
   * Test the getItemName method.
   */
  @Test
  public void testGetItemName() {
    assertEquals("Sword", item.getItemName());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("Sword (Damage: 10)", item.toString());
  }
}
