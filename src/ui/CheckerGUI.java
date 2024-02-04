package ui;

import core.CheckersLogic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

import java.util.Scanner;

public class CheckerGUI extends Application {

    private static CheckersLogic game;
    private int[][] board;

    private String userIn;

    class Cell extends Pane {
        // Token used for this cell
        private int token = -1;

        public Cell(String color) {
            setStyle("-fx-border-color: black;" + color);
            //setStyle(color);
            this.setPrefSize(75, 75);
        }

        /**
         * Return token
         */
        public int getToken() {
            return token;
        }

        /**
         * Set a new token
         */
        public void setToken(int i) {
            token = i;

            if (token == 1) {
                Circle circ1 = new Circle(37.5, 37.5,30, Color.BLACK);
                circ1.setStroke(Color.BLACK);

                // Add the circle to the pane
                this.getChildren().addAll(circ1);
            } else if (token == 0) {
                Circle circ2 = new Circle(37.5, 37.5, 30, Color.RED);
                circ2.setStroke(Color.BLACK);

                // Add the circle to the pane
                this.getChildren().addAll(circ2);
            } else if (token == -1) {
                // this.getChildren().clear();
            }
        }
    }
    @Override
    public void start(Stage primaryStage) {
        boolean UIMode = startGame();
        if (!UIMode) {
            new CheckersTextConsole().play(game);
        } else {
            Cell[][] guiBoard = new Cell[8][8];

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == -1) {
                        if (i % 2 == j % 2)
                            guiBoard[i][j] = new Cell("-fx-background-color: papayawhip");
                        else
                            guiBoard[i][j] = new Cell("-fx-background-color: snow");
                        guiBoard[i][j].setToken(-1);
                    } else if (board[i][j] == 1) {
                        if (i % 2 == j % 2)
                            guiBoard[i][j] = new Cell("-fx-background-color: papayawhip");
                        else
                            guiBoard[i][j] = new Cell("-fx-background-color: snow");
                        guiBoard[i][j].setToken(1);
                    } else {
                        if (i % 2 == j % 2)
                            guiBoard[i][j] = new Cell("-fx-background-color: papayawhip");
                        else
                            guiBoard[i][j] = new Cell("-fx-background-color: snow");
                        guiBoard[i][j].setToken(0);
                    }
                }
            }
            GridPane boardPane = new GridPane();
            boardPane.setAlignment(Pos.TOP_CENTER);
            boardPane.setPadding(new Insets(10, 10, 10, 10));
            for (int i = 0; i < guiBoard.length; i++)
                for (int j = 0; j < guiBoard[i].length; j++)
                    boardPane.add(guiBoard[i][j], j, i);

            VBox genPane = new VBox();
            genPane.getChildren().add(boardPane);
            genPane.setAlignment(Pos.TOP_CENTER);


            // Create a scene and place it in the stage
            Scene scene = new Scene(genPane, 600, 800);
            primaryStage.setTitle("Checkers GUI"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage
        }
    }

//-------------------------- non UI methods -------------------------------
    public boolean startGame() {

        boolean GUI = false;
        boolean success = false;

        while (!success) {
            userIn = setUI();
            success = userIn.equals("console") || userIn.equals("GUI");
        }

        if(userIn.equals("GUI")) {
            GUI = true;
        }

        success = false;
        while (!success) {
            userIn = startNewGame();
            success = userIn.equals("player") || userIn.equals("computer");
        }
        game = new CheckersLogic(userIn);
        board = game.getBoard();

        return GUI;
    }

    /**
     * reads input from the user using Scanner
     * @return the next line of input from the user
     */
    public static String getInput() {
        Scanner scnr = new Scanner(System.in);

        return scnr.nextLine();
    }

    /**
     * gathers input for game mode to start a new instance of the checkers game
     * @return user input of game mode to start playing
     */
    public static String startNewGame() {
        System.out.println("Enter 'player' if you want to play against another player; " +
                " enter 'computer' to play against the computer");
        return getInput();
    }

    public static String setUI() {
        System.out.println("To start a new game, enter 'console' for console based ui or 'GUI' for GUI game");
        return getInput();
    }

}
