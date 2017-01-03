package Database;

public class DataQuery {
    private static final String DB_NAME = "DATA";
    private static final int DB_VERSION = 4;
    private static final String COLUMN_MODUL = "MODUL";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_PHYSICAL_VALUES = "PHYSICAL_VALUES";
    private static final String COLUMN_TYPE = "TYPE";
    private static final String[] COLUMNS = {
            COLUMN_MODUL, COLUMN_DATE, COLUMN_PHYSICAL_VALUES
    };

    // String create table data
    private static final String CREATE_DB_DATA = "CREATE TABLE " + DB_NAME
            + " (" +
            COLUMN_MODUL + " VARCHAR(25), " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_PHYSICAL_VALUES + " FLOAT," +
            COLUMN_TYPE + " TEXT, FOREIGN KEY (" +
            COLUMN_MODUL + ") " + "REFERENCES " +
            ModulesQuery.getDbName() + "(" + COLUMN_MODUL + "));";

    public static String getDbName() {
        return DB_NAME;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getColumnModul() {
        return COLUMN_MODUL;
    }

    public static String getColumnDate() {
        return COLUMN_DATE;
    }

    public static String getColumnPhysicalValues() {
        return COLUMN_PHYSICAL_VALUES;
    }

    public static String getColumnType() {
        return COLUMN_TYPE;
    }

    public static String[] getColumns() {
        return COLUMNS;
    }

    public static String getCreateDb() {
        return CREATE_DB_DATA;
    }


    public static String getDataData(String modul) {
        final String GET_DATA_DATA = "SELECT * FROM " + DB_NAME + "where " + COLUMN_MODUL + " = " + modul;
        return GET_DATA_DATA;
    }


}
