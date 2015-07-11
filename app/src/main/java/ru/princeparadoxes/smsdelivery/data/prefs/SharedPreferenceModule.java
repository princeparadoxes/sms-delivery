package ru.princeparadoxes.smsdelivery.data.prefs;

import android.app.Application;
import android.content.SharedPreferences;

import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class SharedPreferenceModule {

    private static final String SP_NAME = "smsdelivery";


    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences(SP_NAME, MODE_PRIVATE);
    }

}
