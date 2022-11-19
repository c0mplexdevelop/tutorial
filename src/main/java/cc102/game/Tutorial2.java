package cc102.game;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tutorial2 extends Application {
    public static void main(String[] args) {
        // javafx works with a Stage (window), scene (the current visible one), and
        // scene-graphs (like the elements of the scene)
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Group rootNode = new Group();
        // in creating a new instance of the scene, you can also put the width, height, and the color to fill the Scene
        Scene primaryScene = new Scene(rootNode, 800, 600, Color.BLUE);

        //We create a new text object to display, then we use the setText method to put a text value within
        //you can also use new Text(x,y,text) for cleaner code
        Text tutorialText = new Text();
        tutorialText.setTextOrigin(VPos.TOP);
        tutorialText.setText("Tutorial Text!");
        tutorialText.setFont(Font.font("Times New Roman", 30));
        tutorialText.setX(0);
        tutorialText.setY(0);

        Line line = new Line(300,400,10,400);
        line.setStrokeWidth(10);


        rootNode.getChildren().addAll(tutorialText,line);

        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }
}
