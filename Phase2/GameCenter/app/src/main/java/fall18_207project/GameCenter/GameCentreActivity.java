package fall18_207project.GameCenter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameCentreActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    public static String CURRENT_ACCOUNT = "";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game_center:
                    break;
                case R.id.navigation_user_history:
                    Intent tmp2 = new Intent(GameCentreActivity.this, UserHistoryActivity.class);
                    startActivity(tmp2);
                    break;
                case R.id.navigation_global_scoreboard:
                    Intent tmp3 = new Intent(GameCentreActivity.this, ScoreBoardActivity.class);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        addGameButtonListener();
        addMatchingCardsGameButtonListener();
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
        if (id == R.id.nav_reset){
            AccountManager.accountMap.get(CURRENT_ACCOUNT).setUserName(update);
        } else if (id == R.id.nav_intro){
            AccountManager.accountMap.get(CURRENT_ACCOUNT).getProf().setIntro(update);
        } else if (id == R.id.nav_password){
            AccountManager.accountMap.get(CURRENT_ACCOUNT).setPassword(update);
        }

    }

    private void updateProfileShow(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        TextView textUser = navHeader.findViewById(R.id.profileUser);
        textUser.setText(AccountManager.accountMap.get(CURRENT_ACCOUNT).getUserName());

        TextView textIntro = navHeader.findViewById(R.id.profileIntro);
        textIntro.setText(AccountManager.accountMap.get(CURRENT_ACCOUNT).getProf().getIntro());
        //ImageView userImg = navHeader.findViewById(R.id.profileImg);
        //userImg.setImageBitmap(AccountManager.accountMap.get(CURRENT_ACCOUNT).getProf().getAvatarImage());

        TextView textPlayTime = navHeader.findViewById(R.id.profileTime);
        textPlayTime.setText("Play Time in Total: " +
                AccountManager.accountMap.get(CURRENT_ACCOUNT).getProf().getTotalPlayTime());
    }

    private void addGameButtonListener() {

        ImageButton ButtonGame = findViewById(R.id.SlidingTileGame);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    private void addMatchingCardsGameButtonListener() {

        ImageButton ButtonGame = findViewById(R.id.MatchingCardsGame);
        ButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMatchingCardsGame();
            }
        });
    }

    private void switchToGame() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }

    private  void switchToMatchingCardsGame(){
        Intent tmp = new Intent(this, MatchingCardStartActivity.class);
        startActivity(tmp);
    }

    private void switchToLogin() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);

    }

}
