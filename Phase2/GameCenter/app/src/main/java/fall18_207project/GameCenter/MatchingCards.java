package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MatchingCards extends Game implements Cloneable, Serializable {

    protected MatchingBoard matchingBoard;
    private  int match;
    private  int prePos;
    private  boolean startMode;
    private  boolean matchBomb;

    public int getMatch(){return match;}
    public int getPrePos(){return prePos;}
    public MatchingBoard getMatchingBoard(){
        return matchingBoard;
    }
    public boolean isStartMode(){return startMode;}

    public boolean isMatchBomb(){return matchBomb;}

    MatchingCards(int num) {
        super();
        this.gameId = num;
        List<Card> cardList = new ArrayList<>();
        final int numCards = num * num;
        for (int cardNum = 0; cardNum < numCards; cardNum++) {
            cardList.add(new Card(cardNum));
        }
        Collections.shuffle(cardList);
        matchingBoard = new MatchingBoard(cardList, num);
        this.endTime = 0;
        this.prePos = -1;
        this.match = 0;
        this.startMode = true;
        this.matchBomb = false;
    }

    @Override
    public boolean isSolved() {
        return matchBomb||(match == 2 * this.gameId);
    }

    @Override
    public boolean isValidTap(int position) {
        int row = position / matchingBoard.getNumOfRows();
        int col = position % matchingBoard.getNumOfColumns();
        return (!this.startMode) && position < matchingBoard.getNumOfCards() && !(this.isCardUsed(row, col));
    }

    @Override
    public void touchMove(int position) {
        countMove++;
        int row = position / matchingBoard.getNumOfRows();
        int col = position % matchingBoard.getNumOfColumns();
        int row1 = prePos/ matchingBoard.getNumOfRows();
        int col1 = prePos % matchingBoard.getNumOfColumns();
        if (matchingBoard.getCard(row, col).isBomb()) {
            matchingBoard.turnCard(row, col, true);
            matchBomb = true;
        }
        else if (this.isMatched(row, col)){
            match++;
            matchingBoard.turnCard(row, col, true);
            matchingBoard.useCards(row, col, row1, col1);
            prePos = -1;
            saveMove.push(position);
        }else {
            if(this.prePos == -1){
                matchingBoard.turnCard(row, col, true);
                prePos = position;
                saveMove.push(position);
            }
            else{
                matchingBoard.turnCard(row1,col1, false);
                prePos = -1;
                saveMove.pop();
            }
        }
    }

    public boolean isMatched(int row0, int col0){
        int row = prePos/ matchingBoard.getNumOfRows();
        int col = prePos % matchingBoard.getNumOfColumns();
        return prePos != -1 && ((matchingBoard.getCard(row, col).getId() % 2 == 0 ? matchingBoard.getCard(row, col).getId()
                == matchingBoard.getCard(row0, col0).getId() + 1 :
                matchingBoard.getCard(row, col).getId() == matchingBoard.getCard(row0, col0).getId() - 1));
    }

    public boolean isCardUsed(int row, int col){ return matchingBoard.getCard(row, col).isUsed();}


    public void setStartMode(){ this.startMode = false;}

    @Override
    public long getElapsedTime() {
        return super.getElapsedTime();
    }

    public int calculateScore(){
        if (!isMatchBomb())
            return Math.round(1400 / (countMove + 1) + 600 / (endTime + 1));
        else
            return 0;
    }
}