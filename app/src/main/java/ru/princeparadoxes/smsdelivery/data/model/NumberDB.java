package ru.princeparadoxes.smsdelivery.data.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class NumberDB extends RealmObject {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static NumberDB fromObject(String s, Realm realm) {
        realm.beginTransaction();
        RealmResults<NumberDB> query = realm.where(NumberDB.class).equalTo("number", s).findAll();
        NumberDB numberDB = null;
        if (query != null) {
            for (NumberDB numberDB1 : query) {
                numberDB = numberDB1;
            }
        }
        realm.commitTransaction();
        return numberDB;
    }
}
