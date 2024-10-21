package controller;

import model.World;

/**
 * This class implements the Command Controller interface to display the player details 
  like name, room that the player is present in, and the items that they carry. 
 */
public class DisplayPlayerInfo implements CommandController {
  
  private final int playerNum;
  
  /**
   * This is the constructor for the display player information command.
   * @param gamePlayerNum player number of the player whose details need to be displayed.
   * @throws IllegalArgumentException thrown if invalid player number is passed.
   */
  public DisplayPlayerInfo(int gamePlayerNum) throws IllegalArgumentException {
    if (gamePlayerNum < 0) {
      throw new IllegalArgumentException("Player does not exist");
    }
    this.playerNum = gamePlayerNum;
  }

  @Override 
  public String go(World m) throws IllegalArgumentException {
    if (m == null || !(m instanceof World)) {
      throw new IllegalArgumentException("Not correct Model");
    }
    return m.getSpecificPlayerDetails(this.playerNum);
  }
}
