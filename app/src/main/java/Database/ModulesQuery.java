package Database;

import com.example.liebherr_365_gesundheitsapp.R;

public class ModulesQuery {
    private static final String DB_NAME = "MODULES";
    private static final int DB_VERSION = 3;
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_MODUL = "MODUL";
    private static final String COLUMN_FLAG = "FLAG";

    //Flag for modules
    private static int FLAG_TRUE = 1;
    private static int FLAG_FALSE = 0;

    // String create table modules
    private static final String CREATE_DB_MODULES = "CREATE TABLE " + DB_NAME + " (" +
            COLUMN_ID + " NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_MODUL + " VARCHAR(20) UNIQUE, " +
            COLUMN_FLAG + " BOOLEAN);";

    private static final String SELECT_ALL_DATA = "SELECT * FROM " + DB_NAME;
    // default mensa string FLAG_TRUE!

    private static final String GET_DATA_MODULES = "SELECT * FROM " + DB_NAME;

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


}
