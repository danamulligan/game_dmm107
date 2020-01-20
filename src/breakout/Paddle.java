package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Rectangle paddleLeft;
    private Rectangle paddleMiddle;
    private Rectangle paddleRight;
    public static final int BASIC_PADDLE_WIDTH = 81; //divisible by 9
    public static final int BASIC_PADDLE_SPEED = 10;
    public static final int PADDLE_HEIGHT = 10;
    public static final Paint paddleColor = Color.PINK;
    private double actualPaddleWidth;
    private int paddleSpeed = BASIC_PADDLE_SPEED;

    /**
     * Constructor method that creates three different Rectangles that will make up the paddle
     * @param scale used to change the size of the paddle
     * @param code used for cheat codes, S is shrink and G is grow
     */
    public Paddle(double scale, KeyCode code){
        actualPaddleWidth = BASIC_PADDLE_WIDTH;
        if(code == KeyCode.G || code == KeyCode.S){
            actualPaddleWidth = BASIC_PADDLE_WIDTH*scale;
        }
        paddleLeft = new Rectangle(Main.SIZE/2-actualPaddleWidth/2, Main.SIZE-PADDLE_HEIGHT-Main.GAP, actualPaddleWidth/3, PADDLE_HEIGHT);
        paddleLeft.setFill(Color.BLUE); //change later to paddleColor
        paddleMiddle = new Rectangle(Main.SIZE/2-actualPaddleWidth/6, Main.SIZE-PADDLE_HEIGHT-Main.GAP, actualPaddleWidth/3, PADDLE_HEIGHT);
        paddleMiddle.setFill(Color.BLUE); //change later to paddleColor
        paddleRight = new Rectangle(Main.SIZE/2+actualPaddleWidth/6, Main.SIZE-PADDLE_HEIGHT-Main.GAP, actualPaddleWidth/3, PADDLE_HEIGHT);
        paddleRight.setFill(Color.BLUE); //change later to paddleColor
    }

    /**
     * create a paddle at half the size
     * @param code
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
     * @return actualPaddleWidth
     */
    public double getActualPaddleWidth(){
        return actualPaddleWidth;
    }

    /**
     * get leftmost xPosition
     * @return leftmost xPosition
     */
    public double getLeftX(){
        return paddleLeft.getX();
    }

    /**
     * return paddleLeft so it can be added to a collection
     * @return paddleLeft
     */
    public Rectangle getNodeLeft(){
        return paddleLeft;
    }
    /**
     * return paddleMiddle so it can be added to a collection
     * @return paddleMiddle
     */
    public Rectangle getNodeMiddle(){
        return paddleMiddle;
    }
    /**
     * return paddleRight so it can be added to a collection
     * @return paddleRight
     */
    public Rectangle getNodeRight(){
        return paddleRight;
    }
}
