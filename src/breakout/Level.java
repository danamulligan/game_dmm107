package breakout;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Level is made up of a matrix of Bricks
 * Depends of Brick class and Main
 */
public class Level {
    public Brick[][] myBricks;
    public int[][] hitsNeeded;
    public int[][] locationInfoX;
    public int[][] locationInfoY;
    int screenSize;
    int brickWidth;
    int brickHeight;
    int totalNumberOfBricks = 0;
    public int numberBricksDestroyed = 0;
    private boolean[][] clear;

    public Level(File filename){
        try{
            Scanner sc = new Scanner(filename);
            screenSize = Main.SIZE;
            brickWidth = Brick.BRICK_WIDTH;
            brickHeight = Brick.BRICK_HEIGHT;

            hitsNeeded = new int[screenSize/brickHeight][screenSize/brickWidth];
            locationInfoX = new int[screenSize/brickHeight][screenSize/brickWidth];
            locationInfoY = new int[screenSize/brickHeight][screenSize/brickWidth];
            clear = new boolean[screenSize/brickHeight][screenSize/brickWidth];
            for(int row=0; row < screenSize/brickHeight; row++){
                for(int col=0; col<screenSize/brickWidth; col++){
                    if(sc.hasNext()){
                        hitsNeeded[row][col] = sc.nextInt();
                        locationInfoX[row][col] = col*brickWidth;
                        locationInfoY[row][col] = row*brickHeight;
                        totalNumberOfBricks++;
                    }
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        setUpLevel();
    }
    public void setUpLevel(){
        myBricks = new Brick[screenSize/brickHeight][screenSize/brickWidth];
        for(int row=0; row<screenSize/brickHeight; row++){
            for(int col = 0; col<screenSize/brickWidth; col++){
                Brick holder;
                if(hitsNeeded[row][col]==0){
                    holder = new Brick();
                    numberBricksDestroyed++;
                } else if(hitsNeeded[row][col]==100){
                    holder = new PowerUpBrick("points", locationInfoX[row][col],locationInfoY[row][col]);
                } else if(hitsNeeded[row][col]==110){
                    holder = new PowerUpBrick("paddle speed", locationInfoX[row][col], locationInfoY[row][col]);
                } else if(hitsNeeded[row][col]==120){
                    holder = new PowerUpBrick("ball radius", locationInfoX[row][col], locationInfoY[row][col]);
                } else if(hitsNeeded[row][col]==140){
                    holder = new PowerUpBrick("shield", locationInfoX[row][col], locationInfoY[row][col]);
                } else if(hitsNeeded[row][col]==-100){
                    holder = new PenaltyBrick("neg", locationInfoX[row][col],locationInfoY[row][col]);
                } else if(hitsNeeded[row][col]==-110){
                    holder = new PenaltyBrick("ball speed", locationInfoX[row][col], locationInfoY[row][col]);
                } else if(hitsNeeded[row][col]==-120){
                    holder = new PenaltyBrick("paddle size", locationInfoX[row][col], locationInfoY[row][col]);
                } else {
                    holder = new Brick(hitsNeeded[row][col], locationInfoX[row][col], locationInfoY[row][col]);
                }
                myBricks[row][col] = holder;
            }
        }
    }
    public Rectangle getBrickNode(int row, int col){
        return myBricks[row][col].getNode();
    }
    public void incrementBricksDestroyed(){
        numberBricksDestroyed++;
    }
    public boolean isClear(){
        return (numberBricksDestroyed==totalNumberOfBricks);
    }
}
