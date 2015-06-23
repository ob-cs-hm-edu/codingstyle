package edu.hm.hafner.java2.poker;

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

        assertThat(fullHouse.isValid(CLUBS_ACE, SPADES_ACE, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING)).isTrue();
        assertThat(fullHouse.isValid(CLUBS_ACE, SPADES_ACE, CLUBS_KING, DIAMONDS_ACE, DIAMONDS_KING)).isTrue();
        assertThat(fullHouse.isValid(CLUBS_ACE, CLUBS_KING, SPADES_ACE, DIAMONDS_ACE, DIAMONDS_KING)).isTrue();
        assertThat(fullHouse.isValid(CLUBS_KING, CLUBS_ACE, SPADES_ACE, DIAMONDS_ACE, DIAMONDS_KING)).isTrue();

        assertThat(fullHouse.isValid(SPADES_ACE, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING, CLUBS_ACE)).isTrue();
        assertThat(fullHouse.isValid(SPADES_ACE, CLUBS_KING, DIAMONDS_ACE, DIAMONDS_KING, CLUBS_ACE)).isTrue();
        assertThat(fullHouse.isValid(CLUBS_KING, SPADES_ACE, DIAMONDS_ACE, DIAMONDS_KING, CLUBS_ACE)).isTrue();

        assertThat(fullHouse.isValid(DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING, SPADES_ACE, CLUBS_ACE)).isTrue();
        assertThat(fullHouse.isValid(CLUBS_KING, DIAMONDS_ACE, DIAMONDS_KING, SPADES_ACE, CLUBS_ACE)).isTrue();

        assertThat(fullHouse.isValid(CLUBS_KING, DIAMONDS_KING, DIAMONDS_ACE, SPADES_ACE, CLUBS_ACE)).isTrue();
    }
}
