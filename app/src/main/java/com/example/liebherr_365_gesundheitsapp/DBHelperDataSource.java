package com.example.liebherr_365_gesundheitsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelperDataSource {

    private static final String LOG_TAG = DBHelperDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DBHelperDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DBHelper(context);

    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public void deletedb() {
        database = dbHelper.getWritableDatabase();
        database.delete(weightquery.getDbName(), null, null);
        dbHelper.close();
    }

    //function insert data into database
    public void insertdata(Weightdata wd) {
        ContentValues values = new ContentValues();
        values.put(weightquery.getColumnWeight(), wd.getWeight());
        values.put(weightquery.getColumnDate(), wd.getDate());
        values.put(weightquery.getColumnBmi(), wd.getBmi());
        database.insert(weightquery.getDbName(), null, values);
    }

    //function update data in database
    public void updatedata(Weightdata wd) {
        String date = wd.getDate();
        boolean result = false;

        Cursor cursor = database.query(weightquery.getDbName(),
                weightquery.getColumns(), null, null, null, null, weightquery.getColumnDate());

        cursor.moveToFirst();
        Weightdata weightdatadatabase;

        ContentValues values = new ContentValues();
        values.put(weightquery.getColumnDate(), wd.getDate());
        values.put(weightquery.getColumnWeight(), wd.getWeight());
        values.put(weightquery.getColumnBmi(), wd.getBmi());
        String where = weightquery.getColumnDate() + "=" + date;
        Log.d("******where******", where);

        //TODO: UPDATE VON ROW ERFOLGT NICHT
        database.replace(weightquery.getDbName(), null, values);

    }

    private Weightdata cursorToWeightdata(Cursor cursor) {
        int idDate = cursor.getColumnIndex(weightquery.getColumnDate());
        int idWeight = cursor.getColumnIndex(weightquery.getColumnWeight());
        int idBmi = cursor.getColumnIndex(weightquery.getColumnBmi());

        String date = cursor.getString(idDate);
        float weight = cursor.getFloat(idWeight);
        float bmi = cursor.getFloat(idBmi);

        Weightdata weightdata = new Weightdata(weight, date, bmi);

        return weightdata;
    }

    public List<Weightdata> getAllData() {
        List<Weightdata> weightdataList = new ArrayList<>();

        Cursor cursor = database.query(weightquery.getDbName(),
                weightquery.getColumns(), null, null, null, null, weightquery.getColumnDate());

        cursor.moveToFirst();
        Weightdata weightdata;

        while (!cursor.isAfterLast()) {
            weightdata = cursorToWeightdata(cursor);
            weightdataList.add(weightdata);
            cursor.moveToNext();
        }

        cursor.close();


        return weightdataList;
    }

    //function datealreadysaved
    public boolean datealreadysaved(Weightdata wd) {
        String date = wd.getDate();
        Log.d("function", "er hat den fehler gefunden!");

        boolean result = false;

        String query = "SELECT " + weightquery.getColumnDate() + " FROM " + weightquery.getDbName();
        Log.d("query", query);
        Cursor databaseweightresult = database.rawQuery(query, null);
        int count = databaseweightresult.getCount();
        Log.d("Count", String.valueOf(count));
        if (count == 0) {
            result = false;
        } else {
            databaseweightresult.moveToFirst();
            String databasedate = databaseweightresult.getString(databaseweightresult.getColumnIndex(weightquery.getColumnDate()));
            if (date.equals(databasedate)) {
                result = true;
            }
            Log.d("TEST", String.valueOf(count));
            while (databaseweightresult.moveToNext()) {
                databasedate = databaseweightresult.getString(databaseweightresult.getColumnIndex(weightquery.getColumnDate()));
                Log.d("wd.Date", date);
                Log.d("db.date", databasedate);
                if (date.equals(databasedate)) {
                    result = true;
                }
            }
        }
        databaseweightresult.close();
        return result;
    }
}
