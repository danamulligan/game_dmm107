package breakout;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application{
    /**
     * Start of the program.
     */
    public static final String TITLE = "Dana's Game";
    public static final int SIZE = 420;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double TEN_SECONDS = SECOND_DELAY*10;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int GAP = 10;
    public static final int BRICK_WIDTH = 20;
    public static final int BRICK_HEIGHT = 20;
    public static final int BALL_RADIUS = 10;
    public static final int BASIC_BALL_SPEED = 100;
    public static final int DOUBLE_IN_SIZE = 2;
    public static final int STARTING_LIVES = 3;

    private Scene myScene;
    private Stage myStage = new Stage();
    private SplashScreen mySplash;
    private EndScreen myEndScreen;
    private Shield myShield;
    private boolean shieldExists = false;
    private Scene holder;
    private Paddle myPaddle;
    private PowerUp myPowerUp;
    private int myPowerUpsCount;
    private Penalty myPenalty;
    private boolean myPenaltyExists = false;
    private Ball myBall;
    private Ball bonusBall;
    private boolean startingAllowed = true;
    private int myLives;
    private int myLevelNumber;
    private int myScore;
    private Level myLevel;
    private Laser myLaser;
    private boolean laserIsOnBoard = false;
    private boolean paddleIsNormal;
    private boolean gamePlayAllowed;
    private Group root;
    private Timeline animation;
    private int deadBrickCounter;
    private boolean paddleCanMove;
    private boolean bonusBallExists = false;

// ====================================================================================================================
// beginning the game
// ====================================================================================================================
    // some things needed to remember during game
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        mySplash = new SplashScreen();
        mySplash.start(myStage);
        myStage.setScene(mySplash.getNode());
        myStage.setTitle(TITLE);
        myLives = STARTING_LIVES;
        gamePlayAllowed = true;
        myScore = 0;
        myPowerUpsCount = 0;
        myLevelNumber=1;
        changeToLevel(myLevelNumber);
        myScene = setupGame(SIZE, SIZE, BACKGROUND);//, myLevel);
        mySplash.getButton().setOnAction(e -> myStage.setScene(myScene));

        //myScene = setupGame(SIZE, SIZE, BACKGROUND, "level1");
        //stage.setScene(myScene);

        myStage.show();

        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        //if(gamePlayAllowed) {
            KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, stage));
            /*Timeline */animation = new Timeline();
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.getKeyFrames().add(frame);
            animation.play();
        //}
    }
    private Scene setupGame (int width, int height, Paint background){//, Level currentLevel) {
        // create one top level collection to organize the things in the scene
        root = new Group();

        deadBrickCounter = 0;
        startingAllowed = true;

        myBall = new Ball();
        bonusBallExists = false;
        root.getChildren().add(myBall.getNode());

        myPaddle = new Paddle();
        paddleCanMove = false;
        paddleIsNormal = true;
        addPaddleToRoot();

        //myLevel = setUpLevel(levelName);
        root.getChildren().add(getLevelRoot(myLevel/*currentLevel*/));

        //root.getChildren().add(bonusBall.getNode());

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }
    private Level setUpLevel(String levelName){
        File filename = new File(this.getClass().getClassLoader().getResource(levelName).getFile());
        return new Level(filename);
    }
// ====================================================================================================================
// playing the game
// ====================================================================================================================
    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime/*, Level myLevel*/, Stage stage) {
        // move ball
        //bounce ball on paddle
        moveAndHitWithBall(myBall, "ball", elapsedTime);
        if(laserIsOnBoard) {
            moveAndHitWithLasers(elapsedTime);
        }
        if(myPowerUpsCount>0){
            movePackage(myPowerUp, elapsedTime);
            catchPenaltyOrPowerUp(myPowerUp);
        }
        if(myPenaltyExists){
            movePackage(myPenalty, elapsedTime);
            catchPenaltyOrPowerUp(myPenalty);
        }
        if(bonusBallExists){
            moveAndHitWithBall(bonusBall, "bonus ball", elapsedTime);
        }
        if(myLevel.isClear()){
            if(myLevelNumber == 3){
                endGame();
            } else {
                myLevelNumber++;
                updateNewLevel();
            }
        }
    }

    private void updateNewLevel() {
        changeToLevel(myLevelNumber);
        myScene = setupGame(SIZE,SIZE,BACKGROUND);//,myLevel);
        myStage.setScene(myScene);
        myStage.show();
    }

    private void movePackage(PowerUp myPackage, double elapsedTime){
        myPackage.updateCenter(elapsedTime);
    }
    private void moveBall(Ball ballToMove, double elapsedTime){
        ballToMove.updateBallCenter(elapsedTime);
    }
    private void moveAndHitWithLasers(double elapsedTime){
        hitBricks(myLaser.getLeftLaserBeamNode(), "laser left", myLevel);
        hitBricks(myLaser.getRightLaserBeamNode(), "laser right", myLevel);
        myLaser.moveLasers(elapsedTime);
        updateLaserStatus();
    }
    private void moveAndHitWithBall(Ball ball, String label, double elapsedTime){
        if(shieldExists){
            bounceBallOffShield(ball);
        }
        hitBricks(ball.getNode(), label, myLevel);
        moveBall(ball, elapsedTime);
        bounceBallOffPaddle(ball);
        bounceBallsOffWalls(ball);
    }
// ====================================================================================================================
// managing collisions
// ====================================================================================================================
    private void bounceBallsOffWalls(Ball ball){
        if(ball.ballCenterX()+BALL_RADIUS>=SIZE || ball.ballCenterX()<=0) {
            ball.bounceSideWall();
            //myBall.resetHitCapabilities();
        }
        if(ball.ballCenterY()<=0) {
            ball.bounceY();
            ball.resetHitCapabilities();
        }
        if(ball.ballCenterY()+BALL_RADIUS>=SIZE) {
            myLives--;
            resetBallAndPaddle();
        }
        if(myLives == 0){
            endGame();
        }
    }
    private void hitBricks(Shape breaker, String label, Level myLevel){
        for(int row = 0; row<SIZE/Brick.BRICK_HEIGHT; row++) {
            for (int col = 0; col < SIZE/Brick.BRICK_WIDTH; col++) {
                Brick myBrick = myLevel.myBricks[row][col];
                if (!myBrick.isBrickDestroyed() && getBreakerHitStatus(label)){
                    Shape intersectionBrick = Shape.intersect(breaker, myBrick.getNode());
                    if(intersectionBrick.getBoundsInLocal().getWidth() != -1) {
                        if(label.equals("ball")){
                            myBall.bounceY();
                            myBall.ballHitABrick();
                        }
                        if(label.equals("bonus ball")){
                            bonusBall.bounceY();
                            bonusBall.ballHitABrick();
                        }
                        if(label.equals("laser left")) {
                            myLaser.leftLaserHitABrick();
                        }
                        if(label.equals("laser right")){
                            myLaser.rightLaserHitABrick();
                        }
                        myBrick.brickIsHit();
                        myScore += 100;
                        if(myBrick instanceof PenaltyBrick){
                            myPenalty = ((PenaltyBrick) myBrick).getPackage();
                            myPenaltyExists = true;
                            myLevel.incrementBricksDestroyed();
                            root.getChildren().add(myPenalty.getNode());
                        } else if(myBrick instanceof PowerUpBrick){
                            myPowerUp = myBrick.getPackage();
                            myPowerUpsCount++;
                            myLevel.incrementBricksDestroyed();
                            root.getChildren().add(myPowerUp.getNode());
                        }
                    }
                    if (myBrick.getHitsRemaining() == 0) {
                        myBrick.deleteBrick();
                        myLevel.incrementBricksDestroyed();
                        root.getChildren().remove(myBrick.getNode());
                    }
                }
            }
        }
    }
    private void catchPenaltyOrPowerUp(PowerUp myPackage){
        Shape intersectionLeft = Shape.intersect(myPackage.getNode(), myPaddle.getNodeLeft());
        Shape intersectionMiddle = Shape.intersect(myPackage.getNode(), myPaddle.getNodeMiddle());
        Shape intersectionRight = Shape.intersect(myPackage.getNode(), myPaddle.getNodeRight());
        if (intersectionLeft.getBoundsInLocal().getWidth() != -1 || intersectionMiddle.getBoundsInLocal().getWidth() != -1 || intersectionRight.getBoundsInLocal().getWidth() != -1) {
            String type = myPackage.getType();
            //myPowerUpsCount--;
            myPackage.caught();
            root.getChildren().remove(myPackage);
            if (myPackage instanceof Penalty){
                if(type.equals("neg")) {
                    myScore -= 100;
                }
                if(type.equals("ball speed")) {
                    myBall.changeBallSpeed(DOUBLE_IN_SIZE, KeyCode.F);;
                }
                if(type.equals("paddle size")){
                    changePaddle(KeyCode.S);
                }
                //System.out.println("oops... shouldn't have caught that!");
                myPenaltyExists = false;
            } else {
                if (type.equals("points")) {
                    myScore += 100;
                }
                if (type.equals("paddleSpeed")) {
                    myPaddle.changePaddleSpeed(DOUBLE_IN_SIZE);
                }
                if (type.equals("shield")) {
                    myShield = new Shield();
                    root.getChildren().add(myShield.getNode());
                    shieldExists = true;
                }
                myPowerUpsCount--;
            }
            //System.out.println("caught");
        }
    }

    private void bounceBallOffPaddle(Ball ballToBounce){
        Shape intersectionLeft = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeLeft());
        Shape intersectionMiddle = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeMiddle());
        Shape intersectionRight = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeRight());
        if (intersectionLeft.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bouncePaddleLeft();
        }
        if (intersectionMiddle.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bouncePaddleMiddle();
        }
        if (intersectionRight.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bouncePaddleRight();
        }
        if((intersectionLeft.getBoundsInLocal().getWidth()!=-1 || intersectionRight.getBoundsInLocal().getWidth()!=-1) && intersectionMiddle.getBoundsInLocal().getWidth()!=-1){
            ballToBounce.bounceY();
        }
    }
    private void bounceBallOffShield(Ball ballToBounce){
        Shape intersection = Shape.intersect(ballToBounce.getNode(), myShield.getNode());
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bounceY();
            myShield.shieldIsHit();
            ballToBounce.resetHitCapabilities();
        }
    }
    private void updateLaserStatus(){
        if(laserIsOnBoard){
            //this case covers if one or both of the laser beams pass the edge... essentially, the case where both miss, or one hits and the other misses
            if(myLaser.getLeftLaserBeamNode().getY()+Laser.Laser_HEIGHT<=0 || myLaser.getRightLaserBeamNode().getY()+Laser.Laser_HEIGHT<=0){
                laserIsOnBoard = false;
            }
            //both hit a target
            if(!myLaser.getLeftLaserHitStatus() && !myLaser.getRightLaserHitStatus()){
                laserIsOnBoard = false;
            }
        }
    }

// ====================================================================================================================
// key input (cheat keys)
// ====================================================================================================================
    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if ((code == KeyCode.RIGHT || code == KeyCode.LEFT) && paddleCanMove) {
            myPaddle.movePaddle(code);
        }
        else if ((code == KeyCode.A || code == KeyCode.D) && paddleCanMove) {
            myPaddle.setXPositions(code);
        }
        else if (code == KeyCode.SPACE && startingAllowed){ //start moving the ball
            beginGameplay();
        }
        else if ((code == KeyCode.G && paddleIsNormal) || (code == KeyCode.S && !paddleIsNormal)){ //grow or shrink the paddle
            changePaddle(code);
        }
        else if (code == KeyCode.F || code == KeyCode.T){ //make the ball faster or slower
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, code);
        }
        else if (code == KeyCode.Z){ //shoot lasers!!
            shootLasers();
        }
        else if (code == KeyCode.L){ //add a life
            myLives++;
            //System.out.println("Plus one life! You now have "+myLives+" lives");
        }
        else if (code == KeyCode.R){ //reset the current level
            resetLevel();
            myLives = STARTING_LIVES;
        }
        else if (code == KeyCode.Q){ //quit
            endGame();
        }
        else if (code == KeyCode.B){
            //TODO Bonus ball??
            //bonusBallExists = true;
            //bonusBall = new Ball();
            //startingAllowed = true;
        }
        else if (code == KeyCode.DIGIT1){
            myLevelNumber = 1;
            updateNewLevel();
        }
        else if (code == KeyCode.DIGIT2){
            myLevelNumber = 2;
            updateNewLevel();
        }
        else if (code == KeyCode.DIGIT3){
            myLevelNumber = 3;
            updateNewLevel();
        }
    }
    // ====================================================================================================================
// helper methods!
// ====================================================================================================================
    private void shootLasers(){
        if(!laserIsOnBoard){
            myLaser = new Laser(myPaddle.getLeftX(),myPaddle.getActualPaddleWidth());
            laserIsOnBoard = true;
            root.getChildren().addAll(myLaser.getLeftLaserBeamNode(), myLaser.getRightLaserBeamNode());
        }
    }
    private void changePaddle(KeyCode code){
        removePaddleFromRoot();
        if(code == KeyCode.S){
            myPaddle = new Paddle(code);
            paddleIsNormal = true;
        } else if (code == KeyCode.G){
            myPaddle = new Paddle(DOUBLE_IN_SIZE, code);
            paddleIsNormal = false;
        }
        addPaddleToRoot();
    }
    private void beginGameplay() {
        startingAllowed = false;
        paddleCanMove = true;
        myBall.beginMovingBall();
    }
    public void resetLevel(){
        root.getChildren().remove(getLevelRoot(myLevel));
        changeToLevel(myLevelNumber);
        root.getChildren().add(getLevelRoot(myLevel));
        resetBallAndPaddle(); //to reset it to the center
    }
    public void resetBallAndPaddle(){
        startingAllowed = true;
        paddleCanMove = false;
        paddleIsNormal = true;
        updateRootNewBall();
        updateRootNewPaddle();
    }

    public void endGame(){
        //TODO
        //display end game value
        //for now, so that I can see that it's working, not let space be pressed!
        animation.stop();
        gamePlayAllowed = false;
        System.out.println("Your score is " +myScore);
        myEndScreen = new EndScreen();
        myEndScreen.setGameScore(myScore);
        myEndScreen.start(myStage);

        root.getChildren().add(myEndScreen.getRoot());
        myStage.setScene(myEndScreen.getNode());
        myBall.freeze();
        startingAllowed = false;
        paddleCanMove = false;
    }
    public void changeToLevel(int levelNumber) {
        myLives = STARTING_LIVES;
        myLevel = setUpLevel("level"+levelNumber);
        //root.getChildren().add(getLevelRoot(myLevel));
    }
    public void updateRootNewBall(){
        root.getChildren().remove(myBall.getNode());
        myBall = new Ball();
        root.getChildren().add(myBall.getNode());
    }
    public void updateRootNewPaddle(){
        removePaddleFromRoot();
        myPaddle = new Paddle();
        addPaddleToRoot();
    }
    public boolean getBreakerHitStatus(String label){
        if(label.equals("ball")){
            return myBall.getHitStatus();
        }
        else if(label.equals("laser left")){
            return myLaser.getLeftLaserHitStatus();
        }
        else if(label.equals("laser right")){
            return myLaser.getRightLaserHitStatus();
        }
        return false;
    }
    public Group getLevelRoot(Level levelToBeAdded){
        Group levelRoot = new Group();
        for(int row=0; row<SIZE/BRICK_HEIGHT; row++){
            for(int col=0; col<SIZE/BRICK_WIDTH; col++){
                levelRoot.getChildren().add(levelToBeAdded.getBrickNode(row,col));
            }
        }
        return levelRoot;
    }
    /**
     * reason why I have this method is because it's "one" object, and I want to read it as such
     */
    public void removePaddleFromRoot(){
        root.getChildren().removeAll(myPaddle.getNodeLeft(), myPaddle.getNodeMiddle(), myPaddle.getNodeRight());
    }
    /**
     * reason why I have this method is because it's "one" object, and I want to read it as such
     */
    public void addPaddleToRoot(){
        root.getChildren().addAll(myPaddle.getNodeLeft(), myPaddle.getNodeMiddle(), myPaddle.getNodeRight());
    }
    public static void main (String[] args) {
        launch(args);
    }
}
