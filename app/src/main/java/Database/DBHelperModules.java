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
        Log.d("Datenbank wird angelegt", "sdsd ");
        db.execSQL(ModulesQuery.getCreateDb());

        // call function defaultValues
        defaultValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }

    public void deleteweightdb(SQLiteDatabase db) {
        db.rawQuery("DELETE FROM " + ModulesQuery.getDbName(), null);
    }

    //function defaultValues -> fills database with default values
    public void defaultValues(SQLiteDatabase db) {
        // default mensa
        Log.d("*** MENSA  ***", ModulesQuery.getDefaultMensa());
        db.execSQL(ModulesQuery.getDefaultMensa());
        // default weight
        Log.d("*** MENSA  ***", ModulesQuery.getDefaultWeight());
        db.execSQL(ModulesQuery.getDefaultWeight());
        //TODO Insert more modules -> new strings in ModulesQuery required
    }
}
