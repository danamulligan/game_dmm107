# DESIGN.md

## NAME: Dana Mulligan (dmm107)
 
### Design Goals
In designing the project, I wanted to make it easy to **add different types of bricks, levels, and power ups quickly**, and I think that I succeeded! Once I had the bare bones down, it took very little time to add different types of bricks (what I mean by that: bricks that take a different amount of hits to break, and bricks that have powerups and penalties). As described below, adding another level doesn't take much time or effort, and adding a powerup is also described below. Once I had written the ``PowerUpBrick``, ``PowerUp``, ``PenaltyBrick``, and ``Penalty`` Classes, it became really easy to add another type of power up. Adding a new power up that doesn't change the state of any object currently in game play (such as making the ``Shield``) didn't take any time either, and I had it working in under 30 minutes (which is fast for me). So while I struggled in getting the Levels to work, once it was there it was easy to do what I had wanted to do.

### High Level Design
The core classes (related to the *basic* functioning of a breakout game) are:
``Ball``, ``Brick``, ``EndScreen``, ``Level``, ``Main``, ``Paddle``, ``SplashScreen``, and ``Stats``.

**Purposes and interactions of each:**
* ``Ball``
    * Hit ``Bricks`` to score points (stored in ``Stats``), and move through each ``Level`` in the game.
* ``Brick``
    * The building blocks of a ``Level``, a ``Brick`` takes a certain number of hits to break. Destroyed if a ``Ball`` hits it that many times.
* ``EndScreen``
    * Gives the player feedback on how many points they scored, and signifies that the game is over.
* ``Level``
    * Provide the environment of ``Bricks`` for the ``Ball`` to break, serve as a challenge for the player to overcome. If all bricks in a level are destroyed, the level is cleared.
* ``Main``
    * Control gameplay, including all player<->game interactions
    * Starts with a ``SplashScreen`` and waits for player input. Then, sets up the first ``Level`` with a ``Ball``, a ``Paddle``, lives and initial points (stored in ``Stats``), and switches between ``Levels`` until the game is over. Then, switches to an ``EndScreen``.
* ``Paddle``
    * move along the bottom of the screen, and bounce the ``Ball`` back up towards the bricks
* ``SplashScreen``
    * A basic start up screen explaining the rules of the breakout game, and allowing a player to start the game.
* ``Stats``
    * Holds all of the information of each game (how many points, lives, and more (unrelated to core functionality)).

### Assumptions and Decisions Made to Simplify Design
* The level files will have ONLY VALID integers in the file
    * No regular bricks will take over 7 hits to break
        * AKA level files cannot contain int values >7 that were not assigned to a PowerUp
            * This was made so that I could implement PowerUps using integers instead of other characters
    * No negative values with the exception of the Penalties
* The level files will follow the naming sceme of 'level' followed by the level number
* There will only ever be one PowerUp or Penalty on the screen at one time (they're spaced out to try and avoid this). This also assumes that the player will not cheat to break bricks they cannot reach with the ball (aka any bricks 'trapped' by other bricks)
    * This way, I can easily move the PowerUp/Penalty down to the paddle


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
 
3.**Adding a Bonus Ball**

While I never got around it it, I had plans to make a round where the player could also shoot extra balls from the paddle. I set up this decision by setting all methods to do with the ball to take a parameter of a ``Ball``, so that later I could just pass in the new bonus balls.

Examples:
* In `Main` lines 191-194,
* 203-210, 
* 215-223, 
* 296-316, 
* 319-324, and more.

I even set up my code so that the ball could be launced from the paddle regardless of where it was on the screen (lines 449-443 in ``Main``), but never got around to actually making that feature. I didn't fully finish, but the path was there. I would have made a `BonusBall` class that ``extends Ball``, so that it could be treated like a Ball and still function even if it looked physically different.


4. **Adding more PowerUps/Penalties**

    Adding a new PowerUp or Penalty would require adding in another condition in the ``Level`` Class, below line 71, as well as in one of the two methods in Main ``managePenalty()`` on line 398 and ``managePowerUp()`` on line 411, depending on if you were adding a penalty or power up. In these two methods, you would write what you want to happen if the 'package' (the colored circle that drops when a special brick is hit) is caught by the paddle under the same ``if`` statement.
    
    This could be as simple as making a change to the ball (``myBall``) or paddle (``myPaddle``), or creating a new instance of a class (such as what is done on lines 419-421). This class can be created, pre-existing, or extend a pre-existing class if it behaves in the same way.
    
5. **Adding a new Level**
    All that needs to be done to add a new level is to create a new file named properly and following the assumed format (as explained above).
    
    A new cheat key would need to be added (add `` || code == KeyCode.DIGIT#`` to line 370 in Main), and ``skipToLevel(code)`` would need to be updated with an additional 
    ```
    else if (code == KeyCode.DIGIT#){
            myStats.changeLevelNumber(#);
        }    
    ```
    statement. If the changes proposed in 1. were carried out, `finalLevelNum` should be updated to the new total number of levels. Otherwise, change the magic number on line 162 in ``Main``. 