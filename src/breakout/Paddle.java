package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle {
    private Rectangle paddleLeft;
    private Rectangle paddleMiddle;
    private Rectangle paddleRight;
    public static final int BASIC_PADDLE_WIDTH = 81; //divisible by 9
    public static final int PADDLE_HEIGHT = 10;
    public static final Paint paddleColor = Color.PINK;
    private double actualPaddleWidth;
    private int paddleSpeed = 10;

    public Paddle(double scale, KeyCode code){
        actualPaddleWidth = BASIC_PADDLE_WIDTH;
        if(code == KeyCode.G || code == KeyCode.S){
            actualPaddleWidth = BASIC_PADDLE_WIDTH*scale;
        }
        paddleLeft = new Rectangle(Main.SIZE/2-actualPaddleWidth/2, Main.SIZE-PADDLE_HEIGHT-Main.GAP, actualPaddleWidth/3, PADDLE_HEIGHT);
        paddleLeft.setFill(Color.GREEN); //change later to paddleColor
        paddleMiddle = new Rectangle(Main.SIZE/2-actualPaddleWidth/6, Main.SIZE-PADDLE_HEIGHT-Main.GAP, actualPaddleWidth/3, PADDLE_HEIGHT);
        paddleMiddle.setFill(Color.BLUE); //change later to paddleColor
        paddleRight = new Rectangle(Main.SIZE/2+actualPaddleWidth/6, Main.SIZE-PADDLE_HEIGHT-Main.GAP, actualPaddleWidth/3, PADDLE_HEIGHT);
        paddleRight.setFill(Color.RED); //change later to paddleColor
    }
    public Paddle(KeyCode code){
        this(0.5, code);
    }
    public Paddle(){
        this(1, null);
    }
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
    public void changePaddleSpeed(int scale){
        paddleSpeed *= scale;
    }
    public double getActualPaddleWidth(){
        return actualPaddleWidth;
    }
    public double getLeftX(){
        return paddleLeft.getX();
    }

    public Rectangle getNodeLeft(){
        return paddleLeft;
    }
    public Rectangle getNodeMiddle(){
        return paddleMiddle;
    }
    public Rectangle getNodeRight(){
        return paddleRight;
    }
}
