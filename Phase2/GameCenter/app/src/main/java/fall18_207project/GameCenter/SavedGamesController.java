package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedGamesController {
    public static String userEmail = "";
    private AccountManager accountManager;
    private GameManager gameManager;

   SavedGamesController(AccountManager acManager, String user){
       this.accountManager = acManager;
       this.userEmail = user;
   }

    public void getData(List<Map<String, Object>> list, String saveType, String gameType, ArrayList<Game> gameList ) {
        if (saveType.equals("autoSave")) {
            gameManager = accountManager.getAccount(userEmail).getAutoSavedGames();
        } else {
            gameManager = accountManager.getAccount(userEmail).getUserSavedGames();
        }
        ArrayList<Game> allGameList = gameManager.getAllGameList();
            for(Game game : allGameList){
                if("slidingTiles".equals(gameType)){
                    gameList.add(game);
                }
            else if("matchingCards".equals(gameType)){
                    gameList.add(game);
                }
           else if ("game2048".equals(gameType)){
                    gameList.add(game);
                }
            }
        Collections.reverse(gameList);
        for (Game g: gameList) {
            Map<String, Object> map = new HashMap<>();
            map.put("gameId", g.gameId);
            map.put("saveId", g.getTime());
            list.add(map);
        }
    }
}
