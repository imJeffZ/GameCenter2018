package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * This is the game history of Account. It stores all the games that the user saved, but have not finished.
 * For finished games, visit ScoreBoard.
 */
public class GameManager implements Serializable {

    ArrayList<Game> gameList;
    private Account gameAccount;
    public GameManager() {
        this.gameList = new ArrayList<Game>();
    }

    private ArrayList<Game> getGameList() {
        return this.gameList;
    }

    void addGame(Game newGame) {
        this.gameList.add(newGame);
    }

    boolean deleteGame(int saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId() == saveId) {
                this.gameList.remove(game);
                return true;
            }
        }
        return false;
    }

    Game getGame(int saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId() == saveId) {
                return game;
            }
        }
        return null;
    }

    boolean hasGame(int saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId() == saveId) {
                return true;
            }
        }
        return false;
    }

    void clear() {
        this.gameList.clear();
    }
}