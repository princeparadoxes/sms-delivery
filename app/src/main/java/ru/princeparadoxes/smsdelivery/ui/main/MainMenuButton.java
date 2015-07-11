package ru.princeparadoxes.smsdelivery.ui.main;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import ru.princeparadoxes.smsdelivery.R;

public class MainMenuButton extends ImageView {

    public MainMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCheckState(boolean isCheck) {
        final int color = getResources().getColor(isCheck ? R.color.accent : R.color.accent_light);
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}
