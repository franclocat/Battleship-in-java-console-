# Battlefield
 
Gameplay
Upon running the game, it will prompt Player 1 to place their ships on the game field.
Follow the prompts to input coordinates for each ship.
Enter coordinates in the format: A1 A5 for a ship that occupies cells A1 to A5.
After placing Player 1's ships, it will prompt Player 2 to do the same.
Players will take turns shooting at each other's ships by inputting coordinates to fire.
The game will display the boards showing the fog of war for the current player and their own board.
Messages will indicate hits, misses, or when a ship is sunk.
Players will alternate turns until one player wins by sinking all the opponent's ships.
Code Structure
The code consists of:

Main Class: Handles the game's main flow, player turns, and win conditions.
Player Class: Manages player-specific actions like ship placement, shooting, and board management.
Ship Class: Represents each ship with its name, length, direction, and parts.
Key Concepts
Board: Represented as a 10x10 grid where ships are placed and shots are fired.
Ships: Five types of ships with different lengths are available for each player to place.
Fog of War: Each player has a separate board for their view of the opponent's board, showing hits and misses.
Feel free to explore the code, make improvements, or use it as a reference for your own projects. Enjoy playing Battleship!
