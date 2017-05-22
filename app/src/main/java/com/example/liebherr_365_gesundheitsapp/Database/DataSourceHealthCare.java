package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DataSourceHealthCare {
    private static final String LOG_TAG = DataSourceHealthCare.class.getSimpleName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] columns = {
            Queries.COLUMN_ID,
            Queries.COLUMN_DATE,
            Queries.COLUMN_NAME,
            Queries.COLUMN_VENUE,
            Queries.COLUMN_PRICE,
            Queries.COLUMN_FLAG
    };

    public DataSourceHealthCare(Context context) {
        Log.d(LOG_TAG, "DataSrc erzeugt den dbHelper");
        dbHelper = new DBHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die DB wird jetzt angefragt");
        database = dbHelper.getWritableDatabase();
        Log.d(TAG, "DB-Referez erhalten. Pfad zur DB: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(TAG, "Datenbank mit Hilfe des DBHelpers geschlossen");
    }

    public void deleteDB() {
        dbHelper.deleteTable(database, Queries.TABLE_HEALTH_CARE);
    }

    public DataHealthCare createEntry(String date, String name, String venue, String price, String flag) {
        ContentValues values = new ContentValues();

        values.put(Queries.COLUMN_DATE, date);
        values.put(Queries.COLUMN_NAME, name);
        values.put(Queries.COLUMN_VENUE, venue);
        values.put(Queries.COLUMN_PRICE, price);
        values.put(Queries.COLUMN_FLAG, flag);

        long insertId = database.insert(Queries.TABLE_HEALTH_CARE, null, values);

        Cursor cursor = database.query(Queries.TABLE_HEALTH_CARE, columns, Queries.COLUMN_ID + "=" + insertId, null, null, null, null);
        cursor.moveToFirst();

        DataHealthCare data = cursorToDataHealthCare(cursor);
        cursor.close();

        return data;
    }

    private DataHealthCare cursorToDataHealthCare(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Queries.COLUMN_ID);
        int idDate = cursor.getColumnIndex(Queries.COLUMN_DATE);
        int idName = cursor.getColumnIndex(Queries.COLUMN_NAME);
        int idVenue = cursor.getColumnIndex(Queries.COLUMN_VENUE);
        int idPrice = cursor.getColumnIndex(Queries.COLUMN_PRICE);
        int idFlag = cursor.getColumnIndex(Queries.COLUMN_FLAG);

        long id = cursor.getLong(idIndex);
        String date = cursor.getString(idDate);
        String name = cursor.getString(idName);
        String venue = cursor.getString(idVenue);
        String price = cursor.getString(idPrice);
        String flag = cursor.getString(idFlag);

        return new DataHealthCare(id, date, name, venue, price, flag);
    }
}

