package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PowerUp {
    public static final int POWER_UP_RADIUS = 10;
    public static final Paint POWER_UP_COLOR = Color.ORANGE;

    private String powerUpType;
    private int powerUpSpeed=80;

    private Circle powerUp;

    public PowerUp(String type, int xPos, int yPos){
        powerUp = new Circle(xPos+POWER_UP_RADIUS, yPos+POWER_UP_RADIUS,POWER_UP_RADIUS);
        powerUp.setFill(POWER_UP_COLOR);
        powerUpType = type;
    }
    public PowerUp(){
        this(null, Main.SIZE+POWER_UP_RADIUS, Main.SIZE+POWER_UP_RADIUS);
    }
    public String getType(){
        return powerUpType;
    }
    public Circle getNode(){
        return powerUp;
    }
    public void caught(){
        powerUp.setCenterX(Main.SIZE*2);
        powerUp.setCenterY(Main.SIZE*2);
        powerUp.setFill(Main.BACKGROUND);
    }
    public void updateCenter(double elapsedTime){
        powerUp.setCenterY(powerUp.getCenterY() + powerUpSpeed * elapsedTime);
    }

}
