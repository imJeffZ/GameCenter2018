package fall18_207project.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameCentreActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    break;
                case R.id.navigation_user_history:
                    Intent tmp2 = new Intent(GameCentreActivity.this, UserHistoryActivity.class);
                    startActivity(tmp2);
                    break;
                case R.id.navigation_global_scoreboard:
                    Intent tmp3 = new Intent(GameCentreActivity.this, ScoreBoardActivity.class);
                    startActivity(tmp3);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        addGameButtonListener();
    }

    private void addGameButtonListener() {

        ImageButton ButtonGame = findViewById(R.id.SlidingTileGame);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    private void switchToGame() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }

}
