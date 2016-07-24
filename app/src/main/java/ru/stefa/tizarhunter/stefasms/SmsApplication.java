package ru.stefa.tizarhunter.stefasms;

import android.app.Application;

import ru.stefa.tizarhunter.stefasms.data.DataService;
import ru.stefa.tizarhunter.stefasms.data.preferences.Storage;

/**
 * Created by Danil on 06.07.2016.
 */
public class SmsApplication extends Application {

    private DataService dataService;

    @Override
    public void onCreate() {
        super.onCreate();
        new Storage(getSharedPreferences(Storage.KEY_SHARED_PREFERENCES, MODE_PRIVATE));
        dataService = new DataService(this);
    }

    public DataService getDataService() {
        return dataService;
    }
}
