package com.wuxiaolong.androidmvpsample.design.dynamicAgent;

public interface ProxyHandlerListener {
    /**
     * 代理类执行之前织入代码
     */
    void onFrontDo();

    /**
     * 代理类执行之后织入代码
     */
    void onNextDo();
}
