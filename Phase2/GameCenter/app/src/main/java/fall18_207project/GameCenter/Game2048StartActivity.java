package fall18_207project.GameCenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game2048StartActivity extends AppCompatActivity implements
        MultiLoadStartActivity, GameStartingActivity{
    private FirebaseAuth firebaseAuth;
    public static String userEmail = "";
    private AccountManager accountManager;
//    private String userEmail;
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
//    private Game2048 game2048;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
        //saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_game2048_starting);
        firebaseAuth = FirebaseAuth.getInstance();
   //     addAutoSaveButtonListener();
 //       addLoadButtonListener();
//        addSaveButtonListener();
        addLoadGameButtonListener();
        addNewGameButtonListener();
        addLogOutButtonListener();
        addReturnToGameCenterListener();
        TextView account = findViewById(R.id.Hiuser);
        account.setText("Hi, " + accountManager.getAccount(userEmail).getUserName());
    }

    /**
     * Activate the autoSave button.
     */
//    private void addAutoSaveButtonListener() {
//        Button startButton = findViewById(R.id.StartButton);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
//                goToSavedGames.putExtra("saveType", "autoSave");
//                goToSavedGames.putExtra("gameType", "game2048");
//                startActivity(goToSavedGames);
//                loadFromFile(userEmail + AUTO_SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                if (game2048 == null) {
//                    makeAnotherToastCurrentMessage();
//                    return;
//                }
//                makeToastLoadedText();
//                switchToGame();
//            }
//        });
//    }

    /**
     * Activate the load button.
     */
//    private void addLoadButtonListener() {
//        Button loadButton = findViewById(R.id.LoadButton);
//        loadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
//                goToSavedGames.putExtra("saveType", "userSave");
//                goToSavedGames.putExtra("gameType", "game2048");
//                startActivity(goToSavedGames);
//                loadFromFile(userEmail + SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                if (game2048 == null) {
//                    makeToastForLoadGame();
//                    return;
//                }
//                loadFromFile(userEmail + SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                makeToastLoadedText();
//                switchToGame();
//            }
//        });
//    }

//    private void makeToastForLoadGame() {
//        Toast.makeText(this, "No Saved Game", Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * Display that a game was loaded successfully.
//     */
//    private void makeToastLoadedText() {
//        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
//    }
//
//    private void makeAnotherToastCurrentMessage() {
//        Toast.makeText(this, "No current Game", Toast.LENGTH_SHORT).show();
//    }

    /**
     * Activate the save button.
     */
//    private void addSaveButtonListener() {
//        Button saveButton = findViewById(R.id.SaveButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveToFile(userEmail + SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                makeToastSavedText();
//            }
//        });
//    }

    /**
     * Activate new 2048 game.
     */
    private void addNewGameButtonListener() {
        Button Button4 = findViewById(R.id.StartButton);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game2048 game2048 = new Game2048();
                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(game2048);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
                switchToGame(game2048.getSaveId());
            }
        });
    }

    /**
     * Activate the 4x4 new game board.
     //     */
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
                Intent backToGameCenter = new Intent(getApplicationContext(), GameCentreActivity.class);
                startActivity(backToGameCenter);
            }
        });
    }

    public void showLoadDialog(){
        AlertDialog.Builder loadDialog =
                new AlertDialog.Builder(Game2048StartActivity.this);
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

    /**
     * Display that a game was saved successfully.
     */
//    private void makeToastSavedText() {
//        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
//    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    /**
     * Switch to the Game2048Activity view to play the game.
     */
    public void switchToGame(String saveId) {
        Intent tmp = new Intent(this, Game2048Activity.class);
        tmp.putExtra("saveId", saveId);
        tmp.putExtra("saveType", "autoSave");
//        saveToFile(Game2048StartActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    public void switchToSaveGames(){

        Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
        goToSavedGames.putExtra("saveType", "userSave");
        goToSavedGames.putExtra("gameType", "game2048");
        startActivity(goToSavedGames);
    }

    public void switchToAutoSaveGames(){

        Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
        goToSavedGames.putExtra("saveType", "autoSave");
        goToSavedGames.putExtra("gameType", "game2048");
        startActivity(goToSavedGames);

    }

    public void switchToLogin() {
        firebaseAuth.signOut();
        Intent tmp = new Intent(this, LoginActivity.class);
        tmp.putExtra("userEmail", userEmail);
        startActivity(tmp);

    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
//    private void loadFromFile(String fileName) {
//
////        try {
////            InputStream inputStream = this.openFileInput(fileName);
////            if (inputStream != null) {
////                ObjectInputStream input = new ObjectInputStream(inputStream);
////                game2048 = (Game2048) input.readObject();
////                inputStream.close();
////            }
////        } catch (FileNotFoundException e) {
////            Log.e("Game2048 activity", "File not found: " + e.toString());
////        } catch (IOException e) {
////            Log.e("Game2048t activity", "Can not read file: " + e.toString());
////        } catch (ClassNotFoundException e) {
////            Log.e("Game2048 activity", "File contained unexpected data type: " + e.toString());
////        }
////    }

    private void readFromSer(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream in = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) in.readObject();
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("Game2048Start activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game2048Start activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Game2048Start activity", "File contained unexpected data type: " + e.toString());
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
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
