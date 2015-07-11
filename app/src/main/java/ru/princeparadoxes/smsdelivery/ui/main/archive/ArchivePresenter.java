package ru.princeparadoxes.smsdelivery.ui.main.archive;

import android.app.Application;
import android.support.annotation.Nullable;

import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.base.tools.KeyboardPresenter;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.data.TokenStorage;
import ru.princeparadoxes.smsdelivery.ui.main.MainScope;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

@MainScope
public final class ArchivePresenter extends BasePresenter<ArchiveView> {

    private final DataService dataService;
    private final ActivityScreenSwitcher screenSwitcher;
    private final TokenStorage tokenStorage;
    private final KeyboardPresenter keyboardPresenter;
    private final Application application;

    @Nullable
    private CompositeSubscription subscriptions;

    @Inject
    public ArchivePresenter(DataService dataService,
                            ActivityScreenSwitcher screenSwitcher, KeyboardPresenter keyboardPresenter,
                            Application application, TokenStorage tokenStorage
    ) {
        this.dataService = dataService;
        this.screenSwitcher = screenSwitcher;
        this.keyboardPresenter = keyboardPresenter;
        this.application = application;
        this.tokenStorage = tokenStorage;
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
