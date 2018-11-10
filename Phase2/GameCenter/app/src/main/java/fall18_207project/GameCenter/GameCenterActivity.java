package fall18_207project.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameCenterActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    public static String CURRENT_ACCOUNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        addGameButtonListener();
        addScoreBoardButtonListener();
        addLogOutButtonListener();
        TextView userName = findViewById(R.id.Hiuser);
        userName.setText("Hi, " + CURRENT_ACCOUNT);
    }

    /**
     * jump to starting Activity.
     */
    private void addGameButtonListener() {
        Button ButtonGame = findViewById(R.id.ButtonGame);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     *  logout of the current account.
     */
    private void addLogOutButtonListener(){
        Button LogoutButton = findViewById(R.id.logOutBtn);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });

    }

    /**
     * Activate the ScoreBoard.
     */
    private void addScoreBoardButtonListener() {
        Button ButtonScore = findViewById(R.id.ButtonScore);
        ButtonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreBoard();
            }
        });
    }

    private void switchToGame() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }
    private void switchToScoreBoard() {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tmp.putExtra("accountName", CURRENT_ACCOUNT);
        startActivity(tmp);
    }
    private  void switchToLogin(){
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);

    }
}
