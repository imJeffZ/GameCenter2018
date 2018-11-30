package fall18_207project.GameCenter;

import com.google.firebase.auth.FirebaseAuth;

public abstract class GameStartController {
    String userEmail;
    AccountManager accountManager;
    FirebaseAuth firebaseAuth;

    GameStartController(AccountManager accManager, String User){
        this.accountManager = accManager;
        this.userEmail = User;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    String setUserTextViewTest(){
        if (accountManager.getAccount(userEmail) != null)
            return "Hi, " + accountManager.getAccount(userEmail).getUserName();
        return "";
    }

    abstract Game addGameInAcc(Game game);
    void userSignOut(){
        firebaseAuth.signOut();
    }
}
