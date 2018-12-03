# Group_0468 Team Agreement

---

### Members:

+ Shaokan Chu
+ Nick Jiang
+ Marvel Zhang
+ Sailing Zhou
+ Jefferson Zhong



##### Ways of Contact

+ Wechat group chat
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
    - one person will be in charge of overall meeting flows, recording every member's word,
      grouping up the ideas of all members, making sure everyone have joined conversation
      and no one was ignored.

Other than lab time, we meet during the weekends, usually Sunday to work together on the project.

Upon the first two meetings, we collectively decided on the overall structure of the application,
and each member has taken part of the application to their responsibility.

After the first two meetings, we often discuss via WeChat about development progress,
asks questions about implementation details, and help each other.


#### Member Responsable Part

Name: Jingqi Zhang
Prefered English Name: Marvel
Utorid: zhan4868
Student ID: 1004117671
Eml Add: jingqi.zhang@mail.utoronto.ca
Git Global Name: Marvel.Z
Git commit ammount: 127

Marvel is responsable for 3 parts:

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
VeiwController: MovementController ---- ViewModel: SlidingtileGameActivity, MatchingCardsGameActivity, Game2048Activity )
For MVVM design pattern, Marvel contruct the structure of our app as each backend model know nothing about the view, viewmodel and view's controller, activitity classes connects view and update view according to user's behavior as a view model, and controller controll the main view updating.

In addition, she designed Observable pattern for MatchingCards game:
(Observer: MatchingCardsGameActivity ---- Subject Using Observer: MatchingBoard)

Last but not the least, Marvel design the MatchingCards whole game, and write all the classes needed like Cards, MatchingBoard, MatchingCards, MatchingCardStartActivity,  MatchingCardsGameActivity and its controller. She made MatchingCards Game 3 game complexities: 4x4, 5x5, 6x6. and each game has 2 state: start state (show all the card front for user memorizing) and matching state (all cards turn down and start matching 2 cards wiith same value). In order to  increase the difficulty, she create bomb mode in 5x5 and 6x6, which is different from traditional card matching games.



