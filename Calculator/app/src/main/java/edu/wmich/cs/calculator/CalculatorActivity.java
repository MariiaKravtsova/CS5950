package edu.wmich.cs.calculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

public class CalculatorActivity extends AppCompatActivity implements InputFragment.InputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    @Override
    public int calculateResult(Numbers n) {
        ButtonsFragment buttonsFragment = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_buttons);
        n.z = buttonsFragment.operation(n.x, n.y);

        return n.z;
    }
}
