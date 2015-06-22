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
        Card[] hand = {CLUBS_ACE, SPACES_ACE, DIAMONDS_QUEEN, CLUBS_KING, DIAMONDS_KING};

        assertThat(fullHouse.isValid(hand)).isFalse();
    }

    /** Verifies that a full house will be correctly detected. */
    @Test
    public void shouldDetectFullHouse() {
        FullHouse fullHouse = createCategory();
        Card[] hand = {CLUBS_ACE, SPACES_ACE, DIAMONDS_ACE, CLUBS_KING, DIAMONDS_KING};

        assertThat(fullHouse.isValid(hand)).isTrue();
    }
}
