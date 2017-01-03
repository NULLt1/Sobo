package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperData extends SQLiteOpenHelper {


    public DBHelperData(Context context) {
        super(context, DataQuery.getDbName(), null, DataQuery.getDbVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataQuery.getCreateDb());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Placeholder 4 action
    }
}
