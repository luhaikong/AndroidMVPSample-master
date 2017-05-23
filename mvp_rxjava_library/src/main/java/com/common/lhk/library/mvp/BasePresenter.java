package com.common.lhk.library.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public abstract class BasePresenter<V> implements Presenter<V> {
    private CompositeDisposable mCompositeDisposable;
    private Context baseContext;

    public BasePresenter(V view, Context context) {
        this.mCompositeDisposable = new CompositeDisposable();
        this.baseContext = context;
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
     * @param flowable
     * @param subscriber
     */
    protected void addSubscription(Flowable flowable,ResourceSubscriber subscriber){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeDisposable.add(subscriber);
    }

    /**
     * 普通方式添加订阅关系
     * @param observable
     * @param subscriber
     */
    protected void addSubscription(Observable observable, final Observer subscriber) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        Disposable disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        subscriber.onNext(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        subscriber.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        subscriber.onComplete();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        subscriber.onSubscribe(disposable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

}
