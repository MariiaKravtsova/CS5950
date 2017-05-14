/*
 * CS 5650: Homework 1
 * date: May 14, 2017
 * author: Mariia Kravtsova
 * Referenced: http://www.coders-hub.com/2013/09/how-to-make-tempreture-conversion.html#.WRjKonUrJhF
 */

package edu.wmich.cs.tempconversion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class tempActivity extends AppCompatActivity {

    private TextView result;
    private EditText input;
    private Double temp = 0.0;
    private Button convert;

    private RadioButton fromC;
    private RadioButton fromF;
    private RadioButton fromK;
    private RadioButton fromR;

    private RadioButton toC;
    private RadioButton toF;
    private RadioButton toK;
    private RadioButton toR;

    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        result = (TextView)findViewById(R.id.result);
        input = (EditText)findViewById(R.id.input);
        formatter = new DecimalFormat("#0.00");

        convert = (Button)findViewById(R.id.convertButton);

        fromC = (RadioButton)findViewById(R.id.fromC);
        fromF = (RadioButton)findViewById(R.id.fromF);
        fromK = (RadioButton)findViewById(R.id.fromK);
        fromR = (RadioButton)findViewById(R.id.fromR);

        toC = (RadioButton)findViewById(R.id.toC);
        toF = (RadioButton)findViewById(R.id.toF);
        toK = (RadioButton)findViewById(R.id.toK);
        toR = (RadioButton)findViewById(R.id.toR);

        convert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                temp = Double.parseDouble(input.getText().toString());

                if (fromC.isChecked() && toF.isChecked()) {
                    final Double convTemp = ((temp * 9.0) / 5.0 + 32.0);
                    result.setText(temp.toString() + "\u00b0C = " + formatter.format(convTemp)  + "\u00b0F");
                }
                else if (fromC.isChecked() && toK.isChecked()) {
                    final Double convTemp = temp + 273.15;
                    result.setText(temp.toString() + "\u00b0C = " + formatter.format(convTemp)  + "\u00b0K");
                }
                else if (fromC.isChecked() && toR.isChecked()) {
                    final Double convTemp = (temp + 273.15) * (9.0 / 5.0);
                    result.setText(temp.toString() + "\u00b0C = " + formatter.format(convTemp) + "\u00b0R");
                }
                else if (fromC.isChecked() && toC.isChecked()) {
                    result.setText(temp.toString() + "\u00b0C = " + temp.toString() + "\u00b0C");
                }
                else if (fromF.isChecked() && toC.isChecked()) {
                    final Double convTemp = ((temp - 32.0) * 5.0 / 9.0);
                    result.setText(temp.toString() + "\u00b0F = " + formatter.format(convTemp) + "\u00b0C");
                }
                else if (fromF.isChecked() && toF.isChecked()) {
                    result.setText(temp.toString() + "\u00b0F = " + temp.toString() + "\u00b0F");
                }
                else if (fromF.isChecked() && toK.isChecked()) {
                    final Double convTemp = ((temp + 459.67) * 5.0 / 9.0);
                    result.setText(temp.toString() + "\u00b0F = " + formatter.format(convTemp) + "\u00b0K");
                }
                else if (fromF.isChecked() && toR.isChecked()) {
                    final Double convTemp = (temp + 459.67);
                    result.setText(temp.toString() + "\u00b0F = " + formatter.format(convTemp) + "\u00b0R");
                }
                else if (fromK.isChecked() && toC.isChecked()) {
                    final Double convTemp = temp - 273.15;
                    result.setText(temp.toString() + "\u00b0K = " + formatter.format(convTemp) + "\u00b0C");
                }
                else if (fromK.isChecked() && toF.isChecked()) {
                    final Double convTemp = (temp * (9.0/5.0)) - 459.67;
                    result.setText(temp.toString() + "\u00b0K = " + formatter.format(convTemp) + "\u00b0F");
                }
                else if (fromK.isChecked() && toK.isChecked()) {
                    result.setText(temp.toString() + "\u00b0K = " + temp.toString() + "\u00b0K");
                }
                else if (fromK.isChecked() && toR.isChecked()) {
                    final Double convTemp = temp * (9.0/5.0);
                    result.setText(temp.toString() + "\u00b0K = " + formatter.format(convTemp) + "\u00b0R");
                }
                else if (fromR.isChecked() && toC.isChecked()) {
                    final Double convTemp = (temp - 491.67) * (5.0 - 9.0);
                    result.setText(temp.toString() + "\u00b0R = " + formatter.format(convTemp) + "\u00b0C");
                }
                else if (fromR.isChecked() && toF.isChecked()) {
                    final Double convTemp = temp - 459.67;
                    result.setText(temp.toString() + "\u00b0R = " + formatter.format(convTemp) + "\u00b0F");
                }
                else if (fromR.isChecked() && toK.isChecked()) {
                    final Double convTemp = temp * (5.0/9.0);
                    result.setText(temp.toString() + "\u00b0R = " + formatter.format(convTemp) + "\u00b0K");
                }
                else if (fromR.isChecked() && toR.isChecked()) {
                    result.setText(temp.toString() + "\u00b0R = " + temp.toString() + "\u00b0R");
                }
            }
        });


    }
}
