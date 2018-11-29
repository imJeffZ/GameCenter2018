package fall18_207project.GameCenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameCentreActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    public static String userEmail = "";
    private FirebaseAuth firebaseAuth;
    private AccountManager accountManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    break;
                case R.id.navigation_user_history:
                    Intent tmp2 = new Intent(GameCentreActivity.this, UserHistoryActivity.class);
//                    tmp2.putExtra("userEmail", userEmail);
                    startActivity(tmp2);

                    break;
                case R.id.navigation_global_scoreboard:
                    Intent tmp3 = new Intent(GameCentreActivity.this, GlobalScoreBoardActivity.class);
//                    tmp3.putExtra("userEmail", userEmail);
                    startActivity(tmp3);
                    break;
            }
            return false;
        }
    };

    private Handler myHand = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what ==0){
                updateProfileShow();
            }
            sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);
        // Serialization
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
//        if (getIntent().hasExtra("userEmail")) {
//            userEmail = getIntent().getStringExtra("userEmail");
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up fireBaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        updateProfileShow();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        addSlidingTilesGameButtonListener();
        addMatchingCardsGameButtonListener();
        addGame2048ButtonListener();
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            Intent gotoLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(gotoLogin);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readFromSer(LoginActivity.ACCOUNT_MANAGER_DATA);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String editTitle;

        if (id == R.id.nav_image) {
            // Handle the camera action
        } else if (id == R.id.nav_intro) {
            editTitle = "Editing Information";
            editProfileByDialog(id, editTitle);
            myHand.sendEmptyMessageDelayed(0, 1000);


        } else if (id == R.id.nav_reset) {
            editTitle = "Editing User Name";
            editProfileByDialog(id, editTitle);
            myHand.sendEmptyMessageDelayed(0, 1000);

        }else if (id == R.id.nav_password){
            editTitle = "Editing Password";
            editProfileByDialog(id, editTitle);

        } else if (id == R.id.nav_logout) {
            switchToLogin();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void editProfileByDialog(int id, String profTitle){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GameCentreActivity.this);
        dialogBuilder.setTitle(profTitle);
        final int i = id;
        @SuppressLint("InflateParams") final View profView = LayoutInflater.from(GameCentreActivity.this).inflate(R.layout.profile_dialog, null);
        dialogBuilder.setView(profView);
        dialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener(){
            @Override
                  public void onClick(DialogInterface dialogNeeded, int buttonId){
                EditText update = (EditText)profView.findViewById(R.id.update);
                updateProfile( i, update.getText().toString());
            }
                });
        dialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogNeeded, int buttonId){
                        dialogNeeded.dismiss();
                    }
                });
        dialogBuilder.create().show();

    }

    private  void  updateProfile(int id, String update){
        if (accountManager.getAccount(userEmail) != null) {
            if (id == R.id.nav_reset){
                accountManager.getAccount(userEmail).setUserName(update);
            } else if (id == R.id.nav_intro){
                accountManager.getAccount(userEmail).getProf().setIntro(update);
            } else if (id == R.id.nav_password){
                accountManager.getAccount(userEmail).setPassword(update);
            }
            saveToFile(LoginActivity.ACCOUNT_MANAGER_DATA);
        }
    }

    private void updateProfileShow(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        TextView textUser = navHeader.findViewById(R.id.profileUser);
        if (accountManager.getAccount(userEmail) != null) {
            textUser.setText(accountManager.getAccount(userEmail).getUserName());

            TextView textIntro = navHeader.findViewById(R.id.profileIntro);
            textIntro.setText(accountManager.getAccount(userEmail).getProf().getIntro());
            //ImageView userImg = navHeader.findViewById(R.id.profileImg);
            //userImg.setImageBitmap(AccountManager.accountMap.get(CURRENT_ACCOUNT).getProf().getAvatarImage());

            TextView textPlayTime = navHeader.findViewById(R.id.profileTime);
            textPlayTime.setText("Play Time in Total: " +
                    accountManager.getAccount(userEmail).getProf().getTotalPlayTime());
        }

    }

    private void addSlidingTilesGameButtonListener() {

        ImageButton ButtonGame = findViewById(R.id.SlidingTileGame);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTilesStartingAct();
            }
        });
    }

    private void addMatchingCardsGameButtonListener() {

        ImageButton ButtonGame = findViewById(R.id.MatchingCardsGame);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMatchingCardsStartingAct();
            }
        });
    }

    private void addGame2048ButtonListener() {

        ImageButton ButtonGame = findViewById(R.id.Game2048);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame2048StartingAct();
            }
        });
    }

    private void switchToSlidingTilesStartingAct() {
        Intent tmp = new Intent(this, SlidingTileStartingActivity.class);
        tmp.putExtra("userEmail", userEmail);
        startActivity(tmp);
    }

    private  void switchToMatchingCardsStartingAct(){
        Intent tmp = new Intent(this, MatchingCardStartActivity.class);
        tmp.putExtra("userEmail", userEmail);
        startActivity(tmp);
    }

    private void switchToGame2048StartingAct(){
        Intent tmp = new Intent(this, Game2048StartActivity.class);
        tmp.putExtra("userEmail", userEmail);
        startActivity(tmp);
    }

    private void switchToLogin() {
        firebaseAuth.signOut();
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    private void readFromSer(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream in = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) in.readObject();
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("GameCentre activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("GameCentre activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("GameCentre activity", "File contained unexpected data type: " + e.toString());
        }
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
