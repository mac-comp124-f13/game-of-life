Comp 124: Game of Life Lab
====

This lab was originally developed by Michael Ekstrand (ekstrand@cs.umn.edu) and is used with his permission.
Shilad Sen brought it to Macalester and adapted it for Comp 124.
Paul Cantrell (paul@innig.net) rewrote the lab for GitHub and added a bit of flair.

Thanks to everybody who contributed!


The Game of Life
---

The Game of Life is a mathematical “zero-player” game devised by John Conway. It simulates the behavior of populations following simple rules.  Life is played on a grid of cells. Each cell can be either alive or dead.  The game progresses in a series of iterations, called generations.  In each generation, the value of a cell (whether it is alive or dead) is determined by the value of that cell and its neighbors in the previous generation using the following rules:

- If a living cell has less than 2 living neighbors, it will die (of loneliness).
- If a living cell has more than 3 living neighbors, it will die (it’s overcrowded).
- If a dead cell has exactly 3 living neighbors, it will come to life (it is born).
- Otherwise, the cell’s value will not be changed.

For more information on Life, the [Wikipedia article on the topic](http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) is quite informative, as is [Martin Gardner's original article](https://moodle.macalester.edu/mod/resource/view.php?id=12745).


Overview
---

You start this lab with an existing project, which provides a nice user interface for the Game of Life — but the rules are missing!
You will have the following tasks:

- Implement Conway’s rules.
- Create a new rule set implementing a simple variation on Conway’s rules.


Implementing Conway’s Rules
---

Your first task is to implement Conway’s rules for the Game of Life. These are the rules described above in the game description.

Conway.java contains a method, applyRules(), which will be used by the game board to compute the next state in each generation. applyRules() takes two parameters - a boolean value representing the cell’s current value, and an integer representing the number of living neighbors the cell has. It must return the value for that cell in the next generation. true represents a living cell, and false represents a dead cell.

You can test the correctness of your rules by running the "ConwayTest.java" JUnit tests.  To do so, right click (or control-click) on "ConwayTest" and select Run As > JUnit Test.  If a test files, you can look at ConwayTest.java to see the conditions under which it is failing.


Experimenting
---

Once you have the rules implemented and the test cases pass, play with the game by running the MainWindow class as a Java application. It will present you with a game grid and some controls. Create an initial state by clicking various cells — clicking a cell toggles whether it is alive or dead. Then use the ‘Step’ button to run one generation, or the ‘Run’ button to start the simulation performing four generations per second. See if you can create simple shapes that move continuously. Try setting lots of cells randomly and see what happens.


HighLife
---

Your next task is to implement a variant of Conway’s rules, called HighLife. HighLife is exactly like Conway’s game, except there is one additional rule: any dead cell with 6 living neighbors comes to life (this is in addition to the other rules, so cells with 3 neighbors still come to life).

Copy the Conway.java and ConwayTest.java files, creating HighLife.java and HighLifeTest.java files. Also change HighLifeTest so that the setUp() method uses HighLife instead of Conway.

Next, go through the test code in HighLifeTest.java and adjust it so that it is checking for correct play by the HighLife rules rather than the Conway rules.
Add a test method to check that 6 living neighbors bring a cell to life, and modify the other tests as needed to reflect the change in rules.  Once you have the tests implemented, change HighLife to implement the new rules and make the tests pass.

Change HighLife’s getName() method to return the string ”HighLife”.

Finally, once you have the rules implemented, open the MainWindow.java file. In this file, there is a block of code that has been placed in a comment with a note “Uncomment for HighLife”. Remove the comment symbols around this code.

When you run MainWindow, you will now be able to select rule sets.  Have some fun with HighLife.


Optional: Change the Speed
---

Faster is funner! Right now, the simulation only runs four generations per second when you click “Run.” Can you find the spot in the code that sets the speed?

As a programmer, you often have to make a change to a program that is unfamiliar, and possibly too large for you to fully understand in the time available. There is a lot of code in this project. See if you can find where the speed is set, without having to understand everything.


Optional: Add Some Color
---

If you've made it this far, congratulations!  As an extra challenge, try to improve the visualization of the game board by coloring the live cells according to how many neighbors are currently alive.  Some hints:

- Take a look at paintComponent() in the LifeComponent class.
- What color are the alive cells painted right now?
- Where is that color specified in the paintComponent() method?
- Does the board have a method that can help you in calculating the number of live neighbors?
