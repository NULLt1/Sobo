package com.example.liebherr_365_gesundheitsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

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
        database.delete(Weightquery.getDbName(), null, null);
        dbHelper.close();
    }

    //function insert data into database
    public void insertdata(Weightdata wd) {
        ContentValues values = new ContentValues();
        values.put(Weightquery.getColumnWeight(), wd.getWeight());
        values.put(Weightquery.getColumnDate(), wd.getDate());
        values.put(Weightquery.getColumnBmi(), wd.getBmi());
        database.insert(Weightquery.getDbName(), null, values);
    }

    //function update data in database
    public void updatedata(Weightdata wd) {
        ContentValues values = new ContentValues();
        values.put(Weightquery.getColumnDate(), wd.getDate());
        values.put(Weightquery.getColumnWeight(), wd.getWeight());
        values.put(Weightquery.getColumnBmi(), wd.getBmi());
        database.replace(Weightquery.getDbName(), null, values);
    }

    private Weightdata cursorToWeightdata(Cursor cursor) {
        int idDate = cursor.getColumnIndex(Weightquery.getColumnDate());
        int idWeight = cursor.getColumnIndex(Weightquery.getColumnWeight());
        int idBmi = cursor.getColumnIndex(Weightquery.getColumnBmi());

        String date = cursor.getString(idDate);
        float weight = cursor.getFloat(idWeight);
        float bmi = cursor.getFloat(idBmi);

        Weightdata weightdata = new Weightdata(weight, date, bmi);

        return weightdata;
    }

    public Weightdata[] getAllDataasarray() {
        Cursor cursor = database.query(Weightquery.getDbName(),
                Weightquery.getColumns(), null, null, null, null, Weightquery.getColumnDate());

        int size = cursor.getCount();
        Weightdata[] alldata = new Weightdata[size];

        cursor.moveToFirst();
        int counter = 0;

        while (!cursor.isAfterLast()) {
            alldata[counter] = cursorToWeightdata(cursor);
            cursor.moveToNext();
            counter++;
        }
        cursor.close();
        return alldata;
    }


    public List<Weightdata> getAllData() {
        List<Weightdata> weightdataList = new ArrayList<>();

        Cursor cursor = database.query(Weightquery.getDbName(),
                Weightquery.getColumns(), null, null, null, null, Weightquery.getColumnDate());

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

        boolean result = false;

        String query = "SELECT " + Weightquery.getColumnDate() + " FROM " + Weightquery.getDbName();
        Cursor databaseweightresult = database.rawQuery(query, null);
        int count = databaseweightresult.getCount();
        if (count == 0) {
            result = false;
        } else {
            databaseweightresult.moveToFirst();
            String databasedate = databaseweightresult.getString(databaseweightresult.getColumnIndex(Weightquery.getColumnDate()));
            if (date.equals(databasedate)) {
                result = true;
            }
            Log.d("TEST", String.valueOf(count));
            while (databaseweightresult.moveToNext()) {
                databasedate = databaseweightresult.getString(databaseweightresult.getColumnIndex(Weightquery.getColumnDate()));
                if (date.equals(databasedate)) {
                    result = true;
                }
            }
        }
        databaseweightresult.close();
        return result;
    }

    public int getLatestWeight() {

        String queryMaxDate = "(SELECT MAX(" + Weightquery.getColumnDate() + ") from " + Weightquery.getDbName() + ")";
        String queryWhere = Weightquery.getColumnDate() + " = " + queryMaxDate;

        Cursor cursor = database.query(Weightquery.getDbName(), Weightquery.getColumns(), queryWhere, null, null, null, null);
        cursor.moveToFirst();
        int WeightID=cursor.getColumnIndex(Weightquery.getColumnWeight());
        int lastWeight=cursor.getInt(WeightID);

        return lastWeight;

    }


}
