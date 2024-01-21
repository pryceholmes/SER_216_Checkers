package ui;

import core.CheckersLogic;

/**
 * Text-based UI implementation of Checkers game
 *
 * @author Pryce Holmes
 * @version 1.2
 */
public class CheckersTextConsole {
    public static void main(String[] args) {
        CheckersLogic game = new CheckersLogic();
        int logicComms;
        String userIn;
        while (game.determineWinner() == -1) {
            displayBoard(game);
            displayTurn(game);
            userIn = " ";
            logicComms = game.movePiece(userIn);

            if (logicComms == 0)
                System.out.println("invalid move, try again");

        }
    }

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



}
