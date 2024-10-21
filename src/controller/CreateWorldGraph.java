package controller;

import java.io.IOException;
import model.World;

/**
 * This class implements the CommandController interface and allows the controller
  to create the graphical representation of the world.
 * The image is stored in the /res directly from the current working directory.
 */
public class CreateWorldGraph implements CommandController {

  @Override
  public String go(World m) throws IllegalArgumentException, IOException {
    if (m == null || !(m instanceof World)) {
      throw new IllegalArgumentException("Not correct Model");
    }
    try {
      m.drawWorld();
      return "World Image Created to the res/ folder";
    } catch (IOException ioe) {
      throw new IOException("Unable to draw world image.");
    }
  }
}
