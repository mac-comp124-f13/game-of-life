package edu.macalester.comp124.life;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for the Conway ruleset
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 */
public class ConwayTest {

    private RuleSet rules;
    
    /**
     * Initial setup method for the test cases.
     * 
     * If a JUnit test class has a method called setUp, then JUnit calls that
     * method before each of the test methods. It allows you to specify initial
     * actions to be taken for every test. In this case, we start each test with
     * a clean instance of the Conway ruleset.
     */
    @Before
    public void setUp() throws Exception {
        // create a new rule set
        rules = new Conway();
    }
    
    /**
     * Test that an all-dead neighborhood results in the cell staying dead.
     *
     */
    @Test
    public void testNoOp() {
        boolean result = rules.applyRules(false, 0);
        assertFalse(result);
    }
    
    /**
     * Test that 3 neighbors bring a cell to edu.macalester.comp124.life.
     *
     */
    @Test
    public void testComeToLife() {
        assertTrue(rules.applyRules(false, 3));
    }
    
    /**
     * Test that a dead cell with various numbers of neighbors other than 3
     * stays dead.
     */
    @Test
    public void testStayDead() {
        assertFalse(rules.applyRules(false, 2));
        assertFalse(rules.applyRules(false, 4));
        assertFalse(rules.applyRules(false, 6));
        assertFalse(rules.applyRules(false, 8));
    }
    
    /**
     * Test that 2 or 3 neighbors keep a cell alive.
     *
     */
    @Test
    public void testStayAlive() {
        assertTrue(rules.applyRules(true, 2));
        assertTrue(rules.applyRules(true, 3));
    }
    
    /**
     * Test that a cell with 0 or 1 neighbors dies.
     *
     */
    @Test
    public void testLonely() {
        assertFalse(rules.applyRules(true, 0));
        assertFalse(rules.applyRules(true, 1));
    }
    
    /**
     * Test that a cell with 4 or more neighbors dies.
     * 
     * We use representative samples of the domain of applyRules. In this simple
     * case, it would be feasible to exhaustively test every possible case for
     * applyRules, but in most programs exhaustive testing is not possible given
     * the limits of computer speed.
     * 
     */
    @Test
    public void testOvercrowd() {
        assertFalse(rules.applyRules(true, 4));
        assertFalse(rules.applyRules(true, 7));
    }
}
