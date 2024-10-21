package controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import model.World;


/**
 * This is the controller for the Kill Dr. Lucky game. 
 * It implements the GameControllerInterface interface.
 * It uses the command design pattern to execute the user specified commands.
 * The controller takes input from the user and decides which methods need to be executed.
 */
public class GameController implements GameControllerInterface {
    
  private final Appendable out;
  private final Scanner scan;
  private List<Integer> rand;
  //private int randIndex = 0;

  /**
   * Constructor for the controller, which takes Readable input and Appendable output.
   * @param inSource  the source to read from
   * @param outTarget the target to print to
   * @param randNum an array containing the values to substitute random number generator.
   * @throws IllegalArgumentException thrown if input or output stream is not valid.
   */
  public GameController(Readable inSource, Appendable outTarget, List<Integer> randNum) {
    if (inSource == null || outTarget == null || randNum == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = outTarget;
    this.scan = new Scanner(inSource);
    this.rand = randNum;
  } 
  
  @Override
  public void playGame(World worldSetup) throws IllegalArgumentException {
    if (worldSetup == null || !(worldSetup instanceof World)) {
      throw new IllegalArgumentException("Not a valid Model.");
    }
    
    //Create Computer Controlled player who enters room 1 by default.
    CommandController cmd = null;
    try {
      boolean flag = false;
      do {
        try {
          /*
          out.append("""
                     ==== Welcome to the World!! ====
                     We need 1 Computer Controlled Player in the world
                     We are adding one real quick...
                     OK! Now Enter the Maximum number of ITEMS the computer 
                     player is allowed to pick
                     """); 
          */
          System.out.println("""
              ==== Welcome to the World!! 🎮🌍 ====
              We need 1 Computer Controlled Player in the world 🤖
              We are adding one real quick... 🏃💨
              OK! Now Enter the Maximum number of ITEMS 🎒 the computer player is allowed to pick
              """);
          
          int computerItemCount = Integer.parseInt(scan.next());
          if (computerItemCount < 1 || computerItemCount > 5) {
            throw new NumberFormatException("Enter a number between 1 and 5.\n");
          }
          cmd = new CreatePlayer(0, "Computer", computerItemCount, 1);
          if ("Player Created".equals(cmd.go(worldSetup))) {
            out.append("Computer Controlled Player Created\n");
          }
          flag = false;
        } catch (NumberFormatException nfe) {
          out.append("Enter a number between 1 and 5.\n");
          flag = true;
        } catch (IllegalArgumentException iae) {
          out.append("Could not create computer player.\n");
          flag = true;
        }
      } while (flag);
     
       
      
      //user input create players
      int playerCount = 0;
      flag = false;
      do {
        try {
          out.append("\nEnter Total Number of Human Players\n");
          playerCount = Integer.parseInt(scan.next());
          if (playerCount < 1) {
            out.append("Player count should be atleast 1.\n");
            throw new NumberFormatException("Player count should be atleast 1.\n");
          }
          flag = false;
        } catch (NumberFormatException nfe) {
          out.append("Enter Integer for Number of Players.\n");
          flag = true;
        }
      } while (flag);
      
      //create the human players. 
      cmd = null;
      int i = 1;
      while (i <= playerCount) {
        out.append("\nEnter The Name Of Player " + i + " \n"); 
        int maxItem = 0;
        flag = false;
        String playerNameInput = scan.next(); // Read player name first

        do {
          try {
            out.append("\nEnter Total Max Items Allowed For Player " + i + " To Pick "
                + "(between 1 to 5)\n");
            maxItem = Integer.parseInt(scan.next());
            if (maxItem < 1 || maxItem > 5) {
              throw new NumberFormatException("Enter a number between 1 and 5.\n");
            }
            flag = false;
          } catch (NumberFormatException nfe) {
            out.append("Enter a number between 1 and 5.\n");
            flag = true;
          }
        } while (flag);
        
        int firstRoom = 1;
        flag = false;
        do {
          try {
            //String playerNameInput = scan.next();
            out.append("\n" + playerNameInput + ", which Room do you want to enter first? "
                + "Choose room Number from below options:\n");
            out.append(worldSetup.getAllRoomNames());
            firstRoom = Integer.parseInt(scan.next());
            cmd = new CreatePlayer(i, playerNameInput, maxItem, firstRoom);
            if ("Player Created".equals(cmd.go(worldSetup))) {
              i++;
            }
            flag = false;
          } catch (NumberFormatException nfe) {
            out.append("Enter correct room Number to start.\n");
            flag = true;
          } catch (IllegalArgumentException iae) {
            out.append("Enter correct room Number to start.\n");
            flag = true;
          }
        } while (flag);
        
        cmd = null;
      }
      
      int turn = 0;
      int roomNum = 1;
      int itemNum = 1;
      
      //Start the game
      out.append("\n=== IT'S TIME TO PLAY!! ===");
      
      
      while (!worldSetup.gameOver()) {
        List<String> turnList = worldSetup.getTurn();
        turn = Integer.parseInt(turnList.get(0));
        String turnName = turnList.get(1);
        flag = false;
        String in = null;
        Random random = new RandomNumber(rand);
        do {
          out.append("\n\n It's Player N* " + turn + " <<->> " + turnName + "'s Turn:\n");
          out.append("\n==== Choose one of the following options ==== \n");
          out.append("1. Draw World\n2. Display Player Info\n"
              + "3. Display Current Space(Room) Info\n4. Move\n5. Pick Item\n6. "
              + "Look Around\n7. quit\n");
          
          if (turn == 0) {
            in = String.format("%s", random.nextInt(7 - 4) + 4);
          } else {
            try {
              in = scan.next();
              if ("q".equalsIgnoreCase(in) || "quit".equalsIgnoreCase(in) || "7".equals(in)) {
                out.append("=== Game Over ===");
                return;
              }
              flag = false;
              if (!("1".equals(in) || "2".equals(in) || "3".equals(in) || "4".equals(in) 
                  || "5".equals(in) || "6".equals(in))) {
                flag = true; 
                throw new NumberFormatException("Invalid option. Try again.");
              }
            } catch (NumberFormatException nfe) {
              out.append("Invalid option. Try again.\n");
              flag = true;
            }
          }
        } while (flag);
        
        
        // Take valid input for space details when player wants to move.
        String[] element = worldSetup.toString().split("\\s+");
        String[] remove2 = element[15].split(",");
        int roomCount = Integer.parseInt(remove2[0]);
        if (Integer.parseInt(in) == 4) {
          flag = false;
          do {
            try {
              out.append("\nEnter room number to which you want to move:\n");
              if (turn == 0) {
                roomNum = random.nextInt((roomCount + 1) - 1) + 1;
              } else {
                roomNum = Integer.parseInt(scan.next());
              }
              flag = false;
            } catch (NumberFormatException nfe) {
              out.append("Enter a room number between 1 and " + roomCount + "\n");
              flag = true;
            } 
          } while (flag);
        }
          
        //Take valid input for item when player wants to pick item.
        String[] remove = element[18].split(",");
        int itemCount = Integer.parseInt(remove[0]); 

        if (Integer.parseInt(in) == 5) {
          flag = false;
          do {
            try {
              out.append("\nEnter Item number that you want to pick:\n");
              if (turn == 0) {
                itemNum = random.nextInt((itemCount + 1) - 1) + 1;
              } else {
                itemNum = Integer.parseInt(scan.next());
              }
              flag = false;
            } catch (NumberFormatException nfe) {
              out.append("Enter a item number between 1 and " + itemCount + "\n");
              flag = true;
            }
          } while (flag);
        }
         
        Map<String, CommandController> gameCommands = new HashMap<>();
        gameCommands.put("1", new CreateWorldGraph());
        gameCommands.put("2", new DisplayPlayerInfo(turn));
        gameCommands.put("3", new DisplaySpaceInfo(turn));
        gameCommands.put("4", new MovePlayer(turn, roomNum));
        gameCommands.put("5", new PickItem(turn, itemNum));
        gameCommands.put("6", new LookAround(turn)); 
          
        cmd = gameCommands.getOrDefault(in, null);
        if (cmd == null) {
          out.append("Numeric Expected\n");
        } else {
          try {
            out.append(cmd.go(worldSetup));
            cmd = null;
          } catch (IOException ioe) {
            out.append("Unable to draw graph");
          }
        }
      }
      scan.close();
      
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
