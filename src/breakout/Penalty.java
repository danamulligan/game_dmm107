package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Penalty extends PowerUp {

    public static final Paint PENALTY_COLOR = Color.DARKRED;
    public static final int PENALTY_SPEED = 80;

    private String penaltyType;
    private Circle penalty;

    public Penalty(String type, int xPos, int yPos){
        penalty = new Circle(xPos+PowerUp.POWER_UP_RADIUS, yPos+PowerUp.POWER_UP_RADIUS,PowerUp.POWER_UP_RADIUS);
        penalty.setFill(PENALTY_COLOR);
        penaltyType = type;
    }
    @Override
    public Circle getNode(){
        return penalty;
    }
    @Override
    public String getType(){
        return penaltyType;
    }
    @Override
    public void caught(){
        penalty.setCenterX(Main.SIZE*2);
        penalty.setCenterY(Main.SIZE*2);
        penalty.setFill(Main.BACKGROUND);
    }
    @Override
    public void updateCenter(double elapsedTime){
        penalty.setCenterY(penalty.getCenterY() + PENALTY_SPEED* elapsedTime);
    }

}
