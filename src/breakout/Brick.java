package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick {
    private Paint[] hitsNeededColors = {Color.AZURE, Color.LIGHTCORAL, Color.PALEGOLDENROD, Color.PINK, Color.GREEN, Color.BLUE, Color.PEACHPUFF, Color.BLACK};;
    public static final int BRICK_WIDTH = 20;
    public static final int BRICK_HEIGHT = 20;

    private Rectangle myBrick;
    private int hitsNeededToBreak;
    private Paint brickColor;
    public boolean brickIsDestroyed;
    public boolean currentlyBeingHit;
    private String powerUpType;

    public Brick(int hits, int xPosition, int yPosition, String powerUp){
        powerUpType = powerUp;
        hitsNeededToBreak = hits;
        brickColor = hitsNeededColors[hits];
        brickIsDestroyed = (hits == 0); //is this valid?
        if(!brickIsDestroyed){
            myBrick = new Rectangle(xPosition, yPosition, BRICK_WIDTH, BRICK_HEIGHT);
            myBrick.setStroke(Color.GREY);
            myBrick.setFill(brickColor);
        } else {
            myBrick = new Rectangle(Main.SIZE+BRICK_WIDTH*2, Main.SIZE+BRICK_HEIGHT*2, BRICK_WIDTH, BRICK_HEIGHT);
            //make it off screen
        }
    }
    public Brick(){
        this(0,0,0,null);
    }
    public Brick(int hits, int XPosition, int YPosition){
        this(hits, XPosition, YPosition, null);
    }
    public Brick(String powerUpType, int XPosition, int YPosition){
        this(1, XPosition, YPosition, powerUpType);
    }
    public void brickIsHit(){
        hitsNeededToBreak -= 1;
        currentlyBeingHit = true;
        brickIsDestroyed = (hitsNeededToBreak == 0);
        updateBrickColor();
    }
    public boolean getHitStatus(){
        return currentlyBeingHit;
    }
    public void resetHitStatusToFalse(){
        currentlyBeingHit = false;
    }
    public void setHitStatusToBeingHit(){
        currentlyBeingHit = true;
    }
    public void deleteBrick(){
        //updateBrickColor();
        //get RID OF IT
        myBrick.setX(Main.SIZE+BRICK_WIDTH*2);
        myBrick.setY(Main.SIZE+BRICK_HEIGHT*2);
    }
    public int getHitsRemaining(){
        return hitsNeededToBreak;
    }
    public void updateBrickColor(){
        brickColor = hitsNeededColors[hitsNeededToBreak];
        myBrick.setFill(brickColor);
    }
    public boolean isBrickDestroyed(){
        return brickIsDestroyed;
    }
    public Rectangle getNode(){
        return myBrick;
    }
}
