package core;

import java.text.*;
/**
 * Game logic for Checkers Game
 *
 * @author Pryce Holmes
 * @version v1.0
 */
public class CheckersLogic {
    /** 2D integer array representing the checkers board,
     * int -1 = empty
     * int 0 = O's piece
     * int 1 = X's piece
     */
    private int[][] board;

    /** var to determine who's turn it is,
     * int 0 = O turn
     * int 1 = X turn
     */
    private int turn;

    /** default constructor, initializes to a new game board, X's turn */
    public CheckersLogic() {
        board = new int[][]{{-1, 0, -1, 0, -1, 0, -1, 0},
                            {0, -1, 0, -1, 0, -1, 0, -1},
                            {-1, 0, -1, 0, -1, 0, -1, 0},
                            {-1, -1, -1, -1, -1, -1, -1, -1},
                            {-1, -1, -1, -1, -1, -1, -1, -1},
                            {1, -1, 1, -1, 1, -1, 1, -1},
                            {-1, 1, -1, 1, -1, 1, -1, 1},
                            {1, -1, 1, -1, 1, -1, 1, -1}};
        turn = 1;
    }

    /**
     * Given a string input of the move to be done (e.g., 3a-4b),
     * validates move then moves piece on game board if valid.
     *
     *
     * @param input, string representation of move (e.g. 6f-5e)
     * @return 1 if move completed, 0 if invalid
     */
    public int movePiece(String input) {

        int [] decoded = decodeMove(input);
        int old_y = decoded[0];
        int old_x = decoded[1];
        int new_y = decoded[2];
        int new_x = decoded[3];

        // check if valid and move if so, else return invalid
        if (checkValidMove(old_y, old_x, new_y, new_x)) {
            board[old_y][old_x] = -1;
            board[new_y][new_x] = turn;
        } else return 0;







        return 0;


    }

    public boolean checkValidMove(int old_y, int old_x, int new_y, int new_x) {
        // first check that coords are valid
        if (old_y < 0 || old_y > 7) return false;
        else if (old_x < 0 || old_y > 7) return false;
        else if (new_y < 0 || new_y > 7) return false;
        else if (new_x < 0 || new_x > 7) return false;

        // check that player is trying to move their own piece
        if (board[old_y][old_x] != turn) return false;

        //base check of distance between now and old spot
        if (old_y - new_y > 2 || old_x - new_x > 2 || old_x - new_x < -2) return false;

        // normal diag move case
        if (old_y - new_y == 1 && (old_x - new_x == 1 || new_x - old_x == 1)) {
            if (board[new_y][new_x] != -1) return false;

        } // double jump case
        else if (old_y - new_y == 2 && (old_x - new_x == 2 || new_x - old_x == 2)) {
            int temp_y;
            int temp_x;
            if (old_x - new_x == 2) {
                temp_x = old_x - 1;
                temp_y = old_y + 1;
            } else {
                temp_x = old_x + 1;
                temp_y = old_y + 1;
            }
            if (board[temp_y][temp_x] == -1 || board[temp_y][temp_x] == turn) {
                return false;
            } else if (board[new_y][new_x] != -1) return false;
            else {
                board[temp_y][temp_x] = -1;
            }
        }
        return true;
    }

    /**
     *  Decodes user input to valid board coords
     *
     * @param s string of user input
     * @return int array of coords for move
     */
    public int[] decodeMove(String s) {
        CharacterIterator iter = new StringCharacterIterator(s);
        int[] decodeArray = new int[4];

        // decode current y value from string
        decodeArray[0] = Character.getNumericValue(iter.current());
        decodeArray[0] = 8 - decodeArray[0];
        iter.next();

        // decode current x value from string
        decodeArray[1] = iter.current();
        decodeArray[1] = decodeArray[1] - 'a';

        iter.next();
        iter.next();

        // decode new y value from string
        decodeArray[2] = Character.getNumericValue(iter.current());
        decodeArray[2] = 8 - decodeArray[2];
        iter.next();

        // decode new x value from string
        decodeArray[3] = iter.current();
        decodeArray[3] = decodeArray[3] - 'a';

        return decodeArray;
    }

    /**
     * Given a position of a piece, returns if the piece has a valid move
     *
     * @param row row position of piece
     * @param column column position of piece
     * @return 1 if piece can move, 0 if piece is blocked
     */
    public int determineMove(int row, int column) {
        return 0;
    }

    /**
     *  determines if there is a winner on the current board,
     *  checks if a player is eliminated or blocked
     *
     * @return -1 if no winner, 0 is O wins, 1 if X wins
     */
    public int determineWinner() {
        return 0;
    }





}
