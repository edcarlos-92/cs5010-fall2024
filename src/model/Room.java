package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is the implementation of the Space Interface.
 * The space has a name, coordinates, list of items present in it, 
 * and the list of other neighboring spaces.
 */
public final class Room implements Space {  
  
  private final int roomNum;
  private final String roomName;
  private final int[] coordinate;
  private final List<ItemList> itemList;
  private List<Room> neighborList;
  private Map<Integer, String> roomNumOfNeighbors;
  private Map<Integer, String> playerList;
  
  /**
   * Constructor for Room Class to initialize the parameters. 
   * Need not throw an exception because the input fields are already validated and passed. 
   * @param gameRoomNum Room number of the room. The rooms are numbered from 1 to n.
   * @param gameRoomName Contains the Room name.
   * @param roomItemList Contains a list of the items object which are present in this room.
   * @param coordinates first two values in this list are the row and column value for 
    top left corner of the room. Next two values are the row and column value for 
    bottom right corner of the room.
   * @throws IllegalArgumentException if invalid parameters are passed.
   */
  public Room(int gameRoomNum, String gameRoomName, List<ItemList> roomItemList, int[] coordinates) 
      throws IllegalArgumentException {
    if (gameRoomNum <= 0 || gameRoomName == null || "".equals(gameRoomName) || roomItemList == null 
        || coordinates == null) {
      throw new IllegalArgumentException("Invalid Inputs");
    }
    this.roomNum = gameRoomNum;
    this.roomName = gameRoomName;
    this.itemList = roomItemList;
    this.coordinate = coordinates;
    playerList = new HashMap<Integer, String>();
  }
  
  @Override
  public void setNeighbor(List<Room> neighborRoomList) throws IllegalArgumentException {
    if (neighborRoomList == null) {
      throw new IllegalArgumentException("Neighbors are null. Check correctness of input file.");
    } else {
      this.neighborList = neighborRoomList;
      this.roomNumOfNeighbors = new HashMap<Integer, String>();
      for (int i = 0; i < this.neighborList.size(); i++) {
        this.roomNumOfNeighbors.put(this.neighborList.get(i).getRoomNum(), 
            this.neighborList.get(i).getRoomName());
      }
    }
  }
  
  @Override
  public String getRoomName() {
    return this.roomName;
  }

  @Override
  public int getRoomNum() {
    return this.roomNum;
  }
  
  @Override
  public void removePlayer(int playerNum) throws IllegalArgumentException {
    if (playerNum < 0 || !playerList.containsKey(playerNum)) {
      throw new IllegalArgumentException("Not a valid player.");
    }
    this.playerList.remove(playerNum);
    /*
    for (int i = 0; i < playerList.size(); i++) {
      if (this.playerList..get(i) == playerNum) {
        this.playerList.remove(i);
        return;
      }
    }*/
  }
  
  @Override
  public void addPlayer(int playerNum, String playerName) throws IllegalArgumentException {
    if (playerNum < 0) {
      throw new IllegalArgumentException("Not a valid player.");
    }
    this.playerList.put(playerNum, playerName);
  }
  
  @Override
  public boolean hasItem(ItemList item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Not a valid item.");
    }
    if (itemList.contains(item)) {
      return true;
    }
    return false;
  }

  @Override
  public void removeItem(ItemList item) throws IllegalArgumentException {
    if (item == null || !itemList.contains(item)) {
      throw new IllegalArgumentException("Not a valid item.");
    }
    itemList.remove(item);
  }
    
  @Override
  public String displayNeighborDetails() {
    StringBuilder neighborDetails = new StringBuilder();
    for (int i = 0; i < neighborList.size(); i++) {
      neighborDetails.append("Neighbor Num = ");
      neighborDetails.append(neighborList.get(i).getRoomNum());
      neighborDetails.append(" Neighbor Name = ");
      neighborDetails.append(neighborList.get(i).getRoomName());
      neighborDetails.append("\nItems in Room:\n");
      for (int j = 0; j < neighborList.get(i).itemList.size(); j++) {
        neighborDetails.append(neighborList.get(i).itemList.get(j).toString());
        neighborDetails.append("\n");
      }
      neighborDetails.append("\n");
    }
    return neighborDetails.toString();
  } 
 
  @Override
  public String displayPlayerSpaceInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Room Num = %d, Room Name = %s, \nItems Available:\n",
        this.roomNum, this.roomName));
    for (int i = 0; i < itemList.size(); i++) {
      sb.append(itemList.get(i).toString());
      sb.append("\n");
    }
    return sb.toString();
  }
  
  
  @Override
  public String toString() {
    return String.format("Room Num = %d, Room Name = %s, Neighbors = %s, "
        + "Items = %s, player = %s, coord = %d, %d, %d, %d", this.roomNum, this.roomName, 
        this.roomNumOfNeighbors, this.itemList, this.playerList, this.coordinate[0], 
        this.coordinate[1], this.coordinate[2], this.coordinate[3]);    
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Room)) {
      return false;
    }
    Room other = (Room) obj;
    return Arrays.equals(coordinate, other.coordinate) && Objects.equals(roomName, other.roomName)
        && roomNum == other.roomNum;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(coordinate);
    result = prime * result + Objects.hash(roomName, roomNum);
    return result;
  }

}
