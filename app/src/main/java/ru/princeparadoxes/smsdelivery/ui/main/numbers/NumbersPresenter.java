package ru.princeparadoxes.smsdelivery.ui.main.numbers;

import java.util.List;

import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.data.TokenStorage;
import ru.princeparadoxes.smsdelivery.data.model.DatabaseOfPhoneNumbers;
import ru.princeparadoxes.smsdelivery.data.model.NumberDB;
import ru.princeparadoxes.smsdelivery.ui.main.MainScope;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
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
        getData();
    }

    private void getData(){
        subscriptions.add(dataService.getDatabaseOfPhoneNumbers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<DatabaseOfPhoneNumbers>>() {
                            @Override
                            public void call(List<DatabaseOfPhoneNumbers> databaseOfPhoneNumberses) {
                                getView().setData(databaseOfPhoneNumberses);
                            }
                        })
        );
    }

    public void addDatabaseNumbers() {
//        DatabaseOfPhoneNumbers databaseOfPhoneNumbers = new DatabaseOfPhoneNumbers();
        dataService.addDatabaseOfPhoneNumbers("Новая база")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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
