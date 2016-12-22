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

        //TODO: Hier verbessern /Löschen
        databaseModules=dbHelperModules.getWritableDatabase();
        dbHelperModules.defaultValues(databaseModules);

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
        databaseModules = dbHelperModules.getWritableDatabase();
        databaseModules.delete(ModulesQuery.getDbName(), null, null);
        dbHelperModules.close();
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
