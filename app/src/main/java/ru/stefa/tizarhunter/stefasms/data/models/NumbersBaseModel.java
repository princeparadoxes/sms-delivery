package ru.stefa.tizarhunter.stefasms.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danil on 25.07.2016.
 */
public class NumbersBaseModel implements Parcelable {
    private String name;
    private int countNumbers;
    private long lastUse;
    private List<NumberModel> numberList;

    public String getName() {
        return name;
    }

    public NumbersBaseModel setName(String name) {
        this.name = name;
        return this;
    }

    public int getCountNumbers() {
        return countNumbers;
    }

    public NumbersBaseModel setCountNumbers(int countNumbers) {
        this.countNumbers = countNumbers;
        return this;
    }

    public long getLastUse() {
        return lastUse;
    }

    public NumbersBaseModel setLastUse(long lastUse) {
        this.lastUse = lastUse;
        return this;
    }

    @NonNull
    public List<NumberModel> getNumberList() {
        if (numberList == null) numberList = new ArrayList<>();
        return numberList;
    }

    public NumbersBaseModel setNumberList(List<NumberModel> numberList) {
        this.numberList = numberList;
        return this;
    }

    public NumbersBaseModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.countNumbers);
        dest.writeLong(this.lastUse);
        dest.writeTypedList(this.numberList);
    }

    protected NumbersBaseModel(Parcel in) {
        this.name = in.readString();
        this.countNumbers = in.readInt();
        this.lastUse = in.readLong();
        this.numberList = in.createTypedArrayList(NumberModel.CREATOR);
    }

    public static final Creator<NumbersBaseModel> CREATOR = new Creator<NumbersBaseModel>() {
        @Override
        public NumbersBaseModel createFromParcel(Parcel source) {
            return new NumbersBaseModel(source);
        }

        @Override
        public NumbersBaseModel[] newArray(int size) {
            return new NumbersBaseModel[size];
        }
    };
}
