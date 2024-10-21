package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * This is World Implementation Class which extends from the World Interface.
 * It creates the Target object, Spaces Objects and Items Objects.
 * It also creates the graphical representation of the world.
 */
public final class WorldSetup implements World {

  private final Scanner scan;
  private final int totalRow;
  private final int totalCol;
  private String world;
  private final int roomCount;
  private final int itemCount;
  private String[][] rooms;
  private int health;
  private String target;
  private final int gameMaxTurn;
  private int turn;
  private int turnCount;
  private Target targetObj;
  private final Map<Integer, ItemList> itemObjList;  
  private final Map<Integer, Room> roomObjList;
  private final Map<Integer, List<Room>> neighborTable;
  private final Map<Integer, Player> playerList;
  

  /**
   * This is the Constructor for the WorldSetup Class.
   * It initializes takes the file content as string to derive the
    required parameter values. These values are used to create the Spaces, Items and Target.
   * @param fileInput gives the file content in a Readable format.
   * @param maxTurn The integer value for the maximum number of turns for which the game runs.
    Should be greater than 0.
   * @throws IllegalArgumentException If the file content does not have values that 
    satisfy the requirements or max turn is less than 1, exceptions are thrown.
   */
  public WorldSetup(Readable fileInput, int maxTurn) throws IllegalArgumentException {
    if (fileInput == null) {
      throw new IllegalArgumentException("World creation input file content is not correct.");
    }
    if (maxTurn < 1) {
      throw new IllegalArgumentException("To paly the game Max turn should be atleast 1.");
    }
    this.turn = 1;
    this.gameMaxTurn = maxTurn;
    this.turnCount = 1;
    this.itemObjList = new HashMap<Integer, ItemList>();  
    this.roomObjList = new HashMap<Integer, Room>();
    this.neighborTable = new HashMap<Integer, List<Room>>();
    this.playerList = new HashMap<Integer, Player>();
    this.scan = new Scanner(fileInput);
        
    String fileContent = "";
    scan.useDelimiter("");
    while (this.scan.hasNext()) {
      fileContent = fileContent + scan.next();
    }
    String[] lines = fileContent.split("\n");
    String[] element = null;
    
    element = lines[0].strip().split("\\s+");
    
    //Get World Details. 
    this.world = "";
    this.totalRow = Integer.parseInt(element[0].strip());
    this.totalCol = Integer.parseInt(element[1].strip());
    if (this.totalRow <= 0 || this.totalCol <= 0) {
      throw new IllegalArgumentException("Enter correct dimensions for World.");
    }
    for (int j = 2; j < element.length; j++) {
      this.world = this.world + " " + element[j];
    }
        
    //Get Target Details. 
    try {
      element = lines[1].strip().split("\\s+");
      this.health = Integer.parseInt(element[0].strip());
  
      if (this.health <= 0) {
        throw new IllegalArgumentException("Initial Health of Target must be a Positive value.");
      }
      this.target = "";
      for (int j = 1; j < element.length; j++) {
        this.target = this.target + " " + element[j];
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Health should be an integer");
    }

    //Get Room Details.
    try { 
      Integer.parseInt(lines[2].strip());
    } catch (NumberFormatException e)  { 
      throw new IllegalArgumentException("Number of Spaces should be an Integer"); 
    } 
    
    this.roomCount = Integer.parseInt(lines[2].strip());
    if (this.roomCount <= 1) {
      throw new IllegalArgumentException("There should atleast be two rooms present.");
    }
   
    //Get Item Details. 
    try { 
      Integer.parseInt(lines[3 + this.roomCount].strip());
    } catch (NumberFormatException e) {
      System.out.println("Number of Items should be an Integer. "
          + "Or check if correct Number of Spaces are provided.");
    }

    this.itemCount = Integer.parseInt(lines[3 + this.roomCount].strip());
    if (this.itemCount < 1) {
      throw new IllegalArgumentException("There should atleast be 1 item.");
    }
     
    createWorldComponents(lines);
  }
  
 
  //
  // Private Methods of the Class - exception handling needed?
  //
  private void createWorldComponents(String[] lines) {
    //creating 2D array for rooms
    String[] element = null;
    this.rooms = new String[this.roomCount][6];
    try {
      for (int i = 3; i < 3 + this.roomCount; i++) {
        element = null;
        element = lines[i].strip().split("\\s+");
        rooms[i - 3][0] = element[0].strip();
        rooms[i - 3][1] = element[1].strip();
        rooms[i - 3][2] = element[2].strip();
        rooms[i - 3][3] = element[3].strip();
        if (Integer.parseInt(rooms[i - 3][0]) < 0 || Integer.parseInt(rooms[i - 3][1]) < 0 
            || Integer.parseInt(rooms[i - 3][2]) < 0 || Integer.parseInt(rooms[i - 3][3]) < 0 
            || Integer.parseInt(rooms[i - 3][0]) > this.totalRow 
            || Integer.parseInt(rooms[i - 3][1]) > this.totalCol
            || Integer.parseInt(rooms[i - 3][2]) > this.totalRow 
            || Integer.parseInt(rooms[i - 3][3]) > this.totalCol) {
          throw new IllegalArgumentException("Room Column or Row value cannot be negative."
              + " They can also not be greater then the room dimensions.");
        }
        String roomName = "";
        for (int j = 4; j < element.length; j++) {
          roomName = roomName + " " + element[j];
        }
        rooms[i - 3][4] = roomName.strip();
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Correct number of Spaces are not given"); 
    }
    
    //creating 2D array for Items 
    String[][] items = new String[this.itemCount][3];
    try {
      for (int i = 4 + this.roomCount; i < 4 + this.roomCount + this.itemCount; i++) {
        element = null;
        element = lines[i].strip().split("\\s+");
        items[i - 4 - this.roomCount][0] = element[0].strip();
        if (Integer.parseInt(items[i - 4 - this.roomCount][0]) < 0 
            || Integer.parseInt(items[i - 4 - this.roomCount][0]) > this.roomCount - 1) {
          throw new IllegalArgumentException("Item is set to a room that does not exist.");
        }
        items[i - 4 - this.roomCount][1] = element[1].strip();
        if (Integer.parseInt(items[i - 4 - this.roomCount][1]) < 1) {
          throw new IllegalArgumentException("Damage of an item should be positive.");
        }
        String itemName = "";
        for (int j = 2; j < element.length; j++) {
          itemName = itemName + " " + element[j];
        }
        items[i - 4 - this.roomCount][2] = itemName.strip();
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Correct number of Items are not given"); 
    }
    
    // call function to create Target
    createTarget();

    //create items
    createItems(items);
 
    //create rooms
    createRooms(rooms, items);
         
    //check neighbors of rooms
    for (int i = 1; i <= this.roomCount; i++) {
      List<Room> neighborList = new ArrayList<Room>();
      this.neighborTable.put(i, neighborList);
    }
    for (int i = 1; i <= this.roomCount; i++) {
      findNeighbor(i, rooms);      
    }
    
    // set the neighbors to the rooms
    setNeighbors(neighborTable);

  }
  
  private void createTarget() {
    this.targetObj = new Target(this.target, this.health, 1, this.roomCount);
  }
  
  private void createItems(String[][] items) {
    
    for (int i = 1; i <= this.itemCount; i++) {
      this.itemObjList.put(i, new ItemList(i, Integer.parseInt(items[i - 1][0]) + 1,
          items[i - 1][2], Integer.parseInt(items[i - 1][1])));
    }
  }
  
  private void setNeighbors(Map<Integer, List<Room>> neighborTable2) {
    for (int i = 1; i <= this.roomCount; i++) {
      roomObjList.get(i).setNeighbor(neighborTable2.get(i));
    }
  }
  
  
  private void createRooms(String[][] roomsObj, String[][] items) {
    for (int i = 1; i <= this.roomCount; i++) {
      List<ItemList> itemList = new ArrayList<ItemList>();
      for (int j = 1; j <= this.itemCount; j++) {
        if (Integer.parseInt(items[j - 1][0]) + 1 == i) {
          itemList.add(this.itemObjList.get(j));
        }
      }
      int[] coordinates = new int[] {Integer.parseInt(roomsObj[i - 1][1]), 
          Integer.parseInt(roomsObj[i - 1][0]), Integer.parseInt(roomsObj[i - 1][3]) + 1,
          Integer.parseInt(roomsObj[i - 1][2]) + 1};
      this.roomObjList.put(i, new Room(i, roomsObj[i - 1][4], itemList, coordinates));

    }   
  }
  
  private void findNeighbor(int roomNum, String[][] roomsObj) {
    //set coordinate 1
    int[] coordinate1 = new int[] {Integer.parseInt(roomsObj[roomNum - 1][0]), 
        Integer.parseInt(roomsObj[roomNum - 1][1]), Integer.parseInt(roomsObj[roomNum - 1][2])
        + 1, Integer.parseInt(roomsObj[roomNum - 1][3]) + 1}; 
    
    
    //set coordinate 2
    for (int k = 0; k < this.roomCount; k++) {
      if (k != roomNum - 1) {
        int [] coordinate2 = new int[] {Integer.parseInt(roomsObj[k][0]), 
          Integer.parseInt(roomsObj[k][1]), Integer.parseInt(roomsObj[k][2]) 
          + 1, Integer.parseInt(roomsObj[k][3]) + 1};
      
        int[][] quad1 = new int[][] {{coordinate1[0], coordinate1[1]},
          {coordinate1[2], coordinate1[1]}, 
          {coordinate1[2], coordinate1[3]}, {coordinate1[0], coordinate1[3]}};
        int[][] quad2 = new int[][] {{coordinate2[0], coordinate2[1]}, 
          {coordinate2[2], coordinate2[1]}, 
          {coordinate2[2], coordinate2[3]}, {coordinate2[0], coordinate2[3]}};
      
        //call fns
        // for every side on j, check if point i lies on it.
        for (int i = 0; i < 4; i++) {
          for (int j = 0; j < 4; j++) {
            if (j < 2) {
              if (checkWithinLine(quad1[j % 4][0], quad1[j % 4][1], quad1[(j + 1) % 4][0],
                  quad1[(j + 1) % 4][1], quad2[i][0], quad2[i][1])) {
                                
                if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                  neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                }
                if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                  neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                }              
              } else if (i == 3) {
                //check corner case where the two rectangles have only 1 point in common
                if (checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], quad1[(j + 1) % 4][0],
                    quad1[(j + 1) % 4][1], quad2[0][0], quad2[0][1], quad2[i][0], quad2[i][1]) 
                      || checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], 
                          quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], quad2[i][0], 
                          quad2[i][1], quad2[i - 1][0], quad2[i - 1][1])) {
                                        
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }
                }
              } else if (i == 0) {
                if (checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], 
                    quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], quad2[i][0],
                    quad2[i][1], quad2[3][0], quad2[3][1]) 
                    || checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], 
                        quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], quad2[i][0], 
                        quad2[i][1], quad2[i + 1][0], quad2[i + 1][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {  
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }                   
                }
              } else if (i == 1) {
                if (checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], quad1[(j + 1) % 4][0],
                    quad1[(j + 1) % 4][1], quad2[i - 1][0], quad2[i - 1][1], quad2[i][0], 
                    quad2[i][1]) || checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], 
                        quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], quad2[i][0], 
                        quad2[i][1], quad2[i + 1][0], quad2[i + 1][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }
                }
              } else if (i == 2) {
                if (checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], quad1[(j + 1) % 4][0],
                    quad1[(j + 1) % 4][1], quad2[i - 1][0], quad2[i - 1][1], quad2[i][0], 
                    quad2[i][1]) || checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1],
                        quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], quad2[i + 1][0], 
                        quad2[i + 1][1], quad2[i][0], quad2[i][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  } 
                }
              }
            } else {
              if (checkWithinLine(quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], 
                  quad1[j % 4][0], quad1[j % 4][1], quad2[i][0], quad2[i][1])) {
                if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                  neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                }
                if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                  neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                }
              } else if (i == 3) {
                //check corner case
                if (checkTwoCommonPoints(quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], 
                    quad1[j % 4][0], quad1[j % 4][1], quad2[0][0], quad2[0][1], quad2[i][0],
                    quad2[i][1]) || checkTwoCommonPoints(quad1[(j + 1) % 4][0], 
                        quad1[(j + 1) % 4][1], quad1[j % 4][0], quad1[j % 4][1], 
                        quad2[i][0], quad2[i][1], quad2[i - 1][0], quad2[i - 1][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }
                }
              } else if (i == 0) {
                if (checkTwoCommonPoints(quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], 
                    quad1[j % 4][0], quad1[j % 4][1], quad2[i][0], quad2[i][1], quad2[3][0],
                    quad2[3][1]) || checkTwoCommonPoints(quad1[(j + 1) % 4][0], 
                        quad1[(j + 1) % 4][1], quad1[j % 4][0], quad1[j % 4][1], quad2[i][0],
                        quad2[i][1], quad2[i + 1][0], quad2[i + 1][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }
                }
              } else if (i == 1) {
                if (checkTwoCommonPoints(quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], 
                    quad1[j % 4][0], quad1[j % 4][1], quad2[i - 1][0], quad2[i - 1][1], 
                    quad2[i][0], quad2[i][1]) || checkTwoCommonPoints(quad1[(j + 1) % 4][0],
                        quad1[(j + 1) % 4][1], quad1[j % 4][0], quad1[j % 4][1], 
                        quad2[i][0], quad2[i][1], quad2[i + 1][0], quad2[i + 1][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }
                }
              } else if (i == 2) {
                if (checkTwoCommonPoints(quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], 
                    quad1[j % 4][0], quad1[j % 4][1], quad2[i - 1][0], quad2[i - 1][1], 
                    quad2[i][0], quad2[i][1]) || checkTwoCommonPoints(quad1[(j + 1) % 4][0],
                        quad1[(j + 1) % 4][1], quad1[j % 4][0], quad1[j % 4][1], quad2[i + 1][0],
                        quad2[i + 1][1], quad2[i][0], quad2[i][1])) {
                  if (!neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  } 
                }
              }
            }
          }
        }
      }
    }
  }

  private boolean checkTwoCommonPoints(int x1, int y1, int x2, int y2,
      int x3, int y3, int x4, int y4) {
    if ((x1 == x3) && (x2 == x4) && (y1 == y3) && (y2 == y4)) {
      return true;
    }
    return false;  
  }
  
  private boolean checkWithinLine(int x1, int y1, int x2, int y2, int x, int y) {
    if ((x == x1) && (x == x2) && (y > y1) && (y < y2)) {
      return true;
    } else if ((y == y1) && (y == y2) && (x > x1) && (x < x2)) {
      return true;
    } else {
      return false;
    }
      
  }

  private void updateTurn() {
    if (this.turn == this.playerList.size() - 1) {
      this.turn = 0;
    } else {
      this.turn++;
    }
    this.turnCount++;
    moveTarget();
  }
   
  private void moveTarget() {
    this.targetObj.move();
  }

  // 
  //Public Methods from Interface and needed for controller commands
  //
  @Override
  //public void drawWorld(String[][] rooms, int totalRow, int totalCol) throws IOException {
  public void drawWorld() throws IOException {
    int width = totalCol * 25 + 25;
    int height = totalRow * 25 + 25;

    BufferedImage bufferInstance = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics graphicsObj = bufferInstance.createGraphics();
    
    graphicsObj.setColor(Color.gray);
    graphicsObj.fillRect(0, 0, width, height);
    for (int i = 0; i < rooms.length; i++) {
      graphicsObj.setColor(Color.white);
      graphicsObj.fillRect(Integer.parseInt(rooms[i][1]) * 25 + 8, 
          Integer.parseInt(rooms[i][0]) * 25 + 8, (Integer.parseInt(rooms[i][3])
              - Integer.parseInt(rooms[i][1])) * 25 + 25, 
          (Integer.parseInt(rooms[i][2]) - Integer.parseInt(rooms[i][0])) * 25 + 25);
      graphicsObj.setColor(Color.DARK_GRAY);
      graphicsObj.drawRect(Integer.parseInt(rooms[i][1]) * 25 + 8,
          Integer.parseInt(rooms[i][0]) * 25 + 8, (Integer.parseInt(rooms[i][3])
              - Integer.parseInt(rooms[i][1])) * 25 + 25, (Integer.parseInt(rooms[i][2])
                  - Integer.parseInt(rooms[i][0])) * 25 + 25);
      graphicsObj.drawString(rooms[i][4], 
          Integer.parseInt(rooms[i][1]) * 25 + 13, Integer.parseInt(rooms[i][0]) * 25 + 20);
    }
   
    graphicsObj.dispose();
    try {
      
      //ImageIO.write(bufferInstance, "png", new File("world.png"));
      
      // Get the path to the "res" directory relative to the application's root
      String relativePath = "res/world.png"; // Path relative to your JAR or project root
      // Create a File object for the relative path
      File outputFile = new File(relativePath);
            
      // Ensure that the directories in the path exist
      // This will create the "res" directory if it doesn't exist
      outputFile.getParentFile().mkdirs(); 
      
      // Write the image to the specified relative path
      ImageIO.write(bufferInstance, "png", outputFile);
      
      
    } catch (IOException ioe) {
      throw new IOException("Unable to draw world image.");
    }
  }
  
  @Override
  public String createPlayer(int playerNum, String playerName, int maxItem, int curRoom) 
      throws IllegalArgumentException {
    if (playerNum < 0 || playerName == null || "".equals(playerName) || maxItem < 0
        || playerList.containsKey(playerNum)) {
      throw new IllegalArgumentException("Player Details are not correct");
    }
    if (curRoom < 1 || curRoom > roomObjList.size()) {
      throw new IllegalArgumentException("Room does not exist");
    }
    Player player1 = new Player(playerNum, playerName, maxItem, roomObjList.get(curRoom));
    this.playerList.put(playerNum, player1);
    return "Player Created";
  }
  
  @Override
  public String movePlayer(int player, int roomNum) throws IllegalArgumentException {
    if (player < 0 || !playerList.containsKey(player)) {
      throw new IllegalArgumentException("Incorrect Player");
    }
    if (roomNum < 1 || roomNum > roomCount) {
      throw new IllegalArgumentException("Room does not Exist");
    }
    int playerRoom = playerList.get(player).getLocation();
    if (neighborTable.get(playerRoom).contains(roomObjList.get(roomNum))) {
      playerList.get(player).move(roomObjList.get(roomNum));
      updateTurn();
      return "Move Completed";
    }
    updateTurn();
    return "Room not a neighbor. Lose your turn";
  }
  
  @Override
  public String pickItem(int playerNum, int itemNum) throws IllegalArgumentException {
    if (playerNum < 0 || !playerList.containsKey(playerNum)) {
      throw new IllegalArgumentException("Incorrect Player");
    }
    if (itemNum < 1 || itemNum > itemCount) {
      throw new IllegalArgumentException("Item does not Exist");
    }
    if (playerList.get(playerNum).getMaxItems() 
        == playerList.get(playerNum).getItemCount()) {
      return "Max item count reached for player";
    }
    int roomNum = playerList.get(playerNum).getLocation();
    if (roomObjList.get(roomNum).hasItem(itemObjList.get(itemNum))) {
      roomObjList.get(roomNum).removeItem(itemObjList.get(itemNum));
      playerList.get(playerNum).pickItem(itemObjList.get(itemNum));
      itemObjList.get(itemNum).updateRoom();
      updateTurn();
      return "Item Picked";
    }
    return "Item Not Present";
  }
  
  @Override
  public String lookAround(int player) throws IllegalArgumentException {
    if (player < 0 || !playerList.containsKey(player)) {
      throw new IllegalArgumentException("Incorrect Player");
    }
    updateTurn();
    return playerList.get(player).lookAround();
  }

  @Override
  public String getSpecificPlayerDetails(int specificPlayerNum) throws IllegalArgumentException {
    if (specificPlayerNum < 0 || !playerList.containsKey(specificPlayerNum)) {
      throw new IllegalArgumentException("Incorrect Player");
    }
    return playerList.get(specificPlayerNum).toString();
  }
 
  @Override
  public String getSpecificRoomDetails(int specificRoomNum) throws IllegalArgumentException {
    if (specificRoomNum < 0 || specificRoomNum > roomObjList.size()) {
      throw new IllegalArgumentException("Incorrect Room Number");
    }
    return roomObjList.get(specificRoomNum).displayPlayerSpaceInfo();
  }
    
  @Override
  public List<String> getTurn() {
    List<String> playerTurn = new ArrayList<String>();
    playerTurn.add(String.format("%s", this.turn));
    playerTurn.add(playerList.get(this.turn).getPlayerName());
    return playerTurn;
  }
   
  @Override
  public boolean gameOver() {
    if (this.turnCount == this.gameMaxTurn + 1) {
      return true;
    }
    return false;
  }
  
  @Override
  public String getTarget() {
    return this.targetObj.toString();
  }
    
  @Override
  public String getAllRoomNames() {
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= roomCount; i++) {
      sb.append(i);
      sb.append(" ");
      sb.append(this.roomObjList.get(i).getRoomName());
      sb.append("\n");
    }
    return sb.toString();
  }
  
  @Override
  public List<String> getCompleteSpaceDetails() {
    List<String> list = new ArrayList<String>();
    for (int i = 1; i <= roomCount; i++) {
      list.add(this.roomObjList.get(i).toString());
    }
    return list;
  }


  /**
   * This function returns the complete specifications of the world like
    world dimensions, room count and details, item count and details, target details.
   * @return returns a String containing world specifications.
   */
  @Override
  public String toString() {
    return String.format("total rows = %s, total cols = %d, WorldName = %s,\nroomCount = %d,"
        + " itemCount = %d,\nTarget = %s,\nItems = %s,\nRooms = %s\n", 
        this.totalRow, this.totalCol, this.world, this.roomCount, this.itemCount,
        this.targetObj, this.itemObjList, this.roomObjList);
  }

}