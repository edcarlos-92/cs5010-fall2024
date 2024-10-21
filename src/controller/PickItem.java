package controller;

import model.World;

/**
 * This class implements the CommandController interface which allows the player
  to pick an item. 
 * An item can be picked only if it is present in the space that the player is currently present in.
 */
public class PickItem implements CommandController {

  private final int playerNum;
  private final int itemNum;
  
  /**
   * This is the constructor for the pick item command which allows the player
    to pick an item which is in the space they are currently in.
   * @param gamePlayerNum player number of the player who wants to pick an item.
   * @param playerItemNum item number of the item which the player wants to pick.
   * @throws IllegalArgumentException thrown if invalid parameters are passed.
   */
  public PickItem(int gamePlayerNum, int playerItemNum) throws IllegalArgumentException {
    if (gamePlayerNum < 0 || playerItemNum < 1) {
      throw new IllegalArgumentException("Not a valid item Number");
    }
    this.playerNum = gamePlayerNum;
    this.itemNum = playerItemNum;
  }
 
  @Override
  public String go(World m) throws IllegalArgumentException {
    if (m == null || !(m instanceof World)) {
      throw new IllegalArgumentException("Not correct Model");
    }
    String[] element = m.toString().split("\\s+");
    String[] remove = element[18].split(",");
    int itemCount = Integer.parseInt(remove[0]);
    if (this.itemNum > itemCount) {
      return "Item number not in correct range";
    }
    return m.pickItem(this.playerNum, this.itemNum);
  }
}
