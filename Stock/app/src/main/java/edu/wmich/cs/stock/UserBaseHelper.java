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

        addStocks(db);
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

    public void removeStock(String name, int uuid) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(UserDbSchema.StockTable.NAME, UserDbSchema.StockTable.Cols.stock + " = ? and "
                + UserDbSchema.StockTable.Cols.userid + " = " + uuid, new String[]{name});

        db.close();
    }

    public void updatePrice(double price, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(StockTable.Cols.price, price);
        db.update(StockTable.NAME, values, StockTable.Cols.stock + " = ?", new String[] {name});

        db.insert(StockTable.NAME, null, values);
        db.close();
    }

    public double updatePrice(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(StockTable.NAME,
                null,
                StockTable.Cols.stock + " = ?",
                new String[] {stock.getStock()}, null, null, null);
        cursor.moveToFirst();
        Double price = cursor.getDouble(cursor.getColumnIndex(StockTable.Cols.price));
        cursor.close();
        db.close();
        return price;
    }

    protected void createTest() {
        User testUser = new User();

        testUser.setName("test");
        testUser.setEmail("test@test.com");
        testUser.setPassword("test");

        addUser(testUser);
    }

    public void addStocks(SQLiteDatabase db) {
        // These are some stock examples for test

        ContentValues google = new ContentValues();
        google.put(StockTable.Cols.stock, "GOOG");
        google.put(StockTable.Cols.quantity, 5);
        google.put(StockTable.Cols.price, 100.00);
        google.put(StockTable.Cols.userid, 1);
        db.insert(StockTable.NAME, null, google);

        ContentValues fbook = new ContentValues();
        fbook.put(StockTable.Cols.stock, "FB");
        fbook.put(StockTable.Cols.quantity, 3);
        fbook.put(StockTable.Cols.price, 60.00);
        fbook.put(StockTable.Cols.userid, 1);
        db.insert(StockTable.NAME, null, fbook);

        ContentValues micro = new ContentValues();
        micro.put(StockTable.Cols.stock, "MSFT");
        micro.put(StockTable.Cols.quantity, 9);
        micro.put(StockTable.Cols.price, 70.00);
        micro.put(StockTable.Cols.userid, 1);
        db.insert(StockTable.NAME, null, micro);

    }
}

