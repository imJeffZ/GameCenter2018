package fall18_207project.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {
    /**
     * A really bad idea. Please change later.
     */
    public static String CURRENT_ACCOUNT = "";
    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    public static final String AUTO_SAVE_FILENAME = "auto_save.ser";
    /**
     * The board manager.
     */
    private SlidingTiles slidingTiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        add3ButtonListener();
        add4ButtonListener();
        add5ButtonListener();
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
                if (slidingTiles == null) {
                    makeAnotherToatCurrentMessage();
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
                if (slidingTiles == null) {
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

    private void makeAnotherToatCurrentMessage() {
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
    private void add3ButtonListener() {
        Button Button3 = findViewById(R.id.Button3);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingTiles = new SlidingTiles(3);
                switchToGame();
            }
        });
    }

    /**
     * Activate the 4x4 new game board.
     */
    private void add4ButtonListener() {
        Button Button4 = findViewById(R.id.Button4);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingTiles = new SlidingTiles(4);
                switchToGame();
            }
        });
    }

    /**
     * Activate the 5x5 new game board.
     */
    private void add5ButtonListener() {
        Button Button5 = findViewById(R.id.Button5);
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingTiles = new SlidingTiles(5);
                switchToGame();
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
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
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
                slidingTiles = (SlidingTiles) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Starting activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Starting activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Starting activity", "File contained unexpected data type: " + e.toString());
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
            outputStream.writeObject(slidingTiles);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
