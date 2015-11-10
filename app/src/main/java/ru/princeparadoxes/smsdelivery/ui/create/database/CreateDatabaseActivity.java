package ru.princeparadoxes.smsdelivery.ui.create.database;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ru.princeparadoxes.smsdelivery.R;
import ru.princeparadoxes.smsdelivery.SmsComponent;
import ru.princeparadoxes.smsdelivery.base.HasComponent;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseActivity;
import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.base.mvp.BaseView;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreen;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.util.Strings;
import rx.subscriptions.CompositeSubscription;

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
        private final ActivityScreenSwitcher activityScreenSwitcher;
        private String name;

        @Nullable
        private CompositeSubscription subscriptions;

        @Inject
        public Presenter(DataService dataService, ActivityScreenSwitcher activityScreenSwitcher) {
            this.dataService = dataService;
            this.activityScreenSwitcher = activityScreenSwitcher;
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

        public void toFirstScene() {
            if (checkView()) {
                getView().toFirstScene();
            }
        }

        public void toSecondScene(String name) {
            if (checkView()) {
                this.name = name;
                getView().toSecondScene();
            }
        }

        public String getName() {
            if (Strings.isBlank(name)) {
                return Strings.EMPTY;
            } else {
                return name;
            }
        }

        public void finish() {
            activityScreenSwitcher.goBack();
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
