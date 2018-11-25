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

        assertFalse(matchCards.isMatchBomb());
        assertTrue(matchCards.getMatchingBoard().getCard(4,4).isBomb());
        assertFalse(matchCards.isSolved());

        matchCards.touchMove(24);
        assertTrue(matchCards.isMatchBomb());
        assertTrue(matchCards.isSolved());
        assertEquals(0, matchCards.calculateScore());
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
        assertEquals(-1, matchCards.getPrePos());
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(3, 2));

        assertEquals(-1, matchCards.getMatchingBoard().getCard(4, 3)
                .compareTo(matchCards.getMatchingBoard().getCard(4, 2)));
        matchCards.touchMove(23);
        assertFalse(matchCards.isMatched(3, 0));
        assertFalse(matchCards.isMatched(4, 3));
        assertTrue(matchCards.isMatched(4, 2));
    }

    /**
     * test getCountMove method. It should continuously incrementing by 1,
     * although cards not match
     */
    @Test
    public void testGetCountMove() {
        setUpCorrect();
        assertEquals(20, matchCards.getCountMove());

        // not a possible move but count move will ++
        matchCards.touchMove(14);
        assertEquals(21, matchCards.getCountMove());
        matchCards.touchMove(15);
        assertEquals(22, matchCards.getCountMove());

        matchCards.touchMove(22);
        matchCards.touchMove(23);
        assertEquals(24, matchCards.getCountMove());
    }

    /**
     * test isValidTap method.
     * There are two parts of it:
     * 1. When StartMode is true, any tap is not allowed
     * 2. When StartMode is false, only taps on unused card are allowed
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();

        assertFalse(matchCards.isValidTap(0));
        assertFalse(matchCards.isValidTap(24));
        assertFalse(matchCards.isValidTap(23));
        assertTrue(matchCards.isStartMode());

        matchCards.setStartMode();
        assertFalse(matchCards.isStartMode());
        assertTrue(matchCards.isValidTap(24));

        matchCards.touchMove(24);
        assertTrue(matchCards.isValidTap(24));
        assertTrue(matchCards.isValidTap(23));
        assertFalse(matchCards.isValidTap(0));
    }

    /**
     * Test isUp method correctly shows whether a card is up or not.
     */
    @Test
    public void testIsUp() {
        setUpCorrect();

        assertEquals(matchCards.getMatchingBoard().getCard(4, 3).isUp(),
                matchCards.getMatchingBoard().getCard(4, 2).isUp());
        assertFalse(matchCards.getMatchingBoard().getCard(4, 3).isUp());

        matchCards.touchMove(23);
        assertNotEquals(matchCards.getMatchingBoard().getCard(4, 3).isUp(),
                matchCards.getMatchingBoard().getCard(4, 2).isUp());
        assertTrue(matchCards.getMatchingBoard().getCard(4, 3).isUp());
        assertFalse(matchCards.getMatchingBoard().getCard(4, 2).isUp());

        matchCards.touchMove(22);
        assertTrue(matchCards.getMatchingBoard().getCard(4, 3).isUp());
        assertTrue(matchCards.getMatchingBoard().getCard(4, 2).isUp());
    }

    /**
     * Test the card has right backID
     */
    @Test
    public void testBackId() {
        setUpCorrect();

        assertEquals(R.drawable.p1, matchCards.getMatchingBoard().getCard(0, 0).getBackId());
        assertEquals(R.drawable.p24, matchCards.getMatchingBoard().getCard(4, 3).getBackId());
        assertEquals(R.drawable.bomb, matchCards.getMatchingBoard().getCard(4, 4).getBackId());
    }
}
