package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MatchingCard6Test {
    private MatchingCards matchCards;

    /**
     * Make a list of cards in order of "AA22...JJ, Bomb, Bomb, KK" and the bombs in the end
     * @return a list of cards in order of their id
     */
    private List<Card> makeCards() {
        int n = 6;
        List<Card> cards = new ArrayList<>();
        int numTiles = n * n;
        for (int i = 0; i < numTiles; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }

    /**
     * initialize a matchingboard with first 22 cards used
     */
    private void setUpCorrect() {
        matchCards = new MatchingCards(6);
        List<Card> cards = makeCards();
        matchCards.matchingBoard = new MatchingBoard(cards, 6);

        // Since I makecards in order, its guaranteed when I do the following loop, the two cards will match
        for (int i = 0; i<22; i++){
            matchCards.touchMove(i);
        }
    }

    /**
     * Test whether isSolved() return expected value
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();

        assertEquals(23, matchCards.getMatchingBoard().getCard(3, 4).getId());
        assertEquals(24, matchCards.getMatchingBoard().getCard(3, 5).getId());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 4).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 5).isUsed());

        matchCards.touchMove(22);
        matchCards.touchMove(23);
        assertFalse(matchCards.isSolved());

        matchCards.touchMove(25);
        matchCards.touchMove(26);
        assertTrue(matchCards.isSolved());
    }

    @Test
    public void testIsSolvedWithBomb() {
        setUpCorrect();

        assertTrue(matchCards.getMatchingBoard().getCard(5,4).isBomb());
        assertFalse(matchCards.isSolved());
        matchCards.touchMove(34);
        assertTrue(matchCards.isSolved());
    }

    /**
     * Test isUsed method in MatchingCards
     */
    @Test
    public void testIsUsed() {
        setUpCorrect();

        assertTrue(matchCards.getMatchingBoard().getCard(0, 0).isUsed());
        assertFalse(matchCards.getMatchingBoard().getCard(3, 4).isUsed());

        matchCards.touchMove(23);
        assertFalse(matchCards.getMatchingBoard().getCard(3,4).isUsed());
        matchCards.touchMove(22);
        assertTrue(matchCards.getMatchingBoard().getCard(3, 4).isUsed());
    }

    /**
     * Test IsMatched method in MatchingCards
     */
    @Test
    public void testIsMatched() {
        setUpCorrect();

        // Since its comparing to Prepos == -1
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 4));

        matchCards.touchMove(23);
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 3));
        assertTrue(matchCards.isMatched(3, 4));
    }
}
