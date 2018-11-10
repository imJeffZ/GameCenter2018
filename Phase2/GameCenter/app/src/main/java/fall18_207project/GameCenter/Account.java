package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    AccountManager accountManager;
    private String userName;
    private String password;
    private String[] scoreRecord;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.scoreRecord = new String[3];
    }

    String getPassword() {
        return this.password;
    }

    void setPassword(String newPass) {
        this.password = newPass;
    }

    String[] getScoreRecord() {
        return this.scoreRecord;
    }

    void addScore(int index, String score) {
        if (this.scoreRecord[index] == null) {
            this.scoreRecord[index] = score;
        } else if (Integer.parseInt(score) > Integer.parseInt(this.scoreRecord[index])) {
            this.scoreRecord[index] = score;
        }
    }
}
