package com.wuxiaolong.androidmvpsample;

import com.common.lhk.library.BaseApplication;
import com.common.lhk.library.files.AppDataFile;
import com.wuxiaolong.androidmvpsample.utils.LogUtil;

/**
 * Created by MyPC on 2018/5/21.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDataFile.init("mvpsample");//初始化SD卡文件存储目录
        this.registerComponentCallbacks(new MemoryCallBack());//设置内存管理监听
        LogUtil.initLogger();//初始化日志打印工具
    }
}
