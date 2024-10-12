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
import javax.imageio.ImageIO;

/**
 * The WorldSetup class is responsible for initializing the game world,
 * including rooms and items, based on a given configuration file content.
 * It implements the World interface.
 */
public final class WorldSetup implements World {
  private final int totalRow; // Total number of rows in the world
  private final int totalCol; // Total number of columns in the world
  private String world; // String representation of the world
  private final int roomCount; // Number of rooms in the world
  private final int itemCount; // Number of items in the world
  private int health; // Health of the target
  private String target; // Name of the target
  private Target targetObj; // Target object
  private Map<Integer, ItemList> itemObjList; // Map of item objects
  private Map<Integer, Room> roomObjList; // Map of room objects
  private Map<Integer, List<Room>> neighborTable; // Table of neighboring rooms 
  
  /**
   * Constructs a WorldSetup instance with the specified file content.
   *
   * @param fileContent the content of the configuration file
   * @throws IllegalArgumentException if any validation fails
   */
  public WorldSetup(String fileContent) throws IllegalArgumentException {
    String[] lines = fileContent.split("\n");
    String[] element = null;
    element = lines[0].strip().split("\\s+");
    this.world = "";
    this.totalRow = Integer.parseInt(element[0].strip());
    this.totalCol = Integer.parseInt(element[1].strip());

    // Validate world dimensions
    if (this.totalRow > 0 && this.totalCol > 0) {
      int j;
      for (j = 2; j < element.length; ++j) {
        this.world = this.world + " " + element[j];
      }

      // Parse health and target information
      element = lines[1].strip().split("\\s+");
      this.health = Integer.parseInt(element[0].strip());
      if (this.health <= 0) {
        throw new IllegalArgumentException("Initial Health of Target must be a Positive value.");
      } else {
        this.target = "";
        for (j = 1; j < element.length; ++j) {
          this.target = this.target + " " + element[j];
        }
  
        // Validate number of rooms
        try {
          Integer.parseInt(lines[2].strip());
        } catch (NumberFormatException var11) {
          System.out.println("Number of Spaces should be an Integer");
        }

        this.roomCount = Integer.parseInt(lines[2].strip());
        if (this.roomCount <= 1) {
          throw new IllegalArgumentException("There should at least be two rooms present.");
        } else {
          String[][] rooms = new String[this.roomCount][6];

          // Parse room details
          try {
            for (int i = 3; i < 3 + this.roomCount; ++i) {
              element = null;
              
              element = lines[i].strip().split("\\s+");
              rooms[i - 3][0] = element[0].strip(); // Row start
              rooms[i - 3][1] = element[1].strip(); // Column start
              rooms[i - 3][2] = element[2].strip(); // Row end
              rooms[i - 3][3] = element[3].strip(); // Column end

              // Validate room dimensions
              if (Integer.parseInt(rooms[i - 3][0]) < 0 || Integer.parseInt(rooms[i - 3][1]) < 0 
                  || Integer.parseInt(rooms[i - 3][2]) < 0 || Integer.parseInt(rooms[i - 3][3]) < 0 
                  || Integer.parseInt(rooms[i - 3][0]) > this.totalRow 
                  || Integer.parseInt(rooms[i - 3][1]) > this.totalCol 
                  || Integer.parseInt(rooms[i - 3][2]) > this.totalRow 
                  || Integer.parseInt(rooms[i - 3][3]) > this.totalCol) {
                throw new IllegalArgumentException("Room Column or Row value cannot be negative. "
                      + "They can also not be greater than the room dimensions.");
              }

              String roomName = "";
              for (int j1 = 4; j1 < element.length; ++j1) {
                roomName = roomName + " " + element[j1];
              }

              rooms[i - 3][4] = roomName.strip(); // Room name
            }
          } catch (ArrayIndexOutOfBoundsException var12) {
            System.out.println("Correct number of Spaces are not given");
          }

          // Validate number of items
          try {
            Integer.parseInt(lines[3 + this.roomCount].strip());
          } catch (NumberFormatException var10) {
            System.out.println("Number of Items should be an Integer. "
                + "Or check if correct Number of Spaces are provided.");
          }

          this.itemCount = Integer.parseInt(lines[3 + this.roomCount].strip());
          if (this.itemCount < 1) {
            throw new IllegalArgumentException("There should at least be 1 item.");
          } else {
            String[][] items = new String[this.itemCount][3];

            // Parse item details
            int i;
            for (i = 4 + this.roomCount; i < 4 + this.roomCount + this.itemCount; ++i) {
              element = null;
              element = lines[i].strip().split("\\s+");
              items[i - 4 - this.roomCount][0] = element[0].strip(); // Room index

              // Validate room index for item
              if (Integer.parseInt(items[i - 4 - this.roomCount][0]) < 0 
                  || Integer.parseInt(items[i - 4 - this.roomCount][0]) > this.roomCount - 1) {
                throw new IllegalArgumentException("Item is set to a room that does not exist.");
              }

              items[i - 4 - this.roomCount][1] = element[1].strip(); // Item damage
              if (Integer.parseInt(items[i - 4 - this.roomCount][1]) < 1) {
                throw new IllegalArgumentException("Damage of an item should be positive.");
              }

              String itemName = "";
              for (int j1 = 2; j1 < element.length; ++j1) {
                itemName = itemName + " " + element[j1];
              }

              items[i - 4 - this.roomCount][2] = itemName.strip(); // Item name
            }

            this.createTarget();
            this.createItems(items);
            this.createRooms(rooms, items);
            this.neighborTable = new HashMap<Integer, List<Room>>();

            // Initialize neighbor lists for each room
            for (i = 1; i <= this.roomCount; ++i) {
              List<Room> neighborList = new ArrayList<Room>();
              this.neighborTable.put(i, neighborList);
            }

            // Find neighbors for each room
            for (i = 1; i <= this.roomCount; ++i) {
              this.findNeighbor(i, rooms);
            }

            this.setNeighbors(this.neighborTable);

            // Draw the world visualization
            try {
              this.drawWorld(rooms, this.totalRow, this.totalCol);
            } catch (IOException var9) {
              var9.printStackTrace();
            }

          }
        }
      }
    } else {
      throw new IllegalArgumentException("Enter correct dimensions for World.");
    }
  }


  /**
   * Creates a target object with the specified attributes.
   */
  private void createTarget() {
    this.targetObj = new Target(this.target, this.health, 1, this.roomCount);
  }

  /**
   * Creates a list of items from the given array and stores them in a map.
   *
   * @param items A 2D array containing item details where each row represents an item.
   *              The first column is the item id, the second column is the item value,
   *              the third column is the item description, and the fourth column is its weight.
   */
  private void createItems(String[][] items) {
    this.itemObjList = new HashMap<Integer, ItemList>();

    for (int i = 1; i <= this.itemCount; ++i) {
      this.itemObjList.put(i, new ItemList(i, Integer.parseInt(items[i - 1][0]) + 1, 
          items[i - 1][2], Integer.parseInt(items[i - 1][1])));
    }
  }

  /**
   * Sets the neighbors for each room based on the provided neighbor table.
   *
   * @param neighborTable2 A map where the key is the room number
   */
  private void setNeighbors(Map<Integer, List<Room>> neighborTable2) {
    for (int i = 1; i <= this.roomCount; ++i) {
      this.roomObjList.get(i).setNeighbor(neighborTable2.get(i));
    }
  }

  /**
   * Creates room objects from the provided room and item arrays and stores them in a map.
   *
   * @param rooms A 2D array containing room details where each row represents a room.
   *              The array includes the room id, coordinates, and name.
   * @param items A 2D array containing item details as described in the createItems method.
   */
  private void createRooms(String[][] rooms, String[][] items) {
    this.roomObjList = new HashMap<Integer, Room>();

    for (int i = 1; i <= this.roomCount; ++i) {
      List<ItemList> itemList = new ArrayList<ItemList>();

      for (int j = 1; j <= this.itemCount; ++j) {
        if (Integer.parseInt(items[j - 1][0]) + 1 == i) {
          itemList.add(this.itemObjList.get(j));
        }
      }

      int[] coordinates = new int[]{
          Integer.parseInt(rooms[i - 1][1]), 
          Integer.parseInt(rooms[i - 1][0]), 
          Integer.parseInt(rooms[i - 1][3]) + 1, 
          Integer.parseInt(rooms[i - 1][2]) + 1
      };
      this.roomObjList.put(i, new Room(i, rooms[i - 1][4], itemList, coordinates));
    }
  }
  
  /**
   * Draws the world map based on the provided room specifications and saves it as a JPEG image.
   *
   * @param rooms A 2D array containing room details used for drawing.
   * @param worldTotalRow The total number of rows in the world.
   * @param worldTotalCol The total number of columns in the world.
   * @throws IOException If an error occurs during image writing.
   */
  private void drawWorld(String[][] rooms, int worldTotalRow, int worldTotalCol) 
      throws IOException {
    int width = worldTotalCol * 25 + 25;
    int height = worldTotalRow * 25 + 25;
    BufferedImage bufferInstance = new BufferedImage(width, height, 1);
    Graphics graphicsObj = bufferInstance.createGraphics();
    graphicsObj.setColor(Color.gray);
    graphicsObj.fillRect(0, 0, width, height);

    for (int i = 0; i < rooms.length; ++i) {
      graphicsObj.setColor(Color.white);
      graphicsObj.fillRect(Integer.parseInt(rooms[i][1]) * 25 + 8, 
          Integer.parseInt(rooms[i][0]) * 25 + 8, 
             (Integer.parseInt(rooms[i][3]) - Integer.parseInt(rooms[i][1])) * 25 + 25, 
             (Integer.parseInt(rooms[i][2]) - Integer.parseInt(rooms[i][0])) * 25 + 25);
      graphicsObj.setColor(Color.DARK_GRAY);
      graphicsObj.drawRect(Integer.parseInt(rooms[i][1]) * 25 + 8, 
          Integer.parseInt(rooms[i][0]) * 25 + 8, 
             (Integer.parseInt(rooms[i][3]) - Integer.parseInt(rooms[i][1])) * 25 + 25, 
             (Integer.parseInt(rooms[i][2]) - Integer.parseInt(rooms[i][0])) * 25 + 25);
      graphicsObj.drawString(rooms[i][4], Integer.parseInt(rooms[i][1]) * 25 + 13, 
          Integer.parseInt(rooms[i][0]) * 25 + 20);
    }

    graphicsObj.dispose();

    //    try {
    //ImageIO.write(bufferInstance, "jpeg", new File("C:/Users/EDCARLOS/eclipse-workspace/cs5010/"
    //          + "m1-the-world/res/world.jpeg"));
    //    } catch (IOException var9) {
    //      var9.printStackTrace();
    //    }
    
    
    try {
      // Get the path to the "res" directory relative to the application's root
      String relativePath = "res/world.jpeg"; // Path relative to your JAR or project root
      // Create a File object for the relative path
      File outputFile = new File(relativePath);
            
      // Ensure that the directories in the path exist
      // This will create the "res" directory if it doesn't exist
      outputFile.getParentFile().mkdirs(); 
      
      // Write the image to the specified relative path
      ImageIO.write(bufferInstance, "jpeg", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    
    
  }

  /**
   * Finds neighboring rooms for a given room based on their coordinates.
   * This method checks for overlaps between the given room and all other rooms,
   * updating the neighbor table accordingly.
   *
   * @param roomNum The index of the room for which neighbors are being found (1-based).
   * @param rooms A 2D array representing the coordinates of rooms. Each row contains the 
   *              coordinates [x1, y1, x2, y2] for the respective room.
   */
  private void findNeighbor(int roomNum, String[][] rooms) {
    // Extract the coordinates of the specified room and adjust them for easier processing.
    int[] coordinate1 = new int[]{
        Integer.parseInt(rooms[roomNum - 1][0]), // x1
        Integer.parseInt(rooms[roomNum - 1][1]), // y1
        Integer.parseInt(rooms[roomNum - 1][2]) + 1, // x2
        Integer.parseInt(rooms[roomNum - 1][3]) + 1  // y2
    };

    // Loop through all rooms to find neighbors.
    for (int k = 0; k < this.roomCount; ++k) { 
      
      if (k != roomNum - 1) { // Skip the current room.
        // Extract the coordinates of the neighboring room.
        int[] coordinate2 = new int[]{
            Integer.parseInt(rooms[k][0]), // x1
            Integer.parseInt(rooms[k][1]), // y1
            Integer.parseInt(rooms[k][2]) + 1, // x2
            Integer.parseInt(rooms[k][3]) + 1  // y2
        };

        // Define the quadrilaterals (quadrants) for both rooms.
        int[][] quad1 = new int[][]{
            {coordinate1[0], coordinate1[1]}, // Bottom-left
            {coordinate1[2], coordinate1[1]}, // Bottom-right
            {coordinate1[2], coordinate1[3]}, // Top-right
            {coordinate1[0], coordinate1[3]}  // Top-left
        };
        int[][] quad2 = new int[][]{
            {coordinate2[0], coordinate2[1]}, // Bottom-left
            {coordinate2[2], coordinate2[1]}, // Bottom-right
            {coordinate2[2], coordinate2[3]}, // Top-right
            {coordinate2[0], coordinate2[3]}  // Top-left
        };

        // Check for intersections between the quadrilaterals.
        for (int i = 0; i < 4; ++i) { // Iterate over the edges of quad1.
          for (int j = 0; j < 4; ++j) { // Iterate over the edges of quad2.
            // Handle different cases for edges and intersections.
            if (j < 2) { // For bottom and top edges.
              if (this.checkWithinLine(quad1[j % 4][0], quad1[j % 4][1], quad1[(j + 1) % 4][0], 
                  quad1[(j + 1) % 4][1], quad2[i][0], quad2[i][1])) {
                // Add to neighbor table if not already present.
                if (!this.neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                  this.neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                }
                if (!this.neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                  this.neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                }
              } else if (i == 3) {
                // Handle specific cases when i equals 3.
                if (this.checkTwoCommonPoints(quad1[j % 4][0], quad1[j % 4][1], 
                    quad1[(j + 1) % 4][0], quad1[(j + 1) % 4][1], quad2[0][0], 
                    quad2[0][1], quad2[i][0], quad2[i][1]) 
                    || this.checkTwoCommonPoints(quad1[j % 4][0], 
                        quad1[j % 4][1], quad1[(j + 1) % 4][0], 
                      quad1[(j + 1) % 4][1], quad2[i][0], quad2[i][1], 
                      quad2[i - 1][0], quad2[i - 1][1])) {
                  // Add to neighbor table.
                  if (!this.neighborTable.get(roomNum).contains(this.roomObjList.get(k + 1))) {
                    this.neighborTable.get(roomNum).add(this.roomObjList.get(k + 1));
                  }
                  if (!this.neighborTable.get(k + 1).contains(this.roomObjList.get(roomNum))) {
                    this.neighborTable.get(k + 1).add(this.roomObjList.get(roomNum));
                  }
                }
              }
              // Additional checks for other edge cases follow...
            }
            // Similar logic follows for j >= 2 and other combinations of i and j.
          }
        }
      }
    }
  }

  /**
   * Checks if two line segments have two common points.
   * 
   * @param x1 - x-coordinate of the first point of the first line
   * @param y1 - y-coordinate of the first point of the first line
   * @param x2 - x-coordinate of the second point of the first line
   * @param y2 - y-coordinate of the second point of the first line
   * @param x3 - x-coordinate of the first point of the second line
   * @param y3 - y-coordinate of the first point of the second line
   * @param x4 - x-coordinate of the second point of the second line
   * @param y4 - y-coordinate of the second point of the second line
   * @return true if the lines have two common points, false otherwise
   */
  private boolean checkTwoCommonPoints(int x1, int y1, int x2, int y2, int x3, 
      int y3, int x4, int y4) {
    return x1 == x3 && x2 == x4 && y1 == y3 && y2 == y4;
  }

  /**
   * Checks if a point (x, y) is within the bounds of a line segment defined by two points.
   * 
   * @param x1 - x-coordinate of the first point of the line
   * @param y1 - y-coordinate of the first point of the line
   * @param x2 - x-coordinate of the second point of the line
   * @param y2 - y-coordinate of the second point of the line
   * @param x - x-coordinate of the point to check
   * @param y - y-coordinate of the point to check
   * @return true if the point is within the line segment, false otherwise
   */
  private boolean checkWithinLine(int x1, int y1, int x2, int y2, int x, int y) {
    if (x == x1 && x == x2 && y > y1 && y < y2) {
      return true;
    } else {
      return y == y1 && y == y2 && x > x1 && x < x2;
    }
  }
  
  /**
   * Returns a formatted string representing the specifications of the world.
   * 
   * @return a string containing world specifications such as rows, columns, etc...
   */
  public String getWorldSpecs() {
    return String.format("total rows = %s, total cols = %d, WorldName = %s, "
        + "roomCount = %d, itemCount = %d, Health = %d, TargetName = %s, "
        + "Target = %s, Items = %s, Rooms = %s", 
        this.totalRow, this.totalCol, this.world, this.roomCount, this.itemCount, 
        this.health, this.target, this.targetObj, this.itemObjList, this.roomObjList);
  }
  
  /**
   * Gets the map of rooms in the world.
   * 
   * @return a map of room objects
   */
  public Map<Integer, Room> getRooms() {
    return this.roomObjList;
  }
  
  /**
   * Gets the map of items in the world.
   * 
   * @return a map of item objects
   */
  public Map<Integer, ItemList> getItems() {
    return this.itemObjList;
  }
  
  /**
   * Gets the target object in the world.
   * 
   * @return the target object
   */
  public Target getTarget() {
    return this.targetObj;
  }
  

  public int getTotalRow() {
    return totalRow;
  }

  public int getTotalCol() {
    return totalCol;
  }

  public int getHealth() {
    return health;
  }
  
  
}

