package fall18_207project.GameCenter;

public class GameFinishController {

     AccountManager accountManager;
     String userEmail;

     GameFinishController(AccountManager accManager, String user){
         this.accountManager = accManager;
         this.userEmail = user;
     }

     String updateFinishedGame(String saveId){
         String result;
         Game finishedGame;
         finishedGame = accountManager.getAccount(userEmail).getAutoSavedGames().getGame(saveId);
         result = Integer.toString(finishedGame.calculateScore());
         // Update global score board
         accountManager.getGlobalScoreBoard().updateScore(userEmail, finishedGame);
         // Update user score board
         accountManager.getAccount(userEmail).getUserScoreBoard().addGame(finishedGame);
         // Delete game from user's autoSavedGameList
         accountManager.getAccount(userEmail).getAutoSavedGames().deleteGame(saveId);
         return result;
     }

}
