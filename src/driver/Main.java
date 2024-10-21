package driver;

import controller.GameController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.World;
import model.WorldSetup;

/**
 * This is the Driver class used to run and test the model. 
 * It creates the model and the controller and gives the control to the controller.
 * It has the code for example runs to check the behaviour of the model. 
 */
public class Main {
  
  /** 
   * This is the main method from where the program starts execution.
   * @param args accepts 1 command line argument specifying the path to the file
    containing the world specifications.
   * @throws FileNotFoundException If the specified file is not found, this exception is thrown.
   */
  public static void main(String[] args) {
    
    Path  filePath = Paths.get(args[0]);
    
    int maxTurn = Integer.parseInt(args[1]);
    
    //String  filePath = "C:/Users/EDCARLOS/eclipse-workspace/cs5010/
    //m1-the-world/res/dr_lucky_in_marios_castle.txt";
    
    //C:\Users\EDCARLOS\eclipse-workspace\cs5010\m1-the-world\res\mansion.txt
    String fileContent = "";
    try {
      fileContent = Files.readString(filePath, StandardCharsets.US_ASCII);
    } catch (IOException e) {
      System.out.println("File Path is not correct. "
          + "Please enter correct path and try again.");
      return;
    }
    
    World worldSetup = null;
    Readable fileInput = new StringReader(fileContent);
    try {
      worldSetup = new WorldSetup(fileInput, maxTurn);
    } catch (IllegalArgumentException iae) {
      System.out.println("Illegal Argument/File Passed.");
      return;
    }
    

    // Commands for Example Run

    /*
    System.out.println(worldSetup.toString());
    System.out.println();
    System.out.println("Example Run for Model Requirements:");
    System.out.println("Display Space Information in Required Format");
    System.out.println("Player created and added to Room 1:");
    worldSetup.createPlayer(10, "Carlos", 2, 1);
    System.out.println(worldSetup.getCompleteSpaceDetails().get(0));
    
    System.out.println();
    System.out.println("**Space Details updated after Item is picked");
    worldSetup.pickItem(10, 13);
    System.out.println("Player 10 picked Item 13 from room 1");
    System.out.println("Player details: " + worldSetup.getSpecificPlayerDetails(10));
    System.out.println();
    System.out.println("**Space Details Updated after Player is moved");
    worldSetup.movePlayer(10, 20);
    System.out.println("Player 10 moved to room 20");
    System.out.println("Player details: " + worldSetup.getSpecificPlayerDetails(10));
    System.out.println("Player Space Details: " + worldSetup.getCompleteSpaceDetails().get(19));
    
    System.out.println();
    System.out.println("**Look Around Details:");
    System.out.println(worldSetup.lookAround(10));
    */
    
    
    

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    List<Integer> rand = new ArrayList<Integer>();
    try {
      new GameController(input, output, rand).playGame(worldSetup);
    } catch (IllegalArgumentException iae) {
      System.out.println("Incorrect Inputs given to play game.");
      return;
    }
        
  }   
}