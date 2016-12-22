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
            COLUMN_ID + " INT AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_MODUL + " TEXT PRIMARY KEY," +
            COLUMN_FLAG + " INT);";
    private static final String SELECT_ALL_DATA = "SELECT * FROM " + DB_NAME;
    // default mensa string FLAG_TRUE!
    private static final String DEFAULT_MENSA = "INSERT INTO " + DB_NAME + " (" +
            COLUMN_NAME + "," + COLUMN_MODUL + "," + COLUMN_FLAG + ") VALUES (" +
            R.string.namemensa + "," + R.string.modulmensa + "," + FLAG_TRUE + ");";

    // default mensa string FLAG_FALSE!
    private static final String DEFAULT_WEIGHT = "INSERT INTO " + DB_NAME + " (" +
            COLUMN_NAME + "," + COLUMN_MODUL + "," + COLUMN_FLAG + ") VALUES (" +
            R.string.nameweight + "," + R.string.modulweight + "," + FLAG_FALSE + ");";

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

    public static String getDefaultMensa() {
        return DEFAULT_MENSA;
    }

    public static String getDefaultWeight() {
        return DEFAULT_WEIGHT;
    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnFlag() {
        return COLUMN_FLAG;
    }

    public static String getSelectAllData() {
        return SELECT_ALL_DATA;
    }
}
