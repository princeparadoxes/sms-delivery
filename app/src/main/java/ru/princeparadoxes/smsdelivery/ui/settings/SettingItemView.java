package ru.princeparadoxes.smsdelivery.ui.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.princeparadoxes.smsdelivery.R;

public class SettingItemView extends LinearLayout {

    @InjectView(R.id.setting_item_icon)
    protected ImageView logo;
    @InjectView(R.id.setting_item_title)
    protected TextView title;
    @InjectView(R.id.setting_item_checkbox)
    protected CheckBox checker;

    private OnClickListener listener;

    private SettingItems item;

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean check = !checker.isChecked();
                checker.setChecked(check);
                if (listener != null) {
                    listener.onClick(SettingItemView.this);
                }
            }
        });
    }

    public void bindView(SettingItems item, OnClickListener listener) {
        this.listener = listener;
        this.item = item;
        logo.setImageResource(item.getIconResId());
        title.setText(item.getTitleResId());
        checker.setVisibility(item.isCheckable() ? View.VISIBLE : View.GONE);
    }

    public SettingItems getItem() {
        return item;
    }

    public void setChecked(boolean state) {
        checker.setChecked(state);
    }

    public boolean isChecked() {
        return checker.isChecked();
    }

    public static interface OnClickListener {
        public void onClick(SettingItemView view);
    }
}
