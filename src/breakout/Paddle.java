package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Paddle {
    private Rectangle paddleLeft;
    private Rectangle paddleMiddle;
    private Rectangle paddleRight;
    private int screenSize;
    private double paddleWidth;
    private int paddleHeight;
    private Paint paddleColor;
    private Paint paddleColorL = Color.GREEN;
    private Paint paddleColorM = Color.BLUE;
    private Paint paddleColorR = Color.RED;
    private int screenGap;
    private int paddleSpeed;

    public void setPaddleInformation(int SIZE, double PADDLE_WIDTH, int PADDLE_HEIGHT, int GAP, Paint PADDLE_COLOR, int PADDLE_SPEED){
        screenSize = SIZE;
        paddleWidth = PADDLE_WIDTH;
        paddleHeight = PADDLE_HEIGHT;
        screenGap = GAP;
        paddleColor = PADDLE_COLOR;
        paddleSpeed = PADDLE_SPEED;
    }
    public void newPaddle(){
        paddleLeft = new Rectangle(screenSize/2-3*paddleWidth/6, screenSize-paddleHeight-screenGap, paddleWidth/3, paddleHeight);
        paddleLeft.setFill(Color.GREEN);
        paddleMiddle = new Rectangle(screenSize/2-paddleWidth/6, screenSize-paddleHeight-screenGap, paddleWidth/3, paddleHeight);
        paddleMiddle.setFill(Color.BLUE);
        paddleRight = new Rectangle(screenSize/2+paddleWidth/6, screenSize-paddleHeight-screenGap, paddleWidth/3, paddleHeight);
        paddleRight.setFill(Color.RED);
    }
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
    public Rectangle getNodeLeft(){
        return paddleLeft;
    }
    public Rectangle getNodeMiddle(){
        return paddleMiddle;
    }
    public Rectangle getNodeRight(){
        return paddleRight;
    }
    public void growPaddle(int scale){
       paddleWidth *= scale;
       newPaddle();
    }
    public void shrinkPaddle(int scale){
        paddleWidth /= scale;
        newPaddle();
    }

}
