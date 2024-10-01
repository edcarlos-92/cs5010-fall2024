package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room in the game world.
 * Each room has a unique number, a name, coordinates, 
 * a list of items, and a list of neighboring rooms.
 */
public final class Room implements Space {
  private final int roomNum;
  private final String roomName;
  private final int[] coordinate;
  private List<ItemList> itemList;
  private List<Room> neighborList;
  private List<Integer> roomNumOfNeighbors;

  /**
   * Constructs a Room instance with the specified parameters.
   *
   * @param roomNumInst   the unique number of the room, must be greater than 0
   * @param name      the name of the room
   * @param itemListInst  the list of items present in the room
   * @param coordinates the coordinates of the room in the game world
   * @throws IllegalArgumentException if roomNum is less than or equal to 0
   */
  public Room(int roomNumInst, String name, List<ItemList> itemListInst, int[] coordinates) 
      throws IllegalArgumentException {
    if (roomNumInst <= 0) {
      throw new IllegalArgumentException("Room Number starts from 1. Cannot be zero or negative");
    } else {
      this.roomNum = roomNumInst;
      this.roomName = name;
      this.itemList = itemListInst;
      this.coordinate = coordinates;
    }
  }

  /**
   * Sets the neighboring rooms for this room.
   *
   * @param neighborRoomList a list of neighboring rooms
   * @throws IllegalArgumentException if neighborList is null
   */
  public void setNeighbor(List<Room> neighborRoomList) throws IllegalArgumentException {
    if (neighborRoomList == null) {
      throw new IllegalArgumentException("Neighbors are null. Check correctness of input file.");
    } else {
      this.neighborList = neighborRoomList;
      this.roomNumOfNeighbors = new ArrayList<Integer>();

      for (int i = 0; i < this.neighborList.size(); ++i) {
        this.roomNumOfNeighbors.add(((Room) this.neighborList.get(i)).roomNum);
      }
    }
  }

  /**
   * Gets the name of the room.
   *
   * @return the name of the room
   */
  public String getRoomName() {
    return this.roomName;
  }

  /**
   * Gets the list of neighboring room objects.
   *
   * @return a list of neighboring Room objects
   */
  public List<Room> getNeighborObject() {
    return this.neighborList;
  }

  /**
   * Gets the room numbers of the neighboring rooms.
   *
   * @return a list of integers representing the room numbers of neighbors
   */
  public List<Integer> getNeighborRoomNum() {
    return this.roomNumOfNeighbors;
  }

  /**
   * Gets the list of items in the room.
   *
   * @return a list of ItemList objects present in the room
   */
  public List<ItemList> getItems() {
    return this.itemList;
  }

  /**
   * Returns a string representation of the room, including its details.
   *
   * @return a string describing the room
   */
  public String toString() {
    return String.format("Room Num = %d, Room Name = %s, Neighbors = %s, "
        + "Items = %s, coord = %d, %d, %d, %d",
            this.roomNum, this.roomName, this.roomNumOfNeighbors, this.itemList,
            this.coordinate[0], this.coordinate[1], this.coordinate[2], this.coordinate[3]);
  }

  /**
   * Compares this room to another object for equality.
   *
   * @param obj the object to compare with
   * @return true if the rooms are equal, false otherwise
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof Room)) {
      return false;
    } else {
      Room other = (Room) obj;
      return Objects.hash(new Object[]{this.toString()}) == Objects.hash(new Object[]{
          other.toString()
          });
    }
  }

  /**
   * Returns a hash code value for this room.
   *
   * @return a hash code value for the room
   */
  public int hashCode() {
    return Objects.hash(new Object[]{this.toString()});
  }
}
