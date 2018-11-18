package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/***
 * A generalized GameScoreBoard.
 *
 * TODO: Implement extra feature: Tap on a ScoreRecord to play the game again.
 */
class GameScoreBoard implements Serializable {

    // Map of <gameId> to list of ScoreEntry. ScoreEntry is Node of userName and Game.
    Map<Integer, ArrayList<ScoreRecord>> scoreMap;

    GameScoreBoard() {
        scoreMap = new HashMap<Integer, ArrayList<ScoreRecord>>();
    }

    void updateScore(String userName, Game completedGame) {
        int gameId = completedGame.getGameId();
        if (!scoreMap.containsKey(gameId)) {
            scoreMap.put(gameId, new ArrayList<ScoreRecord>());
        }
        scoreMap.get(gameId).add(new ScoreRecord(userName, completedGame));
    }

    /***
     * Helper function to extract a list of ScoreRecords from scoreMap.
     *
     * @param gameId
     * @return an ArrayList of sorted ScoreRecord with respect to the gameId.
     */
    ArrayList<ScoreRecord> getSortedScoreRecord(int gameId) {
        ArrayList<ScoreRecord> scoreRecordList = scoreMap.get(gameId);
        Collections.sort(scoreRecordList);
        return scoreRecordList;
    }

    /***
     *
     * Function to get a ArrayList of ranked userName for scoreBoard.
     * Parallel to getSortedGames() function.
     *
     * @param gameId The identifier for a specific type of Game. Like 3x3 SlidingTile, or 4x4 SlidingTile.
     * @return return a ArrayList of sorted username for the specific game type. Sorting based on score.
     *
     */
    ArrayList<String> getSortedUserNames(int gameId) {
        ArrayList<ScoreRecord> scoreRecordList = getSortedScoreRecord(gameId);
        ArrayList<String> userNameList = new ArrayList<String>();
        for (ScoreRecord scoreRecord : scoreRecordList) {
            userNameList.add(scoreRecord.getUserName());
        }
        return userNameList;
    }

    /***
     *
     * Function to get a ArrayList of ranked game for scoreBoard.
     * Parallel to getSortedUserNames() function.
     *
     * @param gameId The identifier for a specific type of Game. Like 3x3 SlidingTile, or 4x4 SlidingTile.
     * @return return a ArrayList of sorted Game for the specific game type. Sorting based on score.
     */
    ArrayList<Game> getSortedGames(int gameId) {
        ArrayList<ScoreRecord> scoreRecordList = getSortedScoreRecord(gameId);
        ArrayList<Game> gameList = new ArrayList<Game>();
        for (ScoreRecord scoreRecord : scoreRecordList) {
            gameList.add(scoreRecord.getGame());
        }
        return gameList;
    }

    private class ScoreRecord implements Comparable<ScoreRecord>{
        private String userName;
        private Game game;
        ScoreRecord(String userName, Game game) {
            this.userName = userName;
            this.game = game;
        }

        String getUserName() {
            return this.userName;
        }

        Game getGame() {
            return this.game;
        }

        @Override
        public int compareTo(@NonNull ScoreRecord o) {
            return this.game.calculateScore() - o.getGame().calculateScore();
        }
    }
}
