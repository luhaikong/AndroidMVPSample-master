package com.wuxiaolong.androidmvpsample.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lhk on 2017/2/28.
 */

public class SetCacheInterceptor implements Interceptor {

    private Context context;

    public SetCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        int maxAge = 60*60; // 有网络时 设置缓存超时时间1个小时
        int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
        Request request = chain.request();
        if (isNetWorkAvailable(context)) {
            request= request.newBuilder()
                    .addHeader("apikey", "2ffc3e48c7086e0e1faa003d781c0e69")
                    .cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
                    .build();
        } else {
            request= request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                    .build();
        }
        Response response = chain.proceed(request);
        if (isNetWorkAvailable(context)) {
            response= response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            response= response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetWorkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
