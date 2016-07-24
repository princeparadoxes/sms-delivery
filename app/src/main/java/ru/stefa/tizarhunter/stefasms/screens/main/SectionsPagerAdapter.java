package ru.stefa.tizarhunter.stefasms.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.screens.main.archive.ArchiveFragment;
import ru.stefa.tizarhunter.stefasms.screens.main.numbers.NumbersFragment;
import ru.stefa.tizarhunter.stefasms.screens.main.send.SendFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
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
                return mContext.getString(R.string.title_send).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.title_number).toUpperCase(l);
            case 2:
                return mContext.getString(R.string.title_archive).toUpperCase(l);
        }
        return null;
    }
}
