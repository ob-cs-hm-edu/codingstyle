package edu.hm.hafner.java2.sokoban;

/**
 * Defines all operations required to play Sokoban.
 *
 * @author Ullrich Hafner
 */
public interface SokobanGame {
    /**
     * Moves the player to the left. If this is not possible, then nothing is done.
     */
    void moveLeft();

    /**
     * Moves the player to the right. If this is not possible, then nothing is done.
     */
    void moveRight();

    /**
     * Moves the player up. If this is not possible, then nothing is done.
     */
    void moveUp();

    /**
     * Moves the player down. If this is not possible, then nothing is done.
     */
    void moveDown();

    /**
     * Returns the field at the specified position.
     *
     * @param point the position
     * @return the field at the specified position
     */
    Field getField(Point point);

    /**
     * Returns the player position.
     *
     * @return the player position.
     */
    Point getPlayer();

    /**
     * Returns the treasure positions.
     *
     * @return the treasure positions.
     */
    PointSet getTreasures();

    /**
     * Returns the width of the level.
     *
     * @return the width of the level.
     */
    int getWidth();

    /**
     * Returns the height of the level.
     *
     * @return the height of the level.
     */
    int getHeight();
}
