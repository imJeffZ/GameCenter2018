package fall18_207project.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/***
 * A Global ScoreBoard for all users.
 *
 * TODO: Implement extra feature: Tap on a ScoreRecord to play the game again.
 */
class UserScoreBoard implements GameScoreBoard {

    // Map of <gameId> to list of ScoreEntry. ScoreEntry is Node of userName and Game.
    Map<Integer, ArrayList<GameRecord>> recordMap;

    UserScoreBoard() {
        recordMap = new HashMap<Integer, ArrayList<GameRecord>>();
    }


    public Map<Integer, ArrayList<GameRecord>> getRecordMap() {
        return recordMap;
    }

    public void updateScore(Game completedGame) {
        int gameId = completedGame.getGameId();
        if (!recordMap.containsKey(gameId)) {
            recordMap.put(gameId, new ArrayList<GameRecord>());
        }
        recordMap.get(gameId).add(new GameRecord(completedGame));
    }

    /***
     * Helper function to extract a list of GameRecords from recordMap.
     *
     * @param gameId
     * @return an ArrayList of sorted ScoreRecord with respect to the gameId.
     */
    ArrayList<GameRecord> getSortedGameRecord(int gameId) {
        ArrayList<GameRecord> gameRecordList = recordMap.get(gameId);
        Collections.sort(gameRecordList);
        return gameRecordList;
    }

    public ArrayList<Game> getSortedGames(int gameId) {
        ArrayList<GameRecord> gameRecordList = getSortedGameRecord(gameId);
        ArrayList<Game> gameList = new ArrayList<Game>();
        for (GameRecord gameRecord : gameRecordList) {
            gameList.add(gameRecord.getGame());
        }
        return gameList;
    }

    /**
     * Wrapper class to make each GameRecord comparable.
     */
    private class GameRecord implements Comparable<GameRecord>{
        private Game game;
        GameRecord(Game game) {
            this.game = game;
        }

        public Game getGame() {
            return this.game;
        }

        @Override
        public int compareTo(@NonNull GameRecord o) {
            return this.game.calculateScore() - o.getGame().calculateScore();
        }
    }
}
