package fall18_207project.GameCenter;

public interface GameFeature {

    void undo();
    boolean isSolved();
    boolean isValidTap(int position);
    void touchMove(int position);
    long getElapsedTime();
}
