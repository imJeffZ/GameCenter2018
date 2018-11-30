package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Game2048 extends Game implements GameFeature, Cloneable {

    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int UP = 3;
    public final static int DOWN = 4;
    private final static int BLANK_ID = 25;
    private int score;

    private Stack<Game2048Board> boardStack;
    protected Game2048Board board;
    protected List<Game2048Tile> tiles;

    public Game2048() {
        super(); // Explicitly put here
        this.gameId = 7;

        tiles = new ArrayList<>();

        final int numTiles = 16;
        Random r = new Random();
        int beginnum1 = (r.nextInt(1) + 1) * 2;
        int beginnum2 = (r.nextInt(1) + 1) * 2;

        tiles.add(new Game2048Tile(beginnum1 - 1));
        tiles.add(new Game2048Tile(beginnum2 - 1));
        for (int tileNum = 0; tileNum < numTiles - 2; tileNum++) {
            tiles.add(new Game2048Tile(BLANK_ID - 1));
        }
        Collections.shuffle(tiles);

        board = new Game2048Board(tiles, 4);

        this.endTime = 0;
        this.score = 0;
        this.boardStack = new Stack<>();
    }


    // TODO: Implement reset
    @Override
    void reset() {
        return;
    }

    public List<Game2048Tile> cloneTiles() {
        List<Game2048Tile> returnTile = new ArrayList<>();
        for (Game2048Tile tile : tiles) {
            returnTile.add(tile);
        }
        return returnTile;
    }

    public static int getLEFT() {
        return LEFT;
    }

    public int getScore() {
        return score;
    }

    public Stack<Game2048Board> getBoardStack() {
        return boardStack;
    }

    public Game2048Board getBoard() {
        return board;
    }

    @Override
    int calculateScore() {
        if (!hasVaildMove()) {
            return 0;
        } else {
            return score;
        }
    }

    /**
     * @param direction
     * @return
     */
    @Override
    public boolean isValidTap(int direction) {
        return false;
    }

    public boolean hasVaildMove() {
        // TODO: code smell
        boolean valid = false;
        int blankid = 25;
        for (int i = 0; i < board.getNumOfColumns(); i++) {
            for (int j = 0; j < board.getNumOfRows(); j++) {
                if (i + 1 < board.getNumOfColumns()) {
                    if (board.getTile(i + 1, j).getId() == blankid || board.getTile(i + 1, j).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }

                if (i - 1 >= 0) {
                    if (board.getTile(i - 1, j).getId() == blankid || board.getTile(i - 1, j).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }

                if (j + 1 < board.getNumOfColumns()) {
                    if (board.getTile(i, j + 1).getId() == blankid || board.getTile(i, j + 1).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }

                if (j - 1 >= 0) {
                    if (board.getTile(i, j - 1).getId() == blankid || board.getTile(i, j - 1).getId() == board.getTile(i, j).getId()) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    private boolean leftShift(int row) {
        boolean check = false;
        for (int col = 0; col < board.getNumOfColumns() - 1; ++col) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row, col + 1).getId() != BLANK_ID) {
                board.swapTiles(row, col + 1, row, col);
                check = true;
            }
        }
        return check;
    }

    private boolean rightShift(int row) {
        boolean check = false;
        for (int col = board.getNumOfColumns() - 1; col > 0; --col) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row, col - 1).getId() != BLANK_ID) {
                board.swapTiles(row, col, row, col - 1);
                check = true;
            }
        }
        return check;
    }

    private boolean downShift(int col) {
        boolean check = false;
        for (int row = board.getNumOfRows() - 1; row > 0; --row) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row - 1, col).getId() != BLANK_ID) {
                board.swapTiles(row, col, row - 1, col);
                check = true;
            }
        }
        return check;
    }

    private boolean upShift(int col) {
        boolean check = false;
        for (int row = 0; row < board.getNumOfRows() - 1; ++row) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row + 1, col).getId() != BLANK_ID) {
                board.swapTiles(row, col, row + 1, col);
                check = true;
            }
        }
        return check;
    }

    @Override
    public void touchMove(int direction) {
        boolean check = false;
        boardStack.push((Game2048Board) board.clone());
        countMove++;

        int changedBackground, value;

        if (direction == LEFT) {
            for (int row = 0; row < board.getNumOfRows(); ++row) {
                // Shift everything left
                check = check | leftShift(row);
                // and again
                check = check | leftShift(row);
                // Merge
                for (int col = 0; col < board.getNumOfColumns() - 1; ++col) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row, col + 1).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row][col + 1] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                // left shift
                leftShift(row);
            }
        } else if (direction == RIGHT) {
            for (int row = 0; row < board.getNumOfRows(); ++row) {
                check = check | rightShift(row);
                check = check | rightShift(row);

                for (int col = board.getNumOfColumns() - 1; col > 0; --col) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row, col - 1).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row][col - 1] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                rightShift(row);
            }
        } else if (direction == DOWN) {
            for (int col = 0; col < board.getNumOfColumns(); ++col) {
                check = check | downShift(col);
                downShift(col);
                for (int row = board.getNumOfRows() - 1; row > 0; --row) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row - 1, col).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row - 1][col] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                downShift(col);
            }
        } else if (direction == UP) {
            for (int col = 0; col < board.getNumOfColumns(); ++col) {
                check = check | upShift(col);
                upShift(col);
                for (int row = 0; row < board.getNumOfRows() - 1; ++row) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row + 1, col).getId()) {
                        board.getTiles()[row][col] = new Game2048Tile(value * 2 - 1);
                        board.getTiles()[row + 1][col] = new Game2048Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                upShift(col);
            }
        }

        if (check) {
            List<ArrayList<Integer>> blanktiles = new ArrayList<>();
            for (int i = 0; i < board.getNumOfColumns(); i++) {
                for (int j = 0; j < board.getNumOfRows(); j++) {
                    if (board.getTile(i, j).getId() == BLANK_ID) {
                        ArrayList<Integer> coordinate = new ArrayList<>();
                        coordinate.add(i);
                        coordinate.add(j);
                        blanktiles.add(coordinate);
                    }
                }
            }

            int bound = blanktiles.size();
            Random rnd = new Random();
            int position = rnd.nextInt(bound);
            int position2 = rnd.nextInt(bound);
            int number = (rnd.nextInt(1) + 1) * 2;
            board.getTiles()[blanktiles.get(position).get(0)][blanktiles.get(position).get(1)] = new Game2048Tile(number - 1);
            board.swapTiles(blanktiles.get(position2).get(0), blanktiles.get(position2).get(1), blanktiles.get(position).get(0), blanktiles.get(position).get(1));
        }
        boardStack.push(board.clone());
    }

    public void undo() {
        if (!boardStack.isEmpty()) {
            boardStack.pop();
        }

        if (!boardStack.isEmpty()) {

            board = (Game2048Board) boardStack.pop();
        }
    }

    @Override
    public boolean isSolved() {
        boolean check = false;
        for (int i = 0; i < board.getNUM_COLS(); i++){
            for (int j = 0; j< board.getNUM_ROWS(); j++){
                //TODO change back
                if (board.getTile(i,j).getId() == 2048){
                    return true;
                }
            }
        }
        return check;
    }





}
