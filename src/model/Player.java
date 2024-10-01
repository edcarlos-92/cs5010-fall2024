package model;

/**
 * Represents a player in the game world.
 * Provides methods to retrieve the player's location, 
 * move the player, and get the player's health.
 */
public interface Player {
    
  /**
   * Gets the current location of the player.
   *
   * @return the location of the player as an integer
   */
  int getLocation();

  /**
   * Moves the player to a new location.
   */
  void move();

  /**
   * Gets the current health of the player.
   *
   * @return the health of the player as an integer
   */
  int getHealth();

  /**
   * Returns a string representation of the player.
   *
   * @return a string describing the player
   */
  String toString();
}
