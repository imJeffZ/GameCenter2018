package fall18_207project.GameCenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

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
    private BoardManager boardManager;

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

        int counter = boardManager.getCountMove();
        TextView count = findViewById(R.id.steps_id);
        count.setText("Step: " + counter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        mContext = this;
        mTvTimer = findViewById(R.id.time_id);

        addUndoButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNUM_COLS());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();


                        columnWidth = displayWidth / boardManager.getBoard().getNUM_COLS();
                        columnHeight = displayHeight / boardManager.getBoard().getNUM_ROWS();

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
                boardManager.undo();
            }
        }));

    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNUM_ROWS(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNUM_COLS(); col++) {
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
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNUM_ROWS();
            int col = nextPos % boardManager.getBoard().getNUM_COLS();
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
        boardManager.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();

        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        boardManager.resetElapsedTime();
    }

    @Override
    protected void onStop() {
        super.onStop();
        boardManager.updateElapsedTime(mChrono.getElapsedTime());
        mChrono.stop();

        saveToFile(StartingActivity.CURRENT_ACCOUNT + StartingActivity.AUTO_SAVE_FILENAME);
        boardManager.resetElapsedTime();
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
                boardManager = (BoardManager) input.readObject();
                if (boardManager.getElapsedTime() != 0) {
                    mContext = this;
                    mChrono = new GameChronometer(mContext, System.currentTimeMillis() - boardManager.getElapsedTime());
                    mThreadChrono = new Thread(mChrono);
                    mThreadChrono.start();
                    mChrono.start();
                }
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

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
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