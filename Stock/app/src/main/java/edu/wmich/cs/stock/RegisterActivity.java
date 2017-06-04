package edu.wmich.cs.stock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private EditText mEmail;
    private Button mRegister;
    private UserBaseHelper mUserBaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.register);
        user = new User();
        mUserBaseHelper = new UserBaseHelper(RegisterActivity.this);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmailValid(mEmail.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Email is not valid.", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if (!mUserBaseHelper.checkUser(mName.getText().toString().trim(),
                        mPassword.getText().toString().trim())) {
                    user.setName(mName.getText().toString().trim());
                    user.setEmail(mEmail.getText().toString().trim());
                    user.setPassword(mPassword.getText().toString().trim());

                    mUserBaseHelper.addUser(user);

                    Toast toast = Toast.makeText(getApplicationContext(), "User Created.", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "User already exists.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    protected boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }
}
