package model;

import java.util.Map;

/**
 * Represents the game world that contains rooms, a target, and items.
 * This interface defines methods to access world specifications, rooms, the target, and items.
 */
public interface World {
  /**
   * Gets the specifications of the world.
   *
   * @return a string describing the world specifications
   */
  String getWorldSpecs();

  /**
   * Gets a map of all rooms in the world.
   *
   * @return a map where the key is the room number and the value is the Room object
   */
  Map<Integer, Room> getRooms();

  /**
   * Gets the target present in the world.
   *
   * @return the Target object
   */
  Target getTarget();

  /**
   * Gets a map of items in the world.
   *
   * @return a map where the key is the item number and the value is the ItemList object
   */
  Map<Integer, ItemList> getItems();
}
