package ru.stefa.tizarhunter.stefasms.screens.main.archive;

public class ArchiveModel {
    private String mText;
    private int mNumberSends;
    private long mDateTime;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getNumberSends() {
        return mNumberSends;
    }

    public void setNumberSends(int numberSends) {
        mNumberSends = numberSends;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(long dateTime) {
        mDateTime = dateTime;
    }
}