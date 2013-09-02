package edu.macalester.comp124.life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * JComponent to display and manipulate the edu.macalester.comp124.life grid.
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 * This class handles display and manipulation of the Life grid on the screen.
 */
@SuppressWarnings("serial")
public class LifeComponent extends JComponent {
        
    private static final int CELL_SIZE = 5;
    private static final int GUTTER_SIZE = 1;
    
    private GameBoard board;
    private Set<Point> foundCells = new HashSet<Point>();
    
    /**
     * Create a new Life display panel with a board.
     * @param initBoard The intial board to display
     */
    public LifeComponent(GameBoard initBoard) {
        setBoard(initBoard);
        
        // set up event handling
        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                foundCells.clear();
            }
            public void mouseClicked(MouseEvent e) {
                toggleCellAtPoint(e.getPoint());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                toggleCellAtPoint(e.getPoint());
            }
        });
    }
    
    /**
     * Create a new Life display panel with no board
     */
    public LifeComponent() {
        // call other constructor with null param
        this(null);
    }
    
    /**
     * Retrieve the currently displayed game board.
     * @return The game board
     */
    public GameBoard getBoard() {
        return board;
    }
    
    /**
     * Set a new game board to display.
     * @param newBoard The new board to display
     */
    public void setBoard(GameBoard newBoard) {
        board = newBoard;
        Dimension dim = new Dimension();
        
        if (board != null) {
            dim.width = cellDistance(board.getWidth());
            dim.height = cellDistance(board.getHeight());
        } else {
            dim.width = 400;
            dim.height = 400;
        }
        
        setMinimumSize(dim);
        setPreferredSize(dim);
        
        repaint();
    }
    
    /**
     * Paint the edu.macalester.comp124.life board on the screen.
     */
    public void paintComponent(Graphics g) {
        if (board == null)
            return;     // refuse to paint a null board
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.WHITE);
        int bw = cellDistance(board.getWidth());
        int bh = cellDistance(board.getHeight());
        for (int x = 0; x < bw; x += CELL_SIZE + GUTTER_SIZE) {
            g2.fillRect(x, 0, GUTTER_SIZE, bh);
        }
        for (int y = 0; y < bh; y += CELL_SIZE + GUTTER_SIZE) {
            g2.fillRect(0, y, bw, GUTTER_SIZE);
        }
        
        g2.setPaint(Color.BLACK);
        
        for (int x = 0; x < board.getWidth(); x++) {
            int xpos = cellDistance(x);
            
            for (int y = 0; y < board.getHeight(); y++) {
                int ypos = cellDistance(y);
                if (board.getCell(x, y)) {
                    g2.fillRect(xpos, ypos, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
    
    /**
     * Toggle the cell at a particular on-screen point.
     * @param pt The point at which the cell is to be toggled.
     */
    public void toggleCellAtPoint(Point pt) {
        int cx = cellFromDistance(pt.x);
        int cy = cellFromDistance(pt.y);
        if (cx >= 0 && cy >= 0) {
            Point cpt = new Point(cx, cy);
            if (!foundCells.contains(cpt)) {
                foundCells.add(cpt);
                try {
                    board.setCell(cx, cy, !board.getCell(cx, cy));
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                repaint();
            }
        }
    }
    
    /**
     * Computes the distance in screen coordinates of a cell position index.
     * @param cell The cell index to translate to a distance
     * @return The pixel distance represented by the cell index.
     */
    int cellDistance(int cell) {
        int d = GUTTER_SIZE;
        d += cell * (CELL_SIZE + GUTTER_SIZE);
        return d;
    }
    
    /**
     * The inverse of cellDistance - computes the cell at a given distance.
     * @param d The distance of interest
     * @return The cell, or -1 if the mouse is over a gutter
     */
    int cellFromDistance(int d) {
        int d2 = d - GUTTER_SIZE;
        if (d2 < 0) {
            return -1;
        } else {
            d2 /= (CELL_SIZE + GUTTER_SIZE);
            int next = cellDistance(d2 + 1);
            if (next - d <= GUTTER_SIZE)
                return -1;
            else
                return d2;
        }
    }

}
