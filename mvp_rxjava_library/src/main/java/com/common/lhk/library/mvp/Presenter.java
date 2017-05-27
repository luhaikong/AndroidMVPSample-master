package com.common.lhk.library.mvp;

interface Presenter<V> {

    /**
     * 绑定View的接口
     * @param view
     */
    void attachView(V view);

    /**
     * 解除View接口的绑定
     * 释放与Activity/Context绑定的变量或回调
     */
    void detachView();

}
