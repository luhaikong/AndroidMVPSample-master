package com.wuxiaolong.androidmvpsample.mvp.main;

/**
 * Created by lhk on 2015/9/23.
 * 处理业务需要哪些方法
 */
public interface IMainView {

    void getDataSuccess(MainBean model);

    void getDataFail(String msg);

    void showLoading();

    void hideLoading();

    void filter(MainBean model);

    void doOnNext(MainBean model);

}
