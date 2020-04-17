package com.wuxiaolong.androidmvpsample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2020/4/17.
 */

public class Bitmap2FileUtil {
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
        String cachePath;//文件保存地址，可自定义
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
