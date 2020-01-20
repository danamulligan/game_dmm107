package breakout;

import javafx.scene.paint.Paint;

public class PowerUpBrick extends Brick {

    //Brick myBase;
    //private int hits;
    private Paint brickColor;
    private PowerUp powerUp;
    private int xPosition;
    private int yPosition;
    //private boolean brickIsDestroyed;
    String powerUpType;


    public PowerUpBrick(String type, int xPos, int yPos){
        super(1, xPos, yPos);
        xPosition = xPos;
        yPosition = yPos;
        powerUpType = type;
    }
    @Override
    public void brickIsHit(){
        //drop powerup
        dropPackage();
        deleteBrick();
        //destroy brick
    }
    public void dropPackage(){
        powerUp = new PowerUp(powerUpType, xPosition, yPosition);
    }
    //@Override
    public PowerUp getPackage(){
        return powerUp;
    }
    public void movePackage(double elapsedTime){
        powerUp.updateCenter(elapsedTime);
    }

    public static void main(String[] args) {

    }
}
