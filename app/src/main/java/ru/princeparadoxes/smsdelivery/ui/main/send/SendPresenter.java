package ru.princeparadoxes.smsdelivery.ui.main.send;

import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.ui.main.MainScope;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

@MainScope
public final class SendPresenter extends BasePresenter<SendView> {
    private final DataService dataService;
    private final ActivityScreenSwitcher screenSwitcher;

    private CompositeSubscription subscriptions;

    @Inject
    public SendPresenter(DataService dataService, ActivityScreenSwitcher screenSwitcher) {
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
