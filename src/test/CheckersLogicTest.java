package test;

import core.CheckersLogic;
import org.junit.*;

import static org.junit.Assert.*;

public class CheckersLogicTest {

    private static CheckersLogic globalGame;
    private CheckersLogic playerGame;
    private CheckersLogic computerGame;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        globalGame = new CheckersLogic("player");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        globalGame = null;
    }
    @Before
    public void setUp() throws Exception {
        playerGame = new CheckersLogic("player");
        computerGame = new CheckersLogic("computer");
    }

    @After
    public void tearDown() throws Exception {
        playerGame = null;
        computerGame = null;
    }

    @Test
    public void movePiece() {
        final int VALID = 1;
        final int INVALID = 0;

        // test playerGame at start of game
        assertEquals("Invalid move was allowed", playerGame.movePiece(""), INVALID);
        assertEquals("Invalid move was allowed", playerGame.movePiece("aa-bb"), INVALID);
        assertEquals("Invalid move was allowed", playerGame.movePiece("invalidmove"), INVALID);
        assertEquals("Invalid move was allowed", playerGame.movePiece("3a-6g"), INVALID);
        assertEquals("Invalid move was allowed", playerGame.movePiece("3a-4b-6f"), INVALID);
        assertEquals("Invalid double jump was allowed", playerGame.movePiece("3a-5c-7e"), INVALID);
        assertEquals("Valid move not allowed", playerGame.movePiece("3a-4b"), VALID);

        // test computerGame at start of game
        assertEquals("Invalid move was allowed", computerGame.movePiece(""), INVALID);
        assertEquals("Invalid move was allowed", computerGame.movePiece("aa-bb"), INVALID);
        assertEquals("Invalid move was allowed", computerGame.movePiece("invalidmove"), INVALID);
        assertEquals("Invalid move was allowed", computerGame.movePiece("3a-6g"), INVALID);
        assertEquals("Invalid move was allowed", computerGame.movePiece("3a-4b-6f"), INVALID);
        assertEquals("Invalid double jump was allowed", computerGame.movePiece("3a-5c-7e"), INVALID);
        assertEquals("Valid move not allowed", computerGame.movePiece("3c-4d"), VALID);
        assertEquals("Computer move did not go as expected", computerGame.movePiece("cmptr"), VALID);

        //set board to manufacture a double jump to test
        int[][] newBoard = new int[][] {
                {-1, -1, -1, 0, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, 0, -1, -1, -1, -1, -1},
                {-1, -1, -1, 1, -1, -1, -1, -1},
                {-1, 0, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, 1, -1, -1},
                {-1, -1, -1, 0, -1, -1, -1, -1},
                {-1, -1, -1, -1, 1, -1, -1, -1}
        };
        playerGame.setBoard(newBoard);
        computerGame.setBoard(newBoard);

        assertEquals("valid double jump not allowed", playerGame.movePiece("6c-4e-2g"), VALID);
        assertEquals("valid double jump not allowed", computerGame.movePiece("1e-3c-5a"), VALID);
    }

    @Test
    public void checkValidMove() {
        final int INVALID = 0;
        final int VALID1 = 1;
        final int VALID2 = 2;
        assertEquals("Invalid move marked as valid",
                playerGame.checkValidMove(9, 4, 5, 6, false), INVALID);
        assertEquals("Invalid move marked as valid",
                playerGame.checkValidMove(4, 9, 5, 6, false), INVALID);
        assertEquals("Invalid move marked as valid",
                playerGame.checkValidMove(4, 4, 9, 6, false), INVALID);
        assertEquals("Invalid move marked as valid",
                playerGame.checkValidMove(4, 4, 5, 9, false), INVALID);

        assertEquals("player moving incorrect piece",
                playerGame.checkValidMove(2, 1, 3, 2, false), INVALID);
        assertEquals("invalid single jump",
                playerGame.checkValidMove(5, 2, 4, 3, true), INVALID);

        assertEquals("valid move marked invalid",
                playerGame.checkValidMove(5, 4, 4, 5, false), VALID1);

        //set board to manufacture a double jump to test
        int[][] newBoard = new int[][] {
                {-1, -1, -1, 0, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, 0, -1, -1, -1, -1, -1},
                {-1, -1, -1, 1, -1, -1, -1, -1},
                {-1, 0, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, 1, -1, -1},
                {-1, -1, -1, 0, -1, -1, -1, -1},
                {-1, -1, -1, -1, 1, -1, -1, -1}
        };
        playerGame.setBoard(newBoard);
        assertEquals("valid double jump",
                playerGame.checkValidMove(3, 3, 1, 1, false), VALID2);


    }

    @Test
    public void decodeMove() {
        final int INVALID = 0;
        final int VALID = 1;

        int[] decoded;

        decoded = playerGame.decodeMove("");
        assertEquals("Invalid move", decoded[0], INVALID);
        decoded = playerGame.decodeMove("aa-bb");
        assertEquals("Invalid move", decoded[0], INVALID);
        decoded = playerGame.decodeMove("invalidmove");
        assertEquals("Invalid move", decoded[0], INVALID);
        decoded = playerGame.decodeMove("3a-4b");
        assertEquals("Valid Move", decoded[0], VALID);
        // Check that coordinate output is correct
        assertEquals(decoded[1], 5);
        assertEquals(decoded[2], 0);
        assertEquals(decoded[3], 4);
        assertEquals(decoded[4], 1);

    }

    @Test
    public void determineMove() {
        final boolean VALID = true;
        final boolean INVALID = false;

        assertEquals("no valid move", globalGame.determineMove(0, 1), INVALID);
        assertEquals("no valid move", globalGame.determineMove(0, 7), INVALID);
        assertEquals("no valid move", globalGame.determineMove(1, 0), INVALID);
        assertEquals("no valid move", globalGame.determineMove(1, 4), INVALID);
        assertEquals("no valid move", globalGame.determineMove(7, 0), INVALID);
        assertEquals("no valid move", globalGame.determineMove(6, 7), INVALID);
        assertEquals("no valid move", globalGame.determineMove(7, 4), INVALID);
        assertEquals("no valid move", globalGame.determineMove(6, 1), INVALID);
        assertEquals("has valid move", globalGame.determineMove(5, 2), VALID);
        assertEquals("has valid move", globalGame.determineMove(2, 1), VALID);

    }

    @Test
    public void determineWinner() {
        assertEquals("no winner determined", globalGame.determineWinner(), -1);

        // manufacture winner in player game to test
        int[][] newBoard = new int[][] {
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, 1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, 1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, 0, -1, -1, -1}
        };
        playerGame.setBoard(newBoard);
        assertEquals("winner determined", playerGame.determineWinner(), 1);

    }

    @Test
    public void getBoard() {
        int[][] newBoard = new int[][]{
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {0, -1, 0, -1, 0, -1, 0, -1},
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {1, -1, 1, -1, 1, -1, 1, -1},
                {-1, 1, -1, 1, -1, 1, -1, 1},
                {1, -1, 1, -1, 1, -1, 1, -1}};

        int[][] playerBoard = playerGame.getBoard();
        int[][] computerBoard = computerGame.getBoard();

        assertEquals(playerBoard, newBoard);
        assertEquals(computerBoard, newBoard);


    }

    @Test
    public void getTurn() {
        assertEquals(playerGame.getTurn(), 1);
        playerGame.movePiece("3a-4b");
        assertEquals(playerGame.getTurn(), 0);
    }

    @Test
    public void getMode() {
        assertFalse(playerGame.getMode());
        assertTrue(computerGame.getMode());
    }
}