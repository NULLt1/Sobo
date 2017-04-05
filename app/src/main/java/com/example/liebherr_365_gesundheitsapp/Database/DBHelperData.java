package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperData extends SQLiteOpenHelper {


    public DBHelperData(Context context) {
        super(context, DataQuery.getDbName(), null, DataQuery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("", "<DATA>Die Tabelle wird mit SQL-Befehl: " + DataQuery.getCreateDb() + " angelegt.<DATA>");
            db.execSQL(DataQuery.getCreateDb());
        } catch (Exception ex) {
            Log.e("", "<DATA>Fehler beim Anlegen der Tabelle: " + ex.getMessage() + "<DATA>");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }

    public void deleteweightdb(SQLiteDatabase db) {
        db.rawQuery("DELETE FROM " + DataQuery.getDbName(), null);
    }
}
