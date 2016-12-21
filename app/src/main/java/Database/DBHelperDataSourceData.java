package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelperDataSourceData {

    private static final String LOG_TAG = DBHelperDataSourceData.class.getSimpleName();

    private SQLiteDatabase databaseData;
    private DBHelperModules dbHelperData;

    public DBHelperDataSourceData(Context context) {
        Log.d(LOG_TAG, "<DATA>Unsere DataSource erzeugt jetzt den dbHelper.<DATA>");
        dbHelperData = new DBHelperModules(context);

    }

    public void open() {
        Log.d(LOG_TAG, "<DATA>Eine Referenz auf die Datenbank wird jetzt angefragt.<DATA>");
        databaseData = dbHelperData.getWritableDatabase();
        Log.d(LOG_TAG, "<DATA>Datenbank-Referenz erhalten. Pfad zur Datenbank: " + databaseData.getPath() + "<DATA>");
    }

    public void close() {
        dbHelperData.close();
        Log.d(LOG_TAG, "<DATA>Datenbank mit Hilfe des DbHelpers geschlossen.<DATA>");
    }

    public void deletedb() {
        databaseData = dbHelperData.getWritableDatabase();
        databaseData.delete(ModulesQuery.getDbName(), null, null);
        dbHelperData.close();
    }

    //function insert data into database
    public void insertdata(Data data) {
        ContentValues values = new ContentValues();

        values.put(DataQuery.getColumnModul(), data.getModul());
        values.put(DataQuery.getColumnDate(), data.getDate());
        values.put(DataQuery.getColumnPhysicalValues(), data.getPhysicalvalues());
        values.put(DataQuery.getColumnType(), data.getType());

        databaseData.insert(ModulesQuery.getDbName(), null, values);
    }

    //function update data in database
    public void updatedata(Data data) {
        ContentValues values = new ContentValues();

        values.put(DataQuery.getColumnModul(), data.getModul());
        values.put(DataQuery.getColumnDate(), data.getDate());
        values.put(DataQuery.getColumnPhysicalValues(), data.getPhysicalvalues());
        values.put(DataQuery.getColumnType(), data.getType());

        databaseData.replace(ModulesQuery.getDbName(), null, values);
    }

    //function cursorToWeightdata
    private Data cursorToWeightdata(Cursor cursor) {
        Data data;

        int idModul = cursor.getColumnIndex(DataQuery.getColumnModul());
        int idDate = cursor.getColumnIndex(DataQuery.getColumnDate());
        int idWeight = cursor.getColumnIndex(DataQuery.getColumnPhysicalValues());
        int idType = cursor.getColumnIndex(DataQuery.getColumnType());

        String modul = cursor.getString(idModul);
        String date = cursor.getString(idDate);
        float physicalvalues = cursor.getFloat(idWeight);
        String type = cursor.getString(idType);

        data = new Data(modul, date, physicalvalues, type);

        return data;
    }

    //function getAllDataasarray
    public Data[] getAllDataasarray() {
        Cursor cursor = databaseData.query(DataQuery.getDbName(),
                DataQuery.getColumns(), null, null, null, null, DataQuery.getColumnDate());

        int size = cursor.getCount();
        Data[] alldata = new Data[size];

        cursor.moveToFirst();
        int counter = 0;

        while (!cursor.isAfterLast()) {
            alldata[counter] = cursorToWeightdata(cursor);
            cursor.moveToNext();
            counter++;
        }

        cursor.close();
        return alldata;
    }

    //function getAllData
    public List<Data> getAllData() {
        List<Data> DataList = new ArrayList<>();

        Cursor cursor = databaseData.query(DataQuery.getDbName(),
                DataQuery.getColumns(), null, null, null, null, DataQuery.getColumnDate());

        cursor.moveToFirst();
        Data Data;

        while (!cursor.isAfterLast()) {
            Data = cursorToWeightdata(cursor);
            DataList.add(Data);
            cursor.moveToNext();
        }

        cursor.close();
        return DataList;
    }


    //function datealreadysaved
    public boolean datealreadysaved(Data wd) {
        String date = wd.getDate();

        boolean result = false;

        String query = "SELECT " + DataQuery.getColumnDate() + " FROM " +
                DataQuery.getDbName() + " WHERE " +
                DataQuery.getColumnModul() + "='ModulWeight';";
        Cursor databaseweightresult = databaseData.rawQuery(query, null);
        int count = databaseweightresult.getCount();
        if (count == 0) {
            result = false;
        } else {
            databaseweightresult.moveToFirst();
            String databasedate = databaseweightresult.getString(databaseweightresult.getColumnIndex(DataQuery.getColumnDate()));
            if (date.equals(databasedate)) {
                result = true;
            }
            Log.d("TEST", String.valueOf(count));
            while (databaseweightresult.moveToNext()) {
                databasedate = databaseweightresult.getString(databaseweightresult.getColumnIndex(DataQuery.getColumnDate()));
                if (date.equals(databasedate)) {
                    result = true;
                }
            }
        }
        databaseweightresult.close();
        return result;
    }

    //function getLatestWeight
    public int getLatestEntry() {

        //TODO -----> QUERY an neue Datenbank anpassen <----

        String queryMaxDate = "(SELECT MAX(" + DataQuery.getColumnDate() + ") from " + ModulesQuery.getDbName() + ")";
        String queryWhere = DataQuery.getColumnDate() + " = " + queryMaxDate;

        Cursor cursor = databaseData.query(DataQuery.getDbName(), DataQuery.getColumns(), queryWhere, null, null, null, null);
        cursor.moveToFirst();
        int WeightID = cursor.getColumnIndex(DataQuery.getColumnPhysicalValues());
        int lastWeight = cursor.getInt(WeightID);

        return lastWeight;

    }


}
