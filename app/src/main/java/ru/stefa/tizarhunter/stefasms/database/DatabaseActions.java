package ru.stefa.tizarhunter.stefasms.database;

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
        final String SQL_CREATE_ENTRIES = "CREATE TABLE " + tableName + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NUMBER + " INTEGER);";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void dropTable(String tableName)
    {
        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public ArrayList<String> listTables()
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        String query = "SELECT * FROM sqlite_master WHERE type = 'table'";
        Cursor cursor = db.rawQuery(query, null);
        for (int i = 0; i < cursor.getColumnCount(); i++)
        {
            arrayList.add(cursor.getColumnName(i));
        }
        cursor.close();
        return arrayList;
    }
}
