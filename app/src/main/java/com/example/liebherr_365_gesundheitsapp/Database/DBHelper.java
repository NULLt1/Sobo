package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, Queries.DB_NAME, null, Queries.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabellen werden angelegt");
            db.execSQL(Queries.CREATE_TABLE_MODULES);
            db.execSQL(Queries.CREATE_TABLE_DATA);
            db.execSQL(Queries.CREATE_TABLE_PARSED_DATA);
        } catch (Exception e) {
            Log.d(LOG_TAG, "Fehler beim Anlegen der Tabellen: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Queries.dropTable(Queries.TABLE_DATA));
        db.execSQL(Queries.dropTable(Queries.TABLE_MODULES));
        db.execSQL(Queries.dropTable(Queries.TABLE_PARSED_DATA));

        onCreate(db);
    }

    public void deleteTable(SQLiteDatabase db, String tableName) {
        db.execSQL(Queries.deleteTable(tableName));
    }
}
