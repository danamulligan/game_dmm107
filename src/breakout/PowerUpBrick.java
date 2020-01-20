package breakout;

/**
 * PowerUpBrick exists as a Brick that holds a power up
 * Assumptions:
 *      The type that it is EXISTS
 * Dependencies (what other classes or packages it depends on)
 *      package breakout
 *      Brick Class
 *      PowerUp Class
 * Create a PowerUpBrick if the hits needed is 100+, drop a PowerUp if hit
 * Only three types:
 *      adding points
 *      increasing paddle speed
 *      increasing ball radius
 */
public class PowerUpBrick extends Brick {

    private PowerUp powerUp;
    private int xPosition;
    private int yPosition;
    String powerUpType;

    /**
     * Constructor
     * use the Brick constructor to make a brick that takes one hit to break
     * Set powerUpType to type, xPosition to xPos, and yPosition to yPos
     * @param type
     * @param xPos
     * @param yPos
     */
    public PowerUpBrick(String type, int xPos, int yPos){
        super(1, xPos, yPos);
        xPosition = xPos;
        yPosition = yPos;
        powerUpType = type;
    }
    @Override
    /**
     * @Override Brick.brickIsHit()
     * added dropping a PowerUp, immediate destroy of brick
     * assumes the PowerUpBrick needs one hit to break
     */
    public void brickIsHit(){
        dropPackage();
        deleteBrick();
    }

    /**
     * creates a PowerUp of the powerUpType
     * assumes powerUpType is a valid PowerUp
     */
    public void dropPackage(){
        powerUp = new PowerUp(powerUpType, xPosition, yPosition);
    }
    @Override
    /**
     * returns the PowerUp so it can be accessed in gameplay
     * @return powerUp
     */
    public PowerUp getPackage(){
        return powerUp;
    }

    /**
     * allows the PowerUp to fall towards the bottom of the screen
     * @param elapsedTime
     */
    public void movePackage(double elapsedTime){
        powerUp.updateCenter(elapsedTime);
    }

}
