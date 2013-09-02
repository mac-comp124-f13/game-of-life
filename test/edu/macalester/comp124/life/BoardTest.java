package edu.macalester.comp124.life;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for the GameBoard class.
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 * These tests test a number of basic operations on the GameBoard class.  They
 * are not exhaustive, but exercise it a fair amount. 
 */
public class BoardTest {

    private GameBoard board;
    
    /**
     * Set up the tests with a fresh 10x10 game board for each test.
     */
    @Before
    public void setUp() throws Exception {
        board = new GameBoard(10,10);
    }
    
    /**
     * Test dimension queries.
     */
    @Test
    public void testDimensions() {
        assertEquals(10, board.getWidth());
        assertEquals(10, board.getHeight());
        
        GameBoard b2 = new GameBoard(15, 397);
        assertEquals(15, b2.getWidth());
        assertEquals(397, b2.getHeight());
    }
    
    /**
     * Test some basic setCell/getCell operations
     */
    @Test
    public void testGetSetCell() {
        // the board is empty - a cell should be false
        assertFalse(board.getCell(0, 0));
        assertFalse(board.getCell(3, 9));
        
        // the board is 10x10 - we should have out-of-bounds for larger values
        try {
            board.getCell(3, 10);
            // if we get here, then there was no error
            fail("Board index 10 not out of bounds");
        } catch (ArrayIndexOutOfBoundsException e) {
            // do nothing - this is expected
        }
        
        // try setting a cell and getting it
        board.setCell(1, 3, true);
        assertTrue(board.getCell(1, 3));
        assertFalse(board.getCell(3, 1));
    }
    
    /**
     * Test that an empty board reports empty neighborhoods.
     *
     */
    @Test
    public void testCountNeighborsEmpty() {
        int neighbors = board.countLivingNeighbors(1, 1);
        assertEquals(0, neighbors);
    }
    
    /**
     * Test neighborhoods for some simple cases.
     */
    @Test
    public void testCountNeighborsSimple() {
        board.setCell(1, 1, true);
        // 1,1 is the only living cell - it should have no living neighbors
        assertEquals(0, board.countLivingNeighbors(1, 1));
        // 1,1 is a neighbor to 0,0, so 0,0 should have 1 neighbor
        assertEquals(1, board.countLivingNeighbors(0, 0));
    }

    /**
     * Test neighborhoods for a glider.  A glider is a simple pattern, a
     * "spaceship", which moves.  It looks like this:
     * 
     * <pre>
     *  X
     *   X
     * XXX
     * </pre>
     */
    @Test
    public void testCountNeighborsGlider() {
        // create a glider
        board.setCell(1, 0, true);
        board.setCell(2, 1, true);
        board.setCell(0, 2, true);
        board.setCell(1, 2, true);
        board.setCell(2, 2, true);
        
        // the center of the glider should have 5 neighbors
        assertEquals(5, board.countLivingNeighbors(1, 1));
        
        // 0,0, the upper left of the glider, should have 1 neighbor
        assertEquals(1, board.countLivingNeighbors(0, 0));
        
        // 2,2, the lower right corner, should have 2 neighbors
        assertEquals(2, board.countLivingNeighbors(2, 2));
    }
}

