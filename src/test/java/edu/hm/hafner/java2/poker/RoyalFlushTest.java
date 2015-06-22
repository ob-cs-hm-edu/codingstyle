package edu.hm.hafner.java2.poker;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link RoyalFlush}.
 *
 * @author Ullrich Hafner
 */
public class RoyalFlushTest extends AbstractPokerCategoryTest {
    @Override
    protected RoyalFlush createCategory() {
        return new RoyalFlush();
    }

    /** Verifies that a straight flush is not detected as a royal flush. */
    @Test
    public void shouldNotDetectStraightFlushAsRoyalFlush() {
        RoyalFlush royalFlush = createCategory();
        Card[] hand = {CLUBS_QUEEN, CLUBS_KING, CLUBS_NINE, CLUBS_TEN, CLUBS_JACK};

        assertThat(royalFlush.isValid(hand)).isFalse();
    }

    /** Verifies that a flush is not detected as a royal flush. */
    @Test
    public void shouldNotDetectFlushAsRoyalFlush() {
        RoyalFlush royalFlush = createCategory();
        Card[] hand = {CLUBS_ACE, CLUBS_QUEEN, CLUBS_KING, CLUBS_NINE, CLUBS_TEN};

        assertThat(royalFlush.isValid(hand)).isFalse();
    }

    /** Verifies that a straight is not detected as a royal flush. */
    @Test
    public void shouldNotDetectStraightAsRoyalFlush() {
        RoyalFlush royalFlush = createCategory();
        Card[] hand = {CLUBS_ACE, SPADES_QUEEN, CLUBS_KING, CLUBS_TEN, CLUBS_JACK};

        assertThat(royalFlush.isValid(hand)).isFalse();
    }

    /** Verifies that a royal flush will be correctly detected. */
    @Test
    public void shouldDetectRoyalFlush() {
        RoyalFlush royalFlush = createCategory();
        Card[] hand = {CLUBS_ACE, CLUBS_QUEEN, CLUBS_KING, CLUBS_TEN, CLUBS_JACK};

        assertThat(royalFlush.isValid(hand)).isTrue();
    }
}