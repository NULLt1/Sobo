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
        db.execSQL(ModulesQuery.getDefaultMensa());
        // default weight
        db.execSQL(ModulesQuery.getDefaultWeight());
        //TODO Insert more modules -> new strings in ModulesQuery required
    }
}
