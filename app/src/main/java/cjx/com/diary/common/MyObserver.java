package cjx.com.diary.common;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by bear on 2017/4/27.
 */

public abstract class MyObserver<T> implements Observer<T> {

    public abstract void onSuccess(T t);
    public abstract void onError(String msg);
    public abstract void onFinish();
    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable throwable) {
        onError(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
