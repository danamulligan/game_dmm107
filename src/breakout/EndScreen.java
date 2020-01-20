package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * An EndScreen Exists to signify that the game is over, and display the final score
 * assumptions (what situations or values might cause it to fail)
 * Dependencies:
 *      package breakout
 *      Group Class
 *      Scene Class
 *      Label Class
 *      Color Class
 *      Paint Class
 *      Stage Class
 * Create a EndScreen to put at the end of the breakout game
 */
public class EndScreen extends Application {

    private int gameScore = 0;
    private Scene end;
    private Group root;
    private int screenSize;
    private Paint background;
    private int scoreTextWidth = 35;

    /**
     * Constructor
     * create a collection to organize all of the objects, initialize variables, and create a scene
     */
    public EndScreen(){
        root = new Group();
        screenSize = Main.SIZE;
        background = Color.LAVENDERBLUSH;
        end = new Scene(root,screenSize,screenSize,background);
    }
    @Override
    /**
     * @Override Application.start()
     *  Set texts to display score, and prompt the user to quit the window
     */
    public void start(Stage primaryStage) {
        Label scoreText = new Label("Game is Over!\nScore: "+gameScore);
        scoreText.setLayoutX(screenSize/2 - scoreTextWidth);
        scoreText.setLayoutY(screenSize/2-scoreTextWidth);
        root.getChildren().add(scoreText);

        Label getOut = new Label("Please click the red button to exit this window!");
        root.getChildren().add(getOut);

        primaryStage.setScene(end);
        primaryStage.setTitle("End of Game");

        primaryStage.show();
    }

    /**
     * set gameScore to score
     * @param score
     */
    public void setGameScore(int score){
        gameScore = score;
    }

    /**
     * return the EndScreen Scene so it can be added to a collection
     * @return end
     */
    public Scene getNode(){
        return end;
    }

    /**
     * return the sub collection
     * @return root
     */
    public Group getRoot(){
        return root;
    }
}
