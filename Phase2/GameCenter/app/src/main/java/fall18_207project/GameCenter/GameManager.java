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

    boolean deleteGame(String saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId().equals(saveId)) {
                this.gameList.remove(game);
                return true;
            }
        }
        return false;
    }

    Game getGame(String saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId().equals(saveId)) {
                return game;
            }
        }
        return null;
    }

    boolean hasGame(String saveId) {
        for (Game game : this.gameList) {
            if (game.getSaveId().equals(saveId)) {
                return true;
            }
        }
        return false;
    }

    void clear() {
        this.gameList.clear();
    }
}