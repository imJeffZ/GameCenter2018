package fall18_207project.GameCenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTileStartingActivity extends AppCompatActivity implements
        MultiLoadStartActivity, MultiComplexityStartActivity, GameStartingActivity{
    private SlidingTileStartingController mController = new SlidingTileStartingController(SlidingTileStartingActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);

        addLoadGameButtonListener();
        addNewGameButtonListener();
        addLogOutButtonListener();
        addReturnToGameCenterListener();
        setUserTextView();
    }

    private void setUserTextView(){
        TextView account = findViewById(R.id.Hi_User);
            account.setText(mController.setUserTextViewTest());
    }

    public void showLoadDialog(){
        AlertDialog.Builder loadDialog =
                new AlertDialog.Builder(SlidingTileStartingActivity.this);
        loadDialog.setTitle("Load Game ").setMessage("Load From...");

        loadDialog.setNeutralButton("Load Saved game",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switchToSaveGames();
                    }
                });
        loadDialog.setNegativeButton("Load AutoSaved game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switchToAutoSaveGames();
            }
        });
        loadDialog.show();
    }

    public void showNewGameDialog(){
        AlertDialog.Builder newGameDialog =
                new AlertDialog.Builder(SlidingTileStartingActivity.this);
        newGameDialog.setTitle("New Game ").setMessage("Choose the complexity you want:");
        newGameDialog.setNeutralButton("3 x 3",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switchGameByComplexity(3);
                    }
                });
        newGameDialog.setNegativeButton("4 X 4",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switchGameByComplexity(4);
                    }
                });
        newGameDialog.setPositiveButton("5 X 5", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switchGameByComplexity(5);
            }
        });
        newGameDialog.show();
    }

    private void addNewGameButtonListener(){
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewGameDialog();
            }
        });
    }

    private void addLoadGameButtonListener(){
        Button Button4 = findViewById(R.id.loadGameButton);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadDialog();
            }
        });
    }

    /**
     * Log out of the current Account.
     */
    private void addLogOutButtonListener() {
        Button LogoutButton = findViewById(R.id.logOutBtn);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    private void addReturnToGameCenterListener() {
        Button returnToGameCenter = findViewById(R.id.button);
        returnToGameCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentre();
            }
        });
    }

    public void switchToGameCentre() {
        Intent backToGameCenter = new Intent(getApplicationContext(), GameCentreActivity.class);
        startActivity(backToGameCenter);
    }

    /**
     * Switch to the SlidingTileGameActivity view to play the game.
     */
    public void switchToSaveGames(){
        Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
        goToSavedGames.putExtra("saveType", "userSave");
        goToSavedGames.putExtra("gameType", "slidingTiles");
        startActivity(goToSavedGames);
    }

    public void switchToAutoSaveGames(){
        Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
        goToSavedGames.putExtra("saveType", "autoSave");
        goToSavedGames.putExtra("gameType", "slidingTiles");
        startActivity(goToSavedGames);
    }

    public void switchGameByComplexity(int num){
        switchToGame( mController.addGameInAcc(mController.createGame(num)).getSaveId());
    }

    public void switchToGame(String saveId) {
        Intent tmp = new Intent(this, SlidingTileGameActivity.class);
        tmp.putExtra("saveId", saveId);
        tmp.putExtra("saveType", "autoSave");
        startActivity(tmp);
    }

    public void switchToLogin() {
        mController.userSignOut();
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateCurrAccount();
        Intent goToCenter = new Intent(getApplicationContext(), GameCentreActivity.class);
        startActivity(goToCenter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateCurrAccount();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateCurrAccount();
    }

    private void updateCurrAccount() {
        mController.updateCurrAccount();
    }
}
