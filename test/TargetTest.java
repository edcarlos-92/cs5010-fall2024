import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import model.Target;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the Target class.
 * This test verifies the behavior of the Target class and its methods.
 */
public class TargetTest {

  private Target target;

  /**
   * Sets up a Target instance before each test.
   */
  @Before
  public void setUp() {
    target = new Target("Enemy", 100, 1, 5);
  }

  /**
   * Test the constructor with valid parameters.
   */
  @Test
  public void testConstructorValidParameters() {
    assertEquals("Enemy", target.toString().split(",")[0].split("=")[1].trim());
    assertEquals(100, target.getHealth());
    assertEquals(1, target.getLocation());
  }

  /**
   * Test the constructor for invalid health value.
   */
  @Test
  public void testConstructorInvalidHealth() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Target("Invalid Enemy", -10, 1, 5);
    });
  }

  /**
   * Test moving the target to the next room.
   */
  @Test
  public void testMoveToNextRoom() {
    target.move();
    assertEquals(2, target.getLocation());
  }

  /**
   * Test moving the target from the last room to the first room.
   */
  @Test
  public void testMoveFromLastRoomToFirst() {
    target = new Target("Enemy", 100, 5, 5); // Start at last room
    target.move();
    assertEquals(1, target.getLocation());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    String expected = "Target = Enemy, Health = 100, Current Location = 1";
    assertEquals(expected, target.toString());
  }
}
