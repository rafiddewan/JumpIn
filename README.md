# JumpIn

Jumpin is a single player puzzle game in which the goal is to get all the rabbits into the holes by jumping over obstacles.

## Playing the Game
- open the terminal
- navigate to the local path where the JumpIn.jar file is stored
- type the following line into the terminal
```
java -jar JumpIn.jar
```

## Deliverables
Included in this .zip file are the following

#### Code:
 - JumpIn.jar: The executable file of the game JumpIn, allowing the user to play one preconfigured puzzle.
 - Source Code: The code behind JumpIn.exe consisting of ten(10) Java Object classes and two(2) Java Interface Class written in Java Version 1.8
 - Design Patterns that were used: MVC & Observer Pattern
 - Libraries: Java.Swing
 
#### Documentation:
- JavaDoc: Included within each .java file is JavaDoc explaining the functionality of each class and method
- JumpIn.png: The UML diagram used during creation of the game, allowing for planning and visualization of how the objects
would interact
- DesignDecisions.pdf:A pdf explaining the reasoning behind certain decisions made when creating the game 
such as why certain data structures and data types were used  
- UserManual.pdf: A pdf which goes deeper into the premise of the game as well 
as explaining how to play the game and includes a legend explaining the meaning of the symbols on the board
- JumpInSD.png: A png containing the sequence diagram of how the game is played 
 
## Known Issues
### Milestone 2
- Code smells with the `update(JumpInModel model)` method in JumpInView

## Changelog
### Milestone 1
- First version of this project
- Created object classes for the following: Board, EmptySpace, FoxPart, Hole,JumpIn, Mushroom, Rabbit and Space
- Created interface class for Moveable Spaces
- UML Created for JumpIn project
- User Manual written to explain the game
### Milestone 2
- Created first version of the GUI for JumpIn
- Implemented MVC to handle events to separate input logic, infrastructure logic, and UI logic
- Implemented Observer Pattern with the View interface so that the model can update multiple views (which will be used for the final milestone)
- Updated UML with JumpInView and JumpInController which are classes used for the MVC architecture
- Refactored JumpInModel so that the input logic is handled with JumpInController and the game (infrastructure) logic is handled in JumpInModel
- Added some test cases for the JumpInModel class to test all the getters and setters
## Roadmap
- Refactor JumpInView and other classes from code smells (as it is starting to build up)
- Add more graphics for the GUI 
- Add more test cases for the JumpInController
- The third milestone to be reached is the implementation of an undo/redo feature as well as a solver feature to provide hints to
 the player if needed.
- The last milestone in place is the implementation of a save/load feature as well as a level editor function to create new puzzles.

### Contributors
##### Group: I hate C
Nick Coutts,
Rafid Dewan,
Lazar Milojevic,
Benjamin Ransom



Written by: Benjamin Ransom
Written by: Rafid Dewan (November 4, 2019)
