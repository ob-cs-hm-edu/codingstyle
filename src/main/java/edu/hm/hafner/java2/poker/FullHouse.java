package edu.hm.hafner.java2.poker;

import edu.hm.hafner.java2.poker.Card.Rank;

/**
 * Detects if a hand contains a full house.
 *
 * @author Ullrich Hafner
 */
public class FullHouse extends AbstractPokerCategory {
    @Override
    protected boolean isValidCategory(final Card... hand) {
        int[] occurrences = new int[Rank.values().length];
        for (Card card : hand) {
            occurrences[card.getRank().ordinal()]++;
        }
        int threeOfAKind = 0;
        int pair = 0;
        for (int rank = 0; rank < occurrences.length; rank++) {
            int occurrence = occurrences[rank];
            if (occurrence == 3) {
                threeOfAKind = rank;
            }
            if (occurrence == 2) {
                pair = rank;
            }
        }

        return threeOfAKind > 0 && pair > 0;
    }
}
