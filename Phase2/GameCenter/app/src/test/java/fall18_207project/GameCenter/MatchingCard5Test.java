package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchingCard5Test {
    private MatchingCards matchCards;

    /**
     * Make a list of cards in order of "AA22...JJ" and the bomb in the end
     * @return a list of cards in order of their id
     */
    private List<Card> makeCards() {
        int n = 5;
        List<Card> cards = new ArrayList<>();
        int numTiles = n * n;
        for (int i = 0; i < numTiles; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }

    /**
     * initialize a matchingboard with first 20 cards used
     */
    private void setUpCorrect() {
        matchCards = new MatchingCards(5);
        List<Card> cards = makeCards();
        matchCards.matchingBoard = new MatchingBoard(cards, 5);

        // Since I makecards in order, its guaranteed when I do the following loop, the two cards will match
        for (int i = 0; i<20; i++){
            matchCards.touchMove(i);
        }
    }

    /**
     * Test whether isSolved() return expected value
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();

        assertEquals(23, matchCards.getMatchingBoard().getCard(4, 2).getId());
        assertEquals(24, matchCards.getMatchingBoard().getCard(4, 3).getId());
        assertFalse(matchCards.getMatchingBoard().getCard(4, 2).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(4, 3).isUsed());

        matchCards.touchMove(22);
        matchCards.touchMove(23);
        assertFalse(matchCards.isSolved());

        matchCards.touchMove(21);
        matchCards.touchMove(20);
        assertTrue(matchCards.isSolved());
    }

    @Test
    public void testIsSolvedWithBomb() {
        setUpCorrect();

        assertTrue(matchCards.getMatchingBoard().getCard(4,4).isBomb());
        assertFalse(matchCards.isSolved());
        matchCards.touchMove(24);
        assertTrue(matchCards.isSolved());
    }

    /**
     * Test isUsed method in MatchingCards
     */
    @Test
    public void testIsUsed() {
        setUpCorrect();

        assertTrue(matchCards.getMatchingBoard().getCard(0, 0).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(4, 4).isUsed());

        matchCards.touchMove(23);
        assertFalse(matchCards.getMatchingBoard().getCard(4,3).isUsed());
        matchCards.touchMove(22);
        assertTrue(matchCards.getMatchingBoard().getCard(4, 3).isUsed());
    }

    /**
     * Test IsMatched method in MatchingCards
     */
    @Test
    public void testIsMatched() {
        setUpCorrect();

        // Since its comparing to Prepos == -1
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 2));

        matchCards.touchMove(23);
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(4, 3));
        assertTrue(matchCards.isMatched(4, 2));
    }
}
