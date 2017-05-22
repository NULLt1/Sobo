package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class DataSourceParsedData {
    private static final String LOG_TAG = DataSourceParsedData.class.getSimpleName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] columns = {
            Queries.COLUMN_ID,
            Queries.COLUMN_MODUL,
            Queries.COLUMN_DATE,
            Queries.COLUMN_TEASER,
            Queries.COLUMN_TEXT,
            Queries.COLUMN_FLAG
    };

    public DataSourceParsedData(Context context) {
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
        dbHelper.deleteTable(database, Queries.TABLE_PARSED_DATA);
    }

    public DataParsedData createEntry(String modul, String date, String teaser, String text, boolean flag) {
        if (checkAlreadyExist(database, text)) {
            Log.d(LOG_TAG, "Eintrag existiert bereits");
        } else {
            Log.d(LOG_TAG, "Eintrag existiert noch nicht");
            ContentValues values = new ContentValues();
            values.put(Queries.COLUMN_MODUL, modul);
            values.put(Queries.COLUMN_DATE, date);
            values.put(Queries.COLUMN_TEASER, teaser);
            values.put(Queries.COLUMN_TEXT, text);
            values.put(Queries.COLUMN_FLAG, String.valueOf(flag));

            long insertId = database.insert(Queries.TABLE_PARSED_DATA, null, values);

            Cursor cursor = database.query(Queries.TABLE_PARSED_DATA, columns, Queries.COLUMN_ID + "=" + insertId, null, null, null, null);
            cursor.moveToFirst();

            DataParsedData dataParsedData = cursorToDataParsedData(cursor);
            cursor.close();

            return dataParsedData;
        }
        return null;
    }

    private DataParsedData updateEntry(long id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(Queries.COLUMN_FLAG, String.valueOf(true));
        values.put(Queries.COLUMN_DATE, String.valueOf(dateFormat.format(date)));

        database.update(Queries.TABLE_PARSED_DATA, values, Queries.COLUMN_ID + "=" + id, null);

        Cursor cursor = database.query(Queries.TABLE_PARSED_DATA, columns, Queries.COLUMN_ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();

        return cursorToDataParsedData(cursor);
    }

    private DataParsedData cursorToDataParsedData(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Queries.COLUMN_ID);
        int idModul = cursor.getColumnIndex(Queries.COLUMN_MODUL);
        int idDate = cursor.getColumnIndex(Queries.COLUMN_DATE);
        int idTeaser = cursor.getColumnIndex(Queries.COLUMN_TEASER);
        int idText = cursor.getColumnIndex(Queries.COLUMN_TEXT);
        int idIsset = cursor.getColumnIndex(Queries.COLUMN_FLAG);

        long id = cursor.getLong(idIndex);
        String modul = cursor.getString(idModul);
        String date = cursor.getString(idDate);
        String teaser = cursor.getString(idTeaser);
        String text = cursor.getString(idText);
        boolean flag = (cursor.getInt(idIsset) == 1);


        return new DataParsedData(id, modul, date, teaser, text, flag);
    }

    public List<DataParsedData> getAllData() {
        Cursor cursor = database.query(Queries.TABLE_PARSED_DATA, columns, null, null, null, null, null);

        return cursorToList(cursor);
    }

    public List<DataParsedData> getSubscribedTips() {
        String query = "SELECT * FROM " +
                Queries.TABLE_PARSED_DATA + " WHERE " +
                Queries.COLUMN_FLAG + " = 'true' AND " +
                Queries.COLUMN_MODUL + " IN (" +
                Queries.getActiveModules() + ") ORDER BY " + Queries.COLUMN_DATE + " DESC";

        Cursor cursor = database.rawQuery(query, null);

        return cursorToList(cursor);
    }

    public void getNewTip() {

        String query = "SELECT * FROM " +
                Queries.TABLE_PARSED_DATA + " WHERE " +
                Queries.COLUMN_FLAG + " = 'false' AND " +
                Queries.COLUMN_MODUL + " IN (" +
                Queries.getActiveModules() + ")";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();


        if (cursor.getCount() > 1) {
            List<DataParsedData> arrayList = cursorToList(cursor);
            int rnd = new Random().nextInt(arrayList.size());
            long id = arrayList.get(rnd).getId();
            updateEntry(id);

        } else if (cursor.getCount() == 1)
            updateEntry(cursorToDataParsedData(cursor).getId());
    }

    private boolean checkAlreadyExist(SQLiteDatabase db, String text) {
        String query = "SELECT " + Queries.COLUMN_TEXT + " FROM " + Queries.TABLE_PARSED_DATA + " WHERE " + Queries.COLUMN_TEXT + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{text});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else
            cursor.close();
        return false;
    }

    private List<DataParsedData> cursorToList(Cursor cursor) {
        List<DataParsedData> dataList = new ArrayList<>();

        cursor.moveToFirst();
        DataParsedData data;

        while (!cursor.isAfterLast()) {
            data = cursorToDataParsedData(cursor);
            dataList.add(data);
            Log.d(LOG_TAG, "ID: " + data.getId() +
                    " Modul: " + data.getModul() +
                    " Datum: " + data.getDate() +
                    " Teaser: " + data.getTeaser() +
                    " Text: " + data.getText() +
                    " Isset: " + data.getFlag());
            cursor.moveToNext();
        }
        cursor.close();

        return dataList;
    }

}
