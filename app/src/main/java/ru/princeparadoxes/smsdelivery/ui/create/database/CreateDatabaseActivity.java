package ru.princeparadoxes.smsdelivery.ui.create.database;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.SmsComponent;
import ru.princeparadoxes.smsdelivery.base.HasComponent;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseActivity;
import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreen;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.data.DataService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class CreateDatabaseActivity extends BaseActivity implements HasComponent<CreateDatabaseComponent> {
    @Inject
    Presenter presenter;

    private CreateDatabaseComponent component;

    @Override
    protected void onCreateComponent(SmsComponent ozomeComponent) {
        component = DaggerCreateDatabaseComponent.builder().
                smsComponent(ozomeComponent).build();
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        component = null;
        super.onDestroy();
    }

    @Override
    protected int layoutId() {
        return R.layout.create_database_layout;
    }

    @Override
    protected BasePresenter<? extends BaseView> presenter() {
        return presenter;
    }

    @Override
    protected int viewId() {
        return R.id.create_database_layout;
    }

    @Override
    public CreateDatabaseComponent getComponent() {
        return component;
    }

    @CreateDatabaseScope
    public static final class Presenter extends BasePresenter<CreateDatabaseView> {

        private final DataService dataService;
        private final ActivityScreenSwitcher screenSwitcher;

        @Nullable
        private CompositeSubscription subscriptions;

        @Inject
        public Presenter(DataService dataService, ActivityScreenSwitcher screenSwitcher) {
            this.dataService = dataService;
            this.screenSwitcher = screenSwitcher;
        }

        @Override
        protected void onLoad() {
            super.onLoad();
            subscriptions = new CompositeSubscription();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (subscriptions != null) {
                subscriptions.unsubscribe();
                subscriptions = null;
            }
        }

    }

    public static final class Screen extends ActivityScreen {

        public Screen() {
        }

        @Override
        protected void configureIntent(@NonNull Intent intent) {
        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return CreateDatabaseActivity.class;
        }
    }
}
