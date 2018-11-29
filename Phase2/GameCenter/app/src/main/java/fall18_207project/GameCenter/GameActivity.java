package fall18_207project.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
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
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTiles slidingTiles;
    private AccountManager accountManager;
    private GameManager gameManager;
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
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));

        int counter = slidingTiles.getCountMove();
        TextView count = findViewById(R.id.steps_id);
        count.setText("Step: " + counter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//        @NonNull String email = getIntent().getStringExtra("userEmail");
//        userEmail = email;
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
            slidingTiles = new SlidingTiles(1);
        } else {
            slidingTiles = (SlidingTiles) gameManager.getGame(getIntent().getStringExtra("saveId"));
        }

        // Continue the timer
        if (slidingTiles.getElapsedTime() != 0) {
            mContext = this;
            mChrono = new GameChronometer(mContext, System.currentTimeMillis() - slidingTiles.getElapsedTime());
            mThreadChrono = new Thread(mChrono);
            mThreadChrono.start();
            mChrono.start();
        }

        createTileButtons(this);
        setContentView(R.layout.activity_game);

        mContext = this;
        mTvTimer = findViewById(R.id.time_id);

        addUndoButtonListener();
        addSaveButtonListener();
        addResetButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(slidingTiles.getBoard().getNUM_COLS());

        gridView.setGame(slidingTiles);

        slidingTiles.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();


                        columnWidth = displayWidth / slidingTiles.getBoard().getNUM_COLS();
                        columnHeight = displayHeight / slidingTiles.getBoard().getNUM_ROWS();

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
     */
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
                slidingTiles.undo();
            }
        }));

    }

    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.saveGameButton);
        saveButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
                slidingTiles.updateElapsedTime(mChrono.getElapsedTime());
                accountManager.getAccount(userEmail).getUserSavedGames().addGame(slidingTiles);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
                makeSavedMessage();
            }
        }));
    }

    private void makeSavedMessage() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    private void addResetButtonListener() {
        Button resetButton = findViewById(R.id.restartButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView count = findViewById(R.id.steps_id);
                Intent restart = new Intent(getApplicationContext(), GameActivity.class);
                int size = slidingTiles.getGameId() + 2;
                SlidingTiles newSlidingTiles = new SlidingTiles(size);
                newSlidingTiles.tiles = slidingTiles.cloneTiles();
                newSlidingTiles.board = new Board(slidingTiles.tiles, size);
                newSlidingTiles.initialBoard = new Board(slidingTiles.tiles, size);
                restart.putExtra("saveId", newSlidingTiles.getSaveId());
                restart.putExtra("saveType", "autoSave");
                readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
                accountManager.getAccount(userEmail).getAutoSavedGames().addGame(newSlidingTiles);
                saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
                startActivity(restart);
            }
        });
    }
    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = slidingTiles.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != slidingTiles.getBoard().getNUM_ROWS(); row++) {
            for (int col = 0; col != slidingTiles.getBoard().getNUM_COLS(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = slidingTiles.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / slidingTiles.getBoard().getNUM_ROWS();
            int col = nextPos % slidingTiles.getBoard().getNUM_COLS();
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
        slidingTiles.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();
//        slidingTiles.resetElapsedTime();
//        gameManager.addGame(slidingTiles);
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame(slidingTiles);
        saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
//        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        slidingTiles.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();

//        saveToFile(userEmail + StartingActivity.AUTO_SAVE_FILENAME);
//        slidingTiles.resetElapsedTime();
//        gameManager.addGame(slidingTiles);
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame(slidingTiles);
        saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent gotoStarting = new Intent(getApplicationContext(), StartingActivity.class);
        startActivity(gotoStarting);
        saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
    }


    //    /**
//     * Load the board manager from fileName.
//     *
//     * @param fileName the name of the file
//     */
//    private void loadFromFile(String fileName) {
//
//        try {
//            InputStream inputStream = this.openFileInput(fileName);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                slidingTiles = (SlidingTiles) input.readObject();
//                if (slidingTiles.getElapsedTime() != 0) {
//                    mContext = this;
//                    mChrono = new GameChronometer(mContext, System.currentTimeMillis() - slidingTiles.getElapsedTime());
//                    mThreadChrono = new Thread(mChrono);
//                    mThreadChrono.start();
//                    mChrono.start();
//                }
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("Game activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("Game activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("Game activity", "File contained unexpected data type: " + e.toString());
//        }
//    }

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

    private void readFromSer(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Game activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Game activity", "File contained unexpected data type: " + e.toString());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
