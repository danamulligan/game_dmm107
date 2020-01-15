package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Paddle {
    private Rectangle paddleLeft;
    private Rectangle paddleMiddle;
    private Rectangle paddleRight;
    public static final int SCREEN_SIZE = Main.SIZE; //should i take it out and just ref it as main.size?
    public double basicPaddleWidth = 81; //divisible by 9
    public static final int PADDLE_HEIGHT = 10;
    public static final Paint paddleColor = Color.PINK;
    private int paddleSpeed = 10;

    public Paddle(int scale, String changeBy){
        double paddleWidth = basicPaddleWidth;
        if(changeBy.equals("grow")){
            paddleWidth = basicPaddleWidth*scale;
        }
        else if(changeBy.equals("shrink")){
            paddleWidth /= scale;
        }
        paddleLeft = new Rectangle(Main.SIZE/2-paddleWidth/2, Main.SIZE-PADDLE_HEIGHT-Main.GAP, paddleWidth/3, PADDLE_HEIGHT);
        paddleLeft.setFill(Color.GREEN); //change later to paddleColor
        paddleMiddle = new Rectangle(Main.SIZE/2-paddleWidth/6, Main.SIZE-PADDLE_HEIGHT-Main.GAP, paddleWidth/3, PADDLE_HEIGHT);
        paddleMiddle.setFill(Color.BLUE); //change later to paddleColor
        paddleRight = new Rectangle(Main.SIZE/2+paddleWidth/6, Main.SIZE-PADDLE_HEIGHT-Main.GAP, paddleWidth/3, PADDLE_HEIGHT);
        paddleRight.setFill(Color.RED); //change later to paddleColor
    }
    public Paddle(){
        this(1, "nothing");
    }
    public void movePaddle(String direction){
        int sign = 1;
        if(direction.equals("left")){
            sign = -1;
        } else if (direction.equals("right")){
            sign = 1;
        }
        paddleLeft.setX(paddleLeft.getX() + sign * paddleSpeed);
        paddleMiddle.setX(paddleMiddle.getX() + sign * paddleSpeed);
        paddleRight.setX(paddleRight.getX() + sign * paddleSpeed);
    }
    /*
    public void moveRight(){
        paddleLeft.setX(paddleLeft.getX() + paddleSpeed);
        paddleMiddle.setX(paddleMiddle.getX() + paddleSpeed);
        paddleRight.setX(paddleRight.getX() + paddleSpeed);
    }
    public void moveLeft(){
        paddleLeft.setX(paddleLeft.getX() - paddleSpeed);
        paddleMiddle.setX(paddleMiddle.getX() - paddleSpeed);
        paddleRight.setX(paddleRight.getX() - paddleSpeed);
    }
     */
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
