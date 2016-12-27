package Database;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelperModules extends SQLiteOpenHelper {


    public DBHelperModules(Context context) {
        super(context, ModulesQuery.getDbName(), null, ModulesQuery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ModulesQuery.getCreateDb());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }

    public void deleteweightdb(SQLiteDatabase db) {
        db.rawQuery("DELETE FROM " + ModulesQuery.getDbName(), null);
    }
}
