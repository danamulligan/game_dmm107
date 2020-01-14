package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball{

    private Circle ball;
    private int startingXPosition;
    private int startingYPosition;
    private int screenSize;
    private double ballYSpeed;
    private double ballXSpeed;
    private int ballRadius;
    private Paint basicBallColor;
    private Paint bonusBallColor = Color.PURPLE;
    private int basicBallSpeed;
    private int paddleHeight;
    private int gap;

    public void setBallInformation(Paint BALL_COLOR, int SCREEN_SIZE, int BALL_RADIUS, int GAP, int PADDLE_HEIGHT, int BASIC_BALL_SPEED){
        paddleHeight = PADDLE_HEIGHT;
        ballRadius = BALL_RADIUS;
        screenSize = SCREEN_SIZE;
        basicBallColor = BALL_COLOR;
        basicBallSpeed = BASIC_BALL_SPEED;
        gap = GAP;
        startingXPosition = screenSize/2;
        startingYPosition = screenSize-ballRadius-gap-paddleHeight;
    }
    public void newBall(){
        ball = new Circle(startingXPosition,startingYPosition, ballRadius, basicBallColor);
        ballYSpeed = 0;
        ballXSpeed = 0;
    }
    public void newBonusBall(int BALL_RADIUS, double xPositionCurrent, double yPositionCurrent, double BALL_X_SPEED, double BALL_Y_SPEED){
        ball = new Circle(xPositionCurrent,yPositionCurrent,ballRadius,bonusBallColor);
        ballYSpeed = BALL_Y_SPEED;
        ballXSpeed = BALL_X_SPEED/Math.abs(BALL_X_SPEED) * (Math.random() * basicBallSpeed/2 + basicBallSpeed/2);
    }
    public void beginMovingBall(){
        ballYSpeed = -1 * basicBallSpeed;
    }
    public void freeze(){
        ballXSpeed = 0;
        ballYSpeed = 0;
    }
    public void bounceY(){
        ballYSpeed *= -1;
    }
    public void bounceSideWall(){
        ballXSpeed *= -1;
    }
    public void bouncePaddleLeft(){
        ballXSpeed = -1 * Math.random() * Math.abs(basicBallSpeed/2)-basicBallSpeed/2;
        ballYSpeed *= -1;
    }
    public void bouncePaddleMiddle(){
        bounceY();
        ballXSpeed = 0;
    }
    public void bouncePaddleRight(){
        ballXSpeed = Math.random()*Math.abs(basicBallSpeed);
        ballYSpeed *= -1;
    }
    public void updateBallCenterX(double elapsedTime){
        ball.setCenterX(ball.getCenterX() + ballXSpeed * elapsedTime);
    }
    public void updateBallCenterY(double elapsedTime){
        ball.setCenterY(ball.getCenterY() + ballYSpeed * elapsedTime);
    }
    public void growBall(){

    }
    public void changeBallSpeed(int scale){
        ballYSpeed *= scale;
        ballXSpeed *= scale;
    }
    public double getBallXSpeed() {
        return ballXSpeed;
    }
    public double getBallYSpeed(){
        return ballYSpeed;
    }
    public double ballCenterX(){
        return ball.getCenterX();
    }
    public double ballCenterY(){
        return ball.getCenterY();
    }
    public Circle getNode(){
        return ball;
    }
}
