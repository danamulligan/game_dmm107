package breakout;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.TextStyle;
import java.util.Scanner;

public class Level {
    public Brick[][] myBricks;
    public int[][] hitsNeeded;
    public int[][] locationInfoX;
    public int[][] locationInfoY;
    int screenSize;
    int brickWidth;
    int brickHeight;
    int totalNumberOfBricks = 0;

    public void setLevelInformation(File filename, int SCREEN_SIZE, int BRICK_WIDTH, int BRICK_HEIGHT){
        try{
            Scanner sc = new Scanner(filename); //red line bc you're not checking that filename is a valid file
            screenSize = SCREEN_SIZE;
            brickWidth = BRICK_WIDTH;
            brickHeight = BRICK_HEIGHT;
            hitsNeeded = new int[screenSize/brickHeight][screenSize/brickWidth];
            locationInfoX = new int[screenSize/brickHeight][screenSize/brickWidth];
            locationInfoY = new int[screenSize/brickHeight][screenSize/brickWidth];
            for(int row=0; row < screenSize/brickHeight; row++){
                for(int col=0; col<screenSize/brickWidth; col++){
                    if(sc.hasNext()){
                        hitsNeeded[row][col] = sc.nextInt();
                        locationInfoX[row][col] = col*brickWidth;
                        locationInfoY[row][col] = row*brickHeight;
                        System.out.println("Hits Needed: " + hitsNeeded[row][col] + " X: "+locationInfoX[row][col]+" Y: "+locationInfoY[row][col]);
                        totalNumberOfBricks++;
                    }
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }
    public void setUpLevel(){
        myBricks = new Brick[screenSize/brickHeight][screenSize/brickWidth];
        System.out.println("total num of bricks: "+totalNumberOfBricks);
        for(int row=0; row<screenSize/brickHeight; row++){
            for(int col = 0; col<screenSize/brickWidth; col++){
                Brick holder;
                if(hitsNeeded[row][col]==0){
                    holder = new Brick();
                }
                else{
                    holder = new Brick(hitsNeeded[row][col], locationInfoX[row][col], locationInfoY[row][col]);
                }
                myBricks[row][col] = holder;
                System.out.println("Made a brick that needs " + hitsNeeded[row][col] + " hits at X: "+locationInfoX[row][col]+" Y: "+locationInfoY[row][col]);
            }
        }
        System.out.println("done with setUpLevel()");
    }
    public Rectangle getBrickNode(int row, int col){
        return myBricks[row][col].getNode();
    }

    public static void main(String[] args) {
        Level test = new Level();
        File testFile = new File("/Users/danamulligan/Archives/workspace308/game_dmm107/doc/testFileLevel");
        test.setLevelInformation(testFile, 400, 20, 10);
        test.setUpLevel();
    }
}
