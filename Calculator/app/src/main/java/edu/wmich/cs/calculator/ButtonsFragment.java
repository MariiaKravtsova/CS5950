package edu.wmich.cs.calculator;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ButtonsFragment extends Fragment {

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

        mButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.addition:
                        operation(mNumber.x, mNumber.y);
                        break;
                    case R.id.subtraction:
                        operation(mNumber.x, mNumber.y);
                        break;
                    case R.id.multiplication:
                        multiplication = true;
                        operation(mNumber.x, mNumber.y);
                        break;
                    case R.id.division:
                        division = true;
                        operation(mNumber.x, mNumber.y);
                        break;
                }
            }
        });

        return v;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);


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
        else if (multiplication) {
            mNumber.z = x * y;
        }
        else if (division) {
            mNumber.z = x / y;
        }

        return mNumber.z;
    }
}
