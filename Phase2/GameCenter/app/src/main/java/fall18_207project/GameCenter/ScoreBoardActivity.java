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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ScoreBoardActivity extends Activity {
    public static String currentAccount;
    public static String SCOREBOARD = "scoreBoard.ser";
    private ScoreBoard scoreBoard;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    Intent tmp1 = new Intent(ScoreBoardActivity.this, GameCentreActivity.class);
                    startActivity(tmp1);
                    break;
                case R.id.navigation_user_history:
                    Intent tmp2 = new Intent(ScoreBoardActivity.this, UserHistoryActivity.class);
                    startActivity(tmp2);
                    break;
                case R.id.navigation_global_scoreboard:
                   break;
            }
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        //String accountEmail = Objects.requireNonNull(getIntent().getExtras()).getString("accountEmail");
        //currentAccount = accountEmail;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.scoreBoardView);
        final List<Map<String, Object>> list = new ArrayList<>();
        getData(list, 1);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.scoreboard_item, new String[]{"user", "score"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);

        Spinner spinner = findViewById(R.id.spinner);
        String[] mItems = {"3 X 3", "4 x 4", "5 X 5"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                    list.clear();
                    getData(list, pos+1);
                    adapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void getData(List<Map<String, Object>> list, int gameId) {

        scoreBoard = new ScoreBoard();
        loadFromScoreBoard(SCOREBOARD);
        ArrayList<Integer> score = scoreBoard.getRankScorePerGame(gameId);
        ArrayList<String> user = scoreBoard.getRankUserNamePerGame(gameId);

            for (int i = 0; i < user.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("user", user.get(i));
                map.put("score", score.get(i));
                list.add(map);
            }
//        }
    }

    private void loadFromScoreBoard(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreBoard = (ScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("ScoreBoard activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("ScoreBoard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("ScoreBoard activity", "File contained unexpected data type: " + e.toString());
        }
    }



}
