game
====

This project implements the game of Breakout.

Name: Dana Mulligan (dmm107)

### Timeline

Start Date: Friday, January 10th, 2020

Finish Date: Jan 19th, 2020

Hours Spent: 
- plan: 3 
- complete: 30

### Resources Used


### Running the Program

Main class: Main

Data files needed: level1, level2, and level3

Key/Mouse inputs:
* all of the cheat keys
* click the button on the splash screen to move to game
* Left and right key to move paddle
* space bar to shoot ball if it's on paddle

Cheat keys:
* 'A' to move paddle to the far left
* "D" to move paddle to the far right
* 'F' to make the ball move faster
* 'T' to make the ball move slower
* 'G' to grow the paddle
* Click on a brick to destroy it
* 'L' to add a life
* 'R' to reset level with 3 lives
* 'Q' to quit level
* 'Z' to shoot lasers
* 'shift key' to cause the ball to be sticky (stick to the paddle)
* '1' to move to level 1, '2' for level 2, '3' for level 3
* Click on ball to make it bigger

Known Bugs:
The physics of the ball hitting the bricks isn't always right. If it hits a brick on the side,
it will bounce down instead of going up again. This is because I couldn't figure out how to 
differentiate the wall of a brick from the bottom.

Extra credit:
Added multiple something extras:
    lasers that shoot from the paddle and a ball that sticks to the paddle in stickyMode

### Notes/Assumptions
*Lasers can only be shot two at a time (one key press)
*Paddle cannot shrink unless it has grown
*There is no different end screen for winning versus losing, the game just ends
*Score and Lives are not displayed throughout the game.

### Impressions
This project was really difficult. I got stuck on learning javafx, and had a particular hard
time implementing switching between levels as they were cleared, which slowed me down a lot.
I look forward to working with others, I think that will allow me to work faster. I have a better
impression of how I should have treated everything, which I will discuss heavily in the analysis.
I wish that I could have done more in terms of status displays, and comments. But, I am really proud
of what I came up with!
