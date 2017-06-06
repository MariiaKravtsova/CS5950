package edu.wmich.cs.stock;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UserBaseHelper mUserBaseHelper;
    private ArrayList<Stock> mStocks;
    private int mUUID;
    private SQLiteDatabase mdb;
    private TextView mTotal;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_stock_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_stock:
                Intent iAdd = new Intent(getApplicationContext(), AddActivity.class);
                iAdd.putExtra("uuid", mUUID);
                startActivity(iAdd);
                break;
            case R.id.refresh:
                Toast toast = Toast.makeText(getApplicationContext(), "Coming soon.", Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        mdb = new UserBaseHelper(getApplicationContext()).getWritableDatabase();
        mUserBaseHelper = new UserBaseHelper(StockActivity.this);

        Intent intent = getIntent();
        mUUID = intent.getIntExtra("uuid", 1);

        mStocks = new ArrayList<Stock>();

        mUserBaseHelper.addStocks();
        populateStocks();

        mRecyclerView = (RecyclerView) findViewById(R.id.stock_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StockActivity.this));
        StockAdapter adapter = new StockAdapter();
        mRecyclerView.setAdapter(adapter);

    }

    public void populateStocks() {

        Cursor cursor = mdb.query(UserDbSchema.StockTable.NAME, null, UserDbSchema.StockTable.Cols.userid + " = " + mUUID,
                null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                Stock stock = new Stock();

                stock.setUserId(cursor.getInt(cursor.getColumnIndex("userid")));
                stock.setStock(cursor.getString(cursor.getColumnIndex("stock")));
                stock.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                stock.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));

                mStocks.add(stock);

            } while (cursor.moveToNext());
        }

        cursor.close();
        mdb.close();

        setTotal();
    }

    private void setTotal() {
        double total = 0;
        for (Stock stock: mStocks) {
            total += stock.getValue();
        }

        mTotal = (TextView) findViewById(R.id.total);
        String text = "Total: " + String.valueOf(total);
        mTotal.setText(text);
    }

    private class StockHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mPrice;
        private TextView mValue;
        private TextView mQuantity;

        public StockHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.stock_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.name);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mValue = (TextView) itemView.findViewById(R.id.value);
            mQuantity = (TextView) itemView.findViewById(R.id.quantity);

        }

        public void bind(Stock stock) {
            mName.setText(stock.getStock());
            mPrice.setText(String.valueOf(stock.getPrice()));
            mValue.setText(String.valueOf(stock.getValue()));
            mQuantity.setText(String.valueOf(stock.getQuantity()));
        }
    }

    private class StockAdapter extends RecyclerView.Adapter<StockHolder> {
        @Override
        public StockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(StockActivity.this);
            return new StockHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(StockHolder holder, int position) {
            holder.bind(mStocks.get(position));
        }

        @Override
        public int getItemCount() {
            return mStocks.size();
        }
    }
}
