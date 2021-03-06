package ru.stefa.tizarhunter.stefasms.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.data.models.ContactModel;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import ru.stefa.tizarhunter.stefasms.screens.main.archive.ArchiveModel;

public class DatabaseService {
    public static final String UID = "_id";
    public static final String NUMBER = "number";
    private Database mDatabase;
    private SQLiteDatabase db;

    public void connectionDatabase(Context context) {
        mDatabase = new Database(context);
        db = mDatabase.getWritableDatabase();
    }

    public NumbersBaseModel createTableNumbers(String tableName) {
        ContentValues newValues = new ContentValues();
        newValues.put(Database.NAME_COLUMN, tableName);
        db.insert(Database.NUMBERS_TABLE, null, newValues);
        final String SQL_CREATE_ENTRIES = "CREATE TABLE `" + tableName + "` (" + UID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT," + NUMBER + " TEXT);";
        db.execSQL(SQL_CREATE_ENTRIES);
        return new NumbersBaseModel()
                .setName(tableName)
                .setCountNumbers(0)
                .setLastUse(System.currentTimeMillis());
    }

    public NumbersBaseModel insertNumbersInTable(String tableName, ArrayList<String> numbers) {
        tableName = "`" + tableName + "`";
        ContentValues newValues = new ContentValues();
        for (int i = 0; i < numbers.size(); i++) {
            newValues.put(NUMBER, numbers.get(i));
            db.insert(tableName, null, newValues);
        }
        return new NumbersBaseModel()
                .setName(tableName)
                .setCountNumbers(numbers.size())
                .setLastUse(System.currentTimeMillis())
                .setNumberList(getNumberList(tableName));
    }

    public void deleteNumbersFromTable(String tableName, ArrayList<String> numbers) {
        tableName = "`" + tableName + "`";
        for (int i = 0; i < numbers.size(); i++) {
            db.delete(tableName, NUMBER + "=" + numbers.get(i), null);
        }
    }

    public void dropTable(String tableName) {
        db.delete(Database.NUMBERS_TABLE, Database.NAME_COLUMN + "=\"" + tableName + "\"", null);
        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS `" + tableName + "`";
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void tableDataList(String tableName) {
        tableName = "`" + tableName + "`";
        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public List<NumbersBaseModel> getNumbersBaseList() {
        ArrayList<String> basesNames = readTableColumn(Database.NUMBERS_TABLE, Database.NAME_COLUMN);
        ArrayList<NumbersBaseModel> numbersModels = new ArrayList<>();
        for (String baseName : basesNames) {
            NumbersBaseModel numbersModel = new NumbersBaseModel()
                    .setName(baseName)
                    .setCountNumbers(getCountTableRow(baseName))
                    .setNumberList(getNumberList(baseName));
            numbersModels.add(numbersModel);
        }
        return numbersModels;
    }

    public List<ContactModel> getNumberList(String tableName) {
        List<ContactModel> arrayList = new ArrayList<>();
        tableName = "`" + tableName + "`";
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String s = c.getString(c.getColumnIndex(NUMBER));
                arrayList.add(new ContactModel().setNumber(s));
            }
            while (c.moveToNext());
        }
        c.close();
        return arrayList;
    }

    public void addToArchive(String text, int count, long time) {
        ContentValues newValues = new ContentValues();
        newValues.put(Database.TEXT_COLUMN, text);
        newValues.put(Database.COUNT_COLUMN, count);
        newValues.put(Database.BASES_COLUMN, time);
        db.insert(Database.ARCHIVE_TABLE, null, newValues);
    }

    public List<ArchiveModel> getAllArchive() {
        List<ArchiveModel> archiveModels = new ArrayList<ArchiveModel>();
        Cursor c = db.query(Database.ARCHIVE_TABLE, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                ArchiveModel archiveModel = new ArchiveModel();
                archiveModel.setText(c.getString(c.getColumnIndex(Database.TEXT_COLUMN)));
                archiveModel.setNumberSends(c.getInt(c.getColumnIndex(Database.COUNT_COLUMN)));
                archiveModel.setDateTime(c.getLong(c.getColumnIndex(Database.BASES_COLUMN)));
                archiveModels.add(archiveModel);
            }
            while (c.moveToNext());
        }
        c.close();
        return archiveModels;
    }

    public ArrayList<String> readTableColumn(String tableName, String column) {
        ArrayList<String> arrayList = new ArrayList<String>();
        tableName = "`" + tableName + "`";
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(c.getString(c.getColumnIndex(column)));
            }
            while (c.moveToNext());
        }
        c.close();
        return arrayList;
    }

    public int getCountTableRow(String tableName) {
        Cursor mCount = db.rawQuery("select count(*) from `" + tableName + "`", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        return count;
    }
}
