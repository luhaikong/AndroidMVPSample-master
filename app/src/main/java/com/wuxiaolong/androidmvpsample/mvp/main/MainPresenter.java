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
import io.reactivex.subscribers.ResourceSubscriber;

public class MainPresenter extends BasePresenter<IMainView> {

    private ApiStores apiStores;
    private ResourceSubscriber subscriber;
    private boolean isFlowable = true;

    public MainPresenter(IMainView view, Context context) {
        attachView(view);
        apiStores = AppClient.retrofit(context).create(ApiStores.class);
    }

    /**
     * 向后端请求数据
     * @param cityId
     */
    public void loadData(String cityId) {
        mvpView.showLoading();
        if (!isFlowable){
            addSubscription(apiStores.loadData(cityId),
                    new SubscriberCallBackObserver<>(new ApiCallbackAdapter<MainBean>() {
                        @Override
                        public void onSuccess(MainBean model) {
                            mvpView.getDataSuccess(model);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            mvpView.getDataFail(msg);
                        }

                        @Override
                        public void onCompleted() {
                            mvpView.hideLoading();
                        }
                    }));
        } else {
            Flowable<MainBean> flowable = apiStores.loadFlowableData(cityId);
            subscriber = new SubscriberCallBackFlowable<>(new ApiCallbackAdapter<MainBean>() {
                @Override
                public void onSuccess(MainBean model) {
                    mvpView.getDataSuccess(model);
                }

                @Override
                public void onFailure(int code, String msg) {
                    mvpView.getDataFail(msg);
                }

                @Override
                public void onCompleted() {
                    mvpView.hideLoading();
                }
            });
            addSubscription(flowable, subscriber);
        }

    }

    public void unSubscribe(){
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
        mvpView.showLoading();
        if (apiStores!=null)
        addSubscription(apiStores.loadData(cityId),
                new SubscriberCallBackObserver<>(new ApiCallbackAdapter<MainBean>() {
                    @Override
                    public void onSuccess(MainBean model) {
                        mvpView.getSecondSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getSecondFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mvpView.hideLoading();
                    }
                }));
    }
    
    public void loadData(){
        mvpView.showLoading();
        if (apiStores!=null)
        addSubscription(apiStores.loadData("feec2e995632ec4329ec4591bdd7c20b","sf","575677355677"),
                new SubscriberCallBackObserver<>(new ApiCallbackAdapter<Root>() {
                    @Override
                    public void onSuccess(Root model) {
                        mvpView.getSecondSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getSecondFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mvpView.hideLoading();
                    }
                }));
    }
    
    public void postData(){
        mvpView.showLoading();
        if (apiStores!=null)
        addSubscription(apiStores.postData("feec2e995632ec4329ec4591bdd7c20b","sf","575677355677"),
                new SubscriberCallBackObserver<>(new ApiCallbackAdapter<Root>() {
                    @Override
                    public void onSuccess(Root model) {
                        mvpView.getSecondSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getSecondFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mvpView.hideLoading();
                    }
                }));
    }

}
