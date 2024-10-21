package controller;

import model.World;

/**
 * This class implements the CommandController interface and allows the controller
  to create a player. 
 * The player can be user controller to computer controlled.
 */
public class CreatePlayer implements CommandController {
  
  private final String playerName;
  private final int playerNum;
  private int currentLocation;
  private final int maxItem;
  
  /**
   * This is the constructor for the command which creates the user controlled 
    and computer controlled player.
   * @param gamePlayerNum player number of the player to be created.
   * @param gamePlayerName player name for the player to be created.
   * @param playerMaxItem maximum number of items that the player is allowed to carry.
   * @param cur room number of the room that the player wants to enter first.
   * @throws IllegalArgumentException thrown if invalid parameters are passed.
   */
  public CreatePlayer(int gamePlayerNum, String gamePlayerName, int playerMaxItem, int cur) 
      throws IllegalArgumentException {
    if (gamePlayerNum < 0 || gamePlayerName == null || "".equals(gamePlayerName) 
        || playerMaxItem < 0 || cur < 0) {
      throw new IllegalArgumentException("Player Details are not correct");
    }
    this.playerName = gamePlayerName;
    this.playerNum = gamePlayerNum;
    this.maxItem = playerMaxItem;
    this.currentLocation = cur;
  }
 
  @Override
  public String go(World m) throws IllegalArgumentException {
    if (m == null || !(m instanceof World)) {
      throw new IllegalArgumentException("Not correct Model");
    }
    return m.createPlayer(this.playerNum, this.playerName, this.maxItem, this.currentLocation);
  }
}
