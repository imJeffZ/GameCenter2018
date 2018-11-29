package fall18_207project.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity {


    /***git log --author="__Your_Name_" --pretty=tformat: --numstat | awk '{ add += $1; subs += $2; loc += $1 -
     *  $2 } END { printf "added lines: %s, removed lines: %s, total lines: %s\n", add, subs, loc }'
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goToLogin);
       // addBeginAppButtonListener();
    }

//    private void addBeginAppButtonListener() {
//        Button beginAppBtn = findViewById(R.id.beginApp);
//        beginAppBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(goToLogin);
//            }
//        });
//    }
}
