package fall18_207project.GameCenter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Stack;
import java.util.UUID;

public abstract class Game implements Serializable, GameFeature {
    /**
     * A Generic BoardGame class.
     */

    // Represents different game. 3x3 slidingTiles has different gameId than 4x4 slidingTile.
    protected int gameId;
    // This saveId is an unique identifier for a game. Two 3x3 slidingTiles does not have the same saveId.
    // Intending to use hashCode
    protected final String saveId;
    protected int countMove;
    protected Stack<Integer> saveMove;
    protected long elapsedTime;
    // Record how long the game takes to finish
    // This endTime is not generalized into abstract Game class because the Timer awaits further modification.
    protected long endTime;
    protected LocalDateTime date;

    Game() {
        this.countMove = 0;
        this.elapsedTime = 0;
        this.saveMove = new Stack<>();
        // Creates a universal unique id
        this.saveId = UUID.randomUUID().toString();
        this.date = LocalDateTime.now();
    }

    int getCountMove() {
        return countMove;
    }

    void resetCountMove() {
        this.countMove = 0;
    }

    public int getGameId() {
        return this.gameId;
    }

    public String getSaveId() {
        return this.saveId;
    }

    // TODO: Implement reset
    abstract void reset();
    abstract int calculateScore();

    void updateElapsedTime(long newElapsedTime) {
        elapsedTime = newElapsedTime;
        endTime = newElapsedTime;
    }

    void resetElapsedTime() {
        elapsedTime = 0;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Return the current board.
     */

    @Override
    public String toString() {
        return "Generic Game";
    }

    /**
     * convert the date to standard format and return it.
     * @return return created time in standard format.
     */
    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = this.date.format(formatter);
        return formatDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Game && ((Game) obj).saveId.equals(this.saveId);
    }

    public abstract boolean isValidTap(int position);

    public abstract void touchMove(int position);

    public boolean hasVaildMove() {
        return false;
    }
}
