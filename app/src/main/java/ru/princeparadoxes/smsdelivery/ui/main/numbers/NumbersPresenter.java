package ru.princeparadoxes.smsdelivery.ui.main.numbers;

import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.data.TokenStorage;
import ru.princeparadoxes.smsdelivery.ui.main.MainScope;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

@MainScope
public final class NumbersPresenter extends BasePresenter<NumbersView> {

    private final DataService dataService;
    private final TokenStorage tokenStorage;

    private CompositeSubscription subscriptions;

    @Inject
    public NumbersPresenter(DataService dataService,
                            TokenStorage tokenStorage) {
        this.dataService = dataService;
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
