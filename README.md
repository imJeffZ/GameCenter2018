Table of Contents
=================

* [Project Name: Game Center](#project-name-game-center)
  * [Description](#description)
  * [Installation](#installation)
  * [Brief Walkthrough](#brief-walkthrough)
    * [Registration](#registration)
    * [Login](#login)
    * [Game Center](#game-center)
      * [Profile](#profile)
    * [Start Game](#start-game)
    * [In Game](#in-game)
    * [Finishing Game](#finishing-game)
    * [User History](#user-history)
    * [Global Scoreboard](#global-scoreboard)
    * [Saved Games](#saved-games)

### Project Name: Game Center

#### Description
This is an android application that allows an registered user to play three integrated tile-games, including SlidingTiles, Matching-Cards, and Game2048.

#### Installation

+ Step 1: Install Android Studio.

+ Step 2: Clone and open the project.

  + Open Android Studio, select `Check out project from Version Control -> Git`.

    ![android_studio_start](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/android_studio_start.png)

  + Type https://github.com/imJeffZ/GameCenter2018Public in the `URL` box. Then click `Clone`.

    ![git_clone](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/git_clone.png)

  + A prompt to create new project would be shown, select `yes`.

    ![create_new_project](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/create_new_project.png)

  + Select `Import project from external model`, then select `Gradle`.

    Click `Next`.

    ![import_gradle](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/import_gradle.png)

  + On the next page, select `···` next to the `Gradle Project` box.

    ![gradle_setup_1](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/gradle_setup_1.png)

  + Select `GameCenter2018Public -> Phase2 -> GameCenter/` to open the cloned application.

    Then click `OK`.

    ![gradle_location](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/gradle_location.png)

  + Click `Finish`.

    ![gradle_finish](C:\Users\46138\Documents\GitHub\GameCenter2018Public\readme_resources/setup_screenshots/gradle_finish.png)

+ Step 3: Configure Android Studio.

  **Configure Android SDK.**

  + After we've open the project, the window of Android Studio would open up.

    If prompt to upgrade Gradle, select `Don't remind me again for this project`.

    ![android_studio_prompt_gradle](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/android_studio_prompt_gradle.png)

  + Download the required Android 8.1 (Oreo) API level 27.

    On the top navigation panel, select `Tools -> SDK Manager`.

    ![android_studio_tools](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/android_studio_tools.png)

  + Select `Android 8.1 (Oreo)` . Click `OK`.

    ![android_sdk](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/android_sdk.png)

  + When the installation is complete, click `Finish`.

  **Configure Android Virtual Device.**

  + On the navigation panel, select `Tools -> AVD Manager`.

    ![android_studio_tools](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/android_studio_tools.png)

  + Click `Create New Virtual Device`.

  + Select `Pixel 2`. Click `Next`.

    ![select_pixel2](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/select_pixel2.png)

  + Select `Oreo`. Click `Next`. Click `Finish`.

+ Step 4: Run the Game.

  + On the main window of Android Studio, click on the green triangular button.

    ![run_button](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/run_button.png)

  + Select `Pixel 2 API 27`. Click `OK`.

    ![select_run_device](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/setup_screenshots/select_run_device.png)

  + The virtual Pixel 2 device would be running. Wait until the application is successfully built.

    This would take several minutes.

  + When finished, the login screen of the application would open up in the virtual device.

    ![login_screen](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/login.png)

  + Proceed to the next section **Brief Walkthrough**.

#### Brief Walkthrough

##### Registration
First, sign up with a username and password to get an account.

**There are several restrictions here:**

+ None of the email, username, and password can be empty.
+ Email must be properly formatted in the form akin to abc123@mail.com, such limitation exist because we are utilizing FireBase to store registration information. 

*If this is your first time signing up for this application, there might be a prompt for you to update Google Play Service. Please click on the prompt and proceed to update Google Play Service. **Our application requires updated Google Play Service to run**. Please re-run the application after Google Play Service has been updated.*

![registration](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/registration.png)

##### Login

After registration, you will be redirected back to login screen, login with your account credentials to proceed.
![login](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/login.png)

##### Game Center
Once you logged in, you will be redirected to the Game Center of games.

You can view your profile or select one of the three games to play.

![gamecenter](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/gamecenter.png)

###### Profile 

![profile](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/profile.png)

##### Start Game

We would take *SlidingTiles* in the *Game Center* as an example.

![slidingtiles_start](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/slidingtiles_start.png)

+ To play the game:

  Click on `NEW GAME` .
  We have 3 complexities to choose from: *3x3*, *4x4*, *5x5*.
  Select anyone of them.

  ![st_complexity](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/st_complexity.png)

+ To view saved games:

  Click `LOAD GAME`. Then select `LOAD AUTOSAVED GAME` or `LOAD SAVED GAME`.

  We have implemented both **manual-save** and **auto-save** functionality. The difference between these two type of save would be discussed later in *Save Game* section.

##### In Game

At the beginning of each new game, we shuffle the board such that it is always solvable to make sure the user can win.

**There is a `UNDO` button that allows you to undo changes you've made.**

The timer tells time elapsed in the game, and the `STEPS` shows how many steps you've taken in game.

*There is an `SAVE` button, for which would be discussed later.*

![st_game](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/st_game.png)

The winning condition is to arrange all tiles with increasing order.
E.g. The winning condition for *3x3 SlidingTiles* would be to have the tiles arranged like the following:

```
1 2 3
4 5 6
7 8 \
```

##### Finishing Game
Once you have win/lose a game, a message box would pop out to notify you. Follow the prompt to proceed to the *Score Page*.

![gamefinish](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/gamefinish.png)

In the score page, you will be shown a score calculated based on two factors: **how many moves you've taken** and **how much time you spend**.

![gamescore](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/gamescore.png)

Click anywhere to go back to game center.

##### User History

You can view your personal scoreboard in *User History*, for which can be accessed through the bottom navigation panel.

![userhistory_button](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/userhistory_button.png)

Select the type of game that you want to see completed games from.

![userhistory_drawer](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/userhistory_drawer.png)



![userhistory_dropdown](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/userhistory_dropdown.png)



![userhistory_record](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/userhistory_record.png)

**You can select one of the record to replay the completed game.**

After the completed game is relaunched, you can actually use the undo button to go back step by step to see what changes you've made.

Also, you can restart the game to try to score higher, **and the restarted game is considered to be a new game.**

![replay](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/replay.png)

##### Global Scoreboard

There is also a `Global ScoreBoard` that records the scores and player email of every completed game.

![globalscoreboard_button](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/globalscoreboard_button.png)

![globalscoreboard_record](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/globalscoreboard_record.png)

##### Saved Games

Users can view all saved games by clicking the `LOAD GAME` button in a game's welcome page.

![load_games](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/load_games.png)

There are two types of save:

+ **Manual Save**: The user press the `SAVE` button in game to save the current game status.
+ **Auto Save**: Every game is saved automatically upon very change of game status.
  Auto-saved games are kept in a different place from *manual-saved* games.

Click `LOAD AUTOSAVE GAME` or `LOAD SAVED GAME` to proceed to the *Saved Games* page.

![savedgames](https://raw.githubusercontent.com/imJeffZ/GameCenter2018Public/master/readme_resources/application_screenshots/savedgames.png)

You can click on any record to go back to your saved game.