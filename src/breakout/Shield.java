package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Shield is an object that reaches across the entire screen at the bottom
 * Dependencies:
 *      package breakout
 *      Rectangle Class
 *      Color Class
 *      Shape Class
 *      Main Class for screen size and location
 * A shield can be created, and then hit twice before it is destroyed
 */
public class Shield extends Paddle{
    public static final Color SHIELD_COLOR = Color.CORAL;
    public static final int SHIELD_DURABILITY = 2;
    public static final int SHIELD_HEIGHT = 5;
    private Rectangle shield;
    private int hitsRemaining;

    /**
     * Constructor
     * gives the shield the ability to take the number of hits
     * positions the shield at the bottom of the screen with the paddle
     * changes the color so the shield is visible
     */
    public Shield(){
        shield = new Rectangle(0, Main.SIZE-Main.GAP, Main.SIZE,SHIELD_HEIGHT);
        shield.setFill(SHIELD_COLOR);
        hitsRemaining = SHIELD_DURABILITY;
    }

    /**
     * decrements the hits the shield can take by one, and destroys it if it can't take any more
     */
    public void shieldIsHit(){
        hitsRemaining--;
        if(hitsRemaining==0){
            destroyShield();
        }
    }

    /**
     * returns the Shield's Rectangle so that it can be added to a collection
     * and checked for collisions
     * @return shield as a Shape to disguise what specific type of object it is
     */
    public Shape getNode(){
        return shield;
    }

    /**
     * remove shield, it's dead
     */
    public void destroyShield(){
        shield.setFill(Main.BACKGROUND);
        shield.setX(Main.SIZE);
    }
}