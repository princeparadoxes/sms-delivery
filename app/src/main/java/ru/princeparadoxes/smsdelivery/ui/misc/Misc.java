package ru.princeparadoxes.smsdelivery.ui.misc;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by Danil on 28.05.2015.
 */
public class Misc {
    public static Drawable getDrawable(int resource, Resources resources){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(resource, null);
        } else {
            return resources.getDrawable(resource);
        }
    }
}
