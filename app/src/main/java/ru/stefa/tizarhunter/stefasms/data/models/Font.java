package ru.stefa.tizarhunter.stefasms.data.models;

import android.app.Application;
import android.content.res.AssetManager;
import android.support.annotation.StringRes;

import cat.ppicas.customtypeface.CustomTypeface;
import ru.stefa.tizarhunter.stefasms.R;

/**
 * Created by Danil on 26.07.2016.
 */
public enum Font {
    ROBOTO_REGULAR(R.string.roboto_regular_name, R.string.roboto_regular_path),
    ROBOTO_MEDIUM(R.string.roboto_medium_name, R.string.roboto_medium_path),
    ROBOTO_LIGHT(R.string.roboto_light_name, R.string.roboto_light_path);

    private int typefaceName;
    private int filePath;

    Font(@StringRes int typefaceName, @StringRes int filePath) {
        this.typefaceName = typefaceName;
        this.filePath = filePath;
    }

    public static void registerFonts(Application application) {
        AssetManager assets = application.getAssets();
        for (Font font : Font.values()) {
            String name = application.getString(font.typefaceName);
            String path = application.getString(font.filePath);
            CustomTypeface.getInstance().registerTypeface(name, assets, path);
        }
    }
}
