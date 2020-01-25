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
    public static final int LASER_WIDTH = 5;
    public static final int LASER_HEIGHT = 25;
    public static final int LASER_SPEED = 15;

    private Color laserColor = Color.GREENYELLOW;
    private Rectangle leftLaserBeam;
    private Rectangle rightLaserBeam;
    private boolean leftCanHit;
    private boolean rightCanHit;

    /**
     * Constructor that sets the left beam to the leftmost edge of the paddle, and the right beam to the rightmost edge of the paddle
     * @param xPosition
     * @param paddleWidth
     */
    public Laser(double xPosition, double paddleWidth){
        leftLaserBeam = new Rectangle(xPosition+LASER_WIDTH, Main.SIZE-Main.GAP-LASER_HEIGHT-Paddle.PADDLE_HEIGHT, LASER_WIDTH, LASER_HEIGHT);
        leftLaserBeam.setFill(laserColor);
        leftCanHit = true;

        rightLaserBeam = new Rectangle(xPosition+paddleWidth-2*LASER_WIDTH, Main.SIZE-Main.GAP-LASER_HEIGHT-Paddle.PADDLE_HEIGHT, LASER_WIDTH, LASER_HEIGHT);
        rightLaserBeam.setFill(laserColor);
        rightCanHit = true;
    }

    /**
     * Move lasers up towards the top of the screen
     */
    public void moveLasers(){
        leftLaserBeam.setY(leftLaserBeam.getY()-LASER_SPEED);
        rightLaserBeam.setY(rightLaserBeam.getY()-LASER_SPEED);
    }

    /**
     * update variables after the left beam hits a brick, move it off screen to avoid getting in the way of gameplay
     * needs Main
     */
    public void leftLaserHitABrick(){
        leftCanHit = false;
        leftLaserBeam.setX(Main.SIZE);
        leftLaserBeam.setY(Main.SIZE);
        leftLaserBeam.setFill(Main.BACKGROUND);
    }

    /**
     * update variables after the right beam hits a brick, move it off screen to avoid getting in the way of gameplay
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
     * allow the left laser to be added to a collection by returning its Rectangle
     * @return leftLaserBeam
     */
    public Rectangle getLeftLaserBeamNode() {
        return leftLaserBeam;
    }

    /**
     * allow the right laser to be added to a collection by returning its Rectangle
     * @return rightLaserBeam
     */
    public Rectangle getRightLaserBeamNode(){
        return rightLaserBeam;
    }
}