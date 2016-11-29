package com.example.liebherr_365_gesundheitsapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, Weightquery.getDbName(), null, Weightquery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table weight with values: weight,day,month,year
        db.execSQL(Weightquery.getCreateDb());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }

    public void deleteweightdb(SQLiteDatabase db) {
        db.rawQuery("DELETE FROM " + Weightquery.getDbName(), null);
    }
}
