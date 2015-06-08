package edu.hm.hafner.java2.sokoban;

import org.junit.Test;

import static edu.hm.hafner.java2.sokoban.Field.*;
import static edu.hm.hafner.java2.sokoban.SokobanAssertions.*;

/**
 * Tests the class {@link UndoableSokoban}.
 *
 * @author Ullrich Hafner
 */
public class UndoableSokobanTest extends SokobanTest {
    private static final Point MIDDLE = new Point(3, 3);
    private static final Point LEFT = new Point(2, 3);
    private static final Point RIGHT = new Point(4, 3);
    private static final Point UP = new Point(3, 2);
    private static final Point DOWN = new Point(3, 4);

    @Override
    protected UndoableSokoban createSokoban() {
        return new UndoableSokoban();
    }

    /**
     * Verifies that we can reset to the initial state. The player moves into each direction, moving all treasures
     * to the neighbor field.
     */
    @Test
    public void shouldResetAllMovements() {
        UndoableSokoban sokoban = createLevel();

        sokoban.moveLeft();
        sokoban.moveRight();

        sokoban.moveRight();
        sokoban.moveLeft();

        sokoban.moveUp();
        sokoban.moveDown();

        sokoban.moveDown();

        assertThatPlayerIsAt(sokoban, MIDDLE.moveDown());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP.moveUp(), DOWN.moveDown(), RIGHT.moveRight());

        sokoban.restart();
        assertThatLevelIsInInitialState(sokoban);
    }

    /**
     * Verifies that the first movement is undoable.
     */
    @Test
    public void shouldUndoFirstMovement() {
        UndoableSokoban sokoban = createLevel();

        moveLeft(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);

        moveRight(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);

        moveDown(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);

        moveUp(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);
    }

    /**
     * Verifies that the second movement is undoable.
     */
    @Test
    public void shouldUndoSecondMovement() {
        UndoableSokoban sokoban = createLevel();

        sokoban.moveLeft();
        sokoban.moveRight();
        sokoban.undo();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveLeft());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP, DOWN, RIGHT);
        sokoban.moveRight();

        sokoban.moveRight();
        sokoban.moveLeft();
        sokoban.undo();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveRight());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP, DOWN, RIGHT.moveRight());
        sokoban.moveLeft();

        sokoban.moveDown();
        sokoban.moveUp();
        sokoban.undo();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveDown());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP, DOWN.moveDown(), RIGHT.moveRight());
        sokoban.moveUp();

        sokoban.moveUp();
        sokoban.moveDown();
        sokoban.undo();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveUp());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP.moveUp(), DOWN.moveDown(), RIGHT.moveRight());
    }

    /**
     * Verifies that undo does nothing in the beginning.
     */
    @Test
    public void shouldSkipUndoIfInInitialState() {
        UndoableSokoban sokoban = createLevel();

        assertThatUndoRestoresToInitialState(sokoban);
    }

    /**
     * Verifies that the undo state is not changed if the player actually did not move because of an obstacle.
     */
    @Test
    public void shouldSkipMovementIfNothingChanged() {
        UndoableSokoban sokoban = createLevel();

        moveLeft(sokoban);
        moveLeft(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);

        moveRight(sokoban);
        moveRight(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);

        moveDown(sokoban);
        moveDown(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);

        moveUp(sokoban);
        moveUp(sokoban);
        assertThatUndoRestoresToInitialState(sokoban);
    }

    /**
     * Verifies that undo itself is undoable.
     */
    @Test
    public void shouldUndoUndo() {
        UndoableSokoban sokoban = createLevel();

        sokoban.moveLeft();
        assertThatUndoRestoresToInitialState(sokoban);
        sokoban.undo();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveLeft());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP, DOWN, RIGHT);
        assertThatUndoRestoresToInitialState(sokoban);
    }

    private void moveUp(final SokobanGame sokoban) {
        sokoban.moveUp();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveUp());
        assertThatTreasuresAreAt(sokoban, LEFT, UP.moveUp(), DOWN, RIGHT);
    }

    private void moveDown(final SokobanGame sokoban) {
        sokoban.moveDown();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveDown());
        assertThatTreasuresAreAt(sokoban, LEFT, UP, DOWN.moveDown(), RIGHT);
    }

    private void moveRight(final SokobanGame sokoban) {
        sokoban.moveRight();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveRight());
        assertThatTreasuresAreAt(sokoban, LEFT, UP, DOWN, RIGHT.moveRight());
    }

    private void moveLeft(final SokobanGame sokoban) {
        sokoban.moveLeft();
        assertThatPlayerIsAt(sokoban, MIDDLE.moveLeft());
        assertThatTreasuresAreAt(sokoban, LEFT.moveLeft(), UP, DOWN, RIGHT);
    }

    private void assertThatUndoRestoresToInitialState(final UndoableSokobanGame sokoban) {
        sokoban.undo();
        assertThatLevelIsInInitialState(sokoban);
    }

    private void assertThatLevelIsInInitialState(final SokobanGame sokoban) {
        assertThatPlayerIsAt(sokoban, MIDDLE);
        assertThatTreasuresAreAt(sokoban, LEFT, UP, DOWN, RIGHT);
    }

    private UndoableSokoban createLevel() {
        Field[][] fields = {
                {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
                {WALL, FLOOR, FLOOR, TARGET, FLOOR, FLOOR, WALL},
                {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
                {WALL, TARGET, FLOOR, FLOOR, FLOOR, TARGET, WALL},
                {WALL, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
                {WALL, FLOOR, FLOOR, TARGET, FLOOR, FLOOR, WALL},
                {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
        };
        UndoableSokoban sokoban = createSokoban();
        sokoban.setLevel(fields);
        sokoban.setPlayer(MIDDLE);
        sokoban.addTreasure(LEFT);
        sokoban.addTreasure(RIGHT);
        sokoban.addTreasure(UP);
        sokoban.addTreasure(DOWN);
        sokoban.validate();
        return sokoban;
    }
}