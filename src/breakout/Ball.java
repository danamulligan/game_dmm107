package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball{

    private Circle ball;
    public static final int ballRadius = 8;
    //private int screenSize = Main.SIZE;
    private int startingXPosition = Main.SIZE/2;
    private int startingYPosition = Main.SIZE-ballRadius-Main.GAP-Paddle.PADDLE_HEIGHT;
    private double ballYSpeed;
    private double ballXSpeed;
    public static final Paint ballColor = Color.GREY;
    //private Paint bonusBallColor = Color.PURPLE;
    private int basicBallSpeed = Main.BASIC_BALL_SPEED;
    private int paddleHeight = Paddle.PADDLE_HEIGHT;
    private int gap;

    public Ball(){
        this(ballRadius, ballColor);
    }
    public Ball(int newRadius){
        this(newRadius, ballColor);
        //ball = new Circle(startingXPosition, startingYPosition, newRadius, ballColor);
        //ballXSpeed = 0;
        //ballYSpeed = 0;
    }
    public Ball(int newRadius, Paint newBallColor){
        ball = new Circle(startingXPosition, startingYPosition, newRadius, newBallColor);
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
    //TODO IF YOU ALWAYS PAIR THESE TWO THEN MAKE THEM ONE METHOD
    public void updateBallCenter(double elapsedTime){
        ball.setCenterX(ball.getCenterX() + ballXSpeed * elapsedTime);
        ball.setCenterY(ball.getCenterY() + ballYSpeed * elapsedTime);
    }
    public void changeBallSpeed(int scale, String typeOfScale){
        if(typeOfScale.equals("fast")){
            ballYSpeed *= scale;
            ballXSpeed *= scale;
        }
        else if(typeOfScale.equals("slow")){
            ballYSpeed /= scale;
            ballXSpeed /= scale;
        }
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
