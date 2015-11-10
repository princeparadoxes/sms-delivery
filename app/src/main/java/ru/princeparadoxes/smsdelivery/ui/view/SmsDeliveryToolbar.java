package ru.princeparadoxes.smsdelivery.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.util.Strings;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SmsDeliveryToolbar extends Toolbar {

    @InjectView(R.id.icon_logo)
    protected ImageView logo;

    private final int iconColor;

    private String title;

    public SmsDeliveryToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        iconColor = getResources().getColor(R.color.icons);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // some default configuration
        ButterKnife.inject(this);
        setTitle(R.string.application_name);
    }

    public void setLogoVisibility(boolean visibility) {
        logo.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void setTitleVisibility(boolean visibility) {
        if (visibility) {
            if (Strings.isBlank(title)) {
                setTitle(R.string.application_name);
            } else {
                setTitle(title);
            }
        } else {
            title = getTitle().toString();
            setTitle(null);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.title = title == null ? null : title.toString();
    }

    @Override
    public void setTitle(int resId) {
        super.setTitle(resId);
        title = getResources().getString(resId);
    }

    @Override
    public void inflateMenu(int resId) {
        super.inflateMenu(resId);
        // tint menu icons with theme color
        for (int i = 0; i < getMenu().size(); i++) {
            MenuItem menuItem = getMenu().getItem(i);
            final Drawable icon = menuItem.getIcon();
            if (icon != null) {
                icon.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public void setNavigationIconVisibility(boolean visibility) {
        Drawable drawable = null;
        if (visibility) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getBackDrawableLollipop();
            } else {
                drawable = getResources().getDrawable(R.drawable.ic_arrow_left);
            }
            if (drawable != null) {
                drawable.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
        setNavigationIcon(drawable);
    }

    public void setDrawerIconVisibility(boolean visibility) {
        Drawable drawable = null;
        if (visibility) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getResources().getDrawable(R.drawable.ic_menu, null);
            } else {
                drawable = getResources().getDrawable(R.drawable.ic_menu);
            }
            drawable.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        }
        setNavigationIcon(drawable);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getBackDrawableLollipop() {
        return getResources().getDrawable(R.drawable.ic_arrow_left, null);
    }
}
