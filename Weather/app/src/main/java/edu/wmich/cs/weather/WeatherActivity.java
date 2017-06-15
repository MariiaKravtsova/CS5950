package edu.wmich.cs.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class WeatherActivity extends AppCompatActivity {

    private EditText mZipCode;
    private Button mButton;
    private TextView mTempTextView;
    private String mZipString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mButton = (Button) findViewById(R.id.ok_button);
        mTempTextView = (TextView) findViewById(R.id.temp_textview);
        mZipCode = (EditText) findViewById(R.id.zip_text);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchWeatherTask().execute(mZipCode.getText().toString());
            }
        });
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {


            new WeatherFetcher().fetchItems(strings[0], getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mTempTextView.setText(result);

        }
    }
}
