package com.wuxiaolong.androidmvpsample;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

/**
 * Created by Administrator on 2019/2/12.
 * 通过 Context.registerComponentCallbacks() 注册。
 * 注册后，就会被系统回调到。
 */

public class MemoryCallBack implements ComponentCallbacks2 {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
