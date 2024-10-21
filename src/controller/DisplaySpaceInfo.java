package controller;

import model.World;

/**
 * This class implements the CommandController interface to display the details of 
  the space in which the player is current located. It gives the space number, name and 
  the items available in the space.
 */
public class DisplaySpaceInfo implements CommandController {

  private final int playerNum;
  
  /**
   * This is the constructor for the display space information command to display the 
    details of the space in which the player is present.
   * @param gamePlayerNum player number whose space details are needed for display.
   * @throws IllegalArgumentException thrown if invalid player number is passed.
   */
  public DisplaySpaceInfo(int gamePlayerNum) throws IllegalArgumentException {
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
    String[] element = m.getSpecificPlayerDetails(this.playerNum).split("\\s+");
    return String.format("\n%s", m.getSpecificRoomDetails(Integer.parseInt(element[11])));
  }
}
