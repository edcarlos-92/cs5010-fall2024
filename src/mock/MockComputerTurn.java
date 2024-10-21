package mock;

import java.util.ArrayList;
import java.util.List;

/**
 * This class simulates the computer's turn in a game. 
 * It provides methods to retrieve the turn details and check if the game is over.
 */
public class MockComputerTurn {
    
    
  // A StringBuilder used for logging actions and events.
  private StringBuilder log;

  //Tracks the number of turns the computer has taken.
  private int turn = 0;
    
  /**
   * Constructs a new MockComputerTurn instance.
   * 
   * @param actioLog        The StringBuilder used to log actions.
   * @param uniqueCode A unique code to identify the game state.
   */
  public MockComputerTurn(StringBuilder actioLog, int uniqueCode) {
    this.log = actioLog;  // Initialize the log field
    // The uniqueCode can be used here if needed
  }

  /**
   * Retrieves the details of the current turn.
   * 
   * @return A list of strings representing the turn information. 
   *         The first element is the turn number,and the second element
   *         is "computer" to indicate that it's the computer's turn.
   */
  public List<String> getTurn() {
    log.append("Returning the turn\n");  // Log the action
    List<String> returnStringList = new ArrayList<>();
    returnStringList.add("0");  // Adds the turn number as "0"
    returnStringList.add("computer");  // Adds "computer" to indicate whose turn it is
    return returnStringList;
  }

    
  
    
  /**
   * Checks if the game is over.
   * 
   * @return {@code true} if the game is over; {@code false} if the game is still ongoing.
   *         The game is considered over after the first turn.
   */
  
  public boolean gameOver() {
    
    if (this.turn == 0) {
      ++this.turn;  // Increment the turn counter
      return false;  // Game is not over after the first turn
    } else {
      return true;  // Game is over after the first turn
    }
  }
}
