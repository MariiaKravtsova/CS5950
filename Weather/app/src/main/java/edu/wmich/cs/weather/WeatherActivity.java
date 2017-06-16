/*
 * CS 5950: Homework 4
 * date: June 15, 2017
 * author: Mariia Kravtsova
 * Allow users to enter a location ZIP code and retrieve the current temperature for that location. The application should display the current temperature on the application’s UI.
 * Allow users to receive notifications with the current time and temperature through the notification drawer every 1 minute when the application is not running. Only one notification
 * that conveys the latest retrieved details should be available through the notification drawer. When the user clicks on the notification, your application should be brought to the foreground.
 * Allow users to receive notifications after a reboot of their devices.
 * Stop generating notifications when the application is running in the foreground.
 */

package edu.wmich.cs.weather;

import android.content.Context;
import android.content.Intent;
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
    private String temp;

    public static Intent newIntent(Context context) {
        return new Intent(context, WeatherActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mButton = (Button) findViewById(R.id.ok_button);
        mTempTextView = (TextView) findViewById(R.id.temp_textview);
        mZipCode = (EditText) findViewById(R.id.zip_text);

        if (QueryPreferences.getStoredQuery(getApplicationContext()) != null) {
            mZipCode.setText(QueryPreferences.getStoredQuery(getApplicationContext()));
            new FetchWeatherTask().execute(QueryPreferences.getStoredQuery(getApplicationContext()));
        }


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryPreferences.setStoredQuery(getApplicationContext(), mZipCode.getText().toString());
                new FetchWeatherTask().execute(QueryPreferences.getStoredQuery(getApplicationContext()));

            }
        });

        PollService.setServiceAlarm(getApplicationContext(), false);

    }

    @Override
    public void onStop() {
        super.onStop();
        PollService.setServiceAlarm(getApplicationContext(), true);
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            return new WeatherFetcher().fetchItems(strings[0], getApplicationContext());
        }

        @Override
        protected void onPostExecute(String result) {
            mTempTextView.setText("Temp of " + result + "\u00b0 Fahrenheit");
        }
    }
}
