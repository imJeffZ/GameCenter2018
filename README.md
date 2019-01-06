### Project Name: Game Centre
#### Description:
This is an android application that allows an registered user to play three integrated tile-games, including SlidingTiles, MatchingCards, and Game2048.

#### Installation:

+ Step 1: Install Android Studio.

+ Step 3: Open Android Studio, select `Check out project from Version Control`, then select the `GameCenter2018Public -> Phase2 -> GameCenter/` to open the cloned application.

+ Step 4: Please Create New Virtual Device Pixel2. Try to run the code from right up green triangle button, if there is Pixel2 Device please use it, to display our beautiful UI perfectly, otherwise 
  choose Oreo27 then press finish and you can run.
  
#### Breif Walkthrough
##### Registration
First, sign up with a username and password to get an account.
**There are several restrictions here:**
+ None of the email, username, and password can be empty.
+ Email must be propery formatted in the form akin to abc123@mail.com, such limitation exist because we are utilizing FireUser to store registration information. 
[Insert Registration Photo]

##### Login
After registration, you will be redirected back to login screen, login with your account to proceed.
[Insert Login Info]

##### GameCenter
Once you logged in, you will be redirected to the GameCenter of games.
Select one of the three games in GameCenter to play.
[Insert GameCenter Photo]

##### Game Starting Page
We would take *SlidingTiles* as an example.
[Insert SlidingTilesStart Activity]

Click on `New Game` to play.
We have 3 complexities to choose from: *3x3*, *4x4*, *5x5*.
Select anyone of them.
[Insert SlidingTiles Complexities Photo]

###### In Game functionality
At the beginning of each new game, we shuffle the board such that it is always solvable to make sure the user can win.

**There is a `UNDO` button that allows you to undo changes you've made.**

The timer tells time elapsed in the game, and the `STEPS` shows how many steps you've taken in game.

*There is an `SAVE` button, for which would be discussed later.*

[Insert SlidingTiles]

The winning condition is to arrange all tiles with increasing order.
E.g. The winning condition for *3x3 SlidingTiles* would be to have the tiles arranged like the following:
```
1 2 3
4 5 6
7 8 \
```

###### Finishing Game
Once you have completed a game, a message box would pop out to notify you. Follow the prompt to proceed to the *Score Page*.
[Insert Game Complete Message Box]

In the score page, you will be shown your score calculated 
[]
###### Save Game
There are two types of save:
+ Manual Save: The user press the `SAVE` button in game to save the current game status.
The user can view the manual saved games in the starting page. There is an 'SAVED GAMES' button.

+ Auto Save: Every game is saved automatically upon very change of status.
Auto-saved games are kept in a different place from *manual-saved* games.
The user can view the manual saved games in the starting page. There is an 'AUTO SAVE' button.

There will be timers and number of moves you have done on the top, be careful,
your score after you finish the game will be determined by these two factors, the
faster the better, less moves are better as well.
If you win the game, you will see a pop up message, follow it's instruction to next
page, you will see your score. **CAUTION:** if you decide to leave before jumping to
redirection screen, your score will not be recorded.

From GameCenter you can go into Scoreboard. it displays
the highest score of a user has for each game, or the
ranking of all users that played the game of same complexity. **Only top10**
**will be recorded at all time.**
You can select which game you want to view by using the
drop-down menu in the scoreboard screen.

