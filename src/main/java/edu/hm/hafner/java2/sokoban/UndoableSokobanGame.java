package edu.hm.hafner.java2.sokoban;

/**
 * A Sokoban game that could be restarted. Additionally, the last step can be undone.
 *
 * @author Ullrich Hafner
 */
public interface UndoableSokobanGame extends SokobanGame {
    /**
     * Restarts the game. I.e., player and treasures are at the initial positions.
     */
    void restart();

    /**
     * Undoes the last step. Restores player and treasures.
     */
    void undo();
}
