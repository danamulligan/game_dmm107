package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Laser is used to cheat and hit bricks
 * Dependencies:
 *      Rectangle and Color
 *      package breakout
 *      Main for screen size and background color
 * A key in main is pressed to create a laser, which has two beams
 * each beam is capable of only one hit, and can break bricks
 */
public class Laser {
    public static final int Laser_WIDTH = 5;
    public static final int Laser_HEIGHT = 25;
    public static final int Laser_SPEED = 15;

    private Rectangle leftLaserBeam;
    private Rectangle rightLaserBeam;
    private boolean leftCanHit;
    private boolean rightCanHit;

    /**
     * Constructor
     * @param xPosition
     * @param paddleWidth
     */
    public Laser(double xPosition, double paddleWidth){
        leftLaserBeam = new Rectangle(xPosition+Laser_WIDTH, Main.SIZE-Main.GAP-Laser_HEIGHT-Paddle.PADDLE_HEIGHT, Laser_WIDTH, Laser_HEIGHT);
        leftLaserBeam.setFill(Color.GREENYELLOW);
        rightLaserBeam = new Rectangle(xPosition+paddleWidth-2*Laser_WIDTH, Main.SIZE-Main.GAP-Laser_HEIGHT-Paddle.PADDLE_HEIGHT, Laser_WIDTH, Laser_HEIGHT);
        rightLaserBeam.setFill(Color.GREENYELLOW);
        leftCanHit = true;
        rightCanHit = true;
    }

    /**
     * Move lasers up towards the top of the screen
     * @param elapsedTime
     */
    public void moveLasers(double elapsedTime){
        leftLaserBeam.setY(leftLaserBeam.getY()-Laser_SPEED);
        rightLaserBeam.setY(rightLaserBeam.getY()-Laser_SPEED);
    }

    /**
     * allow the left laser to be added to a collection by returning it's Rectangle
     * @return leftLaserBeam
     */
    public Rectangle getLeftLaserBeamNode() {
        return leftLaserBeam;
    }

    /**
     * update variables after the left beam hits a brick, move it off screen to avoid
     * getting in the way of gameplay
     * needs Main
     */
    public void leftLaserHitABrick(){
        leftCanHit = false;
        leftLaserBeam.setX(Main.SIZE);
        leftLaserBeam.setY(Main.SIZE);
        leftLaserBeam.setFill(Main.BACKGROUND);
    }
    /**
     * update variables after the right beam hits a brick, move it off screen to avoid
     * getting in the way of gameplay
     * needs Main
     */
    public void rightLaserHitABrick(){
        rightCanHit = false;
        rightLaserBeam.setX(Main.SIZE);
        rightLaserBeam.setY(Main.SIZE);
        rightLaserBeam.setFill(Main.BACKGROUND);
    }

    /**
     * get whether or not the left beam can hit a brick
     * @return leftCanHit
     */
    public boolean getLeftLaserHitStatus(){
        return leftCanHit;
    }
    /**
     * get whether or not the right beam can hit a brick
     * @return rightCanHit
     */
    public boolean getRightLaserHitStatus(){
        return rightCanHit;
    }
    /**
     * allow the right laser to be added to a collection by returning it's Rectangle
     * @return rightLaserBeam
     */
    public Rectangle getRightLaserBeamNode(){
        return rightLaserBeam;
    }
}
