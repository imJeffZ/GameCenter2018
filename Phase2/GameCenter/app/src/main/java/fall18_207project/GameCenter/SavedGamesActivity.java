package fall18_207project.GameCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedGamesActivity extends Activity {

    public static String userEmail = "";
    private AccountManager accountManager;
    private GameManager gameManager;
    private String gameType = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
        ArrayList<Game> allGameList = new ArrayList<>();
        ArrayList<Game> gameList = new ArrayList<>();

        if (getIntent().getStringExtra("saveType").equals("autoSave")) {
            gameManager = accountManager.getAccount(userEmail).getAutoSavedGames();
        } else {
            gameManager = accountManager.getAccount(userEmail).getUserSavedGames();
        }
        // TODO: Marvel Make this only show specific type of games
        allGameList = gameManager.getAllGameList();
        if (getIntent().getStringExtra("gameType") != null) {
            gameType = getIntent().getStringExtra("gameType");
        }
        if("slidingTiles".equals(gameType)){
            for(Game game : allGameList){
                if(game.gameId == 1 || game.gameId == 2 || game.gameId == 3){
                    gameList.add(game);
                }
            }
        }else if("matchingCards".equals(gameType)){
            for(Game game : allGameList) {
                if (game.gameId == 4 || game.gameId == 5 || game.gameId == 6) {
                    gameList.add(game);
                }
            }
        }else if ("game2048".equals(gameType)){
            for(Game game : allGameList) {
                if (game.gameId == 7) {
                    gameList.add(game);
                }
            }
        }
        Collections.reverse(gameList);
        setContentView(R.layout.activity_saved_games);

        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.historyView);
        final List<Map<String, Object>> list = new ArrayList<>();
        getData(list, gameList);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.user_history_item, new String[]{"gameId", "saveId"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);

        final ArrayList<Game> finalGameList = gameList;
        scoreBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Need further generalization in SlidingTileGameActivity.
                Game selectedGame = finalGameList.get(position);
                if("slidingTiles".equals(gameType)) {
                    Intent goToGame = new Intent(getApplicationContext(), SlidingTileGameActivity.class);
                    goToGame.putExtra("saveType", getIntent().getStringExtra("saveType"));
                    goToGame.putExtra("saveId", selectedGame.getSaveId());
                    startActivity(goToGame);
                }else if("matchingCards".equals(gameType)) {
                    Intent goToGame = new Intent(getApplicationContext(), MatchingCardsGameActivity.class);
                    goToGame.putExtra("saveType", getIntent().getStringExtra("saveType"));
                    goToGame.putExtra("saveId", selectedGame.getSaveId());
                    startActivity(goToGame);
                }else if("game2048".equals(gameType)) {
                    Intent goToGame = new Intent(getApplicationContext(), Game2048Activity.class);
                    goToGame.putExtra("saveType", getIntent().getStringExtra("saveType"));
                    goToGame.putExtra("saveId", selectedGame.getSaveId());
                    startActivity(goToGame);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    private void getData(List<Map<String, Object>> list, ArrayList<Game> gameArrayList) {

        for (Game g: gameArrayList) {
            Map<String, Object> map = new HashMap<>();
            map.put("gameId", g.gameId);
            map.put("saveId", g.saveId);
            list.add(map);
        }
    }


    private void readAccountManagerFromSer(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("SavedGames activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("SavedGames activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("SavedGames activity", "File contained unexpected data type: " + e.toString());
        }
    }

}