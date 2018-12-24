package fall18_207project.GameCenter;

public interface GameController {

    boolean isValidTap(int position);

    void touchMove(int position);

    boolean hasValidMove();

    boolean isSolved();
}
