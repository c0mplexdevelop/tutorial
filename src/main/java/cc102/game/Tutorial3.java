package cc102.game;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Tutorial3 extends Application {
    static String currentTurn = "X";
    static Text currentTurnText = new Text(0, 0, String.format("Current Turn: %s", currentTurn));
    Board board = new Board(createBoard());
    List<Rectangle> rects = new ArrayList<Rectangle>();
    String winningPlayer = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final Pane root = new AnchorPane();
        final Scene primaryScene = new Scene(root, 600, 700, Color.BLACK);
        final Text gameOverMessage = new Text(primaryScene.getWidth()/2, 30, "");

        primaryScene.setOnMouseClicked(mouseEvent -> {
            if(checkIfDraw(board)) {
                restartIfDraw(root, primaryScene, gameOverMessage);
            } else if(checkIfRowWon(board)) {
                System.out.println("Row won");
                drawLineOnRowWon(board, root, primaryScene, board.winningRow);
                restartIfLetterWon(root, primaryScene, gameOverMessage);
            } else if(checkIfColumnWon(board)) {
                System.out.println("Col Won");
                drawLineOnColumnWon(board, root, primaryScene, board.winningCol);
                restartIfLetterWon(root, primaryScene, gameOverMessage);
            } else if(checkIfLeftDiagonalWon(board)) {
                System.out.println("Left Diagonal Won");
                drawLineLeftDiagonalWon(board, root, primaryScene);
            } else if(checkIfRightDiagonalWon(board)) {
                System.out.println("Right Diagonal Won");
            }

            System.out.println(root.getChildren().toString());
        });

        /*
         * currentTurnText.setFill(Color.RED);
         * currentTurnText.setTextOrigin(VPos.TOP);
         * currentTurnText.setFont(Font.font("Times New Roman", 30));
         */

        setCurrentTurn(currentTurnText, "X");
    
        gameOverMessage.setFill(Color.RED);
        gameOverMessage.setTextOrigin(VPos.TOP);
        gameOverMessage.setFont(Font.font("Times New Roman", 25));


        rects = createFXBoard(rects, primaryScene, root);

        // root.getChildren().addAll(rects);
        root.getChildren().add(currentTurnText);
        root.getChildren().add(gameOverMessage);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

    public static String[][] createBoard() {
        String[][] board = new String[3][3];

        for(int row = 0; row < 3; row ++) {
            for(int col = 0; col < 3; col ++) {
                board[row][col] = " ";
            }
        }

        return board;
    }

    private List<Rectangle> createFXBoard(List<Rectangle> rects, Scene scene, Pane root) {
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                // Rect takes the first 2 arguments as WIDTH AND LENGTH, with the last one as Color.
                Rectangle rect = new Rectangle(scene.getWidth()/3, 600/3);

                final Text boardMove = new Text("X");
                boardMove.setFill(Color.RED);
                
                boardMove.setFont(Font.font("Times New Roman", 50));

                rect.setX(scene.getWidth()/3 * col);
                rect.setY(600/3 * row + 100);
                // Set the border color RED
                rect.setStroke(Color.RED);
                //set the thickness of the border, i.e. A Strok.
                rect.setStrokeWidth(1);

                // Get the respective coordiantes of the rectangles, then divide it by its length to get the row index
                int boardRow = (int) (rect.getY() / rect.getWidth()); // We get Y as rows go downward, and X doesnt change
                // Same as for column index
                int boardCol = (int) (rect.getX() / rect.getHeight());

                //Will happen when the rectangle is clicked.
                rect.setOnMouseClicked(mouseEvent -> {
                    
                    if(board.checkIfValid(boardRow, boardCol)) {
                        boardMove.setText(currentTurn);

                        board.board[boardRow][boardCol] = currentTurn;

                        // One liner for "If current turn is X, change it to O, otherwise X"
                        currentTurn = (currentTurn=="X") ? "O" : "X";
                        // Change the currentTurnText to display the current turn
                        setCurrentTurn(currentTurnText, currentTurn);
                        // Set the text to be on top of the rect, other words: Make the text visible
                        boardMove.toFront();
                        // Put the origin coordinate (0,0) on the top left of the text
                        boardMove.setTextOrigin(VPos.TOP);
                        
                        /*
                         *  To center the text on the rectangle, we get the rect's X coordinate and the third of its length
                         *  Then, we add it to the LENGTH OF THE TEXT within the pixel (pixel length, like rect.getWidth())
                         *  Now its centered in the x axis, do the same for col but instead of addition, s
                         */
                        double centerX = (rect.getX() + rect.getWidth() / 3) + (boardMove.getLayoutBounds().getWidth() / 2); 
                        double centerY = (rect.getY() + rect.getHeight() / 2) - (boardMove.getLayoutBounds().getHeight() / 2);

                        boardMove.setX(centerX);
                        boardMove.setY(centerY);

                        System.out.println(Arrays.deepToString(board.board));

                    }
                    
                });

                root.getChildren().addAll(rect, boardMove);
                rects.add(rect);
            }
        }

        return rects;
    }

    public static void setCurrentTurn(Text currentTurn, String turn) {
        currentTurn.setText(String.format("Current Turn: %s", turn));
        currentTurn.setFill(Color.RED);
        currentTurn.setTextOrigin(VPos.TOP);
        currentTurn.setFont(Font.font("Times New Roman", 30));
    }

    public boolean checkIfDraw(Board board) {
        for (String[] row : board.board) {
            for (String position : row) {
                if (position.equals(" ")) {
                    return false;
                }
            }
        }

        return true;
    }

    public void restart(Pane root, Scene primaryScene, Text gameOverMessage) {
        // Update the currentTurnText properly, as if X won, it remains as O after restart
        setCurrentTurn(currentTurnText, currentTurn);
        // We just need to re-add the gameOverMessage and currentTurnText
        root.getChildren().add(gameOverMessage);
        root.getChildren().add(currentTurnText);

    }

    public void restartIfDraw(Pane root, Scene primaryScene, Text gameOverMessage) {
        PauseTransition transition = new PauseTransition(Duration.seconds(0.84));
        transition.setOnFinished(event -> {
            rects = createFXBoard(rects, primaryScene, root);
            board.board = createBoard();
            currentTurn = "X";
            gameOverMessage.setText("DRAW!");
            gameOverMessage.setX((primaryScene.getWidth() / 2) - (gameOverMessage.getLayoutBounds().getWidth() / 2));
        });

        transition.play();
        primaryScene.setFill(Color.BLACK);
    }

    public boolean checkIfRowWon(Board board) {
        for (int row = 0; row < 3; row++) {
            if(board.board[row][0].equals(" ")) {
                continue;
            }

            if (board.board[row][0].equals(board.board[row][1]) && board.board[row][1].equals(board.board[row][2])) {
                winningPlayer = board.board[row][0];
                board.winningRow = row;
                return true;
            }
        }

        return false;
    }

    public void drawLineOnRowWon(Board board, Pane root, Scene scene, int winningRow) {
        // Get the index position of the correct Rect, we go down as checking for rows is down
        // This multiplies 3 by 0, 1, or 2. If its 0, then we check 0,1,2; if its 1, we check 3,4,5; and if its 2, we check 6,7,8

        double beginningPosition = scene.getWidth()/3 * winningRow + 100;
        double rectHalfWidth = 100;

        Line winningLine = new Line(0, beginningPosition+rectHalfWidth, scene.getWidth(), beginningPosition+rectHalfWidth);
        winningLine.setStroke(Color.LIGHTBLUE);
        winningLine.setStrokeWidth(10);
        winningLine.toBack();
        
        root.getChildren().add(winningLine);
        
        return;
    }

    public void restartIfLetterWon(Pane root, Scene primaryScene, Text gameOverMessage) {
        PauseTransition transition = new PauseTransition(Duration.seconds(0.84));
        transition.setOnFinished(event -> {
            //We clear the group since it would infinitely expand and take alot of memory. Clearing it, then rebuilding it is easier
            root.getChildren().clear();
            rects = createFXBoard(rects, primaryScene, root);
            board.board = createBoard();
            currentTurn = "X";
            gameOverMessage.setText(String.format("%s WON", winningPlayer.toUpperCase()));
            gameOverMessage.setX((primaryScene.getWidth() / 2) - (gameOverMessage.getLayoutBounds().getWidth() / 2));
            restart(root, primaryScene, gameOverMessage);
        });

        transition.play();
    }

    public boolean checkIfColumnWon(Board board) {
        for(int col = 0; col < 3; col++) {
            if(board.board[0][col].equals(" ")) {
                continue;
            }

            if(board.board[0][col].equals(board.board[1][col]) && board.board[1][col].equals(board.board[2][col])) {
                winningPlayer = board.board[0][col];
                board.winningCol = col;
                return true;
            }
        }

        return false;
    }

    public void drawLineOnColumnWon(Board board2, Pane root, Scene primaryScene, int winningCol) {
        //check which column to begin with, so we move in the x-axis
        double beginningPosition = primaryScene.getWidth()/3 * winningCol;
        double rectHalfWidth = (primaryScene.getWidth()/3)/2;
        Line winningLine = new Line(beginningPosition + rectHalfWidth + 3, 105 , beginningPosition + rectHalfWidth + 3, primaryScene.getHeight());
        // Its 105 since we put it within the rectangle, its probably like (Stroke Width / # of suqares) + rect stroke width + 1
        // For the above, we added +3 to centralize the line stroke on the center. Essentially, just do strokeWidth/3
        winningLine.setStroke(Color.LIGHTBLUE);
        winningLine.setStrokeWidth(10);
        winningLine.toBack();

        root.getChildren().add(winningLine);

        return;
    }
   
    public boolean checkIfLeftDiagonalWon(Board board) {
        if(board.board[0][0].equals(" ")) {
            return false;
        } 
        
        if(board.board[0][0].equals(board.board[1][1]) && board.board[1][1].equals(board.board[2][2])) {
            return true;
        }

        return false;
    }

    public void drawLineLeftDiagonalWon(Board board, Pane root, Scene primaryScene) {
        
    }

    public boolean checkIfRightDiagonalWon(Board board) {
        if(board.board[0][2].equals(" ")) {
            return false;
        }
        
        if(board.board[0][2].equals(board.board[1][1]) && board.board[1][1].equals(board.board[2][0])) {
            return true;
        }

        return false;
    }
}
