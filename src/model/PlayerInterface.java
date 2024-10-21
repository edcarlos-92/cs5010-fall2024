package model;

/**
 * This is the Interface for the Player who can be User controlled or System controlled.
 * It provides details like the Player's current location, Items picked and has methods 
  to pick up new items, move to different rooms and look around the room.
 */
public interface PlayerInterface {
  
  /**
   * Gives the name of the Player.
   * @return returns a String containing the Player's name.
   */
  public String getPlayerName();
  
  /**
   * Gives the Current location in terms of the room number of the player.
   * @return returns the integer value of the room number (room numbers start from 1)
    where the player is present. 
   */
  public int getLocation();
  
  /**
   * This method is used to move the Player to the specified room.
   * @param roomNum This is the room number to which the player needs to be moved.
   * @throws IllegalArgumentException if room is not valid.
   */
  public void move(Room roomNum) throws IllegalArgumentException;
  
  /**
   * This method is used to add item to the itemsList that the player picked.
   * @param itemNum This is the item number that the player wants to pick.
   * @throws IllegalArgumentException when an invalid item is passed.
   */
  public void pickItem(ItemList itemNum) throws IllegalArgumentException;
  
  /**
   * Gives details of the space in which player is present along with neighboring space details.
   * @return a string containing details of player's current space and items present in it. Also
    gives details of the neighboring spaces along with items present in neighboring spaces.
   */
  public String lookAround();
  
  /**
   * Tells the maximum count of items that a player is allowed to carry.
   * @return an integer value to represent the maximum number for items allowed to carry.
   */
  public int getMaxItems();

  /**
   * Tells the number of items that player currently has.
   * @return an integer value for number of items that player has.
   */
  public int getItemCount();
  
}