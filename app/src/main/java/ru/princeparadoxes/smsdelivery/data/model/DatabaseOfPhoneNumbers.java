package ru.princeparadoxes.smsdelivery.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOfPhoneNumbers implements Parcelable {
    private int id;
    private String name;
    private int position;
    private List<String> numbers;

    public DatabaseOfPhoneNumbers(int id, String name, int position, List<String> numbers) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.numbers = numbers;
    }

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

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public static DatabaseOfPhoneNumbers fromRealmObject(@NonNull DatabaseOfPhoneNumbersDB type) {
        ArrayList<String> list = new ArrayList<>();
        for (NumberDB numberDB : type.getNumbers()) {
            list.add(numberDB.getNumber());
        }
        return new DatabaseOfPhoneNumbers(type.getId(), type.getName(), type.getPosition(), list);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.position);
        dest.writeStringList(this.numbers);
    }

    protected DatabaseOfPhoneNumbers(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.position = in.readInt();
        this.numbers = in.createStringArrayList();
    }

    public static final Creator<DatabaseOfPhoneNumbers> CREATOR = new Creator<DatabaseOfPhoneNumbers>() {
        public DatabaseOfPhoneNumbers createFromParcel(Parcel source) {
            return new DatabaseOfPhoneNumbers(source);
        }

        public DatabaseOfPhoneNumbers[] newArray(int size) {
            return new DatabaseOfPhoneNumbers[size];
        }
    };
}
