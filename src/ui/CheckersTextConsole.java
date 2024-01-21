package ui;

import core.CheckersLogic;

/**
 * Text-based UI implementation of Checkers game
 *
 * @author Pryce Holmes
 * @version 0.0
 */
public class CheckersTextConsole {
    public static void main(String[] args) {
        CheckersLogic game = new CheckersLogic();

        int test = game.movePiece("3c-4d");
        test = game.movePiece("4d-5c");


    }
}
