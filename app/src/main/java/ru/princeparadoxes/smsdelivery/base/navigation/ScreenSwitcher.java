package ru.princeparadoxes.smsdelivery.base.navigation;

import android.content.Intent;

public interface ScreenSwitcher {
    void open(Screen screen);

    void openForResult(Screen screen, int requestCode);

    void goBackResult(int resultCode, Intent data);

    void setResult(int resultCode, Intent data);

    void goBack();
}
