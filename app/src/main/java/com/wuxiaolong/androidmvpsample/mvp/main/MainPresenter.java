package com.wuxiaolong.androidmvpsample.mvp.main;

import android.content.Context;
import android.util.Log;

import com.common.lhk.library.mvp.BasePresenter;
import com.common.lhk.library.rxjava.ApiCallbackAdapter;
import com.common.lhk.library.rxjava.SubscriberCallBackFlowable;
import com.common.lhk.library.rxjava.SubscriberCallBackObserver;
import com.wuxiaolong.androidmvpsample.retrofit.ApiStores;
import com.wuxiaolong.androidmvpsample.retrofit.AppClient;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainPresenter extends BasePresenter<IMainView> {

    private ApiStores apiStores;
    private ResourceSubscriber subscriber;
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
        super(view, context);
        apiStores = AppClient.retrofit(context).create(ApiStores.class);
    }

    /**
     * 向后端请求数据
     * @param cityId
     */
    public void loadData(String cityId) {
        onMainView.showLoading();
        if (!isFlowAble){
            Observable<MainBean> observable = apiStores.loadData(cityId);
            Observer observer = new SubscriberCallBackObserver<>(new ApiCallbackAdapter<MainBean>() {
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
            addSubscription(observable, observer);
        } else {
            Flowable<MainBean> flowAble = apiStores.loadFlowableData(cityId);
            subscriber = new SubscriberCallBackFlowable<>(new ApiCallbackAdapter<MainBean>() {
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
            addSubscription(flowAble, subscriber);
        }

    }

    @Override
    protected void unSubscribe() {
        super.unSubscribe();
        if (subscriber!=null&&!subscriber.isDisposed()){
            subscriber.dispose();
            Log.d("Main","unSubscribe");
        }
    }

    /**
     * 模拟不同的接口
     * @param cityId
     */
    public void loadSecondData(String cityId){
        onMainView.showLoading();
        if (apiStores!=null)
        addSubscription(apiStores.loadData(cityId),
                new SubscriberCallBackObserver<>(new ApiCallbackAdapter<MainBean>() {
                    @Override
                    public void onSuccess(MainBean model) {
                        onMainView.getSecondSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        onMainView.getSecondFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        onMainView.hideLoading();
                    }
                }));
    }
    
    public void loadData(){
        onMainView.showLoading();
        if (apiStores!=null)
        addSubscription(apiStores.loadData("feec2e995632ec4329ec4591bdd7c20b","sf","575677355677"),
                new SubscriberCallBackObserver<>(new ApiCallbackAdapter<Root>() {
                    @Override
                    public void onSuccess(Root model) {
                        onMainView.getSecondSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        onMainView.getSecondFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        onMainView.hideLoading();
                    }
                }));
    }
    
    public void postData(){
        onMainView.showLoading();
        if (apiStores!=null)
        addSubscription(apiStores.postData("feec2e995632ec4329ec4591bdd7c20b","sf","575677355677"),
                new SubscriberCallBackObserver<>(new ApiCallbackAdapter<Root>() {
                    @Override
                    public void onSuccess(Root model) {
                        onMainView.getSecondSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        onMainView.getSecondFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        onMainView.hideLoading();
                    }
                }));
    }

}
