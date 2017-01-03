package Database;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelperModules extends SQLiteOpenHelper {


    public DBHelperModules(Context context) {
        super(context, ModulesQuery.getDbName(), null, ModulesQuery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("", "<MODULES>Die Tabelle wird mit SQL-Befehl: " + ModulesQuery.getCreateDb() + " angelegt.<MODULES>");
            db.execSQL(ModulesQuery.getCreateDb());
        } catch (Exception ex) {
            Log.e("", "<MODULES>Fehler beim Anlegen der Tabelle: " + ex.getMessage() + "<MODULES>");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }
}
