package ui;

import core.CheckersLogic;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class CheckerGUI extends Application {

    private static CheckersLogic game;
    private int[][] board;
    Cell[][] guiBoard;
    private String userMove = "";
    Text t = new Text();

    @Override
    public void start(Stage primaryStage) {
        boolean UIMode = startGame();
        if (!UIMode) {
            new CheckersTextConsole().play(game);
        } else {
            guiBoard = new Cell[8][8];

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    int row = 8 - i;
                    char column = (char) (j + 'a');

                    if (board[i][j] == -1) {
                        if (i % 2 == j % 2)
                            guiBoard[i][j] = new Cell("-fx-background-color: papayawhip", row, column);
                        else
                            guiBoard[i][j] = new Cell("-fx-background-color: snow", row, column);
                        guiBoard[i][j].setToken(-1);
                    } else if (board[i][j] == 1) {
                        if (i % 2 == j % 2)
                            guiBoard[i][j] = new Cell("-fx-background-color: papayawhip", row, column);
                        else
                            guiBoard[i][j] = new Cell("-fx-background-color: snow", row, column);
                        guiBoard[i][j].setToken(1);
                    } else {
                        if (i % 2 == j % 2)
                            guiBoard[i][j] = new Cell("-fx-background-color: papayawhip", row, column);
                        else
                            guiBoard[i][j] = new Cell("-fx-background-color: snow", row, column);
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


            t.setText("Player black, your turn. \nPlease start by clicking a piece you want to move," +
                    " \nfollowed by clicking 1 or more open spaces you would like to move to. " +
                    "\nFinish your move by clicking Submit Move.");
            t.setFont(Font.font(15));





            Button submitMove = new Button("Submit Move");
            submitMove.setPrefSize(100, 30);
            submitMove.setOnAction(e -> handleSubmitButton());

            Pane buttons = new Pane();
            buttons.getChildren().addAll(submitMove);



            HBox bottomPane = new HBox();
            bottomPane.getChildren().addAll(t, buttons);
            bottomPane.setPadding(new Insets(25, 25, 25, 25));
            bottomPane.setSpacing(-10);


            VBox genPane = new VBox();
            genPane.getChildren().addAll(boardPane, bottomPane);
            genPane.setAlignment(Pos.TOP_CENTER);


            // Create a scene and place it in the stage
            Scene scene = new Scene(genPane, 600, 800);
            primaryStage.setTitle("Checkers GUI"); // Set the stage title
            primaryStage.setScene(scene); // Place the scene in the stage
            primaryStage.show(); // Display the stage
        }

    }

    class Cell extends Pane {
        // Token used for this cell
        private int token = -1;
        int row;
        char column;

        public Cell(String color, int row, char column) {
            setStyle("-fx-border-color: black;" + color);
            this.setPrefSize(75, 75);
            this.row = row;
            this.column = column;
            this.setOnMouseClicked(e -> handlePieceClick(e));

        }

        public int getRow() {return row;}

        public char getColumn() {return column;}

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
                this.getChildren().clear();
            }
        }
    }

    private void handlePieceClick(MouseEvent e) {
        Cell c = (Cell) e.getSource();
        int currRow = c.getRow();
        char currCol = c.getColumn();

        if (!userMove.isEmpty()) {
            userMove += "-";
        }
        userMove += currRow;
        userMove += currCol;
        System.out.println(userMove);
    }

    private void handleSubmitButton() {
        int success = game.movePiece(userMove);
        board = game.getBoard();
        userMove = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == -1) {
                    guiBoard[i][j].setToken(-1);
                } else if (board[i][j] == 1) {
                    guiBoard[i][j].setToken(-1);
                    guiBoard[i][j].setToken(1);
                } else {
                    guiBoard[i][j].setToken(-1);
                    guiBoard[i][j].setToken(0);
                }
            }
        }
        if (success == 0)
            t.setText("Invalid move, please try again. \nPlease start by clicking a piece you want to move, " +
                    " \nfollowed by clicking 1 or more open spaces you would like to move to. " +
                    "\nFinish your move by clicking Submit Move");
        if (game.determineWinner() == 1) {
            t.setText("Player Black Wins, thank you for playing.");
        } else if (game.determineWinner() == 0) {
            t.setText("Player Red Wins, thank you for playing.");
        } else if (game.determineWinner() == -1) {
            if (game.getMode() == false && game.getTurn() == 1) {
                t.setText("Player black, your turn. \nPlease start by clicking a piece you want to move," +
                        " \nfollowed by clicking 1 or more open spaces you would like to move to. " +
                        "\nFinish your move by clicking Submit Move.");
            } else if (game.getMode() == false && game.getTurn() == 0) {
                t.setText("Player red, your turn. \nPlease start by clicking a piece you want to move," +
                        " \nfollowed by clicking 1 or more open spaces you would like to move to. " +
                        "\nFinish your move by clicking Submit Move.");
            } else if (game.getMode() == true) {
                t.setText("To move a piece, \nstart by clicking a piece you want to move \nfollowed by 1 or more open spaces \nyou would like to move to. \nAfter you complete your turn" +
                        ", \nplease allow one second for the \ncomputer to complete its turn. \nAfter the computer has moved \nyou can submit your next move.");
            }
        }
    }

//-------------------------- non UI methods -------------------------------
    public boolean startGame() {
        String userIn = "";
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
