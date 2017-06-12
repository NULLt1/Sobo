package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataSourceMensa {
    private static final String LOG_TAG = DataSourceMensa.class.getSimpleName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] columns = {
            Queries.COLUMN_ID,
            Queries.COLUMN_DATE,
            Queries.COLUMN_DAY,
            Queries.COLUMN_WEEK_OF_THE_YEAR,
            Queries.COLUMN_HEADER,
            Queries.COLUMN_PRICE,
            Queries.COLUMN_MENU
    };

    public static String DAY = "day";
    public static String MENU = "menu";
    public static String HEADER = "header";
    public static String PRICE = "price";

    public DataSourceMensa(Context context) {
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
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DBHelpers geschlossen");
    }

    public void deleteDB() {
        dbHelper.deleteTable(database, Queries.TABLE_MENSA);
    }

    public DataMensaMenu createEntry(String date, String day, int weekOfTheYear, String header, String price, String menu) {
        if (checkAlreadyExist(date, header)) {
            Log.d(LOG_TAG, "Eintrag existiert bereits");

        } else {
            Log.d(LOG_TAG, "Eintrag existiert noch nicht");

            ContentValues values = new ContentValues();
            values.put(Queries.COLUMN_DATE, date);
            values.put(Queries.COLUMN_DAY, day);
            values.put(Queries.COLUMN_WEEK_OF_THE_YEAR, weekOfTheYear);
            values.put(Queries.COLUMN_HEADER, header);
            values.put(Queries.COLUMN_PRICE, price);
            values.put(Queries.COLUMN_MENU, menu);

            long insertId = database.insert(Queries.TABLE_MENSA, null, values);

            Cursor cursor = database.query(Queries.TABLE_MENSA, columns, Queries.COLUMN_ID + "=" + insertId, null, null, null, null);


            cursor.moveToFirst();
            DataMensaMenu dataMensaMenu = cursorToDataMensaMenu(cursor);
            cursor.close();

            return dataMensaMenu;
        }
        return null;
    }

    private DataMensaMenu cursorToDataMensaMenu(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Queries.COLUMN_ID);
        int idDate = cursor.getColumnIndex(Queries.COLUMN_DATE);
        int idDay = cursor.getColumnIndex(Queries.COLUMN_DAY);
        int idWeekOfTheYear = cursor.getColumnIndex(Queries.COLUMN_WEEK_OF_THE_YEAR);
        int idHeader = cursor.getColumnIndex(Queries.COLUMN_HEADER);
        int idPrice = cursor.getColumnIndex(Queries.COLUMN_PRICE);
        int idMenu = cursor.getColumnIndex(Queries.COLUMN_MENU);

        long id = cursor.getLong(idIndex);
        String date = cursor.getString(idDate);
        String day = cursor.getString(idDay);
        int weekOfTheYear = cursor.getInt(idWeekOfTheYear);
        String header = cursor.getString(idHeader);
        String price = cursor.getString(idPrice);
        String menu = cursor.getString(idMenu);

        return new DataMensaMenu(id, date, day, weekOfTheYear, header, price, menu);
    }

    private boolean checkAlreadyExist(String text, String header) {
        String query = "SELECT " + Queries.COLUMN_DATE + " FROM " + Queries.TABLE_MENSA + " WHERE " + Queries.COLUMN_DATE + " =? AND " + Queries.COLUMN_HEADER + " =? ";
        Cursor cursor = database.rawQuery(query, new String[]{text, header});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else
            cursor.close();
        return false;
    }

    private List<DataMensaMenu> cursorToList(Cursor cursor) {
        List<DataMensaMenu> dataList = new ArrayList<>();

        cursor.moveToFirst();
        DataMensaMenu data;

        while (!cursor.isAfterLast()) {
            data = cursorToDataMensaMenu(cursor);
            dataList.add(data);
            Log.d(LOG_TAG, "ID: " + data.getId() +
                    " Datum: " + data.getDate() +
                    " Tag: " + data.getDay() +
                    " Woche im Jahr: " + data.getWeekOfTheYear() +
                    " Header: " + data.getHeader() +
                    " Preis: " + data.getPrice() +
                    " Menü: " + data.getMenu());
            cursor.moveToNext();
        }
        cursor.close();

        return dataList;
    }

    public List<DataMensaMenu> getDataForWeek(int week) {
        //if week == 0 the result will be the additionalMenu
        Cursor cursor = database.query(Queries.TABLE_MENSA, columns, Queries.COLUMN_WEEK_OF_THE_YEAR + "= ?", new String[]{String.valueOf(week)}, null, null, null);
        return cursorToList(cursor);
    }

    public ArrayList<HashMap<String, ArrayList<String>>> getDataAsArrayList(int week) {

        ArrayList<HashMap<String, ArrayList<String>>> arrayList = new ArrayList<>();

        String[] list = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};

        String day = "";
        for (int i = 0; i < list.length; i++) {
            ArrayList<String> daylist = new ArrayList<>();
            ArrayList<String> menulist = new ArrayList<>();
            ArrayList<String> pricelist = new ArrayList<>();
            ArrayList<String> headerlist = new ArrayList<>();
            HashMap<String, ArrayList<String>> map = new HashMap<>();
            day = list[i];
            Cursor cursor = database.query(Queries.TABLE_MENSA, columns, Queries.COLUMN_WEEK_OF_THE_YEAR + "= ? AND " + Queries.COLUMN_DAY + " = ?", new String[]{String.valueOf(week), day}, null, null, null);
            if (cursor.getCount() > 0) {
                List<DataMensaMenu> dataList = cursorToList(cursor);
                cursor.close();
                for (DataMensaMenu data : dataList) {
                    daylist.add(data.getDay() + "\n" + data.getDate());
                    menulist.add(data.getMenu());
                    pricelist.add(data.getPrice());
                    Log.d(TAG, "Preis: " + data.getPrice());
                    headerlist.add(data.getHeader());

                }

                map.put(DAY, daylist);
                map.put(MENU, menulist);
                map.put(HEADER, headerlist);
                map.put(PRICE, pricelist);

                arrayList.add(map);
            }
        }
        return arrayList;
    }

    private DataMensaMenu updateEntry(long id, String price, String header, String menu) {

        ContentValues values = new ContentValues();
        values.put(Queries.COLUMN_PRICE, price);
        values.put(Queries.COLUMN_DATE, header);
        values.put(Queries.COLUMN_MENU, menu);

        database.update(Queries.TABLE_MENSA, values, Queries.COLUMN_ID + "=" + id, null);

        Cursor cursor = database.query(Queries.TABLE_MENSA, columns, Queries.COLUMN_ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();

        return cursorToDataMensaMenu(cursor);
    }

    public ArrayList<HashMap<String, ArrayList<String>>> getTodaysDataAsArrayList() {
        ArrayList<HashMap<String, ArrayList<String>>> arrayList = new ArrayList<>();

        ArrayList<String> daylist = new ArrayList<>();
        ArrayList<String> menulist = new ArrayList<>();
        ArrayList<String> pricelist = new ArrayList<>();
        ArrayList<String> headerlist = new ArrayList<>();
        HashMap<String, ArrayList<String>> map = new HashMap<>();

//TODO:Datum mit aktuellem Datum austauschen, sobald Kantinenplan aktuell ist
        Cursor cursor = database.query(Queries.TABLE_MENSA, columns, Queries.COLUMN_DATE + "= ?", new String[]{"2017.01.10"}, null, null, null);
        if (cursor.getCount() > 0) {
            Log.d(LOG_TAG, String.valueOf(cursor.getCount()));
            List<DataMensaMenu> dataList = cursorToList(cursor);
            cursor.close();
            for (DataMensaMenu data : dataList) {
                daylist.add(data.getDay() + "\n" + data.getDate());
                menulist.add(data.getMenu());
                pricelist.add(data.getPrice());
                Log.d(TAG, "Preis: " + data.getPrice());
                headerlist.add(data.getHeader());

            }

            map.put(DAY, daylist);
            map.put(MENU, menulist);
            map.put(HEADER, headerlist);
            map.put(PRICE, pricelist);

            arrayList.add(map);
        }
        for(String item:pricelist) {
            Log.d(LOG_TAG, String.valueOf("arraylist größe" + item));
        }
        return arrayList;
    }

}
