package fall18_207project.GameCenter;

public interface GameTimer {
    String getBeginTime();
    long getElapsedTime();
    void updateElapsedTime(long newElapsedTime);
    void resetElapsedTime();
}
