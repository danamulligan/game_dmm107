package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick {
    //public static final int brickWidth = 20; //screen size is 400, this gives us space for 10 bricks each side of the middle
    //public static final int brickHeight = 10;
    private Paint[] hitsNeededColors = {Color.AZURE, Color.LIGHTCORAL, Color.PALEGOLDENROD, Color.PINK, Color.GREEN, Color.BLUE, Color.PEACHPUFF, Color.BLACK};;
    private int brickWidth;
    private int brickHeight;
    private int screenSize;
    private Rectangle myBrick;
    private int hitsNeededToBreak;
    private Paint brickColor;
    private boolean brickIsDestroyed;


    public void setBrickInformation(int BRICK_WIDTH, int BRICK_HEIGHT, int SCREEN_SIZE){
        brickWidth = BRICK_WIDTH;
        brickHeight = BRICK_HEIGHT;
        screenSize = SCREEN_SIZE;
    }
    public void makeSingleBrick(int hits, int xPosition, int yPosition) {
        hitsNeededToBreak = hits;
        brickColor = hitsNeededColors[hits];
        brickIsDestroyed = (hits == 0); //is this valid?
        if(!brickIsDestroyed){
            myBrick = new Rectangle(xPosition, yPosition, brickWidth, brickHeight);
            myBrick.setFill(brickColor);
        } else {
            myBrick = new Rectangle(screenSize+brickWidth*2, screenSize+brickHeight*2, brickWidth, brickHeight);
            //make it off screen
        }
        //do I need an else statement to catch this?
    }
    public void brickIsHit(){
        hitsNeededToBreak -= 1;
        updateBrickColor();
    }
    public void deleteBrick(){
        updateBrickColor();
        //get RID OF IT
        myBrick.setX(screenSize+brickWidth*2);
        myBrick.setY(screenSize+brickHeight*2);
    }
    public int getHitsRemaining(){
        return hitsNeededToBreak;
    }
    public void updateBrickColor(){
        brickColor = hitsNeededColors[hitsNeededToBreak];
        myBrick.setFill(brickColor);
    }
    public Rectangle getNode(){
        return myBrick;
    }
}
