package edu.wmich.cs.swipecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

public class CalcActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private String[] mData = {"+", "-", "*", "/"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CalcAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class CalcHolder extends RecyclerView.ViewHolder {

        private Button mButton;

        public CalcHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.button, parent, false));

            mButton = (Button) itemView.findViewById(R.id.button_a);
        }

        public void bind(String item) {
            mButton.setText(item);
        }
    }

    private class CalcAdapter extends RecyclerView.Adapter<CalcHolder> {

        public CalcAdapter(String[] data) {
            mData = data;
        }

        @Override
        public CalcHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(CalcActivity.this);

            return new CalcHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CalcHolder holder, int position) {
            String item = mData[position];
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mData.length;
        }
    }
}


