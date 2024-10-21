package controller;

import model.World;

/**
 * This is the Interface for the controller which has methods to give control of
  the model to the controller.
 */
public interface GameControllerInterface {

  /**
   * This method gives control of the created model to the controller.
   * @param worldSetup instance of the world model which the controller takes control of.
   * @throws IllegalArgumentException thrown when the correct model is not passed.
   */
  public void playGame(World worldSetup) throws IllegalArgumentException;
}
