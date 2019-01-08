# Group_0609 Team Agreement

---

### Members:

+ Shaokan Chu
+ Nick Jiang
+ Marvel Zhang
+ Sailing Zhou
+ Jefferson Zhong



##### Ways of Contact

+ WeChat group chat
+ phone


**Rules of Conduct:**

1. Respect each other's idea.
2. Active engagement.
3. Decide on high-level structural design together.
4. Collectively discuss on progress daily.
5. Equally distributed workloads.
6. Help each other out.
7. Attending meeting is mandatory.

Holding meeting:

+ present updated UML either electronically or by paper

    - one person will be in charge of overall meeting flows, recording every member's idea,
      grouping up the ideas of all members, making sure everyone joined the conversation
      and no one was ignored.

Other than lab time, we met during the weekends, usually on Sunday to work together on the project.

In the first two meetings, we collectively decided on the overall structure of the application,
and each member has taken part of the application to their responsibility.

After the first two meetings, we often discuss via WeChat about development progress,
ask questions about implementation details, and help each other.


#### Members' responsibilities
---

Name: Jingqi Zhang
Preferred English Name: Marvel
Utorid: zhan4868
Student ID: 1004117671
Eml Add: jingqi.zhang@mail.utoronto.ca
Git Global Name: Marvel.Z
Git commit ammount: 127

Marvel is responsible for 3 parts:

1.The frontend classes of our app (except Game2048 and Login/Register parts)
2.The design pattern and structure of our app (especially for the frontend parts)
3.The new game: MatchingCards(including frontend and backend parts).

Firstly, Marvel wrote the frontend structures: 

Almost all activity classes including GameCentreActivity, StartingActivity of 3 games (with its interface GameStartingActivity), UserHistoryActivity, GlobalScoreboardActivity and SavedGamesActivity.  In GameCentreActivity, UserHistoryActivity and GlobalScoreboardActivity, she added the Navigation Bars,and especially in GameCentreActivity, Marvel added a User Profile Drawer for our app. In addition, she used choosing dialogs for loag game and new game if needed to choose the complexity in StartingActivity of 3 games. She also implemented spinners to seperate the scoreboard shown different games in UserHistoryActivity and GlobalScoreboardActivity. Meanwhile, she added each activity its own controllers(GameCentreController, GameStartController of 3 games, UserHistoryController and SavedGamesController) and make each Activity class only a view (know nothing about the backend logic )and its related controller class update the activity class according to the user behavior.

Secondly, Marvel adapted some design patterns for our app: 

She used MVC design patten for :
GameCenter (Model: Profile ---- View: GameCentreActivity ---- Controller: GameCentreController),
UserHistory (Model: Account ---- View: UserHistoryActivity ---- Controller: UserHistoryController),
SavedGames(Model: GameManager ----View: SavedGamesActivity ---- Controller: SavedGamesController),
GameStarting(Model: Constructor of 3 games ---- View: StartingActivity of 3 games ---- Controller: GameStartController of 3 games)
For MVC design patten, Marvel construct the structure of our app as each model know nothing about the view and controller, activitity classes only conain view setting and controller connects view and model.

She also added MVVM design pattern for GameActivity for 3 games: 
(Model: Slidingtiles, MatchingCards, Game2048 ---- View: GestureDetectedGridView ---- 
ViewController: MovementController ---- ViewModel: SlidingtileGameActivity, MatchingCardsGameActivity, Game2048Activity )
For MVVM design pattern, Marvel constructed the structure of our app as each backend model knew nothing about the view, viewmodel and view's controller, activity classes connects view and update view according to user's behavior as a view model, and controller control the main view updating.

In addition, she designed Observable pattern for MatchingCards game:
(Observer: MatchingCardsGameActivity ---- Subject Using Observer: MatchingBoard)

Last but not least, Marvel designed the MatchingCards whole game, and wrote all the classes needed like Cards, MatchingBoard, MatchingCards, MatchingCardStartActivity,  MatchingCardsGameActivity and its controller. She made MatchingCards Game 3 game complexities: 4x4, 5x5, 6x6. and each game has 2 states: start state (show all the card front for user memorizing) and matching state (all cards turn down and start matching 2 cards wiith same value). In order to  increase the difficulty, she create bomb mode in 5x5 and 6x6, which is different from traditional card matching games.

---

Name: Jefferson Zhong
Utorid: zhongjef
Student ID: 1003946000
Email: jefferson.zhong@mail.utoronto.ca
Git Name: Jefferson Zhong
Git commit ammout: 107

Jefferson's main contributions:

+ Implemented backend logic relating to accounts

  - AccountManager that stores and manages Accounts

  - Account that stores an user's email, username, password, Profile, userscoreboard, auto-saved games, and user-saved games.
  - Profile that stores user's avatar, self intro and total played time
  - Absrtact GameManager that keeps track of games, this class has an abstract method addGame.
  - NonDuplicateGameManager that extends GameManager. This class utilzed the abstract method addGame such that no duplicate of the same game (determined by saveId) is of use in Account's user scoreboard, auto-saved games, and user-saved games.
  - Abstract Game class.

+ Implemented current account controller that keeps track of current account and 

  - This current account controller implements the Singleton design pattern.
  - Using singleton, this controller ensures that the current user is updated in time upon change.
  - This singleton controller provides a unique entry/exit point for the app's data. Many activities and controllers utilize read/write file methods to transmit data between each other, by using a global singleton controller, all other controllers need not to worry about how to retrive current user data from locally stored serialization files.
  - Connected singleton controller to MVCs and MVVMs.

+ Implemented backend for Multi-save for an account's auto-saved games and user-saved games, also connected them to the corresponding controllers.

+ Implemented scoreboards

  + GlobalScoreboard, for which globally keeps track of finished games and those games' player's user email.
  + Backend for userscoreboard (userhistory) and connected it to the corresponding userhistory controller.

+ Implemented replay functionality for userscoreboard (userhistory), so that when user clicks on an item from users scoreboard, they would have be able to replay the game.

+ Added backend logic of the timer GameChronometer (Adopted from github). Also make use of the timer in SlidingTilesActivity.


Name: ShaoKan Chu
Utorid: chushaok
Student ID: 1004137365
Email: shao.chu@mail.utoronto.ca
Git Name: ShaoKan
Git commit ammout: 65

ShaoKan's main contributions:

+ Working on fixing all the bugs he found:
    - gameManager mix up the autoSave and save problem.
    - gameManager does not save the game correctly after saving for the 2nd time.
    - game 2048 undo not working as intended and jump backs at random
    - When game breaks, it saved to userSave instead of autoSave
    - fixed timer and move counter reset on saved games problem

+ Working on backend logic of multi save and gameManager, so it properly displays the list corresponding to games, and connect them to frontend activity.

+ Work on Game finish activity, so it will calculate the score, display and save them to where they belongs.

+ attempting to implement the Firebase database, re-designed the login and register activity to connect to online firebase.

+ Handling account log in and log out issues.

+ Re-design the Frontend activity structure to adapt to new backend logic of accounts and data management.

+ adding additional game feature into the game including restart.

Name: Hang Zhou
Utorid : zhouha25
Student ID: 1004116887
Email: hang.zhou@mail.utoronto.ca
Git Name: hangzhou

HangZhou's main contributions:

+ Working on Game2048:
    - Game2048 Backend logic including the autosave , save and undo feature
    - Game2048 Frontend the swiping move, with coloured tiles

+ Made Tile an abstract class, made separate subclasses of tiles for each kind of Game
