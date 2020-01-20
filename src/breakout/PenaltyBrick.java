package breakout;
/**
 * PowerUpBrick exists as a Brick that holds a power up
 * Assumptions:
 *      The type that it is EXISTS
 * Dependencies (what other classes or packages it depends on)
 *      package breakout
 *      Brick Class
 *      PowerUpBrick Class
 *      Penalty Class
 * Create a PenaltyBrick if the hits needed is 100+, drop a Penalty if hit
 * Only three types:
 *      decreasing points
 *      shrinking paddle size
 *      increasing ball speed
 */
public class PenaltyBrick extends PowerUpBrick {

    private Penalty penalty;
    private int xPosition;
    private int yPosition;
    private String penaltyType;

    /**
     * Constructor
     * use the Brick constructor to make a brick that takes one hit to break
     * Set penaltyType to type, xPosition to xPos, and yPosition to yPos
     * @param type
     * @param xPos
     * @param yPos
     */
    public PenaltyBrick(String type, int xPos, int yPos){
        super(type,xPos,yPos);
        penaltyType = type;
        xPosition = xPos;
        yPosition = yPos;
    }
    @Override
    /**
     * @Override PowerUpBrick.dropPackage()
     * creates a Penalty of the penaltyType
     * assumes penaltyType is a valid Penalty
     */
    public void dropPackage(){
        penalty = new Penalty(powerUpType, xPosition, yPosition);
    }
    @Override
    /**
     * @Override PowerUpBrick.getPackage()
     * return the Penalty
     * @return penalty
     */
    public Penalty getPackage(){
        return penalty;
    }
    @Override
    /**
     * @Override PowerUpBrick.movePackage()
     * allow the package to move towards the bottom of the screen
     */
    public void movePackage(double elapsedTime){
        penalty.updateCenter(elapsedTime);
    }
}
