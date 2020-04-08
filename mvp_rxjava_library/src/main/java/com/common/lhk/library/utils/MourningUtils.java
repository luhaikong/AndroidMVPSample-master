package com.common.lhk.library.utils;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2020/4/8.
 * 全国哀悼日 界面颜色变 灰
 * 使用方法：在需要改变主题色的Activity中使用该方法
 */

public class MourningUtils {

    /**
     * 启动全国哀悼主题色
     * @param activity
     */
    public static void startMouning(Activity activity){
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }
}
