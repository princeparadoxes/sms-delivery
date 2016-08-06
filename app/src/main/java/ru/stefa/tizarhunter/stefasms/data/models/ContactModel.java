package ru.stefa.tizarhunter.stefasms.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {
    private String number;

    public String getNumber() {
        return number;
    }

    public ContactModel setNumber(String number) {
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

    public ContactModel() {
    }

    protected ContactModel(Parcel in) {
        this.number = in.readString();
    }

    public static final Parcelable.Creator<ContactModel> CREATOR = new Parcelable.Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel source) {
            return new ContactModel(source);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };
}
