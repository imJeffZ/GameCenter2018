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
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountManager = new AccountManager();
        loadFromFile(LoginActivity.SAVE_ACCOUNT_DETAILS);
        addRegisterButtonListener();
    }

    /**
     * on click, register the account if username does not exist, else
     * notify the user that userName already exists.
     */
    private void addRegisterButtonListener() {
        Button register = findViewById(R.id.confirmRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editPassword = findViewById(R.id.passwordregister);
                EditText editUserName = findViewById(R.id.Usernameregister);
                String password = editPassword.getText().toString();
                String userName = editUserName.getText().toString();
                if (!isValidUserName(userName)) {
                    Toast.makeText(getApplicationContext(), "Invalid Username: No Empty Username", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassWord(password)) {
                    Toast.makeText(getApplicationContext(), "Invalid Password: No Empty Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (accountManager.addAccount(userName, password)) {
                        saveToFile(LoginActivity.SAVE_ACCOUNT_DETAILS);
                        Intent gotoLogin = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(gotoLogin);
                    } else {
                        Toast.makeText(getApplicationContext(), "UserName Already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidUserName(String userName) {
        return !userName.equals("");
    }

    private boolean isValidPassWord(String passWord) {
        return !passWord.equals("");
    }

    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                AccountManager.accountMap = (HashMap) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Register activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Register activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Register activity", "File contained unexpected data type: " + e.toString());
        }
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(AccountManager.accountMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}