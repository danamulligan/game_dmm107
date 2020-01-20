package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Shield is an object that reaches across the entire screen at the bottom
 * Dependencies:
 *      package breakout
 *      Rectangle Class
 *      Paint Class
 *      Color Class
 *      Main Class for screen size and location
 * A shield can be created, and then hit twice before it is destroyed
 */
public class Shield extends Paddle{
    public static final Paint SHIELD_COLOR = Color.CORAL;
    private Rectangle shield;
    private boolean shieldStatus;
    private int hitsRemaining;

    /**
     * Constructor
     * gives the shield the ability to take two hits
     */
    public Shield(){
        shield = new Rectangle(0, Main.SIZE-Main.GAP, Main.SIZE,5);
        shield.setFill(SHIELD_COLOR);
        shieldStatus = true;
        hitsRemaining = 2;
    }

    /**
     * decrements the hits the shield can take by one, and destroys it if it can't take any more
     */
    public void shieldIsHit(){
        hitsRemaining--;
        if(hitsRemaining==0){
            shieldStatus = false;
            destroyShield();
        }
    }

    /**
     * return's the shield's status
     * @return shieldStatus
     */
    public boolean getStatus(){
        return shieldStatus;
    }

    /**
     * returns the Shield's Rectangle so that it can be added to a collection
     * @return shield
     */
    public Rectangle getNode(){
        return shield;
    }

    /**
     * remove shield, it's dead
     */
    public void destroyShield(){
        shieldStatus = false;
        shield.setFill(Main.BACKGROUND);
        shield.setX(Main.SIZE);
    }



}
