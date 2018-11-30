package fall18_207project.GameCenter;


public class Game2048StartController extends GameStartController{

    Game2048StartController(AccountManager accManager, String User){
        super(accManager, User);
    }

    String setUserTextViewTest(){
        if (accountManager.getAccount(userEmail) != null)
            return "Hi, " + accountManager.getAccount(userEmail).getUserName();
        return "";
    }

    Game2048 createGame(){
        return new Game2048();
    }

    Game addGameInAcc(Game game){
        accountManager.getAccount(userEmail).getAutoSavedGames().addGame((Game2048) game);
        return game;
    }
}
