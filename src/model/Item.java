package model;

/**
 * This is the Item interface. 
 * Items represent the weapons used in the game.
 * They are present in particular spaces.
 */
public interface Item {
  
  /**
   * Gives the room number in which the item is present.
   * The room number indexing starts from 1 and not 0.
   * @return returns the room number in which the Item is present
   */
  public int getItemRoom();
  
  /**
   * gives the integer value for the item number.
   * @return the item number for the item (starts from 1)
   */
  public int getItemNum();
  
  /**
   * Gives the damage amount associated with the item. 
   * @return returns the damage as integer which cannot be less than 1.
   */
  public int getItemDamage();

  /**
   * Gives the name of the particular item.
   * @return returns a string containing the name of the item.
   */
  public String getItemName();
  
  /**
   * Returns the details of the Item like item number, name, damage and in which room it is present.
   * @return returns the details as a string.
   */
  public String toString();


}
