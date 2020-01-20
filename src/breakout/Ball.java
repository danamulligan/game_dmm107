package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Ball exists to have a main object that breaks bricks
 * Assumptions:
 *
 * Dependencies:
 *      Depends on Circle, Paddle for the starting position (need paddle dimensions), Main for the screen size
 * A ball can be created at the origin (center of the screen), and given an X and Y speed. These speeds as well as
 * the balls radius, xposition, and yposition can be controlled. The ball can hit a brick.
 */
public class Ball{

    private Circle ball;
    public static final int BALL_RADIUS = 8;
    public static final int BASIC_BALL_SPEED = 100;
    private int startingXPosition = Main.SIZE/2;
    private int startingYPosition = Main.SIZE-BALL_RADIUS-Main.GAP-Paddle.PADDLE_HEIGHT;
    private double ballYSpeed;
    private double ballXSpeed;
    public static final Paint ballColor = Color.GREY;

    private boolean canHit;

    public Ball(){
        this(BALL_RADIUS, ballColor);
    }
    public Ball(int newRadius, Paint newBallColor){
        ball = new Circle(startingXPosition, startingYPosition, newRadius, newBallColor);
        canHit = true;
    }
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
