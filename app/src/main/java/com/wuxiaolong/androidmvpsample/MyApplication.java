package com.wuxiaolong.androidmvpsample;

import com.common.lhk.library.BaseApplication;
import com.common.lhk.library.files.AppDataFile;

/**
 * Created by MyPC on 2018/5/21.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDataFile.init("mvpsample");
        this.registerComponentCallbacks(new MemoryCallBack());
    }
}
