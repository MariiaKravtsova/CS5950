package edu.wmich.cs.calculator;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ButtonsFragment extends Fragment {

    private static final String KEY_A = "a";
    private static final String KEY_S = "s";
    private static final String KEY_M = "m";
    private static final String KEY_D = "d";

    private RadioButton mAdditionButton;
    private RadioButton mSubtractButton;
    private RadioButton mMultiplicationButton;
    private RadioButton mDivisionButton;
    private RadioGroup mButtonGroup;

    boolean addition = false;
    boolean subtraction = false;
    boolean multiplication = false;
    boolean division = false;

    Numbers mNumber = new Numbers(0, 0, 0);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        mButtonGroup = (RadioGroup) v.findViewById(R.id.button_group);
        mAdditionButton = (RadioButton) v.findViewById(R.id.addition);
        mSubtractButton = (RadioButton) v.findViewById(R.id.subtraction);
        mDivisionButton = (RadioButton) v.findViewById(R.id.division);
        mMultiplicationButton = (RadioButton) v.findViewById(R.id.multiplication);

        if (savedInstanceState != null) {
            addition = savedInstanceState.getBoolean(KEY_A);
            subtraction = savedInstanceState.getBoolean(KEY_S);
            multiplication = savedInstanceState.getBoolean(KEY_M);
            division = savedInstanceState.getBoolean(KEY_D);
        }

        mButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                InputFragment frag = (InputFragment) getFragmentManager().findFragmentById(R.id.fragment_input);
                frag.textEntered();
            }
        });

        return v;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_A, addition);
        savedInstanceState.putBoolean(KEY_S, subtraction);
        savedInstanceState.putBoolean(KEY_M, multiplication);
        savedInstanceState.putBoolean(KEY_D, division);
    }

    public int operation(int x, int y) {
        mNumber.x = x;
        mNumber.y = y;

        if (mAdditionButton.isChecked()) {
            mNumber.z = x + y;
        }
        else if (mSubtractButton.isChecked()) {
            mNumber.z = x - y;
        }
        else if (mMultiplicationButton.isChecked()) {
            mNumber.z = x * y;
        }
        else if (mDivisionButton.isChecked()) {
            mNumber.z = x / y;
        }

        return mNumber.z;
    }
}
