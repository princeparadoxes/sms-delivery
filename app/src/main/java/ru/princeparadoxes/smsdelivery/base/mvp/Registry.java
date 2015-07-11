package ru.princeparadoxes.smsdelivery.base.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;

import ru.princeparadoxes.smsdelivery.ui.ActivityHierarchyServer;
import ru.princeparadoxes.smsdelivery.util.Strings;

import java.util.HashMap;

import butterknife.ButterKnife;
import timber.log.Timber;


public class Registry {
    private static final HashMap<String, Entry> registers = new HashMap<>();
    public static final ActivityHierarchyServer.Empty SERVER = new ActivityHierarchyServer.Empty() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            final String key = getKey(activity);
            final Entry entry = registers.get(key);
            Timber.d("%s onActivityCreated", key);
            if (entry != null && savedInstanceState != null) {
                entry.presenter.onRestore(savedInstanceState);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onActivityStarted(Activity activity) {
            final String key = getKey(activity);
            final Entry entry = registers.get(key);
            Timber.d("%s onActivityStarted", key);
            if (entry != null) {
                final BaseView view = ButterKnife.findById(activity, entry.viewId);
                entry.presenter.takeView(view);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onActivityStopped(Activity activity) {
            final String key = getKey(activity);
            final Entry entry = registers.get(key);
            Timber.d("%s onActivityStopped", key);
            if (entry != null) {
                final BaseView view = ButterKnife.findById(activity, entry.viewId);
                entry.presenter.dropView(view);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            final String key = getKey(activity);
            final Entry entry = registers.get(key);
            Timber.d("%s onActivitySaveInstanceState", key);
            if (entry != null) {
                entry.presenter.onSave(outState);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            final String key = getKey(activity);
            Timber.d("%s onActivityDestroyed", key);
            registers.remove(key);
        }
    };

    public static <V extends BaseView> void add(Activity activity, @IdRes int viewId, BasePresenter<V> presenter) {
        registers.put(getKey(activity), new Entry<>(viewId, presenter));
    }

    private static String getKey(Activity activity) {
        StringBuilder builder = new StringBuilder();
        builder.append(activity.getClass().getName());
        if (activity instanceof BaseActivity) {
            final String uniqueKey = ((BaseActivity) activity).uniqueKey();
            if (!uniqueKey.isEmpty()) {
                builder.append(Strings.DOT).append(uniqueKey);
            }
        }
//        final String action = activity.getIntent().getAction();
//        if (action != null) {
//            builder.append(Strings.DOT).append(action);
//        }
//        final Uri data = activity.getIntent().getData();
//        if (data != null) {
//            builder.append(Strings.DOT).append(data.toString());
//        }
//        final Bundle extras = activity.getIntent().getExtras();
//        if (extras != null) {
//            for (String key : extras.keySet()) {
//                Object value = extras.get(key);
//
//                String valueString;
//                if (value.getClass().isArray()) {
//                    valueString = Arrays.toString((Object[]) value);
//                } else {
//                    valueString = value.toString();
//                }
//
//                builder.append(Strings.DOT);
//                builder.append(key).append(Strings.COLON);
//                builder.append(valueString);
//            }
//        }
        return builder.toString();
    }

    private static class Entry<V extends BaseView> {
        @IdRes
        public final int viewId;
        public final BasePresenter<V> presenter;

        public Entry(int viewId, BasePresenter<V> presenter) {
            this.viewId = viewId;
            this.presenter = presenter;
        }
    }
}
