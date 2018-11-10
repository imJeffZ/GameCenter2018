package fall18_207project.GameCenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;


class MovementController {

    private BoardManager boardManager;

    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(final Context context, int position, boolean display) {
        if (boardManager.puzzleSolved()) {
            addScoreOnFinish();
            createDialog(context);
        } else if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
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
                gotoFinishScreen.putExtra("score", boardManager.calculateScore());
                gotoFinishScreen.putExtra("size", boardManager.getBoard().getNUM_COLS());
                context.startActivity(gotoFinishScreen);
            }
        });
        builder.create().show();
    }

    /**
     * add the score to the account when the game is over
     */
    private void addScoreOnFinish() {
        String score = Integer.toString(boardManager.calculateScore());
        int size = boardManager.getBoard().getNUM_COLS();
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
