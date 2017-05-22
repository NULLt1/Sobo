package com.example.liebherr_365_gesundheitsapp.Database;

public class Queries {
    //Database
    public static final String DB_NAME = "sobo_database.db";
    public static final int DB_VERSION = 9;

    //tables
    public static final String TABLE_PARSED_DATA = "parsed_data";
    public static final String TABLE_MODULES = "modules";
    public static final String TABLE_DATA = "data";
    public static final String TABLE_MENSA = "mensa";
    public static final String TABLE_HEALTH_CARE = "health_care";

    //columns

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MODUL = "modul";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEASER = "teaser";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_FLAG = "flag";
    public static final String COLUMN_PHYSICAL_VALUES = "physical_values";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_HEADER = "header";
    public static final String COLUMN_MENU = "menu";
    public static final String COLUMN_WEEK_OF_THE_YEAR = "week_of_the_year";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_VENUE = "venue";


    //Create Statements
    public static final String CREATE_TABLE_PARSED_DATA = "CREATE TABLE " + TABLE_PARSED_DATA + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MODUL + " VARCHAR(25), " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TEASER + " Text, " +
            COLUMN_FLAG + " BOOLEAN, " +
            COLUMN_TEXT + " TEXT, FOREIGN KEY (" +
            COLUMN_MODUL + " ) " + "REFERENCES " +
            TABLE_MODULES + "(" + COLUMN_MODUL + "));";

    public static final String CREATE_TABLE_MODULES = "CREATE TABLE " + TABLE_MODULES + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_MODUL + " VARCHAR(20) UNIQUE, " +
            COLUMN_FLAG + " BOOLEAN);";

    public static final String CREATE_TABLE_DATA = "CREATE TABLE " + TABLE_DATA
            + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MODUL + " VARCHAR(25), " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_PHYSICAL_VALUES + " FLOAT, " +
            COLUMN_TYPE + " TEXT, FOREIGN KEY (" +
            COLUMN_MODUL + ") " + "REFERENCES " +
            TABLE_MODULES + "(" + COLUMN_MODUL + "));";

    public static final String CREATE_TABLE_MENSA = "CREATE TABLE " + TABLE_MENSA + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_DAY + " TEXT, " +
            COLUMN_WEEK_OF_THE_YEAR + " INTEGER, " +
            COLUMN_HEADER + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_MENU + " TEXT);";
    public static final String CREATE_TABLE_HEALTH_CARE = "CREATE TABLE " + TABLE_HEALTH_CARE + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_VENUE + " TEXT, " +
            COLUMN_PRICE + " TEXT, " +
            COLUMN_FLAG + " TEXT);";

    //select statements
    public static String getActiveModules() {
        return "SELECT " + COLUMN_MODUL + " FROM " + TABLE_MODULES + " WHERE " + COLUMN_FLAG + " = 'true'";
    }

    public static String dropTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName + ";";
    }

    public static String getColumnDate() {
        return COLUMN_DATE;
    }

    public static String getColumnPhysicalValues() {
        return COLUMN_PHYSICAL_VALUES;
    }

    public static String deleteTable(String tableName) {
        return "DELETE FROM " + tableName + ";";
    }

    public static String getAllData(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    public static String getDataFromSelectedModul(String modul) {
        return "SELECT * FROM " + TABLE_DATA + "where " + COLUMN_MODUL + " = " + modul;
    }

    public static String getAllDataFromActiveModules() {
        return "SELECT * FROM " + TABLE_MODULES + " WHERE " + COLUMN_FLAG + " = 'true' ";
    }

}
