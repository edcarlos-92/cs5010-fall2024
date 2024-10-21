package mock;

/**
 * This class simulates a space in the game. It provides 
 * methods to retrieve specific player details.
 */
public class MockSpace {
    
  // Constructor for initializing the MockSpace object.
  public MockSpace(StringBuilder log, int uniqueCode) {
    super();  // No specific action here, but could be extended.
  }

  /**
   * Retrieves the details of a specific player.
   * 
   * @param specificPlayerNum The number identifying the specific 
   *        player whose details are being requested.
   * @return A string containing the details of the specified player. 
   *         Currently returns a test string.
   * @throws IllegalArgumentException If the player number is invalid 
   *         (this exception is not actually thrown here, but reserved for future logic).
   */
  public String getSpecificPlayerDetails(int specificPlayerNum) throws IllegalArgumentException {
    // Example test string.
    return "test string test string test string test string some test string 12\n";  
  }
}
