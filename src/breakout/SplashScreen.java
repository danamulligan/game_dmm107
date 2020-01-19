package breakout;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class SplashScreen extends Application {

    private Scene splash;
    private Button button;
    private Group root;
    private int big = 40;
    private Paint backgroundColor = Color.LAVENDERBLUSH;
    private int size = Main.SIZE;
    private String intro = "Welcome to Dana's Game";
    private String startingInstructions = "Click to begin!";

    public SplashScreen() {
        root = new Group();
        splash = new Scene(root, size, size, backgroundColor);
    }

    @Override
    public void start(Stage primaryStage){

        Label introText = new Label(intro);
        //introText.setFont(big);
        introText.setLayoutX(size/2);
        introText.setLayoutY(size/2);
        root.getChildren().add(introText);

        button = new Button();
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
        /*SplashScreen test = new SplashScreen();
        start(test.getNode());*/
        launch(args);
    }
}
