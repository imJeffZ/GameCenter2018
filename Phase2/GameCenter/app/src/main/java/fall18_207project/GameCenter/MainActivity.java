package fall18_207project.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = new Main();
        setContentView(R.layout.activity_main);
        addBeginAppButtonListener();
    }

    private void addBeginAppButtonListener() {
        Button beginAppBtn = findViewById(R.id.beginApp);
        beginAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                goToLogin.putExtra("accountManager", main.getAccountManager());
                startActivity(goToLogin);
            }
        });
    }
}
