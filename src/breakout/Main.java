package breakout;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//import java.lang.invoke.DelegatingMethodHandle$Holder;

/**
 * Feel free to completely change this code or delete it entirely. 
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
    public static final double TEN_SECONDS = SECOND_DELAY*10;
    public static final Paint BACKGROUND = Color.AZURE;
    //public static final Paint HIGHLIGHT = Color.LAVENDERBLUSH;
    public static final int GAP = 10;
    public static final int BRICK_WIDTH = 20;
    public static final int BRICK_HEIGHT = 20;
    //public static final Paint BALL_COLOR = Color.GREY;
    public static final int BALL_RADIUS = 10;
    public static final int BASIC_BALL_SPEED = 100;
    public static final int DOUBLE_IN_SIZE = 2;
    public static final int STARTING_LIVES = 3;

    private Scene myScene;
    private Paddle myPaddle;
    private Ball myBall;
    private Ball bonusBall;
    private boolean startingAllowed = true;
    private int myLives;
    //private int myLevelNumber;
    private int myScore;
    private Level myLevel;
    //private Brick myBrick;
    private boolean paddleIsNormal;
    private Group root;
    private boolean paddleCanMove;
    private boolean brickHasBeenDeleted;
    private boolean bonusBallExists = false;


// ====================================================================================================================
// beginning the game
// ====================================================================================================================
    // some things needed to remember during game
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        /*Group*/ root = new Group();
        myLives = 3; //TODO set up this in each level
        myScore = 0;
        //myLevelNumber = 1;

        myBall = new Ball();
        //myBall.setBallInformation(BALL_COLOR, SIZE, BALL_RADIUS, GAP, PADDLE_HEIGHT, BASIC_BALL_SPEED);
        //myBall.newBall();
        myBall = new Ball();
        bonusBallExists = false;

/*
        myBrick = new Brick();
        myBrick.setBrickInformation(BRICK_WIDTH, BRICK_HEIGHT, SIZE, BACKGROUND);
        myBrick.makeSingleBrick(2,20,0);
        brickHasBeenDeleted = (myBrick.getHitsRemaining()==0); //true if hits = 0, false otherwise
*/
        File filename = new File("/Users/danamulligan/Archives/workspace308/game_dmm107/doc/level1");//CHANGE THIS LATER SO YOU DONT DIE WHEN SUBMITTING
        /*try{
            Scanner sc = new Scanner(filename); //red line bc you're not checking that filename is a valid file
            for(int row=0; row < SIZE/BRICK_HEIGHT; row++){
                for(int col=0; col< SIZE/BRICK_WIDTH; col++){
                    if(sc.hasNext()){
                        hitsNeeded[row][col] = sc.nextInt();
                        locationInfoX[row][col] = col*BRICK_WIDTH;
                        locationInfoY[row][col] = row*BRICK_HEIGHT;
                        //System.out.println("Hits Needed: " + hitsNeeded[row][col] + " X: "+locationInfoX[row][col]+" Y: "+locationInfoY[row][col]);
                        totalNumberOfBricks++;
                    }
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        */
        myLevel = new Level();
        myLevel.setLevelInformation(filename, SIZE, BRICK_WIDTH, BRICK_HEIGHT);
        myLevel.setUpLevel();


        myPaddle = new Paddle();
        paddleCanMove = false;
        paddleIsNormal = true;
        //myPaddle.setPaddleInformation(SIZE, PADDLE_WIDTH, PADDLE_HEIGHT, GAP, PADDLE_COLOR, PADDLE_SPEED);
        myPaddle = new Paddle();

        /*myLevel = new Level();
        //TODO change the path below

        myLevel.setLevelInformation(levelNumber, SIZE, BRICK_WIDTH, BRICK_HEIGHT);
        myLevel.setUpLevel();
        addNewLevelToRoot(myLevel);


         */
        //setLevelInformation(levelNumber);
       // printBoard();

        /*
        System.out.println("trying to add myBricks[0][1] to root; it takes "+myBricks[0][1].getHitsRemaining()+" to break");
        Rectangle rect = myBricks[0][1].getNode();
        root.getChildren().add(rect);

         */
        //bonusBallExists = true;
        //bonusBall = new Ball();
        //bonusBall.setBallInformation(Color.PURPLE, SIZE, BALL_RADIUS, GAP, PADDLE_HEIGHT, BASIC_BALL_SPEED);
        //bonusBall.newBonusBall(BALL_RADIUS, myBall.getNode().getCenterX(), myBall.getNode().getCenterY(), myBall.getBallXSpeed(), myBall.getBallYSpeed());
        //root.getChildren().add(bonusBall.getNode());

        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBall.getNode());
        addPaddleToRoot();
        addNewLevelToRoot(myLevel);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }
// ====================================================================================================================
// playing the game
// ====================================================================================================================
    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        // move ball

        //bounce ball on paddle
        hitBrick();
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
        }
        if(myBall.ballCenterY()<=0) {
            myBall.bounceY();
        }
        if(myBall.ballCenterY()+BALL_RADIUS>=SIZE) {
            myLives--;
            resetGame();
        }
        if(bonusBallExists && (bonusBall.ballCenterX()+BALL_RADIUS>=SIZE || bonusBall.ballCenterX()<=0)) {
            bonusBall.bounceSideWall();
        }
        if(bonusBallExists && bonusBall.ballCenterY()<=0) {
            bonusBall.bounceY();
        }
        if(myLives == 0){
            endGame();
        }
    }
    private void hitBrick(){
        //TODO change this to send it down into level class
        Brick myBrick;
        for(int row = 0; row<SIZE/Brick.brickHeight; row++) {
            for (int col = 0; col < SIZE/Brick.brickWidth; col++) {
                myBrick = myLevel.myBricks[row][col];
                if (!myBrick.isBrickDestroyed()){
                    Shape intersectionBrick = Shape.intersect(myBall.getNode(), myBrick.getNode());
                    if (intersectionBrick.getBoundsInLocal().getWidth() != -1 && myBrick.getHitsRemaining()!=0) {
                        myBall.bounceY();
                        myBrick.brickIsHit();
                        myScore += 100;
                        System.out.println("HIT!");
                        System.out.println(myBrick.getHitsRemaining() + " hits remain!");
                        if (myBrick.getHitsRemaining() == 0) {
                            myBrick.deleteBrick();
                            root.getChildren().remove(myBrick.getNode());
                            System.out.println("this is not infinite loop!");
                        }
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
            myPaddle = new Paddle(DOUBLE_IN_SIZE, "shrink");
            paddleIsNormal = true;
            addPaddleToRoot();
        }
        else if (code == KeyCode.F){ //fast
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, "fast");
        }
        else if (code == KeyCode.T){ //slow like a turtle
            myBall.changeBallSpeed(DOUBLE_IN_SIZE, "slow");
        }
        //else if (code == KeyCode.V){
        //    myBall.growBall();
        //}/*
        //else if (code == KeyCode.B){
          //  bonusBallExists = true;
           // bonusBall = new Ball();
           // bonusBall.setBallInformation(Color.PURPLE, SIZE, GAP, PADDLE_HEIGHT, BASIC_BALL_SPEED);
          //  bonusBall.newBonusBall(BALL_RADIUS, myBall.getNode().getCenterX(), myBall.getNode().getCenterY(), myBall.getBallXSpeed(), myBall.getBallYSpeed());
           // root.getChildren().add(bonusBall.getNode());
           // System.out.println("there should be a bonus ball!");
        //}*/
        else if (code == KeyCode.L){ //add a life
            //TODO
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
    public void addNewLevelToRoot(Level levelToBeAdded){
        for(int row=0; row<SIZE/BRICK_HEIGHT; row++){
            for(int col=0; col<SIZE/BRICK_WIDTH; col++){
                root.getChildren().add(levelToBeAdded.getBrickNode(row,col));
                //System.out.println("added brick at x: "+col+ " and y: "+row+" to root");
            }
        }
    }
    /**
     * reason why I have this method is because it's "one" object, and I want to read it as such
     */
    public void removePaddleFromRoot(){
        root.getChildren().remove(myPaddle.getNodeLeft());
        root.getChildren().remove(myPaddle.getNodeMiddle());
        root.getChildren().remove(myPaddle.getNodeRight());
    }
    /**
     * reason why I have this method is because it's "one" object, and I want to read it as such
     */
    public void addPaddleToRoot(){
        root.getChildren().add(myPaddle.getNodeLeft());
        root.getChildren().add(myPaddle.getNodeMiddle());
        root.getChildren().add(myPaddle.getNodeRight());
    }
    public static void main (String[] args) {
        launch(args);
    }
}
