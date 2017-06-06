package edu.wmich.cs.stock;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wmich.cs.stock.UserDbSchema.UserTable;
import edu.wmich.cs.stock.UserDbSchema.StockTable;

public class UserBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME + "(" +
            UserTable.Cols.UUID + " integer primary key autoincrement, " +
            UserTable.Cols.username + " text, " +
            UserTable.Cols.password + " text, " +
            UserTable.Cols.email + " text)"
        );

        db.execSQL("create table " + StockTable.NAME + "(" +
                StockTable.Cols.UUID + " integer primary key autoincrement, " +
                StockTable.Cols.userid + " integer, " +
                StockTable.Cols.stock + " text, " +
                StockTable.Cols.quantity + " integer, " +
                StockTable.Cols.price + " real)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean checkUser(String name, String password) {
        String[] columns = {UserTable.Cols.UUID};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = UserTable.Cols.username + " = ? " + " AND " + UserTable.Cols.password + " = ?";

        String[] selectionArgs = {name, password};

        Cursor cursor = db.query(UserTable.NAME, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getUser(String name, String password) {
        String[] columns = {UserTable.Cols.UUID};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = UserTable.Cols.username + " = ? " + " AND " + UserTable.Cols.password + " = ?";

        String[] selectionArgs = {name, password};

        Cursor cursor = db.query(UserTable.NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();

        }

        cursor.moveToFirst();
        int uid = cursor.getInt(cursor.getColumnIndex("uuid"));
        cursor.close();
        db.close();
        return uid;
    }

    public String checkEmail(String email) {
        String[] columns = {UserTable.Cols.UUID};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = UserTable.Cols.email + " = ? ";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(UserTable.NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1) {
            cursor.close();
            return "";
        }

        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        db.close();
        return password;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserTable.Cols.username, user.getName());
        values.put(UserTable.Cols.email, user.getEmail());
        values.put(UserTable.Cols.password, user.getPassword());

        db.insert(UserTable.NAME, null, values);
        db.close();
    }

    public void addStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(StockTable.Cols.userid, stock.getUserId());
        values.put(StockTable.Cols.stock, stock.getStock());
        values.put(StockTable.Cols.price, stock.getPrice());
        values.put(StockTable.Cols.quantity, stock.getQuantity());

        db.insert(StockTable.NAME, null, values);
        db.close();
    }


    protected void createTest() {
        User testUser = new User();

        testUser.setName("test");
        testUser.setEmail("test@test.com");
        testUser.setPassword("test");

        addUser(testUser);
    }

    public void addStocks() {
        // These are some stock examples since I didn't fetch them from the internet
        Stock google = new Stock();
        google.setStock("GOOGL");
        google.setUserId(1);
        google.setPrice(1000.00);
        google.setQuantity(3);

        addStock(google);

        Stock fbook = new Stock();
        fbook.setStock("FB");
        fbook.setUserId(1);
        fbook.setPrice(150.00);
        fbook.setQuantity(9);

        addStock(fbook);

        Stock micro = new Stock();
        micro.setStock("MSFT");
        micro.setUserId(1);
        micro.setPrice(70.00);
        micro.setQuantity(7);

        addStock(micro);
    }
}

