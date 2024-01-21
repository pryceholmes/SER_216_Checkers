package core;


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
    /** var to keep track of WIN/LOSS state,
     * int -1 = no winner determined
     * int 0 = O winner determined
     * int 1 = X winner determined
     */
    private int winner;

    /** var to determine who's turn it is,
     * int 0 = O turn
     * int 1 = X turn
     */
    private int turn;

    /** default constructor, initializes to a new game board, no winner determined, X's turn */
    public CheckersLogic() {
        board = new int[][]{{-1, 0, -1, 0, -1, 0, -1, 0},
                            {0, -1, 0, -1, 0, -1, 0, -1},
                            {-1, 0, -1, 0, -1, 0, -1, 0},
                            {-1, -1, -1, -1, -1, -1, -1, -1},
                            {-1, -1, -1, -1, -1, -1, -1, -1},
                            {1, -1, 1, -1, 1, -1, 1, -1},
                            {-1, 1, -1, 1, -1, 1, -1, 1},
                            {1, -1, 1, -1, 1, -1, 1, -1}};
        winner = -1;
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
    public int movePiece(int input) {
        return 0;

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
     */
    public void determineWinner() {

    }





}
