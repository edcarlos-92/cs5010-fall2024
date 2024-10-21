package model;

import java.util.Objects;

/**
 * This is the implementation class for the Items. It is 
 extended from the Item Interface.
 * It gives details of the items like the name, which room it is 
  present in and how much damage it can cause to the target.
 */
public final class ItemList implements Item {
  
  private final int itemNum;
  private int itemRoom;
  private final String itemName;
  private final int itemDamage;
  
  /**
   * This is the constructor to initialize the parameters of the ItemList Class.
   * @param itemNumVal This is the item number value given to the object. 
    Items numbers start from 1.
   * @param itemRoomNum This is the room number in which item is present.
    room numbers are between 1 to total room count.
   * @param name This is the name given to the item.
   * @param damage value for the damage that the item can cause, which should be greater than 0.
   * @throws IllegalArgumentException Throws an exception if invalid parameters are passed.
   */
  public ItemList(int itemNumVal, int itemRoomNum, String name, int damage) 
      throws IllegalArgumentException {
    if (name == null || "".equals(name) || itemNumVal < 1 || itemRoomNum < 1 || damage <= 0) {
      throw new IllegalArgumentException("Invalid parameters passed.");
    }
    this.itemNum = itemNumVal;
    this.itemRoom = itemRoomNum;
    this.itemName = name;
    this.itemDamage = damage;
  }

  @Override
  public int getItemRoom() {
    return this.itemRoom;
  }

  @Override
  public int getItemNum() {
    return this.itemNum;
  }
  
  @Override
  public int getItemDamage() {
    return this.itemDamage;    
  }

  @Override
  public String getItemName() {
    return this.itemName;
  }
  
  public void updateRoom() {
    this.itemRoom = 0;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.itemNum);
    sb.append(". ");
    sb.append(this.itemName);
    sb.append(", Item Damage: ");
    sb.append(this.itemDamage);
    sb.append(", Item Room: ");
    sb.append(this.itemRoom);
    return sb.toString();    
  }
 
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ItemList)) {
      return false;
    }
    ItemList other = (ItemList) obj;
    return itemDamage == other.itemDamage && Objects.equals(itemName, other.itemName)
        && itemNum == other.itemNum && itemRoom == other.itemRoom;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(itemDamage, itemName, itemNum, itemRoom);
  }
  
}
