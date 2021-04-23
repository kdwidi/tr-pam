package com.example.cryptos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class AccountDatabase extends SQLiteOpenHelper {
    private final String TAG = AccountDatabase.class.getSimpleName();

    private static final String DBNAME = "account.db";

    public AccountDatabase(Context c) {
        super(c, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate");
        db.execSQL("CREATE TABLE login(" +
                "username TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);
    }

    public String isLoggedIn() {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM login", null, null);
        c.moveToFirst();
        if(!c.isAfterLast()) {
            String username = c.getString(c.getColumnIndex("username"));
            c.close();
            return username;
        } else
            return null;
    }

    public int login(String username) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        try {
            getWritableDatabase().insertOrThrow("login", null, cv);
        } catch (SQLiteConstraintException e) {
            return -1;
        }
        return 1;
    }

    public void logout() {
        getWritableDatabase().delete("login",null,null);
    }

}
