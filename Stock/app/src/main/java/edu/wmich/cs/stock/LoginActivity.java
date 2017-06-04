package edu.wmich.cs.stock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private Button mForgot;
    private UserBaseHelper mUserBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mName = (EditText) findViewById(R.id.name);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mForgot = (Button) findViewById(R.id.forgot);
        mRegister = (Button) findViewById(R.id.register);
        mUserBaseHelper = new UserBaseHelper(LoginActivity.this);

        mUserBaseHelper.createTest();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        mForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iForgot = new Intent(getApplicationContext(), ForgotActivity.class);
                startActivity(iForgot);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(iRegister);
            }
        });
    }

    private void verify() {
        if (mUserBaseHelper.checkUser(mName.getText().toString().trim(),
                mPassword.getText().toString().trim())) {

            Intent iStocks = new Intent(getApplicationContext(), StockActivity.class);
            startActivity(iStocks);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Wrong username or password.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
