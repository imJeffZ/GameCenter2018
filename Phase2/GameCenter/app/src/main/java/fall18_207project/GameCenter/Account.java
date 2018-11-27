package fall18_207project.GameCenter;

import java.io.Serializable;

/***
 * Account class that stores a user.
 *
 * TODO: Connect to DataBase
 */
public class Account implements Serializable {
    private String email;
    private String userName;
    private String password;
    private GameManager userSavedGames;
    private GameManager autoSavedGames;
    private Profile prof;

//    @Deprecated private ArrayList scoreRecord;
    private UserScoreBoard userScoreBoard;


    public Account(){

    }

    public Account(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
//        this.scoreRecord = new ArrayList(3);
//        this.scoreRecord.add(null);
//        this.scoreRecord.add(null);
//        this.scoreRecord.add(null);
        // TODO: Rename prof to profile
        this.prof = new Profile();
//        this.userScoreBoard = new UserScoreBoard();
        // TODO: Change to userSavedGames to new DuplicateGameManager(), someHow I might have reverted the functionality of DuplicateGameManger and NonDuplicateGameManager
        this.userSavedGames = new NonDuplicateGameManager();
        this.autoSavedGames = new NonDuplicateGameManager();
        this.userScoreBoard = new UserScoreBoard();
    }

    public GameManager getUserSavedGames() {
        return this.userSavedGames;
    }

    public GameManager getAutoSavedGames() {
        return this.autoSavedGames;
    }

    public UserScoreBoard getUserScoreBoard() {
        return this.userScoreBoard;
    }

    //    @Deprecated public ArrayList getScoreRecord() {
//        return this.scoreRecord;
//    }


//
//    @Deprecated void addScore(int index, String score) {
//        if (this.getScoreRecord().get(index) == null) {
//            this.getScoreRecord().set(index, score);
//        } else if (Integer.parseInt(score) > Integer.parseInt(((String)this.getScoreRecord().get(index)))) {
//            this.getScoreRecord().set(index, score);
//        }
//    }

    public String getEmail() {
        return this.email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return this.userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

//    void emptySavedGames() {
//        this.savedGames.clear();
//    }

    void setPassword(String newPass) {
        this.password = newPass;
    }

    public Profile getProf() {
        return prof;
    }

//    boolean deleteSavedGame(String saveId) {
//        return this.savedGames.deleteGame(saveId);
//    }
//
//    public UserScoreBoard getUserScoreBoard() {
//        return this.userScoreBoard;
//    }


}
