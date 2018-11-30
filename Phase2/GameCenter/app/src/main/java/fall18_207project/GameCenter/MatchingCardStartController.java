package fall18_207project.GameCenter;

import com.google.firebase.auth.FirebaseAuth;

public class MatchingCardStartController extends GameStartController{

    MatchingCardStartController(AccountManager accManager, String User){
        super(accManager, User);
    }

    MatchingCards createGame(int num){
        return new MatchingCards(num);
    }

    Game addGameInAcc( Game game){
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame((MatchingCards) game);
        return game;
    }

}
