package com.common.lhk.library;

import android.app.Application;

import com.common.lhk.library.crash.CrashHandler;

/**
 * Created by lhk on 2017/3/16.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance(this);
    }
}
