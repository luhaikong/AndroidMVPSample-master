package com.wuxiaolong.androidmvpsample.retrofit;

import android.content.Context;

import com.wuxiaolong.androidmvpsample.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lhk
 * on 2016/3/24.
 */
public class AppClient {
    private static final long DEFAULT_TIMEOUT = 15;
    private static Retrofit mRetrofit;

    public static Retrofit retrofit(Context context) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(cacheClient(context))
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 获取没有缓存设置的OkHttpClient
     * @return
     */
    public static OkHttpClient genericClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            httpClient.addInterceptor(loggingInterceptor);
        }
        return httpClient.build();
    }

    /**
     * 获取有缓存设置的OkHttpClient
     * @param context
     * @return
     */
    public static OkHttpClient cacheClient(Context context) {
        //缓存路径
        File cacheFile = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);//缓存文件为10MB

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new SetCacheInterceptor(context))
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //网络请求日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        return httpClient.build();
    }

}
