package ru.stefa.tizarhunter.stefasms;

import android.app.Application;

import ru.stefa.tizarhunter.stefasms.data.Storage;

/**
 * Created by Danil on 06.07.2016.
 */
public class SmsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Storage(getSharedPreferences(Storage.KEY_SHARED_PREFERENCES, MODE_PRIVATE));
    }
}
