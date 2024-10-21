package controller;

import java.io.IOException;
import model.World;

/**
 * The command controller interface for the Kill Dr. Lucky game. 
 * The functions here are designed to execute the commands that the user has requested'
  the controller to execute. This used this command design pattern.
 */
public interface CommandController {
  /**
   * Starting point for the controller. Game starts from here.
   * program gives control to the controller with this function.
   * @param m the World model to use
   * @return a string which contains the requested command output.
   * @throws IllegalArgumentException thrown if invalid parameters are passed.
   * @throws IOException thrown when world graph could not be created.
   * @throws IllegalArgumentException thrown when correct parameters are not passed.
   */
  public String go(World m) throws IllegalArgumentException, IOException;
}
