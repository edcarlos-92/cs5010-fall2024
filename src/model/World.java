package model;

import java.io.IOException;
import java.util.List;

/**
 * This is the Interface for the World.
 * It contains Spaces, Items, Target, Players.
 * It has methods to create new players, move them to different rooms,
  and pick items in turns.
 */
public interface World {

  /**
   * Creates a graphical representation of the world in the path /res from the current directory.
   * @throws IOException is thrown if there was a error while creating the image.
   */
  public void drawWorld() throws IOException;
  
  
  /**
   * Upon valid user input, a player is created and added to the world in the room specified.
   * @param playerNum index of the player.
   * @param playerName Name of the player which cannot be empty/null.
   * @param maxItem the maximum number of items that a player can carry.
   * @param curRoom room number (starting from 1) to which the player wants to enter first.
   * @return a String which returns "Player created" or appropriate message if there was an error.
   * @throws IllegalArgumentException thrown if valid parameters within required range not passed.
   */
  public String createPlayer(int playerNum, String playerName, int maxItem, int curRoom) 
      throws IllegalArgumentException;
  
  /**
   * This method moves the specified player to the specified room location.
   * @param player index of the player who needs to be moved.
   * @param roomNum room number (starts from 1) where the player needs to move.
   * @return a string stating if player was moved or any appropriate error.
   * @throws IllegalArgumentException thrown if valid parameters within required range not passed.
   */
  public String movePlayer(int player, int roomNum) throws IllegalArgumentException;
  
  /**
   * This method allows the player to pick the items in the space they are in.
   * @param playerNum index of the player who wants to pick item.
   * @param itemNum item number (starts from 1) of the item to be picked.
   * @return a string stating if the item was picked or any appropriate error message.
   * @throws IllegalArgumentException thrown if valid parameters within required range not passed.
   */
  public String pickItem(int playerNum, int itemNum) throws IllegalArgumentException;
  
  /**
   * Gives details of present and neighboring space along with what items are present in them.
   * @param player index of the player who wants to look around the specific space.
   * @return a String giving the details of the current space and neighboring space 
    along with items present in those spaces.
   * @throws IllegalArgumentException thrown if valid parameters within required range not passed.
   */
  public String lookAround(int player) throws IllegalArgumentException;

  /**
   * Gives the details of the player playing the game.
   * @param specificPlayerNum index of the player whose details are needed.
   * @return a String containing the player details like name, current location, items picked and 
    max items allowed to pick.
   * @throws IllegalArgumentException thrown if valid parameters within required range not passed.
   */
  public String getSpecificPlayerDetails(int specificPlayerNum) throws IllegalArgumentException;
  
  /**
   * Provides details of space which is needed to show the player.
    Does not show details of the neighbors or player present in that space.
   * @param specificRoomNum the room number (starts from 1) whose details are needed for the player.
   * @return a string containing space details for a player like room name and items available.
   * @throws IllegalArgumentException thrown if valid parameters within required range not passed.
   */
  public String getSpecificRoomDetails(int specificRoomNum) throws IllegalArgumentException;
    
  /**
   * This method can be used to find which player has the active turn to play.
   * @return  a list whose first element is the index of the player whose turn it is
    and second element is the name of the player whose turn it is.
   */
  public List<String> getTurn();
   
  /**
   * This method checks if the game is over.
   * @return true if game is over or return false if game is in progress.
   */
  public boolean gameOver();
  
  /**
   * Get the target details like name, health, and current location.
   * @return a string containing target details.
   */
  public String getTarget();
    
  /**
   * Gives the room number and names without any additional details of the space.
   * @return a string containing the room number followed by their names.
   */
  public String getAllRoomNames();

  /**
   * Gives complete details of a list of all spaces.
   * @return a List of strings, where each string contains the room number, name, items 
    in the room, neighbors of the space, players in the space.
   */
  public List<String> getCompleteSpaceDetails();

}
