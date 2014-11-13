package ru.stefa.tizarhunter.stefasms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseActions
{
    private Database mDatabase;
    private SQLiteDatabase db;
    public static final String UID = "_id";
    public static final String NUMBER = "number";

    public void connectionDatabase(Context context)
    {
        mDatabase = new Database(context);
        db = mDatabase.getWritableDatabase();
    }

    public void createTable(String tableName)
    {
        ContentValues newValues = new ContentValues();
        newValues.put(mDatabase.NAME_COLUMN, tableName);
        db.insert(mDatabase.DATABASE_TABLE, null, newValues);
        final String SQL_CREATE_ENTRIES = "CREATE TABLE " + tableName + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NUMBER + " INTEGER);";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void dropTable(String tableName)
    {
        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void tableDataList(String tableName)
    {
        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public ArrayList<String> listTables()
    {
        ArrayList<String> arrayList = readTableColumn(mDatabase.DATABASE_TABLE, mDatabase.NAME_COLUMN);
         return arrayList;
    }

    public ArrayList<String> readTableColumn (String tableName, String column){
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(c.getString(c.getColumnIndex(column)));
            } while (c.moveToNext());
        }
        c.close();
        return arrayList;
    }
}
