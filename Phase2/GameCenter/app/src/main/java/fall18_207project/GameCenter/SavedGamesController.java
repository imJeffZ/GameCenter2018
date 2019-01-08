package fall18_207project.GameCenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedGamesController {
    private GameManager gameManager;

    public void getData(List<Map<String, Object>> list, String saveType, String gameType, ArrayList<Game> gameList ) {
        Account currAccount = CurrentAccountController.getCurrAccount();

        if (currAccount != null) {
            if (saveType.equals("autoSave")) {
                gameManager = currAccount.getAutoSavedGames();
            } else {
                gameManager = currAccount.getUserSavedGames();
            }

                if("slidingTiles".equals(gameType)){
                    for(int i = 1; i <= 3; i++)
                        gameList.addAll(gameManager.getSavedGames(i));
                } else if("matchingCards".equals(gameType)){
                    for(int i = 4; i <= 6; i++)
                        gameList.addAll(gameManager.getSavedGames(i));
                } else if ("game2048".equals(gameType)){
                        gameList.addAll(gameManager.getSavedGames(7));
                }

            Collections.reverse(gameList);
            for (Game g: gameList) {
                Map<String, Object> map = new HashMap<>();
                map.put("gameId", g.gameId);
                map.put("saveId", g.getBeginTime());
                list.add(map);
            }
        }
    }
}
