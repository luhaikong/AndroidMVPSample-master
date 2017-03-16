package com.common.lhk.library.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by lhk on 2016/5/18.
 */
public class SubscriberCallBackObserver<T> implements Observer<T> {
    private ApiCallbackAdapter<T> apiCallback;

    public SubscriberCallBackObserver(ApiCallbackAdapter<T> apiCallback) {
        this.apiCallback = apiCallback;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            apiCallback.onFailure(code, msg);
        } else {
            apiCallback.onFailure(0, e.getMessage());
        }
        apiCallback.onCompleted();
    }

    @Override
    public void onComplete() {
        apiCallback.onCompleted();
    }

    @Override
    public void onSubscribe(Disposable d) {
        apiCallback.onSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        apiCallback.onSuccess(t);
    }
}
