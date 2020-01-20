package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * SplashScreen exists to give the breakout game an intro screen
 * Assumptions:
 *      none
 * Dependencies
 *      depends on Main for the screen size, and breakout package
 * an example of how to use it
 * any other details users should know
 */
public class SplashScreen extends Application {

    private Scene splash;
    private Button button;
    private Group root;
    private int buttonWidth = 100;
    private Paint backgroundColor = Color.LAVENDERBLUSH;
    private int size = Main.SIZE;
    private String intro = "Welcome to Dana's Game!";
    private int introTextHeight = 20;
    private String rules = "Some Rules:\n1. Press space to begin moving the ball!\n2. Use left and right arrow keys to move the paddle\n3. You get three lives at the beginning of each level\n4. Hit bricks with the ball to break them\n5. Bounce the ball on the left third to move it left,\n    middle to go straight up,\n    and right third to move the ball to the right";
    private int rulesTextHeight = 120;
    private String startingInstructions = "Click to begin!";

    /**
     * Constructor
     * create a new collection (root) for all of the objects on this SplashScreen
     * create a new scene (splash)
     */
    public SplashScreen() {
        root = new Group();
        splash = new Scene(root, size, size, backgroundColor);
    }

    @Override
    /**
     * @Override Application.start()
     * Set the scene with the intro text, rules, and button
     * @param primaryStage
     */
    public void start(Stage primaryStage){
        // set intro text
        Text introText = new Text(intro);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setStroke(Color.BLACK);
        introText.setLayoutX(size/3);
        introText.setLayoutY(size/3);
        root.getChildren().add(introText);

        // set rules text
        Text rulesText = new Text(rules);
        rulesText.setTextAlignment(TextAlignment.LEFT);
        rulesText.setX(size/5);
        rulesText.setY(size/3+introTextHeight);
        root.getChildren().add(rulesText);

        // set button
        button = new Button();
        button.setLayoutX((size-buttonWidth)/2);
        button.setLayoutY((size+introTextHeight+rulesTextHeight)/2);
        button.setText(startingInstructions);
        root.getChildren().add(button);
        primaryStage.setScene(splash);
        primaryStage.setTitle(intro);

        primaryStage.show();
    }

    /**
     * exists so the SplashScreen's scene can be added to a stage
     * no assumptions
     * @return Scene splash
     */
    public Scene getNode(){
        return splash;
    }

    /**
     * exists so the SplashScreen's button can be added to a collection
     * no assumptions
     * @return Button button
     */
    public Button getButton(){
        return button;
    }
}
