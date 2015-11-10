package ru.princeparadoxes.smsdelivery;

import android.app.Application;

import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public final class SmsModule {
    private final SmsApplication app;

    public SmsModule(SmsApplication app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return app;
    }

//    @Provides
//    RefWatcher provideRefWatcher() {
//        return app.getRefWatcher();
//    }
}
