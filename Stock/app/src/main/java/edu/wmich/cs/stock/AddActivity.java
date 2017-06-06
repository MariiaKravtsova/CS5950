package edu.wmich.cs.stock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private UserBaseHelper mUserBaseHelper;
    private int mUUID;
    private TextView mName;
    private TextView mQuantity;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mUserBaseHelper = new UserBaseHelper(AddActivity.this);

        Intent intent = getIntent();
        mUUID = intent.getIntExtra("uuid", 1);

        mName = (TextView) findViewById(R.id.name);
        mQuantity = (TextView) findViewById(R.id.quantity);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stock stock = new Stock();

                stock.setUserId(mUUID);
                stock.setQuantity(Integer.parseInt(mQuantity.getText().toString()));
                stock.setPrice(100.00);
                stock.setStock(mName.getText().toString().trim());

                mUserBaseHelper.addStock(stock);

                Toast toast = Toast.makeText(getApplicationContext(), "Stock Added, re-login to see it.", Toast.LENGTH_SHORT);
                toast.show();


            }
        });

    }
}
