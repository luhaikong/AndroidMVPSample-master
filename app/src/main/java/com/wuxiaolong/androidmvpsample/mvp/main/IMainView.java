package com.wuxiaolong.androidmvpsample.mvp.main;

/**
 * Created by WuXiaolong on 2015/9/23.
 * 处理业务需要哪些方法
 */
public interface IMainView {

    void getDataSuccess(MainBean model);

    void getDataFail(String msg);

    void showLoading();

    void hideLoading();

    //模拟不同的接口
    void getSecondSuccess(MainBean model);

    void getSecondFail(String msg);

    //模拟不同的接口
    void getSecondSuccess(Root model);

}
