package model;

import java.util.List;

/**
 * Represents a space in the game world, such as a room.
 * This interface defines the behaviors that any space should implement.
 */
public interface Space {
  /**
   * Gets the name of the room or space.
   *
   * @return the name of the room
   */
  String getRoomName();

  /**
   * Gets the list of neighboring room objects.
   *
   * @return a list of neighboring Room objects
   */
  List<Room> getNeighborObject();

  /**
   * Gets the room numbers of the neighboring rooms.
   *
   * @return a list of integers representing the room numbers of neighbors
   */
  List<Integer> getNeighborRoomNum();

  /**
   * Gets the list of items in the room or space.
   *
   * @return a list of ItemList objects present in the room
   */
  List<ItemList> getItems();

  /**
   * Returns a string representation of the space.
   *
   * @return a string describing the space
   */
  String toString();
}
