package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Paddle exists to create a moving object
 * Assumptions
 *      Paddle won't move in the Y direction
 * Dependencies
 *      Paint, Color, and KeyCode
 *      package breakout
 *      Rectangle Class
 *      Main for screen size and paddle location
 * Create a paddle to move left or right along the bottom of a screen
 */
public class Paddle {
    public static final int BASIC_PADDLE_WIDTH = 81; //divisible by 9
    public static final int BASIC_PADDLE_SPEED = 10;
    public static final int PADDLE_HEIGHT = 10;
    public static final Paint PADDLE_COLOR = Color.BLUE;

    private Rectangle paddleLeft;
    private Rectangle paddleMiddle;
    private Rectangle paddleRight;
    private double actualPaddleWidth;
    private int paddleSpeed = BASIC_PADDLE_SPEED;

    /**
     * Constructor method that creates three different Rectangles that will make up the paddle
     * @param scale used to change the size of the paddle
     * @param code used for cheat codes, S is shrink and G is grow
     */
    public Paddle(double scale, KeyCode code){
        actualPaddleWidth = BASIC_PADDLE_WIDTH;

        //check and see if there is a scale
        if(code == KeyCode.G || code == KeyCode.S){
            actualPaddleWidth = BASIC_PADDLE_WIDTH*scale;
        }

        //I'm going to be typing these a lot in a moment, so to make things faster
        int ypos = Main.SIZE-PADDLE_HEIGHT-Main.GAP;
        double thirdPaddleWidth = actualPaddleWidth/3;

        paddleLeft = new Rectangle(Main.SIZE/2-actualPaddleWidth/2, ypos, thirdPaddleWidth, PADDLE_HEIGHT);
        paddleLeft.setFill(PADDLE_COLOR);

        paddleMiddle = new Rectangle(Main.SIZE/2-actualPaddleWidth/6, ypos, thirdPaddleWidth, PADDLE_HEIGHT);
        paddleMiddle.setFill(PADDLE_COLOR);

        paddleRight = new Rectangle(Main.SIZE/2+actualPaddleWidth/6, ypos, thirdPaddleWidth, PADDLE_HEIGHT);
        paddleRight.setFill(PADDLE_COLOR);
    }

    /**
     * constructor to shrink the paddle by half the normal size
     */
    public Paddle(KeyCode code){
        this(0.5, code);
    }

    /**
     * default constructor
     */
    public Paddle(){
        this(1, null);
    }

    /**
     * Change each of the three Rectangle's xPositions to the new one based on the direction and paddle speed
     * @param direction
     */
    public void movePaddle(KeyCode direction){
        int sign = 1;
        if(direction == KeyCode.LEFT){
            sign = -1;
        } else if (direction == KeyCode.RIGHT){
            sign = 1;
        }
        paddleLeft.setX(paddleLeft.getX() + sign * paddleSpeed);
        paddleMiddle.setX(paddleMiddle.getX() + sign * paddleSpeed);
        paddleRight.setX(paddleRight.getX() + sign * paddleSpeed);
    }

    /**
     * One method for all three Rectangles
     * If KeyCode.A move to far left of screen
     * If KeyCode.D move to far right of screen
     * @param location
     */
    public void setXPositions(KeyCode location){
        if(location == KeyCode.A){
            paddleLeft.setX(0);
            paddleMiddle.setX(actualPaddleWidth/3);
            paddleRight.setX(actualPaddleWidth*2/3);
        }
        if(location == KeyCode.D){
            paddleLeft.setX(Main.SIZE - actualPaddleWidth);
            paddleMiddle.setX(Main.SIZE - 2*actualPaddleWidth/3);
            paddleRight.setX(Main.SIZE - actualPaddleWidth/3);
        }
    }

    /**
     * change paddle speed by scale
     * @param scale
     */
    public void changePaddleSpeed(int scale){
        paddleSpeed *= scale;
    }

    /**
     * return the paddle's full current width
     * used so the lasers can fire from the correct positions
     * @return actualPaddleWidth
     */
    public double getActualPaddleWidth(){
        return actualPaddleWidth;
    }

    /**
     * get leftmost xPosition
     * used so the lasers can fire from the correct positions
     * @return leftmost xPosition
     */
    public double getLeftX(){
        return paddleLeft.getX();
    }

    /**
     * return paddleLeft so it can be added to a collection,
     * and checked for collisions with the ball (ball will bounce to the left)
     * @return paddleLeft
     */
    public Shape getNodeLeft(){
        return paddleLeft;
    }

    /**
     * return paddleMiddle so it can be added to a collection,
     * and checked for collisions with the ball (ball will bounce directly up)
     * @return paddleMiddle
     */
    public Shape getNodeMiddle(){
        return paddleMiddle;
    }

    /**
     * return paddleRight so it can be added to a collection,
     * and checked for collisions with the ball (ball with bounce to the right
     * @return paddleRight
     */
    public Shape getNodeRight(){
        return paddleRight;
    }
}