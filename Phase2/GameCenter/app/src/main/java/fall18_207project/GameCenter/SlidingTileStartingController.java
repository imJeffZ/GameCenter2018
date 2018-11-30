package fall18_207project.GameCenter;

public class SlidingTileStartingController extends GameStartController {


    SlidingTileStartingController(AccountManager accManager, String User){
        super(accManager, User);
    }

    SlidingTiles createGame(int num){
        return new SlidingTiles(num);
    }

    Game addGameInAcc(Game game){
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame((SlidingTiles) game);
        return game;
    }

    void userSignOut(){
        firebaseAuth.signOut();
    }
}

