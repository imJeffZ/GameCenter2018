package fall18_207project.GameCenter;

import java.io.Serializable;

/***
 * Account class that stores a user.
 *
 * TODO: Add Profile Class
 *
 * TODO: Connect to DataBase
 */
public class Account implements Serializable {
    private String email;
    private String userName;
    private String password;
    private GameManager savedGames;
    private Profile prof;
    private String[] scoreRecord;

    public Account(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.scoreRecord = new String[3];
    }

    String[] getScoreRecord() {
        return this.scoreRecord;
    }

    void addScore(int index, String score) {
        if (this.getScoreRecord()[index] == null) {
            this.getScoreRecord()[index] = score;
        } else if (Integer.parseInt(score) > Integer.parseInt(this.getScoreRecord()[index])) {
            this.getScoreRecord()[index] = score;
        }
    }

    String getEmail() {
        return this.email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getUserName() {
        return this.userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getPassword() {
        return this.password;
    }

    void emptySavedGames() {
        this.savedGames.clear();
    }

    void setPassword(String newPass) {
        this.password = newPass;
    }

    boolean deleteSavedGame(String saveId) {
        return this.savedGames.deleteGame(saveId);
    }

}
