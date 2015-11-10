package ru.princeparadoxes.smsdelivery.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Archive implements Parcelable {
    private int id;
    private String text;
    private int countNumbers;
    private long timestamp;

    public Archive(int id, String text, int countNumbers, long timestamp) {
        this.id = id;
        this.text = text;
        this.countNumbers = countNumbers;
        this.timestamp = timestamp;
    }

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

    public static Archive fromRealmObject(@NonNull ArchiveDB type) {
        return new Archive(type.getId(), type.getText(), type.getCountNumbers(), type.getTimestamp());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.text);
        dest.writeInt(this.countNumbers);
        dest.writeLong(this.timestamp);
    }

    protected Archive(Parcel in) {
        this.id = in.readInt();
        this.text = in.readString();
        this.countNumbers = in.readInt();
        this.timestamp = in.readLong();
    }

    public static final Creator<Archive> CREATOR = new Creator<Archive>() {
        public Archive createFromParcel(Parcel source) {
            return new Archive(source);
        }

        public Archive[] newArray(int size) {
            return new Archive[size];
        }
    };
}
