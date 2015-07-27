package ru.princeparadoxes.smsdelivery.ui.main;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.ui.misc.CoordinatorPageAdapter;

import java.util.Arrays;
import java.util.List;

public enum MainScreens implements CoordinatorPageAdapter.Item {
    SEND_SCREEN(
            R.layout.main_send_view,
            R.string.send_feed_name),
    NUMBERS_SCREEN(
            R.layout.main_databases_view,
            R.string.databases_feed_name),
    ARCHIVE_SCREEN(
            R.layout.main_archive_view,
            R.string.archive_feed_name);

    private final int mResId;
    private final int mStringResId;

    MainScreens(@LayoutRes int resId, @StringRes int stringNameResId) {
        this.mResId = resId;
        this.mStringResId = stringNameResId;
    }

    @Override
    public int getResId() {
        return mResId;
    }

    public static List<CoordinatorPageAdapter.Item> getList() {
        final CoordinatorPageAdapter.Item[] values = MainScreens.values();
        return Arrays.asList(values);
    }

    @Override
    public int getNameResId() {
        return mStringResId;
    }
}
