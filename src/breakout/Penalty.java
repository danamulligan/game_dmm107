package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Penalty exist for negative power ups
 * Dependencies:
 *      package breakout
 *      Classes Circle, Paint, and Color
 *      PowerUp Class
 *      Main Class
 * Penalty is created as a PenaltyBrick is broken, but Penalty doesn't depend on PenaltyBrick
 */
public class Penalty extends PowerUp {

    public static final Paint PENALTY_COLOR = Color.DARKRED;
    public static final int PENALTY_SPEED = 80;

    private String penaltyType;
    private Circle penalty;

    /**
     * Constructor
     * create a Circle Penalty
     * set penaltyType to type, xPosition to xPos, and yPosition to yPos
     * @param type
     * @param xPos
     * @param yPos
     */
    public Penalty(String type, int xPos, int yPos){
        penalty = new Circle(xPos+PowerUp.POWER_UP_RADIUS, yPos+PowerUp.POWER_UP_RADIUS,PowerUp.POWER_UP_RADIUS);
        penalty.setFill(PENALTY_COLOR);
        penaltyType = type;
    }
    @Override
    /**
     * @Override PowerUp.getNode()
     * return the Circle so it can be added to a collection
     * @return penalty
     */
    public Circle getNode(){
        return penalty;
    }
    @Override
    /**
     * @Override PowerUp.getType()
     * used so other's can know what type of Penalty it is
     * @return penaltyType
     */
    public String getType(){
        return penaltyType;
    }
    @Override
    /**
     * @Override PowerUp.caught()
     * move the penalty circle off screen so it doesn't get in the way of game play
     */
    public void caught(){
        penalty.setCenterX(Main.SIZE*2);
        penalty.setCenterY(Main.SIZE*2);
        penalty.setFill(Main.BACKGROUND);
    }
    @Override
    /**
     * @Override PowerUp.updateCenter();
     * set the Circle's center position so the penalty can fall towards the paddle
     */
    public void updateCenter(double elapsedTime){
        penalty.setCenterY(penalty.getCenterY() + PENALTY_SPEED* elapsedTime);
    }

}
