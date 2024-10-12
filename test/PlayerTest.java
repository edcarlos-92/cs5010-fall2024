import static org.junit.Assert.assertEquals;

import model.Player;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for the Player interface.
 * This test verifies the behavior of the Player implementation.
 */
public class PlayerTest {

  private Player player;

  /**
   * A simple implementation of the Target Player interface for testing.
   */
  private class TestPlayer implements Player {
    private int location;
    private int health;

    public TestPlayer(int targetLocation, int targetHealth) {
      this.location = targetLocation;
      this.health = targetHealth;
    }

    @Override
    public int getLocation() {
      return location;
    }

    @Override
    public void move() {
      // Simulate moving to a new location (e.g., incrementing the location)
      this.location += 1;
    }

    @Override
    public int getHealth() {
      return health;
    }

    @Override
    public String toString() {
      return String.format("Player Location: %d, Health: %d", location, health);
    }
  }

  /**
   * Sets up a player before each test.
   */
  @Before
  public void setUp() {
    player = new TestPlayer(0, 100);
  }

  /**
   * Test the getLocation method.
   */
  @Test
  public void testGetLocation() {
    assertEquals(0, player.getLocation());
  }

  /**
   * Test the move method.
   */
  @Test
  public void testMove() {
    player.move();
    assertEquals(1, player.getLocation());
  }

  /**
   * Test the getHealth method.
   */
  @Test
  public void testGetHealth() {
    assertEquals(100, player.getHealth());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("Player Location: 0, Health: 100", player.toString());
  }
}
