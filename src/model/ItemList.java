package model;

import java.util.Objects;

/**
 * Represents an item in the game world with properties such as 
 * item number, room location, name, and damage value.
 */
public final class ItemList implements Item {
  private final int itemNum;
  private int itemRoom;
  private final String itemName;
  private final int itemDamage;

  /**
   * Constructs an ItemList with specified properties.
   *
   * @param itemNumSpec the unique identifier for the item
   * @param itemRoomSpec the room number where the item is located
   * @param itemNameSpec the name of the item
   * @param itemDamageSpec the damage value of the item; must be greater than 0
   * @throws IllegalArgumentException if damage is less than or equal to 0
   */
  public ItemList(int itemNumSpec, int itemRoomSpec, String itemNameSpec, int itemDamageSpec) 
        throws IllegalArgumentException {
    if (itemDamageSpec <= 0) {
      throw new IllegalArgumentException("Damage cannot be 0 or negative.");
    } else {
      this.itemNum = itemNumSpec;
      this.itemRoom = itemRoomSpec;
      this.itemName = itemNameSpec;
      this.itemDamage = itemDamageSpec;
    }
  }

  /**
   * Gets the room number where the item is located.
   *
   * @return the room number as an integer
   */
  public int getItemRoom() {
    return this.itemRoom;
  }

  /**
   * Gets the damage value of the item.
   *
   * @return the damage value as an integer
   */
  public int getItemDamage() {
    return this.itemDamage;
  }

  /**
   * Gets the name of the item.
   *
   * @return the name of the item as a String
   */
  public String getItemName() {
    return this.itemName;
  }

  /**
   * Returns a string representation of the item.
   *
   * @return a string describing the item
   */
  public String toString() {
    return String.format("Item Num = %d, Item Room = %d, Item Name = %s, Item Damage = %d",
              this.itemNum, this.itemRoom, this.itemName, this.itemDamage);
  }

  /**
   * Compares this item to another object for equality.
   *
   * @param obj the object to compare to
   * @return true if the objects are equal; false otherwise
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof ItemList)) {
      return false;
    } else {
      ItemList other = (ItemList) obj;
      return Objects.hash(new Object[]{this.itemNum, this.itemRoom, 
          this.itemName, this.itemDamage}) 
              == Objects.hash(new Object[]{other.itemNum, other.itemRoom, 
                  other.itemName, other.itemDamage});
    }
  }

  /**
   * Returns a hash code for this item.
   *
   * @return the hash code as an integer
   */
  public int hashCode() {
    return Objects.hash(new Object[]{this.itemNum, this.itemRoom, this.itemName, this.itemDamage});
  }
}
