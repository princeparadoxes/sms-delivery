package ru.princeparadoxes.smsdelivery.ui.settings;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import ru.princeparadoxes.smsdelivery.R;

enum SettingItems {
    SETTING(R.id.setting_item_first, R.string.settings_first_title, 0 , true);

    private final int viewId;
    private final int titleResId;
    private final int iconResId;
    private final boolean isCheckable;

    SettingItems(int viewId, @StringRes int titleResId, @DrawableRes int iconResId, boolean isCheckable) {
        this.viewId = viewId;
        this.titleResId = titleResId;
        this.iconResId = iconResId;
        this.isCheckable = isCheckable;
    }

    public int getViewId() {
        return viewId;
    }

    public String getTitle(Context context) {
        return context.getResources().getString(titleResId);
    }

    public int getTitleResId() {
        return titleResId;
    }

    public boolean isCheckable() {
        return isCheckable;
    }

    public int getIconResId() {
        return iconResId;
    }
}
