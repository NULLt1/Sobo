package com.example.liebherr_365_gesundheitsapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weightdb";
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table weight with values: weight,day,month,year
        String sqlquery = "CREATE TABLE weight " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "weight INT NOT NULL, " +
                "day INT NOT NULL, " +
                "month INT NOT NULL, " +
                "year INT NOT NULL)";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }
}
