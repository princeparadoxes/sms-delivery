package ru.princeparadoxes.smsdelivery.data.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DatabaseOfPhoneNumbersDB extends RealmObject {
    private int id;
    private String name;
    private int position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RealmList<NumberDB> getNumbers() {
        return numbers;
    }

    public void setNumbers(RealmList<NumberDB> numbers) {
        this.numbers = numbers;
    }

    public static List<DatabaseOfPhoneNumbers> getAll(Realm realm) {
        RealmResults<DatabaseOfPhoneNumbersDB> query = realm.where(DatabaseOfPhoneNumbersDB.class).findAll();
        query.sort("position", RealmResults.SORT_ORDER_ASCENDING);
        List<DatabaseOfPhoneNumbers> list = new ArrayList<>();
        if (query != null) {
            for (DatabaseOfPhoneNumbersDB databaseOfPhoneNumbersDB : query) {
                list.add(DatabaseOfPhoneNumbers.fromRealmObject(databaseOfPhoneNumbersDB));
            }
        }
        realm.close();
        return list;
    }

    public static void setMinPosition(DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        int newPosition = (int) (realm.where(DatabaseOfPhoneNumbersDB.class).minimumInt("position") - 1);
        databaseOfPhoneNumbers.setPosition(newPosition);
        putDB(databaseOfPhoneNumbers, realm);
    }

    public static void addNewDatabase(String name, Realm realm) {
        int nextID = (int) (realm.where(DatabaseOfPhoneNumbersDB.class).maximumInt("id") + 1);
        int nextPosition = (int) (realm.where(DatabaseOfPhoneNumbersDB.class).maximumInt("position") + 1);
        List<String> list = new ArrayList<>();
        DatabaseOfPhoneNumbers database = new DatabaseOfPhoneNumbers(nextID, name, nextPosition, list);
        putDB(database, realm);
    }

    public static void put(@NonNull DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        putDB(databaseOfPhoneNumbers, realm);
        realm.close();
    }

    private static void putDB(@NonNull DatabaseOfPhoneNumbers newObject, Realm realm) {
        DatabaseOfPhoneNumbersDB query = realm.where(DatabaseOfPhoneNumbersDB.class).equalTo("id", newObject.getId()).findFirst();
        if (query == null) {
            createItem(newObject, realm);
        } else {
            realm.beginTransaction();
            query.setName(newObject.getName());
            query.setPosition(newObject.getPosition());
            RealmList<NumberDB> list = new RealmList<>();
            for (String s : newObject.getNumbers()) {
                list.add(NumberDB.fromObject(s, realm));
            }
            query.setNumbers(list);
            realm.commitTransaction();
        }
    }

    private static void createItem(DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        realm.beginTransaction();
        DatabaseOfPhoneNumbersDB object = realm.createObject(DatabaseOfPhoneNumbersDB.class);
        object.setId(databaseOfPhoneNumbers.getId());
        object.setName(databaseOfPhoneNumbers.getName());
        object.setPosition(databaseOfPhoneNumbers.getPosition());
        RealmList<NumberDB> list = new RealmList<>();
        for (String s : databaseOfPhoneNumbers.getNumbers()) {
            list.add(NumberDB.fromObject(s, realm));
        }
        object.setNumbers(list);
        realm.commitTransaction();
    }

    public static void remove(@NonNull DatabaseOfPhoneNumbers databaseOfPhoneNumbers, Realm realm) {
        DatabaseOfPhoneNumbersDB object = realm.where(DatabaseOfPhoneNumbersDB.class).equalTo("id", databaseOfPhoneNumbers.getId()).findFirst();
        realm.beginTransaction();
        object.removeFromRealm();
        realm.commitTransaction();
        realm.close();
    }
}
