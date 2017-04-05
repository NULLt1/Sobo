package com.example.liebherr_365_gesundheitsapp.Database;

public class ModulesQuery {
    private static final String DB_NAME = "MODULES";
    private static final int DB_VERSION = 3;
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_MODUL = "MODUL";
    private static final String COLUMN_FLAG = "FLAG";

    // String create table modules
    private static final String CREATE_DB_MODULES = "CREATE TABLE " + DB_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_MODUL + " VARCHAR(20) UNIQUE, " +
            COLUMN_FLAG + " BOOLEAN);";

    private static final String SELECT_ALL_DATA = "SELECT * FROM " + DB_NAME;

    private static final String SELECT_SELECTED_DATA = "SELECT * FROM " + DB_NAME + " WHERE " + COLUMN_FLAG + " = 'true'";

    public static String getCreateDb() {
        return CREATE_DB_MODULES;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnModul() {
        return COLUMN_MODUL;
    }

    public static String getColumnFlag() {
        return COLUMN_FLAG;
    }

    public static String getSelectAllData() {
        return SELECT_ALL_DATA;
    }

    public static String getSelectSelectedData() {
        return SELECT_SELECTED_DATA;
    }

    ;
}
