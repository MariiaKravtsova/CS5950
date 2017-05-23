package edu.wmich.cs.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class InputFragment extends Fragment {

    private EditText mInputA;
    private EditText mInputB;
    private TextView mOutput;

    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_Z = "z";

    Numbers mNumber = new Numbers(0, 0, 0);

    InputListener mCallback;

    public interface InputListener {
        int calculateResult(Numbers n);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InputListener) {
            mCallback = (InputListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement InputListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);

        if (savedInstanceState != null) {
            mNumber.x = savedInstanceState.getInt(KEY_X, 0);
            mNumber.y = savedInstanceState.getInt(KEY_Y, 0);
            mNumber.z = savedInstanceState.getInt(KEY_Z, 0);
        }

        mInputA = (EditText) v.findViewById(R.id.input_a);
        mInputB = (EditText) v.findViewById(R.id.input_b);
        mOutput = (TextView) v.findViewById(R.id.output);

        mInputA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mNumber.x = Integer.parseInt(mInputA.getText().toString());
                } catch (NumberFormatException e) {
                    mNumber.x = 0;
                }
                textEntered();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mInputB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mNumber.y = Integer.parseInt(mInputB.getText().toString());
                } catch (NumberFormatException e) {
                    mNumber.y = 0;
                }
                textEntered();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }

    public void textEntered() {
        int result = mCallback.calculateResult(mNumber);
        setResult(result);
    }

    public void setResult(int result) {
        mOutput.setText(String.valueOf(result));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_X, mNumber.x);
        savedInstanceState.putInt(KEY_Y, mNumber.y);
        savedInstanceState.putInt(KEY_Z, mNumber.z);
    }
}
