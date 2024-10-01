package model;

/**
 * Represents a target player in the game.
 * This class implements the Player interface and defines the properties and behaviors of a target.
 */
public final class Target implements Player {
  private final String targetName;
  private final int health;
  private int currentLocation;
  private final int roomCount;

  /**
   * Constructs a Target object with the specified name, health, starting location, and room count.
   *
   * @param name          the name of the target
   * @param healthStatus        the initial health of the target (must be positive)
   * @param startLocation the initial location of the target
   * @param roomCountTotal     the total number of rooms in the game
   * @throws IllegalArgumentException if health is less than or equal to zero
   */
  public Target(String name, int healthStatus, int startLocation, int roomCountTotal) 
      throws IllegalArgumentException {
    if (healthStatus <= 0) {
      throw new IllegalArgumentException("Initial Health of Target must be a Positive value.");
    } else {
      this.targetName = name;
      this.health = healthStatus;
      this.currentLocation = startLocation;
      this.roomCount = roomCountTotal;
    }
  }

  /**
   * Gets the current location of the target.
   *
   * @return the current location of the target
   */
  public int getLocation() {
    return this.currentLocation;
  }

  /**
   * Gets the health of the target.
   *
   * @return the health of the target
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * Moves the target to the next room in a circular manner.
   * If the target is in the last room, it moves back to the first room.
   */
  public void move() {
    if (this.currentLocation == this.roomCount) {
      this.currentLocation = 1;
    } else {
      ++this.currentLocation;
    }
  }

  /**
   * Returns a string representation of the target.
   *
   * @return a string describing the target's name, health, and current location
   */
  public String toString() {
    return String.format("Target = %s, Health = %d, Current Location = %d", 
        this.targetName, this.health, this.currentLocation);
  }
}
