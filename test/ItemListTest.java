import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import model.ItemList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the ItemList class.
 * This test verifies the behavior of the ItemList class.
 */
public class ItemListTest {
  private ItemList item;

  /**
   * Sets up an item before each test.
   */
  @Before
  public void setUp() {
    item = new ItemList(1, 10, "Sword", 10);
  }

  /**
   * Test the getItemRoom method.
   */
  @Test
  public void testGetItemRoom() {
    assertEquals(10, item.getItemRoom());
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
    assertEquals("Item Num = 1, Item Room = 10, Item Name = Sword, Item Damage = 10", 
                   item.toString());
  }

  /**
   * Test the equals method.
   */
  @Test
  public void testEquals() {
    ItemList otherItem = new ItemList(1, 10, "Sword", 10);
    assertEquals(item, otherItem);
  }

  /**
   * Test the hashCode method.
   */
  @Test
  public void testHashCode() {
    ItemList otherItem = new ItemList(1, 10, "Sword", 10);
    assertEquals(item.hashCode(), otherItem.hashCode());
  }

  /**
   * Test IllegalArgumentException for negative damage value.
   */
  @Test
  public void testConstructorNegativeDamage() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ItemList(1, 10, "Sword", -1);
    });
  }

  /**
   * Test IllegalArgumentException for zero damage value.
   */
  @Test
  public void testConstructorZeroDamage() {
    assertThrows(IllegalArgumentException.class, () -> {
      new ItemList(1, 10, "Sword", 0);
    });
  }
}
