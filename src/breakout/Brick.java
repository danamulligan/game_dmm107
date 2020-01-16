package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick {
    //public static final int brickWidth = 20; //screen size is 400, this gives us space for 10 bricks each side of the middle
    //public static final int brickHeight = 10;
    private Paint[] hitsNeededColors = {Color.AZURE, Color.LIGHTCORAL, Color.PALEGOLDENROD, Color.PINK, Color.GREEN, Color.BLUE, Color.PEACHPUFF, Color.BLACK};;
    public static final int brickWidth = 20;
    public static final int brickHeight = 20;

    private Rectangle myBrick;
    private int hitsNeededToBreak;
    private Paint brickColor;
    public boolean brickIsDestroyed;

    public Brick(int hits, int xPosition, int yPosition){
        //TODO
        hitsNeededToBreak = hits;
        brickColor = hitsNeededColors[hits];
        brickIsDestroyed = (hits == 0); //is this valid?
        if(!brickIsDestroyed){
            myBrick = new Rectangle(xPosition, yPosition, brickWidth, brickHeight);
            myBrick.setStroke(Color.GREY);
            myBrick.setFill(brickColor);
        } else {
            myBrick = new Rectangle(Main.SIZE+brickWidth*2, Main.SIZE+brickHeight*2, brickWidth, brickHeight);
            //make it off screen
        }
    }
    public Brick(){
        this(0,0,0);
    }
    public void brickIsHit(){
        hitsNeededToBreak -= 1;
        brickIsDestroyed = (hitsNeededToBreak == 0);
        updateBrickColor();
    }
    public void deleteBrick(){
        //updateBrickColor();
        //get RID OF IT
        myBrick.setX(Main.SIZE+brickWidth*2);
        myBrick.setY(Main.SIZE+brickHeight*2);
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
