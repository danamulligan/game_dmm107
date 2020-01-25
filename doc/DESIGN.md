# DESIGN.md

## NAME: Dana Mulligan (dmm107)

This document is typically an update of a planning Design Document to describe the final, implemented, version of the code to provide a detailed roadmap for other developers to understand your project's design goals.

This file should be included in the doc folder of your project repository and contain the following information:

- [x] names of all people who worked on the project    
- [x] each person's role in developing the project
- [ ] what are the project's design goals, specifically what kinds of new features did you want to make easy to add
- [ ] describe the high-level design of your project, focusing on the purpose and interaction of the core classes
- [ ] what assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
- [x] describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline
 
 
### Design Goals
In designing the project, I wanted to make it easy to **add different types of bricks, levels, and power ups quickly**. 

### High Level Design


### Detailed Design (specific examples)

### Assumptions and Decisions Made to Simplify Design
* The level files will have ONLY VALID integers in the file
    * No regular bricks will take over 7 hits to break
        * AKA level files cannot contain int values >7 that were not assigned to a PowerUp
            * This was made so that I could implement PowerUps using integers instead of other characters
    * No negative values with the exception of the Penalties
* The level files will follow the naming sceme of 'level' followed by the level number


### Adding New Features
Adding a new feature would require two things: adding a new class/updating an existing one, and updating Main to create the new feature in the game. 

1. For example, I never added the feature of **different end screens** depending on whether the game was won or not. The way I would go about doing that would be to add one new method to the ``EndScreen`` Class, such as ``updateGameResults(boolean gameWon)`` and a new private instance variable such as ``private String endMessage``, which would be instanciated to ``Game Over! You lost :(`` in the constructor.
 
    The method method would change ``endMessage`` to something positive (``"You won the game!"``) depending on if ``gameWon`` was ``true``.  Then, ``endMessage`` would be added to the ``Group root``, which is already returned in one of the other methods in the ``EndScreen`` class, ``public Group getRoot()``. 

    example code:
    ```
    public void updateGameResults(boolean gameWon){
        if(gameWon) endMessage = "You won the game";
    }
    ```

    In Main, I would need to add a call of this new method inside of the method ``endGame()`` between lines 492 and 493, and I would pass in ``myLevelNumber==finalLevelNum && myLevel.isClear()`` as the condition. That way, the only way the game can be won is if all bricks on level 3 (the final level) have been hit. (``finalLevelNum`` would need to be initialized in the beginning of Main as a private int variable, set to the total number of levels)

2. **Adding Display of Number of Lives and Score During Gameplay**
    In the ``Stats`` class, add a method that returns a ``Group``
    
    pseudo(ish) code:
    ```
        public Group displayLivesAndScore(){
            Group root = new Group();
            
            Text message = new Text("Lives: " + myLives + "\n Score: " + myScore);
            message.setY(Main.SIZE - Main.GAP - messageHeight); 
            \\where messageHeight is determined based on how it looks
            
            root.getChildren().add(message);
            return root;
        }
    ```
 in ``Main``, add this Group to Main's ``root`` in ``step()`` (line 144), but before doing that remove the *previous* Group containing the previous score and lives stats. Do that by adding another method to ``Main`` that serves that purpose.
 
3. **Adding more PowerUps/Penalties**

    Adding a new PowerUp or Penalty would require adding in another condition in the ``Level`` Class, below line 71, as well as in one of the two methods in Main ``managePenalty()`` on line 398 and ``managePowerUp()`` on line 411, depending on if you were adding a penalty or power up. In these two methods, you would write what you want to happen if the 'package' (the colored circle that drops when a special brick is hit) is caught by the paddle under the same ``if`` statement.
    
    This could be as simple as making a change to the ball (``myBall``) or paddle (``myPaddle``), or creating a new instance of a class (such as what is done on lines 419-421). This class can be created, pre-existing, or extend a pre-existing class if it behaves in the same way.
    
4. **Adding a new Level**
    All that needs to be done to add a new level is to create a new file named properly and following the assumed format (as explained above).
    
    A new cheat key would need to be added (add `` || code == KeyCode.DIGIT#`` to line 370 in Main), and ``skipToLevel(code)`` would need to be updated with an additional 
    ```
    else if (code == KeyCode.DIGIT#){
            myStats.changeLevelNumber(#);
        }    
    ```
    statement. If the changes proposed in 1. were carried out, `finalLevelNum` should be updated to the new total number of levels. 

 
