package breakout;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private SplashScreen mySplash;
    private Scene holder;
    private Paddle myPaddle;
    private Ball myBall;
    private Ball bonusBall;
    private boolean startingAllowed = true;
    private int myLives;
    //private int myLevelNumber;
    private int myScore;
    private Level myLevel1;
    private Level myLevel2;
    private Level myLevel3;
    private Laser myLaser;
    private boolean laserIsOnBoard = false;
    private boolean paddleIsNormal;
    private Group root;
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
        stage.setScene(mySplash.getNode());
        stage.setTitle(TITLE);
        myLives = 3;
        myScore = 0;
        //mySplash.getNode().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //if()
        myScene = setupGame(SIZE, SIZE, BACKGROUND, "level1");
        stage.setScene(myScene);

        stage.show();

        Level myLevel = setUpLevel("level1");
        addNewLevelToRoot(myLevel);

        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, myLevel));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    private Scene setupGame (int width, int height, Paint background, String levelName) {
        // create one top level collection to organize the things in the scene
        /*Group*/ root = new Group();
        //myLives = 3; //TODO set up this in each level
        //myScore = 0;

        myBall = new Ball();
        bonusBallExists = false;
        root.getChildren().add(myBall.getNode());

        myPaddle = new Paddle();
        paddleCanMove = false;
        paddleIsNormal = true;
        addPaddleToRoot();

        /*myLevel1 = setUpLevel("level1");
        addNewLevelToRoot(myLevel1);
        myLevel2 = setUpLevel("level2");
        addNewLevelToRoot(myLevel2);
        myLevel3 = setUpLevel("level3");
        addNewLevelToRoot(myLevel3);*/
        /*
        myLevel = setUpLevel(levelName);
        addNewLevelToRoot(myLevel);*/

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
    private void step (double elapsedTime, Level myLevel) {
        // move ball
        //bounce ball on paddle
        hitBricks(myBall.getNode(), "ball", myLevel);
        if(laserIsOnBoard) {
            hitBricks(myLaser.getLeftLaserBeamNode(), "laser left", myLevel);
            hitBricks(myLaser.getRightLaserBeamNode(), "laser right", myLevel);
            myLaser.moveLasers(elapsedTime);
        }
        moveBall(myBall, elapsedTime);
        bounceBallOffPaddle(myBall);
        if(bonusBallExists){
            moveBall(bonusBall, elapsedTime);
            bounceBallOffPaddle(bonusBall);
        }
        bounceBallsOffWalls();
    }
    private void moveBall(Ball ballToMove, double elapsedTime){
        ballToMove.updateBallCenter(elapsedTime);
    }
// ====================================================================================================================
// managing collisions
// ====================================================================================================================
    private void bounceBallsOffWalls(){
        if(myBall.ballCenterX()+BALL_RADIUS>=SIZE || myBall.ballCenterX()<=0) {
            myBall.bounceSideWall();
            //myBall.resetHitCapabilities();
        }
        if(myBall.ballCenterY()<=0) {
            myBall.bounceY();
            myBall.resetHitCapabilities();
        }
        if(myBall.ballCenterY()+BALL_RADIUS>=SIZE) {
            myLives--;
            resetGame();
        }/*
        if(bonusBallExists && (bonusBall.ballCenterX()+BALL_RADIUS>=SIZE || bonusBall.ballCenterX()<=0)) {
            bonusBall.bounceSideWall();
            //myBall.resetHitCapabilities();;
        }
        if(bonusBallExists && bonusBall.ballCenterY()<=0) {
            bonusBall.bounceY();
            myBall.resetHitCapabilities();
        }*/
        if(myLives == 0){
            endGame();
        }
    }
    private void hitBricks(Shape breaker, String label, Level myLevel){
        //TODO change this to send it down into level class
        Brick myBrick;

        for(int row = 0; row<SIZE/Brick.BRICK_HEIGHT; row++) {
            for (int col = 0; col < SIZE/Brick.BRICK_WIDTH; col++) {
                myBrick = myLevel.myBricks[row][col];
                boolean breakerHitStatus = getBreakerHitStatus(label);
                if (!myBrick.isBrickDestroyed() && breakerHitStatus){//&&!myBrick.getHitStatus()){
                    Shape intersectionBrick = Shape.intersect(breaker, myBrick.getNode());
                    if(intersectionBrick.getBoundsInLocal().getWidth() != -1 /*&& myBrick.getHitsRemaining()!=0 && !myBrick.getHitStatus()*/) {
                        if(label.equals("ball")){
                            myBall.bounceY();
                            //myBall.bounceSideBrick();
                            myBall.ballHitABrick();
                        }
                        myBrick.brickIsHit();
                        if(label.equals("laser left")) {
                            myLaser.leftLaserHitABrick();
                            //root.getChildren().remove(myLaser.getLeftLaserBeamNode());
                            myLaser.destroyLaserBeamLeft();
                        }
                        if(label.equals("laser right")){
                            myLaser.rightLaserHitABrick();
                            //root.getChildren().remove(myLaser.getRightLaserBeamNode());
                            myLaser.destroyLaserBeamRight();
                        }
                        //myBrick.resetHitStatusToFalse();
                        myScore += 100;
                    }
                    if (myBrick.getHitsRemaining() == 0) {
                        myBrick.deleteBrick();
                        root.getChildren().remove(myBrick.getNode());
                    }
                }
            }
        }
    }
    private void bounceBallOffPaddle(Ball ballToBounce){
        Shape intersectionLeft = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeLeft());
        Shape intersectionMiddle = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeMiddle());
        Shape intersectionRight = Shape.intersect(ballToBounce.getNode(), myPaddle.getNodeRight());
        if (intersectionLeft.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bouncePaddleLeft();
            //myBall.resetHitCapabilities();
        }
        if (intersectionMiddle.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bouncePaddleMiddle();
            //myBall.resetHitCapabilities();
        }
        if (intersectionRight.getBoundsInLocal().getWidth() != -1) {
            ballToBounce.bouncePaddleRight();
            //myBall.resetHitCapabilities();
        }
        if((intersectionLeft.getBoundsInLocal().getWidth()!=-1 || intersectionRight.getBoundsInLocal().getWidth()!=-1) && intersectionMiddle.getBoundsInLocal().getWidth()!=-1){
            ballToBounce.bounceY();
           // myBall.resetHitCapabilities();
        }
    }
// ====================================================================================================================
// key input (cheat keys)
// ====================================================================================================================
    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && paddleCanMove) { //might need to rename this var, or make it a dif one
            //TODO create a separate paddle allowed var?
            myPaddle.movePaddle("right");
        }
        else if (code == KeyCode.LEFT && paddleCanMove) {
            myPaddle.movePaddle("left");
        }
        else if (code == KeyCode.SPACE && startingAllowed){
            startingAllowed = false;
            paddleCanMove = true;
            myBall.beginMovingBall();
            //TODO
        }
        else if (code == KeyCode.G && paddleIsNormal){ //grow
            removePaddleFromRoot();
            myPaddle = new Paddle(DOUBLE_IN_SIZE, "grow");
            paddleIsNormal = false;
            addPaddleToRoot();
        }
        else if (code == KeyCode.S && !paddleIsNormal){ //shrink
            removePaddleFromRoot();
            myPaddle = new Paddle();//DOUBLE_IN_SIZE, "shrink");
            paddleIsNormal = true;
            addPaddleToRoot();
        }
        else if (code == KeyCode.F){ //fast
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, "fast");
        }
        else if (code == KeyCode.T){ //slow like a turtle
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, "slow");
        }
        else if (code == KeyCode.Z){
            myLaser = new Laser(myPaddle.getLeftX(),myPaddle.getActualPaddleWidth());
            laserIsOnBoard = true;
            root.getChildren().addAll(myLaser.getLeftLaserBeamNode(), myLaser.getRightLaserBeamNode());
        }
        else if (code == KeyCode.L){ //add a life
            myLives++;
            System.out.println("Plus one life! You now have "+myLives+" lives");
        }
        else if (code == KeyCode.R){ //reset
            resetGame();
            myLives = STARTING_LIVES;
        }
        else if (code == KeyCode.Q){ //quit
            endGame();
        }
        else if (code == KeyCode.DIGIT1){
            changeToLevel(1);
        }
        else if (code == KeyCode.DIGIT2){
            changeToLevel(2);
        }
        else if (code == KeyCode.DIGIT3){
            changeToLevel(3);
        }
    }
// ====================================================================================================================
// helper methods!
// ====================================================================================================================
    public void resetGame(){
        startingAllowed = true;
        paddleCanMove = false;
        updateRootNewBall();
        updateRootNewPaddle(); //to reset it to the center
    }
    public void endGame(){
        //TODO
        //display end game value
        //for now, so that I can see that it's working, not let space be pressed!
        myBall.freeze();
        startingAllowed = false;
        paddleCanMove = false;
    }
    public void changeToLevel(int levelNumber) {

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
    public void addNewLevelToRoot(Level levelToBeAdded){
        for(int row=0; row<SIZE/BRICK_HEIGHT; row++){
            for(int col=0; col<SIZE/BRICK_WIDTH; col++){
                root.getChildren().add(levelToBeAdded.getBrickNode(row,col));
            }
        }
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
