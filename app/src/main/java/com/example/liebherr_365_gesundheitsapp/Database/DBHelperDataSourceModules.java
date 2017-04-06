package com.example.liebherr_365_gesundheitsapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelperDataSourceModules {
    private static final String LOG_TAG = DBHelperDataSourceData.class.getSimpleName();

    private SQLiteDatabase databaseModules;
    private DBHelperModules dbHelperModules;

    public DBHelperDataSourceModules(Context context) {
        Log.d(LOG_TAG, "<MODULES>Unsere DataSource erzeugt jetzt den dbHelper.<MODULES>");
        dbHelperModules = new DBHelperModules(context);
        databaseModules = dbHelperModules.getReadableDatabase();
    }

    public void open() {
        Log.d(LOG_TAG, "<MODULES>Eine Referenz auf die Datenbank wird jetzt angefragt.<MODULES>");
        Log.d(LOG_TAG, "<MODULES>Datenbank-Referenz erhalten. Pfad zur Datenbank: " + databaseModules.getPath() + "<MODULES>");
    }

    public void close() {
        dbHelperModules.close();
        Log.d(LOG_TAG, "<MODULES>Datenbank mit Hilfe des DbHelpers geschlossen.<MODULES>");
    }

    public void deletedb(String modulname) {
        databaseModules.delete(ModulesQuery.getDbName(), ModulesQuery.getColumnModul() + "='" + modulname + "'", null);
        Log.d(LOG_TAG, "<MODULES>Datenbank gelöscht<MODULES>");
    }

    //function add modul to database modules
    public void insertmodules(String name, String modul, boolean flag) {
        ContentValues values = new ContentValues();

        values.put(ModulesQuery.getColumnName(), name);
        values.put(ModulesQuery.getColumnModul(), modul);
        values.put(ModulesQuery.getColumnFlag(), String.valueOf(flag));

        databaseModules.insert(ModulesQuery.getDbName(), null, values);
    }

    //function insertdefaultmodules
    public void insertdefaultmodules() {
        Cursor cursor = getAllDataCursor();
        if (cursor.getCount() == 0) {
            insertmodules("Mensa", "ModulMensa", true);
            insertmodules("Gewicht", "ModulWeight", false);
        }
    }

    //function changemodulstatus
    public void changemodulstatus(String modul, boolean flag) {
        databaseModules.execSQL("UPDATE " + ModulesQuery.getDbName() + " SET " + ModulesQuery.getColumnFlag() + "='" + flag + "' WHERE " + ModulesQuery.getColumnName() + "='" + modul + "'");
    }

    public Cursor getAllDataCursor() {
        Cursor cursor = databaseModules.rawQuery(ModulesQuery.getSelectAllData(), null);
        return cursor;
    }

    public Cursor getSelectedDataCursor() {
        Cursor cursor = databaseModules.rawQuery(ModulesQuery.getSelectSelectedData(), null);
        return cursor;
    }

    //function getactivemodulescursor
    private Cursor getactivemodulescursor() {
        Cursor cursor = databaseModules.rawQuery(ModulesQuery.getSelectActiveModules(), null);
        return cursor;
    }

    //TODO: NW
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
                int ModulesID = cursor.getColumnIndex(ModulesQuery.getColumnModul());
                activemodules[i] = cursor.getString(ModulesID);
                Log.d("Modul in Array", cursor.getString(ModulesID));
            }
        }
        return activemodules;
    }
}