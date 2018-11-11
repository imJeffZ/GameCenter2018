package fall18_207project.GameCenter;

public interface GameFeature {

    int calculateScore();
    void undo();
    boolean isSolved();
    boolean isValidTap(int position);
    void touchMove(int position);
    long getElapsedTime();
}
