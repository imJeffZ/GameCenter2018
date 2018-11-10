package fall18_207project.GameCenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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


public class ScoreBoardActivity extends Activity {
    public String currentAccount;
    public static String SCOREBOARD = "scoreBoard.ser";
    private ScoreBoard scoreBoard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        String accountName = getIntent().getExtras().getString("accountName");
        currentAccount = accountName;
        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.scoreBoardView);
        final List<Map<String, Object>> list = new ArrayList<>();
        getData(list, 0);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.scoreboard_item, new String[]{"user", "score"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);

        Spinner spinner = findViewById(R.id.spinner);
        String[] mItems = {"User Name: " + accountName, "3 X 3", "4 x 4", "5 X 5"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mItems);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                list.clear();
                getData(list, pos);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void getData(List<Map<String, Object>> list, int gameId) {

        String[] accountScore;
        String[] accountGame = {"3X3", "4X4", "5X5"};
        scoreBoard = new ScoreBoard();
        loadFromScoreBoard(SCOREBOARD);
        ArrayList<Integer> score = scoreBoard.getRankScorePerGame(gameId);
        ArrayList<String> user = scoreBoard.getRankUserNamePerGame(gameId);

        if (gameId == 0) {
            accountScore = AccountManager.accountMap.get(currentAccount).getScoreRecord();
            for (int i = 0; i < accountScore.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("user", accountGame[i]);
                map.put("score", accountScore[i]);
                list.add(map);
            }

        } else {

            for (int i = 0; i < user.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("user", user.get(i));
                map.put("score", score.get(i));
                list.add(map);
            }
        }
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
