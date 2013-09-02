package edu.macalester.comp124.life;

import java.io.*;

/**
 * Class implementing the Life gameboard.
 * 
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 * This class uses a RuleSet to implement a board for the game of edu.macalester.comp124.life.  It
 * implements a standard non-wrapping Life board, with the edges of the board
 * bordered by dead cells.
 */
public class GameBoard {
    
    /**
     * x,y offsets of the various neighbors of a cell.
     */
    private static int[][] neighborOffsets = {
        {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1},  {-1, 1}, {-1, 0}, {-1, -1}
    };
    
    /** The board's width */
    private int boardWidth;
    /** The board's height */
    private int boardHeight;
    /** The current game board */
    private boolean[][] board;
    /** The rule set to use */
    private RuleSet ruleSet;
    
    /**
     * Constructs a game board for play.
     * @param rules The ruleset to use
     * @param width The board width
     * @param height The board height
     */
    public GameBoard(RuleSet rules, int width, int height) {
        boardWidth = width;
        boardHeight = height;
        board = new boolean[width][height];
        // clear the board to false
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = false;
            }
        }
        ruleSet = rules;
    }
    
    /**
     * Create a new game board with the default (Conway) rules.
     * @param width
     * @param height
     */
    public GameBoard(int width, int height) {
        this(new Conway(), width, height);
    }
    
    /**
     * Create a 100x100 board with Conway rules.
     *
     */
    public GameBoard() {
        this(new Conway());
    }
    
    /**
     * Create a 100x100 board with the specified rules.
     * @param rules
     */
    public GameBoard(RuleSet rules) {
        this(rules, 100, 100);
    }
    
    /**
     * Loads a game board from a file.
     * @param file The file containing game board data.
     * @throws java.io.IOException If an error occurs.
     */
    public GameBoard(RuleSet rules, File file) throws IOException {
        ruleSet = rules;

        FileInputStream s = new FileInputStream(file);
        DataInputStream data = new DataInputStream(s);

        try {
            boardWidth = data.readInt();
            boardHeight = data.readInt();

            board = new boolean[boardWidth][boardHeight];

            for (int y = 0; y < boardWidth; y++) {
                for (int x = 0; x < boardHeight; x++) {
                    setCell(x, y, data.readBoolean());
                }
            }
        } finally {
            data.close();
        }
    }

    /**
     * Save the game board to a file
     * @param file The file to which to save the game board
     * @throws java.io.IOException If an error occurs
     */
    public void save(File file) throws IOException {
        DataOutputStream data = new DataOutputStream(new FileOutputStream(file));
        try {
            data.writeInt(boardWidth);
            data.writeInt(boardHeight);
            
            for (int y = 0; y < boardHeight; y++) {
                for (int x = 0; x < boardWidth; x++) {
                    data.writeBoolean(getCell(x, y));
                }
            }
        } finally {
            data.close();
        }
    }
    
    /**
     * Queries the currently-active rule set
     * @return The rule set currently in use
     */
    public RuleSet getRuleSet() {
        return ruleSet;
    }
    
    /**
     * Sets the rule set to use
     * @param rules
     */
    public void setRuleSet(RuleSet rules) {
        ruleSet = rules;
    }
    
    /**
     * Queries the board's width.
     * @return The width of the game board
     */
    public int getWidth() {
        return boardWidth;
    }
    
    /**
     * Queries the board's height.
     * @return The height of the game board
     */
    public int getHeight() {
        return boardHeight;
    }
    
    /**
     * Retrieve the value of a particular cell.
     * @param x The X-coordinate of the cell.
     * @param y The Y-coordinate of the cell.
     * @return The cell's value
     */
    public boolean getCell(int x, int y) {
        return board[x][y];
    }
    
    /**
     * Set the value of a particular cell.
     * @param x The X-coordinate of the cell.
     * @param y The Y-coordinate of the cell.
     * @param state The new state of the cell.
     */
    public void setCell(int x, int y, boolean state) {
        board[x][y] = state;
    }
    
    /**
     * Computes the next state of the game board.
     */
    public void next() {
        // Allocate a new board to hold the next state
        boolean[][] nextBoard = new boolean[boardWidth][boardHeight];
        
        // Ask the ruleset to compute the next board based on the neighborhood
        // of each cell in the current board.
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                int neighbors = countLivingNeighbors(x, y);
                nextBoard[x][y] = ruleSet.applyRules(getCell(x, y), neighbors);
            }
        }
        
        // Replace the current board with the new board
        board = nextBoard;
    }
    
    /**
     * Counts the living neighbors of a cell.  The edge of the board is
     * considered to be dead.
     * @param x The X-coordinate of the cell of interest.
     * @param y The Y-coordinate of the cell of interest.
     * @return The number of living neighbors of the cell (x,y).
     */
    int countLivingNeighbors(int x, int y) {
        int n = 0;
        
        // Loop over the neighborhood, counting neighbors.
        for (int i = 0; i < 8; i++) {
            int nx = x + neighborOffsets[i][0];
            int ny = y + neighborOffsets[i][1];
            try {
                if (board[nx][ny])
                    n++;
            } catch (ArrayIndexOutOfBoundsException e) {
                // This block will be reached if (nx,ny) is not a valid
                // coordinate pair in the board.  In this case, do nothing.
            }
        }
        
        return n;
    }
}
