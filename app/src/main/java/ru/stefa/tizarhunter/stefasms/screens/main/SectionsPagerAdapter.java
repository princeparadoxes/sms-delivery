package ru.stefa.tizarhunter.stefasms.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import ru.stefa.tizarhunter.stefasms.R;
import ru.stefa.tizarhunter.stefasms.screens.main.archive.ArchiveFragment;
import ru.stefa.tizarhunter.stefasms.screens.main.base.list.NumbersBaseListFragment;
import ru.stefa.tizarhunter.stefasms.screens.main.send.SendFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SendFragment();
            case 1:
                return new NumbersBaseListFragment();
            case 2:
                return ArchiveFragment.newInstance(position + 1, context);
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
                return context.getString(R.string.title_send).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_number).toUpperCase(l);
            case 2:
                return context.getString(R.string.title_archive).toUpperCase(l);
        }
        return null;
    }
}
