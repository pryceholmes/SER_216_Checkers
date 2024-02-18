package core;

import java.util.NoSuchElementException;

/**
 * This class implements a "computer player" that can play through a checkers game with a user
 *
 */
public class CheckersComputerPlayer {

    /**
     * The x coordinate of the last moved piece by the computer.
     */
    private int lastX;

    /**
     * The y coordinate of the last moved piece by the computer.
     */
    private int lastY;

    /**
     * holds a reference to the game board
     */
    private int[][] board;

    /**
     * Constructor to create a new computer player, given a reference to game board. No last moved piece
     *
     * @param boardRef A reference to game board of the game computer player is playing with user
     */
    public CheckersComputerPlayer(int[][] boardRef) {
        board = boardRef;
        lastX = -1;
        lastY = -1;

    }

    /**
     * sets the value of the last used x coordinate
     *
     * @param x the x value of the last used piece
     */
    public void setLastX(int x) {
        lastX = x;
    }

    /**
     * sets the value of the last used y coordinate
     *
     * @param y the y value of the last used piece
     */
    public void setLastY(int y) {
        lastY = y;
    }

    /**
     * finds a computer player piece that has a valid move
     *
     * @return int array containing success value, x coord, and y coord.
     * If piece was found, array[0] is 1, else array[0] is 0.
     */
    public int[] findPiece() {
        int[] pieceCoords = {0, 0, 0};

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == -1)
                    continue;
                else if (board[i][j] == 1) {
                    continue;
                } else {
                    if (findValidMove(i, j)[0] == 1) {
                        pieceCoords[1] = i;
                        pieceCoords[2] = j;
                        pieceCoords[0] = 1;
                        break;
                    }
                }
            }
        }
        return pieceCoords;
    }

    /**
     * given a piece, returns the coords of where to move this piece to. Assumes piece is computer piece.
     *
     * @param row    row number of given piece.
     * @param column column number of given piece.
     * @return int[] containing success value, y coord, and x coord.
     * If valid move was found, array[0] is 1, else array[0] is 0.
     */
    public int[] findValidMove(int row, int column) {
        int[] moveCoords = {0, 0, 0};

        int yDiag = row + 1;
        int yDoubDiag = row + 2;
        int xDiagL = column - 1;
        int xDiagR = column + 1;
        int xDoubDiagL = column - 2;
        int xDoubDiagR = column + 2;

        // if piece is in last row, return false
        if (row == 7) return moveCoords;

        // if piece is not near edge, check all move cases
        if (row < 6 && column > 1 && column < 6) {
            // check if piece can move 1 space diag
            if (board[yDiag][xDiagL] == -1) {
                moveCoords[1] = yDiag;
                moveCoords[2] = xDiagL;
                moveCoords[0] = 1;
                return moveCoords;
            } else if (board[yDiag][xDiagR] == -1) {
                moveCoords[1] = yDiag;
                moveCoords[2] = xDiagR;
                moveCoords[0] = 1;
                return moveCoords;
            }
            // check if piece can capture a piece to the left
            else if (board[yDoubDiag][xDoubDiagL] == -1 && board[yDiag][xDiagL] == 1) {
                moveCoords[1] = yDoubDiag;
                moveCoords[2] = xDoubDiagL;
                moveCoords[0] = 1;
                return moveCoords;
            }
            // check if piece can capture a piece to the left
            else if (board[yDoubDiag][xDoubDiagR] == -1 && board[yDiag][xDiagR] == 1) {
                moveCoords[1] = yDoubDiag;
                moveCoords[2] = xDoubDiagR;
                moveCoords[0] = 1;
                return moveCoords;
            }
        }
        // if piece is in second to last row or second to last columns, can only do single jumps
        else if (row == 6 || column == 1 || column == 6) {
            if (column < 7) {
                if (board[yDiag][xDiagR] == -1) {
                    moveCoords[1] = yDiag;
                    moveCoords[2] = xDiagR;
                    moveCoords[0] = 1;
                    return moveCoords;
                }
            }
            if (column > 0) {
                if (board[yDiag][xDiagL] == -1) {
                    moveCoords[1] = yDiag;
                    moveCoords[2] = xDiagL;
                    moveCoords[0] = 1;
                    return moveCoords;
                }
            }
        }

        // if piece is not in last two rows, but in column zero can only move right, single or double
        else if (column == 0) {
            // check if piece can move 1 space diag right
            if (board[yDiag][xDiagR] == -1) {
                moveCoords[1] = yDiag;
                moveCoords[2] = xDiagR;
                moveCoords[0] = 1;
                return moveCoords;
            }

            // check if piece can capture a piece to the right
            else if (board[yDoubDiag][xDoubDiagR] == -1 && board[yDiag][xDiagR] == 1) {
                moveCoords[1] = yDoubDiag;
                moveCoords[2] = xDoubDiagR;
                moveCoords[0] = 1;
                return moveCoords;
            }
        }

        // if piece is not in last two rows, but in column 7 can only move left, single or double
        else if (column == 7) {
            //check if piece can move 1 space diag left
            if (board[yDiag][xDiagL] == -1) {
                moveCoords[1] = yDiag;
                moveCoords[2] = xDiagL;
                moveCoords[0] = 1;
                return moveCoords;
            }

            //check if piece can capture a piece to the left
            if (board[yDoubDiag][xDoubDiagL] == -1 && board[yDiag][xDiagL] == 1) {
                moveCoords[1] = yDoubDiag;
                moveCoords[2] = xDoubDiagL;
                moveCoords[0] = 1;
                return moveCoords;
            }
        }

        return moveCoords;
    }


    /**
     * Method called by game logic to invoke computer player to move a piece
     *
     * @return decoded array of computer move for CheckersLogic to validate.
     */
    public int[] movePiece() throws NoSuchElementException {
        int[] decoded = {0, -1, -1, -1, -1};

        if ((lastY >= 0 && lastY <= 7)
                && (lastX >= 0 && lastX <= 7)
                && (board[lastY][lastX] == 0)
                && (this.findValidMove(lastY, lastX)[0] == 1)) {
            decoded[1] = lastY;
            decoded[2] = lastX;
        } else {
            int[] findPiece = findPiece();

            if (findPiece[0] == 0)
                throw new NoSuchElementException("No valid moves were found");
            else {
                decoded[1] = findPiece[1];
                decoded[2] = findPiece[2];
            }
        }
        int[] moveCoords = findValidMove(decoded[1], decoded[2]);
        if (moveCoords[0] == 0)
            throw new NoSuchElementException("No valid moves were found");
        else {
            decoded[3] = moveCoords[1];
            decoded[4] = moveCoords[2];
        }

        decoded[0] = 1;
        return decoded;

    }

    /**
     * updates the computers game board to the given input
     *
     * @param updatedBoard the game board to update this classes version of the game board to
     */
    public void updateBoard(int[][] updatedBoard) {
        board = updatedBoard;
    }
}


