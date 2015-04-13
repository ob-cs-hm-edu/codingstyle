package edu.hm.hafner.java2.sokoban;

/**
 * Represents the game field of Sokoban.
 *
 * @author FIXME: add your name
 */
public class Sokoban {
    /**
     * Sets the level. The level consists of an array of lines. Each line is represented by an array of fields.
     *
     * @param level the level
     */
    public void setLevel(final Field[][] level) {
        // FIXME: implement method
    }

    /**
     * Sets the position of the player to the specified coordinates.
     *
     * @param point the new position
     */
    public void setPlayer(final Point point) {
        // FIXME: implement method
    }

    /**
     * Adds a treasure at the specified coordinates. If there is already a treasure at that position, nothing is
     * done.
     *
     * @param point the position of the treasure
     */
    public void addTreasure(final Point point) {
        // FIXME: implement method
    }

    /**
     * Removes a treasure from the specified coordinates. If there is no treasure at that position, nothing is
     * done.
     *
     * @param point the position of the treasure
     */
    public void removeTreasure(final Point point) {
        // FIXME: implement method
    }

    /**
     * Validates this level.
     *
     * @throws IllegalArgumentException if the level is not valid
     */
    public void validate() {
        // FIXME: implement method
    }

    /**
     * Returns whether this level has been solved. The level is solved, if each treasure covers a target.
     *
     * @return {@code true} if this level has been solved, {@code false} otherwise
     */
    public boolean isSolved() {
        return true; // FIXME: implement method
    }

    /**
     * Returns the field at the specified position.
     *
     * @param point the position
     * @return the field at the specified position
     */
    public Field getField(final Point point) {
        return Field.FLOOR; // FIXME: implement method
    }

    /**
     * Returns the player position.
     *
     * @return the player position.
     */
    public Point getPlayer() {
        return new Point(0, 0); // FIXME: implement method
    }

    /**
     * Returns the treasure positions.
     *
     * @return the treasure positions.
     */
    public PointSet getTreasures() {
        return new PointSet(); // FIXME: implement method
    }

    /**
     * Returns the width of the level.
     *
     * @return the width of the level.
     */
    public int getWidth() {
        return 5; // FIXME: implement method

    }

    /**
     * Returns the height of the level.
     *
     * @return the height of the level.
     */
    public int getHeight() {
        return 5; // FIXME: implement method
    }
}