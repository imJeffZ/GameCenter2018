package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Game2048 extends Game implements GameFeature, Cloneable {

    private final static int LEFT = 1;
    private final static int RIGHT = 2;
    private final static int UP = 3;
    private final static int DOWN = 4;
    private int score;
    private Stack<Board> boardStack;

    public Game2048() {
        super(); // Explicitly put here
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 16;
        Random r = new Random();
        int beginnum1 = (r.nextInt(2) + 1) * 2;
        int beginnum2 = (r.nextInt(2) + 1) * 2;

        tiles.add(new Tile(beginnum1 + 1));
        tiles.add(new Tile(beginnum2 + 1));
        Collections.shuffle(tiles);
        board = new Board(tiles, 4);
        this.endTime = 0;
        this.score = 0;
        this.boardStack = new Stack<>();
        boardStack.add(board);
    }


    @Override
    int calculateScore() {
        return score;
    }

    /**
     *
     *
     * @param direction
     * @return
     */
    @Override
    public boolean isValidTap(int direction) {
        boolean valid = false;
        int blankid = 25;
        if (direction == LEFT) {
            for (int i = 0; i < board.getNUM_COLS() - 1; i++) {
                for (int j = board.getNUM_ROWS() -1; j > 0; j--) {
                    if (board.getTile(i, j ).getId()!= blankid && board.getTile(i, j - 1).getId() == blankid) {
                        return true;
                    } else if ((board.getTile(i, j - 1)!= null && board.getTile(i, j - 1).getId() == board.getTile(i, j).getId())) {
                        return true;
                    }
                }

            }
        }

        if (direction == RIGHT) {
            for (int i = 0; i < board.getNUM_COLS() - 1; i++) {
                for (int j = 0; j < board.getNUM_ROWS() - 1; j++) {
                    if (board.getTile(i, j ).getId()!= blankid && board.getTile(i, j + 1).getId() == blankid) {
                        return true;
                    } else if (board.getTile(i, j + 1)!= null && board.getTile(i, j + 1).getId() == board.getTile(i, j).getId()) {
                        return true;
                    }
                }

            }
        }

        if (direction == DOWN) {
            for (int i = 0; i < board.getNUM_ROWS() - 1; i++) {
                for (int j = 0; j < board.getNUM_COLS() - 1; j++) {
                    if (board.getTile(j,i).getId()!= blankid &&board.getTile(j+1,i).getId() == blankid) {
                        return true;
                    } else if (board.getTile(j+1, i).getId() == board.getTile(j,i).getId()) {
                        return true;
                    }
                }

                }
            }



        if (direction == UP) {
            for (int i = 0; i < board.getNUM_ROWS() - 1; i++) {
                for (int j = board.getNUM_COLS() - 1; j > 0; j--) {
                    if (board.getTile(j,i).getId()!= blankid &board.getTile(j-1,i)!= null &&board.getTile(j-1,i).getId() == blankid) {
                        return true;
                    } else if (board.getTile(j-1, i).getId() == board.getTile(j,i).getId()) {
                        return true;
                    }
                }

            }

        }

    return valid;
    }

    @Override
    public void touchMove(int direction) {
        int blankid = 25;
        if (direction == LEFT) {
            for (int i = 0; i < board.getNUM_COLS() - 1; i++) {
                for (int j = board.getNUM_ROWS() -1; j > 0; j--) {
                    if (board.getTile(i, j - 1)!= null && board.getTile(i, j - 1).getId() == blankid) {
                            board.swapTiles(i, j, i, j - 1);
                    } else if ((board.getTile(i, j - 1)!= null && board.getTile(i, j - 1).getId() == board.getTile(i, j).getId())) {
                        int changedbackground = board.getTile(i, j - 1).getBackground() * 2;
                        board.getTiles()[i][j - 1] = new Tile(changedbackground);
                        board.getTiles()[i][j] = new Tile(blankid);
                        this.score += changedbackground;
                    }
                }
                for (int m = board.getNUM_ROWS() -1; m > 0; m--) {
                    if (board.getTile(i, m - 1)!= null && board.getTile(i, m - 1).getId() == blankid) {
                        board.swapTiles(i, m, i, m - 1);
                    }

                }
            }
        }


        if (direction == RIGHT) {
            for (int i = 0; i < board.getNUM_COLS() - 1; i++) {
                for (int j = 0; j < board.getNUM_ROWS() - 1; j++) {
                    if (board.getTile(i, j + 1)!= null && board.getTile(i, j + 1).getId() == blankid) {
                        board.swapTiles(i, j, i, j + 1);
                    } else if (board.getTile(i, j + 1)!= null && board.getTile(i, j + 1).getId() == board.getTile(i, j).getId()) {
                        int changedbackground = board.getTile(i, j + 1).getBackground() * 2;
                        board.getTiles()[i][j + 1] = new Tile(changedbackground);
                        board.getTiles()[i][j] = new Tile(blankid);
                        this.score += changedbackground;
                    }
                }
                for (int m = 0; m < board.getNUM_ROWS() - 1; m++) {
                    if (board.getTile(i, m - 1)!= null && board.getTile(i, m - 1).getId() == blankid) {
                        board.swapTiles(i, m, i, m - 1);
                    }

                }
            }
        }

        if (direction == DOWN) {
            for (int i = 0; i < board.getNUM_ROWS() - 1; i++) {
                for (int j = 0; j < board.getNUM_COLS() - 1; j++) {
                    if (board.getTile(j+1,i).getId() == blankid) {
                        board.swapTiles(j, i, j+1,i);
                    } else if (board.getTile(j+1, i).getId() == board.getTile(j,i).getId()) {
                        int changedbackground = board.getTile(j+1,i).getBackground() * 2;
                        board.getTiles()[j+1][i] = new Tile(changedbackground);
                        board.getTiles()[j][i] = new Tile(blankid);
                        this.score += changedbackground;
                    }
                }
                for (int m = 0; m < board.getNUM_ROWS() - 1; m++) {
                    if (board.getTile(m+1,i).getId() == blankid) {
                        board.swapTiles(m,i ,m+1,i);
                    }

                }
            }

        }

        if (direction == UP) {
            for (int i = 0; i < board.getNUM_ROWS() - 1; i++) {
                for (int j = board.getNUM_COLS() - 1; j > 0; j--) {
                    if (board.getTile(j-1,i)!= null &&board.getTile(j-1,i).getId() == blankid) {
                        board.swapTiles(j, i, j-1,i);
                    } else if (board.getTile(j-1, i).getId() == board.getTile(j,i).getId()) {
                        int changedbackground = board.getTile(j-1,i).getBackground() * 2;
                        board.getTiles()[j-1][i] = new Tile(changedbackground);
                        board.getTiles()[j][i] = new Tile(blankid);
                        this.score += changedbackground;
                    }
                }
                for (int m= board.getNUM_COLS() - 1; m >0; m++) {
                    if (board.getTile(i, m - 1).getId() == blankid) {
                        board.swapTiles(m,i ,m-1,i);
                    }

                }
            }

        }

    if (isValidTap(direction)){
        List<ArrayList<Integer>> blanktiles = new ArrayList<>();
        for (int i = 0; i< board.getNUM_COLS(); i++){
            for (int j= 0; j< board.getNUM_ROWS(); j++){
                if (board.getTile(i,j).getId() == blankid){
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
        int number = (rnd.nextInt(2) + 1) *2;
        board.getTiles()[blanktiles.get(position).get(0)][blanktiles.get(position).get(1)] = new Tile(number);

    }

    boardStack.push(board);


    }


    @Override
    public void undo() {
        if (!boardStack.isEmpty()){
            boardStack.pop();
        }
        if (! boardStack.isEmpty()){
           board = (Board) boardStack.pop();
        }


    }

    @Override
    public boolean isSolved() {
        Iterator<Tile> it = board.iterator();
        while (it.hasNext()) {
            int curbackground = it.next().getBackground();
            if (curbackground == 2048){
                return true;
            }
        }
        return false;
    }

    @Override
    public Board clone(){
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<Tile> tiles= new ArrayList<>();
        for (int i = 0; i< board.getNUM_COLS(); i++) {
            for (int j = 0; j < board.getNUM_ROWS(); j++) {
                tiles.add(board.getTile(i ,j));
            }
        }
        return new Board(tiles, board.getNUM_ROWS());

    }
}
