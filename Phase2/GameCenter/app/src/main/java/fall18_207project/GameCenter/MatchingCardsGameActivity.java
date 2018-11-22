package fall18_207project.GameCenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MatchingCardsGameActivity extends AppCompatActivity implements Observer {
    /**
     * The matchingGame.
     */
    private MatchingCards matchingCards;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> cardButtons;

    //Timer textview
    TextView mTvTimer;
    //Instance of Chronometer
    //GameChronometer mChrono;
    //Thread for chronometer
   // Thread mThreadChrono;
    //Reference to the Activity (this class!)
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
        if (matchingCards.isStartMode())
            initCardButtons();
        else
            updateCardButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));

        int counter = matchingCards.getCountMove();
        TextView count = findViewById(R.id.steps_id);
        count.setText("Step: " + counter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(MatchingCardStartActivity.TEMP_SAVE_FILENAME);
        createCardButtons(this);
        setContentView(R.layout.activity_matchingcard_game_main);

        mContext = this;
        mTvTimer = findViewById(R.id.time_id);

        addStartButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(matchingCards.getMatchingBoard().getNumOfColumns());
        gridView.setGame(matchingCards);
        matchingCards.getMatchingBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();


                        columnWidth = displayWidth / matchingCards.getMatchingBoard().getNumOfColumns();
                        columnHeight = displayHeight / matchingCards.getMatchingBoard().getNumOfRows();

                        display();
                    }
                });
//        if (mChrono == null) {
//            mChrono = new GameChronometer(mContext);
//            mThreadChrono = new Thread(mChrono);
//            mThreadChrono.start();
//            mChrono.start();
//        }
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


    private void addStartButtonListener() {
        final Button startButton = findViewById(R.id.StartMatchButton);
        startButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchingCards.setStartMode();
                startButton.setClickable(false);
                startButton.setText("Started");
                updateCardButtons();
            }
        }));

    }

    /**
     * Create the buttons for displaying the cards.
     *
     * @param context the context
     */
    private void createCardButtons(Context context) {
        MatchingBoard matchingBoard = matchingCards.getMatchingBoard();
        cardButtons = new ArrayList<>();
        for (int row = 0; row < matchingCards.getMatchingBoard().getNumOfRows(); row++) {
            for (int col = 0; col < matchingCards.getMatchingBoard().getNumOfColumns(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(matchingBoard.getCard(row, col).getBackId());
                this.cardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the cards.
     */
    private void updateCardButtons() {
        MatchingBoard matchingBoard = matchingCards.getMatchingBoard();
        int nextPos = 0;
        for (Button b : cardButtons) {
            int row = nextPos /matchingCards.getMatchingBoard().getNumOfRows();
            int col = nextPos % matchingCards.getMatchingBoard().getNumOfColumns();
            if(matchingBoard.getCard(row, col).isUp()) {
                b.setBackgroundResource(matchingBoard.getCard(row, col).getBackId());
                }
            else {
                b.setBackgroundResource(R.drawable.matching_front);

            }
            nextPos++;
        }
    }

    private void initCardButtons() {
        MatchingBoard matchingBoard = matchingCards.getMatchingBoard();
        int nextPos = 0;
        for (Button b : cardButtons) {
            int row = nextPos /matchingCards.getMatchingBoard().getNumOfRows();
            int col = nextPos % matchingCards.getMatchingBoard().getNumOfColumns();
            b.setBackgroundResource(matchingBoard.getCard(row, col).getBackId());
            nextPos++;
        }
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
//        matchingCards.updateElapsedTime(mChrono.getElapsedTime());
//        mChrono.stop();

        saveToFile(MatchingCardStartActivity.TEMP_SAVE_FILENAME);
        matchingCards.resetElapsedTime();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        matchingCards.updateElapsedTime(mChrono.getElapsedTime());
//        mChrono.stop();

        saveToFile(MatchingCardStartActivity.CURRENT_ACCOUNT + MatchingCardStartActivity.AUTO_SAVE_FILENAME);
        matchingCards.resetElapsedTime();
    }

    /**
     * Load the matchingTile from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                matchingCards = (MatchingCards) input.readObject();
//                if (matchingCards.getElapsedTime() != 0) {
//                    mContext = this;
//                    mChrono = new GameChronometer(mContext, System.currentTimeMillis() - matchingCards.getElapsedTime());
//                    mThreadChrono = new Thread(mChrono);
//                    mThreadChrono.start();
//                    mChrono.start();
//                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("MatchingCardsGame activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("MatchingCardsGame activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("MatchingCardsGame activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the matchingTile to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(matchingCards);
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