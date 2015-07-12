package ru.princeparadoxes.smsdelivery.data;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.realm.Realm;
import ru.princeparadoxes.smsdelivery.BuildConfig;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;

@ApplicationScope
public class DatabaseService {
    private static final String DATABASE_NAME = "_fitfat.db";

    private final Context mContext;
    private final boolean mIsDebug;

    @Nullable
    private Realm mRealm;

    @Inject
    public DatabaseService(Context context) {
        mContext = context;
        mIsDebug = BuildConfig.DEBUG;
    }

    public Realm request() {
        mRealm = Realm.getInstance(mContext, mIsDebug ? "debug" + DATABASE_NAME : DATABASE_NAME);
        return mRealm;
    }

    public void closeRequest() {
        if (mRealm != null) {
            mRealm.close();
            mRealm = null;
        }
    }

}
