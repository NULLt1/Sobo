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
    private DBHelperData dbHelperData;

    public DBHelperDataSourceData(Context context) {
        Log.d(LOG_TAG, "<DATA>Unsere DataSource erzeugt jetzt den dbHelper.<DATA>");
        dbHelperData = new DBHelperData(context);
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
        //databaseData.delete(DataQuery.getDbName(), null, null);
        databaseData.execSQL("DROP TABLE IF EXISTS " + DataQuery.getDbName());
        Log.d(LOG_TAG, "<DATA>Datenbank gelöscht<DATA>");
    }

    //function insertdata into database
    public void insertdata(Data data) {
        Log.d("INSERT", "INSERT");

        ContentValues values = new ContentValues();

        values.put(DataQuery.getColumnModul(), data.getModul());
        values.put(DataQuery.getColumnDate(), data.getDate());
        values.put(DataQuery.getColumnPhysicalValues(), data.getPhysicalvalues());
        values.put(DataQuery.getColumnType(), data.getType());

        databaseData.insert(DataQuery.getDbName(), null, values);
    }

    //function deletesingledata in database
    public void deletesingledata(Data data) {
        Log.d("DELETE", "DELETE");
        String modul = data.getModul();
        Log.d("MODUL", modul);
        String date = data.getDate();
        Log.d("DATE", date);
        String[] values = new String[]{modul, date};
        databaseData.delete(DataQuery.getDbName(), DataQuery.getColumnModul() + "=? and " + DataQuery.getColumnDate() + "=?", values);
        Log.d("DELETED", "DELETED");
    }

    //function updatedata in database
    public void updatedata(Data data) {
        Log.d("UPDATE", "UPDATE");
        //call function deletedata
        deletesingledata(data);
        //call function insertdata
        insertdata(data);
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
        //String type = cursor.getString(idType);

        data = new Data(modul, date, physicalvalues, "kg");

        return data;
    }

    public Cursor getPreparedCursorForWeightList() {
        String query = "SELECT * FROM " + DataQuery.getDbName() + " WHERE " + DataQuery.getColumnModul() + "='ModulWeight' ORDER BY " + DataQuery.getColumnId() + " DESC LIMIT 5";
        return databaseData.rawQuery(query, null);
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
        boolean result = false;
        String date = wd.getDate();

        String query = "SELECT " + DataQuery.getColumnDate() + " FROM " + DataQuery.getDbName() + " WHERE " + DataQuery.getColumnModul() + "='" + wd.getModul() + "'";
        Cursor databaseweightresult = databaseData.rawQuery(query, null);

        int count = databaseweightresult.getCount();

        if (count == 0) {
            result = false;
        } else {
            databaseweightresult.moveToFirst();
            int iddate = databaseweightresult.getColumnIndex(DataQuery.getColumnDate());
            String datefound = databaseweightresult.getString(iddate);
            if (date.equals(datefound)) {
                result = true;
            }
            while (databaseweightresult.moveToNext()) {
                datefound = databaseweightresult.getString(databaseweightresult.getColumnIndex(DataQuery.getColumnDate()));
                if (date.equals(datefound)) {
                    result = true;
                }
            }
        }
        return result;
    }

    //function getLatestWeight
    public int getLatestEntry(String modulname) {
        String queryMaxDate = "(SELECT MAX(" + DataQuery.getColumnDate() + ") from " + DataQuery.getDbName() + ")";
        String queryWhere = DataQuery.getColumnDate() + " = " + queryMaxDate + " AND " + DataQuery.getColumnModul() + " ='" + modulname + "'";

        Cursor cursor = databaseData.query(DataQuery.getDbName(), DataQuery.getColumns(), queryWhere, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return 0;
        } else {
            int ID = cursor.getColumnIndex(DataQuery.getColumnPhysicalValues());
            int value = cursor.getInt(ID);

            return value;
        }
    }

    // function getFirstWeight
    public float getFirstWeight() {
        String queryMaxDate = "(SELECT MIN(" + DataQuery.getColumnDate() + ") from " + DataQuery.getDbName() + ")";
        String queryWhere = DataQuery.getColumnDate() + " = " + queryMaxDate;

        Cursor cursor = databaseData.query(DataQuery.getDbName(), DataQuery.getColumns(), queryWhere, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return 0;
        } else {
            int WeightID = cursor.getColumnIndex(DataQuery.getColumnPhysicalValues());
            float lastWeight = cursor.getFloat(WeightID);

            return lastWeight;
        }
    }
}
