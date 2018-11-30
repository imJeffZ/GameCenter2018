package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHistoryController {
    public static String userEmail;
    private AccountManager accountManager;
    private List<Map<String, Object>> list;
    private ArrayList<Game> playGame;

    UserHistoryController(AccountManager accManager, String user){
         this.accountManager = accManager;
         this.userEmail = user;
    }

    public void getData(List<Map<String, Object>> list, ArrayList<Game> playGame, int id) {
        ArrayList<Game> gameList = accountManager.getAccount(userEmail).getUserScoreBoard().getAllGameList();
        for (int i = 0; i < gameList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            if (gameList.get(i).gameId ==id){
                playGame.add(gameList.get(i));
                map.put("gameId", gameList.get(i).getTime());
                map.put("score",  gameList.get(i).calculateScore());
                list.add(map);
            }
        }
       this.list = list;
        this.playGame = playGame;
    }

    public void addAutoSaveGame(Game selectedGame){
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame(selectedGame);
    }

    public List<Map<String, Object>> getList(){
        return list;
    }

    public ArrayList<Game> getPlayGame() {
        return playGame;
    }
}
