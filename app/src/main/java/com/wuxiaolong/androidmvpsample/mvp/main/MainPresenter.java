package com.wuxiaolong.androidmvpsample.mvp.main;

import android.content.Context;
import android.util.Log;

import com.common.lhk.library.mvp.BasePresenter;
import com.common.lhk.library.rxjava.ApiCallbackAdapter;
import com.common.lhk.library.rxjava.SubscriberCallBackFlowable;
import com.common.lhk.library.rxjava.SubscriberCallBackObserver;
import com.wuxiaolong.androidmvpsample.retrofit.ApiStores;
import com.wuxiaolong.androidmvpsample.retrofit.AppClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainPresenter extends BasePresenter<IMainView> {

    private ApiStores apiStores;
    private IMainView onMainView;
    private boolean isFlowAble = false;

    @Override
    public void attachView(IMainView view) {
        this.onMainView = view;
    }

    @Override
    public void detachView() {
        super.detachView();
        this.onMainView = null;
    }

    public MainPresenter(IMainView view, Context context) {
        super(view);
        apiStores = AppClient.retrofit(context).create(ApiStores.class);
    }

    /**
     * 向后端请求数据
     * @param cityId
     */
    public void loadData(String cityId) {
        onMainView.showLoading();
        if (isFlowAble){
            ResourceSubscriber subscriber = new SubscriberCallBackFlowable<>(new ApiCallbackAdapter<MainBean>() {
                @Override
                public void onSuccess(MainBean model) {
                    onMainView.getDataSuccess(model);
                }

                @Override
                public void onFailure(int code, String msg) {
                    onMainView.getDataFail(msg);
                }

                @Override
                public void onCompleted() {
                    onMainView.hideLoading();
                }
            });
            apiStores.loadFlowableData(cityId)
                    .doOnNext(new Consumer<MainBean>() {
                        @Override
                        public void accept(MainBean mainBean) throws Exception {
                            Log.d("OnNext-io",mainBean.getWeatherinfo().toString());
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Predicate<MainBean>() {
                        @Override
                        public boolean test(MainBean o) throws Exception {
                            onMainView.filter(o);
                            return true;
                        }
                    })
                    .doOnNext(new Consumer<MainBean>() {
                        @Override
                        public void accept(MainBean mainBean) throws Exception {
                            Log.d("OnNext-main",mainBean.getWeatherinfo().toString());
                            onMainView.doOnNext(mainBean);
                        }
                    })
                    .subscribe(subscriber);
            addSubscription(subscriber);
        } else {
            final Observer observer = new SubscriberCallBackObserver<>(new ApiCallbackAdapter<MainBean>() {
                @Override
                public void onSuccess(MainBean model) {
                    onMainView.getDataSuccess(model);
                }

                @Override
                public void onFailure(int code, String msg) {
                    onMainView.getDataFail(msg);
                }

                @Override
                public void onCompleted() {
                    onMainView.hideLoading();
                }
            });
            apiStores.loadData(cityId)
                    .doOnNext(new Consumer<MainBean>() {
                        @Override
                        public void accept(MainBean mainBean) throws Exception {
                            Log.d("doOnNext-io",mainBean.getWeatherinfo().toString());
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Predicate<MainBean>() {
                        @Override
                        public boolean test(MainBean o) throws Exception {
                            onMainView.filter(o);
                            return true;
                        }
                    })
                    .doOnNext(new Consumer<MainBean>() {
                        @Override
                        public void accept(MainBean o) throws Exception {
                            Log.d("doOnNext-main",o.getWeatherinfo().toString());
                            onMainView.doOnNext(o);
                        }
                    })
                    .subscribe(new Consumer<MainBean>() {
                        @Override
                        public void accept(MainBean o) throws Exception {
                            observer.onNext(o);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            observer.onError(throwable);
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            observer.onComplete();
                        }
                    }, new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            observer.onSubscribe(disposable);
                            addSubscription(disposable);
                        }
                    });
        }

    }

}
