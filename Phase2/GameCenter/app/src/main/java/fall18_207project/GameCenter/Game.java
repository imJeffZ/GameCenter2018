package fall18_207project.GameCenter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.UUID;

/**
 * A Generic BoardGame class.
 */
public abstract class Game implements Serializable, GameFeature {

    // TODO: Make Game cloneable
    /***
     *  Represents different game. 3x3 slidingTiles has different gameId than 4x4 slidingTile.
     */
    protected int gameId;

    /***
     * This saveId is an unique identifier for a game. Two 3x3 slidingTiles does not have the same saveId.
     */
    protected final String saveId;
    protected int countMove;
    protected Stack<Integer> savedMove;

    protected LocalDateTime beginTime;
    /***
     * Record how long the timer has run for the game
     */
    protected long elapsedTime;
    /***
     *  Record how long the game takes to finish
     */
    protected long endTime;

    Game() {
        // Creates a universal unique id
        this.saveId = UUID.randomUUID().toString();
        this.countMove = 0;
        this.savedMove = new Stack<>();
        this.beginTime = LocalDateTime.now();
        this.elapsedTime = 0;
    }

    public int getGameId() {
        return this.gameId;
    }

    public String getSaveId() {
        return this.saveId;
    }

    int getCountMove() {
        return countMove;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * convert the beginTime to standard format and return it.
     *
     * @return return created time in standard format.
     */
    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(this.beginTime);
    }

    void updateElapsedTime(long newElapsedTime) {
        elapsedTime = newElapsedTime;
        endTime = newElapsedTime;
    }

    void resetElapsedTime() {
        elapsedTime = 0;
    }

    abstract Game reset();


    /**
     * reset CountMove to be 0
     */
    void resetCountMove() {
        this.countMove = 0;
    }

    abstract int calculateScore();

    /**
     * check if one position/move is valid
     *
     * @param position
     * @return if this position is valid tap
     */
    public abstract boolean isValidTap(int position);

    /**
     * check if this game still has other valid moves
     *
     * @return if there are more valid moves
     */
    public boolean hasValidMove() {
        return false;
    }

    /**
     * change current state based on the position input
     *
     * @param position
     */
    public abstract void touchMove(int position);

    /**
     * @return a string that represents current game
     */
    @Override
    public String toString() {
        return "Generic Game";
    }

    /**
     * check if 2 games are the same game and if its same game by checking saveID.
     *
     * @param obj
     * @return whether 2 games are the same game
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Game && ((Game) obj).saveId.equals(this.saveId);
    }
}
