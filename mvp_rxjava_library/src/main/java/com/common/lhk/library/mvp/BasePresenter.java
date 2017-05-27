package com.common.lhk.library.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @param <V> View 接口
 */
public abstract class BasePresenter<V> implements Presenter<V> {
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V view) {
        this.mCompositeDisposable = new CompositeDisposable();
        this.attachView(view);
    }

    @Override
    public void detachView() {
        unSubscribe();
    }

    /**
     * 取消所有订阅关系
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 背压方式添加订阅关系
     * @param subscriber
     */
    protected void addSubscription(ResourceSubscriber subscriber){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscriber);
    }

    /**
     * 普通方式添加订阅关系
     * @param disposable
     */
    protected void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

}
