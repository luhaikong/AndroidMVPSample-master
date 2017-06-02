package com.common.lhk.library.rxjava.callback;


import io.reactivex.disposables.Disposable;

/**
 * Created by lhk on 16/5/7.
 */
public interface ApiCallback<T> {

    void onSuccess(T model);

    void onFailure(int code, String msg);

    void onCompleted();

    void onSubscribe(Disposable d);

}
