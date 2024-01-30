package ui;

import core.CheckersLogic;


import java.util.Scanner;

/**
 * Text-based UI implementation of Checkers game
 *
 * @author Pryce Holmes
 * @version 1.4
 */
public class CheckersTextConsole {
    /**
     * Main method for console-based checkers game. Uses CheckersLogic.java for all game logic
     *
     * @param args
     */
    public static void main(String[] args) {
        int logicComms;
        String userIn;
        CheckersLogic game;
            try {
                // catch if user didn't clarify correct input
                userIn = startNewGame();
                if (!userIn.equals("player") && !userIn.equals("computer"))
                    throw new UnsupportedOperationException("invalid game mode");
            } catch (UnsupportedOperationException ex) {
                System.out.println("Sorry, this is not a valid game mode. Please rerun the program to try again.");
                throw ex;
            }
            game = new CheckersLogic(userIn);

        while (game.determineWinner() == -1) {


            if (game.getTurn() == 1 || !game.getMode()) {
                displayBoard(game);
                displayTurn(game);
                try {
                    // try to get input from user
                    userIn = getInput();
                } catch (Exception ex) {
                    // if exception is thrown from input, cause game to return an invalid move
                    // this will give user the option to try again
                    userIn = "invalid";
                }

            } else {
                displayBoard(game);
                System.out.println("It is the computers turn, one moment while they pick their move.");
                userIn = "Cturn";
            }
            try {
                // try to move piece for user
                logicComms = game.movePiece(userIn);
            } catch (Exception ex) {
                // if exception is throw such as invalid move or ArrayOutOfBounds than catch
                // and let user try again by forcing invalid input in next if statement
                logicComms = 0;
            }
            if (logicComms == 0 && (!game.getMode() || game.getTurn() == 1))
                System.out.println("invalid move, try again");

        }
        displayBoard(game);
        if (game.determineWinner() == 0)
            System.out.println("Player o Wins");
        else if (game.determineWinner() == 1)
            System.out.println("Player x Wins");
    }

    /**
     * displays text to the console regarding who's turn it is and instructions for moving a piece
     *
     * @param game a reference to the current state of the checkers game
     */
    public static void displayTurn(CheckersLogic game) {
        char player;
        if (game.getTurn() == 1)
            player = 'x';
        else player = 'o';
        System.out.printf("Player %c - your turn.\n", player);
        System.out.println("Choose a cell position of a piece to be moved and the new position. " +
                "e.g. 3a-4b or 3g-4h");
        System.out.println("you can capture multiple pieces by listing more than one spot to move to but all must be eligible captures.");

    }

    /**
     * displays the current state of the game board
     * @param game A reference to the current checkers game
     */
    public static void displayBoard(CheckersLogic game) {
        int[][] currentBoard = game.getBoard();

        for (int i = 0; i < currentBoard.length + 1; i++) {
            if (i < currentBoard.length)
                System.out.printf("%s | ", String.valueOf(8-i));
            else
                System.out.printf("   ");
            for (int j = 0; j < currentBoard[0].length; j++) {
                if (i == currentBoard.length)
                    System.out.printf(" %c  ", j+97);
                else if (currentBoard[i][j] == -1)
                    System.out.print("_ | ");
                else if (currentBoard[i][j] == 1)
                    System.out.printf("x | ");
                else {
                    System.out.printf("o | ");
                }
            }

            System.out.println();
        }

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
        System.out.println("To start a new game, enter 'player' if you want to play against another player; " +
                " enter 'computer' to play against the computer");
        return getInput();
    }





}
