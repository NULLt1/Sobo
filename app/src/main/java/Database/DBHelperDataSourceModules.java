package Database;

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

    }

    public void open() {
        Log.d(LOG_TAG, "<MODULES>Eine Referenz auf die Datenbank wird jetzt angefragt.<MODULES>");
        databaseModules = dbHelperModules.getWritableDatabase();

        Log.d(LOG_TAG, "<MODULES>Datenbank-Referenz erhalten. Pfad zur Datenbank: " + databaseModules.getPath() + "<MODULES>");
    }

    public void close() {
        dbHelperModules.close();
        Log.d(LOG_TAG, "<MODULES>Datenbank mit Hilfe des DbHelpers geschlossen.<MODULES>");
    }

    public void deletedb() {
        Log.d(LOG_TAG, "<MODULES>Die Modul-DB wird gelöscht <MODULES>");
        databaseModules = dbHelperModules.getWritableDatabase();
        databaseModules.delete(ModulesQuery.getDbName(), null, null);
        dbHelperModules.close();
    }

    //function add modul to database modules
    public void insertdefaultmodules(String modul, String name, boolean flag) {
        ContentValues values = new ContentValues();

        values.put(ModulesQuery.getColumnName(), name);
        values.put(ModulesQuery.getColumnModul(), modul);
        values.put(ModulesQuery.getColumnFlag(), String.valueOf(flag));

        databaseModules.insert(ModulesQuery.getDbName(), null, values);
    }

    //function changemodulstatus
    public void changemodulstatus(String modul) {
        //TODO: Fertig machen
        databaseModules = dbHelperModules.getWritableDatabase();
       // databaseModules.update(ModulesQuery.getDbName(), null, );
        dbHelperModules.close();
    }
    public Cursor getAllDataCursor(){
        databaseModules = dbHelperModules.getWritableDatabase();

        Cursor cursor = databaseModules.rawQuery(ModulesQuery.getSelectAllData(),null);
        dbHelperModules.close();
        return cursor;
    }
}
