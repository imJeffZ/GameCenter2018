package fall18_207project.GameCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    Intent tmp1 = new Intent(UserHistoryActivity.this, GameCentreActivity.class);
                    startActivity(tmp1);
                    break;
                case R.id.navigation_user_history:
                    break;
                case R.id.navigation_global_scoreboard:
                    Intent tmp2 = new Intent(UserHistoryActivity.this, GlobalScoreBoardActivity.class);
                    startActivity(tmp2);
                    break;
            }
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readAccountManagerFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);


        // TODO: Make this only show specific type of games by utilizing the getUserScoreBoard().getSortedGames(Game id) method
        setContentView(R.layout.activity_user_history);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.historyView);
        final List<Map<String, Object>> list = new ArrayList<>();
        final ArrayList<Game> finalGameList = new ArrayList<>();
        getData(list, finalGameList,0);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.user_history_item, new String[]{"gameId", "score"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);

        Spinner spinner = findViewById(R.id.spinner);
        String[] mItems = {"ST 3 X 3", "ST 4 x 4", "ST 5 X 5", "MC 4 x 4", "MC 5 x 5", "MC 6 x 6", "2048"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                list.clear();
                finalGameList.clear();
                getData(list, finalGameList,pos+1);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        scoreBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game selectedGame = finalGameList.get(position);
                selectedGame.reset();
                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(selectedGame);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
                int i = selectedGame.getGameId();
                goToDifferentGames(i, selectedGame);

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

    private void getData(List<Map<String, Object>> list,  ArrayList<Game> playGame, int id) {
        ArrayList<Game> gameList = accountManager.getAccount(userEmail).getUserScoreBoard().getAllGameList();
        for (int i = 0; i < gameList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            if (gameList.get(i).gameId ==id){
                playGame.add(gameList.get(i));
                map.put("gameId", gameList.get(i).saveId);
                map.put("score",  gameList.get(i).calculateScore());
                list.add(map);
            }
        }
    }

    private void goToDifferentGames(int id, Game selectedGame){

        Intent goToGame = id <= 3? new Intent(getApplicationContext(), GameActivity.class):
                id <= 6? new Intent(getApplicationContext(), MatchingCardsGameActivity.class):
                        new Intent(getApplicationContext(), Game2048Activity.class);
        goToGame.putExtra("saveType", "userHistory");
        goToGame.putExtra("saveId", selectedGame.getSaveId());
        startActivity(goToGame);
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