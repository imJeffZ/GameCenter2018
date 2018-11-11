package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.Stack;

public abstract class Game implements Serializable {
    /**
     * The board being managed.
     */

    // Represents different game. 3x3 slidingTiles has different gameId than 4x4 slidingTile.
    protected int gameId;
    // This saveId is an unique identifier for a game. Two 3x3 slidingTiles does not have the same saveId.
    // Intending to use hashCode
    protected int saveId;
    /**
     * The board being managed.
     */
    protected Board board;
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Game && ((Game) obj).saveId == this.saveId;
    }

}
