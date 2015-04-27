package edu.hm.hafner.java2.sokoban;

/**
 * Represents the game field of Sokoban.
 *
 * @author Ullrich Hafner
 */
public class Sokoban implements SokobanGame {
    private Field[][] fields;

    private int width;
    private int height;

    private final PointSet treasures = new PointSet();

    private Point player;
    private int targetCount;

    /**
     * Sets the level. The level consists of an array of lines. Each line is represented by an array of fields.
     *
     * @param level the level
     */
    public void setLevel(final Field[][] level) {
        width = level[0].length;
        height = level.length;
        targetCount = 0;

        fields = new Field[height][width];
        for (int y = 0; y < height; y++) {
            Field[] line = level[y];
            if (line.length != width) {
                throw new IllegalArgumentException(
                        String.format("Line %d has not the width %d of previous line.", y, width));
            }
            for (int x = 0; x < width; x++) {
                Field field = level[y][x];
                if (field == null) {
                    throw new NullPointerException("Field is null at " + new Point(x, y));
                }
                if (field == Field.TARGET) {
                    targetCount++;
                }
                fields[y][x] = field;
            }
        }
    }

    /**
     * Sets the position of the player to the specified coordinates.
     *
     * @param point the new position
     */
    public void setPlayer(final Point point) {
        if (point == null) {
            throw new NullPointerException("Player must not be null.");
        }

        player = point;
    }

    /**
     * Adds a treasure at the specified coordinates. If there is already a treasure at that position, nothing is
     * done.
     *
     * @param point the position of the treasure
     */
    public void addTreasure(final Point point) {
        if (point == null) {
            throw new NullPointerException("Treasure must not be null.");
        }

        treasures.add(point);
    }

    /**
     * Removes a treasure from the specified coordinates. If there is no treasure at that position, nothing is
     * done.
     *
     * @param point the position of the treasure
     */
    public void removeTreasure(final Point point) {
        if (point == null) {
            throw new NullPointerException("Treasure must not be null.");
        }

        treasures.remove(point);
    }

    /**
     * Validates this level.
     *
     * @throws IllegalArgumentException if the level is not valid
     */
    public void validate() {
        ensureThatPlayerIsSet();
        ensureThatNoWallBelowPlayer();
        ensureThatNoTreasureBelowPlayer();
        ensureThatNoWallBelowTreasures();
        ensureThatTreasuresAndTargetsMatch();
    }

    private void ensureThatNoTreasureBelowPlayer() {
        if (treasures.contains(player)) {
            throw new IllegalArgumentException("Player is on treasure: " + player);
        }
    }

    private void ensureThatTreasuresAndTargetsMatch() {
        if (targetCount != treasures.size()) {
            throw new IllegalArgumentException(
                    String.format("#Treasures (%d) !=  #Targets (%d)", treasures.size(), targetCount));
        }
    }

    private void ensureThatPlayerIsSet() {
        if (player == null) {
            throw new IllegalArgumentException("Player is not set!");
        }
        assertThatPointIsInField("Player", player);
    }

    private void assertThatPointIsInField(final String name, final Point point) {
        if (point.getX() < 0 || point.getX() >= width
                || point.getY() < 0 || point.getY() >= height) {
            throw new IllegalArgumentException(
                    String.format("%s %d is not set on field of size %dx%d: ", name, player, width, height));
        }
    }

    private void ensureThatNoWallBelowPlayer() {
        if (isWallAt(player)) {
            throw new IllegalArgumentException("Player is on wall: " + player);
        }
    }

    private void ensureThatNoWallBelowTreasures() {
        for (int i = 0; i < treasures.size(); i++) {
            Point treasure = treasures.get(i);
            if (isWallAt(treasure)) {
                throw new IllegalArgumentException("Treasure is on wall: " + treasure);
            }
        }
    }

    private boolean isWallAt(final Point position) {
        return fields[position.getY()][position.getX()] == Field.WALL;
    }

    /**
     * Returns whether this level has been solved. The level is solved, if each treasure covers a target.
     *
     * @return {@code true} if this level has been solved, {@code false} otherwise
     */
    public boolean isSolved() {
        for (int i = 0; i < treasures.size(); i++) {
            Point treasure = treasures.get(i);
            if (fields[treasure.getY()][treasure.getX()] != Field.TARGET) {
                return false;
            }
        }
        return true;
    }

    public Field getField(final Point point) {
        return fields[point.getY()][point.getX()];
    }

    public Point getPlayer() {
        return player;
    }

    public PointSet getTreasures() {
        return new PointSet(treasures);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void moveLeft() {
        // FIXME: implement method
    }

    @Override
    public void moveRight() {
        // FIXME: implement method
    }

    @Override
    public void moveUp() {
        // FIXME: implement method
    }

    @Override
    public void moveDown() {
        // FIXME: implement method
    }
}