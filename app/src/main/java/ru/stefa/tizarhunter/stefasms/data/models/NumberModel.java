package ru.stefa.tizarhunter.stefasms.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NumberModel implements Parcelable {
    private String number;

    public String getNumber() {
        return number;
    }

    public NumberModel setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
    }

    public NumberModel() {
    }

    protected NumberModel(Parcel in) {
        this.number = in.readString();
    }

    public static final Parcelable.Creator<NumberModel> CREATOR = new Parcelable.Creator<NumberModel>() {
        @Override
        public NumberModel createFromParcel(Parcel source) {
            return new NumberModel(source);
        }

        @Override
        public NumberModel[] newArray(int size) {
            return new NumberModel[size];
        }
    };
}
