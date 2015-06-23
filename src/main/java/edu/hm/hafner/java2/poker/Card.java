package edu.hm.hafner.java2.poker;

/**
 * A poker card.
 *
 * @author Ullrich Hafner
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    /**
     * Creates a new instance of {@link Card}.
     *
     * @param suit suit of this card
     * @param rank rank of this card
     */
    public Card(final Suit suit, final Rank rank) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card)o;

        if (suit != card.suit) {
            return false;
        }
        return rank == card.rank;

    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return suit + " " + rank;
    }

    /**
     * Returns the rank of this card.
     *
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of this card.
     *
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * All possible suits.
     */
    public enum Suit { SPADES, HEARTS, DIAMONDS, CLUBS }

    /**
     * All possible ranks.
     */
    public enum Rank { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}
}
