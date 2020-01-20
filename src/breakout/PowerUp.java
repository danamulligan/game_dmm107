package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * PowerUp create a positive side effect, a Circle that drops towards the paddle and can be activated if caught
 * Dependencies:
 *      package breakout
 *      Classes Circle, Paint, and Color
 *      Main Class
 * PowerUp is created as a PowerUpBrick is broken, but PowerUp doesn't depend on PowerUpBrick
 */
public class PowerUp {
    public static final int POWER_UP_RADIUS = 10;
    public static final Paint POWER_UP_COLOR = Color.ORANGE;

    private String powerUpType;
    private int powerUpSpeed=80;

    private Circle powerUp;

    /**
     * Constructor
     * set powerUp as a new Circle in the position of xPosition and yPosition (where the brick holding it was)
     * et powerUpType to type, xPosition to xPos, and yPosition to yPos
     * @param type
     * @param xPos
     * @param yPos
     */
    public PowerUp(String type, int xPos, int yPos){
        powerUp = new Circle(xPos+POWER_UP_RADIUS, yPos+POWER_UP_RADIUS,POWER_UP_RADIUS);
        powerUp.setFill(POWER_UP_COLOR);
        powerUpType = type;
    }

    /**
     * default constructor
     */
    public PowerUp(){
        this(null, Main.SIZE+POWER_UP_RADIUS, Main.SIZE+POWER_UP_RADIUS);
    }

    /**
     * allow other objects to see what type it is
     * @return powerUpType
     */
    public String getType(){
        return powerUpType;
    }

    /**
     * allow the Circle to be added to a collection
     * @return powerUp
     */
    public Circle getNode(){
        return powerUp;
    }

    /**
     * move the powerUp off the screen so it doesn't interfere with game play
      */
    public void caught(){
        powerUp.setCenterX(Main.SIZE*2);
        powerUp.setCenterY(Main.SIZE*2);
        powerUp.setFill(Main.BACKGROUND);
    }

    /**
     * allow power up to fall at powerUpSpeed to the bottom of the screen
     * @param elapsedTime
     */
    public void updateCenter(double elapsedTime){
        powerUp.setCenterY(powerUp.getCenterY() + powerUpSpeed * elapsedTime);
    }

}
