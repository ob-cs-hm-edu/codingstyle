package edu.hm.hafner.java2.sokoban;

/**
 * Converts an array of strings to a new Sokoban level.
 *
 * @author Ullrich Hafner
 */
public interface StringLevelConverter {
    /**
     * Converts the array of strings to a Sokoban level.
     *
     * @param lines the rows of the level
     * @return the created level
     */
    SokobanGame convert(String[] lines);
}
