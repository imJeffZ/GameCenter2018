package fall18_207project.GameCenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;


class MovementController {

    private SlidingTiles slidingTiles;

    MovementController() {
    }

    void setSlidingTiles(SlidingTiles slidingTiles) {
        this.slidingTiles = slidingTiles;
    }

    void processTapMovement(final Context context, int position, boolean display) {
        if (slidingTiles.puzzleSolved()) {
            addScoreOnFinish();
            createDialog(context);
        } else if (slidingTiles.isValidTap(position)) {
            slidingTiles.touchMove(position);
            if (slidingTiles.puzzleSolved()) {
                addScoreOnFinish();
                createDialog(context);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Created a dialog when the game finished, which have a continue button to continue to
     * next screen.
     *
     * @param context nothing
     */
    private void createDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("You won! press continue to keep going!");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent gotoFinishScreen = new Intent(context, SlidingTileFinishActivity.class);
                gotoFinishScreen.putExtra("score", slidingTiles.calculateScore());
                gotoFinishScreen.putExtra("size", slidingTiles.getBoard().getNUM_COLS());
                context.startActivity(gotoFinishScreen);
            }
        });
        builder.create().show();
    }

    /**
     * add the score to the account when the game is over
     */
    private void addScoreOnFinish() {
        String score = Integer.toString(slidingTiles.calculateScore());
        int size = slidingTiles.getBoard().getNUM_COLS();
        int index;
        if (size == 3) {
            index = 0;
        } else if (size == 4) {
            index = 1;
        } else {
            index = 2;
        }
        Account currentAccount = AccountManager.accountMap.get(StartingActivity.CURRENT_ACCOUNT);
        currentAccount.addScore(index, score);
        AccountManager.accountMap.put(StartingActivity.CURRENT_ACCOUNT, currentAccount);
    }
}
