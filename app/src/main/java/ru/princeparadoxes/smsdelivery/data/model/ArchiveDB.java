package ru.princeparadoxes.smsdelivery.data.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ArchiveDB extends RealmObject {
    private int id;
    private String text;
    private int countNumbers;
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCountNumbers() {
        return countNumbers;
    }

    public void setCountNumbers(int countNumbers) {
        this.countNumbers = countNumbers;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static List<Archive> getAll(Realm realm) {
        RealmResults<ArchiveDB> query = realm.where(ArchiveDB.class).findAll();
        List<Archive> list = new ArrayList<Archive>();
        if (query != null) {
            for (ArchiveDB archiveDB : query) {
                list.add(Archive.fromRealmObject(archiveDB));
            }
        }
        realm.close();
        return list;
    }

    public static void put(@NonNull List<Archive> archives, Realm realm) {
        for (Archive type : archives) {
            putDB(type, realm);
        }
        realm.close();
    }

    public static void put(@NonNull Archive archive, Realm realm) {
        putDB(archive, realm);
        realm.close();
    }

    private static void putDB(@NonNull Archive archive, Realm realm) {
        RealmObject query = realm.where(ArchiveDB.class).equalTo("id", archive.getId()).findFirst();
        if (query == null) {
            createItem(archive, realm);
        }
    }

    private static void createItem(Archive archive, Realm realm) {
        realm.beginTransaction();
        ArchiveDB object = realm.createObject(ArchiveDB.class);
        object.setId(archive.getId());
        object.setText(archive.getText());
        object.setCountNumbers(archive.getCountNumbers());
        object.setTimestamp(archive.getTimestamp());
        realm.commitTransaction();
    }

    public static void remove(@NonNull Archive archive, Realm realm) {
        ArchiveDB object = realm.where(ArchiveDB.class).equalTo("id", archive.getId()).findFirst();
        realm.beginTransaction();
        object.removeFromRealm();
        realm.commitTransaction();
        realm.close();
    }
}
