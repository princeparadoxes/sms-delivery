package ru.princeparadoxes.smsdelivery.ui.main.databases;

import java.util.List;

import javax.inject.Inject;

import ru.princeparadoxes.smsdelivery.base.mvp.BasePresenter;
import ru.princeparadoxes.smsdelivery.base.navigation.activity.ActivityScreenSwitcher;
import ru.princeparadoxes.smsdelivery.data.DataService;
import ru.princeparadoxes.smsdelivery.data.TokenStorage;
import ru.princeparadoxes.smsdelivery.data.model.DatabaseOfPhoneNumbers;
import ru.princeparadoxes.smsdelivery.ui.create.database.CreateDatabaseActivity;
import ru.princeparadoxes.smsdelivery.ui.main.MainScope;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@MainScope
public final class DatabasesPresenter extends BasePresenter<DatabasesView> {

    private final DataService dataService;
    private final TokenStorage tokenStorage;
    private final DatabasesActionsDialogBuilder dialog;
    private final ActivityScreenSwitcher activityScreenSwitcher;

    private CompositeSubscription subscriptions;

    @Inject
    public DatabasesPresenter(DataService dataService,
                              TokenStorage tokenStorage,
                              DatabasesActionsDialogBuilder dialog,
                              ActivityScreenSwitcher activityScreenSwitcher) {
        this.dataService = dataService;
        this.tokenStorage = tokenStorage;
        this.dialog = dialog;
        this.activityScreenSwitcher = activityScreenSwitcher;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        subscriptions = new CompositeSubscription();
        getData();
    }

    private void getData() {
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

    public void openDialog(final int position) {
        dialog.openDialog();
        dialog.setOnDatabasesDialogListener(new DatabasesActionsDialogBuilder.OnDatabasesDialogListener() {
            @Override
            public void delete() {
                getView().deleteElement(position);
            }

            @Override
            public void rename() {

            }

            @Override
            public void moveToTop() {
                getView().moveToTop(position);
            }
        });
    }

    public void addDatabaseNumbers() {
        dataService.addDatabaseOfPhoneNumbers("New base")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        getData();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void moveToTop(DatabaseOfPhoneNumbers databaseOfPhoneNumbers) {
        dataService.moveToTopDatabaseOfPhoneNumbers(databaseOfPhoneNumbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void deleteDatabase(DatabaseOfPhoneNumbers databaseOfPhoneNumbers) {
        dataService.deleteDatabaseOfPhoneNumbers(databaseOfPhoneNumbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    public void openCreateDatabaseScreen() {
        activityScreenSwitcher.open(new CreateDatabaseActivity.Screen());
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
