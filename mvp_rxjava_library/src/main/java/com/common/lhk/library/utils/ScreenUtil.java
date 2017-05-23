package com.common.lhk.library.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtil {
    private static int screenW;
    private static int screenH;
    private static float screenDensity;
    private static DisplayMetrics metric;

    public static void initScreen(Activity mActivity){
        metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
        screenDensity = metric.density;
    }

    public static int getScreenW(){
        if (metric==null){
            throw new IllegalStateException("ScreenUtil未初始化");
        }
        return screenW;
    }

    public static int getScreenH(){
        if (metric==null){
            throw new IllegalStateException("ScreenUtil未初始化");
        }
        return screenH;
    }

    public static float getScreenDensity(){
        if (metric==null){
            throw new IllegalStateException("ScreenUtil未初始化");
        }
        return screenDensity;
    }

    /** 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    public static int dp2px(float dpValue) {
        if (metric==null){
            throw new IllegalStateException("ScreenUtil未初始化");
        }
        return (int) (dpValue * getScreenDensity() + 0.5f);
    }

    /** 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
    public static int px2dp(float pxValue) {
        if (metric==null){
            throw new IllegalStateException("ScreenUtil未初始化");
        }
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }
}
