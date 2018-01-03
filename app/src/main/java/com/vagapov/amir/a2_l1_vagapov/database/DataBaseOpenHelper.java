package com.vagapov.amir.a2_l1_vagapov.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DataBaseOpenHelper extends SQLiteOpenHelper implements DataBaseDescription {

    private static final String DATABASE_NAME = "notes.db";

    private static final int VERSION = 1;

    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_UUID +", " + COLUMN_TITLE + ", " + COLUMN_ADDRESS +
                        ", " + COLUMN_DESCRIPTION + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
