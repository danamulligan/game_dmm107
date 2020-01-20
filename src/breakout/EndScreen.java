package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class EndScreen extends Application {

    private int gameScore = 0;
    private Scene end;
    private Group root;
    private int screenSize;
    private Paint background;

    public EndScreen(){
        root = new Group();
        screenSize = Main.SIZE;
        background = Color.LAVENDERBLUSH;
        end = new Scene(root,screenSize,screenSize,background);
    }
    @Override
    public void start(Stage primaryStage) {
        Label scoreText = new Label("Score: "+gameScore);
        //introText.setFont(big);
        scoreText.setLayoutX(screenSize/2 - scoreText.getWidth());
        scoreText.setLayoutY(screenSize/2);
        root.getChildren().add(scoreText);

        /*button = new Button();
        button.setText(startingInstructions);
        root.getChildren().add(button);*/
        primaryStage.setScene(end);
        primaryStage.setTitle("End of Game");

        primaryStage.show();
    }
    public void setGameScore(int score){
        gameScore = score;
    }
    public Scene getNode(){
        return end;
    }
    public Group getRoot(){
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
