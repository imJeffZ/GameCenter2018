package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Cloneable, Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    private int NUM_COLS = 4;


    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public Board(List<Tile> tiles, int size) {
        this.NUM_COLS = size;
        this.NUM_ROWS = size;
        this.tiles = new Tile[size][size];

        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != this.getNUM_COLS(); row++) {
            for (int col = 0; col != this.getNUM_COLS(); col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    int getNUM_COLS() {
        return NUM_COLS;
    }

    int getNUM_ROWS() {
        return NUM_ROWS;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    Tile[][] getTiles(){
        return this.tiles;
    }
    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */

    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile t1 = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = t1;
        setChanged();
        notifyObservers();
    }

    @NonNull
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * The iterator class for Board's iterator.
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The position
         */
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position != NUM_ROWS * NUM_COLS;
        }

        @Override
        public Tile next() {
            int row = position / NUM_ROWS;
            int col = position % NUM_COLS;
            Tile nextTile = tiles[row][col];
            position++;
            return nextTile;
        }
    }

    public Board clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < this.getNUM_COLS(); i++) {
            for (int j = 0; j < this.getNUM_ROWS(); j++) {
                tiles.add(this.getTile(i, j));
            }
        }

       Board board1 = new Board(tiles, this.getNUM_ROWS());
        return board1;

    }
}
