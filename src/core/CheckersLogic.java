package core;

import java.text.*;
import java.util.Arrays;

/**
 * Game logic for Checkers Game
 *
 * @author Pryce Holmes
 * @version v1.4
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

    private final boolean computerGame;
    private CheckersComputerPlayer computer;

    /** default constructor, initializes to a new game board, X's turn */
    public CheckersLogic(String input) throws UnsupportedOperationException{
        board = new int[][]{{-1, 0, -1, 0, -1, 0, -1, 0},
                            {0, -1, 0, -1, 0, -1, 0, -1},
                            {-1, 0, -1, 0, -1, 0, -1, 0},
                            {-1, -1, -1, -1, -1, -1, -1, -1},
                            {-1, -1, -1, -1, -1, -1, -1, -1},
                            {1, -1, 1, -1, 1, -1, 1, -1},
                            {-1, 1, -1, 1, -1, 1, -1, 1},
                            {1, -1, 1, -1, 1, -1, 1, -1}};
        turn = 1;
        if (input.equals("player")) {
            computerGame = false;
            computer = null;
        }
        else if (input.equals("computer")) {
            computerGame = true;
            computer = new CheckersComputerPlayer(board);
        } else {
            throw new UnsupportedOperationException("player must choose against player or computer");
        }
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

        int[] decoded;
        boolean jumpEligible = false;
        int old_y;
        int old_x;
        int new_y;
        int new_x;


        if (computerGame == false || turn == 1) {
            if (input.length() < 5)
                return 0;
        }
        while (input.length() >= 5) {
            if (computerGame && turn == 0) {
                decoded = computer.movePiece();
            } else {
                decoded = decodeMove(input);
            }
            if (decoded[0] == 0)
                return 0;

            old_y = decoded[1];
            old_x = decoded[2];
            new_y = decoded[3];
            new_x = decoded[4];

            if (checkValidMove(old_y, old_x, new_y, new_x, jumpEligible) == 0) return 0;
            else if (checkValidMove(old_y, old_x, new_y, new_x, jumpEligible) == 1) {
                board[old_y][old_x] = -1;
                board[new_y][new_x] = turn;
            } else {
                board[old_y][old_x] = -1;
                board[new_y][new_x] = turn;
                jumpEligible = true;
            }

            if (input.length() > 5) {
                input = input.substring(3);
            } else {
                input = input.substring(4);
            }

            if (computerGame && turn == 0) {
                computer.setLastX(new_x);
                computer.setLastY(new_y);
            }
        }
        computer.updateBoard(board);
        if (turn == 1) turn = 0;
        else if (turn == 0) turn = 1;
        return 1;
    }

    /**
     * Checks if a move is valid given the current position and new position of the piece
     *
     * @param old_y current y coord for the piece
     * @param old_x current x coord for the piece
     * @param new_y requested y coord for the piece
     * @param new_x requested x coord for the piece
     * @param doubleJump boolean representing if only a double jump is eligible
     * @return 0 if not valid, 1 if valid, 2 if valid and another jump eligible
     */
    public int checkValidMove(int old_y, int old_x, int new_y, int new_x, boolean doubleJump) {
        int returnValue = 1;

        // first check that coords are valid
        if (old_y < 0 || old_y > 7) return 0;
        else if (old_x < 0 || old_y > 7) return 0;
        else if (new_y < 0 || new_y > 7) return 0;
        else if (new_x < 0 || new_x > 7) return 0;

        // check that player is trying to move their own piece
        if (board[old_y][old_x] != turn) return 0;

        //base check of distance between now and old spot
        if (old_y - new_y > 2 || new_y - old_y > 2 || old_x - new_x > 2 || old_x - new_x < -2) return 0;

        // normal diag move case
        if (((old_y - new_y == 1 && turn ==1) || (new_y - old_y == 1 && turn == 0)) && (old_x - new_x == 1 || new_x - old_x == 1)) {
            if (board[new_y][new_x] != -1) return 0;
            if (doubleJump == true) return 0;
            returnValue = 1;

        } // double jump case
        else if (((old_y - new_y == 2 && turn ==1) || (new_y-old_y == 2 && turn == 0)) && (old_x - new_x == 2 || new_x - old_x == 2)) {
            int temp_y;
            int temp_x;
            if (old_x - new_x == 2) {
                temp_x = old_x - 1;
                if (turn == 1)
                    temp_y = old_y - 1;
                else temp_y = old_y + 1;
            } else {
                temp_x = old_x + 1;
                if (turn == 1)
                    temp_y = old_y - 1;
                else temp_y = old_y + 1;
            }
            if (board[temp_y][temp_x] == -1 || board[temp_y][temp_x] == turn) {
                return 0;
            } else if (board[new_y][new_x] != -1) return 0;
            else {
                board[temp_y][temp_x] = -1;
                returnValue = 2;
            }
        } else {
            return 0;
        }
        return returnValue;
    }

    /**
     *  Decodes user input to valid board coords
     *
     * @param s string of user input
     * @return int array of coords for move, first value is 1 if decode successful, 0 otherwise
     */
    public int[] decodeMove(String s) {
        CharacterIterator iter = new StringCharacterIterator(s);
        char temp;
        int[] decodeArray = new int[5];
        int decodeValid = 1;

        // decode current y value from string
        temp = iter.current();
        if (Character.isDigit(temp) == false)
            decodeValid = 0;
        decodeArray[1] = Character.getNumericValue(temp);
        decodeArray[1] = 8 - decodeArray[1];
        iter.next();

        // decode current x value from string
        temp = iter.current();
        if (Character.isAlphabetic(temp) == false)
            decodeValid = 0;
        decodeArray[2] = temp;
        decodeArray[2] = decodeArray[2] - 'a';

        iter.next();
        iter.next();

        // decode new y value from string
        temp = iter.current();
        if (Character.isDigit(temp) == false)
            decodeValid = 0;
        decodeArray[3] = Character.getNumericValue(temp);
        decodeArray[3] = 8 - decodeArray[3];
        iter.next();

        // decode new x value from string
        temp = iter.current();
        if (Character.isAlphabetic(temp) == false)
            decodeValid = 0;
        decodeArray[4] = temp;
        decodeArray[4] = decodeArray[4] - 'a';

        decodeArray[0] = decodeValid;

        return decodeArray;
    }

    /**
     * Given a position of a piece, returns if the piece has a valid move
     *
     * @param row row position of piece
     * @param column column position of piece
     * @return true if piece can move, false if piece is blocked
     */
    public boolean determineMove(int row, int column) {
        boolean success = false;

        if (board[row][column] == 1)
            success = determineMoveX(row,column);
        else if (board[row][column] == 0)
            success = determineMoveO(row, column);

        return success;
    }

    /**
     * Helper method for determine move funtion, checks for only x case moves
     * @param row row value for piece to move
     * @param column column value for piece to move
     * @return true if piece can move, false if it cannot
     */
    private boolean determineMoveX(int row, int column) {
        int yDiag = row - 1;
        int yDoubDiag = row - 2;
        int xDiagL = column - 1;
        int xDiagR = column + 1;
        int xDoubDiagL = column - 2;
        int xDoubDiagR = column + 2;

        // if piece is in last row, return false
        if (row == 0) return false;

        // if piece is not near edge, check all move cases
        if (row > 1 && row < 6 && column > 1 && column < 6) {
            // check if can move 1 space diag
            if (board[yDiag][xDiagL] == -1 || board[yDiag][xDiagR] == -1) return true;

            // check if can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagL] == -1 && board[yDiag][xDiagL] == 0) return true;

            // check if can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagR] == -1 && board[yDiag][xDiagR] == 0) return true;
        }
        // if piece is in second to last row or second to last columns, can only do single jumps
        else if (row == 1 || column == 1 || column == 6) {
            if (board[yDiag][xDiagL] == -1 || board[yDiag][xDiagR] == -1) return true;
        }

        // if piece not in last two rows, but in column zero can only move right, single or double
        else if (column == 0) {
            // check if can move 1 space diag right
            if (board[yDiag][xDiagR] == -1) return true;

            // check if can capture a piece to the right
            if (board[yDoubDiag][xDoubDiagR] == -1 && board[yDiag][xDiagR] == 0) return true;
        }

        // if piece is not in last two rows, but in column 7 can only move left, single or double
        else if (column == 7) {
            //check if can move 1 space diag left
            if (board[yDiag][xDiagL] == -1) return true;

            //check if can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagL] == -1 && board[yDiag][xDiagL] == 0) return true;
        }

        return false;

    }

    /**
     * Helper method for determine move method, checks only o pieces
     * @param row row value for piece to move
     * @param column column value for piece to move
     * @return true if piece can move, false if not
     */
    private boolean determineMoveO(int row, int column) {
        int yDiag = row + 1;
        int yDoubDiag = row + 2;
        int xDiagL = column - 1;
        int xDiagR = column + 1;
        int xDoubDiagL = column - 2;
        int xDoubDiagR = column + 2;

        // if piece is in last row, return false
        if (row == 7) return false;

        // if piece is not near edge, check all move cases
        if (row > 1 && row < 6 && column > 1 && column < 6) {
            // check if can move 1 space diag
            if (board[yDiag][xDiagL] == -1 || board[yDiag][xDiagR] == -1) return true;

            // check if can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagL] == -1 && board[yDiag][xDiagL] == 1) return true;

            // check if can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagR] == -1 && board[yDiag][xDiagR] == 1) return true;
        }
        // if piece is in second to last row or second to last columns, can only do single jumps
        else if (row == 6 || column == 1 || column == 6) {
            if (board[yDiag][xDiagL] == -1 || board[yDiag][xDiagR] == -1) return true;
        }

        // if piece not in last two rows, but in column zero can only move right, single or double
        else if (column == 0) {
            // check if can move 1 space diag right
            if (board[yDiag][xDiagR] == -1) return true;

            // check if can capture a piece to the right
            if (board[yDoubDiag][xDoubDiagR] == -1 && board[yDiag][xDiagR] == 1) return true;
        }

        // if piece is not in last two rows, but in column 7 can only move left, single or double
        else if (column == 7) {
            //check if can move 1 space diag left
            if (board[yDiag][xDiagL] == -1) return true;

            //check if can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagL] == -1 && board[yDiag][xDiagL] == 1) return true;
        }

        return false;

    }
    /**
     *  determines if there is a winner on the current board,
     *  checks if a player is eliminated or blocked
     *
     * @return -1 if no winner, 0 is O wins, 1 if X wins,
     */
    public int determineWinner() {
        int X_Moves = 0;
        int O_Moves = 0;
        int X_Pieces = 0;
        int O_Pieces = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == -1)
                    continue;
                else if (board[i][j] == 1) {
                    X_Pieces++;
                    if (determineMove(i, j))
                        X_Moves++;
                } else {
                    O_Pieces++;
                    if (determineMove(i ,j))
                        O_Moves++;
                }
            }
        }

        if (X_Pieces == 0 && O_Pieces > 0)
            return 0;
        else if (O_Pieces == 0 && X_Pieces > 0)
            return 1;
        else if (X_Moves >= 1 && O_Moves == 0)
            return 1;
        else if (O_Moves >= 1 && X_Moves == 0)
            return 0;
        else return -1;
    }

    /**
     * Get a reference of the current game board
     * @return int[][] board - the current game board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * gets whos turn it currently is
     * @return int representing whos turn it is
     */
    public int getTurn() {
        return turn;
    }

    public boolean getMode() {
        return computerGame;
    }

    public void setBoard(int[][] newBoard) {
        board = newBoard;
    }





}
