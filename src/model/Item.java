package model;

/**
 * Represents an item in the game world.
 * Each item has a room location, damage value, and a name.
 */
public interface Item {
    
  /**
   * Gets the room number where the item is located.
   *
   * @return the room number as an integer
   */
  int getItemRoom();
  
  /**
   * Gets the damage value of the item.
   *
   * @return the damage value as an integer
   */
  int getItemDamage();
  
  /**
   * Gets the name of the item.
   *
   * @return the name of the item as a String
   */
  String getItemName();
  
  /**
   * Returns a string representation of the item.
   *
   * @return a string describing the item
   */
  String toString();
}
