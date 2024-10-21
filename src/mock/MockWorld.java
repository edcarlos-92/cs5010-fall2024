package mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class serves as a mock implementation of the World interface
 * used for testing purposes. It provides methods to simulate world-related
 * operations such as drawing the world, creating and moving players, picking items, etc.
 */
public abstract class MockWorld implements model.World {
   
  // StringBuilder used to log actions in the mock world.
  protected StringBuilder log;
   
  // Unique code representing this mock world instance.
  protected final int uniqueCode;

  /**
   * Constructs a new MockWorld instance.
   *
   * @param actionsLog The StringBuilder used to log actions performed by this world.
   * @param worldUniqueCode An integer representing a unique code for this world.
   */
  public MockWorld(StringBuilder actionsLog, int worldUniqueCode) {
    this.log = actionsLog;
    this.uniqueCode = worldUniqueCode;
  }

  /**
   * Simulates drawing the world by appending a log entry.
   *
   * @throws IOException if an I/O error occurs while drawing the world.
   */
  public void drawWorld() throws IOException {
    this.log.append("Input Received for Drawing World\n");
  }

  /**
  * Creates a player in the mock world.
  *
  * @param playerNum The unique ID of the player.
  * @param playerName The name of the player.
  * @param maxItem The maximum number of items the player can carry.
  * @param curRoom The starting room of the player.
  * @return A string confirming player creation.
  * @throws IllegalArgumentException if any arguments are invalid.
  */
  public String createPlayer(int playerNum, String playerName, int maxItem, int curRoom) 
      throws IllegalArgumentException {
    this.log.append(String.format("Creating Player %d. %s, Max Items = %d, "
        + "first room = %d\n", playerNum, playerName, maxItem, curRoom));
    return "Player Created";
  }

  /**
   * Moves the specified player to a new room.
   *
   * @param player The ID of the player to be moved.
   * @param roomNum The target room number.
   * @return A string containing the unique code of the world.
   * @throws IllegalArgumentException if the player or room is invalid.
   */
  public String movePlayer(int player, int roomNum) throws IllegalArgumentException {
    this.log.append(String.format("Moving Player %d to room = %d\n", player, roomNum));
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Simulates a player picking up an item.
    *
    * @param playerNum The ID of the player.
    * @param itemNum The ID of the item.
    * @return A string containing the unique code of the world.
    * @throws IllegalArgumentException if the player or item is invalid.
    */
  public String pickItem(int playerNum, int itemNum) throws IllegalArgumentException {
    this.log.append(String.format("Player %d picking item %d\n", playerNum, itemNum));
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Simulates a player looking around the room.
    *
    * @param player The ID of the player.
    * @return A string containing the unique code of the world.
    * @throws IllegalArgumentException if the player is invalid.
    */
  public String lookAround(int player) throws IllegalArgumentException {
    this.log.append("Letting Player " + player + " Look Around\n");
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Retrieves the details of a specific player.
    *
    * @param specificPlayerNum The ID of the player.
    * @return A string containing the unique code of the world.
    * @throws IllegalArgumentException if the player is invalid.
    */
  public String getSpecificPlayerDetails(int specificPlayerNum) throws IllegalArgumentException {
    this.log.append("Displaying Player " + specificPlayerNum + " details\n");
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Retrieves the details of a specific room.
    *
    * @param specificRoomNum The room number.
    * @return A string containing the unique code of the world.
    * @throws IllegalArgumentException if the room number is invalid.
    */
  public String getSpecificRoomDetails(int specificRoomNum) throws IllegalArgumentException {
    this.log.append("Displaying details of Room " + specificRoomNum + "\n");
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Returns the current turn of the game.
    *
    * @return A list of strings representing the current turn.
    */
  public List<String> getTurn() {
    this.log.append("Returning the turn\n");
    List<String> returnStringList = new ArrayList<String>();
    returnStringList.add("1");
    returnStringList.add("player1");
    return returnStringList;
  }

  /**
    * Determines if the game is over.
    *
    * @return false, as this is a mock implementation.
    */
  public boolean gameOver() {
    return false;
  }

  /**
    * Retrieves the details of the game's target.
    *
    * @return A string containing the unique code of the world.
    */
  public String getTarget() {
    this.log.append("Target Details\n");
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Retrieves the names of all rooms.
    *
    * @return A string containing the unique code of the world.
    */
  public String getAllRoomNames() {
    this.log.append("Room Number and Names\n");
    return String.format("%s", this.uniqueCode);
  }

  /**
    * Retrieves the complete details of the spaces (rooms) in the world.
    *
    * @return A list of strings containing the complete space details.
    */
  public List<String> getCompleteSpaceDetails() {
    this.log.append("Complete Space Details\n");
    List<String> returnStringList = new ArrayList<String>();
    returnStringList.add(String.format("%s", this.uniqueCode));
    return returnStringList;
  }

  /**
    * Returns a string representation of the mock world.
    *
    * @return A string describing the world layout and details.
    */
  public String toString() {
    return "total rows = 30, total cols = 23, WorldName =  Doctor Lucky's Mansion,\r\n"
            + "roomCount = 21, itemCount = 22,\r\n"
            + "Target = Target =  Doctor Lucky, Health = 50, Current Location = 1,";
  }
}
