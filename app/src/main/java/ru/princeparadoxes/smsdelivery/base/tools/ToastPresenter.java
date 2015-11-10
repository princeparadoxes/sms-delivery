package ru.princeparadoxes.smsdelivery.base.tools;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.widget.Toast;

import ru.princeparadoxes.smsdelivery.base.ActivityConnector;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

import javax.inject.Inject;

@ApplicationScope
public class ToastPresenter extends ActivityConnector<Activity> {

    @Inject
    public ToastPresenter() {
    }

    public void show(final String msg) {
        final Activity activity = getAttachedObject();
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void show(@StringRes final int msgRes) {
        final Activity activity = getAttachedObject();
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, msgRes, Toast.LENGTH_LONG).show();
            }
        });
    }

}
