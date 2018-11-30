package fall18_207project.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 * test for push.
 */
public class Game2048Activity extends AppCompatActivity implements Observer, GameActivity {

    /**
     * The board manager.
     */
    private Game2048 game2048;
    private AccountManager accountManager;
    private  GameManager gameManager;
    private String saveType;
    public static String userEmail = "";

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    //Timer textview
    TextView mTvTimer;
    //Instance of Chronometer
    GameChronometer mChrono;
    //Thread for chronometer
    Thread mThreadChrono;
    //Reference to the MainActivity (this class!)
    Context mContext;


    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateGameTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));

        int counter = game2048.getCountMove();
        TextView count = findViewById(R.id.steps_id);
        count.setText("Step: " + counter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//        userEmail = getIntent().getStringExtra("userEmail");
        saveType = getIntent().getStringExtra("saveType");
        if (saveType.equals("autoSave")) {
            gameManager = accountManager.getAccount(userEmail).getAutoSavedGames();
        } else if (saveType.equals("userSave")){
            gameManager = accountManager.getAccount(userEmail).getUserSavedGames();
        } else {
            gameManager = accountManager.getAccount(userEmail).getUserScoreBoard();
        }
        // TODO: Better fix on how to deal with Null pointer exception when passing data through intent
        String saveId = getIntent().getStringExtra("saveId");
        if (saveId == null) {
            game2048 = new Game2048();
        } else {
            game2048 = (Game2048) gameManager.getGame(getIntent().getStringExtra("saveId"));
            System.out.println("HERE");
        }

//        loadFromFile(Game2048StartActivity.TEMP_SAVE_FILENAME);
        if (game2048.getElapsedTime() != 0) {
            mContext = this;
            mChrono = new GameChronometer(mContext, System.currentTimeMillis() - game2048.getElapsedTime());
            mThreadChrono = new Thread(mChrono);
            mThreadChrono.start();
            mChrono.start();
        }
        createGameTileButtons(this);
        setContentView(R.layout.activity_game2048);

        mContext = this;
        mTvTimer = findViewById(R.id.time_id);

        addUndoButtonListener();
        addSaveButtonListener();
        addResetButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(game2048.getBoard().getNumOfColumns());
        gridView.setGame(game2048);
        game2048.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();


                        columnWidth = displayWidth / game2048.getBoard().getNumOfColumns();
                        columnHeight = displayHeight / game2048.getBoard().getNumOfRows();

                        display();
                    }
                });
         if (mChrono == null) {
            mChrono = new GameChronometer(mContext);
            mThreadChrono = new Thread(mChrono);
            mThreadChrono.start();
            mChrono.start();
        }
    }


    /**
     * Update the text of tv_timer
     *
     * @param timeAsText the text to update tv_timer with
//     */
//    public void updateTimerText(final String timeAsText) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mTvTimer.setText(timeAsText);
//            }
//        });


    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTimer.setText(timeAsText);
            }
        });
    }


    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game2048.undo();
                setObserver(game2048.getBoard());
                game2048.getBoard().swapTiles(1,2,2,1);
                game2048.getBoard().swapTiles(1,2,2,1);
            }
        }));

    }

    private void addResetButtonListener() {
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restart = new Intent(getApplicationContext(), Game2048Activity.class);
                Game2048 newGame2048 = new Game2048();
                newGame2048.tiles = game2048.cloneTiles();
                newGame2048.board = new Game2048Board(game2048.tiles, 4);
                newGame2048.initialBoard = new Board(game2048.tiles, 4);
                restart.putExtra("saveId", newGame2048.getSaveId());
                restart.putExtra("saveType", "autoSave");
                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(newGame2048);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
                startActivity(restart);
            }
        });
    }

    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.saveGameButton);
        saveButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
                game2048.updateElapsedTime(mChrono.getElapsedTime());
                accountManager.getAccount(userEmail).getUserSavedGames().addGame(game2048);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
                makeSavedMessage();
            }
        }));
    }
    private void makeSavedMessage() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent gotoStarting = new Intent(getApplicationContext(), Game2048StartActivity.class);
        startActivity(gotoStarting);
        saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

//    private void addResetButtonListener() {
//        Button resetButton = findViewById(R.id.resetButton);
//        resetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                game2048.reset();
//                TextView counter = findViewById(R.id.steps_id);
//                counter.setText("Step: " + 0);
//            }
//        });
//    }

    private void setObserver(Board board) {
        board.addObserver(this);
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public void createGameTileButtons(Context context) {
        Game2048Board board = game2048.getBoard();

        tileButtons = new ArrayList<>();
        for (int row = 0; row != game2048.getBoard().getNumOfRows(); row++) {
            for (int col = 0; col != game2048.getBoard().getNumOfColumns(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public void updateGameTileButtons() {
        Board board = game2048.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / game2048.getBoard().getNumOfRows();
            int col = nextPos % game2048.getBoard().getNumOfColumns();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        game2048.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();

//        saveToFile(Game2048StartActivity.TEMP_SAVE_FILENAME);
//        game2048.resetElapsedTime();
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame(game2048);
        saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    protected void onStop() {
        super.onStop();
        game2048.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();

 //       saveToFile(userEmail+ Game2048StartActivity.AUTO_SAVE_FILENAME);
 //       game2048.resetElapsedTime();
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame(game2048);
        saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
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
                if (game2048.getElapsedTime() != 0) {
                    mContext = this;
                   // mChrono = new GameChronometer(mContext, System.currentTimeMillis() - game2048.getElapsedTime());
                   // mThreadChrono = new Thread(mChrono);
                   // mThreadChrono.start();
                    //mChrono.start();
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Game2048 activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game2048 activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Game2048 activity", "File contained unexpected data type: " + e.toString());
        }
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
            Log.e("Game2048 activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game2048 activity", "Can not read file: " + e.toString());
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
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        display();
    }




}
