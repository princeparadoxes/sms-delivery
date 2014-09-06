package ru.stefa.tizarhunter.stefasms.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    private SQLiteDatabase mSQLiteDatabase;

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
//        mSQLiteDatabase = db;
//        String query = "SELECT * FROM sqlite_master WHERE type = 'table'";
//        Cursor cursor = mSQLiteDatabase.rawQuery(query, null);

    }



    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

    }
}
