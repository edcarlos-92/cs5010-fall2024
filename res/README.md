Here’s a simplified version of your markdown file while retaining all the technical details:

---

# CS 5010 Fall 2024 Semester Project: Kill Doctor Lucky

**Name:** Carlos Semeho Edorh  
**Email:** edorh.c@northeastern.edu  
**Preferred Name:** Carlos  

### Overview

This project showcases the MVC design pattern to create a board game inspired by Kill Doctor Lucky. 

- The first part of the project involves the Model, which includes the World, spaces within the World, items in those spaces, and the target character that moves sequentially through the rooms. 
- The second part updates the model to include players, who can be either controlled by the computer or by humans. 
- A text-based synchronous controller allows players to interact with the game via the command line.

### Features

1. Creates the world based on parameters from an input file, returning parsed details.
2. Generates a visual representation of the world.
3. The target character moves according to the index of spaces. When it reaches the last space, it returns to the first one.
4. Displays a list of neighbors for each space.
5. Accepts a text file in a specified format (examples are in the `/res` folder) and raises specific exceptions for incorrect content.
6. Provides details about items and players in each room.
7. Supports adding human and computer-controlled players.
8. Computer-controlled players act independently.
9. Players can move to adjacent spaces.
10. Players can pick up items in their current space, subject to a defined limit on how many items they can carry.
11. Player turns are taken in the order they were added to the game.
12. Players can explore their current space to see neighboring areas and items, as well as details about the target.

### How to Run

Download the JAR file from the `/res` folder.  
To run it from the command prompt, provide two arguments: the path to the input file and the maximum number of turns for the game (set to `10`).  
**Example Command:**  
```bash
java -jar m2-synchronous-controller.jar {fully qualified path to}/res/dr_lucky_in_marios_castle.txt 10
```

In your case, the command would be:
```bash
java -jar m2-synchronous-controller.jar C:\Users\CARLOS-PC\eclipse-workspace\cs5010\m1-the-world\res\dr_lucky_in_marios_castle.txt 10
```

### Note on Milestone 1

The JAR file for Milestone 1 is included in the `/res/M1` folder, along with instructions on how to run it. You can also execute it directly from the JAR directory:
```bash
java -jar m1-the-world.jar C:\Users\CARLOS-PC\eclipse-workspace\cs5010\m1-the-world\res\dr_lucky_in_marios_castle.txt
```

### How to Use the Program

You can run the program using the JAR file in the `/res` folder. For detailed instructions on executing the JAR file, refer to the previous "How to Run" section. If you clone the repository, the program runs using the main class, which is the Driver class. You can execute the "Main.java" file in the src/driver with two command-line arguments: the path to the text file for world creation and the maximum turns value.

### Example Runs

The file `M1TestOutput.txt` contains the output from one example run of Milestone 1, demonstrating:
1. Displaying neighbors by room number and object references.
2. Showing items in a space.
3. Providing details of a room, including items and neighbors.
4. Listing all items in the world.
5. Displaying target details and updating the target's location.

#### Example Runs from Milestone 2:
1. `/res/M2Run.txt` shows outputs based on updated model requirements.
2. `/res/M2Run@Game.txt` contains an example of playing the game with the following scenarios:
   - Creating a computer-controlled player.
   - Adding two human-controlled players.
   - Correct turn order for players.
   - Allowing players to move to neighboring spaces; moving to a non-neighboring space results in a lost turn.
   - Players can pick up items from their current space, with a chance to retry if they select incorrectly.
   - Players can look around to decide their next action and view target details.
   - Displaying player details, including current space and items collected.
   - Limiting item collection to the maximum allowed.
   - Saving a graphical representation of the world in PNG format in the `/res` folder.
   - Displaying space information for available items.

### Design/Model Changes

**Changes from UML1 to UML2 in Milestone 1:**
1. The World constructor initially accepted a File input but has been changed to accept a String.
2. Added private helper methods in World for better code organization.
3. Introduced the `setNeighbors` method in World to establish neighbor relationships among spaces.
4. The `changeHealth()` method of Target has not been implemented due to unclear requirements and has been removed from the UML.

**Changes from MILESTONE -2 UML_DIAGRAM_REFINED_DESIGN to MILESTONE -2 UML_DIAGRAM_UPDATED_REFACTORED_DESIGN:**
- The initial design didn't clarify the need for random numbers, but it became apparent later, leading to the following changes:
1. The World Interface now contains only the necessary methods for the command controller.
2. The controller interacts solely with the World Interface, not with any other interfaces in the model.
3. A `RandomNumber` class has been added for generating random numbers for the computer-controlled player.
4. The command controller classes have been simplified to include only the `go()` method, which calls relevant model methods through the World Interface.

### Assumptions

1. World dimensions must be positive integers.
2. The initial health of the Target must be positive.
3. At least two spaces must exist.
4. Room coordinates in the input file are expected to be correct.
5. Room column or row values cannot be negative or exceed room dimensions.
6. At least one item must be present.
7. All integers in the input file should be whole numbers.
8. Computer-controlled and human-controlled players belong to the same class and are created similarly; the controller manages their differences.
9. Only one computer-controlled player is allowed.
10. Player numbers must be integers.
11. The controller accepts indices for moving spaces and picking items. Users should refer to displayed commands to find integer equivalents for their actions.

### Limitations

1. If an error occurs during program execution, the game stops and must be restarted manually.
2. A high number of rooms may lead to longer execution times.

### Citations

Reference for creating buffered images and graphics:  
"How to use drawRect method in java.awt.Graphics". Available: https://www.tabnine.com/code/java/methods/java.awt.Graphics/drawRect

Reference for generating random numbers in a specified range:  
"https://www.baeldung.com/java-generating-random-numbers-in-range".