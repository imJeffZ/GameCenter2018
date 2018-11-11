package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.Stack;

public abstract class Game implements Serializable {
    /**
     * The board being managed.
     */

    // Represents different game. 3x3 slidingTiles has different gameId than 4x4 slidingTile.
//    protected int gameId;
//    protected int saveId;
//    protected Board board;
    protected int countMove;
    protected Stack<Integer> saveMove;
    protected long elapsedTime;

    Game() {
        this.countMove = 0;
        this.elapsedTime = 0;
        this.saveMove = new Stack<>();
    }

    @Override
    public String toString() {
        return "Generic Game";
    }
}
