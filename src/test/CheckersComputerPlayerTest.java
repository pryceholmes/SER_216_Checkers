package test;

import core.CheckersComputerPlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CheckersComputerPlayerTest {
    public int[][] board;
    public CheckersComputerPlayer comp;

    @Before
    public void setUp() throws Exception {
        board = new int[][]{
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {0, -1, 0, -1, 0, -1, 0, -1},
                {-1, 0, -1, 0, -1, 0, -1, 0},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {1, -1, 1, -1, 1, -1, 1, -1},
                {-1, 1, -1, 1, -1, 1, -1, 1},
                {1, -1, 1, -1, 1, -1, 1, -1}};
        comp = new CheckersComputerPlayer(board);
    }

    @After
    public void tearDown() throws Exception {
        comp = null;
    }

    @Test
    public void findPiece() {
        final int FOUND = 1;
        final int NOTFOUND = 0;

        int[] move = comp.findPiece();
        assertEquals("move available", move[0], FOUND);

        int[][] newBoard = new int[][]{
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, 0, -1, 0, -1, -1, -1, -1}
        };

        comp.updateBoard(newBoard);
        move = comp.findPiece();
        assertEquals("move unavailable", move[0], NOTFOUND);

        newBoard = new int[][]{
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, 1, -1, 1, -1, -1, -1, -1}
        };

        comp.updateBoard(newBoard);
        move = comp.findPiece();
        assertEquals("no pieces to move", move[0], NOTFOUND);
    }

    @Test
    public void findValidMove() {
        final int FOUND = 1;
        final int NOTFOUND = 0;
        int[] move;

        int [][] newBoard = new int[][]{
                {-1, 0, -1, -1, -1, -1, 0, -1},
                {0, -1, -1, 0, -1, -1, -1, 0},
                {-1, -1, -1, -1, -1, 0, -1, 0},
                {-1, -1, -1, 0, 0, 0, 1, -1},
                {-1, -1, 1, 1, 1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {0, 0, -1, -1, -1, -1, 0, 0},
                {0, -1, 0, -1, -1, -1, -1, -1}
        };
        comp.updateBoard(newBoard);
        // row 0, column 1
        move = comp.findValidMove(0 ,1);
        assertEquals("findMove r0, c1",
                move[0], FOUND);

        // row 0, column 7
        move = comp.findValidMove(0 , 7);
        assertEquals("findMove r0, c7",
               move[0], FOUND);

        // row 1, column 0
        move = comp.findValidMove(1, 0);
        assertEquals("findMove r1, c0",
                move[0], FOUND);

        // row 1, column 3
        move = comp.findValidMove(1, 3);
        assertEquals("findMove r1, c3",
                move[0], FOUND);

        // row 1, column 7
        move = comp.findValidMove(1, 7);
        assertEquals("findMove r1, c7",
                move[0], FOUND);

        // row 2, column 5
        move = comp.findValidMove(2, 5);
        assertEquals("findMove r2, c5",
                move[0], FOUND);

        // row 2, column 7
        move = comp.findValidMove(2, 7);
        assertEquals("findMove r2, c7",
                move[0], FOUND);

        // row 3, column 3
        move = comp.findValidMove(3, 3);
        assertEquals("findMove r3, c3",
                move[0], FOUND);

        // row 3, column 4
        move = comp.findValidMove(3, 4);
        assertEquals("findMove r3, c4",
                move[0], FOUND);

        // row 6, column 0
        move = comp.findValidMove(6, 0);
        assertEquals("findMove r6, c0",
                move[0], FOUND);

        // row 6, column 1
        move = comp.findValidMove(6, 1);
        assertEquals("findMove r6, c1",
                move[0], NOTFOUND);

        // row 6, column 6
        move = comp.findValidMove(6, 6);
        assertEquals("findMove r6, c6",
                move[0], FOUND);

        //  row 6, column 7
        move = comp.findValidMove(6, 7);
        assertEquals("findMove r6, c7",
                move[0], FOUND);

        // row 7, column 0
        move = comp.findValidMove(7, 0);
        assertEquals("findMove r7, c0",
                move[0], NOTFOUND);

        // row 7, column 2
        move = comp.findValidMove(7, 2);
        assertEquals("findMove r7, c2",
                move[0], NOTFOUND);
    }

    @Test
    public void movePiece() {
        int[] move = comp.movePiece();
        assertEquals(move[0], 1);

        comp.setLastY(2);
        comp.setLastX(1);
        move = comp.movePiece();
        assertEquals(move[0], 1);



        int [][] newBoard = new int[][]{
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, 0, -1, -1}
        };
        comp.updateBoard(newBoard);
        try {
            move = comp.movePiece();
        } catch (NoSuchElementException e) {
            assertEquals(e.getMessage(), "No valid moves were found");
        }
    }
}