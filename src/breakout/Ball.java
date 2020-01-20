package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball{

    private Circle ball;
    public static final int BALL_RADIUS = 8;
    public static final int BASIC_BALL_SPEED = 100;
    //private int screenSize = Main.SIZE;
    private int startingXPosition = Main.SIZE/2;
    private int startingYPosition = Main.SIZE-BALL_RADIUS-Main.GAP-Paddle.PADDLE_HEIGHT;
    private double ballYSpeed;
    private double ballXSpeed;
    public static final Paint ballColor = Color.GREY;
    //private Paint bonusBallColor = Color.PURPLE;

    private int paddleHeight = Paddle.PADDLE_HEIGHT;
    private int gap;
    private boolean canHit;

    public Ball(){
        this(BALL_RADIUS, ballColor);
    }
    public Ball(int newRadius){
        this(newRadius, ballColor);
        //ball = new Circle(startingXPosition, startingYPosition, newRadius, ballColor);
        //ballXSpeed = 0;
        //ballYSpeed = 0;
    }
    public Ball(int newRadius, Paint newBallColor){
        ball = new Circle(startingXPosition, startingYPosition, newRadius, newBallColor);
        canHit = true;
        //ballXSpeed = 0;
        //ballYSpeed = 0;
    }
    /*
    public Ball(String "bonus"){
        this();
        ballYSpeed = BALL_Y_SPEED;
        ballXSpeed = BALL_X_SPEED/Math.abs(BALL_X_SPEED) * (Math.random() * basicBallSpeed/2 + basicBallSpeed/2);
    }*/
    public void beginMovingBall(){
        ballYSpeed = -1 * BASIC_BALL_SPEED;
    }
    public void freeze(){
        ballXSpeed = 0;
        ballYSpeed = 0;
    }
    public void bounceY(){
        ballYSpeed *= -1;
    }
    public void bounceSideWall(){
        bounceSideBrick();
        canHit = true;
    }
    public void bounceSideBrick(){
        ballXSpeed *= -1;
    }
    public void bouncePaddleLeft(){
        ballXSpeed = -1 * Math.random() * Math.abs(BASIC_BALL_SPEED/2)-BASIC_BALL_SPEED/2;
        ballYSpeed *= -1;
        canHit = true;
    }
    public void bouncePaddleMiddle(){
        bounceY();
        canHit = true;
        ballXSpeed = 0;
    }
    public void bouncePaddleRight(){
        ballXSpeed = Math.random()*BASIC_BALL_SPEED;
        canHit = true;
        ballYSpeed *= -1;
    }
    //TODO IF YOU ALWAYS PAIR THESE TWO THEN MAKE THEM ONE METHOD
    public void updateBallCenter(double elapsedTime){
        ball.setCenterX(ball.getCenterX() + ballXSpeed * elapsedTime);
        ball.setCenterY(ball.getCenterY() + ballYSpeed * elapsedTime);
    }
    public void changeBallSpeed(int scale, KeyCode code){
        if(code == KeyCode.F){
            ballYSpeed *= scale;
            ballXSpeed *= scale;
        }
        else if(code == KeyCode.T){
            ballYSpeed /= scale;
            ballXSpeed /= scale;
        }
    }
    public void moveBall(KeyCode direction){
        int sign = 1;
        if(direction == KeyCode.LEFT){
            sign = -1;
        } else if (direction == KeyCode.RIGHT){
            sign = 1;
        }
        ball.setCenterX(ball.getCenterX() + sign * Paddle.BASIC_PADDLE_SPEED);
    }
    public void grow(){
        ball.setRadius(BALL_RADIUS*2);
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
    public void ballHitABrick(){
        canHit = false;
    }
    public void resetHitCapabilities(){
        canHit = true;
    }
    public boolean getHitStatus(){
        return canHit;
    }
    public Circle getNode(){
        return ball;
    }
}
