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
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHistoryActivity extends Activity {

    public static String userEmail = "";
    private AccountManager accountManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);

        ArrayList<Game> gameList = new ArrayList<>();

        // TODO: Make this only show specific type of games
        for (Game g : accountManager.getAccount(userEmail).getAutoSavedGames().getAllGameList()) {
            if (g.isSolved()) {
                gameList.add(g);
            }
        }
//        gameList = accountManager.getAccount(userEmail).getAutoSavedGames().getAllGameList();
        Collections.reverse(gameList);
        setContentView(R.layout.activity_user_history);

        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.historyView);
        final List<Map<String, Object>> list = new ArrayList<>();
        getData(list, gameList);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.user_history_item, new String[]{"gameId", "score"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);

        final ArrayList<Game> finalGameList = gameList;
        scoreBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Need further generalization in GameActivity.
                Game selectedGame = finalGameList.get(position);
                selectedGame.reset();
                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(selectedGame);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);

                Intent goToGame = new Intent(getApplicationContext(), GameActivity.class);
                goToGame.putExtra("saveType", "autoSave");
                goToGame.putExtra("saveId", selectedGame.getSaveId());
                startActivity(goToGame);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    private void getData(List<Map<String, Object>> list, ArrayList<Game> gameArrayList) {

        for (Game g: gameArrayList) {
            Map<String, Object> map = new HashMap<>();
            map.put("gameId", g.gameId);
            map.put("score", g.calculateScore());
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
            Log.e("UserHistory activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("UserHistory activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("UserHistory activity", "File contained unexpected data type: " + e.toString());
        }
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}