package fall18_207project.GameCenter;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedGamesActivity extends Activity {

    private String gameType;
    private  SavedGamesController mController = new SavedGamesController();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("gameType") != null) {
            gameType = getIntent().getStringExtra("gameType");
        }
        setContentView(R.layout.activity_saved_games);
        setGameListView();
    }


    public void setGameListView(){
        ListView scoreBoardView;
        scoreBoardView = findViewById(R.id.historyView);
        final List<Map<String, Object>> list = new ArrayList<>();
        final ArrayList<Game> gameList = new ArrayList<>();
        mController.getData(list, getIntent().getStringExtra("saveType"), gameType, gameList);
        final SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.user_history_item, new String[]{"gameId", "saveId"},
                new int[]{R.id.user, R.id.score});
        scoreBoardView.setAdapter(adapter);
        scoreBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("slidingTiles".equals(gameType)) {
                    Intent goToGame = new Intent(getApplicationContext(), SlidingTileGameActivity.class);
                    switchToGame(goToGame, gameList, position);
                }else if("matchingCards".equals(gameType)) {
                    Intent goToGame = new Intent(getApplicationContext(), MatchingCardsGameActivity.class);
                    switchToGame(goToGame, gameList, position);
                }else if("game2048".equals(gameType)) {
                    Intent goToGame = new Intent(getApplicationContext(), Game2048Activity.class);
                    switchToGame(goToGame, gameList, position);
                }
            }
        });
    }

   private void switchToGame(Intent goToGame, ArrayList<Game> gameList, int position){
       goToGame.putExtra("saveType", getIntent().getStringExtra("saveType"));
       goToGame.putExtra("saveId", gameList.get(position).getSaveId());
       startActivity(goToGame);
   }
}