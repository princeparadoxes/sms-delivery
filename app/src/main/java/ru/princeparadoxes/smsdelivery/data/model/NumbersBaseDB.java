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

    public static List<NumbersBase> getAll(Realm realm) {
        RealmResults<NumbersBaseDB> query = realm.where(NumbersBaseDB.class).findAll();
        List<NumbersBase> list = new ArrayList<>();
        if (query != null) {
            for (NumbersBaseDB numbersBaseDB : query) {
                list.add(NumbersBase.fromRealmObject(numbersBaseDB));
            }
        }
        realm.close();
        return list;
    }

    public static void put(@NonNull List<NumbersBase> numbersBases, Realm realm) {
        for (NumbersBase type : numbersBases) {
            putDB(type, realm);
        }
        realm.close();
    }

    public static void put(@NonNull NumbersBase numbersBase, Realm realm) {
        putDB(numbersBase, realm);
        realm.close();
    }

    private static void putDB(@NonNull NumbersBase numbersBase, Realm realm) {
        RealmObject query = realm.where(NumbersBaseDB.class).equalTo("id", numbersBase.getId()).findFirst();
        if (query == null) {
            createItem(numbersBase, realm);
        }
    }

    private static void createItem(NumbersBase numbersBase, Realm realm) {
        realm.beginTransaction();
        NumbersBaseDB object = realm.createObject(NumbersBaseDB.class);
        object.setId(numbersBase.getId());
        object.setName(numbersBase.getName());
        RealmList<NumberDB> list = new RealmList<>();
        for (String s : numbersBase.getNumbers()) {
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
