package edu.hm.hafner.java2.sokoban;

import org.junit.Test;

import static edu.hm.hafner.java2.sokoban.Field.*;
import static edu.hm.hafner.java2.sokoban.SokobanAssertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link Sokoban}.
 *
 * @author Ullrich Hafner
 */
public class SokobanTest {
    /** Verifies that a treasure can be moved. */
    @Test
    public void shouldMoveTreasureToTarget() {
        SokobanGame sokoban = createLevel();
        assertThatPlayerIsAt(sokoban, new Point(3, 4));
        assertThatTreasuresAreAt(sokoban, new Point(2, 4), new Point(4, 5));

        sokoban.moveLeft();
        assertThatPlayerIsAt(sokoban, new Point(3, 4));

        sokoban.moveDown();
        assertThatPlayerIsAt(sokoban, new Point(3, 5));

        sokoban.moveLeft();
        assertThatPlayerIsAt(sokoban, new Point(2, 5));

        sokoban.moveUp();
        assertThatPlayerIsAt(sokoban, new Point(2, 4));
        assertThatTreasuresAreAt(sokoban, new Point(2, 3), new Point(4, 5));

        sokoban.moveRight();
        sokoban.moveRight();
        sokoban.moveRight();
        sokoban.moveDown();
        sokoban.moveLeft();
        assertThatPlayerIsAt(sokoban, new Point(4, 5));
        assertThatTreasuresAreAt(sokoban, new Point(2, 3), new Point(3, 5));
    }

    /**
     * Verifies that the player move methods work in the corner.
     */
    @Test
    public void shouldDetectCorners() {
        // Given
        Field[][] fields = {
                {WALL, WALL, WALL, WALL},
                {WALL, FLOOR, TARGET, WALL},
                {WALL, WALL, WALL, WALL},
        };
        Sokoban sokoban = createSokoban();
        sokoban.setLevel(fields);
        Point player = new Point(1, 1);
        sokoban.setPlayer(player);
        sokoban.addTreasure(new Point(2, 1));
        sokoban.validate();

        // When and Then
        sokoban.moveUp();
        assertThatPlayerIsAt(sokoban, player);

        // When and Then
        sokoban.moveDown();
        assertThatPlayerIsAt(sokoban, player);

        // When and Then
        sokoban.moveLeft();
        assertThatPlayerIsAt(sokoban, player);
    }

    private SokobanGame createLevel() {
        Field[][] fields = {
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, WALL, WALL, WALL, WALL, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, WALL, FLOOR, TARGET, WALL, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, WALL, FLOOR, FLOOR, WALL, WALL, WALL, BACKGROUND},
                {BACKGROUND, WALL, TARGET, FLOOR, FLOOR, FLOOR, WALL, BACKGROUND},
                {BACKGROUND, WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL, BACKGROUND},
                {BACKGROUND, WALL, FLOOR, FLOOR, WALL, WALL, WALL, BACKGROUND},
                {BACKGROUND, WALL, WALL, WALL, WALL, BACKGROUND, BACKGROUND, BACKGROUND},
                {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
        };
        Sokoban sokoban = createSokoban();
        sokoban.setLevel(fields);
        sokoban.setPlayer(new Point(3, 4));
        sokoban.addTreasure(new Point(2, 4));
        sokoban.addTreasure(new Point(4, 5));
        sokoban.validate();
        return sokoban;
    }

    /** Verifies that null values are not stored. */
    @Test
    public void shouldThrowNpeIfLevelIsNull() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokoban();

            // When
            sokoban.setLevel(null);
        }).isInstanceOf(NullPointerException.class); // Then
    }

    /** Verifies that null values are not stored. */
    @Test
    public void shouldThrowNpeIfFieldIsNull() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokoban();
            Field[][] level = createLevelWithOneTreasure();
            level[0][0] = null;

            // When
            sokoban.setLevel(level);
        }).isInstanceOf(NullPointerException.class); // Then
    }

    /** Verifies that null values are not stored. */
    @Test
    public void shouldThrowNpeIfPlayerIsNull() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();

            // When
            sokoban.setPlayer(null);
        }).isInstanceOf(NullPointerException.class); // Then
    }

    /** Verifies that null values are not stored. */
    @Test
    public void shouldThrowNpeIfTreasureIsNull() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();

            // When
            sokoban.addTreasure(null);
        }).isInstanceOf(NullPointerException.class); // Then
    }

    /** Verifies that the player is not placed on a wall. */
    @Test
    public void shouldDetectInvalidWorldWithPlayerOnWall() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.setPlayer(new Point(0, 0));
            sokoban.addTreasure(new Point(1, 2));

            // When
            sokoban.validate();
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that no treasure is placed on a wall. */
    @Test
    public void shouldDetectInvalidWorldWithTreasureOnWall() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.setPlayer(new Point(1, 1));
            sokoban.addTreasure(new Point(0, 0));

            // When
            sokoban.validate();
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that the player is not placed on a treasure. */
    @Test
    public void shouldDetectInvalidWorldWithPlayerOnTreasure() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.setPlayer(new Point(1, 1));
            sokoban.addTreasure(new Point(1, 1));

            // When
            sokoban.validate();
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that the number of targets is equal to the number of treasures. */
    @Test
    public void shouldDetectInvalidWorldThatHasNotEnoughTargets() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.setPlayer(new Point(2, 3));
            sokoban.addTreasure(new Point(3, 3));
            sokoban.addTreasure(new Point(3, 4));

            // When
            sokoban.validate();
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that the number of targets is equal to the number of treasures. */
    @Test
    public void shouldDetectInvalidWorldThatHasNotEnoughTreasures() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.setPlayer(new Point(2, 3));

            // When
            sokoban.validate();
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that the player has been set. */
    @Test
    public void shouldDetectInvalidWorldThatHasNoPlayer() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.addTreasure(new Point(3, 4));

            // When
            sokoban.validate();
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that the field array is regular, i.e. the size of each line is the width. */
    @Test
    public void shouldDetectIrregularArray() {
        assertThatThrownBy(() -> {
            // Given
            Sokoban sokoban = createSokobanWithOneTreasure();
            sokoban.setLevel(createIrregularArray());
        }).isInstanceOf(IllegalArgumentException.class); // Then
    }

    /** Verifies that duplicate treasures are skipped. */
    @Test
    public void shouldSkipDuplicateTreasure() {
        // Given
        Sokoban sokoban = createSokobanWithOneTreasure();
        sokoban.setPlayer(new Point(1, 1));
        sokoban.addTreasure(new Point(1, 2));
        sokoban.addTreasure(new Point(1, 2));

        // When
        sokoban.validate();
        boolean isSolved = sokoban.isSolved();

        // Then
        assertThat(isSolved).isFalse();
    }

    /** Verifies that setting a player outside of the visible level is correctly detected. */
    @Test
    public void shouldDetectPlayerOutsideOfLevel() {
        shouldDetectPlayersOutsideOfField(
                new Point(-1, 0), new Point(0, -1), // Links oben
                new Point(2, 0), new Point(1, -1), // Rechts oben
                new Point(2, 1), new Point(1, 2),  // Rechts unten
                new Point(-1, 1), new Point(0, 2)   // Links unten
        );
    }

    private void shouldDetectPlayersOutsideOfField(final Point... outsidePlayers) {
        for (Point outsidePoint : outsidePlayers) {
            assertThatThrownBy(() -> {
                // Given
                Sokoban sokoban = createSokobanWithOneTreasure();
                sokoban.setLevel(new Field[][]{
                        {WALL, WALL},
                        {WALL, TARGET},
                });
                sokoban.setPlayer(outsidePoint);
                // When
                sokoban.validate();
            }).isInstanceOf(IllegalArgumentException.class); // Then
        }
    }

    /** Verifies that setting a treasure outside of the visible level is correctly detected. */
    @Test
    public void shouldDetectTreasureOutsideOfLevel() {
        shouldDetectTreasuresOutsideOfField(
                new Point(-1, 0), new Point(0, -1), // Links oben
                new Point(2, 0), new Point(1, -1), // Rechts oben
                new Point(2, 1), new Point(1, 2),  // Rechts unten
                new Point(-1, 1), new Point(0, 2)   // Links unten
        );
    }

    private void shouldDetectTreasuresOutsideOfField(final Point... outsidePlayers) {
        for (Point outsidePoint : outsidePlayers) {
            assertThatThrownBy(() -> {
                // Given
                Sokoban sokoban = createSokobanWithOneTreasure();
                sokoban.setLevel(new Field[][]{
                        {WALL, WALL},
                        {WALL, TARGET},
                });
                sokoban.setPlayer(new Point(1, 1));
                sokoban.addTreasure(outsidePoint);
                // When
                sokoban.validate();
            }).isInstanceOf(IllegalArgumentException.class); // Then
        }
    }

    /** Verifies that a valid world with one treasure is correctly detected. */
    @Test
    public void shouldValidateCorrectWorldWithOneTreasure() {
        // Given
        Sokoban sokoban = createSokobanWithOneTreasure();
        Point player = new Point(2, 3);
        sokoban.setPlayer(player);
        Point treasure = new Point(3, 4);
        sokoban.addTreasure(treasure);

        // When
        sokoban.validate();
        boolean isSolved = sokoban.isSolved();

        // Then
        assertThat(isSolved).isFalse();

        assertThatPlayerIsAt(sokoban, player);
        assertThatTreasuresAreAt(sokoban, treasure);
        assertThatFieldIsCorrect(sokoban, createLevelWithOneTreasure());
    }

    /** Verifies that a valid world with two treasures is correctly detected. */
    @Test
    public void shouldValidateCorrectWorldWithTwoTreasures() {
        // Given
        Sokoban sokoban = createSokobanWithTwoTreasures();
        Point player = new Point(1, 1);
        sokoban.setPlayer(player);
        Point treasure1 = new Point(1, 2);
        sokoban.addTreasure(treasure1);
        Point treasure2 = new Point(1, 3);
        sokoban.addTreasure(treasure2);

        // When
        sokoban.validate();
        boolean isSolved = sokoban.isSolved();

        // Then
        assertThat(isSolved).isFalse();

        assertThatPlayerIsAt(sokoban, player);
        assertThatTreasuresAreAt(sokoban, treasure1, treasure2);
        assertThatFieldIsCorrect(sokoban, createLevelWithTwoTreasures());
    }

    /** Verifies that a level has been solved. */
    @Test
    public void shouldDetectSolvedWorldWithOneTreasure() {
        // Given
        Sokoban sokoban = createSokobanWithOneTreasure();
        sokoban.setPlayer(new Point(2, 3));
        sokoban.addTreasure(new Point(2, 1));

        // When
        sokoban.validate();
        boolean isSolved = sokoban.isSolved();

        // Then
        assertThat(isSolved).isTrue();

        // Then
        assertThat(sokoban.isSolved()).isTrue();
    }

    /** Verifies that a level has been solved. */
    @Test
    public void shouldDetectSolvedWorldWithTwoTreasures() {
        // Given
        Sokoban sokoban = createSokobanWithTwoTreasures();
        sokoban.setPlayer(new Point(1, 1));
        sokoban.addTreasure(new Point(2, 1));
        sokoban.addTreasure(new Point(2, 2));

        // When
        sokoban.validate();
        boolean isSolved = sokoban.isSolved();

        // Then
        assertThat(isSolved).isTrue();
    }

    /** Verifies that a level will be copied internally. */
    @Test
    public void shouldCopyLevel() {
        // Given
        Field[][] level = createLevelWithOneTreasure();
        Sokoban sokoban = createSokoban();
        sokoban.setLevel(level);
        sokoban.setPlayer(new Point(2, 3));
        sokoban.addTreasure(new Point(2, 1));

        // When
        fillArrayWithTarget(level);

        // When
        sokoban.validate();
        // Then
        assertThat(sokoban.isSolved()).isTrue();
    }

    private Sokoban createSokobanWithOneTreasure() {
        Sokoban sokoban = createSokoban();
        sokoban.setLevel(createLevelWithOneTreasure());
        return sokoban;

    }

    protected Sokoban createSokoban() {
        return new Sokoban();
    }

    private Sokoban createSokobanWithTwoTreasures() {
        Sokoban sokoban = createSokoban();
        sokoban.setLevel(createLevelWithTwoTreasures());
        return sokoban;
    }

    private void fillArrayWithTarget(final Field[][] level) {
        for (Field[] fields : level) {
            for (int i = 0; i < fields.length; i++) {
                fields[i] = TARGET;
            }
        }
    }

    private Field[][] createIrregularArray() {
        return new Field[][]{
                {WALL, WALL, WALL, WALL},
                {WALL, FLOOR, TARGET, WALL},
                {WALL, FLOOR, FLOOR, WALL, WALL, WALL},
                {WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
                {WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
                {WALL, FLOOR, FLOOR, WALL},
                {WALL, WALL, WALL, WALL},
        };
    }

    private Field[][] createLevelWithOneTreasure() {
        return new Field[][]{
                {WALL, WALL, WALL, WALL, BACKGROUND, BACKGROUND},
                {WALL, FLOOR, TARGET, WALL, BACKGROUND, BACKGROUND},
                {WALL, FLOOR, FLOOR, WALL, WALL, WALL},
                {WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
                {WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL},
                {WALL, FLOOR, FLOOR, WALL, BACKGROUND, BACKGROUND},
                {WALL, WALL, WALL, WALL, BACKGROUND, BACKGROUND},
        };
    }

    private Field[][] createLevelWithTwoTreasures() {
        return new Field[][]{
                {WALL, WALL, WALL, WALL},
                {WALL, FLOOR, TARGET, WALL},
                {WALL, FLOOR, TARGET, WALL},
                {WALL, FLOOR, FLOOR, WALL},
                {WALL, WALL, WALL, WALL},
        };
    }
}