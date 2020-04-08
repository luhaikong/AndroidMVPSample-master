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
        // 新建颜色矩阵，修改色调
        ColorMatrix cm = new ColorMatrix();
        // 修改饱和度为0
        cm.setSaturation(0);

        // ColorMatrixColorFilter：一个通过4*5的颜色矩阵来改变颜色的颜色过滤器
        // LightingColorFilter：一个可以模拟简单的灯光效果的颜色过滤器
        // PorterDuffColorFilter：一个可以使用单个颜色和指定Porter-Duff模式来对源像素进行染色颜色过滤器
        // 为画笔 设置颜色过滤
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }
}
