package ru.princeparadoxes.smsdelivery.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NumbersBase implements Parcelable {
    private int id;
    private String name;
    private List<String> numbers;

    public NumbersBase(int id, String name, List<String> numbers) {
        this.id = id;
        this.name = name;
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

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public static NumbersBase fromRealmObject(@NonNull NumbersBaseDB type) {
        ArrayList<String> list = new ArrayList<>();
        for (NumberDB numberDB : type.getNumbers()) {
            list.add(numberDB.getNumber());
        }
        return new NumbersBase(type.getId(), type.getName(), list);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.numbers);
    }

    protected NumbersBase(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.numbers = in.createStringArrayList();
    }

    public static final Creator<NumbersBase> CREATOR = new Creator<NumbersBase>() {
        public NumbersBase createFromParcel(Parcel source) {
            return new NumbersBase(source);
        }

        public NumbersBase[] newArray(int size) {
            return new NumbersBase[size];
        }
    };
}
