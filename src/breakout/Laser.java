package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Laser {
    public static final int Laser_WIDTH = 5;
    public static final int Laser_HEIGHT = 25;
    public static final int Laser_SPEED = 15;


    private Rectangle leftLaserBeam;
    private Rectangle rightLaserBeam;
    private boolean leftCanHit;
    private boolean rightCanHit;


    public Laser(double xPosition, double paddleWidth){
        leftLaserBeam = new Rectangle(xPosition+Laser_WIDTH, Main.SIZE-Main.GAP-Laser_HEIGHT-Paddle.PADDLE_HEIGHT, Laser_WIDTH, Laser_HEIGHT);
        leftLaserBeam.setFill(Color.GREENYELLOW);
        rightLaserBeam = new Rectangle(xPosition+paddleWidth-2*Laser_WIDTH, Main.SIZE-Main.GAP-Laser_HEIGHT-Paddle.PADDLE_HEIGHT, Laser_WIDTH, Laser_HEIGHT);
        rightLaserBeam.setFill(Color.GREENYELLOW);
        leftCanHit = true;
        rightCanHit = true;
    }
    public void moveLasers(double elapsedTime){
        leftLaserBeam.setY(leftLaserBeam.getY()-Laser_SPEED);
        rightLaserBeam.setY(rightLaserBeam.getY()-Laser_SPEED);
    }
    public void destroyLaserBeamLeft(){
        leftLaserBeam.setX(Main.SIZE);
        leftLaserBeam.setY(Main.SIZE);
        leftLaserBeam.setFill(Main.BACKGROUND);
    }
    public void destroyLaserBeamRight(){
        rightLaserBeam.setX(Main.SIZE);
        rightLaserBeam.setY(Main.SIZE);
        rightLaserBeam.setFill(Main.BACKGROUND);
    }
    public Rectangle getLeftLaserBeamNode() {
        return leftLaserBeam;
    }
    public void leftLaserHitABrick(){
        leftCanHit = false;
    }
    public void rightLaserHitABrick(){
        rightCanHit = false;
    }
    public boolean getLeftLaserHitStatus(){
        return leftCanHit;
    }
    public boolean getRightLaserHitStatus(){
        return rightCanHit;
    }
    public Rectangle getRightLaserBeamNode(){
        return rightLaserBeam;
    }
}
