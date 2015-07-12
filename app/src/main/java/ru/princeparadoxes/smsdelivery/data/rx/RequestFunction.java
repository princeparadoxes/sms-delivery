package ru.princeparadoxes.smsdelivery.data.rx;

import rx.Observable;
import rx.Subscriber;

public abstract class RequestFunction<T> implements Observable.OnSubscribe<T> {
    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
            subscriber.onNext(request());
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }

    protected abstract T request();
}
