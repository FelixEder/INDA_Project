# Gunslinger duel
Gunslinger duel is a simple game where 2 players battle it out with their smoking barrels. This fast-paced strategy game forces the two players to make quick decisions in order to outsmart their opponent and win.

When a duel has begun, the players are presented with three combat options:
- **Shoot:** Fire one of your bullets at your opponent! They will lose 1 stock only if they reload.
- **Shield:** Protect yourself from bullets while saving all your ammunition.
- **Reload:** Increase your weapons magazine by 1 bullet, but you will lose a stock if your opponent shoots.

The game is turned based, where both players have to make a move within a set time-frame. It is fairly similar to the Rock Paper Scissors game in terms of gameplay. If you do not make a move during your turn it will be randomized for you.

When your opponent loses all their stocks, you win!

**Testing strategy**

In the beginning of the project, we planned out the different classes and how they should relate to each other. We then wrote JUnit test code for the lower classes (player, AI) were it was harder to see if they where working correctly and then wrote the actual code for them.

For the classes that handle print outs (and then later graphics) we wrote the code and then simply played the game and made sure that the correct output was given for the corresponding input.

**How to play**

A .jar file has been created in the bin-folder. Enter the command "java -jar GunslingerDuel.jar" when in the bin-folder and the game should start.

### This repo is a copy from the actual repo which was used during the course and therefor the all the files where added in one commit
