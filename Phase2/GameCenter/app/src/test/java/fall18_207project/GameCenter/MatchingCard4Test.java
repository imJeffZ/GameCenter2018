package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchingCard4Test {
    private MatchingCards matchCards;

    /**
     * Make a list of cards in order of "AA22...88"
     * @return a list of cards in order of their id
     */
    private List<Card> makeCards() {
        int n = 4;
        List<Card> cards = new ArrayList<>();
        int numTiles = n * n;
        for (int i = 0; i < numTiles; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }

    /**
     * initialize a matchingboard with first 12 cards used
     */
    private void setUpCorrect() {
        matchCards = new MatchingCards(4);
        List<Card> cards = makeCards();
        matchCards.matchingBoard = new MatchingBoard(cards, 4);

        // Since I makecards in order, its guaranteed when i do the following loop, the two cards will match
        for (int i = 0; i<6; i++){
            matchCards.touchMove(2 * i);
            matchCards.touchMove(2 * i+1);
        }
    }

    /**
     * Test whether isSolved() return expected value
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();

        assertEquals(15, matchCards.getMatchingBoard().getCard(3, 2).getId());
        assertEquals(16, matchCards.getMatchingBoard().getCard(3, 3).getId());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 2).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 3).isUsed());

        matchCards.touchMove(14);
        matchCards.touchMove(15);
        assertFalse(matchCards.isSolved());

        matchCards.touchMove(13);
        matchCards.touchMove(12);
        assertTrue(matchCards.isSolved());
    }

    /**
     * Test isUsed method in MatchingCards
     */
    @Test
    public void testIsUsed() {
        setUpCorrect();

        assertTrue(matchCards.getMatchingBoard().getCard(0, 0).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 3).isUsed());

        matchCards.touchMove(14);
        matchCards.touchMove(15);
        assertTrue(matchCards.getMatchingBoard().getCard(3, 3).isUsed());
    }

    /**
     * Test IsMatched method in MatchingCards
     */
    @Test
    public void testIsMatched() {
        setUpCorrect();

        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 2));

        matchCards.touchMove(15);
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 3));
        assertTrue(matchCards.isMatched(3, 2));
    }
}
