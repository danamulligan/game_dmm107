package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Brick is an object that can be hit by a ball, a laser, or by clicking. The brick doesn't know what can hit it, just that it has been hit
 * Brick is the building block of a Level, but isn't dependant on Level
 * Dependencies:
 *      Main (for background color) and screen size
 *      package breakout
 * Assumptions: hitsNeededToBreak will be in the range 0-7
 */
public class Brick {
    public static final Color[] HITS_NEEDED_COLORS = {Main.BACKGROUND, Color.LIGHTCORAL, Color.PALEGOLDENROD, Color.PINK, Color.GREEN, Color.BLUE, Color.PEACHPUFF, Color.BLACK};
    public static final int BRICK_WIDTH = 20;
    public static final int BRICK_HEIGHT = 20;

    private Rectangle myBrick;
    private int hitsNeededToBreak;
    private Color brickColor;
    public boolean brickIsDestroyed;

    /**
     * Constructor that sets the number of hits to break the brick, color, and position on the screen
     * @param hits number of hits needed to break the brick; assumes it's in the range from 0-7
     * @param xPosition x location of the brick
     * @param yPosition y location of the brick
     */
    public Brick(int hits, int xPosition, int yPosition){
        hitsNeededToBreak = hits;
        brickColor = HITS_NEEDED_COLORS[hits];
        brickIsDestroyed = (hits == 0);

        if(!brickIsDestroyed){
            myBrick = new Rectangle(xPosition, yPosition, BRICK_WIDTH, BRICK_HEIGHT);
            myBrick.setStroke(Color.GREY);
            myBrick.setFill(brickColor);
        } else {
            myBrick = new Rectangle(Main.SIZE+BRICK_WIDTH*2, Main.SIZE+BRICK_HEIGHT*2, BRICK_WIDTH, BRICK_HEIGHT);
            //make it off screen
        }
    }

    /**
     * Default constructor
     */
    public Brick(){
        this(0,0,0);
    }

    /**
     * Called if a brick is hit, updates the brick to its new color
     */
    public void brickIsHit(){
        hitsNeededToBreak -= 1;
        brickIsDestroyed = (hitsNeededToBreak == 0);
        updateBrickColor();
    }

    /**
     * move the brick off screen
     * this is public so the order events happens in the correct way (need line 263 on Main)
     */
    public void deleteBrick(){
        myBrick.setX(Main.SIZE+BRICK_WIDTH*2);
        myBrick.setY(Main.SIZE+BRICK_HEIGHT*2);
    }

    private void updateBrickColor(){
        brickColor = HITS_NEEDED_COLORS[hitsNeededToBreak];
        myBrick.setFill(brickColor);
    }

    /**
     * Doesn't give out any of the information apart from whether it's dead or not
     * @return brickIsDestroyed, which is true if hitsNeededToBreak ==0
     */
    public boolean isBrickDestroyed(){
        return brickIsDestroyed;
    }

    /**
     * Allow the brick the be added to a collection, and be checked for intersections
     * @return myBrick
     */
    public Rectangle getNode(){
        return myBrick;
    }
}