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
    private Stack<Board> boardStack;
    private Board board;

    public Game2048() {
        super(); // Explicitly put here
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 16;
        Random r = new Random();
        int beginnum1 = (r.nextInt(1) + 1) * 2;
        int beginnum2 = (r.nextInt(1) + 1) * 2;

        tiles.add(new Tile(beginnum1 + 1));
        tiles.add(new Tile(beginnum2 + 1));
        for (int tileNum = 0; tileNum < numTiles - 2; tileNum++) {
            tiles.add(new Tile(24));
        }
        Collections.shuffle(tiles);
        board = new Board(tiles, 4);
        this.endTime = 0;
        this.score = 0;
        this.boardStack = new Stack<>();
        boardStack.add(board);
    }

    public static int getLEFT() {
        return LEFT;
    }

    public int getScore() {
        return score;
    }

    public Stack<Board> getBoardStack() {
        return boardStack;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    int calculateScore() {
        return score;
    }

    /**
     * @param direction
     * @return
     */
    @Override
    public boolean isValidTap(int direction) {
        return true;


    }


    public boolean hasVaildMove() {
        boolean valid = false;
        int blankid = 25;
        for (int i = 0; i < board.getNUM_COLS(); i++) {
            for (int j = 0; j < board.getNUM_ROWS(); j++) {
                if (i + 1 < board.getNUM_COLS()) {
                    if (board.getTile(i + 1, j).getId() == blankid || board.getTile(i + 1, j).getId() == board.getTile(i, j).getId()) {
                        return true;
                    }
                }
                if (i - 1 >= 0) {
                    if (board.getTile(i - 1, j).getId() == blankid || board.getTile(i - 1, j).getId() == board.getTile(i, j).getId()) {
                        return true;
                    }
                }

                if (j + 1 < board.getNUM_COLS()) {
                    if (board.getTile(i, j + 1).getId() == blankid || board.getTile(i, j + 1).getId() == board.getTile(i, j).getId()) {
                        return true;
                    }
                }

                if (j - 1 >= 0) {
                    if (board.getTile(i, j - 1).getId() == blankid || board.getTile(i, j - 1).getId() == board.getTile(i, j).getId()) {
                        return true;
                    }
                }

            }
        }
        return valid;

    }

    private boolean leftShift(int row) {
        boolean check = false;
        for (int col = 0; col < board.getNUM_COLS() - 1; ++col) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row, col + 1).getId() != BLANK_ID) {
                board.swapTiles(row, col + 1, row, col);
                check = true;
            }
        }
        return check;
    }

    private boolean rightShift(int row) {
        boolean check = false;
        for (int col = board.getNUM_COLS() - 1; col > 0; --col) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row, col - 1).getId() != BLANK_ID) {
                board.swapTiles(row, col, row, col - 1);
                check = true;
            }
        }
        return check;
    }

    private boolean downShift(int col) {
        boolean check = false;
        for (int row = board.getNUM_ROWS() - 1; row > 0; --row) {
            if (board.getTile(row, col).getId() == BLANK_ID && board.getTile(row - 1, col).getId() != BLANK_ID) {
                board.swapTiles(row, col, row - 1, col);
                check = true;
            }
        }
        return check;
    }

    private boolean upShift(int col) {
        boolean check = false;
        for (int row = 0; row < board.getNUM_ROWS() - 1; ++row) {
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
        int counterlr = 0;
        int counterud = 0;
        int boundlr = 0;
        int bourdud = 0;

        int changedBackground, value;

        if (direction == LEFT) {
            for (int row = 0; row < board.getNUM_ROWS(); ++row) {
                // Shift everything left
                check = check | leftShift(row);
                // and again
                check = check | leftShift(row);
                // Merge
                for (int col = 0; col < board.getNUM_COLS() - 1; ++col) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row, col + 1).getId()) {
                        board.getTiles()[row][col] = new Tile(value * 2 - 1);
                        board.getTiles()[row][col + 1] = new Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                // left shift
                leftShift(row);
            }
        } else if (direction == RIGHT) {
            for (int row = 0; row < board.getNUM_ROWS(); ++row) {
                check = check | rightShift(row);
                check = check | rightShift(row);

                for (int col = board.getNUM_COLS() - 1; col > 0; --col) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row, col - 1).getId()) {
                        board.getTiles()[row][col] = new Tile(value * 2 - 1);
                        board.getTiles()[row][col - 1] = new Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                rightShift(row);
            }
        } else if (direction == DOWN) {
            for (int col = 0; col < board.getNUM_COLS(); ++col) {
                check = check | downShift(col);
                downShift(col);
                for (int row = board.getNUM_ROWS() - 1; row > 0; --row) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row - 1, col).getId()) {
                        board.getTiles()[row][col] = new Tile(value * 2 - 1);
                        board.getTiles()[row - 1][col] = new Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                downShift(col);
            }
        } else if (direction == UP) {
            for (int col = 0; col < board.getNUM_COLS(); ++col) {
                check = check | upShift(col);
                upShift(col);
                for (int row = 0; row < board.getNUM_ROWS() - 1; ++row) {
                    if ((value = board.getTile(row, col).getId()) != BLANK_ID && board.getTile(row, col).getId() == board.getTile(row + 1, col).getId()) {
                        board.getTiles()[row][col] = new Tile(value * 2 - 1);
                        board.getTiles()[row + 1][col] = new Tile(BLANK_ID - 1);
                        check = true;
                        this.score += (value * 2);
                    }
                }
                upShift(col);
            }
        }

        if (check) {
            List<ArrayList<Integer>> blanktiles = new ArrayList<>();
            for (int i = 0; i < board.getNUM_COLS(); i++) {
                for (int j = 0; j < board.getNUM_ROWS(); j++) {
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
            board.getTiles()[blanktiles.get(position).get(0)][blanktiles.get(position).get(1)] = new Tile(number - 1);
            board.swapTiles(blanktiles.get(position2).get(0), blanktiles.get(position2).get(1), blanktiles.get(position).get(0), blanktiles.get(position).get(1));
        }

        boardStack.push(board.clone());

    }


    public void undo() {
        if (!boardStack.isEmpty()) {

            boardStack.pop();
        }
        if (!boardStack.isEmpty()) {
            board = (Board) boardStack.pop();


        }

    }

    @Override
    public boolean isSolved() {
        Iterator<Tile> it = board.iterator();
        while (it.hasNext()) {
            int curbackground = it.next().getId();
            if (curbackground == 2048 - 1) {
                return true;
            }
        }
        return false;
    }


}

