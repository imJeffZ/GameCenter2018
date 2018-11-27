package fall18_207project.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//test comment here
public class GameFinishActivity extends AppCompatActivity {
    public final static String SCOREBOARD = "scoreBoard.ser";
    public ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finish);
        addReturnButtonListener();
        scoreBoard = new ScoreBoard();
        loadFromScoreBoard(SCOREBOARD);
        saveToFile(LoginActivity.SAVE_ACCOUNT_DETAILS);

        TextView tvResult = findViewById(R.id.textView2);
        int score = getIntent().getExtras().getInt("score");
        String result = Integer.toString(score);
        tvResult.setText(result);
        int size = getIntent().getExtras().getInt("size");
        if (size == 1) {
            scoreBoard.updateScorePerGame(1, StartingActivity.CURRENT_ACCOUNT, score);
        } else if (size == 2) {
            scoreBoard.updateScorePerGame(2, StartingActivity.CURRENT_ACCOUNT, score);
        } else if (size == 3) {
            scoreBoard.updateScorePerGame(3, StartingActivity.CURRENT_ACCOUNT, score);
        }
        saveToScoreBoard(SCOREBOARD);
    }

    /**
     * click on anywhere of the board and it will return to starting activity/
     */
    private void addReturnButtonListener() {
        Button returnToScreen = findViewById(R.id.backToGamebtn);
        returnToScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToMainScreen = new Intent(getApplicationContext(), GameCentreActivity.class);
                startActivity(returnToMainScreen);
            }
        });
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(AccountManager.accountMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
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
            Log.e("GameFinish activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("GameFinish activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("GameFinish activity", "File contained unexpected data type: " + e.toString());
        }
    }

    public void saveToScoreBoard(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(scoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
