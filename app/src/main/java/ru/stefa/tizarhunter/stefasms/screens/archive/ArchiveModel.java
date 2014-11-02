package ru.stefa.tizarhunter.stefasms.screens.archive;

import java.text.DateFormat;

public class ArchiveModel
{
    private String mText;
    private int mNumberSends;
    private DateFormat mDateFormat;

    public String getText()
    {
        return mText;
    }

    public void setText(String text)
    {
        mText = text;
    }

    public int getNumberSends()
    {
        return mNumberSends;
    }

    public void setNumberSends(int numberSends)
    {
        mNumberSends = numberSends;
    }

    public DateFormat getDateFormat()
    {
        return mDateFormat;
    }

    public void setDateFormat(DateFormat dateFormat)
    {
        mDateFormat = dateFormat;
    }
}