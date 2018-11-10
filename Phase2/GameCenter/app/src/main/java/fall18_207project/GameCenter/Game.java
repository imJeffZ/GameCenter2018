package fall18_207project.GameCenter;

import java.util.Stack;

class Game {
    /**
     * The board being managed.
     */

    // Represents different game. 3x3 slidingTiles has different gameId than 4x4 slidingTile.
    protected int gameId;
    protected int saveId;
    protected Board board;
    protected int countMove;
    protected Stack<Integer> saveMove;
    protected long elapsedTime;

    Game() {
        countMove = 0;
        elapsedTime = 0;
        saveMove = new Stack<>();
    }

    @Override
    public String toString() {
        return "Generic Game";
    }
}
