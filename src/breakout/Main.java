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

/**
 * @author danamulligan
 * Purpose: Control breakout gameplay
 * Assumptions:
 *      There can only ever be one penalty or power up on the screen at one time, otherwise it'll get stuck as it falls
 *      The Ball can only break one brick at a time. It must bounce off of a wall or the paddle to break another one
 * Main depends on the following classes:
 *      Ball
 *      Brick
 *      EndScreen
 *      Laser
 *      Level
 *      Paddle
 *      Penalty
 *      PenaltyBrick
 *      PowerUp
 *      PowerUpBrick
 *      Shield
 *      SplashScreen
 *      Stats
 *          and the breakout package
 * To play the breakout game, run main
 */
public class Main extends Application{
    /**
     * Start of the program.
     */
    public static final String TITLE = "Dana's Game";
    public static final int SIZE = 420;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Color BACKGROUND = Color.AZURE;
    public static final int GAP = 10;
    public static final int DOUBLE_IN_SIZE = 2;
    public static final int ONE_BRICK_WORTH_OF_POINTS = 100;

    private Scene myScene;
    private Stage myStage = new Stage();
    private SplashScreen mySplash;
    private EndScreen myEndScreen;
    private Level myLevel;
    private Laser myLaser;
    private Stats myStats;
    private Shield myShield;
    private Paddle myPaddle;
    private PowerUp myPowerUp;
    private Penalty myPenalty;
    private Ball myBall;
    private Group root;
    private Timeline animation;

// ====================================================================================================================
// beginning the game
// ====================================================================================================================
    // some things needed to remember during game

    /**
     * Create the splash screen, and wait for the player to start the game.
     * Once the game is started, put level 1 on the screen by setting stage's scene to the first level
     * begin the Timeline to allow for gameplay
     * @param stage
     */
    @Override
    public void start (Stage stage) {
        // create a tracker for the status of the game variables
        myStats = new Stats();

        // create a splash screen and set the stage with it
        mySplash = new SplashScreen();
        mySplash.start(myStage);
        myStage.setScene(mySplash.getNode());
        myStage.setTitle(TITLE);

        //get ready for the game starting
        changeMyLevelToLevel(myStats.getMyLevelNumber());
        myScene = setupGame(SIZE, SIZE, BACKGROUND);//, myLevel);

        //start game
        mySplash.getButton().setOnAction(e -> myStage.setScene(myScene));
        myStage.show();

        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        //below code in this method is sourced from lab_bounce in lab_example
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, stage));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    private Scene setupGame (int width, int height, Paint background){
        // create one top level collection to organize the things in the scene <-comment sourced from lab_bounce
        root = new Group();

        //create moving variables and add them to that top level collection
        myBall = new Ball();
        root.getChildren().add(myBall.getNode());

        myPaddle = new Paddle();
        myStats.setPaddleCanMove(false);
        myStats.setPaddleIsNormal(true);
        addPaddleToRoot();

        //add the bricks of the level to the top level collection
        root.getChildren().add(getLevelRoot(myLevel));

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);

        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }
    private Level setUpLevel(String levelName){
        // create a Level
        File filename = new File(this.getClass().getClassLoader().getResource(levelName).getFile());
        return new Level(filename);
    }
// ====================================================================================================================
// playing the game
// ====================================================================================================================
    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime, Stage stage) {
        // move ball around the screen, and check for hitting of bricks
        moveAndHitWithBall(myBall, "ball", elapsedTime);

        // if there is a laser, move the laser and check for hitting of bricks
        if(myStats.isThereALaserOnBoard()) {
            moveAndHitWithLasers();
        }

        // if there is a power up or penalty, move it towards the paddle and check for catching it
        if(myStats.getPowerUpsCount()>0){
            movePackage(myPowerUp, elapsedTime);
            catchPenaltyOrPowerUp(myPowerUp);
        }
        if(myStats.doesPenaltyExist()){
            movePackage(myPenalty, elapsedTime);
            catchPenaltyOrPowerUp(myPenalty);
        }

        // if all of the bricks in the current level have been cleared from the scene, check if the game is completed or move to the next level
        if(myLevel.isClear()){
            if(myStats.getMyLevelNumber() == 3){
                endGame();
            } else {
                myStats.nextLevelNumber();
                changeToNewLevelScene();
            }
        }
    }
    private void skipToLevel(KeyCode code){
        if(code == KeyCode.DIGIT1){
            myStats.changeLevelNumber(1);
        }else if(code == KeyCode.DIGIT2){
            myStats.changeLevelNumber(2);
        }else if(code == KeyCode.DIGIT3){
            myStats.changeLevelNumber(3);
        }
        // change the scene to the one associated with the new level
        changeToNewLevelScene();
    }
    private void changeToNewLevelScene() {
        // create the new level
        changeMyLevelToLevel(myStats.getMyLevelNumber());

        // create the new scene, and attach it to stage
        myScene = setupGame(SIZE,SIZE,BACKGROUND);
        myStage.setScene(myScene);
        myStage.show();
    }

    private void movePackage(PowerUp myPackage, double elapsedTime){
        myPackage.updateCenter(elapsedTime);
    }
    private void moveBall(Ball ballToMove, double elapsedTime){
        ballToMove.updateBallCenter(elapsedTime);
    }
    private void moveAndHitWithLasers(){
        hitBricks(myLaser.getLeftLaserBeamNode(), "laser left", myLevel);
        hitBricks(myLaser.getRightLaserBeamNode(), "laser right", myLevel);
        myLaser.moveLasers();
        updateLaserStatus();
    }
    private void moveAndHitWithBall(Ball ball, String label, double elapsedTime){
        if(myStats.doesShieldExist()){
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
        if(ball.ballCenterX()+Ball.BALL_RADIUS>=SIZE || ball.ballCenterX()<=0) {
            ball.bounceSideWall();
        }
        if(ball.ballCenterY()<=0) {
            ball.bounceY();
            ball.resetHitCapabilities();
        }
        if(ball.ballCenterY()+Ball.BALL_RADIUS>=SIZE) {
            myStats.updateLives(-1);
            resetBallAndPaddle();
        }
        if(myStats.getMyLives() == 0){
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
                        if(label.equals("laser left")) {
                            myLaser.leftLaserHitABrick();
                        }
                        if(label.equals("laser right")){
                            myLaser.rightLaserHitABrick();
                        }
                        updateScoreAndDropPackages(myBrick);
                    }
                }
            }
        }
    }
    private void updateScoreAndDropPackages(Brick myBrick){
        myBrick.brickIsHit();
        myStats.updateScore(ONE_BRICK_WORTH_OF_POINTS);
        if(myBrick instanceof PenaltyBrick){
            weHavePenalty(myBrick);
        } else if(myBrick instanceof PowerUpBrick){
            weHavePowerUp((PowerUpBrick) myBrick);
        }
        if (myBrick.isBrickDestroyed()) {
            myBrick.deleteBrick();
            myLevel.incrementBricksDestroyed();
            root.getChildren().remove(myBrick.getNode());
        }
    }
    private void weHavePenalty(Brick myBrick){
        myPenalty = ((PenaltyBrick) myBrick).getPackage();
        myStats.setPenaltyStatus(true);
        myLevel.incrementBricksDestroyed();
        root.getChildren().add(myPenalty.getNode());
    }
    private void weHavePowerUp(PowerUpBrick myBrick){
        myPowerUp = myBrick.getPackage();
        myStats.changePowerUpCount(1);
        myLevel.incrementBricksDestroyed();
        root.getChildren().add(myPowerUp.getNode());
    }
    private void catchPenaltyOrPowerUp(PowerUp myPackage){
        Shape intersectionLeft = Shape.intersect(myPackage.getNode(), myPaddle.getNodeLeft());
        Shape intersectionMiddle = Shape.intersect(myPackage.getNode(), myPaddle.getNodeMiddle());
        Shape intersectionRight = Shape.intersect(myPackage.getNode(), myPaddle.getNodeRight());
        if (intersectionLeft.getBoundsInLocal().getWidth() != -1 || intersectionMiddle.getBoundsInLocal().getWidth() != -1 || intersectionRight.getBoundsInLocal().getWidth() != -1) {
            String type = myPackage.getType();
            myPackage.caught();
            root.getChildren().remove(myPackage);
            if (myPackage instanceof Penalty){
                managePenalty(type);
            } else {
                managePowerUp(type);
            }
        }
    }

    private void bounceBallOffPaddle(Ball ballToBounce){
        Shape intersectionLeft = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeLeft());
        Shape intersectionMiddle = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeMiddle());
        Shape intersectionRight = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeRight());
        // if the ball is sticky, it'll stick to the paddle if it hits it
        if(myStats.isBallSticky() && (intersectionLeft.getBoundsInLocal().getWidth()!=-1 || intersectionRight.getBoundsInLocal().getWidth()!=-1 || intersectionMiddle.getBoundsInLocal().getWidth()!=-1)){
            ballToBounce.freeze();
            ballToBounce.resetHitCapabilities();
        }
        if (intersectionLeft.getBoundsInLocal().getWidth() != -1 && !myStats.isBallSticky()) {
            ballToBounce.bouncePaddleLeft();
        }
        if (intersectionMiddle.getBoundsInLocal().getWidth() != -1 && !myStats.isBallSticky()) {
            ballToBounce.bouncePaddleMiddle();
        }
        if (intersectionRight.getBoundsInLocal().getWidth() != -1 && !myStats.isBallSticky()) {
            ballToBounce.bouncePaddleRight();
        }
        //the edge case for where two of the three rectangles that make up the paddle interact
        if(((intersectionLeft.getBoundsInLocal().getWidth()!=-1 || intersectionRight.getBoundsInLocal().getWidth()!=-1) && intersectionMiddle.getBoundsInLocal().getWidth()!=-1) && !myStats.isBallSticky()){
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
        if(myStats.isThereALaserOnBoard()){
            //this case covers if one or both of the laser beams pass the edge... essentially, the case where both miss, or one hits and the other misses
            if(myLaser.getLeftLaserBeamNode().getY()+Laser.LASER_HEIGHT<=0 || myLaser.getRightLaserBeamNode().getY()+Laser.LASER_HEIGHT<=0){
                myStats.setLaserStatus(false);
            }
            //both hit a target
            if(!myLaser.getLeftLaserHitStatus() && !myLaser.getRightLaserHitStatus()){
                myStats.setLaserStatus(false);
            }
        }
    }

// ====================================================================================================================
// key input (cheat keys)
// ====================================================================================================================
    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if ((code == KeyCode.RIGHT || code == KeyCode.LEFT) && myStats.canPaddleMove()) {
            movePaddleAndStickyBall(code);
        }
        else if ((code == KeyCode.A || code == KeyCode.D) && myStats.canPaddleMove()) { //keys were chosen from gaming keys
            myPaddle.setXPositions(code);
        }
        else if (code == KeyCode.SPACE && myStats.isStartingAllowed()){ //start moving the ball
            beginGameplay();
        }
        else if ((code == KeyCode.G && myStats.isPaddleNormal()) || (code == KeyCode.S && !myStats.isPaddleNormal() && myStats.canPaddleMove())){ //grow or shrink the paddle
            changePaddle(code);
        }
        else if (code == KeyCode.F || code == KeyCode.T){ //make the ball faster or slower
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, code);
        }
        else if (code == KeyCode.Z){ //pew pew pew!!!
            shootLasers();
        }
        else if (code == KeyCode.L || code == KeyCode.R || code == KeyCode.Q){ //'l' for add a life, 'r' for reset, and 'q' for quit
            addLifeResetOrQuit(code);
        }
        else if (code == KeyCode.SHIFT){ // shift key was chosen because of sticky keys on the mac
            myStats.setBallIsSticky(true);
            myStats.setStartingAllowed(true);
        }
        else if (code == KeyCode.DIGIT1 || code == KeyCode.DIGIT2 || code == KeyCode.DIGIT3){
            skipToLevel(code);
        }
    }
// ====================================================================================================================
// mouse input (more cheat keys)
// ====================================================================================================================

    private void handleMouseInput (double x, double y) {

        // hit bricks by clicking on them
        for(int row = 0; row<SIZE/Brick.BRICK_HEIGHT; row++) {
            for (int col = 0; col < SIZE / Brick.BRICK_WIDTH; col++) {
                Brick myBrick = myLevel.myBricks[row][col];
                if (myBrick.getNode().contains(x, y)) {
                    updateScoreAndDropPackages(myBrick);
                }
            }
        }
        // grow ball if it's clicked
        if(myBall.getNode().contains(x,y)){
            myBall.grow();
        }

    }
// ====================================================================================================================
// helper methods!
// ====================================================================================================================
    private void managePenalty(String type){
        if(type.equals("neg")) {
            myStats.updateScore(-1*ONE_BRICK_WORTH_OF_POINTS);
        }
        if(type.equals("ball speed")) {
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, KeyCode.F);
        }
        if(type.equals("paddle size")){
            changePaddle(KeyCode.S);
        }
        // penalty has be carried out and is no longer in active game play
        myStats.setPenaltyStatus(false);
    }
    private void managePowerUp(String type){
        if (type.equals("points")) {
            myStats.updateScore(ONE_BRICK_WORTH_OF_POINTS);
        }
        if (type.equals("paddle speed")) {
            myPaddle.changePaddleSpeed(DOUBLE_IN_SIZE);
        }
        if (type.equals("shield")) {
            myShield = new Shield();
            root.getChildren().add(myShield.getNode());
            myStats.shieldAdded();
        }
        if (type.equals("ball radius")){
            myBall.grow();
        }
        // power up is no longer in active game play
        myStats.changePowerUpCount(-1);
    }
    private void addLifeResetOrQuit(KeyCode code){ //this method has to do with changing lives
        if(code == KeyCode.L){
            myStats.updateLives(1);
        }
        if(code == KeyCode.R){
            resetLevel();
            myStats.resetLives();
        }
        if(code == KeyCode.Q){
            myStats.dead();
        }
    }
    private void shootLasers(){
        // don't shoot a laser if there is already one on the screen
        if(!myStats.isThereALaserOnBoard()){
            myLaser = new Laser(myPaddle.getLeftX(),myPaddle.getActualPaddleWidth());
            myStats.setLaserStatus(true);
            root.getChildren().addAll(myLaser.getLeftLaserBeamNode(), myLaser.getRightLaserBeamNode());
        }
    }
    private void movePaddleAndStickyBall(KeyCode code){
        myPaddle.movePaddle(code);
        // check to make sure the ball is 'frozen' aka touching the paddle by checking the y speed is 0
        if(myStats.isBallSticky()&&myBall.getBallYSpeed()==0) {
            myBall.moveBall(code);
        }
    }
    private void changePaddle(KeyCode code){
        removePaddleFromRoot();
        //shrink
        if(code == KeyCode.S){
            myPaddle = new Paddle(code);
            myStats.setPaddleIsNormal(true);
        }
        //grow
        else if (code == KeyCode.G){
            myPaddle = new Paddle(DOUBLE_IN_SIZE, code);
            myStats.setPaddleIsNormal(false);
        }
        addPaddleToRoot();
    }
    private void beginGameplay() {
        // the kraken has been unleashed
        myStats.setPaddleCanMove(true);
        myBall.beginMovingBall();
    }
    private void resetLevel(){
        root.getChildren().remove(getLevelRoot(myLevel));
        changeMyLevelToLevel(myStats.getMyLevelNumber());
        root.getChildren().add(getLevelRoot(myLevel));
        resetBallAndPaddle();
    }
    private void resetBallAndPaddle(){
        myStats.setStartingAllowed(true);
        myStats.setPaddleCanMove(false);
        myStats.setPaddleIsNormal(true);
        updateRootNewBall();
        updateRootNewPaddle();
    }

    private void endGame(){
        animation.stop();
        myEndScreen = new EndScreen();
        myEndScreen.setGameScore(myStats.getMyScore());
        myEndScreen.start(myStage);

        root.getChildren().add(myEndScreen.getRoot());
        myStage.setScene(myEndScreen.getNode());

        removePaddleFromRoot();
        root.getChildren().remove(myBall);
    }
    private void changeMyLevelToLevel(int levelNumber) {
        myStats.resetLives();
        myLevel = setUpLevel("level"+levelNumber);
    }
    private void updateRootNewBall(){
        root.getChildren().remove(myBall.getNode());
        myBall = new Ball();
        root.getChildren().add(myBall.getNode());
    }
    private void updateRootNewPaddle(){
        removePaddleFromRoot();
        myPaddle = new Paddle();
        addPaddleToRoot();
    }
    private boolean getBreakerHitStatus(String label){
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

    /**
     * create a sub collection consisting of the bricks in the level levelToBeAdded to add to the larger top collection
     * @param levelToBeAdded
     * @return the sub collection levelRoot
     */
    private Group getLevelRoot(Level levelToBeAdded){
        Group levelRoot = new Group();
        for(int row=0; row<SIZE/Brick.BRICK_HEIGHT; row++){
            for(int col=0; col<SIZE/Brick.BRICK_WIDTH; col++){
                levelRoot.getChildren().add(levelToBeAdded.getBrickNode(row,col));
            }
        }
        return levelRoot;
    }
    /**
     * reason why I have this method is because it's "one" object, and I want to read it as such
     */
    private void removePaddleFromRoot(){
        root.getChildren().removeAll(myPaddle.getNodeLeft(), myPaddle.getNodeMiddle(), myPaddle.getNodeRight());
    }
    /**
     * reason why I have this method is because it's "one" object, and I want to read it as such
     */
    private void addPaddleToRoot(){
        root.getChildren().addAll(myPaddle.getNodeLeft(), myPaddle.getNodeMiddle(), myPaddle.getNodeRight());
    }
    public static void main (String[] args) {
        launch(args);
    }
}
