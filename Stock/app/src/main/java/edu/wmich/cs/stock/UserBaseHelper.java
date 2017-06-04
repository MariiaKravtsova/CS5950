package edu.wmich.cs.stock;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wmich.cs.stock.UserDbSchema.UserTable;

public class UserBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
            UserTable.Cols.UUID + ", " +
            UserTable.Cols.username + ", " +
            UserTable.Cols.password + ", " +
            UserTable.Cols.email + ")"
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

    protected void createTest() {
        User testUser = new User();

        testUser.setName("test");
        testUser.setEmail("test@test.com");
        testUser.setPassword("test");

        addUser(testUser);

    }
}

