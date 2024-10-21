package model;

/**
 * This is the Interface for the Target Character. The Target Object is of type TargetInterface.
 * It provides details like the Player's current location, health, and has methods to
  move to a different position.
 */
public interface TargetInterface {

  /**
   * Gives the Current location in terms of the room number of the player.
   * @return returns the integer value of the room number (room numbers start from 1)
    where the player is present. 
   */
  public int getLocation();
  
  /**
   * This method is used to move the Player to a different room.
   * Does not return a value after moving the player.
   */
  public void move();
  
  /**
   * Gives the health of the player. Health is expected to be greater than 0.
   * @return returns the integer value of the player's health.
   */
  int getHealth();
  
  /**
   * Gives the details of the Player like Name, health, current location.
   * @return returns the String representation of the player details.
   */
  public String toString();
  
}