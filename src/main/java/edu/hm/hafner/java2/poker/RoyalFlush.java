package edu.hm.hafner.java2.poker;

import static edu.hm.hafner.java2.poker.Card.Rank.*;

/**
 * Detects if a hand contains a royal flush.
 *
 * @author Ullrich Hafner
 */
public class RoyalFlush extends AbstractPokerCategory {
    private static final int FLUSH_ORDINAL = ACE.ordinal() + KING.ordinal() + QUEEN.ordinal() + JACK.ordinal() + TEN.ordinal();

    @Override
    protected boolean isValidCategory(final Card... hand) {
        int sum = points(hand[0]);

        for (int i = 1; i < hand.length; i++) {
            Card card = hand[i];
            if (card.getSuit() != hand[0].getSuit()) {
                return false;
            }
            sum += points(card);
        }
        return sum == FLUSH_ORDINAL;
    }

    private int points(final Card card) {
        return card.getRank().ordinal();
    }
}
