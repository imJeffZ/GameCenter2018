package fall18_207project.GameCenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTileStartingActivity extends AppCompatActivity implements
        MultiLoadStartActivity, MultiComplexityStartActivity, GameStartingActivity{

    public static String userEmail = "";
    private FirebaseAuth firebaseAuth;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_starting_);
        firebaseAuth = FirebaseAuth.getInstance();
 //       addLoadAutoSaveButtonListener();
        //      addLoadButtonListener();
//        addSaveButtonListener();
 //       add3ButtonListener();
 //       add4ButtonListener();
 //       add5ButtonListener();
        addLoadGameButtonListener();
        addNewGameButtonListener();
        addLogOutButtonListener();
        addReturnToGameCenterListener();
        TextView account = findViewById(R.id.Hiuser);
        if (accountManager.getAccount(userEmail) != null) {
            account.setText("Hi, " + accountManager.getAccount(userEmail).getUserName());
        }
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

    /**
     * Activate the load auto save button.
     */
//    private void addLoadAutoSaveButtonListener() {
//        Button startButton = findViewById(R.id.StartButton);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
//                goToSavedGames.putExtra("saveType", "autoSave");
//                goToSavedGames.putExtra("gameType", "slidingTiles");
//                startActivity(goToSavedGames);
//                loadFromFile(userEmail + AUTO_SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                if (slidingTiles == null) {
//                    makeAnotherToastCurrentMessage();
//                    return;
//                }
//                makeToastLoadedText();
//            }
//        });
//    }

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
     * Activate the load button.
     */
//    private void addLoadButtonListener() {
//        Button loadButton = findViewById(R.id.LoadButton);
//        loadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToSavedGames = new Intent(getApplicationContext(), SavedGamesActivity.class);
//                goToSavedGames.putExtra("saveType", "userSave");
//                goToSavedGames.putExtra("gameType", "slidingTiles");
//                startActivity(goToSavedGames);
//                loadFromFile(userEmail + SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                if (slidingTiles == null) {
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
//
//    /**
//     * Activate the save button.
//     */
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
     * Activate the 3x3 new game Board.
     */
//    private void add3ButtonListener() {
//        Button Button3 = findViewById(R.id.Button3);
//        Button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Game slidingTiles = new SlidingTiles(3);
//                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(slidingTiles);
//                saveToSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//                switchToGame(slidingTiles.getSaveId());
//            }
//        });
//    }


    /**
     * Activate the 4x4 new game board.
     */
//    private void add4ButtonListener() {
//        Button Button4 = findViewById(R.id.Button4);
//        Button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Game slidingTiles = new SlidingTiles(4);
//                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(slidingTiles);
//                saveToSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//                switchToGame(slidingTiles.getSaveId());
//            }
//        });
//    }

    /**
     * Activate the 5x5 new game board.
     */
//    private void add5ButtonListener() {
//        Button Button5 = findViewById(R.id.Button5);
//        Button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Game slidingTiles = new SlidingTiles(5);
//                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(slidingTiles);
//                saveToSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//                switchToGame(slidingTiles.getSaveId());
//            }
//        });
//    }

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

//    /**
//     * Display that a game was saved successfully.
//     */
//    private void makeToastSavedText() {
//        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * Read the temporary board from disk.
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadFromFile(TEMP_SAVE_FILENAME);
//    }

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
        Game slidingTiles = new SlidingTiles(num);
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame(slidingTiles);
        saveToSer(LoginActivity.ACCOUNT_MANAGER_DATA);
        switchToGame(slidingTiles.getSaveId());

    }
    public void switchToGame(String saveId) {
        Intent tmp = new Intent(this, SlidingTileGameActivity.class);
        tmp.putExtra("saveId", saveId);
        tmp.putExtra("saveType", "autoSave");
//        saveToFile(SlidingTileStartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    public void switchToLogin() {
        firebaseAuth.signOut();
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);

    }

    private void readFromSer(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream in = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) in.readObject();
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("Starting activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Starting activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Starting activity", "File contained unexpected data type: " + e.toString());
        }
    }

    public void saveToSer(String fileName) {
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
