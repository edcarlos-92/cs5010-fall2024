# CS 5010 Fall 2024 Semester Project: Kill Doctor Lucky

`Name`: Carlos Semeho Edorh  
`Email`: edorh.c@northeastern.edu  
`Preferred Name`: Carlos  

## Overview
This project represents the coursework for CS 5010 for Fall 2024. It implements a board game inspired by the classic game *Kill Doctor Lucky* using the Model-View-Controller (MVC) design pattern. The project focuses on creating a dynamic world populated with various items, spaces, and characters, where a target character navigates through the rooms sequentially.

## Key Components
- **Model:** The core of the application, representing:
  - **World:** The entire game environment.
  - **Spaces:** Individual rooms within the world.
  - **Items:** Objects located within the spaces.
  - **Target Character:** The character that moves through the rooms.

## Features
- **Dynamic World Creation:** Generate the game world based on parameters specified in an input file, allowing for flexibility and easy adjustments.
- **Pictorial Representation:** Create a visual representation of the game world, enhancing user engagement and understanding.
- **Game Mechanics:** Implement basic game functions such as moving the target character and interacting with items within the spaces.


## How to Run the Application
1. **Download the JAR File:** Locate the JAR file in the `/res` folder of the repository.
2. **Run the JAR File:** Use the command line to execute the JAR file, providing the path to the input file as an argument.

   ```bash
   java -jar Project-KillDrLucky.jar {fully qualified path to}/res/doctor_lucky_world.txt
   ```


## How to Use the Program
After cloning the repository, navigate to the main class designated as the Driver class. The application can be executed by running the `Main.java` file, ensuring that you pass the required command line argument pointing to the specifications text file. For example, if using an IDE, set up your run configuration to include the path to the input file.

## Example Runs
The file `test.txt` contains sample output from various test runs, demonstrating the application's functionality:
- **Display Neighbors:** Outputs the neighbors of each room with their room numbers and object references.
- **Display Items:** Lists items contained within a specific space.
- **Room Details:** Shows comprehensive details about a room, including items and neighbors.
- **World Inventory:** Displays all items present in the game world.
- **Target Movement:** Shows details of the target character and updates its location upon movement.

   The Graphical Representation of the World is available as an image in /res folder under the name world.jpeg.


## Justification for Using the Player Interface for the Target Character
At this point in the milestone, there is no focus placed on a player based on the project specifications. However, using the Player interface for the Target Character is beneficial for several reasons:
- **Shared Actions:** The Target Character shares movement and interaction behaviors with player characters, ensuring consistent functionality.
- **Clear Design:** Utilizing the Player interface creates a well-structured codebase, making it easier to understand and maintain.
- **Future Scalability:** This design allows for easy addition of new characters and features in later milestones.
- **Distinct Roles:** Although the Target Character is different from the main players, representing it as a "player" in terms of interactions helps integrate it into the overall game mechanics.

## Future Improvements
Depends on the Milestones ahead...