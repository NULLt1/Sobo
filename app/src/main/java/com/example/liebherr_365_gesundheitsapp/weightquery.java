package com.example.liebherr_365_gesundheitsapp;

public class weightquery {
    //private string
    private static final String DB_NAME = "weightdb";
    private static final int DB_Version = 3;
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_BMI = "bmi";

    private static final String CREATE_DB = "CREATE TABLE " + DB_NAME + " (" +
            COLUMN_DATE + " TEXT PRIMARY KEY, " +
            COLUMN_WEIGHT + " FLOAT, " +
            COLUMN_BMI + " FLOAT)";


    private static final String DELETE_DB = "DELETE * FROM " + DB_NAME;
    private static final String DROP_DB = "DROP TABLE " + DB_NAME;

    public weightquery() {

    }

    public static String getCreateDb() {
        return CREATE_DB;
    }

    public static String getDbName() {
        return DB_NAME;

    }

    public static int getDbVersion() {
        return DB_Version;
    }

    public static String getColumnDate() {
        return COLUMN_DATE;
    }

    public static String getColumnWeight() {
        return COLUMN_WEIGHT;
    }

    public static String getColumnBmi() {
        return COLUMN_BMI;
    }

    public static String getDeleteDb() {
        return DELETE_DB;
    }

    public static String getDropDb() {
        return DROP_DB;
    }
}
