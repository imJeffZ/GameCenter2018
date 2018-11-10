package fall18_207project.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    public static final String SAVE_ACCOUNT_DETAILS = "account_detail.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        readFromSer(SAVE_ACCOUNT_DETAILS);
        addLoginButtonListener();
        addRegisterButtonListener();

    }


    private void readFromSer(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream in = new ObjectInputStream(inputStream);
                AccountManager.accountMap = (HashMap) in.readObject();
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("Login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    private void addLoginButtonListener() {
        Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = findViewById(R.id.userNametext);
                EditText passWord = findViewById(R.id.passwordtext);
                String userNameValue = userName.getText().toString();
                String passWordValue = passWord.getText().toString();
                if (!AccountManager.accountMap.containsKey(userNameValue)) {
                    Toast.makeText(getApplicationContext(), "You are not a user yet!", Toast.LENGTH_SHORT).show();
                } else if (!(passWordValue.equals(AccountManager.accountMap.get(userNameValue).getPassword()))) {
                    Toast.makeText(getApplicationContext(), "Wrong password or userName", Toast.LENGTH_SHORT).show();
                } else {
                    StartingActivity.CURRENT_ACCOUNT = userNameValue;
                    GameCenterActivity.CURRENT_ACCOUNT = userNameValue;
                    Intent goToCenter = new Intent(getApplicationContext(), GameCenterActivity.class);
                    goToCenter.putExtra("accountName", userNameValue);
                    startActivity(goToCenter);
                }

            }
        });
    }

    private void addRegisterButtonListener() {
        Button register = findViewById(R.id.registerbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(goToRegister);
            }
        });
    }
}
