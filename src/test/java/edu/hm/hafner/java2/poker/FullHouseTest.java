package edu.hm.hafner.java2.poker;

import java.util.Arrays;

import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@code FullHouse}.
 *
 * @author Ullrich Hafner
 */
public class FullHouseTest extends AbstractPokerCategoryTest {
    @Override
    protected FullHouse createCategory() {
        return new FullHouse();
    }

    /** Verifies that two pairs are not detected as a full house. */
    @Test
    public void shouldNotDetectTwoPairsAsFullHouse() {
        FullHouse fullHouse = createCategory();
        Card[] hand = {CLUBS_ACE, SPADES_ACE, DIAMONDS_QUEEN, CLUBS_KING, DIAMONDS_KING};

        assertThat(fullHouse.isValid(hand)).isFalse();
    }

    /** Verifies that a full house will be correctly detected. */
    @Test
    public void shouldDetectFullHouse() {
        FullHouse fullHouse = createCategory();

        assertThatHandIsFullHouse(fullHouse, CLUBS_ACE, SPADES_ACE, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING);
        assertThatHandIsFullHouse(fullHouse, CLUBS_ACE, SPADES_ACE, CLUBS_KING, DIAMONDS_ACE, DIAMONDS_KING);
        assertThatHandIsFullHouse(fullHouse, CLUBS_ACE, CLUBS_KING, SPADES_ACE, DIAMONDS_ACE, DIAMONDS_KING);
        assertThatHandIsFullHouse(fullHouse, CLUBS_KING, CLUBS_ACE, SPADES_ACE, DIAMONDS_ACE, DIAMONDS_KING);

        assertThatHandIsFullHouse(fullHouse, SPADES_ACE, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING, CLUBS_ACE);
        assertThatHandIsFullHouse(fullHouse, SPADES_ACE, CLUBS_KING, DIAMONDS_ACE, DIAMONDS_KING, CLUBS_ACE);
        assertThatHandIsFullHouse(fullHouse, CLUBS_KING, SPADES_ACE, DIAMONDS_ACE, DIAMONDS_KING, CLUBS_ACE);

        assertThatHandIsFullHouse(fullHouse, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING, SPADES_ACE, CLUBS_ACE);
        assertThatHandIsFullHouse(fullHouse, CLUBS_KING, DIAMONDS_ACE, DIAMONDS_KING, SPADES_ACE, CLUBS_ACE);

        assertThatHandIsFullHouse(fullHouse, CLUBS_KING, DIAMONDS_KING, DIAMONDS_ACE, SPADES_ACE, CLUBS_ACE);
    }

    private AbstractBooleanAssert<?> assertThatHandIsFullHouse(final FullHouse fullHouse, final Card... hand) {
        return assertThat(fullHouse.isValid(hand)).as("Hand should be a Full House: " + Arrays.toString(hand)).isTrue();
    }
}
