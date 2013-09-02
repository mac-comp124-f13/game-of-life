package edu.macalester.comp124.life;

/**
 * This interface defines the interface between the Life engine and the set of
 * rules describing the automata.
 * 
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 * 
 */
public interface RuleSet {
    
    /**
     * Obtain the name of a rule set.
     * @return The rule set's name
     */
    public String getName();
    
    /**
     * Apply the rules described by this rule set.
     * @param value The current value of the cell.
     * @param neighborCount The number of living neighbors.
     * @return The value of the cell in the next generation.
     */
    public boolean applyRules(boolean value, int neighborCount);
}