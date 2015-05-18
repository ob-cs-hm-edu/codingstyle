package edu.hm.hafner.java2.sokoban;

import static org.assertj.core.api.Assertions.*;

/**
 * Provides several assertions to simplify testing of a Sokoban level.
 *
 * @author Ullrich Hafner
 */
public final class SokobanAssertions {
    private SokobanAssertions() {
        // prevents instantiation
    }

    public static void assertThatTreasuresAreAt(final SokobanGame sokoban, final Point... treasures) {
        assertThat(sokoban.getTreasures().size()).as("Wrong number of treasures").isEqualTo(treasures.length);
        for (Point treasure : treasures) {
            assertThat(sokoban.getTreasures().contains(treasure)).as("Treasure not found at " + treasure).isTrue();
        }
    }

    public static void assertThatPlayerIsAt(final SokobanGame sokoban, final Point player) {
        assertThat(sokoban.getPlayer().isEqualTo(player)).as("Player in not at position " + player).isTrue();
    }

    public static void assertThatFieldIsCorrect(final SokobanGame sokoban, final Field[][] expected) {
        for (int y = 0; y < sokoban.getHeight(); y++) {
            for (int x = 0; x < sokoban.getWidth(); x++) {
                assertThat(sokoban.getField(new Point(x, y)))
                        .as("Field (%d, %d)", x, y)
                        .isEqualTo(expected[y][x]);
            }
        }
    }

}
