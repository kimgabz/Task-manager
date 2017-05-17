package com.intellij.assessment.database;

public class DBConstants {

    public static final String TABLE_TASKS = "tasks";

    public static final String COLUMN_TABLE_ID     = "columnId";
    public static final String COLUMN_ID           = "id";
    public static final String COLUMN_TITLE        = "title";
    public static final String COLUMN_DETAILS      = "details";


    public static final String TABLE_CREATE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_TABLE_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID          + " INTEGER, " +
                    COLUMN_TITLE       + " TEXT, " +
                    COLUMN_DETAILS     + " TEXT " +
                    ")";

    public static final String[] allTaskColumns = {
        COLUMN_TABLE_ID,
        COLUMN_ID,
        COLUMN_TITLE,
        COLUMN_DETAILS
    };

}
