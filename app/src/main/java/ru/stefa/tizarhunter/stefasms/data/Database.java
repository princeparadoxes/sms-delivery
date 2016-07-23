package ru.stefa.tizarhunter.stefasms.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Database extends SQLiteOpenHelper {
    public static final String NUMBERS_TABLE = "nameTables";
    public static final String NAME_COLUMN = "name";
    public static final String ARCHIVE_TABLE = "archive";
    public static final String TEXT_COLUMN = "text";
    public static final String COUNT_COLUMN = "count";
    public static final String BASES_COLUMN = "bases";
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_NUMBERS = "create table "
            + NUMBERS_TABLE + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + NAME_COLUMN
            + " text not null);";
    private static final String DATABASE_CREATE_ARCHIVE = "create table "
            + ARCHIVE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + TEXT_COLUMN + " text not null, "
            + COUNT_COLUMN + " integer, "
            + BASES_COLUMN + " long);";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_NUMBERS);
        db.execSQL(DATABASE_CREATE_ARCHIVE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
