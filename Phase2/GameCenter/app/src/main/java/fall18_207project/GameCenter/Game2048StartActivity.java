package fall18_207project.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game2048StartActivity extends AppCompatActivity {
    public static String CURRENT_ACCOUNT = "";
    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_2048_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_2048_file_tmp.ser";
    public static final String AUTO_SAVE_FILENAME = "auto_save_2048.ser";
    /**
     * The board manager.
     */
    private Game2048 game2048;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_game2048_starting);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        add4ButtonListener();
        addLogOutButtonListener();
        addReturnToGameCenterListener();
        TextView account = findViewById(R.id.Hiuser);
        account.setText("Hi, " + AccountManager.accountMap.get(CURRENT_ACCOUNT).getUserName());
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(CURRENT_ACCOUNT + AUTO_SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                if (game2048 == null) {
                    makeAnotherToastCurrentMessage();
                    return;
                }
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(CURRENT_ACCOUNT + SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                if (game2048 == null) {
                    makeToastForLoadGame();
                    return;
                }
                loadFromFile(CURRENT_ACCOUNT + SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    private void makeToastForLoadGame() {
        Toast.makeText(this, "No Saved Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    private void makeAnotherToastCurrentMessage() {
        Toast.makeText(this, "No current Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(CURRENT_ACCOUNT + SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the 3x3 new game Board.
     */
    private void add4ButtonListener() {
        Button Button4 = findViewById(R.id.Button4);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game2048 = new Game2048();
                switchToGame();
            }
        });
    }

    /**
     * Activate the 4x4 new game board.
     //     */


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
                Intent backToGameCenter = new Intent(getApplicationContext(), GameCentreActivity.class);
                startActivity(backToGameCenter);
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, Game2048Activity.class);
        saveToFile(Game2048StartActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    private void switchToLogin() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);

    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                game2048 = (Game2048) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Game2048 activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game2048t activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Game2048 activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(game2048);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
