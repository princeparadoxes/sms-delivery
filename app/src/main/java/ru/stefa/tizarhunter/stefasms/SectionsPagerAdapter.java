package ru.stefa.tizarhunter.stefasms;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.Locale;

import ru.stefa.tizarhunter.stefasms.screens.archive.ArchiveFragment;
import ru.stefa.tizarhunter.stefasms.screens.numbers.NumbersFragment;
import ru.stefa.tizarhunter.stefasms.screens.send.SendFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
private Context mContext;
    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext= context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SendFragment();
            case 1:
                return NumbersFragment.newInstance(position + 1, mContext);
            case 2:
                return ArchiveFragment.newInstance(position + 1, mContext);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return mContext.getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}
