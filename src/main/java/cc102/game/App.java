package cc102.game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        // javafx works with a Stage (window), scene (the current visible one), and scene-graphs (like the elements of the scene)
        launch();
    }

    //needed for the
    @Override
    public void start(Stage primaryStage) throws Exception {
        //to create a new stage
        // Stage name = new Stage();

        //Create a new scene, NOTE: Scenes need a root node, we'll use a simple one called Group!
        //NOTE: If we dont have scenes, the background will act funky, and if you resize it, black shades will appear.
        Group rootNode = new Group();
        Scene scene = new Scene(rootNode, Color.BLACK);

        //To change the icon, the file must be at the SOURCE (SRC) FOLDER, NOT THE APPLICATION
        // Image name = new Image('filename.format')
        // TO set the icon,
        // stage.getIcons().add(iconVariable)

        //Set the window name, i.e., the stage.
        primaryStage.setTitle("TicTacToe GUI");

        // sets the scene to show on the stage
        primaryStage.setScene(scene);

        // To set the width and height of the screen/stage:
        // primaryStage.setWidth()
        // primaryStage.setHeight()

        //Disallows resizing the screen
        primaryStage.setResizable(false);
        
        // show the stage
        primaryStage.show();
    }
}