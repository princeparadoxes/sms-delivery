package ru.princeparadoxes.smsdelivery.data;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.realm.Realm;
import ru.princeparadoxes.smsdelivery.BuildConfig;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

@ApplicationScope
public class DatabaseService {
    private static final String DATABASE_NAME = "_fitfat.db";

    private final Context context;
    private final boolean isDebug;

    @Nullable
    private Realm mRealm;

    @Inject
    public DatabaseService(Application application) {
        context = application;
        isDebug = BuildConfig.DEBUG;
    }

    public Realm request() {
        mRealm = Realm.getInstance(context, isDebug ? "debug" + DATABASE_NAME : DATABASE_NAME);
        return mRealm;
    }

    public void closeRequest() {
        if (mRealm != null) {
            mRealm.close();
            mRealm = null;
        }
    }

}
