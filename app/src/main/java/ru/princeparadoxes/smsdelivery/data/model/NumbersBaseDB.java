package ru.princeparadoxes.smsdelivery.data.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class NumbersBaseDB extends RealmObject {
    private int id;
    private String name;
    private RealmList<NumberDB> numbers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<NumberDB> getNumbers() {
        return numbers;
    }

    public void setNumbers(RealmList<NumberDB> numbers) {
        this.numbers = numbers;
    }

    public static List<DatabaseOfPhoneNumbers> getAll(Realm realm) {
        RealmResults<NumbersBaseDB> query = realm.where(NumbersBaseDB.class).findAll();
        List<DatabaseOfPhoneNumbers> list = new ArrayList<>();
        if (query != null) {
            for (NumbersBaseDB numbersBaseDB : query) {
                list.add(DatabaseOfPhoneNumbers.fromRealmObject(numbersBaseDB));
            }
        }
        realm.close();
        return list;
    }

    public static void put(@NonNull List<DatabaseOfPhoneNumbers> databaseOfPhoneNumberses, Realm realm) {
        for (DatabaseOfPhoneNumbers type : databaseOfPhoneNumberses) {
            putDB(type, realm);
        }
        realm.close();
    }

    public static void addNewDatabase(String name, Realm realm){
        int nextID = (int) (realm.where(NumbersBaseDB.class).maximumInt("id") + 1);
        List<String> list = new ArrayList<>();
        DatabaseOfPhoneNumbers database = new DatabaseOfPhoneNumbers(nextID, name, list);
        putDB(database, realm);
    }
    public static void put(@NonNull DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        putDB(databaseOfPhoneNumbers, realm);
        realm.close();
    }

    private static void putDB(@NonNull DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        RealmObject query = realm.where(NumbersBaseDB.class).equalTo("id", databaseOfPhoneNumbers.getId()).findFirst();
        if (query == null) {
            createItem(databaseOfPhoneNumbers, realm);
        }
    }

    private static void createItem(DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        realm.beginTransaction();
        NumbersBaseDB object = realm.createObject(NumbersBaseDB.class);
        object.setId(databaseOfPhoneNumbers.getId());
        object.setName(databaseOfPhoneNumbers.getName());
        RealmList<NumberDB> list = new RealmList<>();
        for (String s : databaseOfPhoneNumbers.getNumbers()) {
            list.add(NumberDB.fromObject(s, realm));
        }
        object.setNumbers(list);
        realm.commitTransaction();
    }

    public static void remove(@NonNull Archive archive, Realm realm) {
        NumbersBaseDB object = realm.where(NumbersBaseDB.class).equalTo("id", archive.getId()).findFirst();
        realm.beginTransaction();
        object.removeFromRealm();
        realm.commitTransaction();
        realm.close();
    }
}
