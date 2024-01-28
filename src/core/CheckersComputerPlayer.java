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
     * @param boardRef A reference to game board of the game computer player is playing with user
     */
    CheckersComputerPlayer(int[][] boardRef) {
        board = boardRef;
        lastX = -1;
        lastY = -1;

    }

    /**
     * sets the value of the last used x coordinate
     * @param x the x value of the last used piece
     */
    public void setLastX(int x) {
        lastX = x;
    }

    /**
     * sets the value of the last used y coordinate
     * @param y the y value of the last used piece
     */
    public void setLastY(int y) {
        lastY = y;
    }

    /**
     * finds a computer player piece that has a valid move
     * @return int array containing success value, x coord, and y coord.
     *         If piece was found, array[0] is 1, else array[0] is 0.
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
                    if (hasValidMove(i, j)) {
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
     * @param row row number of given piece.
     * @param column column number of given piece.
     * @return int[] containing success value, y coord, and x coord.
     *               If valid move was found, array[0] is 1, else array[0] is 0.
     */
    public int[] findValidMove(int row, int column) {
        int[] moveCoords = {0, 0, 0};

        if (row == 7) return moveCoords;
        else if (column == 0) {
            if ((board[row + 2][column + 2] == -1) && (board[row + 1][column + 1] == 1)) {
                moveCoords[1] = row + 2;
                moveCoords[2] = column + 2;
                moveCoords[0] = 1;
            } else if (board[row + 1][column + 1] == -1) {
                moveCoords[1] = row + 1;
                moveCoords[2] = column + 1;
                moveCoords[0] = 1;
            }
        } else if (column == 7) {
            if ((board[row + 2][column - 2] == -1) && (board[row + 1][column - 1] == 1)) {
                moveCoords[1] = row + 2;
                moveCoords[2] = column - 2;
                moveCoords[0] = 1;
            } else if (board[row + 1][column - 1] == -1) {
                moveCoords[1] = row + 1;
                moveCoords[2] = column - 1;
                moveCoords[0] = 1;
            }
        } else {
            if (column > 1) {
                if ((board[row + 2][column - 2] == -1) && (board[row + 1][column - 1] == 1)) {
                    moveCoords[1] = row + 2;
                    moveCoords[2] = column - 2;
                    moveCoords[0] = 1;
                } else if ((board[row + 1][column - 1] == -1)) {
                    moveCoords[1] = row + 1;
                    moveCoords[2] = column - 1;
                    moveCoords[0] = 1;
                } else if ((board[row + 1][column + 1] == -1)) {
                    moveCoords[1] = row + 1;
                    moveCoords[2] = column + 1;
                    moveCoords[0] = 1;
                }
            }
            if (column < 6) {
                if ((board[row + 2][column + 2] == -1) && (board[row + 1][column + 1] == 1)) {
                    moveCoords[1] = row + 2;
                    moveCoords[2] = column + 2;
                    moveCoords[0] = 1;
                } else if ((board[row + 1][column - 1] == -1)) {
                    moveCoords[1] = row + 1;
                    moveCoords[2] = column - 1;
                    moveCoords[0] = 1;
                } else if ((board[row + 1][column + 1] == -1)) {
                    moveCoords[1] = row + 1;
                    moveCoords[2] = column + 1;
                    moveCoords[0] = 1;
                }
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
                && (this.hasValidMove(lastY, lastX))) {
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
     * Given a position of a piece, returns if the piece has a valid move
     *
     * @param row row position of piece
     * @param column column position of piece
     * @return true if piece can move, false if piece is blocked
     */
    public boolean hasValidMove(int row, int column) {
        //check if one or both possible moves are occupied or don't exist
            if (row == 7) return false;
            else {
                if (column == 0) {
                    return (board[row + 1][column + 1] == -1) || ((board[row+2][column+2] == -1) && (board[row+1][column+1] == 1));
                } else if (column == 7) {
                    return (board[row + 1][column - 1] == -1) || ((board[row+2][column-2] == -1) && (board[row+1][column-1] == 1));
                } else {
                    if (column > 1)
                        return ((board[row + 1][column - 1] == -1) || ((board[row+2][column-2] == -1) && (board[row+1][column-1] == 1))
                            || (board[row + 1][column + 1] == -1));
                    if (column < 6) {
                        return ((board[row + 1][column - 1] == -1) || (board[row + 1][column + 1] == -1) ||
                                ((board[row+2][column+2] == -1) && (board[row+1][column+1] == 1)));
                    }
                }
            }
            return false;
    }

}
