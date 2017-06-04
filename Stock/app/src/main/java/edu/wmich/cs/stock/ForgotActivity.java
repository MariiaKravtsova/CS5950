package edu.wmich.cs.stock;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotActivity extends AppCompatActivity {

    private EditText mEmail;
    private Button mSend;
    private UserBaseHelper mUserBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        mEmail = (EditText) findViewById(R.id.email);
        mSend = (Button) findViewById(R.id.send);
        mUserBaseHelper = new UserBaseHelper(ForgotActivity.this);


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmailValid(mEmail.getText().toString().trim())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Email is not valid.", Toast.LENGTH_SHORT);
                    toast.show();
                }

                String data = mUserBaseHelper.checkEmail(mEmail.getText().toString().trim());

                if (data == "") {
                    Toast toast = Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent iSend = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", mEmail.getText().toString().trim(), null
                    ));
                    iSend.putExtra(Intent.EXTRA_TEXT, data);
                    startActivity(iSend);
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
