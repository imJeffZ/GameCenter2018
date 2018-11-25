package fall18_207project.GameCenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SlidingTile5Test {
    private SlidingTiles slidingTile;

    /**
     * Create a solved board, also means a list of tiles in order as their id.
     * @return a list of tiles in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 5 * 5;
        for (int tileNum = 0; tileNum != numTiles - 1; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }
        tiles.add(new Tile(24));
        return tiles;
    }

    /**
     * Set up the SlidingTile so that we can use it when testing. Also, it's a solved board!
     */
    private void setUpCorrect() {
        slidingTile = new SlidingTiles(5);
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles, 5);
        for (int row = 0; row != 5; row++) {
            for (int col = 0; col != 5; col++) {
                slidingTile.board = board;
            }
        }
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingTile.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();

        assertEquals(true, slidingTile.isSolved());
        swapFirstTwoTiles();
        assertEquals(false, slidingTile.isSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();

        assertEquals(1, slidingTile.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingTile.getBoard().getTile(0, 1).getId());
        slidingTile.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTile.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingTile.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test if swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();

        assertEquals(24, slidingTile.getBoard().getTile(4, 3).getId());
        assertEquals(25, slidingTile.getBoard().getTile(4, 4).getId());
        slidingTile.getBoard().swapTiles(4, 4, 4, 3);
        assertEquals(25, slidingTile.getBoard().getTile(4, 3).getId());
        assertEquals(24, slidingTile.getBoard().getTile(4, 4).getId());
    }

    /**
     * Test isValidTap method.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();

        assertEquals(false, slidingTile.isValidTap(0));
        assertEquals(true, slidingTile.isValidTap(19));
        assertEquals(true, slidingTile.isValidTap(23));
        assertEquals(false, slidingTile.isValidTap(14));
        assertEquals(false, slidingTile.isValidTap(22));
    }

    /**
     *  Test touchMove method and undo method.
     */
    @Test
    public void testTouchMoveAndUndo() {
        setUpCorrect();
        Tile blankTile = new Tile(24);
        slidingTile.touchMove(23);

        assertEquals(false, slidingTile.isSolved());
        assertEquals(true, slidingTile.isValidTap(22));
        slidingTile.touchMove(22);
        assertEquals(true, slidingTile.board.getTile(4, 2).getId() == 25);
        slidingTile.undo();
        assertEquals(false, slidingTile.board.getTile(4,2).getId() == 25);
        assertEquals(true, slidingTile.board.getTile(4,3).getId() == 25);
        slidingTile.undo();
        assertEquals(false, slidingTile.board.getTile(4,2).getId() == 25);
        assertEquals(true, slidingTile.board.getTile(4,4).getId() == 25);

        // It's weird to test isSolved here
        // because it's illegal to keep playing the game after it's solved(we setUpCorrect at the begining)
        // when you are actually playing the game, but just make sure it's still working.
        slidingTile.isSolved();
    }
}
