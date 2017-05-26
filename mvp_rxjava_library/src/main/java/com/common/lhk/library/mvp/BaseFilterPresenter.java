package com.common.lhk.library.mvp;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @param <V> View 接口
 * @param <T> Object 数据
 */
public abstract class BaseFilterPresenter<V,T> implements Presenter<V> {
    private CompositeDisposable mCompositeDisposable;
    private ItestinFilter itestinFilter;

    public BaseFilterPresenter(V view) {
        this.mCompositeDisposable = new CompositeDisposable();
        this.attachView(view);
    }

    public void setItestinFilter(ItestinFilter itestinFilter) {
        this.itestinFilter = itestinFilter;
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
     * io线程切换到主线程
     * @param flowable
     * @param subscriber
     */
    protected void addioSubscription(Flowable flowable,ResourceSubscriber subscriber){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        io2main(flowable)
                .filter(new Predicate<T>() {
                    @Override
                    public boolean test(T o) throws Exception {
                        if (itestinFilter!=null){
                            itestinFilter.testInfilter(o);
                        }
                        return true;
                    }
                })
                .subscribe(subscriber);
        mCompositeDisposable.add(subscriber);
    }

    /**
     * 背压方式添加订阅关系
     * 新启线程切换到主线程
     * @param flowable
     * @param subscriber
     */
    protected void addThreadSubscription(Flowable flowable,ResourceSubscriber<T> subscriber){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        newThread2main(flowable)
                .filter(new Predicate() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        if (itestinFilter!=null){
                            itestinFilter.testInfilter(o);
                        }
                        return true;
                    }
                })
                .subscribe(subscriber);
        mCompositeDisposable.add(subscriber);
    }

    /**
     * 普通方式添加订阅关系
     * io线程切换到主线程
     * @param observable
     * @param subscriber
     */
    protected void addioSubscription(Observable observable, final Observer subscriber) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        Disposable disposable = io2main(observable)
                .filter(new Predicate<T>() {
                    @Override
                    public boolean test(T o) throws Exception {
                        if (itestinFilter!=null){
                            itestinFilter.testInfilter(o);
                        }
                        return true;
                    }
                })
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

    /**
     * 普通方式添加订阅关系
     * 新启线程切换到主线程
     * @param observable
     * @param subscriber
     */
    protected void addThreadSubscription(Observable observable, final Observer subscriber) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        Disposable disposable = newThread2main(observable)
                .filter(new Predicate() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        if (itestinFilter!=null){
                            itestinFilter.testInfilter(o);
                        }
                        return true;
                    }
                })
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

    protected Observable io2main(Observable observable){
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected Observable newThread2main(Observable observable){
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected Flowable io2main(Flowable flowable){
        return flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected Flowable newThread2main(Flowable flowable){
        return flowable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
