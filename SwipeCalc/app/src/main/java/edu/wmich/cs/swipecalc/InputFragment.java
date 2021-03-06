package edu.wmich.cs.swipecalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputFragment extends Fragment {

    private int mOperation;
    private EditText x;
    private EditText y;
    private TextView z;
    private Button mButton;
    private String[] mData = {"+", "-", "*", "/"};

    public static InputFragment newInstance(int operation_id) {
        InputFragment fr = new InputFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", operation_id);
        fr.setArguments(args);

        return fr;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mOperation = getArguments().getInt("someInt");

        View v = inflater.inflate(R.layout.input, container, false);
        x = (EditText) v.findViewById(R.id.input_a);
        y = (EditText) v.findViewById(R.id.input_b);
        z = (TextView) v.findViewById(R.id.output);
        mButton = (Button) v.findViewById(R.id.calculate_button);

        mButton.setText(mData[mOperation]);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double r = 0;
                switch (mOperation) {
                    case 0:
                        r = Double.parseDouble(x.getText().toString()) + Double.parseDouble(y.getText().toString());
                        break;
                    case 1:
                        r = Double.parseDouble(x.getText().toString()) - Double.parseDouble(y.getText().toString());
                        break;
                    case 2:
                        r = Double.parseDouble(x.getText().toString()) * Double.parseDouble(y.getText().toString());
                        break;
                    case 3:
                        r = Double.parseDouble(x.getText().toString()) / Double.parseDouble(y.getText().toString());
                        break;
                    default:
                        break;
                }
                z.setText(Double.toString(r));
            }
        });

        return v;
    }

}
