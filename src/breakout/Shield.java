package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class Shield extends Paddle{
    public static final Paint SHIELD_COLOR = Color.CORAL;
    private Rectangle shield;
    private boolean shieldStatus;
    private int hitsRemaining;

    public Shield(){
        shield = new Rectangle(0, Main.SIZE-Main.GAP, Main.SIZE,5);
        shield.setFill(SHIELD_COLOR);
        shieldStatus = true;
        hitsRemaining = 2;
    }
    public void shieldIsHit(){
        hitsRemaining--;
        if(hitsRemaining==0){
            shieldStatus = false;
            destroyShield();
        }
    }
    public boolean getStatus(){
        return shieldStatus;
    }
    public Rectangle getNode(){
        return shield;
    }
    public void destroyShield(){
        shield.setFill(Main.BACKGROUND);
        shield.setX(Main.SIZE);
    }



}
