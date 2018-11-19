package fall18_207project.GameCenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;


class MovementController {

    private Game game;

    MovementController() {
    }

    void setGame(Game g) {
        this.game = g;
    }

    void processTapMovement(final Context context, int position, boolean display) {
        if (this.game.isSolved()) {
            addScoreOnFinish();
            createDialog(context);
        } else if (this.game.isValidTap(position)) {
            this.game.touchMove(position);
            if (this.game.isSolved()) {
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
                Intent gotoFinishScreen = new Intent(context, GameFinishActivity.class);
                gotoFinishScreen.putExtra("score", game.calculateScore());
                gotoFinishScreen.putExtra("size", game.gameId);
                context.startActivity(gotoFinishScreen);
            }
        });
        builder.create().show();
    }

    /**
     * add the score to the account when the game is over
     */
    private void addScoreOnFinish() {
        String score = Integer.toString(this.game.calculateScore());
        int size = this.game.gameId;
        int index;
        if (size == 1) {
            index = 0;
        } else if (size == 2) {
            index = 1;
        } else {
            index = 2;
        }
        Account currentAccount = AccountManager.accountMap.get(GameCentreActivity.CURRENT_ACCOUNT);
        currentAccount.addScore(index, score);
        AccountManager.accountMap.put(StartingActivity.CURRENT_ACCOUNT, currentAccount);
    }
}
