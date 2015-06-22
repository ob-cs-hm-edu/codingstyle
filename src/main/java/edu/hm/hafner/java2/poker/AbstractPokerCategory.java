package edu.hm.hafner.java2.poker;

import java.util.Arrays;

/**
 * Base class for a poker category that provides validation of the hand.
 *
 * @author Ullrich Hafner
 */
public abstract class AbstractPokerCategory {
    /**
     * Returns whether the specified hand is valid for this category.
     *
     * @param hand the hand to check
     * @return {@code true} if the hand is valid, {@code false} otherwise
     * @throws IllegalArgumentException if the hand does not contain 5 different cards
     */
    public final boolean isValid(final Card... hand) {
        if (hand.length != 5) {
            throw new IllegalArgumentException("Hand must contain exactly 5 cards: " + Arrays.toString(hand));
        }
        for (int i = 0; i < hand.length - 1; i++) {
            for (int j = i + 1; j < hand.length; j++) {
                Card first = hand[i];
                Card second = hand[j];
                if (first == second || first.equals(second)) {
                    throw new IllegalArgumentException("Duplicate card in hand: " + first);
                }
            }
        }

        return isValidCategory(hand);
    }

    /**
     * Returns whether the specified hand is valid for this category.
     *
     * @param hand the hand to check
     * @return {@code true} if the hand is of the given category, {@code false} otherwise
     */
    protected abstract boolean isValidCategory(final Card... hand);
}
