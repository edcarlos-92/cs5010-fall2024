package driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.ItemList;
import model.Room;
import model.World;
import model.WorldSetup;

/**
 * Main class to drive the board game application.
 * It sets up the game world based on the provided input file,
 * displays room neighbors, items in specific rooms, and manages the target's movement.
 */
public class Main {
  static World worldSetup; // Static variable to hold the game world setup.

  /**
   * Main method to execute the board game application.
   * 
   * @param args Command line arguments, where args[0] is the file path for the input data.
   * @throws Throwable If an error occurs during file reading or processing.
   */
  public static void main(String[] args) throws FileNotFoundException {
    String filePath = args[0]; // Get the file path from command line arguments.
    StringBuffer s = new StringBuffer(); // Buffer to hold the contents of the file.

    String[] element; // Array to hold elements parsed from the file.
    int roomCount; // Variable to count the number of rooms.

    try {
      element = null; // Initialize element to null.

      // Read the contents of the file
      try (FileReader readableFile = new FileReader(filePath)) {
        while ((roomCount = readableFile.read()) != -1) { // Read until end of file
          s.append((char) roomCount); // Append each character to the StringBuffer
        }
      } 
    } catch (IOException var18) {
      throw new FileNotFoundException("File Path is not correct. Please enter "
          + "correct path and try again.");
    }

    // Convert the StringBuffer content to a string and set up the world
    String fileContent = s.toString();
    
    // Create a new WorldSetup object with the file content
    worldSetup = new WorldSetup(fileContent); 

    // Display neighbors for specific rooms
    System.out.println();
    System.out.println("**Neighbors Test");
    System.out.println("Displaying the Neighbors of Room 2. Result is a list of "
        + "the Neighbor's Room Number. Room Numbers start from 1.");
    // Get neighbors of Room 2
    System.out.println(((Room) worldSetup.getRooms().get(2)).getNeighborRoomNum());
    System.out.println();
    
    System.out.println("Displaying the Neighbors of Room 10. Result is a list of "
        + "the Neighbor's Room Number. Room Numbers start from 1.");
    // Get neighbors of Room 10
    System.out.println(((Room) worldSetup.getRooms().get(10)).getNeighborRoomNum()); 
    System.out.println();
    
    System.out.println("The program also returns the object references for the "
        + "neighbors.");
    System.out.println("Displaying the List of Neighbor Objects for Room 2.");
    // Get neighbor objects of Room 2
    System.out.println(((Room) worldSetup.getRooms().get(2)).getNeighborObject()); 
    System.out.println();
    
    System.out.println("Displaying the List of Neighbor Objects for Room 10.");
    // Get neighbor objects of Room 10
    System.out.println(((Room) worldSetup.getRooms().get(10)).getNeighborObject()); 
    System.out.println();
    
    System.out.println();
    System.out.println("**List of Items in Specific Space.");
    System.out.println("Program returns the list of objects of type items that "
        + "are present in the particular Space.");
    
    // Display items in specific rooms
    System.out.println("Displaying List of objects of type Items in Space 5.");
    System.out.println(((Room) worldSetup.getRooms().get(5)).getItems()); // Get items in Space 5
    System.out.println("You will notice Space 5 (which is represented as index 4 "
        + "in the input file) has no neighbors");
    System.out.println();
    
    System.out.println("Displaying List of objects of type Items in Space 8.");
    System.out.println(((Room) worldSetup.getRooms().get(8)).getItems()); // Get items in Space 8
    System.out.println();
    
    System.out.println("Displaying List of objects of type Items in Space 15.");
    System.out.println(((Room) worldSetup.getRooms().get(15)).getItems()); // Get items in Space 15
    System.out.println();
    
    System.out.println();
    System.out.println("**Room Details along with Neighbor room numbers and "
        + "items list present in that room.");
    element = worldSetup.getWorldSpecs().split("\\s+"); // Split world specs by whitespace
    String[] remove = element[15].split(","); // Split room count information
    roomCount = Integer.parseInt(remove[0]); // Parse room count as an integer

    // Display details of each room
    for (int i = 1; i <= roomCount; ++i) {
      // Print each room's details
      System.out.println(((Room) worldSetup.getRooms().get(i)).toString()); 
    }

    System.out.println();
    System.out.println();
    System.out.println("**Item Details.");
    String[] remove2 = element[18].split(","); // Split item count information
    int itemCount = Integer.parseInt(remove2[0]); // Parse item count as an integer

    // Display details of each item
    for (int i = 1; i <= itemCount; ++i) {
      // Print each item's details
      System.out.println(((ItemList) worldSetup.getItems().get(i)).toString()); 
    }

    System.out.println();
    System.out.println();
    System.out.println("**Displaying Target Details.");
    System.out.println("Initial State of Target");
    System.out.println(worldSetup.getTarget()); // Display the initial state of the target
    System.out.println();
    
    System.out.println("**Move Target");
    worldSetup.getTarget().move(); // Move the target once
    System.out.println("New State of Target:");
    System.out.println(worldSetup.getTarget()); // Display the new state of the target
    System.out.println();
    
    // Move the target multiple times and display its state
    System.out.println("**Called the Move Method of Target 5 more times");
    for (int i = 0; i < 5; i++) {
      worldSetup.getTarget().move();
    }
    System.out.println("New State of Target:");
    System.out.println(worldSetup.getTarget()); // Display the new state of the target
    System.out.println();
    
    // Move the target 14 more times and display its state
    System.out.println("**Called the Move Method of Target 14 more times");
    for (int i = 0; i < 14; i++) {
      worldSetup.getTarget().move();
    }
    System.out.println("New State of Target:");
    System.out.println(worldSetup.getTarget()); // Display the new state of the target
    System.out.println();
    
    // Move the target once and display its state
    System.out.println("**Called the Move Method of Target once");
    worldSetup.getTarget().move();
    System.out.println(worldSetup.getTarget()); // Display the new state of the target
    System.out.println();
    
    // Demonstrate the target can reset to Room 1
    System.out.println("We can see location can reset to Room 1. Calling the move"
        + " method 4 more times");
    for (int i = 0; i < 4; i++) {
      worldSetup.getTarget().move();
    }
    System.out.println("New State of Target:");
    System.out.println(worldSetup.getTarget()); // Display the new state of the target
    System.out.println();
    System.out.println();
    
    // Final message regarding graphical representation of the world
    System.out.println("The Graphical Representation of the World is available "
        + "as an image in /res folder under the name world.jpeg.");
  }
}
