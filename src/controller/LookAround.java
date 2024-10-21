package controller;

import model.World;

/**
 * This class implements the CommandController interface to display the details of 
  the current space and the neighboring spaces along with the items present in them.
 * It gives the space number, name and the items available in the current space. It also
  gives the neighboring space details like its number and name along with items present in them.
 */
public class LookAround implements CommandController {

  private final int playerNum;
  
  /**
   * This is the constructor for the LookAround command to display neighboring details
    for the space in which the player is currently present.
   * @param gamePlayerNum player number of the player who wants to look around.
   * @throws IllegalArgumentException thrown if invalid player number is passed.
   */
  public LookAround(int gamePlayerNum) throws IllegalArgumentException {
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
    return String.format("\n%s%s\n", m.lookAround(this.playerNum), m.getTarget());
  }
}