package com.example.liebherr_365_gesundheitsapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, weightquery.getDbName(), null, weightquery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table weight with values: weight,day,month,year

        db.execSQL(weightquery.getCreateDb());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }
}
