package edu.hm.hafner.java2.poker;

import org.junit.Test;

import static edu.hm.hafner.java2.poker.Card.Rank.*;
import static edu.hm.hafner.java2.poker.Card.Suit.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Abstract test for poker categories.
 *
 * @author Ullrich Hafner
 */
public abstract class AbstractPokerCategoryTest {
    protected static final Card CLUBS_ACE = new Card(CLUBS, ACE);
    protected static final Card CLUBS_KING = new Card(CLUBS, KING);
    protected static final Card CLUBS_QUEEN = new Card(CLUBS, QUEEN);
    protected static final Card CLUBS_JACK = new Card(CLUBS, JACK);
    protected static final Card CLUBS_TEN = new Card(CLUBS, TEN);
    protected static final Card CLUBS_NINE = new Card(CLUBS, NINE);
    protected static final Card CLUBS_EIGHT = new Card(CLUBS, EIGHT);
    protected static final Card CLUBS_SIX = new Card(CLUBS, SIX);
    protected static final Card CLUBS_FIVE = new Card(CLUBS, FIVE);
    protected static final Card CLUBS_FOUR = new Card(CLUBS, FOUR);
    protected static final Card CLUBS_THREE = new Card(CLUBS, THREE);
    protected static final Card CLUBS_TWO = new Card(CLUBS, TWO);

    protected static final Card DIAMONDS_ACE = new Card(DIAMONDS, ACE);
    protected static final Card DIAMONDS_KING = new Card(DIAMONDS, KING);
    protected static final Card DIAMONDS_QUEEN = new Card(DIAMONDS, QUEEN);

    protected static final Card SPACES_ACE = new Card(SPADES, ACE);
    protected static final Card SPADES_KING = new Card(SPADES, KING);
    protected static final Card SPADES_QUEEN = new Card(SPADES, QUEEN);

    /**
     * Creates the category under test.
     *
     * @return  the category under test.
     */
    protected abstract AbstractPokerCategory createCategory();

    /** Verifies that a hand with 6 cards is not valid. */
    @Test
    public void shouldNotAcceptMoreThan5Cards() {
        assertThatThrownBy(() -> {
            AbstractPokerCategory fullHouse = createCategory();
            Card[] hand = {CLUBS_ACE, SPACES_ACE, CLUBS_KING, SPADES_KING, CLUBS_QUEEN, SPADES_QUEEN};

            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[CLUBS ACE, SPADES ACE, CLUBS KING, SPADES KING, CLUBS QUEEN, SPADES QUEEN]");
    }

    /** Verifies that a hand with 4 cards is not valid. */
    @Test
    public void shouldNotAcceptLessThan5Cards() {
        assertThatThrownBy(() -> {
            AbstractPokerCategory fullHouse = createCategory();
            Card[] hand = {CLUBS_ACE, SPACES_ACE, DIAMONDS_KING, CLUBS_KING};

            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[CLUBS ACE, SPADES ACE, DIAMONDS KING, CLUBS KING]");
    }

    /** Verifies that the same cards are not allowed in a hand. */
    @Test
    public void shouldNotAcceptSameCards() {
        assertThatThrownBy(() -> {
            AbstractPokerCategory fullHouse = createCategory();
            Card[] hand = {CLUBS_ACE, SPACES_ACE, CLUBS_KING, SPADES_KING, CLUBS_KING};

            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("CLUBS KING");
    }

    /** Verifies that equal cards are not allowed in a hand. */
    @Test
    public void shouldNotAcceptEqualCards() {
        assertThatEqualCardsAreDetected(
                new Card(CLUBS, KING), SPADES_KING, CLUBS_ACE, SPACES_ACE, new Card(CLUBS, KING));
        assertThatEqualCardsAreDetected(
                new Card(CLUBS, KING), new Card(CLUBS, KING), CLUBS_ACE, SPACES_ACE, SPADES_KING);
        assertThatEqualCardsAreDetected(
                CLUBS_ACE, SPACES_ACE, SPADES_KING, new Card(CLUBS, KING), new Card(CLUBS, KING));
    }

    private void assertThatEqualCardsAreDetected(final Card... hand) {
        assertThatThrownBy(() -> {
            AbstractPokerCategory fullHouse = createCategory();
            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("CLUBS KING");
    }

    /** Verifies that null is not allowed as hand. */
    @Test
    public void shouldNotAcceptNullHand() {
        AbstractPokerCategory fullHouse = createCategory();

        assertThatThrownBy(() -> {
            fullHouse.isValid(null);
        }).isInstanceOf(NullPointerException.class);
    }
}