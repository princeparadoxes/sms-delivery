package ru.princeparadoxes.smsdelivery.ui.settings;

import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.ui.ApplicationSwitcher;
import ru.princeparadoxes.smsdelivery.ui.main.MainScope;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

@MainScope
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private final ApplicationSwitcher applicationSwitcher;
    private final DataService dataService;

    private CompositeSubscription subscriptions;

    @Inject
    public SettingsPresenter(ApplicationSwitcher applicationSwitcher,
                             DataService dataService) {
        this.applicationSwitcher = applicationSwitcher;
        this.dataService = dataService;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        subscriptions = new CompositeSubscription();
    }

    @Override
    protected void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }

    public void openFeedback() {
        applicationSwitcher.openFeedbackEmailApplication();
    }

    public void openGooglePlay() {
        applicationSwitcher.openGooglePlayAppPage();
    }
}
