package ru.princeparadoxes.smsdelivery.data;

import android.app.Application;
import android.content.Context;

import java.util.List;

import ru.princeparadoxes.smsdelivery.data.model.NumbersBase;
import ru.princeparadoxes.smsdelivery.data.model.NumbersBaseDB;
import ru.princeparadoxes.smsdelivery.data.rx.RequestFunction;
import ru.princeparadoxes.smsdelivery.ui.ApplicationScope;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;

@ApplicationScope
public class DataService {
    public static final String NO_INTERNET_CONNECTION = "No internet connection";

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

    public Observable<List<NumbersBase>> getNumbersBases()
    {
        return Observable.create(new RequestFunction<List<NumbersBase>>() {
            @Override
            protected List<NumbersBase> request() {
                return NumbersBaseDB.getAll(databaseService.request());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
