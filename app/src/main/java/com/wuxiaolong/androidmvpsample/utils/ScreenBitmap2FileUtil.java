package com.wuxiaolong.androidmvpsample.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2020/4/17.
 */

public class ScreenBitmap2FileUtil {

    /**
     * 截屏
     * @param activity Activity对象
     * @param dialog 界面中的dialog对象
     * @param isDeleteStatusBar 是否需要删除状态栏
     * @return
     */
    public static Bitmap screenShot(@NonNull final Activity activity, Dialog dialog, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        boolean drawingCacheEnabled = decorView.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = decorView.willNotCacheDrawing();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null) {
            decorView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            decorView.layout(0, 0, decorView.getMeasuredWidth(), decorView.getMeasuredHeight());
            decorView.buildDrawingCache();
            bmp = Bitmap.createBitmap(decorView.getDrawingCache());
        }
        if (bmp == null) return null;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }

        if (dialog!=null&&dialog.getWindow()!=null){
            View dialogView = dialog.getWindow().getDecorView();
            int location[] = new int[2];
            decorView.getLocationOnScreen(location);
            int location2[] = new int[2];
            dialogView.getLocationOnScreen(location2);
            dialogView.setDrawingCacheEnabled(true);
            dialogView.buildDrawingCache();
            Bitmap bitmap2 = Bitmap.createBitmap(dialogView.getDrawingCache(), 0, 0, dialogView.getWidth(), dialogView.getHeight());
            Canvas canvas = new Canvas(ret);
            canvas.drawBitmap(bitmap2, location2[0] - location[0], location2[1] - location[1], new Paint());

            dialogView.destroyDrawingCache();
        }

        decorView.destroyDrawingCache();
        decorView.setWillNotCacheDrawing(willNotCacheDrawing);
        decorView.setDrawingCacheEnabled(drawingCacheEnabled);
        return ret;
    }

    /**
     * 将Bitmap类型的图片转化成file类型，便于上传到服务器
     * 使用方法：
     long current = System.currentTimeMillis();
     String time = new SimpleDateFormat("yyyyMMddHHmm").format(new Date(current));
     saveFile(bitmap,"Screen"+time+".png");
     * @param bm bitmap对象
     * @param fileName 文件名字
     * @return
     * @throws IOException
     */
    public File saveFile(Bitmap bm, String fileName, Context context) throws IOException {
        String cachePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();//文件保存地址，可自定义
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            ///sdcard/Android/data/<application package>/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            ///data/data/<application package>/cache
            cachePath = context.getCacheDir().getPath();
        }
        File dirFile = new File(cachePath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(cachePath +"/"+ fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
}
