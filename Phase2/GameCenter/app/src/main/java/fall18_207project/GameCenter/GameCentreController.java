package fall18_207project.GameCenter;

import android.os.Handler;
import android.os.Message;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameCentreController {

    private AccountManager accountManager;
    public String userEmail;

    GameCentreController(AccountManager accManager, String User){
        this.accountManager = accManager;
        this.userEmail = User;
    }

    void  updateProfile(int id, String update){
        if (accountManager.getAccount(userEmail) != null) {
            if (id == R.id.nav_reset){
                accountManager.getAccount(userEmail).setUserName(update);
            } else if (id == R.id.nav_intro){
                accountManager.getAccount(userEmail).getProf().setIntro(update);
            } else if (id == R.id.nav_password){
                accountManager.getAccount(userEmail).setPassword(update);
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                currentUser.updatePassword(update);
            }
        }
    }

    void updateImage(int id){
        if (accountManager.getAccount(userEmail) != null) {
            accountManager.getAccount(userEmail).getProf().setAvatarId(id);
        }
    }

}
