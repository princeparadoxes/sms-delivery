package ru.princeparadoxes.smsdelivery.base.tools;

import android.app.Activity;
import android.view.View;

import ru.princeparadoxes.smsdelivery.base.ActivityConnector;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;
import ru.princeparadoxes.smsdelivery.util.KeyboardTools;

import javax.inject.Inject;

@ApplicationScope
public class KeyboardPresenter extends ActivityConnector<Activity> {

    @Inject
    public KeyboardPresenter() {
    }

    public void hide() {
        Activity attachedObject = getAttachedObject();
        if (attachedObject == null) return;
        KeyboardTools.hideSoftKeyboard(attachedObject);
    }

    public void show(View view) {
        KeyboardTools.showSoftKeyboard(view);
    }
}
