package breakout;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class SplashScreen extends Application {

    private Scene splash;
    private Button button;
    private Group root;
    private int buttonWidth = 100;
    private Paint backgroundColor = Color.LAVENDERBLUSH;
    private int size = Main.SIZE;
    private String intro = "Welcome to Dana's Game!";
    private int introTextHeight = 20;
    /*private String rule0 = "Some Rules: ";
    private String rule1 = "1. Press space to begin moving the ball!\n";
    private String rule2 = "2. Use left and right arrow keys to move the paddle";
    private String rule3 = "3. You get three lives at the beginning of each level";
    private String rule4 = "4. Hit bricks with the ball to break them";*/
    private String rules = "Some Rules:\n1. Press space to begin moving the ball!\n2. Use left and right arrow keys to move the paddle\n3. You get three lives at the beginning of each level\n4. Hit bricks with the ball to break them\n5. Bounce the ball on the left third to move it left,\n    middle to go straight up,\n    and right third to move the ball to the right";
    private int rulesTextHeight = 120;
    private String startingInstructions = "Click to begin!";

    public SplashScreen() {
        root = new Group();
        splash = new Scene(root, size, size, backgroundColor);
    }

    @Override
    public void start(Stage primaryStage){

        //Label introText = new Label(intro);
        Text introText = new Text(intro);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setStroke(Color.BLACK);
        introText.setLayoutX(size/3);
        introText.setLayoutY(size/3);
        root.getChildren().add(introText);

        Text rulesText = new Text(rules);
        rulesText.setTextAlignment(TextAlignment.LEFT);
        rulesText.setX(size/5);
        rulesText.setY(size/3+introTextHeight);
        root.getChildren().add(rulesText);

        button = new Button();
        button.setLayoutX((size-buttonWidth)/2);
        button.setLayoutY((size+introTextHeight+rulesTextHeight)/2);
        button.setText(startingInstructions);
        root.getChildren().add(button);
        primaryStage.setScene(splash);
        primaryStage.setTitle(intro);

        primaryStage.show();
        //btn.setOnAction();
    }
    public Scene getNode(){
        return splash;
    }
    public Button getButton(){
        return button;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
