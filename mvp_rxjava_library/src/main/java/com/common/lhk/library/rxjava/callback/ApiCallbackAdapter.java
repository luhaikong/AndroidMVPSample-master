package com.common.lhk.library.rxjava.callback;

import io.reactivex.disposables.Disposable;

/**
 * Created by lhk on 2017/3/15.
 * 适配三个返回方法和四个返回方法
 */

public abstract class ApiCallbackAdapter<T> implements ApiCallback<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }
}
