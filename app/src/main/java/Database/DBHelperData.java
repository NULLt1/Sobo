package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperData extends SQLiteOpenHelper {


    public DBHelperData(Context context) {
        super(context, DataQuery.getDbName(), null, DataQuery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("$$$$", "Die Tabelle wird mit SQL-Befehl: " + DataQuery.getCreateDb() + " angelegt.");
            db.execSQL(DataQuery.getCreateDb());
        } catch (Exception ex) {
            Log.e("$$$$", "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }

    public void deleteweightdb(SQLiteDatabase db) {
        db.rawQuery("DELETE FROM " + DataQuery.getDbName(), null);
    }
}
