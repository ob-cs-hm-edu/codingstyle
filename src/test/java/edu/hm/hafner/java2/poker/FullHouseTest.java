package edu.hm.hafner.java2.poker;

import org.junit.Test;

import static edu.hm.hafner.java2.poker.Card.Rank.*;
import static edu.hm.hafner.java2.poker.Card.Suit.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@code FullHouse}.
 *
 * @author Ullrich Hafner
 */
public class FullHouseTest {
    private static final Card CLUBS_ACE = new Card(CLUBS, ACE);
    private static final Card CLUBS_KING = new Card(CLUBS, KING);
    private static final Card CLUBS_QUEEN = new Card(CLUBS, QUEEN);

    private static final Card DIAMONDS_ACE = new Card(DIAMONDS, ACE);
    private static final Card DIAMONDS_KING = new Card(DIAMONDS, KING);
    private static final Card DIAMONDS_QUEEN = new Card(DIAMONDS, QUEEN);

    private static final Card SPACES_ACE = new Card(SPADES, ACE);
    private static final Card SPADES_KING = new Card(SPADES, KING);
    private static final Card SPADES_QUEEN = new Card(SPADES, QUEEN);

    /** Verifies that a hand with 6 cards is not valid. */
    @Test
    public void shouldNotAcceptMoreThan5Cards() {
        assertThatThrownBy(() -> {
            FullHouse fullHouse = new FullHouse();
            Card[] hand = {CLUBS_ACE, SPACES_ACE, CLUBS_KING, SPADES_KING, CLUBS_QUEEN, SPADES_QUEEN};

            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[CLUBS ACE, SPADES ACE, CLUBS KING, SPADES KING, CLUBS QUEEN, SPADES QUEEN]");
    }

    /** Verifies that a hand with 4 cards is not valid. */
    @Test
    public void shouldNotAcceptLessThan5Cards() {
        assertThatThrownBy(() -> {
            FullHouse fullHouse = new FullHouse();
            Card[] hand = {CLUBS_ACE, SPACES_ACE, DIAMONDS_KING, CLUBS_KING};

            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[CLUBS ACE, SPADES ACE, DIAMONDS KING, CLUBS KING]");
    }

    /** Verifies that the same cards are not allowed in a hand. */
    @Test
    public void shouldNotAcceptSameCards() {
        assertThatThrownBy(() -> {
            FullHouse fullHouse = new FullHouse();
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
            FullHouse fullHouse = new FullHouse();
            fullHouse.isValid(hand);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("CLUBS KING");
    }

    /** Verifies that null is not allowed as hand. */
    @Test
    public void shouldNotAcceptNullHand() {
        FullHouse fullHouse = new FullHouse();

        assertThatThrownBy(() -> {
            fullHouse.isValid(null);
        }).isInstanceOf(NullPointerException.class);
    }
    
    /** Verifies that two pairs are not detected as a full house. */
    @Test
    public void shouldNotDetectTwoPairsAsFullHouse() {
        FullHouse fullHouse = new FullHouse();
        Card[] hand = {CLUBS_ACE, SPACES_ACE, DIAMONDS_QUEEN, CLUBS_KING, DIAMONDS_KING};

        assertThat(fullHouse.isValid(hand)).isFalse();
    }

    /** Verifies that a full house will be correctly detected. */
    @Test
    public void shouldDetectFullHouse() {
        FullHouse fullHouse = new FullHouse();
        Card[] hand = {CLUBS_ACE, SPACES_ACE, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING};

        assertThat(fullHouse.isValid(hand)).isTrue();
    }
}
