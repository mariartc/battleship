# Battleship game

This project was created for the purposes of the [Multimedia Techonology Laboratory](http://hscnl.ece.ntua.gr/index.php/teaching/undergraduate/multimedia-technology) at [ECE NTUA](https://www.ece.ntua.gr/en).
It's an implementation of classic battleship game, using JavaFX.

## Overview
In the implemented game, the user plays against the computer alternately. Each player has 40 shots in order to hit the oponent's ships, gaining points with each successfull shot. The game ends when all the ships of one player are sunk, or when all of the players' available shots are called. 
Computer has an elementary intelligence, being able to hit neighboring cells of their last successfull shot.

## Ship placement
User can load a ship placement scenario for both them and the oponent by adding a txt file in the medialab folder, following the style of the existing ones.

## Procedure
The player starts the game by running the `Battleship class main function`. After the pop up window appears, they can load one of the existing ship placement scenarios in medialab folder by pressing the Load option in the Application menu. The game starts when the Start option is selected. The player can see two boards - the one with their ships and the enemy's shots, the other with their shots on the oponent's board. Beneath the boards there is a form in which the desirable coordinates are entered. In the Details menu, the player can see some of the game's data in detail. User can exit the game anytime by pressing the Exit option in Application menu.
