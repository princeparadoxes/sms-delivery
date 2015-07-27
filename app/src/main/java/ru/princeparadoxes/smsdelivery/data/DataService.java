package ru.princeparadoxes.smsdelivery.data;

import android.app.Application;
import android.content.Context;

import java.util.List;

import ru.princeparadoxes.smsdelivery.data.model.DatabaseOfPhoneNumbers;
import ru.princeparadoxes.smsdelivery.data.model.NumbersBaseDB;
import ru.princeparadoxes.smsdelivery.data.rx.RequestFunction;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

@ApplicationScope
public class DataService {

    private final Context context;
    private final FileService fileService;
    private final TokenStorage tokenStorage;
    private final DatabaseService databaseService;

    @Inject
    public DataService(Application application, TokenStorage tokenStorage,
                       FileService fileService, DatabaseService databaseService) {
        this.context = application;
        this.fileService = fileService;
        this.tokenStorage = tokenStorage;
        this.databaseService = databaseService;
    }

    public Observable<List<DatabaseOfPhoneNumbers>> getDatabaseOfPhoneNumbers()
    {
        return Observable.create(new RequestFunction<List<DatabaseOfPhoneNumbers>>() {
            @Override
            protected List<DatabaseOfPhoneNumbers> request() {
                return NumbersBaseDB.getAll(databaseService.request());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> addDatabaseOfPhoneNumbers(final String name)
    {
        return Observable.create(new RequestFunction<Boolean>() {
            @Override
            protected Boolean request() {
                NumbersBaseDB.addNewDatabase(name, databaseService.request());
                return true;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
