package ru.princeparadoxes.smsdelivery;

import android.app.Application;
import android.content.Context;


import javax.inject.Inject;

import cat.ppicas.customtypeface.CustomTypeface;
import ru.princeparadoxes.smsdelivery.ui.ActivityHierarchyServer;
import timber.log.Timber;


public class SmsApplication extends Application {
    private SmsComponent component;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;


//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        CustomTypeface.getInstance().registerTypeface("regular", getAssets(), "fonts/roboto_regular.ttf");
        CustomTypeface.getInstance().registerTypeface("light", getAssets(), "fonts/roboto_light.ttf");
        CustomTypeface.getInstance().registerTypeface("medium", getAssets(), "fonts/roboto_medium.ttf");
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//            refWatcher = RefWatcher.DISABLED;
//            refWatcher = LeakCanary.install(this);
        } else {
//            refWatcher = RefWatcher.DISABLED;
        }

        buildComponentAndInject();

        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildComponentAndInject() {
        component = SmsComponent.Initializer.init(this);
        component.inject(this);
    }

    public SmsComponent component() {
        return component;
    }

    public static SmsApplication get(Context context) {
        return (SmsApplication) context.getApplicationContext();
    }

//    public RefWatcher getRefWatcher() {
//        return refWatcher;
//    }

}
