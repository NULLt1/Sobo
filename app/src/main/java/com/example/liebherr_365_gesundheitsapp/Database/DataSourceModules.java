package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataSourceModules {
    private static final String LOG_TAG = DataSourceData.class.getSimpleName();

    private SQLiteDatabase databaseModules;
    private DBHelper dbHelper;

    public DataSourceModules(Context context) {
        Log.d(LOG_TAG, "<MODULES>Unsere DataSource erzeugt jetzt den dbHelper.<MODULES>");
        dbHelper = new DBHelper(context);
        databaseModules = dbHelper.getReadableDatabase();
    }

    public void open() {
        Log.d(LOG_TAG, "<MODULES>Eine Referenz auf die Datenbank wird jetzt angefragt.<MODULES>");
        Log.d(LOG_TAG, "<MODULES>Datenbank-Referenz erhalten. Pfad zur Datenbank: " + databaseModules.getPath() + "<MODULES>");
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "<MODULES>Datenbank mit Hilfe des DbHelpers geschlossen.<MODULES>");
    }

    public void deleteModule(String modulname) {
        databaseModules.delete(Queries.TABLE_MODULES, Queries.COLUMN_MODUL + "='" + modulname + "'", null);
        Log.d(LOG_TAG, "<MODULES>Datenbank gelöscht<MODULES>");
    }

    //function add modul to database modules
    public void insertmodules(String name, String modul, boolean flag) {
        ContentValues values = new ContentValues();

        values.put(Queries.COLUMN_NAME, name);
        values.put(Queries.COLUMN_MODUL, modul);
        values.put(Queries.COLUMN_FLAG, String.valueOf(flag));

        databaseModules.insert(Queries.TABLE_MODULES, null, values);
    }

    //function insertdefaultmodules
    public void insertdefaultmodules() {
        Cursor cursor = getAllDataCursor();
        if (cursor.getCount() == 0) {
            insertmodules("Kantine", "ModulMensa", true);
            insertmodules("Gewicht", "ModulWeight", false);
            insertmodules("Tipp des Tages", "ModulTipOfTheDay", true);
            insertmodules("Trinken", "ModulDrink", false);
            insertmodules("Gesundheitsangebot", "ModulHealth", true);
            insertmodules("Kaffee", "ModulCoffee", false);
        }
    }

    //function changemodulstatus
    public void changemodulstatus(String modul, boolean flag) {
        databaseModules.execSQL("UPDATE " + Queries.TABLE_MODULES + " SET " + Queries.COLUMN_FLAG + "='" + flag + "' WHERE " + Queries.COLUMN_NAME + "='" + modul + "'");
    }

    public Cursor getAllDataCursor() {
        Cursor cursor = databaseModules.rawQuery(Queries.getAllData(Queries.TABLE_MODULES), null);
        return cursor;
    }

    //function getactivemodulescursor
    public Cursor getactivemodulescursor() {
        Cursor cursor = databaseModules.rawQuery(Queries.getAllDataFromActiveModules(), null);
        return cursor;
    }

    public void deleteDB() {
        dbHelper.deleteTable(databaseModules, Queries.TABLE_MODULES);
    }


    //function getactivemodulesstringarray
    public String[] getactivemodulesstringarray() {
        Cursor cursor;
        cursor = getactivemodulescursor();

        int cursorvalue = cursor.getCount();

        String[] activemodules = new String[cursorvalue];

        cursor.moveToFirst();
        if (cursorvalue == 0) {
            return null;
        } else {
            for (int i = 0; i < cursorvalue; i++) {
                int ModulesID = cursor.getColumnIndex(Queries.COLUMN_MODUL);
                activemodules[i] = cursor.getString(ModulesID);
                Log.d("Modul in Array", cursor.getString(ModulesID));
                cursor.moveToNext();
            }
        }
        return activemodules;
    }
}