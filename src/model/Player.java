package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is the implementation class of the players. It implements the PlayerInterface Interface.
 * This class is used to represent both the human controlled player as well as the computer 
  controlled player. It is left to the controller to handle the difference.
 * It provides details like the Player's current location, Items picked and has methods 
  to pick up new items, move to different rooms and look around the room.
 */
public class Player implements PlayerInterface {
  
  private final String playerName;
  private final int playerNum;
  private Room currentLocation;
  private final int maxItem;
  private final List<ItemList> pickedItems;
  
  /**
   * This is the constructor for the Player class. If valid parameters are passed,
    a Player is created and added to the start room.
   * @param gamePlayerNum integer value for the player to be created.
   * @param gamePlayerName player name for the player being created.
   * @param playerMaxItem the maximum number of items that the player is allowed to pick.
   * @param cur Object reference for the room where the player enters first.
   * @throws IllegalArgumentException thrown when invalid parameters are passed.
   */
  public Player(int gamePlayerNum, String gamePlayerName, int playerMaxItem, Room cur) 
      throws IllegalArgumentException {
    if (gamePlayerNum < 0 || gamePlayerName == null || "".equals(gamePlayerName) 
         || playerMaxItem < 0 
         || cur == null) {
      throw new IllegalArgumentException("Player Details are not correct");
    }
    pickedItems = new ArrayList<ItemList>();
    this.playerNum = gamePlayerNum;
    this.playerName = gamePlayerName;
    this.maxItem = playerMaxItem;
    this.currentLocation = cur;
    this.currentLocation.addPlayer(this.playerNum, this.playerName);
  }

  @Override
  public String getPlayerName() {
    return this.playerName;
  }

  @Override
  public int getMaxItems() {
    return this.maxItem;
  }
  
  @Override
  public int getLocation() {
    return this.currentLocation.getRoomNum();
  }

  @Override
  public void move(Room nextRoom) throws IllegalArgumentException {
    if (nextRoom == null) {
      throw new IllegalArgumentException("Not a valid Room");
    }
    this.currentLocation.removePlayer(this.playerNum);
    this.currentLocation = nextRoom;
    nextRoom.addPlayer(this.playerNum, this.playerName);
    //this.currentLocation.addPlayer(this.playerNum);
  }

  @Override
  public void pickItem(ItemList item) throws IllegalArgumentException {
    if (item == null) {
      throw new IllegalArgumentException("Not a valid item to pick.");
    }
    pickedItems.add(item);
  }

  @Override
  public String lookAround() {    
    return String.format("Current Room Num = %d, Current Room Name = %s "
        + "\nNeighboring Rooms with Items: \n%s", currentLocation.getRoomNum(),
        currentLocation.getRoomName(), currentLocation.displayNeighborDetails());
  }
  
  @Override
  public int getItemCount() {
    return pickedItems.size();
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nPlayer Num: ");
    sb.append(this.playerNum);
    sb.append("\nPlayer Name: ");
    sb.append(this.playerName);
    sb.append("\nPlayer in Room: Num: ");
    sb.append(currentLocation.getRoomNum());
    sb.append(" ( ");
    sb.append(currentLocation.getRoomName());
    sb.append(" ) ");
    sb.append("\nMax Number of Items allowed to pick: ");
    sb.append(maxItem);
    sb.append("\nItems Picked:\n");
    for (int i = 0; i < getItemCount(); i++) {
      sb.append(pickedItems.get(i).getItemNum());
      sb.append(". ");
      sb.append(pickedItems.get(i).getItemName());
      sb.append(", Item Damage: ");
      sb.append(pickedItems.get(i).getItemDamage());
      sb.append("\n");
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Player)) {
      return false;
    }
    Player other = (Player) obj;
    return Objects.equals(currentLocation, other.currentLocation) && maxItem == other.maxItem
        && Objects.equals(pickedItems, other.pickedItems)
        && Objects.equals(playerName, other.playerName) && playerNum == other.playerNum;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(currentLocation, maxItem, pickedItems, playerName, playerNum);
  }
  
}
