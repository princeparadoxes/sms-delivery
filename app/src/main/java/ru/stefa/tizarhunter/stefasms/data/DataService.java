package ru.stefa.tizarhunter.stefasms.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.stefa.tizarhunter.stefasms.data.database.DatabaseService;
import ru.stefa.tizarhunter.stefasms.data.models.NumbersBaseModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Danil on 25.07.2016.
 */
public class DataService {
    private DatabaseService databaseService;

    public DataService(Context context) {
        databaseService = new DatabaseService();
        databaseService.connectionDatabase(context);
    }

    private static Observable.Transformer<Object, Object> schedulersTransformer =
            listObservable -> listObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    public Observable<NumbersBaseModel> createNumbersBase(String name) {
        return Observable.just(databaseService.createTableNumbers(name));
    }

    public Observable<NumbersBaseModel> createNumbersBase(String name, ArrayList<String> numbers) {
        return Observable.just(databaseService.insertNumbersInTable(name, numbers));
    }

    public Observable<List<NumbersBaseModel>> getNumbersBaseList() {
        return Observable.just(databaseService.getNumbersBaseList());
    }

}
